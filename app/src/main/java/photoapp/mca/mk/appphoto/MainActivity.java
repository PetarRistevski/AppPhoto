package photoapp.mca.mk.appphoto;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

import static photoapp.mca.mk.appphoto.MyAdapter.REQUEST_CODE;
import static photoapp.mca.mk.appphoto.SecondActivity.CHANGED;
import static photoapp.mca.mk.appphoto.SecondActivity.CODE;
import static photoapp.mca.mk.appphoto.SecondActivity.DELETED;
import static photoapp.mca.mk.appphoto.SecondActivity.Deleted;
import static photoapp.mca.mk.appphoto.SecondActivity.Edited;
import static photoapp.mca.mk.appphoto.SecondActivity.FLAG_CHANGE;
import static photoapp.mca.mk.appphoto.SecondActivity.FLAG_DELETED;
import static photoapp.mca.mk.appphoto.SecondActivity.NEW_TITLE;
import static photoapp.mca.mk.appphoto.SecondActivity.OLD_TITLE;
import static photoapp.mca.mk.appphoto.SecondActivity.POSITION_TO_DELETE;
import static photoapp.mca.mk.appphoto.SecondActivity.USER_DELETED;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton myFab;
    ArrayList<ApiModel> mObjects = new ArrayList<>();
    int i = 0;
        final String PHOTOS_URL = "http://jsonplaceholder.typicode.com/photos";
        List<ApiModel> objects = new ArrayList<>();
        private RecyclerView recyclerView;
        private MyAdapter adapter;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            myFab = findViewById(R.id.fab);
            myFab.setImageResource(R.drawable.add);


            initRecyclerView();
            adapter.setData(mObjects);
            adapter.notifyDataSetChanged();
            //TODO: CHECK WHY NOT WORK DELETE
            getJsonObjects();





             myFab.setOnClickListener(v -> {
                 Intent intent = new Intent(MainActivity.this, SendPhoto.class);
                 startActivity(intent);
             });



            }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode==REQUEST_CODE){
            if (resultCode ==  Deleted) {

                int positon = data.getIntExtra(POSITION_TO_DELETE, 0);
                Toast.makeText(this, "I AM IN Activity  for result", Toast.LENGTH_SHORT).show();
                if (positon != 0) {
                    Toast.makeText(this, "Try to remove", Toast.LENGTH_SHORT).show();
                    adapter.removeAt(positon);
                }

            }
            if(resultCode == Edited){

            }

        }
    }



    private void checkToDeleteOrRename(Intent data) {

        Toast.makeText(this, "Checking Result;", Toast.LENGTH_SHORT).show();



    }


    private void initRecyclerView() {
        recyclerView =  findViewById(R.id.myRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(this.getApplicationContext());
        recyclerView.setAdapter(adapter);

    }


    public void getJsonObjects() {
            AsyncHttpClient client = new AsyncHttpClient();
            client.get(PHOTOS_URL, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    objects = fromJson(response);
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Toast.makeText(MainActivity.this, "Немате активна Интернет Конекција", Toast.LENGTH_LONG).show();
                }
            });
        }

        public ArrayList<ApiModel> fromJson(JSONArray jsonArray) {

            try {
                for (int i = 0; i < jsonArray.length(); i++) {

                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ApiModel photoData = new ApiModel();

                        photoData.mTitle = jsonObject.getString("title");

                        photoData.Url = jsonObject.getString("url");
                        photoData.thumbnailUrl = jsonObject.getString("thumbnailUrl");
                        mObjects.add(photoData);
                        adapter.notifyDataSetChanged();


                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                }

            } catch (Exception ex) {

            }

            return mObjects;
        }


    }

