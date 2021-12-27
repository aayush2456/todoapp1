package com.tbcmad.todoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import static com.basgeekball.awesomevalidation.ValidationStyle.BASIC;


public class LoginActivity extends AppCompatActivity {
    Button btnLogin;
    EditText user;
    EditText password;
    CheckBox save;
    Boolean authentication;

    EditTodoFragment td = new EditTodoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = findViewById(R.id.login_activity_btn_login);
        user = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        save = findViewById(R.id.checkBox);

        String regexPassword = ".{8,}";
        AwesomeValidation mAwesomeValidation = new AwesomeValidation(BASIC);
        mAwesomeValidation.addValidation(this, R.id.editTextTextEmailAddress, Patterns.EMAIL_ADDRESS, R.string.email_error);
        mAwesomeValidation.addValidation(this, R.id.editTextTextPassword, RegexTemplate.NOT_EMPTY, R.string.password_error);
        mAwesomeValidation.addValidation(this, R.id.editTextTextPassword, regexPassword, R.string.invalid_password);

        SharedPreferences preference = getApplicationContext().getSharedPreferences("todo_pref", 0);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAwesomeValidation.validate()) {
                    saveLogin();

                }else{
                    DialogForInvalid();
                }
            }

        });
        authentication= preference.getBoolean("authentication",true);
        if(authentication==true){

            user.setText(preference.getString("user",null));
            password.setText(preference.getString("password",null));




        }



    }
    private  void saveLogin(){

        String username = user.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        if (username.equals("todoapp@tbc.com") && userPassword.equals("todoapp123")) {

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();

            if(save.isChecked()){
                SharedPreferences preference = getApplicationContext().getSharedPreferences("todo_pref", 0);
                SharedPreferences.Editor editor = preference.edit();
                editor.putBoolean("authentication", true);
                editor.putString("user",username);
                editor.putString("password",userPassword);
                editor.apply();
                Toast.makeText(LoginActivity.this, "User Details Saved", Toast.LENGTH_SHORT).show();

            }else if(!save.isChecked()){
                    SharedPreferences preference = getApplicationContext().getSharedPreferences("todo_pref", 0);
                    SharedPreferences.Editor editor = preference.edit();
                    editor.putBoolean("authentication", false);
                    //editor.putString("user",username);
                    //editor.putString("password",userPassword);
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "User Details not Saved", Toast.LENGTH_SHORT).show();

            }


        }else{
            DialogForInvalid();

        }

    }
    private void DialogForInvalid(){
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setTitle("User is invalid");
        alertDialog.setMessage("Please check Username or Password");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Please Try Again",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }
    private void DialogForWelcome(){
        String username = user.getText().toString().trim();
        String userPassword = password.getText().toString().trim();
        AlertDialog alertDialog = new AlertDialog.Builder(LoginActivity.this).create();
        alertDialog.setTitle("Welcome "+ username);
        alertDialog.setMessage("Create Your Basic Task");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Create",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        //  alertDialog.show();

    }



}