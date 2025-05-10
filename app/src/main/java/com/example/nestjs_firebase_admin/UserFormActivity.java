package com.example.nestjs_firebase_admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.nestjs_firebase_admin.api.RetrofitClient;
import com.example.nestjs_firebase_admin.model.User;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFormActivity extends AppCompatActivity {
    public static final String EXTRA_USER = "extra_user";
    private TextInputEditText editTextName;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_form);

        editTextName = findViewById(R.id.editTextName);
        Button buttonSave = findViewById(R.id.buttonSave);

        user = (User) getIntent().getSerializableExtra(EXTRA_USER);
        if (user != null) {
            editTextName.setText(user.getName());
            setTitle("Edit User");
        } else {
            setTitle("New User");
        }

        buttonSave.setOnClickListener(v -> saveUser());
    }

    private void saveUser() {
        String name = editTextName.getText().toString().trim();
        if (name.isEmpty()) {
            Toast.makeText(this, "Please enter a name", Toast.LENGTH_SHORT).show();
            return;
        }

        if (user == null) {
            // Get FCM token for new users
            FirebaseMessaging.getInstance().getToken()
                    .addOnCompleteListener(task -> {
                        if (!task.isSuccessful()) {
                            Toast.makeText(UserFormActivity.this, "Failed to get FCM token", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        String fcmToken = task.getResult();
                        createUser(name, fcmToken);
                    });
        } else {
            updateUser(name);
        }
    }

    private void createUser(String name, String fcmToken) {
        User newUser = new User(name, fcmToken);
        RetrofitClient.getInstance().getApiService().createUser(newUser).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(UserFormActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UserFormActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUser(String name) {
        user.setName(name);
        RetrofitClient.getInstance().getApiService().updateUser(user.getId(), user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    setResult(RESULT_OK);
                    finish();
                } else {
                    Toast.makeText(UserFormActivity.this, "Error: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UserFormActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}