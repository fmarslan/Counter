package com.fmka.sayac;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private CounterManager counterManager;
    private TextView counterLabel;
    private TextView counterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        counterManager = new CounterManager(this);
        counterName = (TextView)findViewById(R.id.counterName);
        counterLabel = (TextView)findViewById(R.id.counterLabel);
        counterLabel.setText(counterManager.getCounter().getValue().toString());
        ImageButton counterButton = (ImageButton)findViewById(R.id.counterButton);
        counterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counterLabel.setText(counterManager.getCounter().increase().toString());
            }
        });
        counterManager.getCounter().setLoadListener(new Counter.LoadListener() {
            @Override
            public void loaded(String key) {
               counterName.setText(key);
                counterLabel.setText(counterManager.getCounter().getValue().toString());
            }
        });
        counterManager.getCounter().load(counterManager.getCounter().getKey());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_add) {
            counterManager.addCounter(new CounterManager.AddCounterListener() {
                @Override
                public void added(String key) {
                    counterLabel.setText(counterManager.getCounter().getValue().toString());
                }
            });
            return true;
        }
        if (id == R.id.action_select) {
            counterManager.selectCounter();
            return true;
        }
        if (id == R.id.action_reset) {
            counterLabel.setText(counterManager.getCounter().reset().toString());
            return true;
        }
        if (id == R.id.action_delete) {
            counterLabel.setText(counterManager.getCounter().deleteCounter().toString());
            return true;
        }
        if (id == R.id.action_edit) {
            counterManager.editCounter(new CounterManager.AddCounterListener() {
                @Override
                public void added(String key) {

                }
            });
            return true;
        }
        if (id == R.id.action_close) {
            finish();
            System.exit(0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
