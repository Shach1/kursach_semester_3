package ru.mirea.androidcoursework;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home_item) {
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.homeFragment);
                return true;
            }
            else if (item.getItemId() == R.id.cart_item) {
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.cartFragment);
                return true;
            }
            else if (item.getItemId() == R.id.settings_item) {
                Navigation.findNavController(this, R.id.fragmentContainer).navigate(R.id.settingsFragment);
                return true;
            }
            return false;
        });
    }
}