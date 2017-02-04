package dep.todoekspert;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class TodoListActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_1 = 123;
    ListActivity la;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout)
        {
            finish();
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.action_add)
        {
            Intent intent = new Intent(getApplicationContext(), AddTodoActivity.class);
            startActivityForResult(intent, REQUEST_CODE_1);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_1){
            if (resultCode == RESULT_OK){
                Todo todo = (Todo) data.getSerializableExtra(AddTodoActivity.TODO);
                Toast.makeText(getApplicationContext(), "Result OK :" + todo.content, Toast.LENGTH_SHORT).show();
            }
            else if (resultCode == RESULT_CANCELED){
                Toast.makeText(getApplicationContext(), "Result Cancled", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
