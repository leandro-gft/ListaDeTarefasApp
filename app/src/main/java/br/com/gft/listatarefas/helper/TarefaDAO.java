package br.com.gft.listatarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.List;

import br.com.gft.listatarefas.model.Tarefa;

public class TarefaDAO implements iTarefaDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;

    public TarefaDAO(Context context) {
        DbHelper db = new DbHelper(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public boolean salvar(Tarefa tarefa) {
        try{
            ContentValues cv = new ContentValues();
            cv.put("nomeTarefa", tarefa.getNomeTarefa());
            escreve.insert(DbHelper.TABELA_TAREFAS, null,cv);
            Log.i("INFO", "Tarefa salva com sucesso");
        } catch (Exception e){
            Log.e("INFO", "Erro ao salvar tarefa "+e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(Tarefa tarefa) {
        return false;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        return false;
    }

    @Override
    public List<Tarefa> listar() {
        return null;
    }
}
