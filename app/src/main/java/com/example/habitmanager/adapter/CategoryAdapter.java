package com.example.habitmanager.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.habitmanager.R;
import com.example.habitmanager.data.category.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapter extends ArrayAdapter<Category> {
    private final ArrayList<Category> list;

    public CategoryAdapter(@NonNull Context context, int resource, List<Category> list) {
        super(context, resource, list);
        this.list = (ArrayList<Category>) list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if(view == null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, null);
            viewHolder.textView = view.findViewById(R.id.categoryTextView);
            viewHolder.imageView = view.findViewById(R.id.categoryImageView);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.imageView.setImageResource(list.get(position).getPicture());
        viewHolder.textView.setText(list.get(position).getName());
        return view;

    }

    public static class ViewHolder{
        private TextView textView;
        private ImageView imageView;
    }
}
