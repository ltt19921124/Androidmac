package activitytest.example.com.uiwidgettest2;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText mEditText;
    private ImageView mImageView;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button);
        mEditText = findViewById(R.id.edit_text);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progressbar);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.button:
//                        mImageView.setImageResource(R.drawable.ic_launcher);
//                        String inputText = mEditText.getText().toString();
//                        Toast.makeText(MainActivity.this,inputText,
//                                Toast.LENGTH_SHORT).show();
//                        if (mProgressBar.getVisibility() == View.GONE) {
//                            mProgressBar.setVisibility(View.VISIBLE);
//                        } else {
//                            mProgressBar.setVisibility(View.GONE);
//                        }
//                        int progress = mProgressBar.getProgress();
//                        progress = progress + 10;
//                        mProgressBar.setProgress(progress);
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setTitle("This is Dialod");
                        dialog.setMessage("Something important.");
                        dialog.setCancelable(false);
                        dialog.setPositiveButton("ok",

                                new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        dialog.show();

                        break;
                    default:
                        break;
                }
            }
        });

    }
}
