package finalcompilation.finalfivemin.article;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.entity.FeedItem;

/**
 * Created by Ernest on 3/3/2017.
 */

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>  {

    Context context;
    ArrayList<FeedItem> listOfFeedItem;

    public ArticleAdapter(Context context, ArrayList<FeedItem> listOfFeedItem){
        this.context = context;
        this.listOfFeedItem = listOfFeedItem;
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.article_card,parent,false);
        ArticleViewHolder holder=new ArticleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        YoYo.with(Techniques.FadeIn).duration(1000).repeat(0).playOn(holder.card);
        final FeedItem current=listOfFeedItem.get(position);
        holder.title.setText(current.getTitle());
        holder.description.setText(current.getDescription());
        holder.date.setText(current.getDate());
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ArticleContentActivity.class);
                intent.putExtra("link",current.getLink());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listOfFeedItem.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder {

        TextView title,description,date;
        CardView card;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            title= (TextView) itemView.findViewById(R.id.article_title_text);
            description= (TextView) itemView.findViewById(R.id.article_description_text);
            date= (TextView) itemView.findViewById(R.id.article_date_text);
            card= (CardView) itemView.findViewById(R.id.article_card);
        }
    }
}
