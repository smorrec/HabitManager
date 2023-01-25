package com.example.habitmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitmanager.data.calendar.model.CalendarObject;
import com.example.habitmanager.data.calendar.repository.CalendarRepository;
import com.example.habitmanager.data.habit.repository.HabitRepository;
import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.databinding.CalendarItemBinding;
import com.example.habitmanager.databinding.ItemHabitBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder>{
    public int selectedPosition = -1;
    private CalendarAdapter.OnItemClickListener listener;
    private ArrayList<CalendarObject> list;

    public CalendarAdapter(CalendarAdapter.OnItemClickListener listener) {
        this.list = new ArrayList<>();
        this.listener = listener;
    }
    public void updateData(ArrayList<CalendarObject> data){
        list.clear();
        list.addAll(data);
        selectedPosition = -1;
        notifyDataSetChanged();
    }

    public CalendarObject getItem(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public CalendarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CalendarItemBinding binding = CalendarItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        CalendarAdapter.ViewHolder viewHolder = new CalendarAdapter.ViewHolder(binding.getRoot());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.ViewHolder holder, int position) {
        holder.binding.weekDayName.setText(list.get(position).getWeekDay());
        holder.binding.weekDatValue.setText(String.valueOf(list.get(position).getDay()));
        holder.binding.getRoot().setSelected(position == selectedPosition);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public boolean selectDay(Calendar calendar) {
        boolean success = false;
        CalendarObject calendarObject = new CalendarObject(calendar);
        if(list.contains(calendarObject)){
            selectedPosition = list.indexOf(calendarObject);
            success = true;
            notifyDataSetChanged();
        }
        return success;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CalendarItemBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            binding = CalendarItemBinding.bind(itemView);
        }

        @Override
        public void onClick(View view) {
            if(!view.isSelected()) {
                view.setSelected(true);
            }
            selectedPosition = getLayoutPosition();
            listener.onClick(view, getLayoutPosition());
            notifyDataSetChanged();

        }
    }

    public interface OnItemClickListener{
        void onClick(View view, int position);
    }
}
