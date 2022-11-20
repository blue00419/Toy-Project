package com.example.test2.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test2.ApiService;
import com.example.test2.Json_Test;
import com.example.test2.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        FloatingActionButton fab = view.findViewById(R.id.camera);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // post됨
//                Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL)
//                        .addConverterFactory(GsonConverterFactory.create()).build();
//                ApiService apiService = retrofit.create(ApiService.class);
//
//                Json_Test item = new Json_Test("bbbbb");
//                Call<Json_Test> comment = apiService.post_Test3(item);
//                comment.enqueue(new Callback<Json_Test>() {
//                    @Override
//                    public void onResponse(Call<Json_Test> call, Response<Json_Test> response) {
//                        if(response.isSuccessful()){
//                            Log.d("Retrofit", "post 성공");
//                        }
//                        else{
//                            Log.d("Retrofit", "post 실패");
//                            Log.d("Retrofit", "" + response.code());
//                            Log.d("Retrofit", response.errorBody().toString());
//                            Log.d("Retrofit", call.request().body().toString());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<Json_Test> call, Throwable t) {
//                        Log.d("Retrofit", "연결 실패");
//                    }
//                });
            }
        });


        // Inflate the layout for this fragment
        return view;
    }
}