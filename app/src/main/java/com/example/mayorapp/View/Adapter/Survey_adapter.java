package com.example.mayorapp.View.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.example.mayorapp.Model.Survey;
import com.example.mayorapp.R;
import com.example.mayorapp.Services.CustomVolleyRequest;
import com.example.mayorapp.data.serverConstants;
import com.example.mayorapp.databinding.SurveyViewBinding;

import java.util.ArrayList;
import java.util.List;

public class Survey_adapter extends RecyclerView.Adapter<Survey_adapter.Survey_ViewHolder> {
    private List<Survey> surveys = new ArrayList<>();
    public Context context;
    private String TAG = "ADAPTER SURVEY";
    private SurveyViewBinding binding;
    private final OnItemClickListener listener;
    public Survey_adapter(List<Survey> surveys, Context context, OnItemClickListener listener) {
        this.surveys = surveys;
        this.context = context;
        this.listener = listener;
    }

    public Survey_adapter(Context context, OnItemClickListener listener) {
        this.context = context;
        this.listener = listener;
    }
    public void setList(List<Survey> surveys,Context context) {
        this.context = context;
        this.surveys=surveys;

    }

    @NonNull
    @Override
    public Survey_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=SurveyViewBinding.inflate(LayoutInflater.from(context),parent,false);
        return new Survey_adapter.Survey_ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final Survey_ViewHolder holder, int position) {
        holder.survey = surveys.get(position);
        holder.viewBinding.surveySubjectView.setText(surveys.get(position).getSubject());
        holder.viewBinding.surveyNumberView.setText("تعداد مشارکت : " + surveys.get(position).getNumber());
        holder.v_id=surveys.get(position).getV_id();
        //holder.opts=holder.get_vote_opt_from_server(holder.v_id);
        Log.i(TAG, "onBindViewHolder: "+holder.v_id);

        ImageLoader imageLoader;
        String image_url = serverConstants.ROOT_URL + surveys.get(position).getPic();
        if (!image_url.equals("Undefined")) {
            imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
            imageLoader.get(image_url, ImageLoader.getImageListener(holder.viewBinding.surveyImage, R.drawable.ic_launcher_background, R.drawable.surv03));
            holder.viewBinding.surveyImage.setImageUrl(image_url, imageLoader);
        }
        else {
            holder.viewBinding.surveyImage.setDefaultImageResId(R.drawable.surv03);
        }
        holder.viewBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.survey);
            }
        });
    }

    @Override
    public int getItemCount() {
        return surveys.size();
    }

    public class Survey_ViewHolder extends RecyclerView.ViewHolder {
        private Survey survey;
        private SurveyViewBinding viewBinding;
        private String TAG = "VIEW_HOLDER_SURVEY";
        public String v_id = "";

        public Survey_ViewHolder(@NonNull final SurveyViewBinding itemView) {
            super(itemView.getRoot());
            viewBinding = itemView;
           /* dialogSurveyLayoutBinding=DialogSurveyLayoutBinding.inflate(LayoutInflater.from(viewBinding.getRoot().getContext()),itemView.getRoot(),false);
            AlertDialog.Builder builder=new AlertDialog.Builder(viewBinding.getRoot().getContext());
            builder.setCancelable(true)
                    .setView(dialogSurveyLayoutBinding.getRoot());
            final AlertDialog dialog=builder.create();

            viewBinding.getRoot().setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Log.i(TAG, "onClick:");
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.dialog_survey_layout, (ViewGroup) itemView.getRoot(), false);

                    dialogSurveyLayoutBinding.subjectSurveyDialog.setText(survey.getSubject());

                    ImageLoader imageLoader;
                    String image_url2 = serverConstants.ROOT_URL + survey.getPic();
                    Log.i(TAG, "onClick: imageurl 2: "+image_url2);
                    if (!image_url2.equals("Undefined")){
                        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
                        imageLoader.get(image_url2, ImageLoader.getImageListener(dialogSurveyLayoutBinding.imageSurveyDialog, R.drawable.ic_launcher_background, R.drawable.surv03));
                        dialogSurveyLayoutBinding.imageSurveyDialog.setImageUrl(image_url2, imageLoader);
                    }
                    else {
                        dialogSurveyLayoutBinding.imageSurveyDialog.setDefaultImageResId(R.drawable.surv03);

                    }


                    ArrayList entries=new ArrayList<>();
                    ArrayList labels=new ArrayList();
                    boolean NoOneVoted=true;
                    Log.i(TAG, "onClick: opt.size="+opts.size());
                    for (int i=0;i<opts.size();i++){
                        entries.add(new Entry(Integer.valueOf(opts.get(i).getNum()),i));
                        labels.add(opts.get(i).getOption());
                        if (opts.get(i).getNum()==0 && NoOneVoted==true){
                            NoOneVoted=true;
                        }
                        else {
                            NoOneVoted=false;
                        }
                        Log.i(TAG, "onClick:entries: "+ entries.get(i));
                        Log.i(TAG, "onClick: labels:"+labels.get(i));
                    }
                    if (NoOneVoted == false) {
                        PieDataSet dataSet=new PieDataSet(entries,"");
                        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                        PieData data=new PieData(labels,dataSet);
                        data.setValueTextSize(20);
                        dialogSurveyLayoutBinding.chart.setData(data);
                        dialogSurveyLayoutBinding.chart.setDescription("میزان نظر ها به هر گزینه");
                        dialogSurveyLayoutBinding.chart.animateY(5000);
                    }
                    else {
                        dialogSurveyLayoutBinding.chart.setVisibility(View.GONE);
                        dialogSurveyLayoutBinding.nofind.setVisibility(View.VISIBLE);
                    }

                    dialog.show();
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                }


            });*/
        }

       /* private List<Option> get_vote_opt_from_server(final String v_id) {
            final List<Option> o=new ArrayList<>();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {

                        String formated_URL = serverConstants.getVoteOptionParticipate+"?v_id="+v_id;

                        Log.i(TAG, "run: get opt from server: url="+formated_URL );

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

                        Log.i(TAG, "run: get options from server : response"+http_get_response);
                        if (http_get_response.equals("{\"response\":\"No Option\"}")) {
                            Log.i(TAG, "run: NO FIND option!");
                        } else {
                            Log.i(TAG, "run: opt finded !!");
                            JSONArray jsonArray = new JSONArray(http_get_response);
                            for (int i=0; i<jsonArray.length(); i++){
                                Option temp = new Option();
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                temp.setOption(jsonObject.getString("option"));
                                temp.setNum(jsonObject.getInt("num"));

                                o.add(temp);
                                Log.i(TAG, "run: find opt");

                            }

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
            return o;
        }
    }*/
    }
    public interface OnItemClickListener{
        void onItemClick(Survey item);
    }
}
