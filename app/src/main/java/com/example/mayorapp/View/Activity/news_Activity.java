package com.example.mayorapp.View.Activity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.mayorapp.BaseApplication;
import com.example.mayorapp.Model.MyResponse;
import com.example.mayorapp.R;
import com.example.mayorapp.ViewModel.InsertNewsViewModel;
import com.example.mayorapp.databinding.ActivityNewsBinding;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;


public class news_Activity extends AppCompatActivity {
    private ActivityNewsBinding binding;
    public String tag_string="shahrdari";
    private String TAG1="ADD NEWS";
    private String TAG2="LIST NEWS";
    private Uri imageUri;
    private boolean isPicAdded=false;
    private String encoded_pic="";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //viewbinding
        binding = ActivityNewsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ((BaseApplication) getApplication()).getAppComponent().inject(this);


        final InsertNewsViewModel insertNewsViewModel=new ViewModelProvider(this,viewModelFactory).get(InsertNewsViewModel.class);
        getSupportActionBar().hide();
        binding.radioGroupNews.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.shahrdari_btn_news_tag){
                    tag_string="shahrdari";
                }
                else {
                    tag_string="kashan";
                }
            }
        });

        binding.btnPicNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPic();

            }
        });

        binding.btnAddNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.subjectNews.getText().toString().equals("") || binding.bodyNews.getText().toString().equals("") || isPicAdded==false){
                    Toast.makeText(news_Activity.this, "اطلاعات را به درستی وارد کنید", Toast.LENGTH_SHORT).show();
                }
                else {
                    insertNewsViewModel.insertNews(binding.subjectNews.getText().toString().trim(),tag_string,encoded_pic,binding.bodyNews.getText().toString().trim())
                            .observe(news_Activity.this, new Observer<MyResponse>() {
                                @Override
                                public void onChanged(MyResponse myResponse) {
                                    if (myResponse.getResponse().equals("inserted")){
                                        Toast.makeText(news_Activity.this, "خبر با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                                    }
                                    else if (myResponse.getResponse().equals("failed")){
                                        Toast.makeText(news_Activity.this, "افزودن خبر با خطا مواجه شد...", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                    //insert_news_to_server();
                }
            }
        });
        binding.btnNextList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoListNews();
            }
        });

        binding.linearBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoListNews();
            }
        });


    }

    private void GoListNews() {
        Intent intent = new Intent(news_Activity.this, List_News_Activity.class);
        startActivity(intent);
    }


    private void addPic() {
        Intent gallery=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, 100);   //100 FOR PICK IMAGE
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100){
            imageUri = data.getData();
            binding.imageNews.setImageURI(imageUri);
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

    /*private void insert_news_to_server() {
        final String sub=binding.subjectNews.getText().toString().trim();
        final String bod=binding.bodyNews.getText().toString().trim();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    String data = URLEncoder.encode("subject", "UTF-8") + "=" +
                            URLEncoder.encode(sub, "UTF-8");
                    data += "&" + URLEncoder.encode("tag", "UTF-8") + "=" +
                            URLEncoder.encode(tag_string, "UTF-8");
                    data += "&" + URLEncoder.encode("pic", "UTF-8") + "=" +
                            URLEncoder.encode(encoded_pic, "UTF-8");
                    data += "&" + URLEncoder.encode("body", "UTF-8") + "=" +
                            URLEncoder.encode(bod, "UTF-8");


                    URL url = new URL(serverConstants.insertNews);
                    Log.i(TAG1, "run: " + url.toString());
                    URLConnection conn = url.openConnection();
                    conn.setDoOutput(true);
                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                    wr.write(data);
                    wr.flush();
                    BufferedReader reader = new BufferedReader(new
                            InputStreamReader(conn.getInputStream()));
                    final StringBuilder sb = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    Log.i(TAG1, "run: " + sb.toString());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (sb.toString().equals("{\"response\":\"inserted\"}")) {
                                Toast.makeText(news_Activity.this, "خبر جدید با موفقیت اضافه شد", Toast.LENGTH_SHORT).show();
                            } else if (sb.toString().equals("{\"response\":\"failed\"}")) {
                                Toast.makeText(news_Activity.this, "اضافه کردن خبر با خطا مواجه شد", Toast.LENGTH_SHORT).show();
                            }
                            else if (sb.toString().equals("{\"response\":\"base64 is invalid\"}")){
                                Toast.makeText(news_Activity.this, "بارگزاری عکس با خطا مواجه شد", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }catch (UnsupportedEncodingException e) {
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