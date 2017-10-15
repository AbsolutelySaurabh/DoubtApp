package com.appsomniac.doubtapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.appsomniac.doubtapp.R;
import com.appsomniac.doubtapp.base.MainActivity;
import com.appsomniac.doubtapp.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class IntroActivity extends AppCompatActivity {

    String[] spinnerClassValues = { "Select Class", "Class 1st", "Class 2nd", "Class 3rd", "Class 4th", "Class 5th", "Class 6th", "Class 7th", "Class 8th",
    "Class 9th", "Class 10th", "Class 11th", "Class 12th", "Class 12+"};

    String[] spinnerStreamValues = {"Select Stream", "Science", "Commerce with Maths", "Commerce with IP", "Arts", "Other"};
    String[] spinnerBoardValues = {"Select Board", "CBSE", "ICSE", "UP Board", "Bihar Board", "Other"};
    String[] spinnerExamValues = {"Select Exam", "JEE Mains", "JEE Advance", "IP University", "UPSC", "Bank P.O."};

    Spinner spinner_class, spinner_board, spinner_stream, spinner_exam;
    TextView proceed_text;

    //add Firebase Database stuff
    private FirebaseDatabase mFirebaseDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        proceed_text = (TextView) findViewById(R.id.text_proceed);

        // Spinner element
        spinner_class = (Spinner) findViewById(R.id.spinner_class);
        spinner_board = (Spinner) findViewById(R.id.spinner_board);
        spinner_stream = (Spinner) findViewById(R.id.spinner_stream);
        spinner_exam = (Spinner) findViewById(R.id.spinner_exam);

        // attaching data adapter to spinner
        spinner_class.setAdapter(new MyAdapter(this, R.layout.spinner_class, spinnerClassValues, 0));
        spinner_board.setAdapter(new MyAdapter(this, R.layout.spinner_board, spinnerBoardValues, 1));
        spinner_stream.setAdapter(new MyAdapter(this, R.layout.spinner_stream, spinnerStreamValues, 2));
        spinner_exam.setAdapter(new MyAdapter(this, R.layout.spinner_exam, spinnerExamValues, 3));

    }

    public void proceedNowCondition(){

        if(!(spinner_class.getSelectedItem().toString().equals("Select Class")) && !(spinner_board.getSelectedItem().toString().equals("Select Board"))
                && !(spinner_stream.getSelectedItem().toString().equals("Select Stream")) ){

            Toast.makeText(this, "You can proceed now", Toast.LENGTH_SHORT).show();

            proceed_text.setEnabled(true);

            proceed_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    SharedPreferences.Editor editor = getSharedPreferences("user_data", MODE_PRIVATE).edit();
                    editor.putString("class", spinner_class.getSelectedItem().toString());
                    editor.putString("board", spinner_board.getSelectedItem().toString());
                    editor.putString("stream", spinner_stream.getSelectedItem().toString());
                    editor.putString("exam", spinner_exam.getSelectedItem().toString());
                    editor.apply();

                    addInfoToDatabase();

                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();

                }
            });

        }else{

            proceed_text.setEnabled(true);

            proceed_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getApplicationContext(), "Complete info!", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void addInfoToDatabase(){

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        FirebaseUser user = mFirebaseAuth.getCurrentUser();
        userID = user.getUid();

        User userInformation = new User(user.getEmail(),user.getDisplayName(),user.getPhoneNumber(),
                spinner_stream.getSelectedItem().toString(), spinner_class.getSelectedItem().toString(),
                spinner_exam.getSelectedItem().toString(), spinner_board.getSelectedItem().toString());

        myRef.child("users").child(userID).setValue(userInformation);

    }

    public class MyAdapter extends ArrayAdapter<String> {

        private int spinnerPosition;
        public MyAdapter(Context ctx, int txtViewResourceId, String[] objects, int spinnerPosition) {
            super(ctx, txtViewResourceId, objects);
            this.spinnerPosition = spinnerPosition;
        }

        @Override
        public View getDropDownView(int position, View cnvtView, ViewGroup prnt) {

            //this is for the layout inside spinner
            return getCustomView(position, cnvtView, prnt);

        }

        @Override
        public View getView(int position, View cnvtView, ViewGroup parent) {

            //after selection action here
            //this is the spinner view after selection and before selection also by default values
            View mySpinner = null;
            if(spinnerPosition == 0) {
                mySpinner = getLayoutInflater().inflate(R.layout.spinner_class, parent, false);
                TextView main_text = (TextView) mySpinner.findViewById(R.id.spinner_category);
                main_text.setText(spinnerClassValues[position]);
                proceedNowCondition();

            }else
                if(spinnerPosition == 1){
                    mySpinner = getLayoutInflater().inflate(R.layout.spinner_board, parent, false);
                    TextView main_text = (TextView) mySpinner.findViewById(R.id.spinner_category);
                    main_text.setText(spinnerBoardValues[position]);
                    proceedNowCondition();

                }else
                    if(spinnerPosition == 2){

                        mySpinner = getLayoutInflater().inflate(R.layout.spinner_stream, parent, false);
                        TextView main_text = (TextView) mySpinner.findViewById(R.id.spinner_category);
                        main_text.setText(spinnerStreamValues[position]);
                        proceedNowCondition();

                    }else
                        if(spinnerPosition == 3){

                            mySpinner = getLayoutInflater().inflate(R.layout.spinner_exam, parent, false);
                            TextView main_text = (TextView) mySpinner.findViewById(R.id.spinner_category);
                            main_text.setText(spinnerExamValues[position]);
                            proceedNowCondition();

                        }

            return mySpinner;

        }


        public View getCustomView(int position, View convertView, ViewGroup parent) {

            //this is the layout inside spinner
            LayoutInflater inflater = getLayoutInflater();

            View mySpinner = inflater.inflate(R.layout.spinner_class_row, parent, false);

            if(spinnerPosition == 0) {

                TextView main_text = (TextView) mySpinner.findViewById(R.id.spinner_class_value);
                main_text.setText(spinnerClassValues[position]);
            }else
                if(spinnerPosition == 1){
                    TextView main_text = (TextView) mySpinner.findViewById(R.id.spinner_class_value);
                    main_text.setText(spinnerBoardValues[position]);

                }else
                    if(spinnerPosition == 2){
                        TextView main_text = (TextView) mySpinner.findViewById(R.id.spinner_class_value);
                        main_text.setText(spinnerStreamValues[position]);

                    }else
                        if(spinnerPosition == 3){

                            TextView main_text = (TextView) mySpinner.findViewById(R.id.spinner_class_value);
                            main_text.setText(spinnerExamValues[position]);

                    }

            return mySpinner;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_intro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.skip:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
