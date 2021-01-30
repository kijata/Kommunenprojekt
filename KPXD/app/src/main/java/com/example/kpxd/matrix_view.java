package com.example.kpxd;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.EventListener;

public class matrix_view extends Fragment implements NewNameListener{

    public ArrayList<String> names;
    public int size;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        ((MainActivity) getActivity()).setNameListener(this);
        return inflater.inflate(R.layout.matrix_view, container, false);

    }
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PaintMatrix();
    }

    @Override
    public void onNewName() {
        this.PaintMatrix();
    }

    public void PaintMatrix()
    {
        TableLayout layout = getActivity().findViewById(R.id.schuldenmatrix);
        layout.removeAllViews();
        names = new ArrayList();
        String[] names_tmp = loadArray("names", getActivity());
        for (String name : names_tmp){
            names.add(name);
        }
        size = names.size() + 1;

        for (int i = 0; i < size; i++) {
            TableRow row = new TableRow(getActivity());
            row.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            for (int j = 0; j < size; j++) {
                EditText edit = new EditText(getActivity());
                edit.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                edit.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                if (i == 0 ) {
                    if (j != 0) {
                        edit.setText(names.get(j-1));
                    }
                }else if(j == 0){
                    if(i != 0){
                        edit.setText(names.get(i-1));
                    }
                }
                else{

                    edit.setText("i=" + i);
                    //fill with samples from the list for each cell
                    // or with the sum (depends on config)
                    // for now sum will be implemented
                }


                edit.setKeyListener(null);
                row.addView(edit);
            }
            layout.addView(row);
        }
    }

    public String[] loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("KP_XD", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        String array[] = new String[size];
        for(int i=0;i<size;i++)
            array[i] = prefs.getString(arrayName + '_' + i, null);
        return array;
    }

}
