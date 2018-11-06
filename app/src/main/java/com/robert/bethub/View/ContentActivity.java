package com.robert.bethub.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.robert.bethub.R;

public class ContentActivity extends AppCompatActivity {

    TextView contentText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        contentText = findViewById(R.id.contentText);
        String content = getIntent().getStringExtra("content");
        Log.d("tag1",content);
        contentText.setText(Html.fromHtml(content));

    }
}
