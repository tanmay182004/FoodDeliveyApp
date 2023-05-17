package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.FoodListAdapter;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.SearchListAdapter;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.ProductDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class SearchProductActivity extends AppCompatActivity {

    // Views
    EditText FoodSearch_editTextSearch;
    RecyclerView recyclerViewItemSearchList;

    //Variables
    String searching_text;
    // List
    ArrayList<FoodDomain> foodDomainArrayList = new ArrayList<>();

    //Adapter
    SearchListAdapter searchListAdapter = new SearchListAdapter(foodDomainArrayList, this);

    //Database classes
    FoodyDBHelper foodyDBHelper = new FoodyDBHelper(this);

    ProductDB productDB = new ProductDB(foodyDBHelper);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product);
        GetBundle();
        SetView();
        SetRecyclerSearchViewFoodList();
    }
    @Override
    public void onBackPressed() {
        startActivity( new Intent(this, MainActivity.class));
    }
    private void SetRecyclerSearchViewFoodList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewItemSearchList.setLayoutManager(linearLayoutManager);
        foodDomainArrayList = productDB.SearchProductByKeyWord(searching_text);

        searchListAdapter = new SearchListAdapter(foodDomainArrayList, SearchProductActivity.this);
        recyclerViewItemSearchList.setAdapter(searchListAdapter);
    }

    private void GetBundle() {
        searching_text =  getIntent().getStringExtra("searching_text");
    }

    public void SetView(){
        FoodSearch_editTextSearch = (EditText) findViewById(R.id.FoodSearch_editTextSearch);
        if(searching_text != null)
            FoodSearch_editTextSearch.setText(searching_text);
        recyclerViewItemSearchList = findViewById(R.id.recyclerViewItemSearchList);

        FoodSearch_editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keycode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keycode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    String searching_text = FoodSearch_editTextSearch.getText().toString();
                    Intent intent = new Intent(SearchProductActivity.this, SearchProductActivity.class);
                    intent.putExtra("searching_text", searching_text);
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });
    }
}