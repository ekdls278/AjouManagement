package com.AjouManagement.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate currentDate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendarscene);
        initWidgets();
        currentDate = LocalDate.now();
        setMonthView();
    }

    // call from xml
    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(currentDate));
        Map<LocalDate,Integer> imageMap = getImageMap();
        ArrayList<DayItem> daysInMonth = dayMonthArray(currentDate, imageMap);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    // calculate the blank or day
    private ArrayList<DayItem> dayMonthArray(LocalDate date,Map<LocalDate, Integer> imageMap) {
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
                int imageResId = 0; // 이미지 리소스 ID를 초기화합니다.
                LocalDate currentDate = yearMonth.atDay(i - dayOfWeek);
                if (imageMap.containsKey(currentDate)) {
                    imageResId = imageMap.get(currentDate);
                }
                dayMonthArray.add(new DayItem(dayText, imageResId));
            }
        }
        return dayMonthArray;
    }

    private Map<LocalDate, Integer> getImageMap() {
        // 이미지 매핑 정보를 구성하는 코드를 작성합니다.
        // 예시로 아래와 같이 Map을 초기화하고 반환합니다.
        Map<LocalDate, Integer> imageMap = new HashMap<>();
//        imageMap.put(LocalDate.of(2023,5,1), R.drawable.image1);
//        imageMap.put(LocalDate.now().plusDays(1), R.drawable.image2);
        // 필요한 이미지 매핑 정보를 추가로 구성합니다.
        return imageMap;
    }
    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view) {
        currentDate = currentDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        currentDate = currentDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.equals("")) {
            String message = "Selected Date " + dayText+ " " +monthYearFromDate(currentDate);
            Toast.makeText(this,message,Toast.LENGTH_LONG).show();

        }

    }




}