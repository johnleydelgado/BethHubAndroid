package com.robert.bethub.View;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.robert.bethub.Classes.Alert;
import com.robert.bethub.Model.User;
import com.robert.bethub.R;

import org.json.JSONObject;

import java.util.List;

import eu.amirs.JSON;
import io.realm.Realm;
import io.realm.RealmList;


public class Login extends AppCompatActivity {

    EditText emailTxtField;
    EditText passwordTxtField;
    private ConstraintLayout loginView;
    private ProgressBar spinner;
    Boolean rolesIsTrue;
    String loginStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        AndroidNetworking.initialize(getApplicationContext());
        Button signinButton = findViewById(R.id.signinButton);
        Button signupButton = findViewById(R.id.signupButton);
        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        emailTxtField = findViewById(R.id.usernameTextField);
        passwordTxtField = findViewById(R.id.passwordTextField);
        loginView = findViewById(R.id.loginView);
        setupUI(loginView);
        signinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    userLogin();
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alert.showAlertWithAction(Login.this,"Go to sign up page",signUp.class);
            }
        });
        Realm.init(this);

    }

    private void userLogin(){

        String email = emailTxtField.getText().toString().trim();
        String password = passwordTxtField.getText().toString().trim();

        if(email.isEmpty()){
            emailTxtField.setError("email or username required");
            emailTxtField.requestFocus();

        }else if(password.isEmpty()){
            passwordTxtField.setError("email or username required");
            passwordTxtField.requestFocus();
        }
        else{
            spinner.setVisibility(View.VISIBLE);
            AndroidNetworking.post("https://bethub.pro/wp-json/bethubpro/v1/login/"+email+"/"+password+"/")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            spinner.setVisibility(View.GONE);
                           // Log.d("tag1","response is : " + response);
                            JSON json = new JSON(response);
                            String status = json.key("status").stringValue();
                            String id = json.key("user").key("data").key("ID").stringValue();
                            String username = json.key("user").key("data").key("user_login").stringValue();
                            String email = json.key("user").key("data").key("user_email").stringValue();

                            JSON membership = json.key("memberships").index(0);
                            JSON roles = json.key("roles").index(0);
                           Log.d("tag membership",roles.stringValue());


                            if(roles.stringValue().contains("subscriber")){
                                rolesIsTrue = false;
                            }
                            else{
                                rolesIsTrue = true;
                            }

                            if (status == "true") {
                                Alert.showAlertWithAction(Login.this,"Successful login",MainActivity.class);
                                Realm realm = Realm.getDefaultInstance();
                                realm.beginTransaction();
                                RealmList<String> listMembership = new RealmList();
                                for(int i=0; i<membership.count(); i++){

                                    JSON info = membership.index(i);
                                    String membershipTitle = info.key("title").stringValue();
                                    listMembership.add(membershipTitle);
                                   // Log.d("tag is",membershipTitle);
                                }



                                User user = realm.createObject(User.class);
                                user.email = email;
                                user.id = Integer.parseInt(id);
                                user.username = username;
                                user.status = status;
                                user.title = listMembership;
                                user.rolesIsTrue = rolesIsTrue;
                                Log.d("tag is", user.title.toString());
                                realm.commitTransaction();
                                //Alert.showAlert(Login.this,"username or password is incorrect");
                            }
                            else{

                                Alert.showAlert(Login.this,"username or password is incorrect");

                            }
                            //Log.d("tag1","status is : " + json.key("status").stringValue());

                            // do anything with response
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                        }
                    });


        }

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(Login.this);
                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

}
