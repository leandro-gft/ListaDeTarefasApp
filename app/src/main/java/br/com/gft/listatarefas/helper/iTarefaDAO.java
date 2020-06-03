package br.com.gft.listatarefas.helper;

import java.util.List;

import br.com.gft.listatarefas.model.Tarefa;

public interface iTarefaDAO {

    public boolean salvar(Tarefa tarefa);
    public boolean atualizar(Tarefa tarefa);
    public boolean deletar(Tarefa tarefa);
    public List<Tarefa> listar();

}
