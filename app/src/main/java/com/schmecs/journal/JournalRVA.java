package com.schmecs.journal;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.schmecs.journal.model.Journal;
import com.schmecs.journal.model.Post;

import java.util.List;

public class JournalRVA extends RecyclerView.Adapter<JournalRVA.CustomViewHolder> {
    private List<Post> postList;
    private Context mContext;

    public JournalRVA(Context context, List<Post> postList) {
        this.postList = postList;
        this.mContext = context;
    }

    private OnItemClickListener onItemClickListener;
    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.journal_entry, viewGroup, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        final Post post = postList.get(i);

        //Setting text view title
        customViewHolder.textViewDate.setText(post.getDate());
        customViewHolder.textViewContent.setText(post.getText());

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(post);
            }
        };
        customViewHolder.textViewDate.setOnClickListener(listener);
        customViewHolder.textViewContent.setOnClickListener(listener);
    }

    @Override
    public int getItemCount() {
        return (null != postList ? postList.size() : 0);
    }

    class CustomViewHolder extends RecyclerView.ViewHolder {
        //protected ImageView imageView;
        private TextView textViewDate;
        private TextView textViewContent;

        public CustomViewHolder(View view) {
            super(view);

            this.textViewDate = (TextView) view.findViewById(R.id.date);
            this.textViewContent = (TextView) view.findViewById(R.id.content);
        }
    }
}
