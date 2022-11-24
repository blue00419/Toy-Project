package com.example.test2.home;

import static android.app.Activity.RESULT_OK;

import static com.example.test2.MainActivity.context;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;

import com.example.test2.ApiService;
import com.example.test2.Data;
import com.example.test2.R;
import com.example.test2.SaveSharedPreference;
import com.example.test2.TicketData;
import com.example.test2.UserData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class HomeFragment extends Fragment {

    private Bitmap bitmap;
    private ImageView imageView;
    private View view;
    private String user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        imageView = view.findViewById(R.id.image);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);

        user = SaveSharedPreference.getUserName(getContext());
        Call<Data> comment = apiService.getImage(user);
        comment.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                if(response.isSuccessful()){
                    Log.d("Retrofit", "이미지 받기 " + response.body().getNickname());
                    Log.d("Retrofit", "이미지 받기2 " + StringToBitmap(response.body().getNickname()));

                    imageView.setImageBitmap(StringToBitmap(response.body().getNickname()));
                }
                else{
                    Log.d("Retrofit", "post 실패");
                }
            }

            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                Log.d("Retrofit", "이미지 받기 실패");
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.camera);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = SaveSharedPreference.getUserName(getContext());
                if(user.length() != 0){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    activityResultPicture.launch(intent);
                }
                else{
                    Snackbar.make(view, "로그인 해 주세요.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public static Bitmap StringToBitmap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 50, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    ActivityResultLauncher<Intent> activityResultPicture = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == RESULT_OK && result.getData() != null){
                        Bundle extras = result.getData().getExtras();
                        bitmap = (Bitmap) extras.get("data");
                        imageView.setImageBitmap(bitmap);

                        File f = new File(context.getCacheDir(), user);
                        try {
                            assert !f.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        // form, body
                        // 이미지의 Binary 정보 (이진 데이터)
                        ByteArrayOutputStream bos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
                        byte[] bitmapdata = bos.toByteArray();

                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(f);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        try {
                            assert fos != null;
                            fos.write(bitmapdata);
                            fos.flush();
                            fos.close();
                        } catch (IOException | NullPointerException e) {
                            e.printStackTrace();
                        }

                        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL)
                                .addConverterFactory(GsonConverterFactory.create()).build();
                        ApiService apiService = retrofit.create(ApiService.class);

                        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
                        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", f.getName(), reqFile);

                        Call<Data> comment = apiService.postImage(body);
                        comment.enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                if(response.isSuccessful()){

                                    String responsemsg = response.body().getMsg();
                                    Log.d("Retrofit", "이미지 전송 " + response.code() + " " + responsemsg);
                                }else{
                                    Log.d("Retrofit", "post 실패");
                                }
                            }

                            @Override
                            public void onFailure(Call<Data> call, Throwable t) {
                                Log.d("Retrofit", "연결 실패");
                            }
                        });
                    }
                }
            }
    );
}