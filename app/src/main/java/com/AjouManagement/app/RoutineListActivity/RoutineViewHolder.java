package com.AjouManagement.app.RoutineListActivity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.AjouManagement.app.R;

//화면에 표시될 아이템 뷰 저장
public class RoutineViewHolder extends RecyclerView.ViewHolder {
    private final TextView routineItemView;
    private RoutineViewHolder(View view) {
        super(view);
        routineItemView = view.findViewById(R.id.title_list);
        /*
        view.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                int pos = getAdapterPosition();
                if (pos!=RecyclerView.NO_POSITION){
                    Log.i("error_viewHolder","postion 없음");
                }

            }
        });*/
    }
    public void bind(String text) {
        routineItemView.setText(text);
    }



    static RoutineViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_routine_list, parent, false);
        return new RoutineViewHolder(view);
    }
}
