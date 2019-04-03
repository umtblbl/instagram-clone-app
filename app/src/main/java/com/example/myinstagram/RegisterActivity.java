package com.example.myinstagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText userName, fullName, email, password;
    Button btnRegister;
    TextView txtLogin;

    FirebaseAuth auth;
    DatabaseReference reference;
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        userName = findViewById(R.id.editTextUsername);
        fullName = findViewById(R.id.editTextFullname);
        email = findViewById(R.id.editTextEmail);
        password = findViewById(R.id.editTextPassword);
        btnRegister = findViewById(R.id.btnRegister);
        txtLogin = findViewById(R.id.txtLogin);

        auth = FirebaseAuth.getInstance();

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog = new ProgressDialog(RegisterActivity.this);
                pDialog.setMessage("Yükleniyor...");
                pDialog.show();

                String str_username = userName.getText().toString();
                String str_fullname = fullName.getText().toString();
                String str_email = email.getText().toString();
                String str_password = password.getText().toString();

                if (TextUtils.isEmpty(str_username) || TextUtils.isEmpty(str_fullname) || 
                        TextUtils.isEmpty(str_email) || TextUtils.isEmpty(str_password))
                    Toast.makeText(RegisterActivity.this, "Alanlar boş geçilemez!", Toast.LENGTH_SHORT).show();
                else if (str_password.length() < 6)
                    Toast.makeText(RegisterActivity.this, "Şifre en az 6 karakter olmalı.", Toast.LENGTH_SHORT).show();
                else {
                    Register(str_username, str_fullname, str_email, str_password);
                }
                    



            }
        });

    }

    private void Register(final String username, final String fullname, String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser firebaseUser = auth.getCurrentUser();
                    String userId = firebaseUser.getUid();

                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("id", userId);
                    hashMap.put("userName", username.toLowerCase());
                    hashMap.put("fullName", fullname.toLowerCase());
                    hashMap.put("bio", "");
                    hashMap.put("imageUrl", "https://firebasestorage.googleapis.com/v0/b/myinstagram-c7f80.appspot.com/o/user2.png?alt=media&token=1014a864-b19c-4062-9895-4950a1cfbd10");

                    reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()) {
                                pDialog.dismiss();
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }
                    });


                }

                else {
                    pDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Email veya şifre yanlış!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


}
