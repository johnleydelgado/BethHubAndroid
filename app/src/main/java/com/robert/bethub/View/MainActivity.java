package com.robert.bethub.View;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.robert.bethub.Classes.Alert;
import com.robert.bethub.Model.User;
import com.robert.bethub.Model.membershipData;
import com.robert.bethub.R;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

public class MainActivity extends AppCompatActivity {

    Boolean loginSucess = false;


    private Button subscribe;
    private Button services;
    private Button todaybet;

    private ImageView targetRacing;
    private ImageView greyhound;
    private ImageView earlybird;
    private ImageView gatespeed;

    private ArrayList<String>  listTitle =new ArrayList<String>();
    private FirebaseAuth mAuth;

    public membershipData membershipdata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);


        subscribe = findViewById(R.id.subscribe);
        services = findViewById(R.id.services);
        todaybet = findViewById(R.id.todaybet);

        targetRacing = findViewById(R.id.targetRacingButton);
        greyhound = findViewById(R.id.greyhoundButton);
        earlybird = findViewById(R.id.earlybirdButton);
        gatespeed = findViewById(R.id.gatespeedButton);

        FloatingActionButton floatingActionButton = findViewById(R.id.fab_tip);
        floatingActionButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                    SubmitTips();
            }
        });

        loginUI();
        membership();
        ContentNotification();


    }


    public void ContentNotification(){
        Bundle bundleValue = getIntent().getExtras();

        if(bundleValue!=null) {
           // Toast.makeText(this, bundleValue.getString("id"), Toast.LENGTH_SHORT).show();
            String id = bundleValue.getString("id");
            if (id != null) {
                Intent intent = new Intent(getApplicationContext(), NotificationContentActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            } else {

            }
        }

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

            Log.d("tag1","Members :" + user.rolesIsTrue);


        }


        if(loginSucess == false) {

            subscribe.setVisibility(View.VISIBLE);
            services.setVisibility(View.VISIBLE);
            todaybet.setVisibility(View.VISIBLE);

        }
        else{

            subscribe.setVisibility(View.GONE);
            services.setVisibility(View.GONE);
            todaybet.setVisibility(View.VISIBLE);


        }



    }

    public void SubmitTips(){

        final Realm realm = Realm.getDefaultInstance();
        RealmQuery <User> query = realm.where(User.class);
        RealmResults<User> result = query.findAll();
        if (query.count() < 1){
            Alert.showAlert(MainActivity.this,"You need to login to access this feature");
        }
        else{
            User user = result.get(0);

            if(user.rolesIsTrue == true){
                startActivity(new Intent(getBaseContext(), TipSubmit.class));
            }else{
                Alert.showAlert(MainActivity.this,"You`re not an administrator");
            }
        }


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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_login, menu);
        MenuItem logout = menu.findItem(R.id.logout);
        MenuItem login = menu.findItem(R.id.login);
        for(int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            SpannableString spanString = new SpannableString(menu.getItem(i).getTitle().toString());
            spanString.setSpan(new ForegroundColorSpan(Color.WHITE), 0,     spanString.length(), 0); //fix the color to white
            item.setTitle(spanString);
        }
        // show the button when some condition is true
        if (loginSucess == true) {
            logout.setVisible(true);
            login.setVisible(false);
        }else{
            logout.setVisible(false);
            login.setVisible(true);
        }
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            /// case R.id.save_tip:
            //  Toast.makeText(this, "save", Toast.LENGTH_SHORT).show();
            //  saveTip();
            //  return true;
            case R.id.login:
                LoginClick();

                return true;
            case R.id.logout:
                LogoutClick();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void LoginClick(){

        Intent intent = new Intent(getApplicationContext(), Login.class);

        startActivity(intent);
    }
    public void LogoutClick(){
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        final RealmResults<User> results = realm.where(User.class).findAll();
        results.deleteAllFromRealm();
        realm.commitTransaction();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

}
