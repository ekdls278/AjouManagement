package com.AjouManagement.app;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

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

    public void bindData(DayItem dayItem) {
        dayOfMonth.setText(dayItem.getDayText());
        if (dayItem.getImageResId() != 0) {
            imageItem.setImageResource(dayItem.getImageResId());
            imageItem.setVisibility(View.VISIBLE);
        } else {
            imageItem.setVisibility(View.GONE);
        }
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageItem.getLayoutParams();
        layoutParams.addRule(RelativeLayout.BELOW, R.id.date_text);
        //아래에 배치
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
