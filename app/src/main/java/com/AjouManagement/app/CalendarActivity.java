package com.AjouManagement.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.AjouManagement.app.RoutineListActivity.RoutineAdapter;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.sql.DriverManager.println;


public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate currentDate;
    private TextView routineTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendarscene);
        initWidgets();
        currentDate = LocalDate.now();
        setMonthView();

    }

    //달력에 보일 요소들을 초기화
    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
        routineTextView = findViewById(R.id.routineTextView);
    }
    // 달성여부를 판단하기 위한 메소드
    private Integer getRoutineState(RoutineDBEntity routine) {
        if (routine.getPerformState() == 1) {
            return 1; // 완성된 상태
        } else {
            return 0; // 미완성의 상태
        }
    }
    //달력을 배치하기 위한 메소드
    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(currentDate));
        RoutineViewModel viewModel = new ViewModelProvider(this).get(RoutineViewModel.class);

        viewModel.getAllRoutines().observe(this, new Observer<List<RoutineDBEntity>>() {
            @Override
            public void onChanged(List<RoutineDBEntity> routineDBEntities) {
                Map<LocalDate, Integer> imageMap = new HashMap<>();
                for (RoutineDBEntity routine : routineDBEntities) {
                    String routineDate = routine.getSelectedDate();
                    LocalDate selectedDate = LocalDate.parse(routineDate, DateTimeFormatter.ofPattern("yyyy/MM/dd"));




                    int totalTodayRoutines = getTodayRoutinesCountForDate(routineDBEntities, routineDate);
                    int completedTodayRoutines = getCompletedTodayRoutinesCountForDate(routineDBEntities, routineDate);
                    setImageView(routineDate, totalTodayRoutines, completedTodayRoutines);
                    //달력 날짜의 정보를 불러 온 다음 '/'를 기준으로 문자열을 나눈다
                    Integer routineState = getRoutineState(routine);
                    String[] parts = routineDate.split("/");
                    int year = Integer.parseInt(parts[0]);
                    int month = Integer.parseInt(parts[1]);
                    int day = Integer.parseInt(parts[2]);
                    // 현재 날짜 이후의 날짜인 경우 루틴 처리를 건너뜁니다.
                    if (selectedDate.isAfter(currentDate)) {
//                        continue;
                        imageMap.put(LocalDate.of(year,month,day),R.drawable.futureroutine);
                    }else {
                        //해당 날짜에 성취도에 따른 이미지를 배치
                        imageMap.put(LocalDate.of(year, month, day), getRoutineImageResource(routineState, totalTodayRoutines, completedTodayRoutines));
                    }
                }

                //리사이클러뷰 구성
                ArrayList<DayItem> daysInMonth = dayMonthArray(currentDate, imageMap);
                CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, CalendarActivity.this, routineDBEntities);
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(CalendarActivity.this, 7);
                calendarRecyclerView.setLayoutManager(layoutManager);
                calendarRecyclerView.setAdapter(calendarAdapter);
            }
        });
    }

    private void setMappingForDate(LocalDate date, int imageResId){
        // 해당 날짜의 View를 찾아옵니다.
        View view = calendarRecyclerView.findViewWithTag(String.valueOf(date.getDayOfMonth()));
        if (view != null) {
            // View에서 이미지를 설정하거나 텍스트를 설정하는 등의 매핑 작업을 수행합니다.
            ImageView imageView = view.findViewById(R.id.imageItem2);
            if (imageView != null) {
                imageView.setImageResource(imageResId);
            }
        }
    }

    //이미지 배치를 위한 메소드
    private void setImageView(String routineDate, int totalTodayRoutines, int completedTodayRoutines) {
        LocalDate today = LocalDate.now();
        LocalDate selectedDate = LocalDate.parse(routineDate,DateTimeFormatter.ofPattern("yyyy/MM/dd"));

        RoutineViewModel routineViewModel = new ViewModelProvider(this).get(RoutineViewModel.class);
        routineViewModel.getTodayRoutines(routineDate).observe(this, new Observer<List<RoutineDBEntity>>() {
            @Override
            public void onChanged(List<RoutineDBEntity> routineDBEntityList) {
                Integer size = routineDBEntityList.size();
                // routinePerformedState 1인 일정만 추출
                List<RoutineDBEntity> completedRoutines = new ArrayList<>();
                for (RoutineDBEntity routine : routineDBEntityList) {
                    if (routine.getPerformState() == 1) {
                        completedRoutines.add(routine);
                    }
                }
                int completedRoutinesCount = completedRoutines.size();

                // 이미지 맵핑에 필요한 값들을 계산한 후에 처리해줍니다.
                int imageResourceId = getRoutineImageResource(1, totalTodayRoutines, completedTodayRoutines);
                // 이미지 리소스를 처리하는 로직을 추가합니다.
            }
        });
    }

    //오늘(선택한 날짜)의 일정의 수를 계산
    private int getTodayRoutinesCountForDate(List<RoutineDBEntity> routineDBEntities, String date) {
        int count = 0;
        for (RoutineDBEntity routine : routineDBEntities) {
            if (routine.getSelectedDate().equals(date)) {
                count++;
            }
        }
        return count;
    }

    //완성된 일정의 수를 계산
    private int getCompletedTodayRoutinesCountForDate(List<RoutineDBEntity> routineDBEntities, String date) {
        int count = 0;
        for (RoutineDBEntity routine : routineDBEntities) {
            if (routine.getSelectedDate().equals(date) && routine.getPerformState() == 1) {
                count++;
            }
        }
        return count;
    }

    //성취도에 따라 다른 이미지를 부여
    private Integer getRoutineImageResource(int routineState, int totalTodayRoutines, int completedTodayRoutines) {
        // 완료된 루틴/날짜의 전체 루틴을 기준으로 성취 정도를 측정합니다.
        double completionRatio = (double) completedTodayRoutines / totalTodayRoutines;

        if (totalTodayRoutines == 0) {
            return R.drawable.empty0; // 루틴이 없는 경우 null값을 반환합니다.
        }
        if (routineState == 1) {
            if (completionRatio > 0.5) {
                return R.drawable.excellent4; // Image for completed routines with high completion ratio
            } else {
                return R.drawable.good3; // Image for completed routines with low completion ratio
            }
        } else if(routineState== 0) {
            if (completionRatio > 0.5) {
                return R.drawable.soso2; // Image for incomplete routines with high completion ratio
            } else {
                return R.drawable.empty0; // Image for incomplete routines with low completion ratio
            }
        } else { return null; }
//            if (completionRatio >= 0.8) {
//                return R.drawable.excellent4; // Image for completed routines with high completion ratio
//            }
//            else if(completionRatio > 0.8 && completionRatio >=0.6){
//                return R.drawable.good3; // Image for completed routines with low completion ratio
//            }
//            else if(completionRatio > 0.6 && completionRatio >=0.4){
//                return R.drawable.soso2; // Image for incomplete routines with high completion ratio
//            }
//            else if(completionRatio > 0.4 && completionRatio >=0.2){
//                return R.drawable.well1; // Image for incomplete routines with low completion ratio
//            } else { return R.drawable.well1; }

    }


    private ArrayList<DayItem> dayMonthArray(LocalDate date, Map<LocalDate, Integer> imageMap) {
        ArrayList<DayItem> dayMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = date.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                dayMonthArray.add(null);
            } else {
                String dayText = String.valueOf(i - dayOfWeek);
                int imageResId = 0;
                LocalDate currentDate = yearMonth.atDay(i - dayOfWeek);
                if (imageMap.containsKey(currentDate)) {
                    imageResId = imageMap.get(currentDate);
                }
                dayMonthArray.add(new DayItem(dayText, imageResId));
            }
        }
        return dayMonthArray;
    }


    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }
    //localdate의 정보를 string으로 바꾸기 위해 사용
    private String YnM(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM");
        return date.format(formatter);
    }

    //이전 달의 정보를 불러온다
    public void previousMonthAction(View view) {
        currentDate = currentDate.minusMonths(1);
        setMonthView();
    }

    //다음 달을 불러 오는 함수
    public void nextMonthAction(View view) {
        currentDate = currentDate.plusMonths(1);
        setMonthView();
    }


    @Override
    public void onItemClick(int position, String dayText) {
        if (!dayText.equals("")) {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(currentDate);
            int zeroplus = Integer.parseInt(dayText);
            String DayDay;
            //1자리 수의 겨우 dayText앞에 0 붙여줌
            if(zeroplus<10) {
                DayDay = YnM(currentDate) +"/"+"0"+dayText;
            } else{
                DayDay = YnM(currentDate) +"/"+dayText;
            }
            // 오늘 날짜를 불러옴

            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            // 오늘 날짜를 기반으로 오늘 날짜의 모든 루틴들을 불러옴
            RoutineViewModel routineViewModel = new ViewModelProvider(this).get(RoutineViewModel.class);
            routineViewModel.getTodayRoutines(DayDay).observe(this, new Observer<List<RoutineDBEntity>>() {
                @Override
                public void onChanged(List<RoutineDBEntity> routineDBEntityList) {
                    List<String> routineTextList = new ArrayList<>();
                    for(RoutineDBEntity routine: routineDBEntityList) {

                        String title = routine.getTitle();
                        String tag = routine.getTag();
                        String routineState;
                        if(routine.getPerformState() ==1) {
                            routineState ="완료";
                        }
                        else if (routine.getPerformState()==0) {
                            routineState = "미완료";
                        }
                        else {
                            routineState = "미완료";
                        }
                        String routineText = "일정: " + title +"|"+ " Tag: " + tag + "  "+" | " +routineState ;
                        routineTextList.add(routineText);
                    }
                    showRoutines(currentDate,dayText,routineTextList);
                }
            });
        }
    }

    // 선택한 날짜에 해당하는 루틴을 화면에 보여주는 메소드
    private void showRoutines(LocalDate currentDate, String dayText,List<String> routineTextList) {
        StringBuilder builder = new StringBuilder();
        for (String routineText : routineTextList) {
            builder.append(routineText).append("\n");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        String Today  =currentDate.format(formatter);
        String str = "<Show the Routine: "+Today+"  " + dayText+ ">\n";
        String routinesText = str+builder.toString();

        routineTextView.setText(routinesText);
    }


}
