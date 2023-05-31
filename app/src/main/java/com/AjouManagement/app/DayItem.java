package com.AjouManagement.app;

public class DayItem {
    private String dayText;
    private int imageResId;
    private boolean selected;

    public DayItem(String dayText, int imageResId) {
        this.dayText = dayText;
        this.imageResId = imageResId;
    }

    public String getDayText() {
        return dayText;
    }

    public int getImageResId() {
        return imageResId;
    }
}
