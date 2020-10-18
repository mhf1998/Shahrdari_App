package com.example.mayorapp.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.example.mayorapp.Model.News;
import com.example.mayorapp.R;
import com.example.mayorapp.Services.CustomVolleyRequest;
import com.example.mayorapp.Services.DateConverter;
import com.example.mayorapp.data.serverConstants;
import com.example.mayorapp.databinding.NewsViewBinding;

import java.util.ArrayList;
import java.util.List;

public class News_adapter extends RecyclerView.Adapter<News_adapter.News_ViewHolder> {
    private List<News> newses = new ArrayList<>();
    public Context context;
    private final OnItemClickListener listener;
    private String TAG = "ADAPTER NEWS";

    private NewsViewBinding binding;
    public News_adapter(List<News> newses, Context context,OnItemClickListener listener) {
        this.newses = newses;
        this.context = context;
        this.listener=listener;
    }

    public News_adapter(Context baseContext, OnItemClickListener listener) {
        this.context=baseContext;
        this.listener = listener;
    }

    public void setList(List<News> newses,Context context){
        this.newses=newses;
        this.context=context;
    }

    @NonNull
    @Override
    public News_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding=NewsViewBinding.inflate(LayoutInflater.from(context),parent,false);
        return new News_adapter.News_ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final News_ViewHolder holder, int position) {


        holder.news = newses.get(position);
        holder.view.subjectNewsView.setText(newses.get(position).getSubject());

        //convert date to shamsi
        String datemiladi=newses.get(position).getDate();
        String[] date2=datemiladi.split("-");
        DateConverter dateConvertertoshamsi=new DateConverter();
        dateConvertertoshamsi.gregorianToPersian(Integer.valueOf(date2[0]),Integer.valueOf(date2[1]),Integer.valueOf(date2[2]));
        String shamsi=dateConvertertoshamsi.getYear()+"-"+dateConvertertoshamsi.getMonth()+"-"+dateConvertertoshamsi.getDay();

        holder.view.dataNewsView.setText(shamsi);
        ImageLoader imageLoader;
        String image_url = serverConstants.ROOT_URL + newses.get(position).getPic();
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(image_url, ImageLoader.getImageListener(holder.view.imageNewsView, R.drawable.ic_launcher_background, android.R.drawable
                .ic_dialog_alert));
        holder.view.imageNewsView.setImageUrl(image_url, imageLoader);
        holder.view.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(holder.news);
            }
        });


    }

    @Override
    public int getItemCount() {
        return newses.size();
    }

    public class News_ViewHolder extends RecyclerView.ViewHolder {
        private News news;
        NewsViewBinding view;
        public News_ViewHolder(@NonNull final NewsViewBinding itemView) {
            super(itemView.getRoot());
            view=itemView;
        }

    }

    public interface OnItemClickListener{
        void onItemClick(News item);
    }
}
