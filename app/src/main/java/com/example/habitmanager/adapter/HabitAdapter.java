package com.example.habitmanager.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitmanager.R;
import com.example.habitmanager.data.habit.comparator.HabitComparatorByCategory;
import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.databinding.ItemHabitBinding;

import java.util.ArrayList;
import java.util.Collections;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder>{
    private ArrayList<Habit> list;
    private OnItemClickListener listener;
    public static int selectedPosition = -1;

    public HabitAdapter(OnItemClickListener listener) {
        this.list = new ArrayList<>();
        this.listener = listener;
    }


    public void deleteHabit(int position){
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    public Habit getItem(int position){
        return list.get(position);
    }

    public void orderByCategory(){
        Collections.sort(list, new HabitComparatorByCategory());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HabitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemHabitBinding binding = ItemHabitBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(binding.getRoot());
        Log.d("show", viewHolder.binding.textView.getText().toString());
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HabitAdapter.ViewHolder holder, int position) {
        holder.binding.textView.setText(list.get(position).getName());
        holder.binding.avatarImageView2.setImageResource(list.get(position).getCategory().getPicture());
        holder.binding.descriptionCotent.setText(list.get(position).getDescription());
        holder.binding.descriptionnBtn.setOnClickListener(view -> holder.showDescription());
        holder.binding.getRoot().setSelected(position == selectedPosition);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
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

        private void showDescription(){
            Log.d("show", binding.textView.getText().toString());
            binding.description.setVisibility(View.VISIBLE);
            binding.descriptionnBtn.setImageResource(R.drawable.ic_arrow_up);
            binding.descriptionnBtn.setOnClickListener(view -> hideDescription());
        }

        private void hideDescription(){
            Log.d("show", binding.textView.getText().toString());
            binding.description.setVisibility(View.GONE);
            binding.descriptionnBtn.setImageResource(R.drawable.ic_arrow_down);
            binding.descriptionnBtn.setOnClickListener(view -> showDescription());
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

    public void updateData(ArrayList<Habit> data){
        list.clear();
        list.addAll(data);
        selectedPosition = -1;
        notifyDataSetChanged();
    }
}
