package com.example.naveen.EmotionApp;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Naveen on 4/15/2017.
 */

public class ListAdapter extends BaseAdapter {
    Context context;
    Cursor dataCursor;
    LayoutInflater inflater;

    static class ViewHolder{
        public ImageView imageView;
        public TextView textView;
    }

    public ListAdapter(Context context, Cursor data){
        this.context = context;
        this.dataCursor = data;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return dataCursor.getCount();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if(rowView == null) {
            rowView = inflater.inflate(R.layout.custom_image_layout, null);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)rowView.findViewById(R.id.imageViewHome);
            viewHolder.textView = (TextView)rowView.findViewById(R.id.textViewDate);

            rowView.setTag(viewHolder);
        }

        ViewHolder viewHolder = (ViewHolder)rowView.getTag();
        dataCursor.moveToPosition(position);
        viewHolder.textView.setText(dataCursor.getString(1));
        new ListImageLoader(viewHolder.imageView).execute(dataCursor.getString(2));

        return rowView;
    }

}

class ListImageLoader extends AsyncTask<String, Void, Bitmap> {

    ImageView imageView;


    public ListImageLoader(ImageView imageView ) {
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        if(strings[0].isEmpty())
            return null;
        //do image decoding task
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap bitmap = BitmapFactory.decodeFile(strings[0],options);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if(bitmap != null){
            imageView.setImageBitmap(bitmap);


        }else {
            //callingActivity.finish();
        }
    }
}
