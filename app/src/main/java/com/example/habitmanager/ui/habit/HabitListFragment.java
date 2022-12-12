package com.example.habitmanager.ui.habit;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import com.example.habitmanager.ui.base.BaseFragmentDialog;

public class HabitListFragment extends Fragment implements HabitAdapter.OnItemClickListener {
    private FragmentHabitListBinding binding;
    private HabitAdapter adapter;
    private HabitListViewModel viewModel;
    public static final String TAG = "habitList";
    private int selectedHabit = -1;

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
        binding.fab.setOnClickListener(view1 ->  habitManagerFragment(null));

        binding.bottomAppBar.setOnMenuItemClickListener(menuItem ->{
            switch (menuItem.getItemId()){
                case R.id.btnEditHabit:
                    habitManagerFragment(setBundle());
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL, false);

        binding.rvHabit.setLayoutManager(linearLayoutManager);

        binding.rvHabit.setAdapter(adapter);
    }

    private void initViewModel(){
        viewModel = new ViewModelProvider(this).get(HabitListViewModel.class);
        viewModel.getStateLiveDataList().observe(getViewLifecycleOwner(), arrayListStateDataList -> {
            switch (arrayListStateDataList.getState()){
                case LOADING:
                    break;
                case NODATA:
                    arrayListStateDataList.setCompleted();
                    break;
                case SUCCESS:
                    adapter.updateData(arrayListStateDataList.getData());
                    arrayListStateDataList.setCompleted();
                    break;
                case COMPLETED:
                    break;
            }
        });

        viewModel.getDataList();
    }

    private void habitManagerFragment(Bundle bundle){
        NavHostFragment.findNavController(this)
                .navigate(R.id.action_habitListFragment_to_habitManagerFragment, bundle);

    }

    private void deleteHabit(){
        Bundle bundle = new Bundle();
        bundle.putString(BaseFragmentDialog.KEY_TITLE, getString(R.string.tittleDeleteHabit));
        bundle.putString(BaseFragmentDialog.KEY_MESSAGE, getString(R.string.messageDeleteDependency, adapter.getItem(selectedHabit).getName()));
        bundle.putString(BaseFragmentDialog.KEY_REQUEST, HabitListFragment.TAG);
        NavHostFragment.findNavController(this).navigate(R.id.action_habitListFragment_to_baseFragmentDialog, bundle);
        getActivity().getSupportFragmentManager().setFragmentResultListener(HabitListFragment.TAG, getViewLifecycleOwner(), (requestKey, result) -> {
            if(result.getBoolean(BaseFragmentDialog.KEY_BUNDLE)){
                viewModel.delete(selectedHabit);
                adapter.deleteHabit(selectedHabit);
                adapter.selectedPosition = -1;
                binding.bottomAppBar.replaceMenu(R.menu.menu_main);
            }
        });

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