package com.tuespotsolutions.ravinder077.ggsdav;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

/**
 * Created by Chugh on 8/3/2017.
 */

public class GallaryAdapter extends RecyclerView.Adapter<GallaryAdapter.MyViewHolder> {


    private List<GallaryData> postlist;
    private Context mcontext;

    public GallaryAdapter(List<GallaryData> postlist) {
        this.postlist = postlist;
    }


    public GallaryAdapter(List<GallaryData> postlist, Context context) {

        this.mcontext=context;
        this.postlist = postlist;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gallary_items, parent, false);

        return new GallaryAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final GallaryData noticeData = postlist.get(position);




        Glide.with(holder.imageView1.getContext()).load(noticeData.getImg())
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imageView1);
        holder.title1.setText(noticeData.getName());







    }



    @Override
    public int getItemCount() {
        return postlist.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView title1;
        public ImageView imageView1;

        public MyViewHolder(View view) {
            super(view);

            title1=(TextView) view.findViewById(R.id.picturetext);
            imageView1=(ImageView) view.findViewById(R.id.pictureid);


        }
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }



    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private GalleryAdapter.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final GalleryAdapter.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}

