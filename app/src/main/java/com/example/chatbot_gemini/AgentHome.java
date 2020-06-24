package com.example.chatbot_gemini;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AgentHome extends AppCompatActivity implements View.OnClickListener {

    String email;
    private static final String TAG = "GoogleActivity";
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_home);

        Intent intent_name = getIntent();
        email=intent_name.getStringExtra("name");

        //toast

        Context context = getApplicationContext();
        CharSequence text = "Signed in as agent "+email;
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        //Declaring Sign In and Sign Out buttons

        findViewById(R.id.signOutButtonAgent).setOnClickListener((View.OnClickListener) this);

        // Declaring a Firebase Auth Instance

        mAuth = FirebaseAuth.getInstance();
    }


    public void onClick(View v) {
        int i = v.getId();
        if(i == R.id.signOutButtonAgent) {
            signOut();
        }
    }

    private void signOut() {
        // Firebase sign out
        Log.d(TAG, "signOutWithCredential:success");

        mAuth.signOut();

        // Google sign out
        mGoogleSignInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent intent_name = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent_name);
                    }
                });

    }
}