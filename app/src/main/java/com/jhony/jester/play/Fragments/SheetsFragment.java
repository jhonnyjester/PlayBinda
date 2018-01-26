package com.jhony.jester.play.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jhony.jester.play.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SheetsFragment extends Fragment {


    public SheetsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       return inflater.inflate(R.layout.bottom_sheets, container, false);
    }

}
