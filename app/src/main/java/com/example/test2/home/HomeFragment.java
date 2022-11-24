package com.example.test2.home;

import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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
import com.example.test2.TicketData;
import com.example.test2.UserData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Url;

public class HomeFragment extends Fragment {

    Bitmap bitmap;
    private ImageView imageView;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_home, container, false);

        imageView = view.findViewById(R.id.image);

        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        ApiService apiService = retrofit.create(ApiService.class);

//        TicketData data = new TicketData("young", bitmap);
//        Call<TicketData> comment = apiService.get_ticket(data);
//        comment.enqueue(new Callback<TicketData>() {
//            @Override
//            public void onResponse(Call<TicketData> call, Response<TicketData> response) {
//                if(response.isSuccessful()){
//                    Log.d("Retrofit", "이미지 받기 ");
//
//                    imageView.setImageBitmap(response.body().getImage2());
//                }
//                else{
//                    Log.d("Retrofit", "post 실패");
//                    Log.d("Retrofit", "" + response.code());
//                    Log.d("Retrofit", response.errorBody().toString());
//                    Log.d("Retrofit", call.request().body().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<TicketData> call, Throwable t) {
//                Log.d("Retrofit", "연결 실패");
//            }
//        });

        FloatingActionButton fab = view.findViewById(R.id.camera);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activityResultPicture.launch(intent);

            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public static String saveBitmapToJpeg(Context context, Bitmap bitmap, String name){
        int maximagesize = 50 * 10000; // 저용량 변환중 최대 사이즈
        int realimagesize = maximagesize;
        int quality = 100; //사진퀄리티는 처음 100부터 줄여나가면서 용량을 맞춥니다.

        File storage = context.getCacheDir(); //임시파일(캐시라 적혀잇죠?)

        String fileName = name + ".jpg";  // 어짜피 임시파일이기 때문에 알맞게 적어주세요.

        File tempFile = new File(storage,fileName);

        try{
            tempFile.createNewFile();  // 파일을 생성해주고


            //아래 부분이 가장 중요한 부분이에요.
            while(realimagesize >= maximagesize) {
                if(quality < 0){
                    return "tobig";
                }
                FileOutputStream out = new FileOutputStream(tempFile);

                bitmap.compress(Bitmap.CompressFormat.JPEG, quality, out);
                realimagesize = (int)tempFile.length(); //작아진 본 파일의 크기를 저장하여 다시 비교합니다.

                quality -= 20; //이부분으 줄면서 용량이 작아닙니다.

                out.close(); // 마무리로 닫아줍니다.

            }

            Log.d("Retrofit","imagelocation resizefilesize result: " + realimagesize);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return tempFile.getAbsolutePath();   //임시파일 경로로 리턴.
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
                        Uri uri = result.getData().getData();

                        String path = saveBitmapToJpeg(getContext(), bitmap, "please");
                        Log.d("Retrofit", "이미지 " + " " + path);

                        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiService.API_URL)
                                .addConverterFactory(GsonConverterFactory.create()).build();
                        ApiService apiService = retrofit.create(ApiService.class);

                        TicketData data = new TicketData(path, bitmap);
                        Call<Data> comment = apiService.post_ticket(data);
                        comment.enqueue(new Callback<Data>() {
                            @Override
                            public void onResponse(Call<Data> call, Response<Data> response) {
                                if(response.isSuccessful()){
                                    int responsecode = response.body().getCode();
                                    String responsemsg = response.body().getMsg();
                                    Log.d("Retrofit", "이미지 전송 " + response.code() + " " + responsecode + " " + responsemsg);

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
            }
    );

    public String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] imagebytes = baos.toByteArray();
        String imageString = Base64.encodeToString(imagebytes, Base64.DEFAULT);
        return imageString;
    }

}