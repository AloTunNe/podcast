package com.example.podcast.Activity;

import static android.content.ContentValues.TAG;

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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {
    Button btnSignIn;
    Button btnRegister;

    EditText edtEmail;
    EditText edtPassword;
    EditText edtConfirmPassword;

    ArrayList<User> userArrayList;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Init();
        getDataUser();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtPassword.getText().toString().compareTo(edtConfirmPassword.getText().toString()) == 0) {
                    String email = edtEmail.getText().toString();
                    boolean check = true;
                    for(int i = 0; i < userArrayList.size(); i++) {
                        if (userArrayList.get(i).getUserEmail().compareTo(email) == 0) {
                            check = false;
                            break;
                        }
                    }
                    if (check == false) Toast.makeText(getApplicationContext(), "Your Email has Created a Account! \n Please try a new Email!", Toast.LENGTH_LONG).show();
                    else {
                        addUser(edtEmail.getText().toString(), edtPassword.getText().toString(), "");
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Confirm Password Incorrect!", Toast.LENGTH_LONG).show();
                    Log.d("bbb: ", "Confirm Password Incorrect!");
                }
            }
        });
    }

    private void Init() {
        btnSignIn = (Button) findViewById(R.id.btn_sign_in);
        btnRegister = (Button) findViewById(R.id.btn_register);

        edtEmail = (EditText) findViewById(R.id.email_input);
        edtPassword = (EditText) findViewById(R.id.password_hint);
        edtConfirmPassword = (EditText) findViewById(R.id.password_confirm);

        userArrayList = new ArrayList<>();
    }
    private void getDataUser() {
        DataService dataService = APIService.getService();
        Call<List<User>> callback = dataService.getDataUser();
        callback.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                ArrayList<User> users = (ArrayList<User>) response.body();
                try {
                    userArrayList.clear();
                    for(int i = 0; i <users.size(); i++) {
                        userArrayList.add(users.get(i));
                    }
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: " + e.getMessage());
                    getDataUser();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });
    }
    private void addUser(String email, String password, String fbid) {
        DataService dataService = APIService.getService();
        Call<String> callback = dataService.AddUser(email, password, fbid);
        callback.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String str = (String) response.body().toString();
                try {
                    if (str.compareTo("Success") == 0) Toast.makeText(Register.this, "Your Account is Created, Please login again!", Toast.LENGTH_LONG).show();
                    else  Toast.makeText(Register.this, "Server not response, try again!", Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    Log.d(TAG, "onResponse: " + e.getMessage());
                    addUser(email, password, fbid);
                }

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
