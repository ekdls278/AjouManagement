package com.AjouManagement.app;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "routine")
public class RoutineDBEntity {

    @PrimaryKey(autoGenerate = true)
    public int routineId;

    @ColumnInfo(name = "routine_title")
    @NonNull
    public String routineTitle;     //루틴 이름은 Null 불가

    @ColumnInfo(name = "routine_tag")
    public String routineTag;

    @ColumnInfo(name = "routine_date")
    public String routineDate;

    @ColumnInfo(name = "routine_Time")
    public String routineTime;

    @ColumnInfo(name = "routine_repeat_end_date")
    public String routineRepeatEndDate;

    @ColumnInfo(name = "routine_repeat_day_of_week")
    public String routineRepeatDayOfWeek;

    public String getSelectedDate(){
        return this.routineDate;
    }
    public String getTag(){
        return this.routineTag;
    }
    public String getTitle(){
        return this.routineTitle;
    }
    public String getSelectedTime(){
        return this.routineTime;
    }
    public String getRepeatDoW(){
        return this.routineRepeatDayOfWeek;
    }
    public String getRepeatEndDate(){
        return this.routineRepeatEndDate;
    }
}
