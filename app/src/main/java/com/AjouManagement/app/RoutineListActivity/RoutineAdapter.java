package com.AjouManagement.app.RoutineListActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.AjouManagement.app.R;

import java.util.ArrayList;

public class RoutineAdapter extends BaseAdapter {
    Context mContext = null;
    LayoutInflater mLayoutInflater = null;
    ArrayList<RoutineData> Routine;

    public RoutineAdapter(Context context, ArrayList<RoutineData> data){
        mContext = context;
        Routine = data;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return Routine.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public RoutineData getItem(int position) {
        return Routine.get(position);
    }

    @Override
    public View getView(int position, View converView, ViewGroup parent) {      //여기만 좀 수정하기

        View view = mLayoutInflater.inflate(R.layout.routine, null);

        TextView title = (TextView)view.findViewById(R.id.title_list);
        TextView selectedDate = (TextView)view.findViewById(R.id.selectedDate_list);
        TextView tag = (TextView)view.findViewById(R.id.tag_list);
        TextView selectedTime = (TextView)view.findViewById(R.id.selectedTime_list);
        TextView repeatDoW = (TextView)view.findViewById(R.id.repeatDOW_list);
        TextView repeatEndDate = (TextView)view.findViewById(R.id.repeatEndDate_list);

        title.setText(Routine.get(position).getTitle());
        selectedDate.setText("수행 날짜: "+Routine.get(position).getSelectedDate());
        tag.setText(Routine.get(position).getTag());
        selectedTime.setText("수행 시간: "+Routine.get(position).getselectedTime());
        repeatDoW.setText("반복 시간: "+Routine.get(position).getrepeatDoW());
        repeatEndDate.setText("종료 날짜: "+Routine.get(position).getrepeatEndDate());

        return view;
    }
}
