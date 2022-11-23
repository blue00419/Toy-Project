package com.example.test2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.test2.home.HomeFragment;
import com.example.test2.setting.SettingFragment;
import com.example.test2.ticketbox.TicketboxFragment;
import com.example.test2.timeline.TimelineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private HomeFragment homeFragment;
    private TimelineFragment timelineFragment;
    private TicketboxFragment ticketboxFragment;
    public static SettingFragment settingFragment;

    private Retrofit retrofit;
    private ApiService apiService;
    private Call<List<Json_Test>> comment;
    private List<Json_Test> result;
    public static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        SaveSharedPreference.getSharedPreferences(getApplicationContext());
        String user = SaveSharedPreference.getUserName(getApplicationContext());
        if(user.length() != 0){
            Log.d("Retrofit", "user 있음 : " + user);
        }
        else{
            Log.d("Retrofit", "user 없음 : " + user);
        }

        retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL).addConverterFactory(GsonConverterFactory.create()).build();
        apiService = retrofit.create(ApiService.class);
        comment = apiService.get_Test3();
        comment.enqueue(new Callback<List<Json_Test>>() {
            @Override
            public void onResponse(Call<List<Json_Test>> call, Response<List<Json_Test>> response) {
                if(response.isSuccessful()){
                    result = response.body();
                    Log.d("Retrofit", "get 성공" + result);
                }
                else{
                    Log.d("Retrofit", "get 실패");
                }
            }

            @Override
            public void onFailure(Call<List<Json_Test>> call, Throwable t) {
                Log.d("Retrofit", "연결 실패");
            }
        });

        homeFragment = new HomeFragment();
        timelineFragment = new TimelineFragment();
        ticketboxFragment = new TicketboxFragment();
        settingFragment = new SettingFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();

        BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
        navigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, homeFragment).commit();
                        return true;
                    case R.id.timeline:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, timelineFragment).commit();
                        return true;
                    case R.id.ticketbox:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, ticketboxFragment).commit();
                        return true;
                    case R.id.setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.containers, settingFragment).commit();
                        return true;
                }

                return false;
            }
        });
    }

    public void refresh(int idx){

        String text;
        if(idx == 0){
            text = "로그아웃";
        }
        else{
            text = "로그인";
        }
        settingFragment.setText(text);
        Intent intent = new Intent(MainActivity.this, MainActivity.class);
        finish();
        overridePendingTransition(0,0);
        startActivity(intent);
        overridePendingTransition(0,0);
    }
}