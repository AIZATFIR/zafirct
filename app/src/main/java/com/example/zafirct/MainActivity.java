package com.example.zafirct;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
    private EditText etShareDocument;
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
        etShareDocument = findViewById(R.id.et_share_document);
        textMessage = findViewById(R.id.text_message);

        if (!messageHistory.isEmpty()) {
            textMessage.setText(messageHistory);
        }

        // Default set profile template in edit text
        loadProfileTemplate(null);
    }

    // --- IMPLICIT INTENT HANDLERS ---

    // 1. Implicit Intent: Open Maps / Location (Gumpang Soditan)
    public void openMapsIntent(View view) {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=Gumpang+Soditan");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        try {
            startActivity(mapIntent);
        } catch (ActivityNotFoundException e) {
            // Fallback to web browser maps
            openUrl("https://www.google.com/maps/search/?api=1&query=Gumpang+Soditan");
        }
    }

    // 2. Implicit Intent: Open WhatsApp directly
    public void openWhatsAppIntent(View view) {
        String phone = "6285946689168";
        Uri waUri = Uri.parse("https://wa.me/" + phone);
        Intent waIntent = new Intent(Intent.ACTION_VIEW, waUri);
        try {
            startActivity(waIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Tidak dapat membuka WhatsApp", Toast.LENGTH_SHORT).show();
        }
    }

    // 3. Implicit Intent: Open Portfolio Web
    public void openPortfolioIntent(View view) {
        openUrl("https://firtofolio.vercel.app");
    }

    // 4. Implicit Intent: Open Focus Clock Web
    public void openFocusClockWeb(View view) {
        openUrl("https://focus-clock-web.vercel.app");
    }

    // 5. Implicit Intent: Open Valeska Virtual Agent Web
    public void openValeskaAgent(View view) {
        openUrl("https://valeska.zafir.my.id");
    }

    // 6. Implicit Intent: Open Qurabic Indo Corpus
    public void openQurabicCorpus(View view) {
        openUrl("https://qurabic-indo-corpus.vercel.app");
    }

    // 7. Implicit Intent: Open Rync
    public void openRync(View view) {
        openUrl("https://rync.zafir.my.id");
    }

    // 8. Implicit Intent: Open Sadar Web
    public void openSadarWeb(View view) {
        openUrl("https://sadar-web.vercel.app");
    }

    // 9. Implicit Intent: Open GitHub Profile
    public void openGithubProfile(View view) {
        openUrl("https://github.com/aizatfahim");
    }

    // Helper method for opening URLs via Browser Implicit Intent
    private void openUrl(String url) {
        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(browserIntent);
        } catch (Exception e) {
            Toast.makeText(this, "Gagal membuka link: " + url, Toast.LENGTH_SHORT).show();
        }
    }

    // Load Focus Clock Paper Template into EditText
    public void loadPaperTemplate(View view) {
        String paperTemplate = "DOCUMENT TEMPLATE - FOCUS CLOCK PAPER\n\n" +
                "Judul: Focus Clock - Time Management & Pomodoro Productivity System\n" +
                "Penulis: Aizat Fahim Firmansyah (Gumpang Soditan)\n" +
                "Kontak: +6285946689168\n\n" +
                "Abstrak:\n" +
                "Focus Clock adalah platform produktivitas berbasis web dan mobile yang mengimplementasikan metode Pomodoro " +
                "untuk meningkatkan efisiensi pengerjaan tugas dan manajemen fokus harian.\n\n" +
                "Tautan Project & Portofolio:\n" +
                "- Web App: https://focus-clock-web.vercel.app\n" +
                "- Portofolio: https://firtofolio.vercel.app\n" +
                "- GitHub: https://github.com/aizatfahim";
        etShareDocument.setText(paperTemplate);
    }

    // Load Profile Summary Template into EditText
    public void loadProfileTemplate(View view) {
        String profileTemplate = "PROFIL & PORTOFOLIO DEVELOPER\n\n" +
                "Nama: Aizat Fahim Firmansyah\n" +
                "Alamat: Gumpang Soditan\n" +
                "WhatsApp: +6285946689168\n\n" +
                "Project Highlights:\n" +
                "1. Portofolio: https://firtofolio.vercel.app\n" +
                "2. Focus Clock Web: https://focus-clock-web.vercel.app\n" +
                "3. Valeska Agent: https://valeska.zafir.my.id\n" +
                "4. Qurabic Indo Corpus: https://qurabic-indo-corpus.vercel.app\n" +
                "5. Rync Platform: https://rync.zafir.my.id\n" +
                "6. Sadar Web: https://sadar-web.vercel.app\n" +
                "7. GitHub: https://github.com/aizatfahim";
        etShareDocument.setText(profileTemplate);
    }

    // Implicit Intent: Share Document / Text (ACTION_SEND)
    public void shareDocumentIntent(View view) {
        String textToShare = etShareDocument.getText().toString().trim();
        if (textToShare.isEmpty()) {
            Toast.makeText(this, "Silakan isi dokumen yang ingin dibagikan!", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Dokumen Portfolio / Focus Clock Paper - Aizat Fahim");
        shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);

        try {
            startActivity(Intent.createChooser(shareIntent, "Bagikan Dokumen via..."));
        } catch (Exception e) {
            Toast.makeText(this, "Gagal membagikan dokumen", Toast.LENGTH_SHORT).show();
        }
    }

    // --- EXPLICIT INTENT HANDLER ---
    public void openSecondActivity(View view) {
        String username = editTextUsername.getText().toString().trim();
        if (username.isEmpty()) {
            username = "Aizat Fahim Firmansyah";
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