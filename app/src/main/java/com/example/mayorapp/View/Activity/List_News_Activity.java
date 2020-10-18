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

import com.android.volley.toolbox.ImageLoader;
import com.example.mayorapp.BaseApplication;
import com.example.mayorapp.Model.News;
import com.example.mayorapp.R;
import com.example.mayorapp.Services.CustomVolleyRequest;
import com.example.mayorapp.Services.DateConverter;
import com.example.mayorapp.View.Adapter.News_adapter;
import com.example.mayorapp.ViewModel.NewsViewModel;
import com.example.mayorapp.data.serverConstants;
import com.example.mayorapp.databinding.ActivityListNewsBinding;
import com.example.mayorapp.databinding.DialogNewsLayoutBinding;

import java.util.List;

import javax.inject.Inject;

public class List_News_Activity extends AppCompatActivity {
    private ActivityListNewsBinding binding;
    private News_adapter news_adapter;
    private RecyclerView.LayoutManager layoutManager;
    public String from="";
    public String to="";
    List<News> newses;
    private String TAG="NEWS-LIST-ACTIVITY";
    String dayfrom="";
    String monthfrom="";
    String yearfrom="";
    String dayto="";
    String monthto="";
    String yearto="";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //viewbinding
        binding = ActivityListNewsBinding.inflate(getLayoutInflater());
        final View view = binding.getRoot();
        setContentView(view);

        ((BaseApplication) getApplication()).getAppComponent().inject(this);

        getSupportActionBar().hide();

        ((BaseApplication)getApplication()).getAppComponent().inject(this);

        final NewsViewModel newsViewModel=new ViewModelProvider(this,viewModelFactory).get(NewsViewModel.class);
        layoutManager = new LinearLayoutManager(List_News_Activity.this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerviewNews.setLayoutManager(layoutManager);
        news_adapter=new News_adapter(getBaseContext(), new News_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(News item) {
                Log.i(TAG, "onClick:");
                DialogNewsLayoutBinding dialogNewsLayoutBinding;
                dialogNewsLayoutBinding=DialogNewsLayoutBinding.inflate(LayoutInflater.from(List_News_Activity.this));
                AlertDialog.Builder builder = new AlertDialog.Builder(List_News_Activity.this);
                builder.setCancelable(true)
                        .setView(dialogNewsLayoutBinding.getRoot());
                final AlertDialog dialog = builder.create();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageLoader imageLoader;
                String image_url = serverConstants.ROOT_URL + item.getPic();
                imageLoader = CustomVolleyRequest.getInstance(List_News_Activity.this).getImageLoader();
                imageLoader.get(image_url, ImageLoader.getImageListener(dialogNewsLayoutBinding.imageNewsDialog, R.drawable.ic_launcher_background, android.R.drawable
                        .ic_dialog_alert));
                //convert date to shamsi
                String datemiladi=item.getDate();
                String[] date2=datemiladi.split("-");
                DateConverter dateConvertertoshamsi=new DateConverter();
                dateConvertertoshamsi.gregorianToPersian(Integer.valueOf(date2[0]),Integer.valueOf(date2[1]),Integer.valueOf(date2[2]));
                String shamsi=dateConvertertoshamsi.getYear()+"-"+dateConvertertoshamsi.getMonth()+"-"+dateConvertertoshamsi.getDay();

                dialogNewsLayoutBinding.subjectNewsDialog.setText(item.getSubject());
                dialogNewsLayoutBinding.dateNewsDialog.setText(shamsi);
                dialogNewsLayoutBinding.bodyNewsDialog.setText(item.getBody());
                dialogNewsLayoutBinding.seenNewsDialog.setText("تعداد بازدید: "+item.getSeen()+" نفر");
                dialogNewsLayoutBinding.imageNewsDialog.setImageUrl(image_url, imageLoader);

                dialog.show();
            }
        });

        if (!newsViewModel.from.equals("") || !newsViewModel.to.equals("")){
            newsViewModel.getNewses(from,to,"create").observe(this, new Observer<List<News>>() {
                @Override
                public void onChanged(List<News> news) {
                    if (news.size()!=0) {
                        binding.nofindnews.setVisibility(View.GONE);
                        binding.recyclerviewNews.setVisibility(View.VISIBLE);
                        news_adapter.setList(news, getBaseContext());
                        binding.recyclerviewNews.setAdapter(news_adapter);
                        news_adapter.notifyDataSetChanged();
                    }
                    else {
                        binding.nofindnews.setVisibility(View.VISIBLE);
                        binding.recyclerviewNews.setVisibility(View.GONE);
                    }
                }
            });

        }

