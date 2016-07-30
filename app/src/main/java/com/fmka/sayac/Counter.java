package com.fmka.sayac;

import android.support.v7.app.AppCompatActivity;

import java.util.HashSet;
import java.util.Set;

public class Counter {

    private final String COUNTER_KEY = "COUNTER_KEY";
    private final String DEFAULT_KEY="Counter";

    public interface LoadListener{
        public void loaded(String key);
    }

    private DbClient dbClient;
    private AppCompatActivity activity;
    private String key;
    private Set<String> counters;
    private LoadListener loadListener;

    public Counter(AppCompatActivity activity) {
        this.activity = activity;
        dbClient = new DbClient(activity);
        if (getCounters().isEmpty())
            load(DEFAULT_KEY);
        else
            load(getCounters().iterator().next());
    }

    public Integer addCounter(String key){
        counters = dbClient.getData(COUNTER_KEY, new HashSet<String>());
        counters.add(key);
        dbClient.setData(COUNTER_KEY,counters);
        return load(key);
    }

    public Integer deleteCounter(){
        return deleteCounter(key);
    }

    public Integer deleteCounter(String counterKey){
        reset(key);
        counters = dbClient.getData(COUNTER_KEY, new HashSet<String>());
        counters.remove(counterKey);
        dbClient.setData(COUNTER_KEY,counters);
        if(counters.isEmpty())
            addCounter(DEFAULT_KEY);
        return load(counters.iterator().next());
    }

    public Integer load(String key){
        this.key = key;
        if(getLoadListener()!=null)
        getLoadListener().loaded(key);
        return dbClient.getData(key,0);
    }

    public Integer increase(){
        Integer counter = dbClient.getData(key,0)+1;
        dbClient.setData(key,counter);
        return counter;
    }

    public Integer reset(String counterKey){
        Integer counter = 0;
        dbClient.setData(counterKey,counter);
        return counter;
    }

    public Integer reset(){
        return reset(key);
    }

    public Integer setValue(Integer value){
        dbClient.setData(key,value);
        return value;
    }

    public Set<String> getCounters() {
        counters = dbClient.getData(COUNTER_KEY, new HashSet<String>());
        return counters;
    }

    public Integer getValue(){
        return getValue(key);
    }

    public Integer getValue(String key){
        return dbClient.getData(key,0);
    }

    public void setLoadListener(LoadListener loadListener) {
        this.loadListener = loadListener;
    }

    public LoadListener getLoadListener() {
        return loadListener;
    }

    public String getKey(){
        return key;
    }
}
