package ru.mirea.androidcoursework;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ru.mirea.androidcoursework.databinding.ActivityMainBinding;
import ru.mirea.androidcoursework.entity.User;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private DatabaseReference mDataBase;
    private final String USER_KEY = "User";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
    }



    public void Save(View view) {
        String id = mDataBase.getKey();
        String name = binding.etName.getText().toString();
        String secondName = binding.etSecondName.getText().toString();
        String email = binding.etEmail.getText().toString();
        if(name.isEmpty() || secondName.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }
        User user = new User(id, name, secondName, email);
        mDataBase.push().setValue(user);
    }
    public void Read(View view) {
        Intent intent = new Intent(MainActivity.this, ReadActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        binding = null;
        super.onDestroy();
    }

}