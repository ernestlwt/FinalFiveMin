package finalcompilation.finalfivemin.clinic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import finalcompilation.finalfivemin.Constants;
import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.entity.Clinic;

import static finalcompilation.finalfivemin.clinic.ClinicsMain.userID;

/**
 * Created by Aiqing on 3/26/17.
 */

public class ClinicFavAdaptor extends RecyclerView.Adapter<ClinicFavAdaptor.ViewHolder> {
    ArrayList<Clinic> savedclinics;
    Context context;
    int userID;


    public ClinicFavAdaptor(){}

    public ClinicFavAdaptor(ArrayList<Clinic> clinics, Context context, int id) {
        this.savedclinics = clinics;
        this.context = context;
        userID = id;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.clinic_cardview,parent,false);
        ViewHolder holder=new ViewHolder(v,context, savedclinics);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Clinic item = savedclinics.get(position);
        holder.clinicName.setText(item.getClinicName());
        holder.clinicAddress.setText(item.getAddress());
        holder.clinicRating.setRating((float)item.getRating());
    }


    @Override
    public int getItemCount() {
        return (savedclinics.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView clinicName;
        TextView clinicAddress;
        RatingBar clinicRating;
        CardView clinic_card;
        ArrayList<Clinic> savedclinics = new ArrayList<>();
        Context ctx;

        public ViewHolder(View itemView, Context ctx, ArrayList<Clinic> clinics) {
            super(itemView);
            this.savedclinics = clinics;
            this.ctx = ctx;
            itemView.setOnClickListener(this);
            clinicName = (TextView)itemView.findViewById(R.id.clinic_name);
            clinicAddress = (TextView)itemView.findViewById(R.id.clinic_address);
            clinicRating = (RatingBar) itemView.findViewById(R.id.clinic_rating);
            clinic_card = (CardView) itemView.findViewById(R.id.clinic_cardview);
        }

        @Override
        public void onClick(View v) {
            int pos = getAdapterPosition();
            Clinic clinic = this.savedclinics.get(pos);
            Intent intentClinicInfo = new Intent(this.ctx,ClinicInfoPage.class);
            intentClinicInfo.putExtra("clinicid",clinic.getID());
            intentClinicInfo.putExtra("name",clinic.getClinicName());
            intentClinicInfo.putExtra("contact",clinic.getContact());
            intentClinicInfo.putExtra("rating",clinic.getRating());
            intentClinicInfo.putExtra("address",clinic.getAddress());
            intentClinicInfo.putExtra("location",clinic.getLocation());
            intentClinicInfo.putExtra(Constants.USER_ID_INTENT,userID);
            Bundle latlng = new Bundle();
            latlng.putParcelable("latlng", clinic.getLocation());
            intentClinicInfo.putExtra("bundle",latlng);
            this.ctx.startActivity(intentClinicInfo);
        }
    }
}
