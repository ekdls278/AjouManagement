package com.AjouManagement.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.AjouManagement.app.RoutineListActivity.RoutineListActivity;
import com.AjouManagement.app.databinding.ActivityAddRoutineBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class AddRoutineActivity extends AppCompatActivity {

    private ActivityAddRoutineBinding binding;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private final int DYNAMIC_ID = 0x8000;
    RoutineDBEntity routineDBEntity = new RoutineDBEntity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        RoutineViewModel viewModel = new ViewModelProvider(this).get(RoutineViewModel.class);

        super.onCreate(savedInstanceState);
        binding = ActivityAddRoutineBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RoutineListActivity.class);
                startActivity(intent);
            }
        });

        //오늘 날짜 띄우기
        SimpleDateFormat sdf = new SimpleDateFormat("MMM.d(E)");    //textview에 보일 형식
        SimpleDateFormat date_db_form = new SimpleDateFormat("yyyy/MM/dd");

        String today = sdf.format(System.currentTimeMillis());
        TextView selectedDateText = (TextView) findViewById(R.id.SelectedDate);
        selectedDateText.setText(today);
        today = date_db_form.format(System.currentTimeMillis());      //db에 넣을 값세팅
        routineDBEntity.routineDate = today;    //오늘 날짜 entity에 미리 넣어주기(0000/00/00)

        //여기서 할거 : 1. 날짜 선택하고, 2. 날짜 기반으로 요일 구하고, 3. 형식에 맞게 날짜 띄우기
        selectedDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int pYear = calendar.get(Calendar.YEAR);
                int pMonth = calendar.get(Calendar.MONTH);
                int pDay = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(AddRoutineActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {   //날짜 정보 받기
                        SimpleDateFormat temp_form = new SimpleDateFormat("yyyy/MM/dd");
                        month = month + 1;
                        String selectDate = year + "/" + month + "/" + date;
                        Date todayDate;
                        try {
                            todayDate = temp_form.parse(selectDate);
                            selectDate = sdf.format(todayDate);
                            routineDBEntity.routineDate = temp_form.format(todayDate);    //날짜 entity에 넣어주기(0000/00/00)

                        } catch (Exception e) {
                        }

                        selectedDateText.setText(selectDate); //MMM.d(E)

                    }
                }, pYear, pMonth, pDay);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        //repeat 여부 선택
        Switch repeat_switch = findViewById(R.id.repeat_switch);
        ConstraintLayout repeat = findViewById(R.id.repeat_container);
        repeat_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    repeat.setVisibility(View.VISIBLE);
                } else {
                    repeat.setVisibility(View.GONE);
                }
            }
        });

        //태그 컨테이너
        final TableLayout tableLayout = (TableLayout) findViewById(R.id.tag_container);

        for (int i = 0; i < TagData.Tags.length / 4; i++) { //row
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tableRow.setPadding(0, 5, 0, 20); //@px -> dp로 변경하기

            for (int j = 0; j < TagData.Tags.length; j++) {
                final TextView text = new TextView(this);
                final CheckBox checkTag = new CheckBox(this);
                int idx = 4 * i + j;
                checkTag.setId(DYNAMIC_ID + idx);
                checkTag.setText(TagData.Tags[idx]);
                checkTag.setBackgroundColor(Color.parseColor(TagData.TagColors[idx]));
                checkTag.setButtonDrawable(null);
                checkTag.setGravity(Gravity.CENTER);
                checkTag.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                checkTag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        int tag_index = 0;
                        if (isChecked) {
                            checkTag.setBackgroundColor(Color.parseColor("#ffffff"));   //색 다시 설정하기
                            Log.i("tag", checkTag.getText().toString());
                        } else {
                            for (int i = 0; i < TagData.Tags.length; i++) {
                                if (checkTag.getText() == TagData.Tags[i]) {
                                    tag_index = i;
                                }
                                checkTag.setBackgroundColor(Color.parseColor(TagData.TagColors[tag_index]));    //체크 해제하면 원래대로 돌려놓기
                            }
                        }
                    }
                });
                tableRow.addView(checkTag);
            }

            tableLayout.addView(tableRow);
        }

        ImageButton addButton = findViewById(R.id.imageButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String checkDay = "";
                String checkTag = "";
                String startDate = routineDBEntity.routineDate;
                int input_check_flag = 0;
                SimpleDateFormat date_db_form = new SimpleDateFormat("yyyy/MM/dd");

                CheckBox checkedTag0 = (CheckBox) findViewById(DYNAMIC_ID + 0);
                CheckBox checkedTag1 = (CheckBox) findViewById(DYNAMIC_ID + 1);
                CheckBox checkedTag2 = (CheckBox) findViewById(DYNAMIC_ID + 2);
                CheckBox checkedTag3 = (CheckBox) findViewById(DYNAMIC_ID + 3);

                EditText textTitle = (EditText) findViewById(R.id.ScheduleTitle);
                TextView textTime = (TextView) findViewById(R.id.time_text);
                Switch repeatSwitch = (Switch) findViewById(R.id.repeat_switch);
                TextView textDate = (TextView) findViewById(R.id.date_text);
                TextView textSelectedDate = (TextView) findViewById(R.id.SelectedDate);

                CheckBox checkedSun = (CheckBox) findViewById(R.id.check_sun);
                CheckBox checkedMon = (CheckBox) findViewById(R.id.check_mon);
                CheckBox checkedTue = (CheckBox) findViewById(R.id.check_tue);
                CheckBox checkedWed = (CheckBox) findViewById(R.id.check_wed);
                CheckBox checkedThu = (CheckBox) findViewById(R.id.check_Thu);
                CheckBox checkedFri = (CheckBox) findViewById(R.id.check_Fri);
                CheckBox checkedSat = (CheckBox) findViewById(R.id.check_Sat);

                String[] editTime = textTime.getText().toString().split(" : ");
                String[] editDate = textDate.getText().toString().split(" / ");
                String editTitle = textTitle.getText().toString();

                if (checkedTag0.isChecked()) {        //태그 하나만 선택
                    checkTag = checkedTag0.getText().toString() + ", ";
                } else if (checkedTag1.isChecked()) {
                    checkTag = checkTag + checkedTag1.getText().toString() + ", ";
                } else if (checkedTag2.isChecked()) {
                    checkTag = checkTag + checkedTag2.getText().toString() + ", ";
                } else if (checkedTag3.isChecked()) {
                    checkTag = checkTag + checkedTag3.getText().toString() + ", ";
                }
                if (!checkTag.equals("")) {
                    checkTag = checkTag.substring(0, checkTag.length() - 2);
                    Log.i("data", "선택 태그 : " + checkTag);
                    routineDBEntity.routineTag = checkTag;      //선택 태그 넣어주기
                    input_check_flag = 1;           //태그 확인, 없어도 됨
                }


                if (!editTitle.equals("")) {
                    Log.i("data", "루틴 이름 : " + editTitle);
                    routineDBEntity.routineTitle = editTitle;       //이름 넣어주기
                    input_check_flag = 1;
                } else {
                    Toast.makeText(getApplicationContext(), "루틴 이름을 입력해주세요.", Toast.LENGTH_LONG).show();
                    input_check_flag = 0; //여기 걸리면 뒤에를 무시해야함
                }

                if (!editTime[0].equals("시작 시간 입력")) {
                    Log.i("data", "루틴 시간 : " + editTime[0] + "시 " + editTime[1] + "분");
                    routineDBEntity.routineTime = editTime[0] + ":" + editTime[1];      //루틴 수행 시간 넣어주기
                    input_check_flag = 1;
                } else {
                    Toast.makeText(getApplicationContext(), "루틴 시간을 지정해주세요.", Toast.LENGTH_LONG).show();
                    input_check_flag = 0;
                }

                if (repeatSwitch.isChecked()) {
                    if (checkedSun.isChecked()) {
                        checkDay = checkDay + "일, ";
                    }
                    if (checkedMon.isChecked()) {
                        checkDay = checkDay + "월, ";
                    }
                    if (checkedTue.isChecked()) {
                        checkDay = checkDay + "화, ";
                    }
                    if (checkedWed.isChecked()) {
                        checkDay = checkDay + "수, ";
                    }
                    if (checkedThu.isChecked()) {
                        checkDay = checkDay + "목, ";
                    }
                    if (checkedFri.isChecked()) {
                        checkDay = checkDay + "금, ";
                    }
                    if (checkedSat.isChecked()) {
                        checkDay = checkDay + "토, ";
                    }
                    if (!checkDay.equals("")) {
                        checkDay = checkDay.substring(0, checkDay.length() - 2);
                        Log.i("data", "반복 요일 : " + checkDay);
                        routineDBEntity.routineRepeatDayOfWeek = checkDay;      //반복 요일 넣어주기
                        input_check_flag = 1;
                    } else {
                        Toast.makeText(getApplicationContext(), "반복 요일을 설정해주세요.", Toast.LENGTH_LONG).show();
                        input_check_flag = 0;
                    }

                    if (!editDate[0].equals("종료 날짜 입력")) {      //종료날짜를 설정하지 않을경우 1년뒤까지 자동 일정 생성해줄까???
                        Log.i("data", "루틴 종료 날짜 : " + editDate[0] + "년 " + editDate[1] + "월 " + editDate[2] + "일 ");
                        routineDBEntity.routineRepeatEndDate = editDate[0] + "/" + editDate[1] + "/" + editDate[2];     //반복 종료 날짜 넣어주기
                        input_check_flag = 1;
                    } else {
                        Toast.makeText(getApplicationContext(), "반복 종료 날짜를 입력해주세요.", Toast.LENGTH_LONG).show();
                        input_check_flag = 0;
                    }
                } else {   //버튼을 키지 않았을경우
                    routineDBEntity.routineRepeatDayOfWeek = null;
                    routineDBEntity.routineRepeatEndDate = null;
                    //input_check_flag = 1;
                }

                routineDBEntity.routinePerformState = 0;

                if (input_check_flag == 1) {        //정보가 제대로 입력되었을 경우
                    viewModel.insert(routineDBEntity);  //이거 빼고는다 routineDate가 endDate랑 동일하게 됨 -> 근데 log에서 routineDate를 출력하면 제대로 됨....

                    if (repeat_switch.isChecked()) {   //반복 체크했을경우
                        try {
                            Date forDate = date_db_form.parse(startDate);     //확인하고자 하는 날짜를 date형태로 변경
                            Calendar cal = Calendar.getInstance();
                            Calendar endCal = Calendar.getInstance();
                            SimpleDateFormat dow_format = new SimpleDateFormat("E", Locale.KOREA);
                            cal.setTime(forDate);
                            do {        //지금 날짜랑 끝나는 날짜랑 같으면 진행하고 종료
                                if (date_db_form.format(cal.getTime()).equals(routineDBEntity.routineRepeatEndDate)) //반복해놓고 현재 날짜를 선택했으면 break
                                    break;
                                //endCal.setTime(forDate);
                                //endCal.add(Calendar.YEAR, 1);   //1년 뒤
                                //if(date_db_form.format(endCal.getTime()).equals(cal.getTime()))  //1년 뒤까지 넣고 종료
                                //    break;
                                cal.add(Calendar.DATE, 1);   //1일 더하기~~!!~~
                                //현재 요일이랑 반복 요일이랑 일치하는지 확인해서 일치한다면 값 db에 바로 삽입해줘야함
                                if (checkDay.contains(dow_format.format(cal.getTime()))) { //지금 요일이 반복 요일에 있다면
                                    routineDBEntity.routineDate = date_db_form.format(cal.getTime());   //형식 지정
                                    Log.i("data", "반복선택된 날짜 : " + routineDBEntity.routineDate);
                                    viewModel.insert(routineDBEntity);
                                    Thread.sleep(50);   //insert후 진행
                                }
                            } while (!date_db_form.format(cal.getTime()).equals(routineDBEntity.routineRepeatEndDate));
                        } catch (Exception e) {
                        }
                    }
                    Intent intent = new Intent(getApplicationContext(), RoutineListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }


    //날짜선택
    public void onDateSelect(View view) {
        TextView dateText = findViewById(R.id.date_text);
        TextView curDateText = findViewById(R.id.SelectedDate);
        Calendar calendar = Calendar.getInstance();
        int pYear = calendar.get(Calendar.YEAR);
        int pMonth = calendar.get(Calendar.MONTH);
        int pDay = calendar.get(Calendar.DAY_OF_MONTH);
        //Log.i("dd", routineDBEntity.routineDate);


        datePickerDialog = new DatePickerDialog(AddRoutineActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                month = month + 1;
                String selectDate = year + " / ";
                if (month < 10) {       //날짜 형식 통일
                    selectDate = selectDate + "0" + month + " / ";
                } else {
                    selectDate = selectDate + month + " / ";
                }
                if (date < 10) {
                    selectDate = selectDate + "0" + date;
                } else {
                    selectDate = selectDate + date;
                }
                dateText.setText(selectDate);
            }
        }, pYear, pMonth, pDay);

        DateFormat sdFormat = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date selectedDate = sdFormat.parse(routineDBEntity.routineDate);
            datePickerDialog.getDatePicker().setMinDate(selectedDate.getTime());

        } catch (ParseException e) {
        }


        datePickerDialog.show();
        dateText.setTextColor(Color.BLACK);

    }

    //시간 선택
    public void onTimeSelect(View view) {
        TextView timeText = findViewById(R.id.time_text);
        Calendar calendar = Calendar.getInstance();
        int pHour = calendar.get(Calendar.HOUR_OF_DAY);
        int pMin = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(AddRoutineActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String selectTime = "";
                if (hour < 10) {
                    selectTime = selectTime + "0" + hour + " : ";
                } else {
                    selectTime = selectTime + hour + " : ";
                }
                if (minute < 10) {
                    selectTime = selectTime + "0" + minute;
                } else {
                    selectTime = selectTime + minute;
                }
                timeText.setText(selectTime);
            }
        }, pHour, pMin, true);
        timePickerDialog.show();
        timeText.setTextColor(Color.BLACK);

    }
}