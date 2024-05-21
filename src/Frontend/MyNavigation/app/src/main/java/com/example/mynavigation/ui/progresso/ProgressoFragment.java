package com.example.mynavigation.ui.progresso;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.mynavigation.R;

public class ProgressoFragment extends Fragment {
    private ProgressBar progressBar;
    private Button buttonIncreaseProgress;
    private int progress = 0;

    public ProgressoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_progresso, container, false);

        // Find the ProgressBar and Button by their IDs
        progressBar = view.findViewById(R.id.progressBar2);
        buttonIncreaseProgress = view.findViewById(R.id.buttonIncreaseProgress);

        // Set an OnClickListener on the button to increase the progress
        buttonIncreaseProgress.setOnClickListener(v -> {
            // Increase the progress value
            progress += 10;
            if (progress > 100) {
                progress = 100; // Ensure progress doesn't exceed 100%
            }
            progressBar.setProgress(progress);
        });

        return view;
    }
}