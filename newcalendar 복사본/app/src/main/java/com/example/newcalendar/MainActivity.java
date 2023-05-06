package com.example.newcalendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import static java.sql.DriverManager.println;

public class MainActivity extends AppCompatActivity implements CalendarInf.OnItemListener {

    EditText edtDiary;

    Button btnWrite;
    String fileName;





    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    private LocalDate selectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();
        edtDiary = (EditText) findViewById(R.id.edtdiary);
        btnWrite = (Button) findViewById(R.id.savebutton);

        btnWrite.setOnClickListener(v -> save());
    }

    private void initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    private void setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        CalendarInf calendarInf = new CalendarInf(daysInMonth, this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarInf);
    }

    private ArrayList<String> daysInMonthArray(LocalDate date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    private String monthYearFromDate(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    public void previousMonthAction(View view) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    public void nextMonthAction(View view) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }





    public void onDateChanged(LocalDate selectedDate,String date ) {
        fileName = monthYearFromDate(selectedDate)+
                date+".txt";
        String str = readDiary(fileName);
        edtDiary.setText(str);
        btnWrite.setEnabled(true);
    }

    String readDiary(String fName){
        String diaryStr = null;
        FileInputStream infs;
        try{
            infs = openFileInput(fName);
            byte[] txt = new byte[500];
            infs.read(txt);
            infs.close();
            diaryStr = (new String(txt)).trim();

        }catch(IOException e){

            println("exception!");
        }
        return diaryStr;
    }


    public void save() {

        try {
            FileOutputStream outfs = openFileOutput(fileName, Context.MODE_PRIVATE);
            String str = edtDiary.getText().toString();
            outfs.write(str.getBytes());
            outfs.close();
            Toast.makeText(getApplicationContext(), fileName + "이 저장됨", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
        }
    }


    @Override
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.equals("")) {

            onDateChanged(selectedDate,dayText);

            String message = "Selected Date " + dayText+ " " +monthYearFromDate(selectedDate);
            Toast.makeText(this,message,Toast.LENGTH_LONG).show();
        }
    }
}