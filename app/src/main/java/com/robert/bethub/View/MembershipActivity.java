package com.robert.bethub.View;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

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


public class MembershipActivity extends AppCompatActivity {

    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static RecyclerView recyclerView;
    private static ArrayList<membershipData> data;
    public static View.OnClickListener myOnClickListener;
    public static int numberDrawable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership);
        numberDrawable = getIntent().getIntExtra("numberDrawable",0);

        myOnClickListener = new MyOnClickListener(this);

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        APICalls();

    }

    private void APICalls(){
        data = new ArrayList<membershipData>();


        String memberTitle = getIntent().getStringExtra("membershipTitle");

        AndroidNetworking.get("https://bethub.pro/wp-json/bethubpro/v1/posts/category/"+memberTitle)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                       JSON json = new JSON(response);
                       JSON post = json.key("posts");


                        for (int i = 0; i < post.count(); i++) {
                            JSON parseData = post.index(i);
                            String title = parseData.key("post_title").stringValue();
                            String content = parseData.key("post_content").stringValue();
                           // Log.d("tag1","title is :"+title);
                            data.add(new membershipData(content,
                                    1,
                                    title));
                        }

                        adapter = new MembershipAdapter(data);
                        recyclerView.setAdapter(adapter);


                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }


    private static class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(Context context) {
            this.context = context;
        }

        @Override
        public void onClick(View v) {

        }

    }

}