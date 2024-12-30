package com.example.messageautomation.store;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

public class WhatsappStore implements Store {

    private static final String KEY_SAVED_DATA = "whatsappData";
    private final SharedPreferences sharedPreferences;

    public WhatsappStore(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public String loadData() {
        String savedData = sharedPreferences.getString(KEY_SAVED_DATA, "");
        if (savedData.isEmpty()) {
            return "";
        }
        StringBuilder dataToDisplay = new StringBuilder();
        String[] datas = savedData.split("\n");
        List<MessageInput> messageInputList = new ArrayList<>();
        for (String data : datas) {
            String[] messageInputs = data.split("#");
            dataToDisplay.append("\n").append("Mobile: ").append(messageInputs[0]).append(", Message: ").append(messageInputs[1]);
            messageInputList.add(new MessageInput(messageInputs[0], messageInputs[1]));
        }
        for(MessageInput input: messageInputList) {
//            sendToWhatsapp(input.getMobile(), input.getMessage());
        }
        return dataToDisplay.toString();
    }

    private static class MessageInput {
        String mobile;
        String message;

        public MessageInput(String mobile, String message) {
            this.mobile = mobile;
            this.message = message;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    @Override
    public void saveData(String data) {
        String savedData = sharedPreferences.getString(KEY_SAVED_DATA, "");
        String updatedData;
        if(savedData.isEmpty()) {
            updatedData = data;
        } else {
            updatedData = savedData + "\n" + data;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_SAVED_DATA, updatedData);
        editor.apply();
    }

//    private void sendToWhatsApp(String mobile, String text) {
//        try {
//            // Create an intent to open WhatsApp
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setData(Uri.parse("https://wa.me/" + mobile + "?text=" + text));
//            intent.setPackage("com.whatsapp");
//            startActivity(intent);
//        } catch (Exception e) {
//            // Handle exception if WhatsApp is not installed
//            e.printStackTrace();
//        }
//    }
}
