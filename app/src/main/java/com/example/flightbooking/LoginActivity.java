package com.example.flightbooking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.flightbooking.auth.AuthManager;
import com.example.flightbooking.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private AuthManager authManager;
    private EditText usernameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addEvent();
    }

    private void addEvent() {
        // Set up the click listener for the "Đăng ký" button
        binding.btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        authManager = new AuthManager(this);

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = binding.edtTk.getText().toString();
                String password = binding.edtMk.getText().toString();
                authManager.login(username, password, new AuthManager.AuthCallback() {
                    @Override
                    public void onSuccess() {
                        // Đăng nhập thành công, chuyển đến MainActivity
                        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(mainIntent);
                        finish(); // Đóng LoginActivity để không quay lại được
                    }

                    @Override
                    public void onFailure() {
                        // Đăng nhập thất bại, hiển thị thông báo lỗi
                        Toast.makeText(LoginActivity.this, "Login failed!", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });
    }
}