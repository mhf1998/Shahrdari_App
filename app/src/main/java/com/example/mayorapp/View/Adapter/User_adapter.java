package com.example.mayorapp.View.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayorapp.Model.User;
import com.example.mayorapp.data.serverConstants;
import com.example.mayorapp.databinding.UserViewBinding;
import com.loopj.android.http.HttpGet;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.ClientProtocolException;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;

public class User_adapter extends RecyclerView.Adapter<User_adapter.User_ViewHolder> {
    private List<User> users=new ArrayList<>();
    private static Context context;
    private String TAG="ADAPTER";
    private UserViewBinding binding;
    private final OnItemClickListener listener;

    public User_adapter(List<User> users, Context context, OnItemClickListener listener) {
        this.users = users;
        this.context = context;
        this.listener = listener;
    }
    public User_adapter(Context context,OnItemClickListener listener){
        this.context=context;
        this.listener=listener;
    }
    public void setList(List<User> users, Context baseContext) {
        this.users=users;
        this.context=baseContext;
    }
    @NonNull
    @Override
    public User_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=UserViewBinding.inflate(LayoutInflater.from(context),parent,false);
        return new User_ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final User_ViewHolder holder, int position) {
        Boolean status;
        holder.user=users.get(position);
        holder.viewBinding.nameViewUser.setText(users.get(position).getName());
        holder.viewBinding.lnameViewUser.setText(users.get(position).getLname());
        holder.viewBinding.codeMeliViewUser.setText(users.get(position).getMeli());
        holder.viewBinding.regionViewUser.setText(users.get(position).getRegion());
        holder.username_view=users.get(position).getUsername();
        String status_string=users.get(position).getStatus();
        Log.i(TAG, "onBindViewHolder: "+status_string);
        holder.viewBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.user);
            }
        });
        if (status_string.equals("Active")){
            status=false;
        }
        else{
            status=true;
        }
        holder.viewBinding.statusUserView.setChecked(status);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }




    public static class User_ViewHolder extends RecyclerView.ViewHolder{
        private User user;
        private String TAG="user_view_Holder";
        private String username_view="";

        private UserViewBinding viewBinding;

        public User_ViewHolder(@NonNull final UserViewBinding itemView) {
            super(itemView.getRoot());
            viewBinding=itemView;

            viewBinding.statusUserView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        int blockdone = 0;
                        blockdone=blockUser();
                        if (blockdone == 0) {
                            viewBinding.statusUserView.setText("غیرفعال شده");
                        }
                        else{
                            viewBinding.statusUserView.setText("فعال");
                        }
                    }
                    else {
                        int activedone = 0;
                        activedone=activeUser();
                        if (activedone == 0) {
                            viewBinding.statusUserView.setText("فعال");
                        }
                        else{
                            viewBinding.statusUserView.setText("غیرفعال شده");
                        }

                    }
                }
            });
        }

        private int activeUser() {
            final int[] activedone = {0};
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String formated_URL = serverConstants.activeUser +"?username=" + username_view;
                        Log.i(TAG, "run: "+formated_URL);
                        URL url = new URL(formated_URL);
                        HttpClient client = new DefaultHttpClient();
                        HttpGet request = new HttpGet();
                        request.setURI(new URI(formated_URL));
                        HttpResponse response = client.execute(request);
                        BufferedReader in = new BufferedReader(new
                                InputStreamReader(response.getEntity().getContent()));
                        StringBuffer sb = new StringBuffer("");
                        String line="";
                        while ((line = in.readLine()) != null) { sb.append(line); }
                        in.close();
                        String http_get_response = "";
                        http_get_response = sb.toString();
                        Log.i(TAG, "run: "+http_get_response);
                        JSONObject user_obj = new JSONObject(http_get_response); //convert string to json object
                        String get_response = user_obj.getString("response");
                        if (get_response.equals("Active user")) {
                            activedone[0]=1;

                            //runOnUiThread(new Runnable() {
                            //    @Override
                            //    public void run() {
                            //Toast.makeText(User_adapter.context, "کاربر فعال شده", Toast.LENGTH_SHORT).show();
                            //    }
                            //});
                        }
                        else if (get_response.equals("failed")) {
                            Log.i(TAG, "run: failed active");
                            activedone[0]=0;
                            //Toast.makeText(User_adapter.context, "فعال کردن با خطا مواجه شد...", Toast.LENGTH_SHORT).show();
                            viewBinding.statusUserView.setChecked(true);
                        }
                    }
                    catch (MalformedURLException | URISyntaxException e) {
                        Log.i(TAG, "user_active: run: MalformedURLException");
                        e.printStackTrace();
                    } catch (ClientProtocolException e) {
                        Log.i(TAG, "user_active: run: ClientProtocolException");
                        e.printStackTrace();
                    } catch (IOException e) {
                        Log.i(TAG, "user_active: run: IOException");
                        e.printStackTrace();
                    } catch (JSONException e) {
                        Log.i(TAG, "user_active: run: JSONException");
                        e.printStackTrace();
                    }
                }
            }).start();
            return activedone[0];

        }

        private int blockUser() {

            final int[] blockdone = {0};
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Log.i(TAG, "run:username= "+username_view);
                        String formated_URL = serverConstants.blockUser + "?username=" + username_view;
                        Log.i(TAG, "run: "+formated_URL);
                        URL url = new URL(formated_URL);
                        HttpClient client = new DefaultHttpClient();
                        HttpGet request = new HttpGet();
                        request.setURI(new URI(formated_URL));
                        HttpResponse response = client.execute(request);
                        BufferedReader in = new BufferedReader(new
                                InputStreamReader(response.getEntity().getContent()));
                        StringBuffer sb = new StringBuffer("");
                        String line="";
                        while ((line = in.readLine()) != null) { sb.append(line); }
                        in.close();
                        String http_get_response = "";
                        http_get_response = sb.toString();
                        Log.i(TAG, "run: "+http_get_response);
                        JSONObject user_obj = new JSONObject(http_get_response); //convert string to json object
                        String get_response = user_obj.getString("response");
                        if (get_response.equals("block user")) {
                            blockdone[0] =1;


                        }
                        else if (get_response.equals("failed")) {
                            Log.i(TAG, "run: failed block");
                            blockdone[0]=0;
                            //Toast.makeText(User_adapter.context, "بلاک کردن با خطا مواجه شد...", Toast.LENGTH_SHORT).show();
                            viewBinding.statusUserView.setChecked(false);
                        }
                    }
                    catch (MalformedURLException | URISyntaxException e) {
                        Log.i(TAG, "user_block: run: MalformedURLException");
                        e.printStackTrace();
                    } catch (ClientProtocolException e) {
                        Log.i(TAG, "user_block: run: ClientProtocolException");
                        e.printStackTrace();
                    } catch (IOException e) {
                        Log.i(TAG, "user_block: run: IOException");
                        e.printStackTrace();
                    } catch (JSONException e) {
                        Log.i(TAG, "user_block: run: JSONException");
                        e.printStackTrace();
                    }
                }
            }).start();
            return blockdone[0];
        }
    }

    public interface OnItemClickListener{
        void onItemClick(User item);
    }
}
