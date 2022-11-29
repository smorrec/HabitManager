package com.example.habitmanager.ui.habit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.habitmanager.R;
import com.example.habitmanager.adapter.HabitAdapter;
import com.example.habitmanager.data.model.Habit;
import com.example.habitmanager.databinding.FragmentHabitListBinding;
import com.example.habitmanager.viewmodel.StateData;

import java.util.ArrayList;


public class HabitListFragment extends Fragment implements HabitAdapter.OnItemClickListener {
    private FragmentHabitListBinding binding;
    private HabitAdapter adapter;
    private int selectedHabit;
    private HabitViewModel habitViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHabitListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.fab.setOnClickListener(view1 -> {
            NavHostFragment.findNavController(this).navigate(R.id.action_habitListFragment_to_addHabitFragment);
        });
        binding.bottomAppBar.setOnMenuItemClickListener(menuItem ->{
            switch (menuItem.getItemId()){
                case R.id.btnEditHabit:
                    editHabit();
                    return true;
                case R.id.btnDeleteHabit:
                    deleteHabit();
                    return true;
                case R.id.btnViewHabit:
                    viewHabit();
                    return true;
                case R.id.action_AboutUs:
                    showAboutUs();
                    return true;
            }
            return false;
        });
        initRvHabit();
        initViewModel();
    }

    private void initRvHabit(){
        adapter = new HabitAdapter(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);

        binding.rvHabit.setLayoutManager(linearLayoutManager);

        binding.rvHabit.setAdapter(adapter);
    }

    private void initViewModel(){
        habitViewModel = new ViewModelProvider(this).get(HabitViewModel.class);
        habitViewModel.getListLiveStateData().observe(getViewLifecycleOwner(), arrayListStateData -> {
            switch (arrayListStateData.getDataState()){
                case LOADING:
                    //Show progressBar
                    break;
                case NODATA:
                    //Mostrar algo
                    arrayListStateData.completed();
                    break;
                case SUCCESS:
                    adapter.update(arrayListStateData.getData());
                    arrayListStateData.completed();
                    break;
                case COMPLETED:
                    //Hide progressBar
                    break;
            }
        });
        habitViewModel.getDataList();
    }

    @Override
    public boolean onLongClick(View view) {
        return false;
    }

    private void editHabit(){
        NavHostFragment.findNavController(this).navigate(R.id.action_habitListFragment_to_editHabitFragment, setBundle());

    }

    private void deleteHabit(){
        try {
            adapter.deleteHabit(selectedHabit);
            adapter.selectedPosition = -1;
            binding.bottomAppBar.replaceMenu(R.menu.menu_main);
            Toast.makeText(getContext(), R.string.deletedHabit, Toast.LENGTH_SHORT).show();
        }catch (Exception e){
        }
    }

    private void viewHabit(){
        NavHostFragment.findNavController(this).navigate(R.id.action_habitListFragment_to_habitViewFragment, setBundle());
    }

    private void showAboutUs(){
        NavHostFragment.findNavController(this).navigate(R.id.action_habitListFragment_to_aboutUsFragment);
    }

    private Bundle setBundle(){
        Bundle bundle = new Bundle();
        Habit habit = adapter.getItem(selectedHabit);
        bundle.putParcelable(Habit.KEY, habit);
        return bundle;
    }

    @Override
    public void onClick(View view, int position) {

        if(view.isSelected()){
            binding.bottomAppBar.replaceMenu(R.menu.menu_list);
        }else{
            binding.bottomAppBar.replaceMenu(R.menu.menu_main);
        }
        selectedHabit = position;
        binding.bottomAppBar.performShow();
    }
}