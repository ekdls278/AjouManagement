package com.AjouManagement.app.RoutineListActivity;

import android.content.Context;
import android.graphics.Color;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.AjouManagement.app.CalendarAdapter;
import com.AjouManagement.app.R;
import com.AjouManagement.app.RoutineDB;
import com.AjouManagement.app.RoutineDBEntity;
import com.AjouManagement.app.TagData;

import java.util.ArrayList;
import java.util.List;

//데이터와 아이템에 대한 iew 생성
//recyclerview에서 아이템을 보이게 만들어주는 역할을 하는 viewHolder 생성
//collection와 viewholder 객체 관리
public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.ViewHolder> {
    private List<RoutineDBEntity> routineList = null;

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        TextView state;
        TextView date;
        TextView time;
        TextView repeatDow;
        TextView repeatEnd;
        LinearLayout tagColor;
        LinearLayout repeatContainer;
        Button modifyButton;
        Button deleteButton;

        ViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.title_list);
            state = view.findViewById(R.id.state_text);
            date = view.findViewById(R.id.selectedDate_list);
            time = view.findViewById(R.id.selectedTime_list);
            repeatDow = view.findViewById(R.id.repeatDOW_list);
            repeatEnd = view.findViewById(R.id.repeatEndDate_list);
            tagColor = view.findViewById(R.id.tag_color);   //태그 색깔 나타내기
            repeatContainer = view.findViewById(R.id.repeat_container);

        }
    }
    private AdapterView.OnItemClickListener mListener =null;
    public void setOnItemClickListener(AdapterView.OnItemClickListener listener){
        this.mListener = listener;
    }
    public interface OnItemClickListener{
        void onItemClick(View v, int position);
    }

    public RoutineAdapter(List<RoutineDBEntity> list){
        routineList = list;
    }
    @Override
    public RoutineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.routine, parent, false);
        RoutineAdapter.ViewHolder vh = new RoutineAdapter.ViewHolder(view);

        return vh;
    }
    @Override
    public void onBindViewHolder(RoutineAdapter.ViewHolder holder, int position) {
        String tag_comp;

        if(routineList.get(position).routineTitle != null)
            holder.title.setText(routineList.get(position).routineTitle);
        if(routineList.get(position).routineTag != null)
            if(routineList.get(position).routinePerformState == 1)
                holder.state.setText("수행 완료");
            else
                holder.state.setText("수행 전");
        if(routineList.get(position).routineDate != null)
            holder.date.setText(routineList.get(position).routineDate);
        if(routineList.get(position).routineTime != null)
            holder.time.setText(routineList.get(position).routineTime);
        if(routineList.get(position).routineRepeatDayOfWeek != null)
            holder.repeatDow.setText("매주 " + routineList.get(position).routineRepeatDayOfWeek);
        else{ //반복 없을때 뷰 처리
           holder.repeatContainer.setVisibility(View.INVISIBLE);

        }
        if(routineList.get(position).routineRepeatEndDate != null)
            holder.repeatEnd.setText(routineList.get(position).routineRepeatEndDate);
        if(routineList.get(position).routineTag != null){
            for(int i = 0; i< TagData.Tags.length; i++) {
                tag_comp = routineList.get(position).routineTag;    //루틴 태그 값 db에서 갖고오기
                if (tag_comp.equals(TagData.Tags[i])) {       //일치하는 태그값 찾아서 색칠해주기
                    holder.tagColor.setBackgroundColor(Color.parseColor(TagData.TagColors[i]));
                    break;
                }
            }
        }
        else{ //태그 선택안했을경우
            holder.tagColor.setBackgroundColor(Color.parseColor("#d9d9d9"));
        }

    }

    @Override
    public int getItemCount() {
        return (null != routineList ? routineList.size() :0);
    }

    public void setRoutineList(List<RoutineDBEntity> data){
        routineList = data;
        notifyDataSetChanged();
    }

}
/*
public class RoutineAdapter extends ListAdapter<RoutineDBEntity, RoutineViewHolder> {
    public RoutineAdapter(@NonNull DiffUtil.ItemCallback<RoutineDBEntity> diffCallback) {
        super(diffCallback);
    }
    @Override
    public RoutineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RoutineViewHolder.create(parent);
    }
    @Override
    public void onBindViewHolder(RoutineViewHolder holder, int position) {
        RoutineDBEntity current = getItem(position);
        holder.bind(current.getTitle());
    }
    static class WordDiff extends DiffUtil.ItemCallback<RoutineDBEntity> {
        @Override
        public boolean areItemsTheSame(@NonNull RoutineDBEntity oldItem, @NonNull RoutineDBEntity newItem) {
            return oldItem == newItem;
        }
        @Override
        public boolean areContentsTheSame(@NonNull RoutineDBEntity oldItem, @NonNull RoutineDBEntity newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    }
}
*/

/*
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
* */