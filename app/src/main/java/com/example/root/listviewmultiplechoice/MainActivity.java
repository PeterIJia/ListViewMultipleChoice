package com.example.root.listviewmultiplechoice;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AbsListView.MultiChoiceModeListener{

    ListView listView;
    CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        
        listView = (ListView) findViewById(R.id.listview);
        List<String> list = new ArrayList<String>();
        String[] numbers = {"One", "Two", "Three",
                "Four", "Five", "Six", "Seven",
                "Eight", "Nine", "Ten", "Eleven",
                "Twelve", "Thirteen", "Fourteen", "Fifteen"};
        int size = numbers.length;
        for (int I = 0; I < size; I++) {
            list.add(numbers[I]);
        }

        mAdapter = new CustomAdapter(this, R.layout.custom_textview, list);
        listView.setAdapter(mAdapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);

        listView.setMultiChoiceModeListener(this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        listView.setItemChecked(position, true);

            }
        });
/*
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

        });*/


    }

    @Override
    public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
        mode.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
        if (item.getItemId() == R.id.delete) {
            SparseBooleanArray selected = mAdapter.getSelectedIds();
            short size = (short) selected.size();
            for (byte I = 0; I < size; I++) {
                if (selected.valueAt(I)) {
                    String selectedItem = mAdapter.getItem(selected.keyAt(I));
                    mAdapter.remove(selectedItem);
                }
            }

            // Close CAB (Contextual Action Bar)
            mode.finish();
            return true;
        }
        return false;
    }

    @Override
    public void onDestroyActionMode(android.view.ActionMode mode) {

    }

    @Override
    public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
        // Prints the count of selected Items in title
        mode.setTitle(listView.getCheckedItemCount() + " Selected");

        // Toggle the state of item after every click on it
        mAdapter.toggleSelection(position);
    }
}
