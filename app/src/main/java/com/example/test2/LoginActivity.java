package com.example.test2;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.test2.setting.SettingFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Set;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private TextView noText;
    private Button loginbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        toolbar = (MaterialToolbar) findViewById(R.id.appbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        loginbutton = (Button) findViewById(R.id.login_button);
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText email1 = (TextInputEditText) findViewById(R.id.email_text);
                String email = email1.getText().toString();

                TextInputEditText ps = (TextInputEditText) findViewById(R.id.password_text);
                String password = ps.getText().toString();

                if(email.length() == 0 || password.length() == 0){
                    Snackbar.make(view, "빈칸을 채워주세요.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL)
                            .addConverterFactory(GsonConverterFactory.create()).build();
                    ApiService apiService = retrofit.create(ApiService.class);

                    UserData user = new UserData(email, password, "aaa");
                    Call<Data> comment2 = apiService.post_login(user);
                    comment2.enqueue(new Callback<Data>() {
                        @Override
                        public void onResponse(Call<Data> call, Response<Data> response) {
                            if(response.isSuccessful()){
                                String responsenickname = response.body().getNickname();
                                int responsecode = response.body().getCode();
                                String responsemsg = response.body().getMsg();
                                Log.d("Retrofit", "login 연결 " + responsecode + " " + responsenickname + " " + responsemsg);

                                switch (responsecode){
                                    case 200:
                                        Snackbar.make(view, responsemsg, Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();

                                        SaveSharedPreference.setUserName(getApplicationContext(), responsenickname);
                                        ((MainActivity) MainActivity.context).refresh(0);
                                        finish();
                                        break;
                                    case 201:
                                        Snackbar.make(view, responsemsg, Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        break;
                                    case 202:
                                        Snackbar.make(view, responsemsg, Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        break;
                                    case 203:
                                        Snackbar.make(view, responsemsg, Snackbar.LENGTH_LONG)
                                                .setAction("Action", null).show();
                                        break;
                                }
                            }
                            else{
                                Log.d("Retrofit", "post 실패");
                                Log.d("Retrofit", "" + response.code());
                                Log.d("Retrofit", response.errorBody().toString());
                                Log.d("Retrofit", call.request().body().toString());
                            }
                        }

                        @Override
                        public void onFailure(Call<Data> call, Throwable t) {
                            Log.d("Retrofit", "연결 실패");
                        }
                    });
                }
            }
        });

        noText = (TextView) findViewById(R.id.no_account_text);
        noText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if(focusView != null){
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if(!rect.contains(x, y)){
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if(imm != null){
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                }
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}