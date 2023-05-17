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

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.OrderHistoryAdapter;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CartDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.TransactionDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CartDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.TransactionDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class OrderHistoryActivity extends AppCompatActivity {
    private ScrollView orderHistoryScrollView;
    private RecyclerView orderHistoryRecyclerView;
    private TextView emptyTxt;
    private ImageView goBackBtn;

    private OrderHistoryAdapter adapter;

    //Database variables
    FoodyDBHelper foodyDBHelper;
    TransactionDB transactionDB;
    ArrayList<TransactionDomain> transactionList = new ArrayList<TransactionDomain>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        InitList();
    }
    @Override
    public void onBackPressed() {
        startActivity( new Intent(this, MainActivity.class));
    }
    private void InitList() {
        orderHistoryScrollView = findViewById(R.id.orderHistoryScrollView);
        orderHistoryRecyclerView = findViewById(R.id.orderHistoryRecyclerView);
        emptyTxt = findViewById(R.id.emptyTextOrderHistory);
        goBackBtn = findViewById(R.id.goBackImageView);

        foodyDBHelper = new FoodyDBHelper(this);
        transactionDB = new TransactionDB(foodyDBHelper);
        transactionList = transactionDB.SelectAllTransactionOfCurrentUser();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        orderHistoryRecyclerView.setLayoutManager(linearLayoutManager);
        adapter = new OrderHistoryAdapter(foodyDBHelper, transactionList, OrderHistoryActivity.this);
        orderHistoryRecyclerView.setAdapter(adapter);

        goBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(transactionList.size() > 0) {
            emptyTxt.setVisibility(View.GONE);
            orderHistoryScrollView.setVisibility(View.VISIBLE);
        }
        else {
            emptyTxt.setVisibility(View.VISIBLE);
            orderHistoryScrollView.setVisibility(View.GONE);
        }
    }
}