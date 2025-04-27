package com.example.perchanceapiapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText apiInput;
    private Button sendRequestButton;
    private TextView responseOutput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInput = findViewById(R.id.api_input);
        sendRequestButton = findViewById(R.id.send_request_button);
        responseOutput = findViewById(R.id.response_output);

        sendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendApiRequest();
            }
        });
    }

    private void sendApiRequest() {
        String apiUrl = apiInput.getText().toString();
        ApiClient apiClient = new ApiClient();
        apiClient.sendGetRequest(apiUrl, new ApiClient.ApiResponseCallback() {
            @Override
            public void onResponse(String response) {
                responseOutput.setText(response);
            }

            @Override
            public void onError(String error) {
                responseOutput.setText(error);
            }
        });
    }

    public void openSettings(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}