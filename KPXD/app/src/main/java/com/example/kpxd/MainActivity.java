package com.example.kpxd;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ArrayList<String> names;
    protected NewNameListener nameListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        names = new ArrayList();

        String[] names_tmp = loadArray("names", this);
        for (String name : names_tmp){
            names.add(name);
        }
        Log.i("names","names: " +names.toString());
    }
    public void setNameListener(NewNameListener nameListener)
    {
        this.nameListener = nameListener;
    }
    public boolean saveArray(String[] array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("KP_XD", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.length);
        for(int i=0;i<array.length;i++)
            editor.putString(arrayName + '_' + i, array[i]);
        return editor.commit();
    }

    public String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("KP_XD", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getString(arrayName + '_' + i, null);
        return array;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_SaveList) {
            String[] names_tmp;
            names_tmp = new String[names.size()];
            for(String name : names){
                names_tmp[names.indexOf(name)] = name;
            }
            saveArray(names_tmp, "names", this);
            return true;
        }
        if (id == R.id.action_LoadList) {
            names.clear();
            String tmp = "";
            String[] names_tmp = loadArray("names", this);
            for (String name : names_tmp){
                names.add(name);
                tmp += name + '\n';
            }

            //((TextView)findViewById(R.id.textview_first)).setText(tmp);
            return true;
        }

        if (id == R.id.action_AddName) {
            names.add("herpaderp"+names.size());
            //saveArray(names_tmp, "names", this);
            Log.i("names","names: " +names.toString());
            String[] names_tmp;
            names_tmp = new String[names.size()];
            for(String name : names){
                names_tmp[names.indexOf(name)] = name;
            }
            saveArray(names_tmp, "names", this);
            nameListener.onNewName();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}