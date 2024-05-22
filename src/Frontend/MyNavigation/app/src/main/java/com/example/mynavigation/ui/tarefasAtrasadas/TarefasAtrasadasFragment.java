package com.example.mynavigation.ui.tarefasAtrasadas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mynavigation.R;
import com.example.mynavigation.ui.progresso.ProgressoFragment;

public class TarefasAtrasadasFragment extends Fragment {
    //Calendario

    public TarefasAtrasadasFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarefasatrasadas, container, false);

        return view;
    }



}

