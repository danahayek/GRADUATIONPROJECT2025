package com.example.GraduationProject.Doctors;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.GraduationProject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

// CALENDAR PAGE FROM MENU CLICKING
public class DoctorCalendar extends AppCompatActivity {

    private CalendarView calendarView;
    private EditText eventTitle;
    private Button addCalendatEvent;
    private String selectData ="";
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_doctor_calendar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("events");
        calendarView = findViewById(R.id.calendarView);
        eventTitle = findViewById(R.id.eventTitle);
        addCalendatEvent = findViewById(R.id.addCalendarEvent);

        calendarView.setOnDateChangeListener((view,year,month,dayOfMonth) -> {
            selectData = year+ "-" +(month +1) + "-" + dayOfMonth;
        });
        addCalendatEvent.setOnClickListener(v ->{
            String title = eventTitle.getText().toString().trim();
            if(!selectData.isEmpty() && !title.isEmpty()){
                addEventToFirebase ( selectData, title);
            }else{
                Toast.makeText(this,"select a date and enter a title",Toast.LENGTH_LONG).show();
            }
        });
    }
    private void addEventToFirebase (String date , String title) {
        String eventID = databaseReference.push().getKey();
        HashMap<String, String> event = new HashMap<>();
        event.put("date", date);
        event.put("title", title);

        assert eventID != null;
        databaseReference.child(eventID).setValue(event)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Event Added", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(this, "Failed To ADD", Toast.LENGTH_LONG).show();


                    }
                });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String date = dataSnapshot.child("date").getValue(String.class);
                    String title = dataSnapshot.child("title").getValue(String.class);

                    // Display or process event details here
                    System.out.println("Date: " + date + ", Title: " + title);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("Error: " + error.getMessage());
            }
        });

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "eventChannel")
                .setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentTitle("Event Reminder")
                .setContentText("You have an event today!")
                .setPriority(NotificationCompat.PRIORITY_HIGH);

        // Show notification
        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.notify(1, builder.build());
    }





}