package com.robert.bethub.View;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.robert.bethub.Classes.Alert;
import com.robert.bethub.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import eu.amirs.JSON;
import okhttp3.Credentials;

public class TipSubmit extends AppCompatActivity {
    private EditText contentText;
    private EditText titleText;
    private EditText categoryText;
    private ConstraintLayout tipView;
    public Integer rolesId;
    private ProgressBar spinner;
    private MaterialSpinner spinnerTips;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_submit);
        contentText = findViewById(R.id.tip_content);
        titleText = findViewById(R.id.tip_title);
        tipView = findViewById(R.id.tipView);
        setupUI(tipView);

        StartUI();


    }

    public void StartUI(){

        spinner = findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        categoryText = findViewById(R.id.categoryEditText);

        spinnerTips = (MaterialSpinner) findViewById(R.id.spinner);
        spinnerTips.setItems("Target Racing", "Greyhound Maestro", "GateSpeed", "Early Bird Racing Club", "Blog");
        spinnerTips.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                // Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();
                categoryText.setText(item);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.toolbar_tipsubmit, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_tip:

                saveTip();
                return true;

            case R.id.action_font:

                initiatePopupWindow();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
    private PopupWindow pwindo;
    //font settings
    private void initiatePopupWindow() {
        try {
// We need to get the instance of the LayoutInflater
            // Toast.makeText(Genesis.this, "Font", Toast.LENGTH_SHORT).show();
            LayoutInflater inflater = (LayoutInflater) TipSubmit.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View layout = inflater.inflate(R.layout.fontsettings,
                    (ViewGroup) findViewById(R.id.Scroolview_hide));
            pwindo = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);

            pwindo.showAtLocation(layout, Gravity.CENTER, 1, 1);

            SeekBar seekBar = (SeekBar)layout.findViewById(R.id.seekBar);
            final TextView seekBarValue = (TextView)layout.findViewById(R.id.size);

            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                                              boolean fromUser) {
                    // TODO Auto-generated method stub

                    contentText.setTextSize(progress);
                    seekBarValue.setText("Size: " + String.valueOf(progress));

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }
            });

            ImageButton close = (ImageButton) layout.findViewById(R.id.btnclose);
            close.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    pwindo.dismiss();
                }
            });


            //Font
            //Font
            Button BookAntiqua= (Button) layout.findViewById(R.id.Ba);
            BookAntiqua.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //  Toast.makeText(Chapters.this, "night", Toast.LENGTH_SHORT).show();
                    Typeface face = Typeface.createFromAsset(getAssets(),
                            "fonts/book-antiqua.ttf");
                    contentText.setTypeface(face);
                }
            });

            Button Arial= (Button) layout.findViewById(R.id.Ar);
            Arial.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //  Toast.makeText(Chapters.this, "night", Toast.LENGTH_SHORT).show();
                    Typeface face = Typeface.createFromAsset(getAssets(),
                            "fonts/Arial.otf");
                    contentText.setTypeface(face);
                }
            });

            Button Georgina= (Button) layout.findViewById(R.id.Ge);
            Georgina.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //  Toast.makeText(Chapters.this, "night", Toast.LENGTH_SHORT).show();
                    Typeface face = Typeface.createFromAsset(getAssets(),
                            "fonts/Georgina.ttf");
                    contentText.setTypeface(face);
                }
            });


            Button Helvetica= (Button) layout.findViewById(R.id.He);
            Helvetica.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //  Toast.makeText(Chapters.this, "night", Toast.LENGTH_SHORT).show();
                    Typeface face = Typeface.createFromAsset(getAssets(),
                            "fonts/Helvetica.ttf");
                    contentText.setTypeface(face);
                }
            });

            Button Serif= (Button) layout.findViewById(R.id.Se);
            Serif.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //  Toast.makeText(Chapters.this, "night", Toast.LENGTH_SHORT).show();
                    Typeface face = Typeface.createFromAsset(getAssets(),
                            "fonts/Serif.ttf");
                    contentText.setTypeface(face);
                }
            });

            Button Verdana= (Button) layout.findViewById(R.id.Ve);
            Verdana.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //  Toast.makeText(Chapters.this, "night", Toast.LENGTH_SHORT).show();
                    Typeface face = Typeface.createFromAsset(getAssets(),
                            "fonts/Verdana.ttf");
                    contentText.setTypeface(face);
                }
            });



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveTip(){

        String roles = spinnerTips.getText().toString();

        if(roles == "Target Racing"){
            rolesId = 26;

        }
        else if(roles == "Greyhound Maestro"){
            rolesId = 22;
        }
        else if(roles == "Early Bird Racing Club"){
            rolesId = 21;

        }
        else if(roles == "GateSpeed"){
            rolesId = 25;

        }
        else if(roles == "Blog"){
            rolesId = 10;

        }

        //array
//        JSONArray arr = new JSONArray();
//        arr.put("25");
//        arr.put("25");
        spinner.setVisibility(View.VISIBLE);
        //object
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("title", titleText.getText().toString());
            jsonObject.put("content", contentText.getText().toString());
            jsonObject.put("status", "publish");
            jsonObject.put("categories", rolesId);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        AndroidNetworking.post("https://bethub.pro/wp-json/wp/v2/posts")
                //.addBodyParameter(user)
                .addHeaders("Authorization", Credentials.basic("neil", "neil0909"))
                .addJSONObjectBody(jsonObject)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        spinner.setVisibility(View.GONE);
                        //Log.d("tag4","response is : " + response);
                        //Toast.makeText(getBaseContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        JSON json = new JSON(response);

                        String status = json.key("status").stringValue();

                        Alert.showAlertWithAction(TipSubmit.this,"Successfully Created",MainActivity.class);
                        //Log.d("tag1","status is : " + json.key("status").stringValue());

                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
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
                    hideSoftKeyboard(TipSubmit.this);
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
