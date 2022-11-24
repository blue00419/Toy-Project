package com.example.test2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.test2.home.HomeFragment;
import com.example.test2.setting.SettingFragment;
import com.example.test2.ticketbox.TicketboxFragment;
import com.example.test2.timeline.TimelineFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_PERMISSION = 11;
    private static final String TAG = "MainActivitiy";

    private HomeFragment homeFragment;
    private TimelineFragment timelineFragment;
    private TicketboxFragment ticketboxFragment;
    public static SettingFragment settingFragment;

    private Retrofit retrofit;
    private ApiService apiService;
    private Call<List<Json_Test>> comment;
    private List<Json_Test> result;
    public static Context context;

    String mCurrentPhotoPath;
    Bitmap bitmap;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        view = getLayoutInflater().inflate(R.layout.activity_main, null);
        checkPermission();

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

    //권한 확인
    public void checkPermission() {
        int permissionCamera = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        int permissionRead = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        int permissionWrite = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        //권한이 없으면 권한 요청
        if (permissionCamera != PackageManager.PERMISSION_GRANTED
                || permissionRead != PackageManager.PERMISSION_GRANTED
                || permissionWrite != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                Toast.makeText(this, "이 앱을 실행하기 위해 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
            }

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION: {
                // 권한이 취소되면 result 배열은 비어있다.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(this, "권한 확인", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();
                    finish(); //권한이 없으면 앱 종료
                }
            }
        }
    }
}