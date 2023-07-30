package com.example.educationalgorithms;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.educationalgorithms.MarksActivity;
import com.example.educationalgorithms.PHPRequest;
import com.example.educationalgorithms.R;
import com.example.educationalgorithms.RequestHandler;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private Button registerButton;

    private PHPRequest phpRequest;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.editTextTextPersonName);
        passwordEditText = findViewById(R.id.editTextPassword);
        confirmPasswordEditText = findViewById(R.id.editTextConfirmPassword);
        registerButton = findViewById(R.id.button2);

        phpRequest = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2575455/");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInput()) {
                    registerUser();
                }
            }
        });
    }

    private boolean validateInput() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void registerUser() {
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString();

        ContentValues params = new ContentValues();
        params.put("username", username);
        params.put("password", password);
        user = username;

        phpRequest.doRequest(RegisterActivity.this, "register.php", params, new RequestHandler() {
            @Override
            public void processResponse(String response) {
                System.out.println(response);
                if (response.trim().equals("success")) {
                    Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, MarksActivity.class);
                    intent.putExtra("user",user);
                    startActivity(intent);}
                    else if(response.trim().equals("duplicate")){
                    Toast.makeText(RegisterActivity.this, "Username already exits. Choose another one.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void ifHaveAccount(View view){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}