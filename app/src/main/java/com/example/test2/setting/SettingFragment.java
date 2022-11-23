package com.example.test2.setting;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.test2.AccountActivity;
import com.example.test2.LoginActivity;
import com.example.test2.MainActivity;
import com.example.test2.R;
import com.example.test2.SaveSharedPreference;

import java.util.Set;

public class SettingFragment extends Fragment {

    private TextView accountText;
    public static TextView loginText;
    public static LayoutInflater _inflater;
    public static ViewGroup _container;
    public static Bundle _savedInstanceState;
    public static SettingFragment settingFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        settingFragment = this;
        _inflater = inflater;
        _container = container;
        _savedInstanceState = savedInstanceState;
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        loginText = (TextView) view.findViewById(R.id.login_text);

        String user = SaveSharedPreference.getUserName(getContext());
        if(user.length() != 0){
            Log.d("Retrofit", "user 있음 : " + user);
            loginText.setText("로그아웃");
        }
        else{
            Log.d("Retrofit", "user 없음 : " + user);
            loginText.setText("로그인");
        }
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user.length() != 0){
                    Log.d("Retrofit", "user 있음 : " + user);
                    SaveSharedPreference.clearUserName(getContext());
                    ((MainActivity) MainActivity.context).refresh(1);
                }
                else{
                    getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            }
        });

        accountText = (TextView) view.findViewById(R.id.account_text);
        accountText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), AccountActivity.class));
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setText(String text){
        loginText.setText(text);
    }
}