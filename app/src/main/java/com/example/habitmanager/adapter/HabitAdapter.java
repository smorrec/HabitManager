package com.example.habitmanager.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.transition.Fade;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.habitmanager.R;
import com.example.habitmanager.data.habit.comparator.HabitComparatorByCategory;
import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.data.habit.repository.HabitRepository;
import com.example.habitmanager.databinding.ItemHabitBinding;

import java.util.ArrayList;
import java.util.Collections;

public class HabitAdapter extends RecyclerView.Adapter<HabitAdapter.ViewHolder>{
    private ArrayList<Habit> list;
    private OnItemClickListener listener;
    public static int selectedPosition = -1;
    public static int deletedPosition = -1;
    private ArrayList<Integer> positionDescriptionShowed;

    public HabitAdapter(OnItemClickListener listener) {
        this.list = new ArrayList<>();
        this.listener = listener;
        this.positionDescriptionShowed = new ArrayList<>();
    }


    public void deleteHabit(int position){
        deletedPosition = position;
        list.remove(position);
        notifyDataSetChanged();
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
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HabitAdapter.ViewHolder holder, int position) {
        holder.binding.textView.setText(list.get(position).getName());
        holder.binding.avatarImageView2.setImageResource(list.get(position).getCategory().getPicture());
        holder.binding.descriptionCotent.setText(list.get(position).getDescription());
        holder.binding.getRoot().setSelected(position == selectedPosition);

        holder.binding.description.setVisibility(View.VISIBLE);
        holder.binding.descriptionnBtn.setImageResource(R.drawable.ic_arrow_up);
        holder.binding.descriptionnBtn.setOnClickListener(view -> {
            AlphaAnimation animation = new AlphaAnimation(1.0f, 0);
            animation.setDuration(500);
            animation.setFillAfter(true);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    holder.hideDescription();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            holder.binding.description.startAnimation(animation);
        });

        if(!positionDescriptionShowed.contains(position)){
            holder.binding.description.setVisibility(View.GONE);
            holder.binding.descriptionnBtn.setImageResource(R.drawable.ic_arrow_down);
            holder.binding.descriptionnBtn.setOnClickListener(view -> {
                holder.binding.description.setVisibility(View.VISIBLE);
                AlphaAnimation animation = new AlphaAnimation(0, 1.0f);
                animation.setDuration(500);
                animation.setFillAfter(true);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        holder.showDescription();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                    }
                });
                holder.binding.description.startAnimation(animation);
            });
        }
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
            positionDescriptionShowed.add(getLayoutPosition());
            notifyDataSetChanged();
        }

        private void hideDescription(){
            positionDescriptionShowed.remove((Integer) getLayoutPosition());
            notifyDataSetChanged();
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

    public void updateData(ArrayList<Habit> data){
        list.clear();
        list.addAll(data);
        selectedPosition = -1;
        notifyDataSetChanged();
    }

    public void undo(Habit habit) {
        list.add(deletedPosition, habit);
        notifyDataSetChanged();
    }
}
