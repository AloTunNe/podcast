package com.example.podcast.Activity;

import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {
    Button btnLogin;
    EditText edtInputEmail;
    EditText edtInputPassword;
    User user;
    Button btnRegister;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Init();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
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

    private void Init() {
        btnLogin = (Button) findViewById(R.id.btn_login);
        edtInputEmail  = (EditText) findViewById(R.id.email_input);
        edtInputPassword = (EditText) findViewById(R.id.password_input);
        btnRegister = (Button) findViewById(R.id.btn_register);
    }

    private void checkData() {
        DataService dataService = APIService.getService();
        Call<List<User>> callback = dataService.getDataUser();
        callback.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                ArrayList<User> users = (ArrayList<User>) response.body();
                boolean check = false;
                String inputEmail;
                String inputPassword;
                inputEmail = edtInputEmail.getText().toString();
                inputPassword = edtInputPassword.getText().toString();
                    for(int i = 0; i < users.size(); i++) {
                    if (users.get(i).getUserEmail().compareTo(inputEmail) == 0 && users.get(i).getUserPassword().compareTo(inputPassword) == 0) {
                        check = true;
                        user = new User(users.get(i).getId(), users.get(i).getUserName(), users.get(i).getUserEmail(), users.get(i).getUserPassword(), users.get(i).getUserAvatar(), users.get(i).getUserOwner());
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
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
}
