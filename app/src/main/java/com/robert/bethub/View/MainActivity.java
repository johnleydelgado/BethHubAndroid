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

import java.util.ArrayList;
import java.util.List;

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

    private ArrayList<String>  listTitle =new ArrayList<String>();

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
            int titleNumber = user.title.size();
            for (int index = 0; index < titleNumber;index++){
                listTitle.add(user.title.get(index));

            }

            Log.d("tag1","Members :" + listTitle);


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
                else {

                    List <String> listClone = new ArrayList<String>();
                    for (String string : listTitle) {
                        if(string.matches("(?i)(Target).*")){
                            listClone.add(string);
                        }
                    }

                    if (!listClone.isEmpty()||listTitle.contains("VIP")){
                        Intent intent = new Intent(getApplicationContext(), MembershipActivity.class);
                        intent.putExtra("numberDrawable", 0);
                        intent.putExtra("membershipTitle", "target-racing");
                        startActivity(intent);
                }
                else{
                        Alert.showAlert(MainActivity.this,"Upgrade your membership to access this feature");
                    }
                }

            }
        });

        greyhound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (loginSucess == false){
                    Alert.showAlert(MainActivity.this,"You need to login to access this feature");
                }
                else {
                    List <String> listClone = new ArrayList<String>();
                    for (String string : listTitle) {
                        if(string.matches("(?i)(Greyhound).*")){
                            listClone.add(string);
                        }
                    }
                    if (!listClone.isEmpty() || listTitle.contains("VIP")) {
                        Intent intent = new Intent(getApplicationContext(), MembershipActivity.class);
                        intent.putExtra("numberDrawable", 1);
                        intent.putExtra("membershipTitle", "Greyhound");
                        startActivity(intent);
                    }
                    else{
                        Alert.showAlert(MainActivity.this,"Upgrade your membership to access this feature");
                    }
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
                    List <String> listClone = new ArrayList<String>();
                    for (String string : listTitle) {
                        if(string.matches("(?i)(Early Racing).*")){
                            listClone.add(string);
                        }
                    }
                    if (!listClone.isEmpty() || listTitle.contains("VIP")||listTitle.contains("Early Bird Racing Yearly")) {
                    Intent intent = new Intent(getApplicationContext(),MembershipActivity.class);
                    intent.putExtra("numberDrawable",2);
                    intent.putExtra("membershipTitle","earlybird-racing");
                    startActivity(intent);

                }
                    else{
                        Alert.showAlert(MainActivity.this,"Upgrade your membership to access this feature");
                    }
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
                    List <String> listClone = new ArrayList<String>();
                    for (String string : listTitle) {
                        if(string.matches("(?i)(Gate).*")){
                            listClone.add(string);
                        }
                    }

                    if (!listClone.isEmpty() || listTitle.contains("VIP")) {
                        Intent intent = new Intent(getApplicationContext(),MembershipActivity.class);
                        intent.putExtra("numberDrawable",3);
                        intent.putExtra("membershipTitle","gatespeed");
                        startActivity(intent);
                    }
                    else{
                        Alert.showAlert(MainActivity.this,"Upgrade your membership to access this feature");
                    }
                }
            }
        });

    }
}
