package com.example.zafirct;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.zafirct.extra.MESSAGE";
    public static final String EXTRA_HISTORY = "com.example.zafirct.extra.HISTORY";
    public static final String EXTRA_COUNTER = "com.example.zafirct.extra.COUNTER";

    private EditText editText;
    private EditText editTextUsername;
    private TextView textMessage;
    private String messageHistory = "";
    private int messageCounter = 0;

    private final ActivityResultLauncher<Intent> secondActivityLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Intent data = result.getData();
                    if (data.hasExtra(EXTRA_HISTORY)) {
                        messageHistory = data.getStringExtra(EXTRA_HISTORY);
                    }
                    messageCounter = data.getIntExtra(EXTRA_COUNTER, messageCounter);
                    if (!messageHistory.isEmpty()) {
                        textMessage.setText(messageHistory);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        editTextUsername = findViewById(R.id.editTextUsername);
        textMessage = findViewById(R.id.text_message);

        if (!messageHistory.isEmpty()) {
            textMessage.setText(messageHistory);
        }
    }

    public void openSecondActivity(View view) {
        String username = editTextUsername.getText().toString().trim();
        if (username.isEmpty()) {
            username = "Zafir";
        }

        String message = editText.getText().toString().trim();
        if (!message.isEmpty()) {
            messageCounter++;
            String entry = messageCounter + ". " + username + ": " + message;
            if (messageHistory.isEmpty()) {
                messageHistory = entry;
            } else {
                messageHistory += "\n" + entry;
            }
        }

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        intent.putExtra(EXTRA_HISTORY, messageHistory);
        intent.putExtra(EXTRA_COUNTER, messageCounter);

        secondActivityLauncher.launch(intent);
        editText.setText("");
    }
}