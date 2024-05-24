package com.example.mynavigation.ui.perfil;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.mynavigation.R;
import com.example.mynavigation.login;


public class PerfilFragment extends Fragment {

    public PerfilFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        // Inicialização do botão de logout
        Button logoutButton = view.findViewById(R.id.button3);

        // Definição do OnClickListener para o botão de logout
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chame o método para realizar o logout
                logoutUser();
            }
        });

        return view;
    }

    private void logoutUser() {
        // Aqui você pode realizar as ações de logout, como limpar as preferências do usuário,
        // encerrar a sessão, redirecionar para a tela de login, etc.

        // Por exemplo, você pode limpar as preferências do usuário:
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().apply();

        // Em seguida, você pode redirecionar para a tela de login:
        Intent intent = new Intent(getActivity(), login.class);
        startActivity(intent);
        getActivity().finish(); // Isso garante que a tela atual (PerfilFragment) seja encerrada após o logout.
    }


}
