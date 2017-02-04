package dep.todoekspert;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Deepak on 04-02-2017.
 */

public class PreventPasteText extends EditText {
    public boolean isPaste() {
        return isPaste;
    }

    private boolean isPaste = true;

    public PreventPasteText(Context context) {
        super(context);
    }

    public PreventPasteText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PreventPasteText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PreventPasteText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public boolean onTextContextMenuItem(int id) {
        if (id == android.R.id.paste){
            isPaste = false;
            return super.onTextContextMenuItem(0);
        }
        return super.onTextContextMenuItem(id);
    }
}
