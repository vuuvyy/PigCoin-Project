package com.example.myapplication.View;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Controller.DatabaseHelper;
import com.example.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_chi_tiet_thog_ke#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_chi_tiet_thog_ke extends Fragment {
    TextView txtTongTienFragChiTiet;
    SQLiteDatabase database;
    int Thang;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters


    public fragment_chi_tiet_thog_ke() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static fragment_chi_tiet_thog_ke newInstance(int param1) {
        fragment_chi_tiet_thog_ke fragment = new fragment_chi_tiet_thog_ke();
        Bundle args = new Bundle();
       args.putString("thang", String.valueOf(param1));

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        View view = inflater.inflate(R.layout.fragment_chi_tiet_thog_ke, container, false);
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
//        databaseHelper.deleteDatabase(getActivity());
        database = databaseHelper.getWritableDatabase();
        if (args != null) {
            String getThang = args.getString("thang");
            Thang = Integer.parseInt(getThang);
            Thang+=1;

        }
        AddControl(view);
        AddEvent();



        return view;
    }
    private void AddControl(View view){
        txtTongTienFragChiTiet = view.findViewById(R.id.txtTongTienFragChiTiet);


    }
    private void AddEvent(){
        String t = String.valueOf(Thang);
        txtTongTienFragChiTiet.setText(t);
    }
}