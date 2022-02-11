package com.example.masrapt;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URI;

public class About extends AppCompatActivity {

    private ImageView icon_back;
    private View masrapt_whatsapp_group, masrapt_discord_group, linkedin_author_1, linkedin_author_2,
        github_author_1, github_author_2, gmail_author_1, gmail_author_2;
    private TextView txt_feedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        icon_back = (ImageView) findViewById(R.id.back_icon);
        icon_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDashboard();
            }
        });

        masrapt_whatsapp_group = (View) findViewById(R.id.bt_whats);
        masrapt_discord_group = (View) findViewById(R.id.bt_discord);
        linkedin_author_1 = (View) findViewById(R.id.bt_linkdin);
        linkedin_author_2 = (View) findViewById(R.id.bt_linkdin_1);
        github_author_1 = (View) findViewById(R.id.bt_github);
        github_author_2 = (View) findViewById(R.id.bt_gmail_1);
        gmail_author_1 = (View) findViewById(R.id.bt_gmail);
        gmail_author_2 = (View) findViewById(R.id.bt_github_1);
        txt_feedback = (TextView) findViewById(R.id.txt_feedback);

        masrapt_whatsapp_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToURL("https://chat.whatsapp.com/D33MkyekgH5KKXsUEVtgY1");
            }
        });

        masrapt_discord_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToURL("https://discord.gg/XSvR75tj");
            }
        });

        linkedin_author_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToURL("https://www.linkedin.com/in/roberto-medina-28062318a/");
            }
        });

        linkedin_author_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToURL("https://www.linkedin.com/in/roberto-medina-28062318a/");
            }
        });

        gmail_author_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToURL("mailto:rmedina@uta.cv");
            }
        });

        gmail_author_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToURL("mailto:rmdelgado@uta.cv");
            }
        });

        github_author_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToURL("https://github.com/robertocarlosmedina");
            }
        });

        github_author_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToURL("https://github.com/Ronz97");
            }
        });

        txt_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToURL("mailto:rmedina@uta.cv");
            }
        });

    }
    public void openDashboard(){
        Intent intent = new Intent(About.this, Dashboard_activity.class);
        startActivity(intent);
    }

    public void goToURL(String url){
        Uri uri = Uri.parse(url);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }
}