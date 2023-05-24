package com.AjouManagement.app.RoutineListActivity;

public class RoutineData {
    private String selectedDate;
    private String Tag;
    private String Title;
    private String selectedTime;
    private String repeatDoW;
    private String repeatEndDate;

    public RoutineData(String selectedDate, String Tag, String Title, String selectedTime, String repeatDoW, String repeatEndDate){
        this.selectedDate = selectedDate;
        this.Tag = Tag;
        this.Title = Title;
        this.selectedTime = selectedTime;
        this.repeatDoW = repeatDoW;
        this.repeatEndDate = repeatEndDate;
    }

    public String getSelectedDate(){
        return this.selectedDate;
    }
    public String getTag(){
        return this.Tag;
    }
    public String getTitle(){
        return this.Title;
    }
    public String getselectedTime(){
        return this.selectedTime;
    }
    public String getrepeatDoW(){
        return this.repeatDoW;
    }
    public String getrepeatEndDate(){
        return this.repeatEndDate;
    }
}
