package com.jhony.jester.play.Activitys;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jhony.jester.play.Adapters.MyRecyclerAdapter;
import com.jhony.jester.play.R;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.jhony.jester.play.Utils.Constants.JOINED;
import static com.jhony.jester.play.Utils.Constants.WHICH;
import static com.jhony.jester.play.Utils.DataSingleton.isHost;

public class JoinActivity extends AppCompatActivity {

    EditText gamePass;
    CircleImageView hostsImage;
    TextView hostsName;
    Button joinGame;
    ImageButton left, right;
    boolean isPass = true;
    Intent joinIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joining_layout);

        left = findViewById(R.id.left_arrow);
        right = findViewById(R.id.right_arrow);
        hostsImage = findViewById(R.id.hosts_image);
        hostsName = findViewById(R.id.hosts_name);
        gamePass = findViewById(R.id.game_pass);
        joinGame = findViewById(R.id.join_game);

        joinIntent = new Intent(JoinActivity.this, WaitingActivity.class);
        joinIntent.putExtra(WHICH, JOINED);

        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPass)
                    if (TextUtils.isEmpty(gamePass.getText().toString())) {
                        Toast.makeText(JoinActivity.this, "Enter The  Password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                startActivity(joinIntent);
                finish();
            }
        });

    }


}
