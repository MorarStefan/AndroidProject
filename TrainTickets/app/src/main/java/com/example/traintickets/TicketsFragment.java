package com.example.traintickets;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.Calendar;

public class TicketsFragment extends Fragment implements DatePickerDialog.OnDateSetListener{

    private EditText fromTownField;
    private EditText toTownField;
    private String date;
    private Button chooseDateButton;
    private Button searchTicketsButton;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initialiseComponents();

        chooseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicketDialog();
            }
        });

        searchTicketsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fromTown = fromTownField.getText().toString().trim();
                String toTown = toTownField.getText().toString();

                if (goToList(fromTown, toTown)) {
                    Intent intent = new Intent(getActivity(), ListingActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void initialiseComponents() {
        fromTownField = getView().findViewById(R.id.from_town_field);
        toTownField = getView().findViewById(R.id.to_town_field);
        date = null;
        chooseDateButton = getView().findViewById(R.id.choose_date_button);
        searchTicketsButton = getView().findViewById(R.id.search_ticket_button);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tickets, container, false);
    }

    private void showDatePicketDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        int realMonth = month + 1;
        date = dayOfMonth + "/" + realMonth + "/" + year;
        String message = "Date: " + dayOfMonth + "/" + realMonth + "/" + year;
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    private boolean goToList(String fromTown, String toTown) {
        if(fromTown != null && toTown != null && date != null) {
            if(fromTown.length() > 0 && toTown.length() > 0) {
                Toast.makeText(getActivity(), "Results are loading...", Toast.LENGTH_SHORT).show();
                return true;
            }
            Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_SHORT).show();
            return false;
        }
        Toast.makeText(getActivity(), "Something went wrong...", Toast.LENGTH_SHORT).show();
        return false;

    }
}
