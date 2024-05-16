package com.example.mynavigation.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.mynavigation.R;
import com.example.mynavigation.databinding.FragmentHomeBinding;
import com.example.mynavigation.humor.AppPreferences;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)  {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurando a data atual
        TextView dataHoje = binding.dataHoje;
        SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
        String dataAtualText = sdf.format(Calendar.getInstance().getTime());
        dataHoje.setText(dataAtualText);


        binding.malButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcaoSelecionada("bad");
            }
        });

        binding.normalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcaoSelecionada("normal");
            }
        });

        binding.felizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opcaoSelecionada("happy");
            }
        });

        ImageView imagemResultado = binding.imagemResultado;
        String estadoAtual = AppPreferences.getInstance(requireContext()).getSelectedOption();
        switch (estadoAtual) {
            case "bad":
                imagemResultado.setImageResource(R.drawable.mal);
                break;
            case "normal":
                imagemResultado.setImageResource(R.drawable.normal);
                break;
            case "happy":
                imagemResultado.setImageResource(R.drawable.feliz);
                break;
        }

        return root;
    }


    

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void opcaoSelecionada(String opcaoSelecionada) {
        ImageView imagemResultado = binding.imagemResultado;

        switch (opcaoSelecionada) {
            case "bad":
                imagemResultado.setImageResource(R.drawable.mal);
                break;
            case "normal":
                imagemResultado.setImageResource(R.drawable.normal);
                break;
            case "happy":
                imagemResultado.setImageResource(R.drawable.feliz);
                break;
        }
        AppPreferences.getInstance(requireContext()).setSelectedOption(opcaoSelecionada);

    }
}

