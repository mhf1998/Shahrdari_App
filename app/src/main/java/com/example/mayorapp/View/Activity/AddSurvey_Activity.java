package com.example.mayorapp.View.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mayorapp.BaseApplication;
import com.example.mayorapp.Model.MyResponse;
import com.example.mayorapp.R;
import com.example.mayorapp.ViewModel.AddSurveyViewModel;
import com.example.mayorapp.databinding.ActivityAddSurveyBinding;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

public class AddSurvey_Activity extends AppCompatActivity {
    private ActivityAddSurveyBinding binding;
    private static String subject="";
    private static String opt1="";
    private static String opt2="";
    private static String opt3="";
    private static String opt4="";
    private int generic=1;
    private String uid="";
    private String region="";
    private String regionserver="";
    private String TAG="ADD_SURVEY";
    private Uri imageUri;
    private boolean isPicAdded=false;
    private String encoded_pic="";
    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //viewbinding
        binding = ActivityAddSurveyBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ((BaseApplication) getApplication()).getAppComponent().inject(this);

        getSupportActionBar().hide();
        uid= MainActivity.userID;
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                AddSurvey_Activity.this, R.array.region_arrey, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerRegion.setAdapter(adapter);
        final AddSurveyViewModel addSurveyViewModel;
        addSurveyViewModel = new ViewModelProvider(AddSurvey_Activity.this,viewModelFactory).get(AddSurveyViewModel.class);


