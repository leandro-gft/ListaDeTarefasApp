package br.com.gft.listatarefas.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import br.com.gft.listatarefas.R;
import br.com.gft.listatarefas.helper.TarefaDAO;
import br.com.gft.listatarefas.model.Tarefa;

public class AdicionarTarefaActivity extends AppCompatActivity {

    private TextInputEditText textTarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_tarefa);

        textTarefa = findViewById(R.id.textTarefa);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId() ){
            case R.id.itemSalvar:
                TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
                Tarefa tarefa = new Tarefa();
                String nomeTarefa = textTarefa.getText().toString();
                if (!nomeTarefa.isEmpty()){
                    tarefa.setNomeTarefa(nomeTarefa);
                    tarefaDAO.salvar(tarefa);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(),"Preencha o nome da tarefa", Toast.LENGTH_LONG).show();
                }

                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
