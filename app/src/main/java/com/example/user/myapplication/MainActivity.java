package com.example.user.myapplication;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import org.opencv.core.Mat;

import java.io.FileNotFoundException;
import java.io.IOException;
import org.opencv.android.Utils;

public class MainActivity extends AppCompatActivity {
    Button b = null;
    Button b2 = null;
    Bitmap bmp;
    final int PICTURE_REQUEST_CODE = 100;
    ImageView image1;
    private Mat matInput;
    private Mat matResult;
    public native void cvGray(long matAddrInput, long matAddrResult);
    static{
        System.loadLibrary("opencv_java3");
        System.loadLibrary("native-lib");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b = (Button)findViewById(R.id.Button);
        b2 = (Button)findViewById(R.id.Button2);

        image1 = (ImageView)findViewById(R.id.imageview);
        b.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                //출처: http://ghj1001020.tistory.com/368 [혁준 블로그]
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Picture"),PICTURE_REQUEST_CODE);
            }
        });

        b2.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(MainActivity.this, Camera.class);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PICTURE_REQUEST_CODE){
            if(resultCode == RESULT_OK){

                //기존 이미지 지우기
                image1.setImageResource(0);

                Uri uri = data.getData();
                ClipData clipData = data.getClipData();
                if(clipData != null){

                }
                else if(uri != null){
                    try {
                        bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                    //image1.setImageURI(uri);
                    Mat result = ToGray(bmp);
                    Utils.matToBitmap(result,bmp);

                    image1.setImageBitmap(bmp);
                }

            }
        }
    }
    public void startGalleryChooser(){

    }
    public Mat ToGray(Bitmap bmp){
        matInput = new Mat();
        Utils.bitmapToMat(bmp, matInput);

        if (matResult != null) matResult.release();
        matResult = new Mat(matInput.rows(), matInput.cols(), matInput.type());

        cvGray(matInput.getNativeObjAddr(), matResult.getNativeObjAddr());
        return matResult;
    }


}
