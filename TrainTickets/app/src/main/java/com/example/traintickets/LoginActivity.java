package com.example.traintickets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText emailField;
    private EditText passwordField;
    private Button loginButton;
    private Button goToRegisterButton;
    private DatabaseConnector databaseConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initialiseComponents();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString();

                if (login(email, password)) {
                    String []columns = databaseConnector.returnRecord(email);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("EMAIL", columns[0]);
                    intent.putExtra("FIRST_NAME", columns[1]);
                    intent.putExtra("LAST_NAME", columns[2]);
                    startActivity(intent);
                }
            }
        });

        goToRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initialiseComponents() {
        emailField = findViewById(R.id.email_login_field);
        passwordField = findViewById(R.id.password_login_field);
        loginButton = findViewById(R.id.login_button);
        goToRegisterButton = findViewById(R.id.go_to_register_button);
        databaseConnector = new DatabaseConnector(this);
    }

    private boolean login(String email, String password) {
        if (databaseConnector.findRecord(email, password)) {
            Toast.makeText(getApplicationContext(), "Welcome!", Toast.LENGTH_SHORT).show();
            return true;
        }
        Toast.makeText(getApplicationContext(), "Wrong email or password!", Toast.LENGTH_SHORT).show();
        return false;
    }
}

