package com.example.habitmanager.ui.habit;

import static com.example.habitmanager.ui.HabitManagerApplication.CHANNEL_ID;

import android.Manifest;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.habitmanager.R;
import com.example.habitmanager.adapter.HabitAdapter;
import com.example.habitmanager.data.habit.model.Habit;
import com.example.habitmanager.databinding.FragmentHabitListBinding;
import com.example.habitmanager.databinding.ModalBottomSheetBinding;
import com.example.habitmanager.preferencies.ListPreferencies;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class HabitListFragment extends Fragment implements HabitAdapter.OnItemClickListener{
    private FragmentHabitListBinding binding;
    private static HabitAdapter adapter;
    private HabitListViewModel viewModel;
    public static final String TAG = "habitList";
    private static int selectedHabit = -1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHabitListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.fab.setOnClickListener(view1 ->  habitManagerFragment(null));

        initRvHabit();
        initViewModel();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_orderByCategory:
                adapter.orderByCategory();
                return true;
            case R.id.action_orderByName:
                adapter.orderByName();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.menu_order, menu);
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
                    sortList();
                    arrayListStateDataList.setCompleted();
                    break;
                case COMPLETED:
                    break;
            }
        });

        viewModel.getDeletedHabit().observe(getViewLifecycleOwner(), habit -> {
            if(viewModel.isUndoEnabled()) {
                Snackbar.make(getView(), getString(R.string.undoText) + habit.getName(), Snackbar.LENGTH_SHORT)
                        .setAction(R.string.undo, view -> {
                            viewModel.undo();
                            adapter.undo(habit);
                        })
                        .show();
                viewModel.setUndoEnabled(false);
            }
        });

        viewModel.getDataList();
    }

    private void sortList() {
        if((new ListPreferencies(getContext())).getOrder().equals("1") || (new ListPreferencies(getContext())).getOrder().isEmpty()){
            adapter.orderByName();
        }else {
            adapter.orderByCategory();
        }
    }

    private void habitManagerFragment(Bundle bundle){
        NavHostFragment.findNavController(this).navigate(R.id.action_habitListFragment_to_habitManagerFragment, bundle);
    }


    protected void deleteHabit(){
        new MaterialAlertDialogBuilder(getContext())
                .setTitle(getString(R.string.tittleDeleteHabit))
                .setMessage(getString(R.string.messageDeleteDependency, adapter.getItem(selectedHabit).getName()))
                .setNegativeButton(android.R.string.cancel, (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                })
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    showDeletedNotification(adapter.getItem(selectedHabit).getName());
                    viewModel.delete(adapter.getItem(selectedHabit));
                    adapter.deleteHabit(selectedHabit);
                    adapter.selectedPosition = -1;

                })
                .show();

    }

    private void showDeletedNotification(String name) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.splashicon)
                        .setContentTitle(getString(R.string.delHabitNotTitle))
                        .setContentText(getString(R.string.delHabitNotTxt, name))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS);
                }
                notificationManagerCompat.notify(0, builder.build());

            }
    }

    private ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    showDeletedNotification(adapter.getItem(selectedHabit).getName());
                } else {

                }
            });

    private void viewHabit(){
        NavHostFragment.findNavController(this).navigate(R.id.action_habitListFragment_to_habitViewFragment, setBundle());
    }

    private Bundle setBundle(){
        Bundle bundle = new Bundle();
        Habit habit = adapter.getItem(selectedHabit);
        bundle.putParcelable(Habit.KEY, habit);
        return bundle;
    }

    @Override
    public void onItemClick(View view, int position) {
        if(view.isSelected()){
            ModalBottomSheet modalBottomSheet = new ModalBottomSheet(this);
            modalBottomSheet.show(getActivity().getSupportFragmentManager(), ModalBottomSheet.TAG);
        }
        selectedHabit = position;
    }



    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    public static class ModalBottomSheet extends BottomSheetDialogFragment{
        private ModalBottomSheetBinding binding;
        private HabitListFragment habitListFragment;
        public static final String TAG = "modalBottomSheet";

        public ModalBottomSheet(HabitListFragment habitListFragment){
            this.habitListFragment = habitListFragment;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);

            dialog.setOnShowListener(dialog1 -> {
                ConstraintLayout bottomSheet = binding.standardBottomSheet;
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            });


            return dialog;
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            binding = ModalBottomSheetBinding.inflate(inflater, container, false);
            binding.dragHandle.setOnClickListener(view -> dismiss());
            binding.edit.setOnClickListener(view -> {
                habitListFragment.habitManagerFragment(habitListFragment.setBundle());
                dismiss();
            });
            binding.delete.setOnClickListener(view -> {
                habitListFragment.deleteHabit();
                dismiss();
            });
            binding.complete.setOnClickListener(view -> dismiss());
            binding.info.setOnClickListener(view -> {
                habitListFragment.viewHabit();
                dismiss();
            });
            binding.statics.setOnClickListener(view -> {
                dismiss();
            });
            return binding.getRoot();
        }

        @Override
        public void onDismiss(@NonNull DialogInterface dialog) {
            super.onDismiss(dialog);
            habitListFragment.adapter.selectedPosition = -1;
            habitListFragment.adapter.notifyDataSetChanged();
        }
    }
}