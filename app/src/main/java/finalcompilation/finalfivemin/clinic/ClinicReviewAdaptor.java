package finalcompilation.finalfivemin.clinic;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.RatingBar;
import android.content.Context;

import java.util.ArrayList;

import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.entity.ClinicReview;

/**
 * Created by Aiqing on 3/6/17.
 */

public class ClinicReviewAdaptor extends RecyclerView.Adapter<ClinicReviewAdaptor.ViewHolder> {

    ArrayList<ClinicReview> reviewList;
    Context context;

    public ClinicReviewAdaptor(){}

    public ClinicReviewAdaptor(ArrayList<ClinicReview> items, Context context) {
        this.reviewList = items;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.clinic_review_cardview,parent,false);
        ViewHolder holder=new ViewHolder(v,context,reviewList);
        return holder;
        //return new ViewHolder(v,context,clinicList);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ClinicReview review = reviewList.get(position);
        holder.review.setText(review.getReview());
        holder.reviewRating.setRating(review.getRating());
    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView review;
        RatingBar reviewRating;
        CardView review_card;
        ArrayList<ClinicReview> reviews = new ArrayList<>();
        Context ctx;


        public ViewHolder(View itemView, Context ctx, ArrayList<ClinicReview> reviewList) {
            super(itemView);
            this.reviews = reviewList;
            this.ctx = ctx;
            itemView.setOnClickListener(this);
            review = (TextView)itemView.findViewById(R.id.review);
            reviewRating = (RatingBar) itemView.findViewById(R.id.review_rating);
            review_card = (CardView) itemView.findViewById(R.id.review_cardview);
        }


        @Override
        public void onClick(View v) {
            return;
        }
    }

}