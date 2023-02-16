package com.example.habitmanager.adapter;

import android.util.Log;
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
    private ArrayList<HabitTask> listToShow = new ArrayList<>();
    private CalendarObject selectedCalendar;

    public TasksAdapter() {
        this.list = HabitTaskRepository.getInstance().getList();
        this.selectedCalendar = new CalendarObject(Calendar.getInstance());
        fillList();
    }
    public void setSelectedCalendar(CalendarObject calendar){
        selectedCalendar = calendar;
        listToShow.clear();
        fillList();
        notifyDataSetChanged();
    }

    private void fillList(){
        for (HabitTask habitTask : list){
            if(habitTask.getCalendar().equals(selectedCalendar)){
                listToShow.add(habitTask);
            }
        }
        Log.d("Adapter", listToShow.size() + ", " + list.size());
    }

    @NonNull
    @Override
    public TasksAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTaskBinding binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull TasksAdapter.ViewHolder holder, int position) {
        holder.binding.avatarImageView2.setImageResource(listToShow.get(position).getHabit().getCategory().getPicture());
        holder.binding.textView.setText(listToShow.get(position).getHabit().getName());
    }

    @Override
    public int getItemCount() {
        return listToShow.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ItemTaskBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemTaskBinding.bind(itemView);
        }

    }
}
