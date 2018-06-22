package photoapp.mca.mk.appphoto;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.drawable.Drawable;

import android.os.Build;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;



import static photoapp.mca.mk.appphoto.MyAdapter.IMG_URL;
import static photoapp.mca.mk.appphoto.MyAdapter.POSITION;
import static photoapp.mca.mk.appphoto.MyAdapter.REQUEST_CODE;
import static photoapp.mca.mk.appphoto.MyAdapter.TITLE;

public class SecondActivity extends AppCompatActivity implements DialogFragmentClass.OnCompleteListener {
    public static final String FLAG_CHANGE = "FLAG_CHANGE";
    public static final String NEW_TITLE = "NEW_TITLE";
    public static final String OLD_TITLE = "OLD_TITLE";
    public static final String CHANGED = "CHANGED";
    public static final String FLAG_DELETED = "FLAG_DELETED";
    public static final String DELETED = "DELETED";
    public static final String USER_DELETED = "USER_DELETED";
    public static final String FULSSCREEN_IMAGE = "FULSSCREEN_IMAGE";
    public static final int CODE = 1;
    public static final String POSITION_TO_DELETE = "POSITION_TO_DELETE";
    public static final String DIALOG = "DIALOG";
    public static final int Deleted = 100;
    public static final int Edited = 200;
    Drawable d;
    TextView mTextView;
    ImageView mImageView;
    SecondActivity mSecondActivity;
    public String url_toDisplay;
    private int  position;

    public  void deleteThis(View view) {
    AlertDialog.Builder builder;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
    } else {
        builder = new AlertDialog.Builder(this);
    }
    builder.setTitle("Delete entry")
            .setMessage("Are you sure you want to delete this entry?")
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Intent deleteIntent = new Intent(SecondActivity.this, MainActivity.class);

                    deleteIntent.putExtra(USER_DELETED, mTextView.getText().toString());
                    deleteIntent.putExtra(POSITION_TO_DELETE,position);
                    setResult(Deleted);
                    mSecondActivity.finish();
                    mSecondActivity.startActivityForResult(deleteIntent, REQUEST_CODE);

                }
            })
            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // do nothing
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
}
public void editTitle(View view) {
    DialogFragmentClass dialogFragment = new DialogFragmentClass();
    dialogFragment.show(getFragmentManager(),DIALOG);

    setResult(Edited);


}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        d = getResources().getDrawable(R.drawable.fakultetilogo);
        mTextView = findViewById(R.id.textViewTitle);
        mImageView = findViewById(R.id.imageView);
        Intent intent = getIntent();
        position = intent.getIntExtra(POSITION, 0);
        mTextView.setText(intent.getStringExtra(TITLE));
       url_toDisplay= intent.getStringExtra(IMG_URL);
        Glide.with(this)
                .load(url_toDisplay)
                .into(mImageView);
        mSecondActivity=this;
        mImageView.setOnClickListener(view -> {
            Intent fullscreen = new Intent(SecondActivity.this, FullscreenActivity.class);
            fullscreen.putExtra(FULSSCREEN_IMAGE, url_toDisplay);
            startActivity(fullscreen);



        });

    }








    @Override
    public void onComplete(String title) {
        Intent changed = new Intent();

        changed.putExtra(OLD_TITLE, mTextView.getText().toString());
        changed.putExtra(NEW_TITLE, title);
        changed.putExtra(FLAG_CHANGE, CHANGED);

        mTextView.setText(title);
        Toast.makeText(this, "Title is changed!", Toast.LENGTH_SHORT).show();
    }
}
