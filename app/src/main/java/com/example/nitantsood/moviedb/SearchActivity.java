package com.example.nitantsood.moviedb;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {
    String Searched;
    EditText searchText;
    Button movie;
    Button tv;
    Button celeb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        movie = (Button) findViewById(R.id.movieSearchButton);
        tv = (Button) findViewById(R.id.tvSearchButton);
        celeb = (Button) findViewById(R.id.celebSearchButton);
        searchText= (EditText) findViewById(R.id.searchText);

        movie.setOnClickListener(this);
        celeb.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Searched=searchText.getText().toString();
        if(Searched.equals("")){
            Toast.makeText(this,"Please put a Valid Search Text",Toast.LENGTH_SHORT).show();
        }
        else{
            int id=v.getId();
            if(id==R.id.movieSearchButton){
                Intent i=new Intent(this,MovieSearchActivity.class);
                i.putExtra("text",Searched);
                startActivity(i);
            }else if(id==R.id.tvSearchButton){
                Intent i=new Intent(this,TvSearchActivity.class);
                i.putExtra("text",Searched);
                startActivity(i);
            }else if(id==R.id.celebSearchButton){
                Intent i=new Intent(this,CelebSearchActivity.class);
                i.putExtra("text",Searched);
                startActivity(i);
            }
        }
    }
}