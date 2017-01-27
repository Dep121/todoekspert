package dep.todoekspert;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.text.TextUtilsCompat;
import android.support.v4.util.PatternsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private EditText usernameEditText;
    private EditText passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        button = (Button) findViewById(R.id.login_btn);
        button.setOnClickListener(this);
        usernameEditText = (EditText) findViewById(R.id.username_et);
        passwordEditText = (EditText) findViewById(R.id.password_et);
    }

    @Override
    public void onClick(View view) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        boolean isError = false;

        if(TextUtils.isEmpty(username)){
            usernameEditText.setError(getString(R.string.this_field_is_required));
            isError = true;
        }
        if(TextUtils.isEmpty(password)){
            passwordEditText.setError(getString(R.string.this_field_is_required));
            isError = true;
        }
        /*if(!PatternsCompat.EMAIL_ADDRESS.matcher(username).matches()){
            usernameEditText.setError("This field has to be email!");
        }*/

        if (!isError){
            login(username, password);
        }
    }

    private void login(String username, String password) {
        AsyncTask<String, Integer, Boolean> asyncTask = new AsyncTask<String, Integer, Boolean>() {
            @Override
            protected Boolean doInBackground(String... params) {
                String username = params[0];
                String password = params[1];
                try{
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return username.equals("test") && password.equals("test");
            }

            @Override
            protected void onPostExecute(Boolean logged) {
                super.onPostExecute(logged);
                button.setEnabled(true);
                if (logged){
                    Toast.makeText(getApplicationContext(), R.string.login_ok, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), TodoListActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                button.setEnabled(false);
            }
        };

        asyncTask.execute(username, password);
    }
}