        binding.searchNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                binding.nofindnews.setVisibility(View.GONE);

                dayfrom=binding.dayNewsFrom.getText().toString().trim();
                monthfrom=binding.monthNewsFrom.getText().toString().trim();
                yearfrom=binding.yearNewsFrom.getText().toString().trim();

                dayto=binding.dayNewsTo.getText().toString().trim();
                monthto=binding.monthNewsTo.getText().toString().trim();
                yearto=binding.yearNewsTo.getText().toString().trim();

                if (dayfrom.equals("") || monthfrom.equals("") || yearfrom.equals("") || dayto.equals("") || monthto.equals("") || yearto.equals(""))
                {
                    binding.recyclerviewNews.setVisibility(View.GONE);
                    Toast.makeText(List_News_Activity.this, "همه فیلد ها را وارد کنید..", Toast.LENGTH_SHORT).show();
                }
                else {
                    DateConverter dateConverter_from = new DateConverter();
                    dateConverter_from.persianToGregorian(Integer.valueOf(yearfrom), Integer.valueOf(monthfrom), Integer.valueOf(dayfrom));

                    from = dateConverter_from.getYear() + "-" + dateConverter_from.getMonth() + "-" + dateConverter_from.getDay();


                    DateConverter dateConverter_to = new DateConverter();
                    dateConverter_to.persianToGregorian(Integer.valueOf(yearto), Integer.valueOf(monthto), Integer.valueOf(dayto));
                    to = dateConverter_to.getYear() + "-" + dateConverter_to.getMonth() + "-" + dateConverter_to.getDay();

                    newsViewModel
                            .getNewses(from,to,"onclick")
                            .observe(List_News_Activity.this , new Observer<List<News>>() {
                        @Override
                        public void onChanged(List<News> news) {
                            if (news.size()!=0) {
                                binding.nofindnews.setVisibility(View.GONE);
                                binding.recyclerviewNews.setVisibility(View.VISIBLE);
                                news_adapter.setList(news, getBaseContext());
                                binding.recyclerviewNews.setAdapter(news_adapter);
                            }
                            else {
                                binding.recyclerviewNews.setVisibility(View.GONE);
                                binding.nofindnews.setVisibility(View.VISIBLE);
                            }

                        }
                    });

                }


            }
        });



    }

    /*private List<News> get_newses_from_server() {
        final List<News> n=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String formated_URL = serverConstants.getNewsFromTo+"?from="+from+"&to="+to;

                    Log.i(TAG, "run: get news from server 109: url="+formated_URL );
                    
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

                    Log.i(TAG, "run: get news from server 125: response"+http_get_response);
                    if (http_get_response.equals("{\"response\":\"No News\"}")) {
                        Log.i(TAG, "run: NO FIND NEWS!");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(List_News_Activity.this, "هیچ خبری یافت نشد...", Toast.LENGTH_LONG).show();
                            }
                        });
                    }else if (!http_get_response.equals("[]")){
                        Log.i(TAG, "run: newses finded !!");
                        JSONArray jsonArray = new JSONArray(http_get_response);
                        for (int i=0; i<jsonArray.length(); i++){
                            News temp = new News();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            temp.setSubject(jsonObject.getString("subject"));
                            temp.setBody(jsonObject.getString("body"));
                            temp.setPic(jsonObject.getString("pic"));
                            temp.setDate(jsonObject.getString("date"));
                            temp.setSeen(jsonObject.getString("seen"));

                            n.add(temp);
                            Log.i(TAG, "run: find news");

                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.nofindnews.setVisibility(View.GONE);
                                binding.recyclerviewNews.setVisibility(View.VISIBLE);
                                news_adapter.notifyDataSetChanged();
                            }
                        });
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.recyclerviewNews.setVisibility(View.GONE);
                                binding.nofindnews.setVisibility(View.VISIBLE);
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

        return n;
    }*/
}