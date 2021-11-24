package com.example.gallerygrid;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class OpenNewImageActivity extends AppCompatActivity {

    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_new_image);
        String regilar = getIntent().getStringExtra("regilar");
        image = findViewById(R.id.image);
        Glide.with(this).load(regilar).into(image);
    }
}