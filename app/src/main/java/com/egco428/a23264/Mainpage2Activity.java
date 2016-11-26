package com.egco428.a23264;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class Mainpage2Activity extends AppCompatActivity {

    private CommentDataSource dataSource;
    private ArrayAdapter<Comment> loginArrayAdapter;
    public static final String User = "username";
    public static final String La = "latitude";
    public static final String Long = "longitude";

    public static final int DETAIL_REQ_CODE = 1001;
    protected List<Comment> values;

    private ListView listView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage2);

        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setTitle(Html.fromHtml("<font color=\"black\">" + "Main Page" + "</font>"));
        mActionBar.setDisplayHomeAsUpEnabled(true);

        dataSource = new CommentDataSource(this);
        dataSource.open();
        values = dataSource.getAllComments();
        loginArrayAdapter = new loginArrayAdapter(this,0,values);
        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(loginArrayAdapter); //push data in adapter into listview
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Comment login = loginArrayAdapter.getItem(position);
                displayDetail(login);
            }
        });
    }

    private void displayDetail(Comment login){
        Log.d("MainPage","Displaying : "+login.getUsername()+login.getLatitude()+login.getLongitude());
        Intent intent = new Intent(this,MapsActivity.class);
        intent.putExtra(User,login.getUsername());
        intent.putExtra(La,login.getLatitude());
        intent.putExtra(Long,login.getLongitude());
        startActivityForResult(intent,DETAIL_REQ_CODE);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:
                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            dialog.dismiss();
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(Mainpage2Activity.this);
            builder.setTitle("Main Page");
            builder.setMessage("Do you want to logout?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();


        }

        return super.onOptionsItemSelected(item);
    }

    class loginArrayAdapter extends ArrayAdapter<Comment> {
        Context context;
        List<Comment> objects;

        public loginArrayAdapter(Context context, int resource, List<Comment> objects) {
            super(context, resource, objects);
            this.context = context;
            this.objects = objects;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Comment login = objects.get(position);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.username_list, null);

            TextView txt = (TextView) view.findViewById(R.id.textView3);
            txt.setText(String.valueOf(login.getUsername()));

            return view;
        }

    }

    @Override
    protected void onResume(){
        dataSource.open();
        super.onResume();
    }

    @Override
    protected void onPause(){
        dataSource.close();
        super.onPause();
    }

}
