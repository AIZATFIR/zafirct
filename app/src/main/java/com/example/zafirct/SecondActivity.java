package com.example.zafirct;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.zafirct.extra.MESSAGE";
    public static final String EXTRA_REPLY = "com.example.zafirct.extra.REPLY";
    public static final String EXTRA_HISTORY = "com.example.zafirct.extra.HISTORY";
    public static final String EXTRA_COUNTER = "com.example.zafirct.extra.COUNTER";

    private EditText editTextReply;
    private EditText editTextUsername;
    private TextView textMessage;
    private String messageHistory = "";
    private int messageCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_second);

        editTextReply = findViewById(R.id.editTextSecond);
        editTextUsername = findViewById(R.id.editTextUsernameSecond);
        textMessage = findViewById(R.id.text_message);

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(EXTRA_HISTORY)) {
                messageHistory = intent.getStringExtra(EXTRA_HISTORY);
            }
            messageCounter = intent.getIntExtra(EXTRA_COUNTER, 0);
            if (!messageHistory.isEmpty()) {
                textMessage.setText(messageHistory);
            }
        }
    }

    public void returnReply(View view) {
        String username = editTextUsername.getText().toString().trim();
        if (username.isEmpty()) {
            username = "Teman";
        }

        String reply = editTextReply.getText().toString().trim();
        if (!reply.isEmpty()) {
            messageCounter++;
            String entry = messageCounter + ". " + username + ": " + reply;
            if (messageHistory.isEmpty()) {
                messageHistory = entry;
            } else {
                messageHistory += "\n" + entry;
            }
        }

        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REPLY, reply);
        replyIntent.putExtra(EXTRA_HISTORY, messageHistory);
        replyIntent.putExtra(EXTRA_COUNTER, messageCounter);
        setResult(RESULT_OK, replyIntent);
        finish();
    }
}