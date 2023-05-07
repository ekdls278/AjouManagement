package com.AjouManagement.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import com.AjouManagement.app.databinding.ActivityAddRoutineBinding;

import java.util.Calendar;

public class AddRoutineActivity extends AppCompatActivity {
    private ActivityAddRoutineBinding binding;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddRoutineBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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
                checkTag.setText(TagData.Tags[(4*i)+j]);
                checkTag.setBackgroundColor(Color.parseColor(TagData.TagColors[(4*i)+j]));
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
                String selectDate = year + " / " + month + " / " + date;

                dateText.setText(selectDate);
            }
        }, pYear, pMonth,pDay);
        datePickerDialog.show();
    }

    public void onTimeSelect(View view) {
        TextView timeText = findViewById(R.id.time_text);
        Calendar calendar = Calendar.getInstance();
        int pHour = calendar.get(Calendar.HOUR_OF_DAY);
        int pMin = calendar.get(Calendar.MINUTE);
        timePickerDialog = new TimePickerDialog(AddRoutineActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                String selectTime = hour + " : "+ minute;

                timeText.setText(selectTime);
            }
        }, pHour,pMin,true);
        timePickerDialog.show();
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
* */