        binding.checkGeneric.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    generic=0;
                    binding.spinnerRegion.setVisibility(View.VISIBLE);

                }
                else{
                    generic=1;
                    binding.spinnerRegion.setVisibility(View.GONE);
                }


            }
        });

        binding.spinnerRegion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                region=(String)parent.getItemAtPosition(position);
                Log.i(TAG, "onItemSelected: "+ region);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.checkAddOpt.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    binding.textInputLayoutAddSurvey4.setVisibility(View.VISIBLE);
                    binding.textInputLayoutAddSurvey5.setVisibility(View.VISIBLE);
                }
                else{
                    binding.textInputLayoutAddSurvey4.setVisibility(View.GONE);
                    binding.textInputLayoutAddSurvey5.setVisibility(View.GONE);
                }
            }
        });


        binding.checkAddPic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    binding.buttonAddPicSurvey.setVisibility(View.VISIBLE);
                    binding.imageviewAddSurveyPic.setVisibility(View.VISIBLE);
                }
                else {
                    binding.buttonAddPicSurvey.setVisibility(View.GONE);
                    binding.imageviewAddSurveyPic.setVisibility(View.GONE);
                }
            }
        });

        binding.buttonAddPicSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPic();
            }
        });

        binding.buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subject=binding.editTextSubjectAddSurvey.getText().toString().trim();
                opt1=binding.editTextOp1AddSurvey.getText().toString().trim();
                opt2=binding.editTextOp2AddSurvey.getText().toString().trim();
                opt3=binding.editTextOp3AddSurvey.getText().toString().trim();
                opt4=binding.editTextOp4AddSurvey.getText().toString().trim();

                if (binding.checkAddOpt.isChecked()){
                    if (!subject.equals("") & !opt1.equals("") & !opt2.equals("") & !opt3.equals("") & !opt4.equals("") ){
                        if (binding.checkAddPic.isChecked()){
                            if (isPicAdded){
                                addSurveyViewModel.addsurvey(4,uid,subject,encoded_pic,String.valueOf(generic),opt1,opt2,opt3,opt4,region).observe(AddSurvey_Activity.this, new Observer<MyResponse>() {
                                    @Override
                                    public void onChanged(MyResponse response) {
                                        Log.i(TAG, "onChanged: "+ response.getResponse());
                                        if (response.getResponse().equals("inserted")){
                                            Toast.makeText(AddSurvey_Activity.this, "نظرسنجی با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (response.getResponse().equals("failed")){
                                            Toast.makeText(AddSurvey_Activity.this, "اضافه کردن نظرسنجی با خطا مواجه شد...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                //add_survey_to_server_4_pic();
                            }
                            else {
                                Toast.makeText(AddSurvey_Activity.this, "عکس بارگزاری نشد... دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else {
                            Log.i(TAG, "onClick: okadd");
                            addSurveyViewModel.addsurvey(2,uid,subject,"Undefined",String.valueOf(generic),opt1,opt2,opt3,opt4,region).observe(AddSurvey_Activity.this, new Observer<MyResponse>() {
                                @Override
                                public void onChanged(MyResponse myResponse) {
                                    if (myResponse.getResponse().equals("inserted")){
                                        Toast.makeText(AddSurvey_Activity.this, "نظرسنجی با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (myResponse.getResponse().equals("failed")){
                                        Toast.makeText(AddSurvey_Activity.this, "اضافه کردن نظرسنجی با خطا مواجه شد...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            //add_survey_to_server_4();
                        }
                    }
                    else{
                        Toast.makeText(AddSurvey_Activity.this, "لطفا همه ی بخش ها را پر کنید", Toast.LENGTH_SHORT).show();
                        Log.i(TAG, "onClick: error ");
                    }
                }
                else{
                    if (!subject.equals("") &  !opt1.equals("") & !opt2.equals("")){
                        if (binding.checkAddPic.isChecked()) {
                            if (isPicAdded){
                                addSurveyViewModel.addsurvey(3,uid,subject,encoded_pic,String.valueOf(generic),opt1,opt2,"","",region).observe(AddSurvey_Activity.this, new Observer<MyResponse>() {
                                    @Override
                                    public void onChanged(MyResponse myResponse) {
                                        if (myResponse.getResponse().equals("inserted")){
                                            Toast.makeText(AddSurvey_Activity.this, "نظرسنجی با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                                        }
                                        else if (myResponse.getResponse().equals("failed")){
                                            Toast.makeText(AddSurvey_Activity.this, "اضافه کردن نظرسنجی با خطا مواجه شد...", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                //add_survey_to_server_2_pic();
                            }
                            else {
                                Toast.makeText(AddSurvey_Activity.this, "عکس بارگزاری نشد... دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            addSurveyViewModel.addsurvey(1,uid,subject,"Undefined",String.valueOf(generic),opt1,opt2,"","",region).observe(AddSurvey_Activity.this, new Observer<MyResponse>() {
                                @Override
                                public void onChanged(MyResponse myResponse) {
                                    if (myResponse.getResponse().equals("inserted")){
                                        Toast.makeText(AddSurvey_Activity.this, "نظرسنجی با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (myResponse.getResponse().equals("failed")){
                                        Toast.makeText(AddSurvey_Activity.this, "اضافه کردن نظرسنجی با خطا مواجه شد...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            //add_survey_to_server_2();
                        }
                    }
                    else{
                        Toast.makeText(AddSurvey_Activity.this, "لطفا همه ی بخش ها را پر کنید", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    private void addPic() {
        Intent gallery=new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 100);   //100 FOR PICK IMAGE
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100){
            imageUri = data.getData();
            binding.imageviewAddSurveyPic.setImageURI(imageUri);
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(imageUri,
                    filePathColumn, null, null, null);
            if (cursor == null) throw new AssertionError();
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap orginal_bitmap = BitmapFactory.decodeFile(picturePath);
            Bitmap resized_bitmap = Bitmap.createScaledBitmap(orginal_bitmap, 200, 200, true);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            resized_bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            byte[] bytes = outputStream.toByteArray();
            encoded_pic = Base64.encodeToString(bytes, Base64.DEFAULT);

            isPicAdded = true;
        }
        else{
            isPicAdded = false;
        }
    }

    /*private void add_survey_to_server_2() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String data = URLEncoder.encode("u_id", "UTF-8") + "=" +
                            URLEncoder.encode(uid, "UTF-8");
                    data += "&" + URLEncoder.encode("subject", "UTF-8") + "=" +
                            URLEncoder.encode(subject, "UTF-8");
                    data += "&" + URLEncoder.encode("pic", "UTF-8") + "=" +
                            URLEncoder.encode("Undefined", "UTF-8");
                    data += "&" + URLEncoder.encode("gen", "UTF-8") + "=" +
                            URLEncoder.encode(String.valueOf(generic), "UTF-8");
                    data += "&" + URLEncoder.encode("op1", "UTF-8") + "=" +
                            URLEncoder.encode(opt1, "UTF-8");
                    data += "&" + URLEncoder.encode("op2", "UTF-8") + "=" +
                            URLEncoder.encode(opt2, "UTF-8");
                    data += "&" + URLEncoder.encode("region", "UTF-8") + "=" +
                            URLEncoder.encode(region, "UTF-8");

                    URL url = new URL(serverConstants.addSurvey2op_URL);
                    Log.i(TAG, "run: "+url.toString());
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write( data );
                    wr.flush();
                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));
                    final StringBuilder sb = new StringBuilder();
                    String line;

                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    Log.i(TAG, "run: "+sb.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (sb.toString().equals("{\"response\":\"inserted\"}")){
                                Toast.makeText(AddSurvey_Activity.this, "نظرسنجی با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                            }else if (sb.toString().equals("{\"response\":\"failed\"}")){
                                Toast.makeText(AddSurvey_Activity.this, "اضافه کردن نظرسنجی با خطا مواجه شد", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void add_survey_to_server_4() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String data = URLEncoder.encode("u_id", "UTF-8") + "=" +
                            URLEncoder.encode(uid, "UTF-8");
                    data += "&" + URLEncoder.encode("subject", "UTF-8") + "=" +
                            URLEncoder.encode(subject, "UTF-8");
                    data += "&" + URLEncoder.encode("pic", "UTF-8") + "=" +
                            URLEncoder.encode("Undefined", "UTF-8");
                    data += "&" + URLEncoder.encode("gen", "UTF-8") + "=" +
                            URLEncoder.encode(String.valueOf(generic), "UTF-8");
                    data += "&" + URLEncoder.encode("op1", "UTF-8") + "=" +
                            URLEncoder.encode(opt1, "UTF-8");
                    data += "&" + URLEncoder.encode("op2", "UTF-8") + "=" +
                            URLEncoder.encode(opt2, "UTF-8");
                    data += "&" + URLEncoder.encode("op3", "UTF-8") + "=" +
                            URLEncoder.encode(opt3, "UTF-8");
                    data += "&" + URLEncoder.encode("op4", "UTF-8") + "=" +
                            URLEncoder.encode(opt4, "UTF-8");
                    data += "&" + URLEncoder.encode("region", "UTF-8") + "=" +
                            URLEncoder.encode(region, "UTF-8");
                    URL url = new URL(serverConstants.addSurvey4op_URL);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write( data );
                    wr.flush();
                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));
                    final StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (sb.toString().equals("{\"response\":\"inserted\"}")){
                                Toast.makeText(AddSurvey_Activity.this, "نظرسنجی با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                            }else if (sb.toString().equals("{\"response\":\"failed\"}")){
                                Toast.makeText(AddSurvey_Activity.this, "اضافه کردن نظرسنجی با خطا مواجه شد", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    private void add_survey_to_server_2_pic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String data = URLEncoder.encode("u_id", "UTF-8") + "=" +
                            URLEncoder.encode(uid, "UTF-8");
                    data += "&" + URLEncoder.encode("subject", "UTF-8") + "=" +
                            URLEncoder.encode(subject, "UTF-8");
                    data += "&" + URLEncoder.encode("pic", "UTF-8") + "=" +
                            URLEncoder.encode(encoded_pic, "UTF-8");
                    data += "&" + URLEncoder.encode("gen", "UTF-8") + "=" +
                            URLEncoder.encode(String.valueOf(generic), "UTF-8");
                    data += "&" + URLEncoder.encode("op1", "UTF-8") + "=" +
                            URLEncoder.encode(opt1, "UTF-8");
                    data += "&" + URLEncoder.encode("op2", "UTF-8") + "=" +
                            URLEncoder.encode(opt2, "UTF-8");
                    data += "&" + URLEncoder.encode("region", "UTF-8") + "=" +
                            URLEncoder.encode(region, "UTF-8");
                    URL url = new URL(serverConstants.addSurvey2opPIC_URL);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write( data );
                    wr.flush();
                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));
                    final StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null) { sb.append(line); }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (sb.toString().equals("{\"response\":\"inserted\"}")){
                                Toast.makeText(AddSurvey_Activity.this, "نظرسنجی با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                            }else if (sb.toString().equals("{\"response\":\"failed\"}")){
                                Toast.makeText(AddSurvey_Activity.this, "اضافه کردن نظرسنجی با خطا مواجه شد", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void add_survey_to_server_4_pic() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String data = URLEncoder.encode("u_id", "UTF-8") + "=" +
                            URLEncoder.encode(uid, "UTF-8");
                    data += "&" + URLEncoder.encode("subject", "UTF-8") + "=" +
                            URLEncoder.encode(subject, "UTF-8");
                    data += "&" + URLEncoder.encode("pic", "UTF-8") + "=" +
                            URLEncoder.encode(encoded_pic, "UTF-8");
                    data += "&" + URLEncoder.encode("gen", "UTF-8") + "=" +
                            URLEncoder.encode(String.valueOf(generic), "UTF-8");
                    data += "&" + URLEncoder.encode("op1", "UTF-8") + "=" +
                            URLEncoder.encode(opt1, "UTF-8");
                    data += "&" + URLEncoder.encode("op2", "UTF-8") + "=" +
                            URLEncoder.encode(opt2, "UTF-8");
                    data += "&" + URLEncoder.encode("op3", "UTF-8") + "=" +
                            URLEncoder.encode(opt3, "UTF-8");
                    data += "&" + URLEncoder.encode("op4", "UTF-8") + "=" +
                            URLEncoder.encode(opt4, "UTF-8");
                    data += "&" + URLEncoder.encode("region", "UTF-8") + "=" +
                            URLEncoder.encode(region, "UTF-8");

                    URL url = new URL(serverConstants.addSurvey4opPIC_URL);
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write( data );
                    wr.flush();
                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));
                    final StringBuilder sb = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null) { sb.append(line); }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (sb.toString().equals("{\"response\":\"inserted\"}")){
                                Toast.makeText(AddSurvey_Activity.this, "نظرسنجی با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                            }else if (sb.toString().equals("{\"response\":\"failed\"}")){
                                Toast.makeText(AddSurvey_Activity.this, "اضافه کردن نظرسنجی با خطا مواجه شد", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/
}