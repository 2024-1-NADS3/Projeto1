package br.fecap.BemViverConnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.mynavigation.R;
import com.example.mynavigation.databinding.ActivityMainBinding;
import br.fecap.BemViverConnect.ui.tarefas.TarefasFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private TarefasFragment tarefas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_tarefas, // Added ID for Tarefas
                R.id.navigation_progresso,
                R.id.navigation_perfil
                )
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

    }

    public void BotaoAddTarefa (View view)
    {
        Intent mudarTela = new Intent(getApplicationContext(), AdicionarActivity.class);
        startActivity(mudarTela);
    }

    public void BotaoLogoff (View view)
    {
        Intent mudarTela = new Intent(getApplicationContext(), login.class);
        startActivity(mudarTela);

        ClasseUsuarioLogado.setIdUsuarioLogado(0);
    }
}