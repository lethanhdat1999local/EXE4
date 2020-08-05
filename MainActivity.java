package com.example.appnhac;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;



import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView listView_casi;
    ArrayList<list_casi> list_casi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView_casi = (ListView) findViewById(R.id.list_casi);

        list_casi = new ArrayList<list_casi>();

        list_casi.add(new list_casi("Binz",R.drawable.binz));
        list_casi.add(new list_casi("Đen Vâu",R.drawable.dendo));
        list_casi.add(new list_casi("Hoài Lâm",R.drawable.hoailam));
        list_casi.add(new list_casi("Jack",R.drawable.jack));


        Adapter_casi adapter_casi = new Adapter_casi(MainActivity.this, R.layout.list_ca_si, list_casi);

        listView_casi.setAdapter(adapter_casi);

        listView_casi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Activity_bai_hat.class);
                intent.putExtra("position", position);
                intent.putExtra("tencasi", list_casi.get(position).ten_casi);
                startActivity(intent);
            }
        });

    }
}
