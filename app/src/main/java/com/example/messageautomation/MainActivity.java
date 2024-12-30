package com.example.messageautomation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.messageautomation.databinding.ActivityMainBinding;
import com.example.messageautomation.store.WhatsappStore;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    private static final String SHARED_PREFS_NAME = "MessageAutomationPrefs";

    private EditText name, message;
    private TextView displaySavedData;
    private SharedPreferences sharedPreferences;
    private WhatsappStore whatsappStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences(SHARED_PREFS_NAME, MODE_PRIVATE);
        whatsappStore = new WhatsappStore(sharedPreferences);

        name = findViewById(R.id.name);
        message = findViewById(R.id.message);
        displaySavedData = findViewById(R.id.displaySavedData);


        setSupportActionBar(binding.toolbar);

        binding.saveButton.setOnClickListener(v -> saveData());

        binding.loadButton.setOnClickListener(v -> loadData());
    }

    private void saveData() {
        String nameString = name.getText().toString();
        String messageString = message.getText().toString();
        String data = nameString +
                "#" +
                messageString;


        whatsappStore.saveData(data);

        Toast.makeText(this, "Data saved!", Toast.LENGTH_SHORT).show();
        name.setText("");
        message.setText("");
    }

    private void loadData() {
        String data = whatsappStore.loadData();
        if(data.isEmpty()) {
            displaySavedData.setText("No data saved yet!");
        } else {
            displaySavedData.setText(data);
        }
    }
    private void sendToWhatsApp(String mobile, String text) {
        try {
            // Create an intent to open WhatsApp
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://wa.me/" + mobile + "?text=" + text));
            intent.setPackage("com.whatsapp");
            startActivity(intent);
        } catch (Exception e) {
            // Handle exception if WhatsApp is not installed
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
