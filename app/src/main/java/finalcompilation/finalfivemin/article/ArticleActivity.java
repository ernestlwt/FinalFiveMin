package finalcompilation.finalfivemin.article;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;

import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.entity.FeedItem;

/**
 * Created by Ernest on 3/3/2017.
 */

public class ArticleActivity extends AsyncTask<Void,Void,Void>{

    Context context;
    ProgressDialog progressDialog;
    ArrayList<FeedItem> listOfFeedItem;
    RecyclerView recyclerView;
    int source;



    public ArticleActivity(Context context, RecyclerView recyclerView, int source){
        this.context = context;
        this.recyclerView = recyclerView;
        this.source = source;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading...");
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        ArticleAdapter adapter=new ArticleAdapter(context,listOfFeedItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected Void doInBackground(Void... params) {
        ArticleXMLProcessor processor = new ArticleXMLProcessor(source);//edit to put in user preferred source
        listOfFeedItem = processor.getListOfFeedItem();
        return null;
    }

}
