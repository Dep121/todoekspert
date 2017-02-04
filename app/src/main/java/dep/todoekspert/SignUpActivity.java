package dep.todoekspert;

import android.content.Intent;
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

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = SignUpActivity.class.getSimpleName();
    public static final String SUCCESS = "Create User Successful";
    private Button registerButton;
    private EditText newPasswordEditText;
    private PreventPasteText confirmPasswordEditText;
    private FirebaseAuth mAuth;
    private EditText newUsernameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_sign_up);

        registerButton = (Button) findViewById(R.id.register_btn);
        newUsernameEditText = (EditText) findViewById(R.id.new_username_et);
        newPasswordEditText = (EditText) findViewById(R.id.new_password_et);
        confirmPasswordEditText = (PreventPasteText) findViewById(R.id.confirm_password_et);

        /*if(!confirmPasswordEditText.isPaste()){
            Toast.makeText(this, "You can't paste", Toast.LENGTH_SHORT).show();
        }*/

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = newUsernameEditText.getText().toString();
                String password = newPasswordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                boolean isError = false;

        /*if(TextUtils.isEmpty(username)){
            newUsernameEditText.setError(getString(R.string.this_field_is_required));
            isError = true;
        }*/
                if(TextUtils.isEmpty(password)){
                    newPasswordEditText.setError(getString(R.string.this_field_is_required));
                    isError = true;
                }
                if(TextUtils.isEmpty(confirmPassword)){
                    confirmPasswordEditText.setError(getString(R.string.this_field_is_required));
                    isError = true;
                }
                if(!PatternsCompat.EMAIL_ADDRESS.matcher(username).matches()){
                    newUsernameEditText.setError("This field has to be email!");
                    isError = true;
                }

                if (!TextUtils.equals(password, confirmPassword)){
                    confirmPasswordEditText.setError("password is not matched");
                    isError = true;
                }

                if (!isError){
                    createAccount(username, password);
                }
            }
        });
    }

    private void createAccount(String username, String password) {

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            String success = "sign up successful";
                            Intent intent = new Intent();
                            intent.putExtra(SUCCESS, success);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "create account failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
