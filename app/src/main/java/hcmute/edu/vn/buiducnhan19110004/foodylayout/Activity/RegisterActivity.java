package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.UserDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.UserDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class RegisterActivity extends AppCompatActivity {
    private EditText fullnameEditText, emailEditText, phoneEditText, passEditText, confirmPassEditText;
    private TextView loginDirectTxt;
    private Button registerBtn;


    private String fullname;
    private String email;
    private String phone;
    private String password;

    private FoodyDBHelper foodyDBHelper = new FoodyDBHelper(RegisterActivity.this);
    private UserDB userDb = new UserDB(foodyDBHelper);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fullnameEditText = findViewById(R.id.fullnameRegisterEditText);
        emailEditText = findViewById(R.id.emailRegisterEditText);
        phoneEditText = findViewById(R.id.phoneRegisterEditText);
        passEditText = findViewById(R.id.passwordRegisterEditText);
        confirmPassEditText = findViewById(R.id.confirmPasswordRegisterEditText);

        registerBtn = findViewById(R.id.registerBtn);
        loginDirectTxt = findViewById(R.id.directLoginTextView);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterUser();
            }
        });

        loginDirectTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });
    }

    public void RegisterUser() {
        if(fullnameEditText.getText().toString().trim().equals("") ||
                emailEditText.getText().toString().trim().equals("") ||
                phoneEditText.getText().toString().trim().equals("") ||
                passEditText.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Invalid input!", Toast.LENGTH_SHORT).show();
        }
        else if(!passEditText.getText().toString().trim().equals(confirmPassEditText.getText().toString().trim())) {
            Toast.makeText(this, "Confirm password doesn't match!", Toast.LENGTH_SHORT).show();
        }
        else {
            fullname = fullnameEditText.getText().toString().trim();
            email = emailEditText.getText().toString().trim();
            phone = phoneEditText.getText().toString().trim();
            password = passEditText.getText().toString().trim();
            UserDomain insertUser = new UserDomain(0, fullname, email, phone, password);
            if(userDb.CheckDuplicateEmail(insertUser)) {
                Toast toast = Toast.makeText(RegisterActivity.this, "Email already existed", Toast.LENGTH_SHORT);
                toast.show();
            }
            else {
                userDb.InsertUser(insertUser);
                userDb.SelectALlUsers();
                Toast toast = Toast.makeText(RegisterActivity.this, "Register successfully for user " + insertUser.getEmail(), Toast.LENGTH_SHORT);
                toast.show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        }
    }
}