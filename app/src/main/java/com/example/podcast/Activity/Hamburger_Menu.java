package com.example.podcast.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.podcast.ImageConverter;
import com.example.podcast.Model.User;
import com.example.podcast.R;
import com.squareup.picasso.Picasso;

public class Hamburger_Menu extends AppCompatActivity {
    private ImageView imgCloseBtn;
    private User user;
    private TextView tvHelloUser;
    private ImageView imgAvatarUser;
    private Button btnLogout;
    private TextView tvFollows;
    private TextView getTvFollowsNumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hamburger_menu);

        Init();
        SetUI();
        imgCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iNewActivity = new Intent(Hamburger_Menu.this, Main.class);
                startActivity(iNewActivity);
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iNewActivity = new Intent(Hamburger_Menu.this, Login.class);
                startActivity(iNewActivity);
                overridePendingTransition(R.anim.slide_in_from_left, R.anim.slide_out_to_right);
            }
        });
    }

    private void SetUI() {
        tvHelloUser.setText("Hello \n" + user.getUserName());
        /*getTvFollowsNumber.setText(user.get);*/
        /*Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),R.drawable.avt_user);
        Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);

        ImageView circularImageView = (ImageView) findViewById(R.id.img_Avatar_User);
        circularImageView.setImageBitmap(circularBitmap);*/

        Picasso.with(this).load(user.getUserAvatar()).into(imgAvatarUser);
    }

    private void Init() {
        user = (User) getIntent().getParcelableExtra("User_Login");
        imgCloseBtn = (ImageView) findViewById(R.id.img_Closebtn);
        tvHelloUser = (TextView) findViewById(R.id.tv_Hello_User);
        imgAvatarUser = (ImageView) findViewById(R.id.img_Avatar_User);
        btnLogout = (Button) findViewById(R.id.btn_logout);
        tvFollows = (TextView) findViewById(R.id.tv_Follows);
        getTvFollowsNumber = (TextView) findViewById(R.id.tv_Follows_Number);

    }
}
