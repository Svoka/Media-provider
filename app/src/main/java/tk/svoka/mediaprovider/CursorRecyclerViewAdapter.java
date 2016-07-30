package tk.svoka.mediaprovider;


import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


public abstract class CursorRecyclerViewAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private Context context;
    private Cursor cursor;
    private int rowIdColumn;
    private CursorContentObserver contentObserver;
    Uri uri;
    String[] projection;
    String selection;
    String[] selectionArgs;
    String orderBy;


    public CursorRecyclerViewAdapter(
            Context context, Uri uri, String[] projection, String selection, String[] selectionArgs, String orderBy
    ){


        this.context = context;
        this.uri = uri;
        this.projection = projection;
        this.selection = selection;
        this.selectionArgs=selectionArgs;
        this.orderBy = orderBy;
        createCursor();




    }

    private void createCursor(){
        if(cursor!=null){
            if(contentObserver!=null){
                cursor.unregisterContentObserver(contentObserver);
            }
            cursor.close();
        }


       cursor =  MediaStore.Images.Media.query(
                context.getContentResolver(),
                uri,
                projection,
                selection,
                selectionArgs,
                orderBy
        );
        if (cursor != null) {
            this.rowIdColumn =  cursor.getColumnIndex("_id");
            this.contentObserver = new CursorContentObserver();
            cursor.registerContentObserver(contentObserver);
        }
        notifyRecyclerOnChanges();
    }


    @Override
    public int getItemCount() {
        if (cursor != null) {
            return cursor.getCount();
        }
        return 0;
    }

    @Override
    public long getItemId(int position) {
        if (cursor != null && cursor.moveToPosition(position)) {
            return cursor.getLong(rowIdColumn);
        }
        return 0;
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    public abstract void onBindViewHolder(VH viewHolder, Cursor cursor);

    @Override
    public void onBindViewHolder(VH viewHolder, int position) {
//        if (!mDataValid) {
//            throw new IllegalStateException("this should only be called when the cursor is valid");
//        }
        if (!cursor.moveToPosition(position)) {
            throw new IllegalStateException("couldn't move cursor to position " + position);
        }
        onBindViewHolder(viewHolder, cursor);
    }




    private void notifyRecyclerOnChanges(){
        Activity activity = (Activity) context;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                notifyDataSetChanged();
            }
        });
    }

    private class CursorContentObserver extends ContentObserver {

        public CursorContentObserver() {
            super(null);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);

            createCursor();


            Log.e("CURSOR","CONTENT CHANGED");
        }

    }
}