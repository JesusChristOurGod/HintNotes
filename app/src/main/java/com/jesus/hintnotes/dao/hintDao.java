package com.jesus.hintnotes.dao;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import com.jesus.hintnotes.models.Hint;


public class hintDao {

    private static hintDao instance;

    private Map<Integer, Hint> hints;



    private hintDao(){
        hints=new HashMap<>();
    }

    public static hintDao getInstance(){
        Log.e("get instance", "get instance");
        if(instance == null){		//если объект еще не создан
            instance = new hintDao();	//создать новый объект
        }
        return instance;
    }

    public Map<Integer,Hint> getHints(){
        return hints;
    }

    public void addHint(Hint hint){
        Log.e("ADD", hint.getTitle()+ " "+hint.getPosition());
        hints.put(hint.getPosition(),hint);
    }

    public Hint findHint(String title) throws NullPointerException{
        for(Map.Entry<Integer,Hint> h: hints.entrySet()) {
            if (h.getValue().getTitle().toLowerCase().equals(title.toLowerCase())) return h.getValue();
        }
        throw new NullPointerException();
    }

    public void updateHint(Hint hint){
        Log.e("Update", hint.getTitle()+ " "+hint.getPosition());
        hints.put(hint.getPosition(),hint);
    }

    public Hint getHint(int id){
        return hints.get(id);
    }

    public void deleteHint(Hint hint) {
        hints.remove(hint.getPosition());
        Log.e("Delete", hint.getTitle()+ " "+hint.getPosition());
    }

}
