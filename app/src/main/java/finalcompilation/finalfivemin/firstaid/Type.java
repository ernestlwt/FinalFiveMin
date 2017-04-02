package finalcompilation.finalfivemin.firstaid;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;

import finalcompilation.finalfivemin.BottomNavigationViewHelper;
import finalcompilation.finalfivemin.Constants;
import finalcompilation.finalfivemin.R;
import finalcompilation.finalfivemin.UserMainActivity;
import finalcompilation.finalfivemin.article.ArticleMainActivity;
import finalcompilation.finalfivemin.clinic.ClinicsMain;
import finalcompilation.finalfivemin.forum.ForumMain;

public class Type extends AppCompatActivity {

    TextView txt1;
    TextView txt2;
    ImageView img1;

    private BottomNavigationView bottomNavigationView;
    int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstaid_type);

        txt1 = (TextView)findViewById(R.id.textView2);
        img1 = (ImageView)findViewById(R.id.imageView2);
        txt2 = (TextView)findViewById(R.id.textView3);

        txt2.setMovementMethod(new ScrollingMovementMethod());



        final Bundle bundle = getIntent().getExtras();
        userID = bundle.getInt(Constants.USER_ID_INTENT);

        if (bundle != null) {
            if (Content.getCat() == 0) {
                txt1.setText(bundle.getString("BurnType"));
                img1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.burn));
                String txt = "";
                if (txt1.getText().toString().equalsIgnoreCase("Fire Burn")) {
                    try {
                        InputStream is = getAssets().open("fireburn.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                    txt2.setText(txt);
                }

                else if ((txt1.getText().toString().equalsIgnoreCase("Electrical Burn"))) {

                    try {
                        InputStream is = getAssets().open("ElectricalBurn.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    txt2.setText(txt);

                } else if ((txt1.getText().toString().equalsIgnoreCase("Chemical Burn"))) {
                    try {
                        InputStream is = getAssets().open("ChemicalBurn.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                    txt2.setText(txt);
                } else if ((txt1.getText().toString().equalsIgnoreCase("Scalding"))) {
                    try {
                        InputStream is = getAssets().open("Scalding.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    txt2.setText(txt);
                } else if ((txt1.getText().toString().equalsIgnoreCase("SunBurn"))) {
                    try {
                        InputStream is = getAssets().open("Sunburn.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    txt2.setText(txt);


                }
            }


            if (Content.getCat() == 1) {
                txt1.setText(bundle.getString("BiteType"));
                img1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.bite));
                String txt = "";
                if (txt1.getText().toString().equalsIgnoreCase("Insect Bite")) {
                    try {
                        InputStream is = getAssets().open("InsectBite.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                    txt2.setText(txt);
                }

                else if ((txt1.getText().toString().equalsIgnoreCase("Spider Bite"))) {

                    try {
                        InputStream is = getAssets().open("SpiderBite.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    txt2.setText(txt);

                } else if ((txt1.getText().toString().equalsIgnoreCase("Snake Bite"))) {
                    try {
                        InputStream is = getAssets().open("SnakeBite.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                    txt2.setText(txt);
                }
            }
            if (Content.getCat() == 2) {
                txt1.setText(bundle.getString("SprainStrainType"));
                img1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.sprainstrain));
                String txt = "";
                if (txt1.getText().toString().equalsIgnoreCase("Sprain")) {
                    try {
                        InputStream is = getAssets().open("Sprain.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                    txt2.setText(txt);
                }
                else if (txt1.getText().toString().equalsIgnoreCase("Strain")) {
                    try {
                        InputStream is = getAssets().open("Strain.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                    txt2.setText(txt);
                }

            }
            if (Content.getCat() == 3) {
                txt1.setText(bundle.getString("OpenWoundType"));
                img1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.openwound));
                String txt = "";
                if (txt1.getText().toString().equalsIgnoreCase("Abrasion")) {
                    try {
                        InputStream is = getAssets().open("Abrasion.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                    txt2.setText(txt);
                } else if (txt1.getText().toString().equalsIgnoreCase("Laceration")) {
                    try {
                        InputStream is = getAssets().open("Laceration.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                    txt2.setText(txt);
                } else if (txt1.getText().toString().equalsIgnoreCase("Incision")) {
                    try {
                        InputStream is = getAssets().open("Incision.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                    txt2.setText(txt);
                } else if (txt1.getText().toString().equalsIgnoreCase("Puncture")) {
                    try {
                        InputStream is = getAssets().open("Puncture.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                    txt2.setText(txt);
                } else if (txt1.getText().toString().equalsIgnoreCase("Avulsion")) {
                    try {
                        InputStream is = getAssets().open("Avulsion.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                    txt2.setText(txt);
                }

            }
            if (Content.getCat() == 4) {
                txt1.setText(bundle.getString("ChokingType"));
                img1.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.choking));
                String txt = "";
                if (txt1.getText().toString().equalsIgnoreCase("Choking (Adults)")) {
                    try {
                        InputStream is = getAssets().open("Choking.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                    txt2.setText(txt);
                }
                else if (txt1.getText().toString().equalsIgnoreCase("Choking (Infant)")) {
                    try {
                        InputStream is = getAssets().open("Choking_Infant.txt");
                        int size = is.available();
                        byte[] buffer = new byte[size];
                        is.read(buffer);
                        is.close();
                        txt = new String(buffer);
                    } catch (IOException ex) {
                        ex.printStackTrace();

                    }
                    txt2.setText(txt);
                }


            }
        }
        //bottom nav bar
        final Context myContext = this;
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_nav);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_firstaid);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
    }}