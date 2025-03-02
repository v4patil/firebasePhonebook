package com.example.phonebook;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phonebook.adapter.MyAdapter;
import com.example.phonebook.databinding.ActivityMainBinding;
import com.example.phonebook.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<User> users;
    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private ActivityMainBinding binding;

    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Users");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    users.add(user);
                    myAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView = binding.recyclerView;

        users = new ArrayList<>();
        myAdapter = new MyAdapter(this, users);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(myAdapter);



    }
}