package br.com.gft.listatarefas.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
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
        try{
            ContentValues cv = new ContentValues();
            cv.put("nomeTarefa", tarefa.getNomeTarefa());
            escreve.update(DbHelper.TABELA_TAREFAS, cv,"id="+tarefa.getId(),null);
            Log.i("INFO", "Tarefa atualizada com sucesso");
        } catch (Exception e){
            Log.e("INFO", "Erro ao atualizar tarefa "+e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deletar(Tarefa tarefa) {
        try{
            escreve.delete(DbHelper.TABELA_TAREFAS, "id="+tarefa.getId(),null);
            Log.i("INFO", "Tarefa deletada com sucesso");
        }catch (Exception e){
            Log.e("INFO", "Erro ao deletar tarefa "+e.getMessage());
            return false;
        }


        return false;
    }

    @Override
    public List<Tarefa> listar() {
        List<Tarefa> listaTarefas = new ArrayList<>();
        String sql = "SELECT * FROM "+DbHelper.TABELA_TAREFAS+";";
        Cursor cursor = le.rawQuery(sql, null);

        while (cursor.moveToNext()){
            Tarefa tarefa = new Tarefa();
            Long id = cursor.getLong(cursor.getColumnIndex("id"));
            String nomeTarefa = cursor.getString(cursor.getColumnIndex("nomeTarefa"));

            tarefa.setId(id);
            tarefa.setNomeTarefa(nomeTarefa);

            listaTarefas.add(tarefa);
        }
        return listaTarefas;
    }
}
