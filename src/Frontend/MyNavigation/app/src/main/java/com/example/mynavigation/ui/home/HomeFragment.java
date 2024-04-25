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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Configurando a data atual
        TextView dataHoje = binding.dataHoje;
        SimpleDateFormat sdf = new SimpleDateFormat("dd 'de' MMMM 'de' yyyy", new Locale("pt", "BR"));
        String dataAtualText = sdf.format(Calendar.getInstance().getTime());
        dataHoje.setText(dataAtualText);

        // Lidando com o clique do botão para iniciar a AdicionarTarefaActivity
       // Button btnNovaTarefa = binding.btnNovaTarefa;
       // btnNovaTarefa.setOnClickListener(new View.OnClickListener() {
           // @Override
           // public void onClick(View v) {
                // Obter NavController a partir do Navigation Host Fragment
                //NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_activity_main);
                // Navegar para o destino definido no arquivo de navegação (nav_graph.xml)
                //navController.navigate(R.id.action_homeFragment_to_adicionarTarefaFragment);
            //}
        //});

        // Lidando com a seleção da opção (bad, normal, happy)
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

        Log.d("HomeFragment", "Item selecionado: " + opcaoSelecionada);
    }
}

