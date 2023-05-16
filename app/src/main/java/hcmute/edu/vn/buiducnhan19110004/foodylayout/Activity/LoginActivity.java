package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.UserDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.UserDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class LoginActivity extends AppCompatActivity {
    private EditText emailLogin, passLogin;
    private CheckBox rememberMeCheckbox;
    private TextView forgotPassTxt, registerDirectTxt;
    private Button loginBtn;

    private SharedPreferences loginSharedPreferences;

    private String email;
    private String pass;

    //db classes
    private FoodyDBHelper foodyDBHelper = new FoodyDBHelper(LoginActivity.this);
    private UserDB userDb = new UserDB(foodyDBHelper);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginSharedPreferences = getSharedPreferences("loginData", MODE_PRIVATE);

        emailLogin = findViewById(R.id.emailLoginEditText);
        passLogin = findViewById(R.id.passwordLoginEditText);
        rememberMeCheckbox = findViewById(R.id.rememberMeCheckbox);
        forgotPassTxt = findViewById(R.id.forgotPasswordTextView);
        registerDirectTxt = findViewById(R.id.registerDirectTextView);
        loginBtn = findViewById(R.id.loginButton);

        emailLogin.setText(loginSharedPreferences.getString("email", ""));
        passLogin.setText(loginSharedPreferences.getString("pass", ""));
        rememberMeCheckbox.setChecked(loginSharedPreferences.getBoolean("rememberMe", false));

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        registerDirectTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        forgotPassTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //write code for a user to get back password if they forgot
            }
        });
    }

    public void Login() {
        email = emailLogin.getText().toString().trim();
        pass = passLogin.getText().toString().trim();

        if(userDb.CheckLoginUser(email, pass)) {

            if(rememberMeCheckbox.isChecked()) {
                SharedPreferences.Editor editor = loginSharedPreferences.edit();
                editor.putString("email", email);
                editor.putString("pass", pass);
                editor.putBoolean("rememberMe", true);
                editor.commit();
            }
            else {
                SharedPreferences.Editor editor = loginSharedPreferences.edit();
                editor.remove("email");
                editor.remove("pass");
                editor.remove("rememberMe");
                editor.commit();
            }
            UserDomain user = userDb.SelectUserByEmail(email);
            CurrentUser.SetCurrentUser(user);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
        else {
            //write code to return to login page and announce that the email or password is incorrect
            Toast toast = Toast.makeText(LoginActivity.this, "Incorrect username or password!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}