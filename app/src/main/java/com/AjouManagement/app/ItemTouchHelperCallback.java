package com.AjouManagement.app;

import android.graphics.Color;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Random;

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private ItemTouchHelperListener listener;
    FallingBall fallingBall;
    Boolean isSwipe;
    Random random;


    public ItemTouchHelperCallback(ItemTouchHelperListener listener, FallingBall fallingBall, boolean isSwipe) {
        this.listener = listener;
        this.fallingBall = fallingBall;
        this.isSwipe = isSwipe;
    }


    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView,
                                @NonNull RecyclerView.ViewHolder viewHolder) {
        int swipe_flags = 0;
        if(isSwipe){
            swipe_flags = ItemTouchHelper.START | ItemTouchHelper.END;
        }
        int drag_flags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        Log.d("getMovementFlags", "getMovement");
        return makeMovementFlags(drag_flags, swipe_flags);
    }

    //Item move method
    //리사이클러뷰, viewHolder, target(viewHolder중 선택된 아이템)을 입력받아 움직임을 감지한다.
    //=>ItemTouchHelperListener의 onItemMove메소드로 해당 아이템의 움직임을 감지한다.
    //onItemMove메소드는 아이템이 움직이고 있는가를 판별하고 boolean값으로 변환한다.
    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView,
                          @NonNull RecyclerView.ViewHolder viewHolder,
                          @NonNull RecyclerView.ViewHolder target) {
        Log.d("onMove", "Move");
        return listener.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
    }


    //리사이클러뷰의 뷰홀더와 움직일 방향을 입력받는다.
    //ItemTouchHelperListner의 onItemSwipe메소드에 움직일 방향을 입력하여 swipe구현
    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        Log.d("onSwiped", "Swiped");

        int position = viewHolder.getAdapterPosition();
        if(direction == ItemTouchHelper.START)
        {
            listener.remove(position);
            onRightSwipe(position);
        }
        else if(direction == ItemTouchHelper.END)
        {
            listener.remove(position);
            onLeftSwipe(position);
        }
    }

    //Right Swipe시 실행할 함수
    public void onRightSwipe(int position){
        random = new Random();
        int directionX = random.nextInt(21)-10;
        fallingBall.createBall(20,100,directionX,1, Color.parseColor("#A9DEF9"));
        Log.d("onSwiped", "Right");
    }
    //Left Swipe시 실행할 함수
    public void onLeftSwipe(int position){
        random = new Random();
        int directionX = random.nextInt(5);
        fallingBall.createBall(980,100,directionX,1, Color.parseColor("#FCF6BD"));
        Log.d("onSwiped", "Left");
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }
}

