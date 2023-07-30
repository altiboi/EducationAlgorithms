package com.example.educationalgorithms;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MarksActivity extends AppCompatActivity {
    private EditText[] subjectMarks;
    private int apsWits;
    private int apsUKZN;
    private int apsDef;
    private int apsUWC;
    private boolean allSelected = true;
    private String username;

    private EditText usernameEditText, hlmEditText, falmEditText, mathEditText,
            mmEditText, e1EditText, e1mEditText, e2EditText, e2mEditText, e3EditText, e3mEditText, loEditText;
    private String selectedMath;
    private String selectedFirstElective;
    private String selectedSecondElective;
    private String selectedThirdElective;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks);

        Intent intent = getIntent();
        username = intent.getStringExtra("user");

        hlmEditText = findViewById(R.id.editTextNumberDecimalHL);
        falmEditText = findViewById(R.id.editTextNumberDecimalFAL);
        mmEditText = findViewById(R.id.editTextNumberDecimalMATH);
        e1mEditText = findViewById(R.id.firstElectiveMark);
        e2mEditText = findViewById(R.id.secondElectiveMark);
        e3mEditText = findViewById(R.id.thirdElectiveMark);
        loEditText = findViewById(R.id.editTextNumberDecimalLO);

        Spinner spinnerMath = findViewById(R.id.spinner_Math);
        Spinner spinnerFirstElective = findViewById(R.id.spinner_firstElective);
        Spinner spinnerSecondElective = findViewById(R.id.spinner_secondElective);
        Spinner spinnerThirdElective = findViewById(R.id.spinner_thirdElective);

        ArrayAdapter<CharSequence> mathAdapter = ArrayAdapter.createFromResource(this,
                R.array.dropdown_items, android.R.layout.simple_spinner_item);
        mathAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMath.setAdapter(mathAdapter);

        spinnerMath.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMath = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> firstElectiveAdapter = ArrayAdapter.createFromResource(this,
                R.array.dropdown_item, android.R.layout.simple_spinner_item);
        firstElectiveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFirstElective.setAdapter(firstElectiveAdapter);

        spinnerFirstElective.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedFirstElective = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> secondElectiveAdapter = ArrayAdapter.createFromResource(this,
                R.array.dropdown_item, android.R.layout.simple_spinner_item);
        secondElectiveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSecondElective.setAdapter(secondElectiveAdapter);

        spinnerSecondElective.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedSecondElective = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        ArrayAdapter<CharSequence> thirdElectiveAdapter = ArrayAdapter.createFromResource(this,
                R.array.dropdown_item, android.R.layout.simple_spinner_item);
        thirdElectiveAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerThirdElective.setAdapter(thirdElectiveAdapter);

        spinnerThirdElective.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedThirdElective = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    private void calculateAPS() {
        subjectMarks = new EditText[]{
                hlmEditText, // HL
                falmEditText, // FAL
                mmEditText, // MATH
                e1mEditText, // Elective 1
                e2mEditText, // Elective 2
                e3mEditText, // Elective 3
                loEditText // LO
        };

        int[] marks = new int[7];

        for (int i = 0; i < 7; i++) {
            String input = subjectMarks[i].getText().toString();
            if (!input.isEmpty()) {
                marks[i] = Integer.parseInt(input);
            }else{
                allSelected = false;
            }
        }

        apsWits = getWits(marks);
        apsUKZN = getUKZN(marks);
        apsDef = getAPS(marks);
        apsUWC = getUWC(marks);
    }
    public void ifWannaContinue(View view) {
        calculateAPS();
        addMarks();
        if(allSelected){
            Intent intent=new Intent(this,Homepage.class);
            intent.putExtra("apsWits", apsWits);
            intent.putExtra("apsUKZN", apsUKZN);
            intent.putExtra("apsAPS", apsDef);
            intent.putExtra("apsUWC", apsUWC);
            startActivity(intent);
        }else{
            Toast.makeText(this, "Please fill in all subject marks.", Toast.LENGTH_SHORT).show();
        }
    }

    public void addMarks()
    {
        ContentValues params = new ContentValues();
        params.put("username", username);
        params.put("hlm", hlmEditText.getText().toString());
        params.put("falm", falmEditText.getText().toString());
        params.put("math", selectedMath);
        params.put("mm", mmEditText.getText().toString());
        params.put("e1", selectedFirstElective);
        params.put("e1m", e1mEditText.getText().toString());
        params.put("e2", selectedSecondElective);
        System.out.println(selectedSecondElective);
        params.put("e2m", e2mEditText.getText().toString());
        params.put("e3", selectedThirdElective);
        params.put("e3m", e3mEditText.getText().toString());
        params.put("lo", loEditText.getText().toString());

        PHPRequest phpRequest = new PHPRequest("https://lamp.ms.wits.ac.za/home/s2575455/");
        phpRequest.doRequest(this, "addmarks.php", params, new RequestHandler() {
            @Override
            public void processResponse(String response) {
                // Handle the server response here if needed
                System.out.println(response);
                if(!response.trim().equals("Inserted successfully") || !response.trim().equals("Updated successfully")){
                    allSelected = false;
                }
            }
        });
    }
    public static int getWits(int marks[]) {
        int aps = 0;

        switch (marks[6] / 10) {
            case 10:
            case 9:
                aps += 4;
                break;
            case 8:
                aps += 3;
                break;
            case 7:
                aps += 2;
                break;
            case 6:
                aps += 1;
                break;
            default:
                break;
        }

        for (int i = 0; i < 6; i++) {

            switch (marks[i] / 10) {
                case 10:
                case 9:
                    if (i == 0 || i == 2) {
                        aps += 10;
                        break;
                    }
                    aps += 8;
                    break;
                case 8:
                    if (i == 0 || i == 2) {
                        aps += 9;
                        break;
                    }
                    aps += 7;
                    break;
                case 7:
                    if (i == 0 || i == 2) {
                        aps += 8;
                        break;
                    }
                    aps += 6;
                    break;
                case 6:
                    if (i == 0 || i == 2) {
                        aps += 7;
                        break;
                    }
                    aps += 5;
                    break;
                case 5:
                    aps += 4;
                    break;
                case 4:
                    aps += 3;
                    break;
                case 3:
                    aps += 2;
                    break;
                default:
                    aps += 1;
                    break;
            }
        }

        return aps;
    }

    public static int getUKZN(int marks[])
    {
        int aps = 0;
        for (int i = 0; i < 7; i++) {
            switch (marks[i] / 10) {
                case 10:
                case 9:
                    aps += 8;
                    break;
                case 8:
                    aps += 7;
                    break;
                case 7:
                    aps += 6;
                    break;
                case 6:
                    aps += 5;
                    break;
                case 5:
                    aps += 4;
                    break;
                case 4:
                    aps += 3;
                    break;
                case 3:
                    aps += 2;
                    break;
                default:
                    aps += 1;
                    break;
            }
        }
        return aps;
    }

    public static int getAPS(int marks[])
    {
        int aps = 0;
        for (int i = 0; i < 6; i++) {
            switch (marks[i] / 10) {
                case 10:
                case 9:
                case 8:
                    aps += 7;
                    break;
                case 7:
                    aps += 6;
                    break;
                case 6:
                    aps += 5;
                    break;
                case 5:
                    aps += 4;
                    break;
                case 4:
                    aps += 3;
                    break;
                case 3:
                    aps += 2;
                    break;
                default:
                    aps += 1;
                    break;
            }
        }
        return aps;
    }

    public static int getUWC(int marks[])
    {
        int aps = 0;

        switch (marks[6] / 10) {
            case 10:
            case 9:
            case 8:
                aps += 3;
                break;
            case 7:
            case 6:
            case 5:
                aps += 2;
                break;
            case 4:
            case 3:
            case 2:
                aps += 1;
            default:
                break;
        }

        for (int i = 0; i < 6; i++) {

            switch (marks[i] / 10) {
                case 10:
                case 9:
                    if (i == 0 || i == 2) {
                        aps += 15;
                        break;
                    }
                    aps += 8;
                    break;
                case 8:
                    if (i == 0 || i == 2) {
                        aps += 13;
                        break;
                    }
                    aps += 7;
                    break;
                case 7:
                    if (i == 0 || i == 2) {
                        aps += 11;
                        break;
                    }
                    aps += 6;
                    break;
                case 6:
                    if (i == 0 || i == 2) {
                        aps += 9;
                        break;
                    }
                    aps += 5;
                    break;
                case 5:
                    if (i == 0 || i == 2) {
                        aps += 7;
                        break;
                    }
                    aps += 4;
                    break;
                case 4:
                    if (i == 0 || i == 2) {
                        aps += 5;
                        break;
                    }
                    aps += 3;
                    break;
                case 3:
                    if (i == 0 || i == 2) {
                        aps += 3;
                        break;
                    }
                    aps += 2;
                    break;
                case 2:
                    aps += 1;
                    break;
                default:
                    break;
            }
        }
        return aps;
    }
}