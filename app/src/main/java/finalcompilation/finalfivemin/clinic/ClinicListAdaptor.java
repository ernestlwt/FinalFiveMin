package finalcompilation.finalfivemin.clinic;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.RatingBar;
import android.content.Context;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import finalcompilation.finalfivemin.Constants;
import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.entity.Clinic;

/**
 * Created by Aiqing on 3/6/17.
 */

public class ClinicListAdaptor extends RecyclerView.Adapter<ClinicListAdaptor.ViewHolder> {


    ArrayList<Clinic> clinicOrigin;
    ArrayList<Clinic> clinicList;
    ArrayList<Clinic> filteredList;
    ArrayList<Clinic> searchedList;
    Context context;
    int userID;
    ArrayAdapter<CharSequence> clinic_sort_adapter;
    ArrayAdapter<CharSequence> clinic_filter_adapter;

    public ClinicListAdaptor(){}

    public ClinicListAdaptor(ArrayList<Clinic> listItem, ArrayList<Clinic> origin, Context context, int id) {
        this.clinicList = listItem;
        this.clinicOrigin = origin;
        this.filteredList = new ArrayList<>();
        filteredList.addAll(origin);
        this.searchedList = new ArrayList<>();
        searchedList.addAll(origin);
        this.context = context;
        this.userID = id;

        clinic_sort_adapter = ArrayAdapter.createFromResource(context,
                R.array.clinic_sorter_array, android.R.layout.simple_spinner_item);
        clinic_sort_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        clinic_filter_adapter = ArrayAdapter.createFromResource(context,
                R.array.clinic_filter_array, android.R.layout.simple_spinner_item);
        clinic_filter_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.clinic_cardview,parent,false);
        ViewHolder holder=new ViewHolder(v,context, clinicList);
        return holder;
        //return new ViewHolder(v,context,clinicList);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Clinic item = clinicList.get(position);
        holder.clinicName.setText(item.getClinicName());
        holder.clinicAddress.setText(item.getAddress());
        holder.clinicRating.setRating((float)item.getRating());
    }


    @Override
    public int getItemCount() {
            return (clinicList.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView clinicName;
        TextView clinicAddress;
        RatingBar clinicRating;
        CardView clinic_card;
        ArrayList<Clinic> clinics = new ArrayList<Clinic>();
        Context ctx;

        public ViewHolder(View itemView, Context ctx, ArrayList<Clinic> clinics) {
            super(itemView);
            this.clinics = clinics;
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
            Clinic clinic = this.clinics.get(pos);
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

    public void filterText(final String text) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                searchedList.clear();
                if (TextUtils.isEmpty(text)) {
                    searchedList.addAll(clinicOrigin);

                } else {
                    for (Clinic clinic : clinicOrigin ) {
                        String name = clinic.getClinicName().toLowerCase();
                        String address = clinic.getAddress().toLowerCase();
                        if (name.contains(text.toLowerCase()) || address.contains(text.toLowerCase())){
                            searchedList.add(clinic);
                        }
                    }
                }
                searchedList.retainAll(filteredList);
                clinicList.clear();
                clinicList.addAll(searchedList);

                // Set on UI Thread
                ((Activity) context).runOnUiThread(new Runnable() {
                   @Override
                    public void run() {
                        // Notify the List that the DataSet has changed...
                        notifyDataSetChanged();
                    }
                });

            }
        }).start();

    }

    public void filterSpinner(int pos){

        double lat = 1.3466137;
        double lng = 103.6819953;
        LatLng ll = new LatLng(lat,lng);

        switch (pos){
            case 0:
                filteredList.clear();
                filteredList.addAll(clinicOrigin);
                break;
            case 1: // distance
                filteredList.clear();
                for (Clinic clinic : clinicOrigin) {
                    ClinicDistanceComparator distancecomp = new ClinicDistanceComparator(ll);
                    double distance = distancecomp.calculateDistance(clinic.getLocation());
                    if (distance < 1000)
                        filteredList.add(clinic);
                }
                break;

            case 2:
                filteredList.clear();
                for (Clinic clinic : clinicOrigin) {
                    ClinicDistanceComparator distancecomp = new ClinicDistanceComparator(ll);
                    Double distance = distancecomp.calculateDistance(clinic.getLocation());
                    if (distance < 5000)
                        filteredList.add(clinic);
                }
                break;

            case 3:
                filteredList.clear();
                for (Clinic clinic : clinicOrigin) {
                    ClinicDistanceComparator distancecomp = new ClinicDistanceComparator(ll);
                    Double distance = distancecomp.calculateDistance(clinic.getLocation());
                    if (distance < 10000)
                        filteredList.add(clinic);
                }
                break;

            case 4:
                filteredList.clear();
                for (Clinic clinic : clinicOrigin) {
                    double rating = clinic.getRating();
                    if (rating > 2.5)
                        filteredList.add(clinic);
                }
                break;


            case 5:
                filteredList.clear();
                for (Clinic clinic : clinicOrigin) {
                    double rating = clinic.getRating();
                    if (rating > 3.5)
                        filteredList.add(clinic);
                }
                break;

            case 6:
                filteredList.clear();
                for (Clinic clinic : clinicOrigin) {
                    double rating = clinic.getRating();
                    if (rating > 4.5)
                        filteredList.add(clinic);
                }
                break;
        }

        filteredList.retainAll(searchedList);
        clinicList.clear();
        clinicList.addAll(filteredList);
        //refresh adaptor
        notifyDataSetChanged();
    }

}