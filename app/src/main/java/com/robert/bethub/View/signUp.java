package com.robert.bethub.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.robert.bethub.Classes.Alert;
import com.robert.bethub.Model.User;
import com.robert.bethub.Model.userSignUp;
import com.robert.bethub.R;

import org.json.JSONObject;

import eu.amirs.JSON;
import io.realm.Realm;

import static com.robert.bethub.View.Login.hideSoftKeyboard;

public class signUp extends AppCompatActivity {

    private EditText firstname;
    private EditText lastname;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText passwordConfirmation;
    private EditText couponcode;
    private Button signupButton;

    private View signupView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        firstname =  findViewById(R.id.firstnameLabel);
        lastname =  findViewById(R.id.lastnameLabel);
        username =  findViewById(R.id.usernameLabel);
        email =  findViewById(R.id.emailLabel);
        password =  findViewById(R.id.passwordLabel);
        passwordConfirmation =  findViewById(R.id.passwordConfirmationLabel);
        couponcode =  findViewById(R.id.couponLabel);
        signupView = findViewById(R.id.signupView);
        signupButton = findViewById(R.id.signupButton);
        setupUI(signupView);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });


    }

    public void signUp(){

        String fname = firstname.getText().toString().trim();
        String lname = lastname.getText().toString().trim();
        String uname = username.getText().toString().trim();
        String em = email.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String passC = passwordConfirmation.getText().toString().trim();
        String cc = couponcode.getText().toString().trim();

        if(fname.isEmpty()){
            firstname.setError("Please fill up all the required fields");
            firstname.requestFocus();

        }else if(lname.isEmpty()){
            lastname.setError("Please fill up all the required fields");
            lastname.requestFocus();
        }
        else if(uname.isEmpty()){
            username.setError("Please fill up all the required fields");
            username.requestFocus();
        }
        else if(em.isEmpty()){
            email.setError("Please fill up all the required fields");
            email.requestFocus();
        }
        else if(pass.isEmpty()){
            password.setError("Please fill up all the required fields");
            password.requestFocus();
        }
        else if(passC.isEmpty()){
            passwordConfirmation.setError("Please fill up all the required fields");
            passwordConfirmation.requestFocus();
        }
        else if(cc.isEmpty()){
            couponcode.setError("Please fill up all the required fields");
            couponcode.requestFocus();
        }
        else if(pass != passC){
            password.setError("Your password didn`t match please try again");
            password.requestFocus();
            passwordConfirmation.setError("Your password didn`t match please try again");
            passwordConfirmation.requestFocus();
        }
        else{
            userSignUp user = new userSignUp();
            user.firstname = fname;
            user.lastname = lname;
            user.username = uname;
            user.email = em;
            user.password = pass;
            
           // spinner.setVisibility(View.VISIBLE);
            AndroidNetworking.post("https://bethub.pro/wp-json/bethubpro/v1/users/register")
                    .addBodyParameter(user)
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            //spinner.setVisibility(View.GONE);
                            Log.d("tag1","response is : " + response);
                            JSON json = new JSON(response);
                            int code = json.key("code").intValue();
                            String message = json.key("message").stringValue();
                            int status = json.key("data").key("status").intValue();

                            if (code == 200) {
                                Alert.showAlertWithAction(signUp.this,message,Login.class);

                                //Alert.showAlert(Login.this,"username or password is incorrect");
                            }
                            if (status == 400){

                                Alert.showAlert(signUp.this,message);

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

    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(signUp.this);
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
