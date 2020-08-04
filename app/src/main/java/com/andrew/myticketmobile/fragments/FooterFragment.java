package com.andrew.myticketmobile.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.andrew.myticketmobile.R;

public class FooterFragment extends Fragment implements DialogInterface.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.menu_layout,
                container, false);
        return view;
    }

    @Override
    public void onClick(DialogInterface dialogInterface, int i) {

    }

}
