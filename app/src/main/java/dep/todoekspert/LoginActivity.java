package dep.todoekspert;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.util.PatternsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getSimpleName();
    public static final int REQUEST_CODE_2 = 101;
    private Button logInButton;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private AsyncTask<String, Integer, Boolean> asyncTask;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private Button signUpButton;
    private boolean alreadyLogged = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_login);


        logInButton = (Button) findViewById(R.id.login_btn);
        logInButton.setOnClickListener(this);
        signUpButton = (Button) findViewById(R.id.signup_btn);
        signUpButton.setOnClickListener(this);

        usernameEditText = (EditText) findViewById(R.id.username_et);
        passwordEditText = (EditText) findViewById(R.id.password_et);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null && alreadyLogged) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(getApplicationContext(), TodoListActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (user == null){
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Toast.makeText(LoginActivity.this, "Please sign in", Toast.LENGTH_SHORT).show();
                    alreadyLogged = false;
                }
                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.signup_btn){
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivityForResult(intent, REQUEST_CODE_2);
        }
        else if (id == R.id.login_btn){
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            boolean isError = false;

        /*if(TextUtils.isEmpty(username)){
            usernameEditText.setError(getString(R.string.this_field_is_required));
            isError = true;
        }*/
            if(TextUtils.isEmpty(password)){
                passwordEditText.setError(getString(R.string.this_field_is_required));
                isError = true;
            }
            if(!PatternsCompat.EMAIL_ADDRESS.matcher(username).matches()){
                usernameEditText.setError("This field has to be email!");
                isError = true;
            }

            if (!isError){
                login(username, password);
            }
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_2){
            if (resultCode == RESULT_OK){
                String success = data.getStringExtra(SignUpActivity.SUCCESS);
                Toast.makeText(getApplicationContext(), "Result OK :" + success, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), TodoListActivity.class);
                startActivity(intent);
                finish();
            }
            else if (resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "User is not created!!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void login(String username, String password) {
        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                            Toast.makeText(getApplicationContext(), R.string.login_ok, Toast.LENGTH_SHORT).show();

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            Intent intent = new Intent(getApplicationContext(), TodoListActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }



    /*private void login(String username, String password) {
        asyncTask = new AsyncTask<String, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                String username = params[0];
                String password = params[1];

                mAuth.signInWithEmailAndPassword(username, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                                // If sign in fails, display a message to the user. If sign in succeeds
                                // the auth state listener will be notified and logic to handle the
                                // signed in user can be handled in the listener.
                                if (!task.isSuccessful()) {
                                    Log.w(TAG, "signInWithEmail:failed", task.getException());
                                    Toast.makeText(getApplicationContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                                }

                                // ...
                            }
                        });
                *//*for (int i = 0; i < 100; ++i) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    publishProgress(i);
                }
                return username.equals("test") && password.equals("test");*//**//*

            }*//*

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                Log.d(TAG, "progress: " + values[0]);
                logInButton.setText(String.valueOf(values[0]));
            }

            @Override
            protected void onPostExecute(Boolean logged) {
                super.onPostExecute(logged);
                logInButton.setEnabled(true);
                if (logged){
                    Toast.makeText(getApplicationContext(), R.string.login_ok, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), TodoListActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (!logged){

                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                logInButton.setEnabled(false);
            }
        };

        asyncTask.execute(username, password);
    }*/
}
