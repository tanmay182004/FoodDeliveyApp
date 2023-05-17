package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Locale;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.FoodListAdapter;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.PopularAdaptor;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CategoryDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodVariationDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.ProductDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CategoryDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodVariationDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class FoodListActivity extends AppCompatActivity {

    // Views
    TextView textViewCategoryTitle;
    RecyclerView recyclerViewFoodList;

    //Variables
    CategoryDomain sent_category;
    // List
    ArrayList<FoodVariationDomain> foodVariationDomainArrayList = new ArrayList<>();
    ArrayList<FoodDomain> foodDomainArrayList = new ArrayList<>();

    //Adapter
    FoodListAdapter foodListAdapter = new FoodListAdapter(foodDomainArrayList, this);

    //Database classes
    FoodyDBHelper foodyDBHelper = new FoodyDBHelper(this);

    ProductDB productDB = new ProductDB(foodyDBHelper);
    CategoryDB categoryDB = new CategoryDB(foodyDBHelper);
    FoodVariationDB foodVariationDB = new FoodVariationDB(foodyDBHelper);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);
        SetView();
        GetBundle();
        SetRecyclerViewFoodList();
    }
    @Override
    public void onBackPressed() {
        startActivity( new Intent(this, MainActivity.class));
    }
    private void SetRecyclerViewFoodList() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewFoodList.setLayoutManager(linearLayoutManager);

        foodVariationDomainArrayList = foodVariationDB.SelectAllVariationByCategoryID(sent_category.getId());
        for(FoodVariationDomain foodVariation: foodVariationDomainArrayList){
            FoodDomain foodDomain = productDB.SelectProductByID(foodVariation.getProduct_id());
            foodDomainArrayList.add(foodDomain);
        }
        //foodDomainArrayList = productDB.SearchProductByKeyWord("burger");
        foodListAdapter = new FoodListAdapter(foodDomainArrayList, FoodListActivity.this);
        recyclerViewFoodList.setAdapter(foodListAdapter);
    }

    private void GetBundle() {
        sent_category =  (CategoryDomain) getIntent().getSerializableExtra("category_object");
        textViewCategoryTitle.setText(sent_category.getTitle().toUpperCase(Locale.ROOT));
    }

    public void SetView(){
        textViewCategoryTitle = findViewById(R.id.textViewCategoryTitle);
        recyclerViewFoodList = findViewById(R.id.recyclerViewFoodList);
    }
}