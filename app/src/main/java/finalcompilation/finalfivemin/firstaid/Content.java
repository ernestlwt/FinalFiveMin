package finalcompilation.finalfivemin.firstaid;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import finalcompilation.finalfivemin.BottomNavigationViewHelper;
import finalcompilation.finalfivemin.Constants;
import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.UserMainActivity;
import finalcompilation.finalfivemin.article.ArticleMainActivity;
import finalcompilation.finalfivemin.clinic.ClinicsMain;
import finalcompilation.finalfivemin.forum.ForumMain;

public class Content extends AppCompatActivity {

    TextView txt;
    ImageView img;

    static int category_no ;

    public void setCat(int category_no){
        this.category_no = category_no;
    }

    public static int getCat(){
        return category_no;
    }

    private BottomNavigationView bottomNavigationView;
    int userID;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstaid_content);

        txt = (TextView)findViewById(R.id.textView1);
        img = (ImageView)findViewById(R.id.imageView1);




        final Bundle bundle = getIntent().getExtras();

        userID = bundle.getInt(Constants.USER_ID_INTENT);
        if (bundle != null){
            txt.setText(bundle.getString("Category"));
            if (txt.getText().toString().equalsIgnoreCase("Burns")){
                img.setImageDrawable(ContextCompat.getDrawable(Content.this,R.drawable.burn));

                final ListView listview = (ListView) findViewById(R.id.listview1);
                ArrayAdapter mAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,
                        getResources().getStringArray(R.array.burn_type));

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Content.this, Type.class);
                        intent.putExtra("BurnType", listview.getItemAtPosition(i).toString());
                        intent.putExtra(Constants.USER_ID_INTENT, userID);
                        startActivity(intent);
                    }
                });

                listview.setAdapter(mAdapter);
                setCat(0);


            } else if (txt.getText().toString().equalsIgnoreCase("Stings/Bites")) {
                img.setImageDrawable(ContextCompat.getDrawable(Content.this, R.drawable.bite));

                final ListView listview = (ListView) findViewById(R.id.listview1);
                ArrayAdapter mAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,
                        getResources().getStringArray(R.array.bite_type));

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Content.this, Type.class);
                        intent.putExtra("BiteType", listview.getItemAtPosition(i).toString());
                        startActivity(intent);
                    }
                });

                listview.setAdapter(mAdapter);
                setCat(1);
            }else if (txt.getText().toString().equalsIgnoreCase("Sprain/Strain")) {
                img.setImageDrawable(ContextCompat.getDrawable(Content.this, R.drawable.sprainstrain));

                final ListView listview = (ListView) findViewById(R.id.listview1);
                ArrayAdapter mAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,
                        getResources().getStringArray(R.array.sprainstrain_type));

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Content.this, Type.class);
                        intent.putExtra("SprainStrainType", listview.getItemAtPosition(i).toString());
                        startActivity(intent);
                    }
                });

                listview.setAdapter(mAdapter);
                setCat(2);





            }else if (txt.getText().toString().equalsIgnoreCase("Open Wounds")) {
                img.setImageDrawable(ContextCompat.getDrawable(Content.this, R.drawable.openwound));

                final ListView listview = (ListView) findViewById(R.id.listview1);
                ArrayAdapter mAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,
                        getResources().getStringArray(R.array.openwound_type));

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Content.this, Type.class);
                        intent.putExtra("OpenWoundType", listview.getItemAtPosition(i).toString());
                        startActivity(intent);
                    }

                });

                listview.setAdapter(mAdapter);
                setCat(3);



            }else if (txt.getText().toString().equalsIgnoreCase("Choking")) {
                img.setImageDrawable(ContextCompat.getDrawable(Content.this, R.drawable.choking));

                final ListView listview = (ListView) findViewById(R.id.listview1);
                ArrayAdapter mAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,
                        getResources().getStringArray(R.array.choking_type));

                listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(Content.this, Type.class);
                        intent.putExtra("ChokingType", listview.getItemAtPosition(i).toString());
                        startActivity(intent);
                    }
                });

                listview.setAdapter(mAdapter);
                setCat(4);



            }


        }
        final Context myContext = this;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_firstaid);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Bundle bundle = getIntent().getExtras();
                        int userID = bundle.getInt(Constants.USER_ID_INTENT);
                        Intent nextIntent;
                        switch (item.getItemId()) {
                            case R.id.bottom_nav_clinic:
                                nextIntent = new Intent(myContext, ClinicsMain.class);
                                nextIntent.putExtra(Constants.USER_ID_INTENT, userID);
                                startActivity(nextIntent);
                                return true;
                            case R.id. bottom_nav_forum:
                                nextIntent = new Intent(myContext, ForumMain.class);
                                nextIntent.putExtra(Constants.USER_ID_INTENT, userID);
                                startActivity(nextIntent);
                                return true;
                            case R.id. bottom_nav_firstaid:
                                nextIntent = new Intent(myContext, FirstAidMainActivity.class);
                                nextIntent.putExtra(Constants.USER_ID_INTENT, userID);
                                startActivity(nextIntent);
                                return true;
                            case R.id. bottom_nav_article:
                                nextIntent = new Intent(myContext, ArticleMainActivity.class);
                                nextIntent.putExtra(Constants.USER_ID_INTENT, userID);
                                startActivity(nextIntent);
                                return true;
                            case R.id. bottom_nav_settings:
                                nextIntent = new Intent(myContext, UserMainActivity.class);
                                nextIntent.putExtra(Constants.USER_ID_INTENT, userID);
                                startActivity(nextIntent);
                                return true;
                        }
                        return false;
                    }
                });



    }
}
