package finalcompilation.finalfivemin.forum;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import finalcompilation.finalfivemin.R;

/**
 * Created by Denise on 15-Mar-17.
 */

public class ForumRecyclerAdapter extends RecyclerView.Adapter<ForumRecyclerAdapter.RecyclerViewHolder>{

    ArrayList<String> ansList = new ArrayList<String>();
    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<String> credList = new ArrayList<String>();


    public ForumRecyclerAdapter(ArrayList<String> ansList, ArrayList<String> nameList, ArrayList<String> credList){
        this.ansList = ansList;
        this.nameList = nameList;
        this.credList = credList;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forum_row_layout, parent, false);

        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.ansText.setText(ansList.get(position));
        holder.nameText.setText(nameList.get(position));
        holder.credText.setText(credList.get(position));
    }

    @Override
    public int getItemCount() {
        return ansList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView ansText, nameText, credText;
        public RecyclerViewHolder(View view){
            super(view);
            ansText = (TextView) view.findViewById(R.id.ansText);
            nameText = (TextView) view.findViewById(R.id.nameText);
            credText = (TextView) view.findViewById(R.id.credText);
        }
    }
}
