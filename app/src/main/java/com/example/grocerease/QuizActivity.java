package com.example.grocerease;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class QuizActivity extends AppCompatActivity {
    private String bloodPressure;
    private String bloodSugarLevels;
    private String highCholesterol;
    private String weightGoals;
    private String name;
    private String sex;
    private int height;
    private int weight;
    private Date birthday;

    Button quizSubmitButton;

    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    Calendar cal = Calendar.getInstance();

    private Boolean isDateChanged = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        //get blood pressure from RadioGroup
        RadioGroup bloodPressureGroup = findViewById(R.id.bloodPressureGroup);

        
        //get blood sugar from RadioGroup
        RadioGroup bloodSugarLevelsGroup = findViewById(R.id.bloodSugarLevelsGroup);

        //get cholesterol from RadioGroup
        RadioGroup HighCholesterolGroup = findViewById(R.id.HighCholesterolGroup);
        //get WeightGoals from RadioGroup
        RadioGroup WeightGoalsGroup = findViewById(R.id.weightGoalsGroup);

        EditText userObjectName = findViewById(R.id.UserObjectName);

        RadioGroup userSex = findViewById(R.id.UserSex);


        EditText userHeight = findViewById(R.id.editTextHeight);
        EditText userWeight = findViewById(R.id.editTextWeight);
        DatePicker userBirthday = findViewById(R.id.birthdayPicker);
        quizSubmitButton = findViewById(R.id.quizSubmit);

        //Initialize a database object instance
        //Initialize a reference to the database
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        //checking for valid SDK version - requirement of the listener
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            userBirthday.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    isDateChanged = true;
                }
            });
        }
        quizSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int bloodPressureID = bloodPressureGroup.getCheckedRadioButtonId();
                RadioButton radioBloodPressureButton = findViewById(bloodPressureID);
                int bloodSugarID = bloodSugarLevelsGroup.getCheckedRadioButtonId();
                RadioButton radioBloodSugarButton = findViewById(bloodSugarID);
                int highCholesterolID = HighCholesterolGroup.getCheckedRadioButtonId();
                RadioButton radioHighCholesterolButton = findViewById(highCholesterolID);
                int WeightGoalsID = WeightGoalsGroup.getCheckedRadioButtonId();
                RadioButton radioWeightGoalsButton = findViewById(WeightGoalsID);
                int userSexID = userSex.getCheckedRadioButtonId();
                RadioButton radioButtonUserSex = findViewById(userSexID);
                Log.d("radiobutton5", "onCreate: " + radioButtonUserSex);
                if(radioBloodPressureButton == null
                        || radioBloodSugarButton == null
                        || radioHighCholesterolButton == null
                        || radioWeightGoalsButton == null
                        || radioButtonUserSex == null
                        || userHeight.getText().toString() == ""
                        || userWeight.getText().toString() == ""
                        || userObjectName == null
                        || isDateChanged == false){
                    Toast.makeText(QuizActivity.this, "Please ensure that all fields have been filled", Toast.LENGTH_LONG).show();
                }
                else{
                    //setting all attributes of the UserPreference Object
                    bloodPressure = radioBloodPressureButton.getText().toString();
                    Log.d("radiobuttonbloodpressure", "onCreate: " + radioBloodPressureButton);
                    bloodSugarLevels = radioBloodSugarButton.getText().toString();
                    highCholesterol = radioHighCholesterolButton.getText().toString();
                    weightGoals = radioWeightGoalsButton.getText().toString();
                    sex = radioButtonUserSex.getText().toString();
                    height = Integer.parseInt(userHeight.getText().toString());
                    weight = Integer.parseInt(userWeight.getText().toString());
                    name = userObjectName.getText().toString();
                    cal.set(Calendar.YEAR, userBirthday.getYear() - 1900);
                    cal.set(Calendar.MONTH, userBirthday.getMonth());
                    cal.set(Calendar.DAY_OF_MONTH, userBirthday.getDayOfMonth());
                    birthday = cal.getTime();
                    //instantiating new UserPreferencesObject
                    UserPreferencesObject UserObject = new UserPreferencesObject(bloodPressure,bloodSugarLevels,highCholesterol,weightGoals,name,sex,height,weight,birthday);
                    //adding it to the DataBase
                    databaseReference.child("Users").child("marcus").child("Preferences").setValue(UserObject);
                }
            }
        });


    }



}