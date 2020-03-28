package nithra.calender.marriage.services.MarriageServices;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import nithra.calender.marriage.services.R;
import nithra.calender.marriage.services.SharedPreference;

public class ActivityMainPage extends AppCompatActivity {

    LinearLayout layoutMandapam, layoutDeceration, layoutPhotography, layoutMusic,layoutSamayal, layoutBeauty;
    SharedPreference sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreference = new SharedPreference();

        layoutMandapam = findViewById(R.id.layoutMandapam);
        layoutMandapam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreference.putString(ActivityMainPage.this,"user_service_type_name", "1");
                sharedPreference.putString(ActivityMainPage.this, "service_name", "மண்டபம்");
                Intent intent = new Intent(ActivityMainPage.this, ActivityMarriageServiceMain.class);
                startActivity(intent);

            }
        });

        layoutDeceration = findViewById(R.id.layoutDeceration);
        layoutDeceration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreference.putString(ActivityMainPage.this,"user_service_type_name", "2");
                sharedPreference.putString(ActivityMainPage.this, "service_name", "டெக்கரேஷன்");
                Intent intent = new Intent(ActivityMainPage.this, ActivityMarriageServiceMain.class);
                startActivity(intent);

            }
        });

        layoutPhotography = findViewById(R.id.layoutPhotography);
        layoutPhotography.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreference.putString(ActivityMainPage.this,"user_service_type_name", "3");
                sharedPreference.putString(ActivityMainPage.this, "service_name", "போட்டோகிராபி");
                Intent intent = new Intent(ActivityMainPage.this, ActivityMarriageServiceMain.class);
                startActivity(intent);

            }
        });

        layoutMusic = findViewById(R.id.layoutMusic);
        layoutMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreference.putString(ActivityMainPage.this,"user_service_type_name", "4");
                sharedPreference.putString(ActivityMainPage.this, "service_name", "இசைகுழு");
                Intent intent = new Intent(ActivityMainPage.this, ActivityMarriageServiceMain.class);
                startActivity(intent);

            }
        });

        layoutBeauty = findViewById(R.id.layoutBeauty);
        layoutBeauty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreference.putString(ActivityMainPage.this,"user_service_type_name", "5");
                sharedPreference.putString(ActivityMainPage.this, "service_name", "அழகு நிலையம்");
                Intent intent = new Intent(ActivityMainPage.this, ActivityMarriageServiceMain.class);
                startActivity(intent);

            }
        });

        layoutSamayal = findViewById(R.id.layoutSamayal);
        layoutSamayal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPreference.putString(ActivityMainPage.this,"user_service_type_name", "6");
                sharedPreference.putString(ActivityMainPage.this, "service_name", "கேட்டரிங்");
                Intent intent = new Intent(ActivityMainPage.this, ActivityMarriageServiceMain.class);
                startActivity(intent);

            }
        });




    }
}
