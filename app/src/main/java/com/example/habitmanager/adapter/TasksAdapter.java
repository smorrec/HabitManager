package com.example.habitmanager.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitmanager.data.calendar.model.CalendarObject;
import com.example.habitmanager.data.category.repository.CategoryRepository;
import com.example.habitmanager.data.habit.repository.HabitRepository;
import com.example.habitmanager.data.task.model.HabitTask;
import com.example.habitmanager.data.task.repository.HabitTaskRepository;
import com.example.habitmanager.databinding.ItemTaskBinding;
import com.google.android.material.checkbox.MaterialCheckBox;

import java.util.ArrayList;
import java.util.Calendar;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.ViewHolder>{
    private final ArrayList<HabitTask> list;
    private final ArrayList<HabitTask> listToShow = new ArrayList<>();
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
        holder.task = listToShow.get(position);
        holder.binding.avatarImageView2.setImageResource(
                CategoryRepository.getInstance().getPicture(HabitRepository.getInstance().selectByName(listToShow.get(position).getHabitName()).getCategoryId()));
        holder.binding.textView.setText(listToShow.get(position).getHabitName());
        holder.binding.descriptionnBtn.setChecked(listToShow.get(position).isCompleted());
        holder.binding.descriptionnBtn.setEnabled(listToShow.get(position).isCurrentDay());


    }

    @Override
    public int getItemCount() {
        return listToShow.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final ItemTaskBinding binding;
        private HabitTask task;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemTaskBinding.bind(itemView);
            binding.descriptionnBtn.addOnCheckedStateChangedListener((checkBox, state) -> {
                task.setCompleted(checkBox.isChecked());
                HabitTaskRepository.getInstance().update(task);
            });

        }

    }
}
