package dep.todoekspert;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class AddTodoActivity extends AppCompatActivity {

    public static final String ISDONE = "isdone";
    public static final String CONTENT = "content";
    private EditText contentEditText;
    private CheckBox doneCheckBox;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);
        contentEditText = (EditText) findViewById(R.id.content_et);
        doneCheckBox = (CheckBox) findViewById(R.id.done_cb);
        saveButton = (Button) findViewById(R.id.save_bt);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = contentEditText.getText().toString();
                boolean isdone = doneCheckBox.isChecked();

                Intent intent = new Intent();
                intent.putExtra(CONTENT, content);
                intent.putExtra(ISDONE, isdone);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
