package com.jhony.jester.play.Activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.connection.ConnectionsClient;
import com.google.android.gms.nearby.connection.Payload;
import com.jhony.jester.play.Interfaces.SplitListener;
import com.jhony.jester.play.Model.AllPlayers;
import com.jhony.jester.play.Model.GsonModel;
import com.jhony.jester.play.R;
import com.jhony.jester.play.Utils.DataSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.jhony.jester.play.Utils.Constants.JOINED;

public class JoinActivity extends AppCompatActivity implements SplitListener {
    EditText gamePass;
    CircleImageView hostsImage;
    TextView hostsName;
    Button joinGame;
    ImageButton left, right;
    boolean isPass = false;
    Intent joinIntent;
    JSONObject joinPayload;
    ConnectionsClient connectionsClient;
    DataSingleton dataSingleton;
    private int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joining_layout);

        dataSingleton = DataSingleton.getOneInstance();

        left = findViewById(R.id.left_arrow);
        right = findViewById(R.id.right_arrow);
        hostsImage = findViewById(R.id.hosts_image);
        hostsName = findViewById(R.id.hosts_name);
        gamePass = findViewById(R.id.game_pass);
        joinGame = findViewById(R.id.join_game);

        joinPayload = new JSONObject();
        connectionsClient = Nearby.getConnectionsClient(getApplicationContext());

        joinIntent = new Intent(JoinActivity.this, WaitingActivity.class);
        joinIntent.putExtra(getResString(R.string.which), JOINED);

        joinGame.setClickable(false);
        joinGame.setBackgroundColor(getResources().getColor(R.color.gray));
        joinGame.setText("Loading...");

        joinGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPass)
                    if (TextUtils.isEmpty(gamePass.getText().toString())) {
                        Toast.makeText(JoinActivity.this, "Enter The  Password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                dataSingleton.getAllPlayer().add(0,
                        new AllPlayers(dataSingleton.getHosts().get(pos).getUniqueId(),
                                dataSingleton.getHosts().get(pos).getPlayerInfo()));

                try {
                    joinPayload.put(getResString(R.string.payload_id), 4);
                    joinPayload.put(getResString(R.string.user_name), dataSingleton.getmUserName());
                    joinPayload.put(getResString(R.string.user_desc), dataSingleton.getmUserDesc());
                    joinPayload.put(getResString(R.string.user_exp), dataSingleton.getmUserLevel());
                    // TODO: 25-01-2018 add image string
                    connectionsClient
                            .sendPayload(dataSingleton.getHosts().get(pos).getUniqueId(),
                                    Payload.fromBytes(joinPayload.toString().getBytes()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dataSingleton.setHostEndPoint(dataSingleton.getHosts().get(pos).getPlayerInfo().getEndpointId());

                startActivity(joinIntent);
                finish();
            }
        });

    }


    @Override
    public void onSplitCompleted(GsonModel gsonModel) {
        //show the hosts
        if (gsonModel.getPayloadId().equals("1")) {
            pos = 0;
            hostsName.setText(dataSingleton.getHosts().get(pos).toString());
            if (!gsonModel.getPassword().isEmpty()){
                isPass = true;
            }
            joinGame.setClickable(true);
            joinGame.setBackground(getResources().getDrawable(R.drawable.button_background));
        }
    }

    private String getResString(int id) {
        return getResources().getString(id);
    }

}
