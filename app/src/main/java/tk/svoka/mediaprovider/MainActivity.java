package tk.svoka.mediaprovider;

import android.database.Cursor;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String[] projectionPhotos = {
            MediaStore.Images.Media._ID,
    };


    //private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Cursor cursor = MediaStore.Images.Media.query(getContentResolver(), MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projectionPhotos, null, null, MediaStore.Images.Media.DATE_TAKEN + " DESC");

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.list);
        recyclerView.setAdapter(new MyListCursorAdapter(this,cursor));





    }
}
