package com.example.habitmanager.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitmanager.data.HabitRepository;
import com.example.habitmanager.data.model.Habit;
import com.example.habitmanager.databinding.ItemHabitBinding;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder>{
    public int selectedPosition = -1;
    private CalendarAdapter.OnItemClickListener listener;
    private ArrayList<Habit> list;

    public CalendarAdapter(CalendarAdapter.OnItemClickListener listener) {
        this.list = HabitRepository.getInstance().getList();
        this.listener = listener;
    }

    @NonNull
    @Override
    public HabitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull HabitAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemHabitBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            binding = ItemHabitBinding.bind(itemView);
        }

        @Override
        public void onClick(View view) {
            int lastSelected = selectedPosition;
            if(view.isSelected()){
                view.setSelected(false);
                selectedPosition = -1;

            }else{
                view.setSelected(true);
                selectedPosition = getLayoutPosition();
            }
            listener.onClick(view, getLayoutPosition());
            notifyItemChanged(lastSelected);

        }
    }

    public interface OnItemClickListener{
        void onClick(View view, int position);
    }
}
