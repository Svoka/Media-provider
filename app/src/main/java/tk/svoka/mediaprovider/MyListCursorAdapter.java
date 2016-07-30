package tk.svoka.mediaprovider;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;


public class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.ViewHolder>{

    private Context context;

    public MyListCursorAdapter(Context context,Cursor cursor){
        super(context,cursor);
        this.context=context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView photoHolder;
        public ViewHolder(View view) {
            super(view);
            photoHolder = (ImageView) view.findViewById(R.id.photoHolder);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {

//        int dataColumn = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
        int id = cursor.getColumnIndex(MediaStore.Images.Media._ID);


        Bitmap bitmap = MediaStore.Images.Thumbnails.getThumbnail(context.getContentResolver(), cursor.getInt(id), MediaStore.Images.Thumbnails.MICRO_KIND, null );
        viewHolder.photoHolder.setImageBitmap(bitmap);



    }
}