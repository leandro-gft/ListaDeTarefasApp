package br.com.gft.listatarefas.activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.com.gft.listatarefas.R;
import br.com.gft.listatarefas.RecyclerItemClickListener;
import br.com.gft.listatarefas.adapter.ListaTarefaAdapter;
import br.com.gft.listatarefas.helper.TarefaDAO;
import br.com.gft.listatarefas.model.Tarefa;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Tarefa> listTarefas = new ArrayList<>();
    private Tarefa tarefaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Configurar recycler
        recyclerView = findViewById(R.id.recyclerView);

        //Adicionar evento de clique
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //Recuperar tarefa para edição
                        tarefaSelecionada = listTarefas.get(position);

                        //Envia tarefa para tela AdicionarTarefa
                        Intent intent = new Intent(MainActivity.this, AdicionarTarefaActivity.class);
                        intent.putExtra("tarefaSelecionada", tarefaSelecionada);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position){
                        alertaExclusao(position);
                    }

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    }
                }
        ));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(), AdicionarTarefaActivity.class);
               startActivity(intent);
            }
        });
    }

    private void alertaExclusao(int position) {
        tarefaSelecionada = listTarefas.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Confirmação de exclusão");
        builder.setMessage("Tem certeza que deseja excluir '" +tarefaSelecionada.getNomeTarefa()+ "' ?");
        builder.setIcon(R.drawable.ic_delete_black_24dp);
        builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int p) {
                if (tarefaSelecionada!=null) {
                    TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());

                    if (tarefaDAO.deletar(tarefaSelecionada)) {
                        Toast.makeText(getApplicationContext(), "Tarefa excluída com sucesso", Toast.LENGTH_LONG).show();
                        carregarListaTarefas();
                    } else{
                        Toast.makeText(getApplicationContext(), "Erro ao excluir a tarefa", Toast.LENGTH_LONG).show();
                    }
                } else{
                    Toast.makeText(getApplicationContext(), "Não foi possível excluir a tarefa", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("NÃO", null);

        builder.create();
        builder.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        carregarListaTarefas();
    }

    public void carregarListaTarefas() {
        TarefaDAO tarefaDAO = new TarefaDAO(getApplicationContext());
        listTarefas = tarefaDAO.listar();

        //Configurar adapter
        ListaTarefaAdapter adapter = new ListaTarefaAdapter(listTarefas);

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayout.VERTICAL));

    }


}
