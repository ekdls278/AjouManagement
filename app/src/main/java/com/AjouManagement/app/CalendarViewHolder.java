package com.AjouManagement.app;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CalendarViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public  TextView dayOfMonth;
    public  ImageView imageItem;
    private final CalendarAdapter.OnItemListener onItemListener;
    public CalendarViewHolder(@NonNull View itemView, CalendarAdapter.OnItemListener onItemListener) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        imageItem = itemView.findViewById(R.id.imageItem);
        this.onItemListener = onItemListener;
        itemView.setOnClickListener(this);

    }

    public void bindData(DayItem dayItem, List<RoutineDBEntity> routineList) {
        dayOfMonth.setText(dayItem.getDayText());
        if (dayItem.getImageResId() != 0) {
            imageItem.setImageResource(dayItem.getImageResId());
            imageItem.setVisibility(View.VISIBLE);
        } else {
            imageItem.setVisibility(View.GONE);
        }

        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageItem.getLayoutParams();
        layoutParams.addRule(RelativeLayout.BELOW, R.id.cellDayText);

        // 루틴 데이터 표시 로직 추가
        if (routineList != null) {
            for (RoutineDBEntity routine : routineList) {
                if (routine.routineDate.equals(dayItem.getDayText())) {
                    imageItem.setImageResource(dayItem.getImageResId());
                    imageItem.setVisibility(View.VISIBLE);
                    // 루틴 데이터와 관련된 로직을 여기에 추가
                    // 예를 들어 배경색 변경, 아이콘 변경 등을 수행할 수 있습니다.
                    // 추후 구현 예정입니다.
                    break;
                }
            }
        }
    }

    public void clearData() {
        dayOfMonth.setText("");
        itemView.setVisibility(View.GONE);
    }
    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        if (position != RecyclerView.NO_POSITION) {
            onItemListener.onItemClick(position, dayOfMonth.getText().toString());
        }
    }
}
