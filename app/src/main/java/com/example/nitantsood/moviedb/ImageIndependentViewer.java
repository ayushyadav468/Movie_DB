package com.example.nitantsood.moviedb;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageIndependentViewer extends AppCompatActivity {
Bitmap bitmap;
    int h,w;
    final int mScreenHeight= Resources.getSystem().getDisplayMetrics().heightPixels;
    final int mScreenWidth=Resources.getSystem().getDisplayMetrics().widthPixels;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_independent_viewer);

        Intent i=getIntent();
        final String url=i.getStringExtra("url");
        final ImageView image=(ImageView) findViewById(R.id.imageView);
        Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500"+url ).into(image, new Callback() {
            @Override
            public void onSuccess() {
                bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                w=bitmap.getWidth();
                h=bitmap.getHeight();
                if(h>w){
                    Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500"+url ).resize(0,mScreenHeight).into(image);
//                    bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                }else{
                    Picasso.with(getApplicationContext()).load("https://image.tmdb.org/t/p/w500"+url ).resize(mScreenWidth,0).into(image);
//                    bitmap = ((BitmapDrawable) image.getDrawable()).getBitmap();
                }
            }

            @Override
            public void onError() {

            }
        });

        image.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                File sdCardDirectory = Environment.getExternalStorageDirectory();
                File image = new File(sdCardDirectory,"MovieDB.png");
                boolean success = false;
                FileOutputStream outStream;
                try {

                    outStream = new FileOutputStream(image);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                    success = true;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (success) {
                    Toast.makeText(getApplicationContext()," image successfully saved", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Error during image saving", Toast.LENGTH_LONG).show();
                }
                return true;
            }
        });

    }
}
