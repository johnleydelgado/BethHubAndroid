package com.robert.bethub.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.robert.bethub.Adapter.MembershipAdapter;
import com.robert.bethub.Model.membershipData;
import com.robert.bethub.R;

import org.json.JSONObject;

import java.util.ArrayList;

import eu.amirs.JSON;
import okhttp3.Credentials;

public class NotificationContentActivity extends AppCompatActivity {
    private ProgressBar spinner;
    TextView contentText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_content_notification);

        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        contentText = findViewById(R.id.contentText);
        AndroidNetworking.initialize(getApplicationContext());
        APICalls();
    }

    private void APICalls(){

        String id = getIntent().getStringExtra("id");
        spinner.setVisibility(View.VISIBLE);
        //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        AndroidNetworking.get("https://bethub.pro/wp-json/wp/v2/posts/" + id)

                .addHeaders("Authorization", Credentials.basic("jezriel", "jez0909"))
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        spinner.setVisibility(View.INVISIBLE);
                        JSON json = new JSON(response);
                        String content = json.key("content").key("rendered").stringValue();
                        //Toast.makeText(NotificationContentActivity.this, content, Toast.LENGTH_SHORT).show();
                        contentText.setText(Html.fromHtml(content));

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}
