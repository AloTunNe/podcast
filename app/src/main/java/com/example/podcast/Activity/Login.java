package com.example.podcast.Activity;

import static android.content.ContentValues.TAG;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.podcast.Model.User;
import com.example.podcast.R;
import com.example.podcast.Service.APIService;
import com.example.podcast.Service.DataService;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    Button btnLogin, btnLoginFb;
    EditText edtInputEmail;
    EditText edtInputPassword;
    User user;
    Button btnRegister;
    CallbackManager callbackManager;
    LoginButton loginButton;

    ArrayList<User> userArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.btn_login_fb);
        loginButton.setReadPermissions("email");

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "======Facebook login success======");
                Log.d(TAG, "Facebook Access Token: " + loginResult.getAccessToken().getToken());
                Toast.makeText(Login.this, "Login Facebook success.", Toast.LENGTH_SHORT).show();


                getFbInfo();
            }

            @Override
            public void onCancel() {
                Toast.makeText(Login.this, "Login Facebook cancelled.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Log.e(TAG, "======Facebook login error======");
                Log.e(TAG, "Error: " + error.toString());
                Toast.makeText(Login.this, "Login Facebook error.", Toast.LENGTH_SHORT).show();
            }
        });



        Init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    checkData();
                }
                catch (Exception e)
                {
                    Log.d(TAG, "onClick: Error " + e.getMessage());
                    Toast.makeText(Login.this, "Connection error please try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(this,Login.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        finish();
    }
    private void Init() {
        btnLoginFb = (Button) findViewById(R.id.btn_login_fb);
        btnLogin = (Button) findViewById(R.id.btn_login);
        edtInputEmail  = (EditText) findViewById(R.id.email_input);
        edtInputPassword = (EditText) findViewById(R.id.password_input);
        btnRegister = (Button) findViewById(R.id.btn_register);

        userArrayList = new ArrayList<>();
    }

    private void checkData() {
        DataService dataService = APIService.getService();
        Call<List<User>> callback = dataService.getDataUser();
        callback.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                ArrayList<User> users = (ArrayList<User>) response.body();
                try {
                    userArrayList.clear();
                    for (int i = 0; i < users.size(); i++) {
                        userArrayList.add(users.get(i));
                    }
                    boolean check = false;
                    String inputEmail;
                    String inputPassword;
                    inputEmail = edtInputEmail.getText().toString();
                    inputPassword = edtInputPassword.getText().toString();
                    for(int i = 0; i < userArrayList.size(); i++) {
                        if (userArrayList.get(i).getUserEmail().compareTo(inputEmail) == 0 && userArrayList.get(i).getUserPassword().compareTo(inputPassword) == 0) {
                            check = true;
                            user = new User(users.get(i));
                            break;
                        }
                    }
                    if (check) {
                        Toast.makeText(getApplicationContext(), "Login success!", Toast.LENGTH_SHORT).show();
                        Intent iNewActivity = new Intent(Login.this, Main.class);
                        iNewActivity.putExtra("User_Login", user);
                        startActivity(iNewActivity);
                        overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Email or Password Incorrect!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception ex) {
                    Log.d(TAG, "onResponse: " + ex.getMessage());
                    checkData();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void getFbInfo() {
        if (AccessToken.getCurrentAccessToken() != null) {
            GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
                    new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(final JSONObject me, GraphResponse response) {
                            if (me != null) {
                                Log.i("Login: ", me.optString("name"));
                                Log.i("ID: ", me.optString("id"));

                                Toast.makeText(Login.this, "Name: " + me.optString("name"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(Login.this, "ID: " + me.optString("id"), Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(), "Login success!", Toast.LENGTH_SHORT).show();
                                Intent iNewActivity = new Intent(Login.this, Main.class);
                                user = new User(me.optString("id"), me.optString("name"), null, null, null, null, null);
                                iNewActivity.putExtra("User_Login", user);
                                startActivity(iNewActivity);
                                overridePendingTransition(R.anim.slide_in_from_right, R.anim.slide_out_to_left);
                            }
                        }
                    });

            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link");
            request.setParameters(parameters);
            request.executeAsync();
        }
    }
}
