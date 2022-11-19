package com.example.test2.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test2.AccountActivity;
import com.example.test2.LoginActivity;
import com.example.test2.R;

import org.w3c.dom.Text;

public class SettingFragment extends Fragment {

    private TextView accountText;
    private TextView loginText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        accountText = (TextView) view.findViewById(R.id.account_text);
        accountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), AccountActivity.class));
            }
        });

        loginText = (TextView) view.findViewById(R.id.login_text);
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}