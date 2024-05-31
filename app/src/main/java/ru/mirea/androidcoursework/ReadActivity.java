package ru.mirea.androidcoursework;

import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ru.mirea.androidcoursework.entity.User;

public class ReadActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference mDataBase;
    private final String USER_KEY = "User";
    private userAdapter adapter;
    private List<User> listData;


    private void init(){
        recyclerView = findViewById(R.id.RecyclerList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listData = new ArrayList<>();
        adapter = new userAdapter(listData);
        recyclerView.setAdapter(adapter);

        mDataBase = FirebaseDatabase.getInstance().getReference(USER_KEY);
        getDataFromDB();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_read);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        init();
    }

    private void getDataFromDB(){
        //Создаем слушателя для базы данных
        mDataBase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //Проходим по всем значениям в базе данных
                if(listData.isEmpty()) listData.clear();
                for(DataSnapshot ds : snapshot.getChildren()){
                    User user = ds.getValue(User.class);
                    assert user != null;
                    listData.add(user);
                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("TAG", "Failed to read value from DB.", error.toException());

            }
        });
    }




}