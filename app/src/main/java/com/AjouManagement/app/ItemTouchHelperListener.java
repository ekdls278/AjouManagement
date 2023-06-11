package com.AjouManagement.app;

public interface ItemTouchHelperListener{
    boolean onItemMove(int form_position, int to_position);
    //void onItemSwipe(int position, int direction);
    RoutineDBEntity remove(int position);
}