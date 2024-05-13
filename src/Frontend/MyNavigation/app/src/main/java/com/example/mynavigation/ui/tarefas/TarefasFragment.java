package com.example.mynavigation.ui.tarefas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.fragment.app.Fragment;
import com.example.mynavigation.R;

public class TarefasFragment extends Fragment {

    private ListView listaTarefas;
    private String[] itens = {
            "Exemplo1", "Exemplo2", "Exemplo3", "Exemplo4", "Exemplo5"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tarefas, container, false);


        listaTarefas = view.findViewById(R.id.listaTarefas);
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, itens);
        //ArrayAdapter<String> adaptador = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, android.R.id.text1, itens);
        listaTarefas.setAdapter(adaptador);

        return view;
    }
}
