package com.example.traintickets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SelectedTrainActivity extends AppCompatActivity {

    private TextView informationField;
    private EditText numberField;
    private Button buyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_train);

        informationField = findViewById(R.id.information_field);
        numberField = findViewById(R.id.number_field);
        buyButton = findViewById(R.id.buy_button);

        informationField.setText(getIntent().getStringExtra("FROM") +
               " → " + getIntent().getStringExtra("TO") +
               "   " + getIntent().getStringExtra("INTERVAL"));

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = numberField.getText().toString().trim();

                if (isNumber(number)) {
                    Toast.makeText(getApplicationContext(), "Tickets bought!", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean isNumber(String number) {
        if(number.equals("0") || number.equals(null) || number.equals("")) {
            return false;
        }
        for (char c : number.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}
