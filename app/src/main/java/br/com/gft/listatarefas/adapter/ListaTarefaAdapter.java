package br.com.gft.listatarefas.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

import br.com.gft.listatarefas.R;
import br.com.gft.listatarefas.model.Tarefa;

public class ListaTarefaAdapter extends RecyclerView.Adapter<ListaTarefaAdapter.MyViewHolder> {


    public List<Tarefa> listaTarefas;

    public ListaTarefaAdapter(List<Tarefa> lista) {
        this.listaTarefas = lista;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //converter xml em object view
        View itemLista = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.adapter_tarefa_list, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.tarefa.setText(
                listaTarefas.get(position).getNomeTarefa()
        );
    }

    @Override
    public int getItemCount() {
        return listaTarefas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tarefa;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tarefa = itemView.findViewById(R.id.textTarefa);
        }
    }
}
