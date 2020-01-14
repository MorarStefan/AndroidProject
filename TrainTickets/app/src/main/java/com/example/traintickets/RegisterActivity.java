package com.example.traintickets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText firstNameField;
    private EditText lastNameField;
    private EditText emailField;
    private EditText passwordField;
    private EditText confirmPasswordField;
    private Button registerButton;
    private DatabaseConnector databaseConnector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initialiseComponents();

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstNameField.getText().toString().trim();
                String lastName = lastNameField.getText().toString().trim();
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString();
                String confirmPassword = confirmPasswordField.getText().toString();

                removeErrors();
                setErrors(firstName, lastName, email, password, confirmPassword);
                if (createAccount(firstName, lastName, email, password, confirmPassword)) {
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    intent.putExtra("EMAIL", email);
                    intent.putExtra("FIRST_NAME", firstName);
                    intent.putExtra("LAST_NAME", lastName);
                    startActivity(intent);
                }
            }
        });
    }

    private void initialiseComponents() {
        firstNameField = findViewById(R.id.first_name_field);
        lastNameField = findViewById(R.id.last_name_field);
        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_field);
        confirmPasswordField = findViewById(R.id.confirm_password_field);
        registerButton = findViewById(R.id.register_button);
        databaseConnector = new DatabaseConnector(this);
    }

    private boolean validate(String firstName, String lastName, String email, String password, String confirmPassword) {
        if (!isValidName(firstName) || !isValidName(lastName) || !isValidEmail(email) || !isValidPassword(password)) {
            return false;
        }
        if (!password.equals(confirmPassword)) {
            return false;
        }
        return true;
    }

    private void removeErrors() {
        firstNameField.setError(null);
        lastNameField.setError(null);
        emailField.setError(null);
        passwordField.setError(null);
        confirmPasswordField.setError(null);
    }

    private void setErrors(String firstName, String lastName, String email, String password, String confirmPassword) {
        if (!isValidName(firstName)) {
            firstNameField.setError("Invalid first name!");
        }
        if (!isValidName(lastName)) {
            lastNameField.setError("Invalid last name!");
        }
        if (!isValidEmail(email)) {
            emailField.setError("Invalid email!");
        }
        if (!isValidPassword(password)) {
            passwordField.setError("Invalid password name!");
        }
        if (!password.equals(confirmPassword)) {
            confirmPasswordField.setError("Different password!");
        }
    }

    private boolean isValidName(String name) {
        Pattern VALID_NAME_REGEX = Pattern.compile("^[a-zA-Z\\\\s]+");
        Matcher matcher = VALID_NAME_REGEX.matcher(name);
        return matcher.find();
    }

    private boolean isValidEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    private boolean isValidPassword(String password) {
        if (password.length() >= 4) {
            return true;
        }
        return false;
    }

    private boolean createAccount(String firstName, String lastName, String email, String password, String confirmPassword) {
        if (validate(firstName, lastName, email, password, confirmPassword)) {
            if (!databaseConnector.findEmail(email)) {
                if (databaseConnector.insert(email, firstName, lastName, password)) {
                    Toast.makeText(getApplicationContext(), "The account was created!", Toast.LENGTH_SHORT).show();
                    return true;
                }
                Toast.makeText(getApplicationContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                return true;
            }
            Toast.makeText(getApplicationContext(), "This email was already used!", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(getApplicationContext(), "Check the errors!", Toast.LENGTH_SHORT).show();
        return false;
    }
}
