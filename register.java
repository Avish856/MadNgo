package com.example.ngo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    //create object of database reference class to access firebase's realtime database
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://signup-4114d-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText fullName=findViewById(R.id.fullname);
        final EditText email=findViewById(R.id.emailaddress);
        final EditText phone=findViewById(R.id.phone);
        final EditText password=findViewById(R.id.password);
        final EditText conPassword=findViewById(R.id.conpassword);

        final Button registerBtn=findViewById(R.id.registerNowBtn);
        final TextView loginNowBtn=findViewById(R.id.loginNow);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get data from edit text into string variables
                final String fullNameText=fullName.getText().toString();
                final String emailTxt=email.getText().toString();
                final String phoneTxt=phone.getText().toString();
                final String passwordTxt=password.getText().toString();
                final String conPasswordTxt=conPassword.getText().toString();

                //Check if user fill all the fields before sending data to firebase
                if(fullNameText.isEmpty() || emailTxt.isEmpty() || phoneTxt.isEmpty() || passwordTxt.isEmpty() || conPasswordTxt.isEmpty())
                {
                    Toast.makeText(Register.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();

                }
                //check if passwords matching each other
                else if(!passwordTxt.equals(conPasswordTxt))
                {
                    Toast.makeText(Register.this, "Password not matching",Toast.LENGTH_SHORT).show();
                }
                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check if regisstered before
                            if(snapshot.hasChild(emailTxt)){
                                Toast.makeText(Register.this, "Already registered",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                //storing data to realtime database
                                //using phonenumber as identity of person
                                //set all the details of user come under phone number
                                databaseReference.child("users").child(emailTxt).child("fullname").setValue(fullNameText);
                                databaseReference.child("users").child(emailTxt).child("phone").setValue(phoneTxt);
                                databaseReference.child("users").child(emailTxt).child("password").setValue(passwordTxt);
                                Toast.makeText(Register.this, "Registered successfully",Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                        //storing data to realtime database
                        //using phonenumber as identity of person
                    //set all the details of user come under phone number
                    databaseReference.child("users").child(emailTxt).child("fullname").setValue(fullNameText);
                    databaseReference.child("users").child(emailTxt).child("phone").setValue(phoneTxt);
                    databaseReference.child("users").child(emailTxt).child("password").setValue(passwordTxt);
                    Toast.makeText(Register.this, "Registered successfully",Toast.LENGTH_SHORT).show();

                }
            }
        });

        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}