package br.fecap.BemViverConnect.ui.adapter;

import static android.content.ContentValues.TAG;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mynavigation.R;
import br.fecap.BemViverConnect.ui.tarefas.TarefasFragment;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private ArrayList<Tarefa> listaTarefas;
    private static Button btnFeito;
    private TarefasFragment tarefas;

    public Adapter(ArrayList<Tarefa> listaTarefas) {
        this.listaTarefas = listaTarefas;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tarefa, parent, false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Tarefa tarefa = listaTarefas.get(position);
        holder.textNomeTarefa.setText(tarefa.getNomeTarefa());
        holder.textDataHora.setText(tarefa.getDataHora());

        Integer idTarefa = tarefa.getId();
        btnFeito = holder.btnFeito;

        btnFeito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mudarStatus(idTarefa, Adapter.this);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaTarefas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView textDataHora, textNomeTarefa;
        private CheckBox checkTarefa;
        private Button btnFeito;
        private TarefasFragment tarefas;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textNomeTarefa = itemView.findViewById(R.id.textNomeTarefa);
            textDataHora = itemView.findViewById(R.id.textDataHora);
            btnFeito = itemView.findViewById(R.id.btnFeito);
        }
    }

    public void mudarStatus(Integer id, RecyclerView.Adapter adapter) {
        Log.e(TAG, "Id: " + id);

        String urlString = "https://vq4x7v-3000.csb.app/atualizarStatus/" + id + "/" + 0;
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);
                    connection.setRequestProperty("Content-Type", "application/json");

                    OutputStream os = connection.getOutputStream();
                    os.flush();
                    os.close();

                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        runOnUiThread(() -> {
                            Toast.makeText(tarefas.getContext(), "Status atualizado com sucesso", Toast.LENGTH_SHORT).show();
                            adapter.notifyDataSetChanged();
                        });
                    } else {
                        runOnUiThread(() -> Toast.makeText(tarefas.getContext(), "Erro ao atualizar status", Toast.LENGTH_SHORT).show());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(tarefas.getContext(), "Erro na solicitação: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                }

                return null;
            }

            private void runOnUiThread(Runnable runnable) {
            }
        }.execute();
    }
}