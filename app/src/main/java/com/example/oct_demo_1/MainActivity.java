package com.example.oct_demo_1;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView imageView;
    ProgressBar progressBar;
    TextInputLayout textInputLayout, textPasswordLayout;
    TextView textView;
    EditText username, password;
    CardView login;
    private String u_name, pass;
    private String url = "https://scontent.fdac147-1.fna.fbcdn.net/v/t39.30808-6/300582007_755115559159904_7395749110668885523_n.png?_nc_cat=108&ccb=1-7&_nc_sid=efb6e6&_nc_ohc=PHoPrJqcwiIAX8C2Yj0&_nc_ht=scontent.fdac147-1.fna&oh=00_AfBeHciv-0SKQee25LXt6uc6DNRust-66X0rQewmwIcKvw&oe=658823E5";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setStatusBarColor(Color.TRANSPARENT);


        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.btn_login);
        username = findViewById(R.id.etUsername);
        password = findViewById(R.id.etPassword);
        login = findViewById(R.id.login_card);
        textInputLayout = findViewById(R.id.etUsernameLayout);
        textPasswordLayout = findViewById(R.id.etPasswordLayout);

        // Loading logo image from webs using URL method
        Glide.with(MainActivity.this).load(url).fitCenter().into(imageView);


        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // This method is called to notify you that the characters within `s` are about to be replaced with new text with a length of `before` characters starting at index `start`.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                textView.setText("Log In");
                textView.setTextColor(Color.parseColor("#FFFFFF"));
                // This method is called to notify you that somewhere within `s`, the characters `before` in length beginning at `start` are about to be replaced with new text with a length of `after` characters.
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // This method is called when the text within `editable` has been changed.
                if (editable.length() > 0) {
                    // If there is text in any of the EditTexts, set the TextView visibility to GONE
                    textInputLayout.setError("");
                    textPasswordLayout.setError("");
                } else {
                    // If all EditTexts are empty, set the TextView visibility to VISIBLE
                    //t_warning.setVisibility(View.VISIBLE);
                    textInputLayout.setError("Enter Username");
                    textPasswordLayout.setError("Enter Password");
                }
            }
        };

        username.addTextChangedListener(textWatcher);
        password.addTextChangedListener(textWatcher);

       /* login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText("Authorizing");
                progressBar.setVisibility(View.VISIBLE);

                u_name = username.getText().toString().trim();
                pass = password.getText().toString().trim();
                Log.d("username: ", u_name);
                Log.d("password: ", pass);

                if(u_name.isEmpty() || pass.isEmpty()) {
                    t_warning.setVisibility(View.VISIBLE);
                    t_warning.setText("Enter Username and Password");
                    //return;

                }
                    new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (u_name.equals("admin") && pass.equals("admin")) {
                                textView.setText("Authorized");
                                progressBar.setVisibility(View.INVISIBLE);
                                textView.setTextColor(Color.parseColor("#22bb33"));
                                finish();
                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            } else {
                                textView.setText("Unauthorized");
                                t_warning.setVisibility(View.VISIBLE);
                                t_warning.setText("Enter correct username and password");
                                progressBar.setVisibility(View.INVISIBLE);
                                textView.setTextColor(Color.parseColor("#bb2124"));
                                username.setText("");
                                password.setText("");
                                textView.setText("Log In");
                            }
                        }
                    }, 4000);
                }
        });*/
    }

    public void log_in(View view) {
        String user_name = username.getText().toString().trim();
        String user_pass = password.getText().toString().trim();



        if (!user_name.equals("") && !user_pass.equals("")){
            textView.setText("Authorizing");
            login.setCardBackgroundColor(Color.parseColor("#9EA13C"));
            progressBar.setVisibility(View.VISIBLE);

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (user_name.equals("admin") && user_pass.equals("admin")) {
                        textView.setText("Authorized");
                        progressBar.setVisibility(View.INVISIBLE);
                        login.setCardBackgroundColor(Color.parseColor("#22bb33"));
                        //textView.setTextColor(Color.parseColor("#22bb33"));
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        //finish();

                    } else {
                        progressBar.setVisibility(View.INVISIBLE);
                        textView.setText("Unauthorized");
                        login.setCardBackgroundColor(Color.parseColor("#bb2124"));
                        username.setText("");
                        password.setText("");
                    }
                }
            }, 4000);
        }
        else {
            if (user_name.equals("") || user_pass.equals("")) {
                if (user_name.equals("")) {
                    textInputLayout.setError("Enter Username");
                } else {
                    textInputLayout.setError(null);  // Clear the error if username is not empty
                }

                if (user_pass.equals("")) {
                    textPasswordLayout.setError("Enter Password");
                } else {
                    textPasswordLayout.setError(null);  // Clear the error if password is not empty
                }
            }

        }

    }
}