package com.example.signuploginrealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Med_Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_info);
        TextView tv=findViewById(R.id.textView7);
        tv.setMovementMethod(LinkMovementMethod.getInstance());


        Button btngotochatgpt=findViewById(R.id.btngotochatgpt);
        btngotochatgpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Med_Info.this,ChatGpt.class));
            }
        });

    }
}