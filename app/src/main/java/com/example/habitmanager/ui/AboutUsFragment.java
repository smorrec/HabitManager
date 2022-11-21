package com.example.habitmanager.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.habitmanager.R;
import com.example.habitmanager.databinding.FragmentAboutUsBinding;
import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;


public class AboutUsFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        AboutView view = AboutBuilder.with(getActivity())
                .setName(R.string.name).setNameColor(R.color.secondaryDarkColor)
                .setSubTitle(R.string.activity).setSubTitleColor(R.color.secondaryColor)
                .setBrief(R.string.brief).setBriefColor(R.color.secondaryColor)
                .setAppIcon(R.mipmap.ic_launcher)
                .setAppName(R.string.app_name)
                .addGitHubLink(R.string.gitHub)
                .addFiveStarsAction()
                .setVersionNameAsAppSubTitle()
                .addShareAction(R.string.app_name)
                .addLinkedInLink(R.string.linkedIn)
                .setWrapScrollView(true)
                .setLinksAnimated(true)
                .setShowAsCard(true)
                .setDividerColor(R.color.secondaryLightColor)
                .build();
        return view;
    }
}