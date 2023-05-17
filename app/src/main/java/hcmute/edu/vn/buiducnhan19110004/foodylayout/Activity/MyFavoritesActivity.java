package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.MyFavoriteAdapter;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CartDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FavoriteDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FavoriteDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class MyFavoritesActivity extends AppCompatActivity {
    private ScrollView favoriteScrollView;
    private RecyclerView favoriteRecycleView;
    private TextView emptyTxt;
    private MyFavoriteAdapter adapter;
    private ImageView goBackBtn;

    //Database variables
    FoodyDBHelper foodyDBHelper;
    FavoriteDB favoriteDB;
    ArrayList<FavoriteDomain> favoriteList = new ArrayList<FavoriteDomain>();

    //Intent contain info last activity
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favorites);

        intent = getIntent();

        InitList();
    }
    @Override
    public void onBackPressed() {
        startActivity( new Intent(this, MainActivity.class));
    }
    private void InitList() {
        emptyTxt = findViewById(R.id.favoriteEmptyTxt);
        favoriteScrollView = findViewById(R.id.favoriteScrollView);
        favoriteRecycleView = findViewById(R.id.favoriteRecyclerView);
        goBackBtn = findViewById(R.id.goBackImageView);
        foodyDBHelper = new FoodyDBHelper(this);
        favoriteDB = new FavoriteDB(foodyDBHelper);
        favoriteList = favoriteDB.SelectFavoritesByCurrentUser();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        favoriteRecycleView.setLayoutManager(linearLayoutManager);
        adapter = new MyFavoriteAdapter(foodyDBHelper, favoriteList, MyFavoritesActivity.this);
        favoriteRecycleView.setAdapter(adapter);

        if(favoriteList.size() > 0) {
            emptyTxt.setVisibility(View.GONE);
            favoriteScrollView.setVisibility(View.VISIBLE);
        }
        else {
            emptyTxt.setVisibility(View.VISIBLE);
            favoriteScrollView.setVisibility(View.GONE);
        }

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}