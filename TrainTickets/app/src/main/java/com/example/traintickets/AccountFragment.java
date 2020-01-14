package com.example.traintickets;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountFragment extends Fragment {

    private EditText newEmailField;
    private Button changeEmailButton;
    private String oldEmail = "";
    private DatabaseConnector databaseConnector;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initialiseComponents();
        Toast.makeText(getContext(), oldEmail, Toast.LENGTH_SHORT).show();
        changeEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = newEmailField.getText().toString().trim();
                if(isValidEmail(newEmail)){
                    databaseConnector.updateEmail(oldEmail, newEmail);
                    ((MainActivity) getActivity()).getEmailField().setText(newEmail);
                    Toast.makeText(getContext(), "Email changed", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(), "Invalid email!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void initialiseComponents() {
        newEmailField = getView().findViewById(R.id.new_email_field);
        changeEmailButton = getView().findViewById(R.id.change_email_button);
        databaseConnector = new DatabaseConnector(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        oldEmail = getArguments().getString("EMAIL");
        return inflater.inflate(R.layout.fragment_account, container, false);
    }

    private boolean isValidEmail(String email) {
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }
}
