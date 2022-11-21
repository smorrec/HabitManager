package com.example.habitmanager.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitmanager.data.HabitRepository;
import com.example.habitmanager.data.model.Habit;
import com.example.habitmanager.databinding.ItemHabitBinding;

import java.util.ArrayList;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder>{
    private ArrayList<Habit> list;
    private OnItemClickListener listener;
    public int selectedPosition = -1;

    public HabitAdapter(OnItemClickListener listener) {
        this.list = HabitRepository.getInstance().getList();
        this.listener = listener;
    }

    public ArrayList<Habit> getList(){
        return list;
    }

    public void deleteHabit(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    public Habit getItem(int position){
        return list.get(position);
    }

    @NonNull
    @Override
    public HabitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHabitBinding binding = ItemHabitBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(binding.getRoot());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HabitAdapter.ViewHolder holder, int position) {
        holder.binding.textView.setText(list.get(position).getName());
        holder.binding.getRoot().setSelected(position == selectedPosition);
    }

    @Override
    public int getItemCount() {
        return list.size();
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

    public interface OnItemClickListener extends View.OnLongClickListener {
        void onClick(View view, int position);
    }
}
