package com.egco428.a23264;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private CommentDataSource dataSource;
    Button signinbtn;
    Button sugnupbtn;
    Button canclebtn;
    EditText username;
    EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle(Html.fromHtml("<font color=\"black\">" + "EGCO492: Assignment 2" + "</font>"));
        username = (EditText)findViewById(R.id.usertxt);
        password = (EditText)findViewById(R.id.passtxt);
        signinbtn = (Button)findViewById(R.id.signin);
        sugnupbtn = (Button)findViewById(R.id.signup);
        canclebtn = (Button)findViewById(R.id.cancle);
        dataSource = new CommentDataSource(this);
        dataSource.open();
    }
    public void GotoSignup(View view){
        startActivity(new Intent(MainActivity.this, SignupActivity.class));
    }
    public void cleartext(View view){
        username.setText("");
        password.setText("");
    }
    @Override
    protected void onResume(){
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        dataSource.close();
        super.onPause();
    }
    public void gosignin(View view){
        String usernametx = username.getText().toString();
        String passwordtx = password.getText().toString();
        String realpassword = dataSource.findpass(usernametx);

        if (realpassword.equals("-")) {

            Toast.makeText(getApplicationContext(), "Login fail",Toast.LENGTH_SHORT).show();

        }

        else {
            if (realpassword.equals(passwordtx)) {
                Toast.makeText(getApplicationContext(), "Login success",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MainActivity.this, Mainpage2Activity.class));
            }
            else {
                Toast.makeText(getApplicationContext(), "Login fail",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
