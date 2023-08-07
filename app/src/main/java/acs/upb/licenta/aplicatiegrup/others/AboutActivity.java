package acs.upb.licenta.aplicatiegrup.others;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import acs.upb.licenta.aplicatiegrup.R;
import acs.upb.licenta.aplicatiegrup.databinding.ActivityAboutBinding;

public class AboutActivity extends DrawerBaseActivity {

    ActivityAboutBinding activityAboutBinding;
    TextView text;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityAboutBinding = ActivityAboutBinding.inflate(getLayoutInflater());
        setContentView(activityAboutBinding.getRoot());
        allocateActivityTitle("About the app");

        text = findViewById(R.id.text_about);
        text.setText("About PlanIt!\n\nThis application is intended to help you and your friends/family organize yours plans easier. Just go to Calendar, pick a date and let the planning begin! After creating an event, everything there is to organize is laid out in front of you: shopping lists, possible tasks, polls and much more! All you have to do is to add whatever you need and let the app do the rest for you. Happy planning!");
    }
}