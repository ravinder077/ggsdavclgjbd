package com.tuespotsolutions.ravinder077.ggsdav;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Chugh on 8/3/2017.
 */

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.MyViewHolder> {


    private List<ActivityData> postlist;
    private Context mcontext;

    public ActivityAdapter(List<ActivityData> postlist) {
        this.postlist = postlist;
    }


    public ActivityAdapter(List<ActivityData> postlist, Context context) {

        this.mcontext=context;
        this.postlist = postlist;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.davcard, parent, false);

        return new ActivityAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final ActivityData activityData = postlist.get(position);


        holder.tveventname.setText(activityData.getName());
        holder.tvvenue.setText(activityData.getVenue());
        holder.tvstdate.setText(activityData.getStdate());

        holder.tvenddate.setText(activityData.getEnddate());
        holder.orgnaizer.setText(activityData.getOrgnaizer());
        holder.contact.setText("hhhhhhhhh");






    }



    @Override
    public int getItemCount() {
        return postlist.size();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView tveventname;
        public TextView tvvenue;
        public TextView tvstdate;
        public TextView tvenddate;
        public TextView orgnaizer;
        public TextView contact;

        public MyViewHolder(View view) {
            super(view);

            tveventname=(TextView) view.findViewById(R.id.tveventname);
            tvvenue=(TextView) view.findViewById(R.id.tvvenue);
            tvstdate=(TextView) view.findViewById(R.id.tvstdate);
            tvenddate=(TextView) view.findViewById(R.id.tvenddate);
            orgnaizer=(TextView) view.findViewById(R.id.orgnaizer);
            contact=(TextView) view.findViewById(R.id.contact);

        }
    }

}

