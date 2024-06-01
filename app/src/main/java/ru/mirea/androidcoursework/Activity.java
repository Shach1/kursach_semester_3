package ru.mirea.androidcoursework;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ru.mirea.androidcoursework.main.CartFragment;
import ru.mirea.androidcoursework.main.HomeFragment;
import ru.mirea.androidcoursework.main.SettingsFragment;

public class Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        FragmentManager fragmentManager = getSupportFragmentManager();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home_item) {
                fragmentManager.beginTransaction().replace(R.id.fragmentConrainer, new HomeFragment()).commit();
                return true;
            }
            else if (item.getItemId() == R.id.cart_item) {
                fragmentManager.beginTransaction().replace(R.id.fragmentConrainer, new CartFragment()).commit();
                return true;
            }
            else if (item.getItemId() == R.id.settings_item) {
                fragmentManager.beginTransaction().replace(R.id.fragmentConrainer, new SettingsFragment()).commit();
                return true;
            }
            return false;
        });
    }
}