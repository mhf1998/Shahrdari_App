package com.example.mayorapp.View.Activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mayorapp.BaseApplication;
import com.example.mayorapp.Model.User;
import com.example.mayorapp.View.Adapter.User_adapter;
import com.example.mayorapp.ViewModel.UserViewModel;
import com.example.mayorapp.databinding.ActivityUsersBinding;
import com.example.mayorapp.databinding.DialogUserLayoutBinding;

import java.util.List;

import javax.inject.Inject;

public class Users_Activity extends AppCompatActivity {
    private ActivityUsersBinding binding;
    private User_adapter user_adapter;
    private RecyclerView.LayoutManager layoutManager;
    List<User> users;
    private String TAG="USERS-LIST-ACTIVITY";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //viewbinding
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ((BaseApplication) getApplication()).getAppComponent().inject(this);

        getSupportActionBar().hide();
        final UserViewModel userViewModel= new ViewModelProvider(this,viewModelFactory).get(UserViewModel.class);
        layoutManager = new LinearLayoutManager(Users_Activity.this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerviewUsers.setLayoutManager(layoutManager);
        user_adapter=new User_adapter(Users_Activity.this, new User_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(User item) {
                Log.i(TAG, "onItemClick: ");
                DialogUserLayoutBinding dialogUserLayoutBinding=DialogUserLayoutBinding.inflate(LayoutInflater.from(Users_Activity.this));
                AlertDialog.Builder builder=new AlertDialog.Builder(Users_Activity.this);
                builder.setCancelable(true)
                        .setView(dialogUserLayoutBinding.getRoot());
                final AlertDialog dialog=builder.create();
                dialogUserLayoutBinding.nameUserDialog.setText(item.getName());
                dialogUserLayoutBinding.lnameUserDialog.setText(item.getLname());
                dialogUserLayoutBinding.codeUserDialog.setText(item.getMeli());
                dialogUserLayoutBinding.phoneUserDialog.setText(item.getPhone());
                dialogUserLayoutBinding.mailUserDialog.setText(item.getEmail());
                dialogUserLayoutBinding.regionUserDialog.setText(item.getRegion());
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            }
        });



//        users=get_users_from_server();
        userViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if (users.size()!=0) {
                    user_adapter.setList(users, getBaseContext());
                    binding.recyclerviewUsers.setAdapter(user_adapter);
                    user_adapter.notifyDataSetChanged();
                }
                else {
                    Toast.makeText(Users_Activity.this, "هیچ کاربری یافت نشد!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

   /* private List<User> get_users_from_server() {
        final List<User> u=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String formated_URL = serverConstants.getUsers;
                    URL url = new URL(formated_URL);
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(formated_URL));
                    HttpResponse response = client.execute(request);
                    BufferedReader in = new BufferedReader(new
                            InputStreamReader(response.getEntity().getContent()));
                    StringBuilder sb = new StringBuilder("");
                    String line = "";
                    while ((line = in.readLine()) != null) { sb.append(line); }
                    in.close();
                    String http_get_response = "";
                    http_get_response = sb.toString();
                    if (http_get_response.equals("{\"response\":\"Not Find Users\"}")) {
                        Log.i(TAG, "run: NO FIND USERS!");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Users_Activity.this, "هیچ کاربری یافت نشد...", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        JSONArray jsonArray = new JSONArray(http_get_response);
                        for (int i=0; i<jsonArray.length(); i++){
                            User temp = new User();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            temp.setU_id(jsonObject.getString("u_id"));
                            temp.setName(jsonObject.getString("name"));
                            temp.setLname(jsonObject.getString("lname"));
                            temp.setMeli(jsonObject.getString("meli"));
                            temp.setEmail(jsonObject.getString("email"));
                            temp.setPhone(jsonObject.getString("phone"));
                            temp.setRegion(jsonObject.getString("region"));
                            temp.setStatus(jsonObject.getString("status"));
                            temp.setUsername(jsonObject.getString("username"));
                            u.add(temp);
                            Log.i(TAG, "run: find users");

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                user_adapter.notifyDataSetChanged();
                            }
                        });
                    }


                } catch (MalformedURLException | URISyntaxException e) {
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return u;
    }*/

}