package com.robert.bethub.View;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.robert.bethub.Classes.Alert;
import com.robert.bethub.Model.User;
import com.robert.bethub.Model.membershipData;
import com.robert.bethub.R;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    Boolean loginSucess = false;

    private Button login;
    private Button logout;

    private Button subscribe;
    private Button services;
    private Button todaybet;

    private ImageView targetRacing;
    private ImageView greyhound;
    private ImageView earlybird;
    private ImageView gatespeed;

    public membershipData membershipdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Realm.init(this);

        login = findViewById(R.id.loginButton);
        logout = findViewById(R.id.logoutButton);

        subscribe = findViewById(R.id.subscribe);
        services = findViewById(R.id.services);
        todaybet = findViewById(R.id.todaybet);

        targetRacing = findViewById(R.id.targetRacingButton);
        greyhound = findViewById(R.id.greyhoundButton);
        earlybird = findViewById(R.id.earlybirdButton);
        gatespeed = findViewById(R.id.gatespeedButton);

        loginUI();
        membership();

    }


    public void loginUI() {

        final Realm realm = Realm.getDefaultInstance();
        RealmQuery <User> query = realm.where(User.class);
        RealmResults<User> result = query.findAll();
        if (query.count() < 1){
            loginSucess = false;
        }
        else{
            User user = result.get(0);
            loginSucess = Boolean.parseBoolean(user.status);
            Log.d("tag1","User is :" + user.status);
        }


        if(loginSucess == false) {
            login.setVisibility(View.VISIBLE);
            logout.setVisibility(View.GONE);
            subscribe.setVisibility(View.VISIBLE);
            services.setVisibility(View.VISIBLE);
            todaybet.setVisibility(View.VISIBLE);

        }
        else{
            login.setVisibility(View.GONE);
            logout.setVisibility(View.VISIBLE);
            subscribe.setVisibility(View.GONE);
            services.setVisibility(View.GONE);
            todaybet.setVisibility(View.VISIBLE);


        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);


            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                realm.beginTransaction();
                final RealmResults<User> results = realm.where(User.class).findAll();
                results.deleteAllFromRealm();
                realm.commitTransaction();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                overridePendingTransition(0, 0);

            }
        });
    }

    public void membership(){

        targetRacing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginSucess == false){
                    Alert.showAlert(MainActivity.this,"You need to login to access this feature");
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),MembershipActivity.class);
                    intent.putExtra("numberDrawable",0);
                    startActivity(intent);
                }

            }
        });

        greyhound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginSucess == false){
                    Alert.showAlert(MainActivity.this,"You need to login to access this feature");
                }
                else{

                    Intent intent = new Intent(getApplicationContext(),MembershipActivity.class);
                    intent.putExtra("numberDrawable",1);
                    startActivity(intent);
                }
            }
        });

        earlybird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginSucess == false){
                    Alert.showAlert(MainActivity.this,"You need to login to access this feature");
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),MembershipActivity.class);
                    intent.putExtra("numberDrawable",2);
                    startActivity(intent);
                }
            }
        });

        gatespeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginSucess == false){
                    Alert.showAlert(MainActivity.this,"You need to login to access this feature");
                }
                else{
                    Intent intent = new Intent(getApplicationContext(),MembershipActivity.class);
                    intent.putExtra("numberDrawable",3);
                    startActivity(intent);
                }
            }
        });

    }
}
