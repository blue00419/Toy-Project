package com.example.test2;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AccountActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private Button accountbutton;
    private TextView alreadyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        toolbar = (MaterialToolbar) findViewById(R.id.appbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        accountbutton = (Button) findViewById(R.id.account_button);
        accountbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextInputEditText email1 = (TextInputEditText) findViewById(R.id.email_text);
                String email = email1.getText().toString();

                TextInputEditText ps1 = (TextInputEditText) findViewById(R.id.password_text);
                String password = ps1.getText().toString();

                TextInputEditText ps2 = (TextInputEditText) findViewById(R.id.password_text2);
                String password2 = ps2.getText().toString();

                TextInputEditText nick = (TextInputEditText) findViewById(R.id.name_text);
                String nickname = nick.getText().toString();

                if(email.length() == 0 || password.length() == 0 || password2.length() == 0 || nickname.length() == 0){
                    Snackbar.make(view, "빈칸을 채워주세요.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    if(password.equals(password2) == false){
                        Snackbar.make(view, "비밀번호를 다시 확인해주세요.", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else{
                        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL)
                                .addConverterFactory(GsonConverterFactory.create()).build();
                        ApiService apiService = retrofit.create(ApiService.class);

                        UserData user = new UserData(email, password, nickname);
                        Call<UserData> comment2 = apiService.post_account(user);
                        comment2.enqueue(new Callback<UserData>() {
                            @Override
                            public void onResponse(Call<UserData> call, Response<UserData> response) {
                                if(response.isSuccessful()){
                                    Log.d("Retrofit", "account 연결 " + response.code());

                                    switch (response.code()){
                                        case 200:
                                            Snackbar.make(view, "성공", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                            finish();
                                        case 201:
                                            Snackbar.make(view, "중복", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();
                                        case 202:
                                            Snackbar.make(view, "이메일 형식을 확인해주세요.", Snackbar.LENGTH_LONG)
                                                    .setAction("Action", null).show();

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
                            public void onFailure(Call<UserData> call, Throwable t) {
                                Log.d("Retrofit", "연결 실패");
                            }
                        });
                    }
                }
            }
        });

        alreadyText = (TextView) findViewById(R.id.already_account_text);
        alreadyText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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