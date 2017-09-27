package com.example.ravinder077.ggsdav;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.wangjie.androidbucket.utils.ABThreadUtil.TAG;

public class NoticeBoard extends Fragment {
    private List<NoticeData> postlist = new ArrayList<NoticeData>();
    private RecyclerView MyRecyclerView;
    private NoticeAdapter adapter;
    private LinearLayoutManager mLayoutManager;
    String mno = null;
    String jsonphoto = null;

    String mobileno=null;

    String dataoffset=null;
    // Store a member variable for the listener
    private EndlessRecyclerViewScrollListener scrollListener;

    com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton postbtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //new changess

       /* Toast.makeText(getContext(),"CardFragmentHome", Toast.LENGTH_SHORT).show();

        ArrayList<NoticeData> al = new ArrayList<NoticeData>();

        // NoticeData cdg=new NoticeData(); */

        SQLiteDatabase mydata=getActivity().openOrCreateDatabase("dav",MODE_PRIVATE,null);
        Cursor resultSet = mydata.rawQuery("Select * from stureg", null);
        if(resultSet.getCount()>0) {
            resultSet.moveToFirst();
            //String uname = resultSet.getString(0);
            mobileno=   mno = resultSet.getString(1);
            dataoffset="0";
        }


        /*OtpGen otpgen =new OtpGen();
        otpgen.execute("http://omtii.com/mile/text.php?mobno="+mno);
        try {
            System.err.println("Photo cc" + otpgen.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        try {
            jsonphoto= otpgen.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        try {
            ArrayList<NoticeData> photono=  jsonToMap(jsonphoto);
            initializeList(photono);
        } catch (JSONException e) {
            e.printStackTrace();
        }


*/


    }


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.board, container, false);

        // Replace 'android.R.id.list' with the 'id' of your RecyclerView
        MyRecyclerView = (RecyclerView) view.findViewById(R.id.noticeboard);

        mLayoutManager = new LinearLayoutManager(this.getActivity());

        MyRecyclerView.setLayoutManager(mLayoutManager);
        // preparePostData();


        adapter = new NoticeAdapter(postlist);




        scrollListener = new EndlessRecyclerViewScrollListener(mLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        // Adds the scroll listener to RecyclerView
        MyRecyclerView.addOnScrollListener(scrollListener);




        fetchStores();
        return view;
    }



    // Append the next page of data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void loadNextDataFromApi(int offset) {
        // Send an API request to retrieve appropriate paginated data
        //  --> Send the request including an offset value (i.e `page`) as a query parameter.
        //  --> Deserialize and construct new model objects from the API response
        //  --> Append the new data objects to the existing set of items inside the array of items
        //  --> Notify the adapter of the new items made with `notifyItemRangeInserted()`

        Toast.makeText(getActivity(), "new data loading", Toast.LENGTH_SHORT).show();

        final Integer count =Integer.parseInt(dataoffset)+10;
        dataoffset=count.toString();


        JsonObjectRequest fetchAllStores = new JsonObjectRequest(Request.Method.POST, "http://timepasstoday.com/fetchnotifcation.php?dataoffset="+count.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d(TAG, "more data query : " + "http://timepasstoday.com/fetchnotifcation.php?dataoffset="+count.toString());

                Log.d(TAG, "Fetch Stores: " + response);
                showStores(response);
                //MyRecyclerView.setAdapter(adapter);
                System.err.println("adpter attached");
                adapter.notifyDataSetChanged();
                System.err.println("data set changed attached");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Fetch Stores Error: " + error.getMessage());
            }
        });
        ApplicationController.getInstance().addToRequestQueue(fetchAllStores);
    }


    public ArrayList<NoticeData> jsonToMap(String jsonStr) throws JSONException {


        HashMap<String, String> contact = new HashMap<>();

        ArrayList<NoticeData> al = new ArrayList<NoticeData>();

        if (jsonStr != null) {

            System.err.print("josn strin is");

            System.err.print(jsonStr);

            System.err.print("josn strin ends");


            try {
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray contacts = jsonObj.getJSONArray("tasks");

                // looping through All Contacts
                JSONArray c = jsonObj.getJSONArray("tasks");
                for (int i = 0; i < c.length(); i++) {
                    JSONObject obj = c.getJSONObject(i);
                    String id = obj.getString("id");

                    String title = obj.getString("title");

                    String desc = obj.getString("desc");
                    String date = obj.getString("date");



                    NoticeData cc = new NoticeData();

                    cc.setId(id);
                    cc.setTitle(title);
                    cc.setDesc(desc);
                    cc.setDate(date);

                 /*   GetBitmapfromUrlThread  getBitmapfromUrlThread=new GetBitmapfromUrlThread();
                    getBitmapfromUrlThread.execute(img);
                    Bitmap bb=getBitmapfromUrlThread.get();


                    cc.setBitpostpic(bb);





                    GetBitmapProfilefromUrlThread  getBitmapProfilefromUrlThread=new GetBitmapProfilefromUrlThread();

                    getBitmapProfilefromUrlThread.execute(profileimg);
                    Bitmap ccc=getBitmapProfilefromUrlThread.get();


                    cc.setBitprofilepic(ccc);
*/
                    al.add(cc);


                }


            } catch (Exception e) {
                e.printStackTrace();


            }
        }

        return al;
    }

    public void initializeList(ArrayList<NoticeData> al) {
        postlist.clear();

        for (NoticeData cd : al) {


            NoticeData item = new NoticeData();


            item.setId(cd.getId());
            item.setTitle(cd.getTitle());
            item.setDesc(cd.getDesc());
            item.setDate(cd.getDate());

            //item.setPostVideo(cd.getPostVideo());

            postlist.add(item);




        }


        System.err.println("data added in list "+postlist);


    }







   /* private void fetchStores() {


        JsonObjectRequest fetchAllStores = new JsonObjectRequest(Request.Method.POST, API.GET_STORES, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, "Fetch Stores: " + response);
                showStores(response);
                shopsRecyclerView.setAdapter(storeAdapter);
                storeAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Fetch Stores Error: " + error.getMessage());
            }
        });
        ApplicationController.getInstance().addToRequestQueue(fetchAllStores);
    }*/


    private void fetchStores() {

        Log.d(TAG, "Data query : " + "http://timepasstoday.com/fetchnotifcation.php?dataoffset="+dataoffset);


        JsonObjectRequest fetchAllStores = new JsonObjectRequest(Request.Method.POST, "http://timepasstoday.com/fetchnotifcation.php?dataoffset="+dataoffset, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d(TAG, "Data query : " + "http://timepasstoday.com/fetchnotifcation.php?dataoffset="+dataoffset);

                Log.d(TAG, "Fetch Stores: " + response);
                showStores(response);
                MyRecyclerView.setAdapter(adapter);
                System.err.println("adpter attached");
                adapter.notifyDataSetChanged();
                System.err.println("data set changed attached");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Fetch Stores Error: " + error.getMessage());
            }
        });
        ApplicationController.getInstance().addToRequestQueue(fetchAllStores);
    }

    private void showStores(JSONObject response) {
        try {
            JSONArray contacts = response.getJSONArray("tasks");

            // looping through All Contacts
            JSONArray c = response.getJSONArray("tasks");
            for (int i = 0; i < c.length(); i++) {
                JSONObject obj = c.getJSONObject(i);
                String id = obj.getString("id");


                String title = obj.getString("title");

                String desc = obj.getString("desc");
                String date = obj.getString("date");


                NoticeData cc = new NoticeData();

                cc.setId(id);
                cc.setTitle(title);
                cc.setDesc(desc);
                cc.setDate(date);
                postlist.add(cc);
            }
        } catch (JSONException e) {
            Log.d(TAG, "Show Stores: " + e.getMessage());
        }
    }
}

