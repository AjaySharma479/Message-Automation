package com.example.messageautomation.store;

import android.content.SharedPreferences;

public interface Store {
    String loadData();
    void saveData(String data);
}
