package com.example.habitmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitmanager.data.calendar.model.CalendarObject;
import com.example.habitmanager.data.task.model.HabitTask;
import com.example.habitmanager.data.task.repository.HabitTaskRepository;
import com.example.habitmanager.databinding.ItemTaskBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder>{
    private ArrayList<HabitTask> list;
    private CalendarObject selectedCalendar;

    public TasksAdapter() {
        selectedCalendar = new CalendarObject(Calendar.getInstance());
        list = new ArrayList<>();
    }
    public void setSelectedCalendar(CalendarObject calendar){
        list.clear();
        list.addAll(calendar.getHabitTasks());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskBinding binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        TasksAdapter.ViewHolder viewHolder = new TasksAdapter.ViewHolder(binding.getRoot());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TasksAdapter.ViewHolder holder, int position) {
        holder.binding.avatarImageView2.setImageResource(list.get(position).getHabit().getCategory().getPicture());
        holder.binding.textView.setText(list.get(position).getHabit().getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private ItemTaskBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemTaskBinding.bind(itemView);
        }

    }
}
