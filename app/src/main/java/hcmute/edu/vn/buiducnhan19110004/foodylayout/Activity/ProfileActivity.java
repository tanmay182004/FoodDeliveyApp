package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.UserDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.UserDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class ProfileActivity extends AppCompatActivity {
    private EditText profilePhone, profileName, profileEmail, profilePassword;
    private TextView updateProfileBtn;
    private Button logOutBtn, deleteProfileBtn;
    private ImageView goBackBtn;

    //Database classes
    private FoodyDBHelper foodyDBHelper;
    private UserDB userDB;

    //Intent contain info of current user and last activity
    Intent intent;

    private String phone, email, fullName, password;
    @Override
    public void onBackPressed() {
        startActivity( new Intent(this, MainActivity.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        intent = getIntent();
        foodyDBHelper = new FoodyDBHelper(this);
        userDB = new UserDB(foodyDBHelper);

        deleteProfileBtn = findViewById(R.id.DeleteProfileButton);
        updateProfileBtn = findViewById(R.id.profileUpdateTxt);
        profilePhone = findViewById(R.id.profilePhoneEditText);
        profileName = findViewById(R.id.profileNameEditText);
        profileEmail = findViewById(R.id.profileEmailEditText);
        profilePassword = findViewById(R.id.profilePasswordEditText);
        logOutBtn = findViewById(R.id.logOutButton);
        goBackBtn = findViewById(R.id.goBackImageView);

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone = profilePhone.getText().toString();
                fullName = profileName.getText().toString();
                email = profileEmail.getText().toString();
                password = profilePassword.getText().toString();

                UserDomain updatedUser = new UserDomain(CurrentUser.getUser_id(), fullName, email, phone, password);
                userDB.UpdateCurrentUser(updatedUser);
                CurrentUser.SetCurrentUser(updatedUser);
                Toast.makeText(ProfileActivity.this, "Update User successfully", Toast.LENGTH_SHORT).show();
            }
        });

        deleteProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteProfile();
            }
        });

        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });

        LoadProfileData();
    }

    private void LoadProfileData() {
        phone = intent.getStringExtra("profile_phone");
        fullName = intent.getStringExtra("profile_name");
        email = intent.getStringExtra("profile_email");
        password = intent.getStringExtra("profile_password");

        profilePhone.setText(phone);
        profileName.setText(fullName);
        profileEmail.setText(email);
        profilePassword.setText(password);
    }

    private void DeleteProfile() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Are you sure want to delete your account?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                userDB.DeleteUserById(CurrentUser.getUser_id());
            }
        });
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        alertDialog.show();
    }
}