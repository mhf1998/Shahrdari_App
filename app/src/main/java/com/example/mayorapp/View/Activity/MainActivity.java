package com.example.mayorapp.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mayorapp.BaseApplication;
import com.example.mayorapp.Model.MyResponse;
import com.example.mayorapp.ViewModel.SignInViewModel;
import com.example.mayorapp.databinding.ActivityMainBinding;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    public static String username="";
    public String password="";
    private String TAG="SIGN-IN";
    public static String userID = "";
    public String name = "";
    public String lname = "";
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //viewbinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ((BaseApplication) getApplication()).getAppComponent().inject(this);


        final SignInViewModel signInViewModel=new ViewModelProvider(this, viewModelFactory).get(SignInViewModel.class);

        //getActionBar().hide();
        getSupportActionBar().hide();
        username=binding.editTextSigninDialogUser.getText().toString().trim();
        password=binding.editTextSigninDialogPass.getText().toString().trim();
        binding.btnSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInViewModel.signIn(binding.editTextSigninDialogUser.getText().toString().trim(),binding.editTextSigninDialogPass.getText().toString().trim()).observe(
                        MainActivity.this,
                        new Observer<MyResponse>() {
                            @Override
                            public void onChanged(MyResponse myResponse) {
                                if (myResponse.getResponse().equals("user is valid")){
                                    Toast.makeText(MainActivity.this, "خوش آمدید", Toast.LENGTH_SHORT).show();

                                    Intent intent=new Intent(MainActivity.this, MenuActivity.class);
                                    intent.putExtra("user",binding.editTextSigninDialogUser.getText().toString().trim());
                                    startActivity(intent);                                }
                                else if (myResponse.getResponse().equals("Blocked user")){
                                    Toast.makeText(MainActivity.this, "دسترسی شما غیرمجاز است!", Toast.LENGTH_SHORT).show();
                                }
                                else if (myResponse.getResponse().equals("invalid user")){
                                    Toast.makeText(MainActivity.this, "چنین کاربری یافت نشد!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                );
                /*new Thread(new Runnable() {
                    String user_username=binding.editTextSigninDialogUser.getText().toString().trim();
                    String user_password=binding.editTextSigninDialogPass.getText().toString().trim();
                    @Override
                    public void run() {
                        try {
                            String formated_URL = serverConstants.ROOT_URL+"userAuth.php?username="+ user_username +"&password="+ user_password;
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
                            JSONObject signin_obj = new JSONObject(http_get_response); //convert string to json object
                            String get_response = signin_obj.getString("response");
                            if (get_response.equals("user is valid")) {
                                Log.i(TAG, "SignIn: run: Valid User");
                                String get_userID = signin_obj.getString("u_id");
                                String get_name = signin_obj.getString("name");
                                String get_lname = signin_obj.getString("lname");

                                userID=get_userID;
                                name=get_name;
                                lname=get_lname;

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "خوش آمدید", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                Bundle bundle=new Bundle();
                                bundle.putString("user",username);
                                Intent intent=new Intent(MainActivity.this, MenuActivity.class);
                                intent.putExtra("user",user_username);
                                startActivity(intent);
                            }
                            else if (get_response.equals("invalid user")) {
                                Log.i(TAG, "SignIn: run: INValid User");

                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "رمز یا نام کاربری اشتباه است", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                        catch (MalformedURLException | URISyntaxException e) {
                            Log.i(TAG, "SignIn: run: MalformedURLException");
                            e.printStackTrace();
                        } catch (ClientProtocolException e) {
                            Log.i(TAG, "SignIn: run: ClientProtocolException");
                            e.printStackTrace();
                        } catch (IOException e) {
                            Log.i(TAG, "SignIn: run: IOException");
                            e.printStackTrace();
                        } catch (JSONException e) {
                            Log.i(TAG, "SignIn: run: JSONException");
                            e.printStackTrace();
                        }
                    }
                }).start();*/
            }
        });
    }
}