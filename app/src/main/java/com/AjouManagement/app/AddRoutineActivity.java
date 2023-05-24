package com.AjouManagement.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ToggleButton;

import com.AjouManagement.app.RoutineListActivity.RoutineList;
import com.AjouManagement.app.databinding.ActivityAddRoutineBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddRoutineActivity extends AppCompatActivity {
    private ActivityAddRoutineBinding binding;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    private final int DYNAMIC_ID = 0x8000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRoutineBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.listButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RoutineList.class);
                startActivity(intent);
            }
        });

        //오늘 날짜 띄우기
        SimpleDateFormat sdf = new SimpleDateFormat("MMM.d(E)");
        String today =sdf.format(System.currentTimeMillis());
        TextView selectedDateText = (TextView)findViewById(R.id.SelectedDate);
        selectedDateText.setText(today);

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
                    public void onDateSet(DatePicker datePicker, int year, int month, int date){   //날짜 정보 받기
                        SimpleDateFormat temp_form = new SimpleDateFormat("yyyy/MM/dd");
                        month = month + 1;
                        String selectDate = year+"/" + month+"/"+date;
                        Date todayDate;
                        try{
                            todayDate = temp_form.parse(selectDate);
                            selectDate = sdf.format(todayDate);
                        }
                        catch (Exception e){}
                        selectedDateText.setText(selectDate);

                    }
                }, pYear, pMonth,pDay);
                datePickerDialog.show();
            }
        });

        //repeat 여부 선택
        Switch repeat_switch = findViewById(R.id.repeat_switch);
        ConstraintLayout repeat = findViewById(R.id.repeat_container);
        repeat_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    repeat.setVisibility(View.VISIBLE);
                }
                else{
                    repeat.setVisibility(View.GONE);
                }
            }
        });

        //태그 컨테이너
        final TableLayout tableLayout = (TableLayout) findViewById(R.id.tag_container);

        for (int i = 0; i < TagData.Tags.length/4; i++) { //row
            final TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
            tableRow.setPadding(0, 5, 0,20); //@px -> dp로 변경하기

            for(int j = 0 ; j < 4 ; j++){
                final TextView text = new TextView(this);
                final CheckBox checkTag = new CheckBox(this);
                int idx = 4*i+j;
                checkTag.setId(DYNAMIC_ID+idx);
                checkTag.setText(TagData.Tags[idx]);
                checkTag.setBackgroundColor(Color.parseColor(TagData.TagColors[idx]));
                checkTag.setButtonDrawable(null);
                checkTag.setGravity(Gravity.CENTER);
                //checkTag.setGravity(Gravity.CENTER);
                checkTag.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                checkTag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                        int tag_index=0;
                        if(isChecked){
                            checkTag.setBackgroundColor(Color.parseColor("#ffffff"));   //색 다시 설정하기
                        }else{
                            for(int i=0;i<TagData.Tags.length;i++){
                                if(checkTag.getText()== TagData.Tags[i]){
                                    tag_index=i;
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


    }


    //날짜선택
    public void onDateSelect(View view) {
        TextView dateText = findViewById(R.id.date_text);
        Calendar calendar = Calendar.getInstance();
        int pYear = calendar.get(Calendar.YEAR);
        int pMonth = calendar.get(Calendar.MONTH);
        int pDay = calendar.get(Calendar.DAY_OF_MONTH);
        datePickerDialog = new DatePickerDialog(AddRoutineActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                month = month + 1;
                String selectDate = year+" / ";
                if(month<10){       //날짜 형식 통일
                    selectDate = selectDate+ "0" + month+" / ";
                }else{
                    selectDate = selectDate+ month+" / ";
                }
                if(date<10){
                    selectDate = selectDate+ "0" + date;
                }else{
                    selectDate = selectDate+ date;
                }
                dateText.setText(selectDate);
            }
        }, pYear, pMonth,pDay);
        datePickerDialog.show();
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
                String selectTime="";
                if(hour<10){
                    selectTime=selectTime+"0"+hour+" : ";
                }else{
                    selectTime=selectTime+hour+" : ";
                }
                if(minute<10){
                    selectTime=selectTime+"0"+minute;
                }else{
                    selectTime=selectTime+minute;
                }
                timeText.setText(selectTime);
            }
        }, pHour,pMin,true);
        timePickerDialog.show();
    }

    //버튼 누르면 입력 값 저장하기
    public void addRoutineClick(View v){
        String checkDay = "";
        String checkTag = "";

        //동적 태그를 어떻게 받아와야하지?
        CheckBox checkedTag0 =(CheckBox)findViewById(DYNAMIC_ID+0);
        CheckBox checkedTag1 =(CheckBox)findViewById(DYNAMIC_ID+1);
        CheckBox checkedTag2 =(CheckBox)findViewById(DYNAMIC_ID+2);
        CheckBox checkedTag3 =(CheckBox)findViewById(DYNAMIC_ID+3);

        EditText textTitle = (EditText)findViewById(R.id.ScheduleTitle);
        TextView textTime = (TextView)findViewById(R.id.time_text);
        Switch repeatSwitch = (Switch)findViewById(R.id.repeat_switch);
        TextView textDate = (TextView)findViewById(R.id.date_text);

        CheckBox checkedSun =(CheckBox)findViewById(R.id.check_sun);
        CheckBox checkedMon =(CheckBox)findViewById(R.id.check_mon);
        CheckBox checkedTue =(CheckBox)findViewById(R.id.check_tue);
        CheckBox checkedWed =(CheckBox)findViewById(R.id.check_wed);
        CheckBox checkedThu =(CheckBox)findViewById(R.id.check_Thu);
        CheckBox checkedFri =(CheckBox)findViewById(R.id.check_Fri);
        CheckBox checkedSat =(CheckBox)findViewById(R.id.check_Sat);

        String[] editTime = textTime.getText().toString().split(" : ");
        String[] editDate = textDate.getText().toString().split(" / ");
        String editTitle = textTitle.getText().toString();

        if(checkedTag0.isChecked()){
            checkTag=checkedTag0.getText().toString()+", ";
        }
        if(checkedTag1.isChecked()){
            checkTag=checkTag+checkedTag1.getText().toString()+", ";
        }
        if(checkedTag2.isChecked()){
            checkTag=checkTag+checkedTag2.getText().toString()+", ";
        }
        if(checkedTag3.isChecked()){
            checkTag=checkTag+checkedTag3.getText().toString()+", ";
        }
        if(!checkTag.equals("")){
            checkTag= checkTag.substring(0,checkTag.length()-2);
            Log.i("data","선택 태그 : "+checkTag);
        }

        if(!editTitle.equals("")){
            Log.i("data","루틴 이름 : "+editTitle);
        }

        if(!editTime[0].equals("시작 시간 입력")){
            Log.i("data","루틴 시간 : "+editTime[0]+"시 "+editTime[1]+"분");
        }

        if(repeatSwitch.isChecked()){
            if(checkedSun.isChecked()){
                checkDay="일, ";
            }
            if(checkedMon.isChecked()){
                checkDay=checkDay+"월, ";
            }
            if(checkedTue.isChecked()){
                checkDay=checkDay+"화, ";
            }
            if(checkedWed.isChecked()){
                checkDay=checkDay+"수, ";
            }
            if(checkedThu.isChecked()){
                checkDay=checkDay+"목, ";
            }
            if(checkedFri.isChecked()){
                checkDay=checkDay+"금, ";
            }
            if(checkedSat.isChecked()){
                checkDay=checkDay+"토, ";
            }
            if(!checkDay.equals("")){
                checkDay= checkDay.substring(0,checkDay.length()-2);
                Log.i("data","반복 요일 : "+checkDay);
            }

            if(!editDate[0].equals("종료 날짜 입력")){
                Log.i("data", "루틴 종료 날짜 : " +editDate[0]+"년 "+editDate[1]+"월 "+editDate[2]+"일 ");
            }
        }

    }

}

/*
* 남은것
* 1. 최상단에 오늘날짜 가져오기 (5)
* 2. tag 만들기 (2) o
* 3. 시간 입력받기 (1) o
* 4. 추가버튼 누르면 데이터 저장되기 (3)
* 5. 저장한 데이터들 다른 뷰에 띄우기 (4)
*
* 현문제: 테이블뷰 col 끼리의 마진 설정 -> 테이블레이아웃을 리니어레이아웃으로 변경하기
* 일정 리스트 액티비티 만들기
* time,date 날짜 색깔 변경
* 추가 버튼 누르면 데이터 저장하기  ->데이터 연결
* 다른 뷰에서 저장한 루틴 불러오기  -> 데이터 연결
*
* 최상단에 오늘 날짜 가져오기 -> 오늘 날짜로 할지? 아니면 누르면 날짜 선택할 수 있게 바꿀지?
* */
