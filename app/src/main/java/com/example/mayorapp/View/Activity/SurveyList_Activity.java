package com.example.mayorapp.View.Activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.example.mayorapp.BaseApplication;
import com.example.mayorapp.Model.Option;
import com.example.mayorapp.Model.Survey;
import com.example.mayorapp.R;
import com.example.mayorapp.Services.CustomVolleyRequest;
import com.example.mayorapp.Services.DateConverter;
import com.example.mayorapp.View.Adapter.Survey_adapter;
import com.example.mayorapp.ViewModel.OptionViewModel;
import com.example.mayorapp.ViewModel.SurveyViewModel;
import com.example.mayorapp.data.serverConstants;
import com.example.mayorapp.databinding.ActivitySurveyListBinding;
import com.example.mayorapp.databinding.DialogSurveyLayoutBinding;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SurveyList_Activity extends AppCompatActivity {
    private ActivitySurveyListBinding binding;
    private Survey_adapter survey_adapter;
    private Survey_adapter survey_adapter2;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.LayoutManager layoutManager2;

    public String from="";
    public String to="";
    List<Survey> surveys;
    List<Survey> surveys2;
    private String TAG="SURVEY-LIST-ACTIVITY";
    private int generic=1;
    public boolean zero=true;
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
        binding = ActivitySurveyListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ((BaseApplication) getApplication()).getAppComponent().inject(this);


        getSupportActionBar().hide();
        binding.radioGroupSurvey.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId==R.id.generic_survey_radio){
                    generic=1;
                } else if (checkedId==R.id.special_survey_radio) {
                    generic=0;
                }
                else if (checkedId==R.id.gen_and_spe){
                    generic=2;
                }
            }
        });
        final SurveyViewModel surveyViewModel= new ViewModelProvider(this,viewModelFactory).get(SurveyViewModel.class);
       // final SurveyViewModel surveyViewModel2=new ViewModelProvider(this,viewModelFactory).get(SurveyViewModel.class);
        layoutManager = new LinearLayoutManager(SurveyList_Activity.this, LinearLayoutManager.VERTICAL, false);
        //layoutManager2 = new LinearLayoutManager(SurveyList_Activity.this, LinearLayoutManager.VERTICAL, false);
        binding.recyclerviewSurvey.setLayoutManager(layoutManager);
        //binding.recyclerviewSurvey2.setLayoutManager(layoutManager2);
        survey_adapter=new Survey_adapter(getBaseContext(), new Survey_adapter.OnItemClickListener() {
            @Override
            public void onItemClick(Survey item) {
                final DialogSurveyLayoutBinding dialogSurveyLayoutBinding;
                dialogSurveyLayoutBinding = DialogSurveyLayoutBinding.inflate(LayoutInflater.from(SurveyList_Activity.this));
                AlertDialog.Builder builder = new AlertDialog.Builder(SurveyList_Activity.this);
                builder.setCancelable(true)
                        .setView(dialogSurveyLayoutBinding.getRoot());
                final AlertDialog dialog = builder.create();
                final OptionViewModel optionViewModel = new ViewModelProvider(SurveyList_Activity.this, viewModelFactory).get(OptionViewModel.class);
                optionViewModel.getOptions(item.getV_id()).observe(SurveyList_Activity.this, new Observer<List<Option>>() {
                    @Override
                    public void onChanged(List<Option> options) {
                        ArrayList entries = new ArrayList<>();
                        ArrayList labels = new ArrayList();
                        boolean NoOneVoted = true;
                        Log.i(TAG, "onClick: opt.size=" + options.size());
                        for (int i = 0; i < options.size(); i++) {

                            entries.add(new Entry(Integer.valueOf(options.get(i).getNum()), i));
                            labels.add(options.get(i).getOption());

                            if (options.get(i).getNum() == 0 && NoOneVoted == true) {
                                NoOneVoted = true;
                            } else {
                                NoOneVoted = false;
                            }
                            Log.i(TAG, "onClick:entries: " + entries.get(i));
                            Log.i(TAG, "onClick: labels:" + labels.get(i));
                        }
                        if (NoOneVoted == false) {
                            PieDataSet dataSet = new PieDataSet(entries, "");
                            dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                            PieData data = new PieData(labels, dataSet);
                            data.setValueTextSize(20);
                            dialogSurveyLayoutBinding.chart.setData(data);
                            dialogSurveyLayoutBinding.chart.setDescription("میزان نظر ها به هر گزینه");
                            dialogSurveyLayoutBinding.chart.animateY(5000);
                        } else {
                            dialogSurveyLayoutBinding.chart.setVisibility(View.GONE);
                            dialogSurveyLayoutBinding.nofind.setVisibility(View.VISIBLE);
                        }
                    }
                });
                Log.i(TAG, "onClick:");
                //LayoutInflater inflater = (LayoutInflater) (SurveyList_Activity.this).getSystemService(SurveyList_Activity.this.LAYOUT_INFLATER_SERVICE);
                //View view = inflater.inflate(R.layout.dialog_survey_layout, , false);

                dialogSurveyLayoutBinding.subjectSurveyDialog.setText(item.getSubject());
                ImageLoader imageLoader;
                String image_url2 = serverConstants.ROOT_URL + item.getPic();
                Log.i(TAG, "onClick: imageurl 2: " + image_url2);
                if (!image_url2.equals("Undefined")) {
                    imageLoader = CustomVolleyRequest.getInstance(SurveyList_Activity.this).getImageLoader();
                    imageLoader.get(image_url2, ImageLoader.getImageListener(dialogSurveyLayoutBinding.imageSurveyDialog, R.drawable.ic_launcher_background, R.drawable.surv03));
                    dialogSurveyLayoutBinding.imageSurveyDialog.setImageUrl(image_url2, imageLoader);
                } else {
                    dialogSurveyLayoutBinding.imageSurveyDialog.setDefaultImageResId(R.drawable.surv03);

                }
                dialog.show();
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        });


//        survey_adapter2 =new Survey_adapter(getBaseContext(), new Survey_adapter.OnItemClickListener()
//        {
//                @Override
//                public void onItemClick (Survey item) {
//                    final DialogSurveyLayoutBinding dialogSurveyLayoutBinding;
//                    dialogSurveyLayoutBinding = DialogSurveyLayoutBinding.inflate(LayoutInflater.from(SurveyList_Activity.this));
//                    AlertDialog.Builder builder = new AlertDialog.Builder(SurveyList_Activity.this);
//                    builder.setCancelable(true)
//                            .setView(dialogSurveyLayoutBinding.getRoot());
//                    final AlertDialog dialog = builder.create();
//                    final OptionViewModel optionViewModel = new ViewModelProvider(SurveyList_Activity.this, viewModelFactory).get(OptionViewModel.class);
//                    optionViewModel.getOptions(item.getV_id()).observe(SurveyList_Activity.this, new Observer<List<Option>>() {
//                        @Override
//                        public void onChanged(List<Option> options) {
//                            ArrayList entries = new ArrayList<>();
//                            ArrayList labels = new ArrayList();
//                            boolean NoOneVoted = true;
//                            Log.i(TAG, "onClick: opt.size=" + options.size());
//                            for (int i = 0; i < options.size(); i++) {
//
//                                entries.add(new Entry(Integer.valueOf(options.get(i).getNum()), i));
//                                labels.add(options.get(i).getOption());
//
//                                if (options.get(i).getNum() == 0 && NoOneVoted == true) {
//                                    NoOneVoted = true;
//                                } else {
//                                    NoOneVoted = false;
//                                }
//                                Log.i(TAG, "onClick:entries: " + entries.get(i));
//                                Log.i(TAG, "onClick: labels:" + labels.get(i));
//                            }
//                            if (NoOneVoted == false) {
//                                PieDataSet dataSet = new PieDataSet(entries, "");
//                                dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
//                                PieData data = new PieData(labels, dataSet);
//                                data.setValueTextSize(20);
//                                dialogSurveyLayoutBinding.chart.setData(data);
//                                dialogSurveyLayoutBinding.chart.setDescription("میزان نظر ها به هر گزینه");
//                                dialogSurveyLayoutBinding.chart.animateY(5000);
//                            } else {
//                                dialogSurveyLayoutBinding.chart.setVisibility(View.GONE);
//                                dialogSurveyLayoutBinding.nofind.setVisibility(View.VISIBLE);
//                            }
//                        }
//                    });
//                    Log.i(TAG, "onClick:");
//                    //LayoutInflater inflater = (LayoutInflater) (SurveyList_Activity.this).getSystemService(SurveyList_Activity.this.LAYOUT_INFLATER_SERVICE);
//                    //View view = inflater.inflate(R.layout.dialog_survey_layout, , false);
//
//                    dialogSurveyLayoutBinding.subjectSurveyDialog.setText(item.getSubject());
//                    ImageLoader imageLoader;
//                    String image_url2 = serverConstants.ROOT_URL + item.getPic();
//                    Log.i(TAG, "onClick: imageurl 2: " + image_url2);
//                    if (!image_url2.equals("Undefined")) {
//                        imageLoader = CustomVolleyRequest.getInstance(SurveyList_Activity.this).getImageLoader();
//                        imageLoader.get(image_url2, ImageLoader.getImageListener(dialogSurveyLayoutBinding.imageSurveyDialog, R.drawable.ic_launcher_background, R.drawable.surv03));
//                        dialogSurveyLayoutBinding.imageSurveyDialog.setImageUrl(image_url2, imageLoader);
//                    } else {
//                        dialogSurveyLayoutBinding.imageSurveyDialog.setDefaultImageResId(R.drawable.surv03);
//
//                    }
//                    dialog.show();
//                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//                }
//            });


        if (!surveyViewModel.from.equals("") || !surveyViewModel.to.equals("")){

            /*if (generic==2){
//                final boolean keyZero1;
//                final boolean keyZero2;
                surveyViewModel.getSurveys(String.valueOf(0),from,to,"create").observe(this, new Observer<List<Survey>>() {
                    @Override
                    public void onChanged(List<Survey> surveys) {
                        if (surveys.size()!=0){
                            if (surveys.size() != 0) {
                                binding.nofindsurvey.setVisibility(View.GONE);
                                binding.recyclerviewSurvey.setVisibility(View.VISIBLE);
                                survey_adapter.setList(surveys, getBaseContext());
                                binding.recyclerviewSurvey.setAdapter(survey_adapter);
                                survey_adapter.notifyDataSetChanged();

//                                keyZero1=false;
                            } else {
//                                keyZero1=true;
                                binding.recyclerviewSurvey.setVisibility(View.GONE);
                            }
                        }
                    }
                });
                surveyViewModel2.getSurveys(String.valueOf(1),from,to,"create").observe(this, new Observer<List<Survey>>() {
                    @Override
                    public void onChanged(List<Survey> surveys) {
                        if (surveys.size()!=0){
                            binding.nofindsurvey.setVisibility(View.GONE);
                            binding.recyclerviewSurvey2.setVisibility(View.VISIBLE);
                            survey_adapter2.setList(surveys, getBaseContext());
                            binding.recyclerviewSurvey2.setAdapter(survey_adapter2);
                            survey_adapter2.notifyDataSetChanged();
//                            keyZero2=false;
                        }
                        else {
//                            keyZero2=true;
                            binding.recyclerviewSurvey2.setVisibility(View.GONE);
                        }
                    }
                });
                if (binding.recyclerviewSurvey.getVisibility()==View.GONE && binding.recyclerviewSurvey2.getVisibility()==View.GONE){
                    binding.nofindsurvey.setVisibility(View.VISIBLE);
                }
            }*/
            //else {
                binding.recyclerviewSurvey2.setVisibility(View.GONE);
                surveyViewModel.getSurveys(String.valueOf(generic), from, to, "create").observe(this, new Observer<List<Survey>>() {
                    @Override
                    public void onChanged(List<Survey> surveys) {
                        if (surveys.size() != 0) {
                            binding.nofindsurvey.setVisibility(View.GONE);
                            binding.recyclerviewSurvey.setVisibility(View.VISIBLE);
                            survey_adapter.setList(surveys, getBaseContext());
                            binding.recyclerviewSurvey.setAdapter(survey_adapter);
                            survey_adapter.notifyDataSetChanged();
                        } else {
                            binding.nofindsurvey.setVisibility(View.VISIBLE);
                            binding.recyclerviewSurvey.setVisibility(View.GONE);
                        }
                    }
                });
            //}

        }
        binding.searchSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    binding.nofindsurvey.setVisibility(View.GONE);
                    //recyclerView.setVisibility(View.GONE);
                    dayfrom = binding.daySurveyFrom.getText().toString().trim();
                    monthfrom = binding.monthSurveyFrom.getText().toString().trim();
                    yearfrom = binding.yearSurveyFrom.getText().toString().trim();

                    dayto = binding.daySurveyTo.getText().toString().trim();
                    monthto = binding.monthSurveyTo.getText().toString().trim();
                    yearto = binding.yearSurveyTo.getText().toString().trim();
                    if (dayfrom.equals("") || monthfrom.equals("") || yearfrom.equals("") || dayto.equals("") || monthto.equals("") || yearto.equals(""))
                    {
                        binding.recyclerviewSurvey.setVisibility(View.GONE);
                        Toast.makeText(SurveyList_Activity.this, "همه فیلد ها را وارد کنید..", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        DateConverter dateConverter_from = new DateConverter();
                        dateConverter_from.persianToGregorian(Integer.valueOf(yearfrom), Integer.valueOf(monthfrom), Integer.valueOf(dayfrom));

                        from = dateConverter_from.getYear() + "-" + dateConverter_from.getMonth() + "-" + dateConverter_from.getDay();
                        DateConverter dateConverter_to = new DateConverter();
                        dateConverter_to.persianToGregorian(Integer.valueOf(yearto), Integer.valueOf(monthto), Integer.valueOf(dayto));
                        to = dateConverter_to.getYear() + "-" + dateConverter_to.getMonth() + "-" + dateConverter_to.getDay();

                        Log.i(TAG, "onClick: search");


                        /*if (generic == 2) {
//                            boolean keyZero1=true;
//                            boolean keyZero2=true;
                            surveyViewModel
                                    .getSurveys(String.valueOf(0),from,to,"onclick")
                                    .observe(SurveyList_Activity.this , new Observer<List<Survey>>() {
                                        @Override
                                        public void onChanged(List<Survey> surveys) {
                                            if (surveys.size()!=0) {
                                                binding.nofindsurvey.setVisibility(View.GONE);
                                                binding.recyclerviewSurvey.setVisibility(View.VISIBLE);
                                                survey_adapter.setList(surveys, getBaseContext());
                                                binding.recyclerviewSurvey.setAdapter(survey_adapter);
//                                                keyZero1=false;
                                            }
                                            else {
//                                                keyZero1=true;
                                                binding.recyclerviewSurvey.setVisibility(View.GONE);
                                            }

                                        }
                                    });
                            surveyViewModel2
                                    .getSurveys(String.valueOf(1),from,to,"onclick")
                                    .observe(SurveyList_Activity.this , new Observer<List<Survey>>() {
                                        @Override
                                        public void onChanged(List<Survey> surveys) {
                                            if (surveys.size()!=0) {
                                                binding.nofindsurvey.setVisibility(View.GONE);
                                                binding.recyclerviewSurvey2.setVisibility(View.VISIBLE);
                                                survey_adapter2.setList(surveys, getBaseContext());
                                                binding.recyclerviewSurvey2.setAdapter(survey_adapter2);
//                                                keyZero2=false;
                                            }
                                            else {
//                                               keyZero2=true;
                                               binding.recyclerviewSurvey2.setVisibility(View.GONE);
                                            }

                                        }
                                    });

                            if (binding.recyclerviewSurvey.getVisibility()==View.GONE && binding.recyclerviewSurvey2.getVisibility()==View.GONE){
                                binding.nofindsurvey.setVisibility(View.VISIBLE);
                            }

                        }*/
                        //else {
                            binding.recyclerviewSurvey2.setVisibility(View.GONE);
                            surveyViewModel
                                    .getSurveys(String.valueOf(generic), from, to, "onclick")
                                    .observe(SurveyList_Activity.this, new Observer<List<Survey>>() {
                                        @Override
                                        public void onChanged(List<Survey> surveys) {
                                            if (surveys.size() != 0) {
                                                binding.nofindsurvey.setVisibility(View.GONE);
                                                binding.recyclerviewSurvey.setVisibility(View.VISIBLE);
                                                survey_adapter.setList(surveys, getBaseContext());
                                                binding.recyclerviewSurvey.setAdapter(survey_adapter);
                                            } else {
                                                binding.recyclerviewSurvey.setVisibility(View.GONE);
                                                binding.nofindsurvey.setVisibility(View.VISIBLE);
                                            }

                                        }
                                    });
                        //}
                    }


            }
        });
    }

    /*private List<Survey> get_survey_from_server() {
        final List<Survey> s=new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String formated_URL = serverConstants.getSurveyFromTo+"?generic="+generic+"&from="+from+"&to="+to;

                    Log.i(TAG, "run: get survey from server : url="+formated_URL );

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

                    Log.i(TAG, "run: get survey from server : response"+http_get_response);
                    if (http_get_response.equals("{\"response\":\"No Vote\"}")) {
                        Log.i(TAG, "run: NO FIND SURVEY!");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(SurveyList_Activity.this, "هیچ نظرسنجی یافت نشد...", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else if (!http_get_response.equals("[]")){
                        Log.i(TAG, "run: surveys finded !!");

                        JSONArray jsonArray = new JSONArray(http_get_response);
                        for (int i=0; i<jsonArray.length(); i++){
                            Survey temp = new Survey();
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            temp.setSubject(jsonObject.getString("subject"));
                            temp.setPic(jsonObject.getString("pic"));
                            temp.setNumber(jsonObject.getString("number"));
                            temp.setV_id(jsonObject.getString("v_id"));

                            s.add(temp);
                            Log.i(TAG, "run: find survey");

                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.nofindsurvey.setVisibility(View.GONE);
                                binding.recyclerviewSurvey.setVisibility(View.VISIBLE);
                                survey_adapter.notifyDataSetChanged();
                                Log.i(TAG, "run: notifydatasetchanged");
                            }
                        });
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                binding.recyclerviewSurvey.setVisibility(View.GONE);
                                binding.nofindsurvey.setVisibility(View.VISIBLE);
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

        return s;
    }*/
}