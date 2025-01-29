package com.example.GraduationProject.NEW;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.GraduationProject.Doctors.DoctorCalendar;
import com.example.GraduationProject.Doctors.DoctorHome;
import com.example.GraduationProject.Doctors.DoctorProfile;
import com.example.GraduationProject.Doctors.DoctorSettings;
import com.example.GraduationProject.Doctors.Doctorlogin;
import com.example.GraduationProject.Pationts.PationtLogin;
import com.example.GraduationProject.R;

public class NewChooseScreen extends AppCompatActivity {

    CardView new_patientCard;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_choose_screen);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        new_patientCard = findViewById(R.id.new_patientCard);
        new_patientCard.setOnClickListener(view -> {
            Intent intent = new Intent(NewChooseScreen.this, PationtLogin.class);
            startActivity(intent);
        });



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.new_doc_menu, menu);
        return true; // Return true to display the menu
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
      int id = item.getItemId();
      if(R.id.new_doc_icon == id){
          Intent intent = new Intent(NewChooseScreen.this, Doctorlogin.class);
          startActivity(intent);

      }

        return super.onOptionsItemSelected(item);
    }
}
