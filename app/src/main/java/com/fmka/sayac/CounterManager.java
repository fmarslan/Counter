package com.fmka.sayac;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CounterManager {

    public interface AddCounterListener{
        public void added(String key);
    }

    private Counter counter;
    private AppCompatActivity activity;

    public CounterManager(AppCompatActivity activity){
        this.activity = activity;
        this.counter = new Counter(activity);
    }

    public void addCounter(final AddCounterListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("New Counter");

        final EditText input = new EditText(activity);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                counter.addCounter(input.getText().toString());
                listener.added(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void editCounter(final AddCounterListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Edit Counter ["+counter.getKey()+"]");

        final EditText input = new EditText(activity);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                counter.setValue(Integer.valueOf(input.getText().toString()));
                listener.added(counter.getKey());
                counter.load(counter.getKey());
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public void selectCounter() {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Select Counter");

        final Spinner s = new Spinner(activity);

        ArrayAdapter<String> sAdapter =new ArrayAdapter<String>(activity, R.layout.select_row,getCounterList());

        s.setAdapter(sAdapter);

        s.setSelection(sAdapter.getPosition(getCounter().getKey()));

        builder.setView(s);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
               counter.load(s.getSelectedItem().toString());
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    public AppCompatActivity getActivity() {
        return activity;
    }

    public Counter getCounter() {
        return counter;
    }

    private List<String> getCounterList(){
        List<String> list = new ArrayList<>();
        Iterator<String> iterator = getCounter().getCounters().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            list.add(key);
        }
        return list;
    }
}
