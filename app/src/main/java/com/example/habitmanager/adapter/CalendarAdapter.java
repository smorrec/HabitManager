package com.example.habitmanager.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitmanager.data.habit.repository.HabitRepository;
import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.databinding.CalendarItemBinding;
import com.example.habitmanager.databinding.ItemHabitBinding;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder>{
    public int selectedPosition = -1;
    private CalendarAdapter.OnItemClickListener listener;
    private ArrayList<Habit> list;

    public CalendarAdapter(CalendarAdapter.OnItemClickListener listener) {
        this.list = HabitRepository.getInstance().getList();
        this.listener = listener;
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
        holder.binding.getRoot().setSelected(position == selectedPosition);
    }

    @Override
    public int getItemCount() {
        return list.size();
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
            if(view.isSelected()){
                view.setSelected(false);
                selectedPosition = -1;

            }else{
                view.setSelected(true);
                selectedPosition = getLayoutPosition();
            }
            listener.onClick(view, getLayoutPosition());
            notifyDataSetChanged();

        }
    }

    public interface OnItemClickListener{
        void onClick(View view, int position);
    }
}
