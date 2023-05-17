package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.PopularAdaptor;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.MerchantDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.MerchantProductDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.ProductDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.MerchantDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class MerchantActivity extends AppCompatActivity {
    //View
    private ImageView merchantPic;
    private TextView merchantName, merchantAddress;
    private RecyclerView merchantRecyclerView;
    private int merchantId;

    //Database
    private FoodyDBHelper foodyDBHelper = new FoodyDBHelper(this);
    private ProductDB productDB = new ProductDB(foodyDBHelper);
    private MerchantDB merchantDB = new MerchantDB(foodyDBHelper);
    private MerchantProductDB merchantProductDB = new MerchantProductDB(foodyDBHelper);

    //List
    ArrayList<FoodDomain> foodDomainArrayList;

    //Intent contain productId
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);

        intent = getIntent();

        InitView();
        InitFoodList();
    }

    private void InitView() {
        merchantPic = findViewById(R.id.merchantPicImageView);
        merchantName = findViewById(R.id.merchantNameTextView);
        merchantAddress = findViewById(R.id.merchantAddressTextView);
        merchantRecyclerView = findViewById(R.id.merchantFoodListRecyclerView);

        foodDomainArrayList = new ArrayList<FoodDomain>();

        //get merchantId according to productId
        this.merchantId = merchantProductDB
                .SelectMerchantFoodByProductId(intent.getIntExtra("productId", 0))
                .getMerchant_id();

        MerchantDomain merchantDomain = merchantDB.SelectMerchantById(this.merchantId);

        merchantName.setText(merchantDomain.getName());
        merchantAddress.setText(merchantDomain.getAddress());
        int drawableResourceId = getResources().getIdentifier(merchantDomain.getPic(), "drawable", getPackageName());

        Glide.with(this).load(drawableResourceId).into(this.merchantPic);
    }

    private void InitFoodList() {
        //get list of productId according to merchantId
        ArrayList<Integer> productIdList = merchantProductDB.SelectFoodIdListByMerchantId(this.merchantId);
        //get list of FoodDomain
        for(Integer productId : productIdList) {
            FoodDomain foodDomain = productDB.SelectProductByID(productId);
            foodDomainArrayList.add(foodDomain);
        }

        if(foodDomainArrayList.size() > 0) {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            merchantRecyclerView.setLayoutManager(linearLayoutManager);
            PopularAdaptor adaptor = new PopularAdaptor(foodDomainArrayList, MerchantActivity.this);
            merchantRecyclerView.setAdapter(adaptor);
        }
        else {
            System.out.println("Get food list failed of merchant_id = " + merchantId + ", empty food list");
        }
    }
}