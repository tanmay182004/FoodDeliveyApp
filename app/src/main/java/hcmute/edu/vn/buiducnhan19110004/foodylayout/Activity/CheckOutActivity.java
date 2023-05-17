package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView$InspectionCompanion;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.CheckOutAdapter;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CartDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.TransactionDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CartDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.TransactionDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class CheckOutActivity extends AppCompatActivity {
    // Views
    RecyclerView recyclerViewCheckOut;
    TextView CheckOut_textViewTotal, CheckOut_textViewOrder;

    //Database classes
    FoodyDBHelper foodyDBHelper;
    CartDB cartDB;
    TransactionDB transactionDB;
    ArrayList<CartDomain> cartDomainArrayList;

    // Adapter
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        foodyDBHelper = new FoodyDBHelper(this);
        cartDB = new CartDB(foodyDBHelper);
        transactionDB = new TransactionDB(foodyDBHelper);
        cartDomainArrayList = cartDB.SelectAllItemsInCartOfCurrentUser();

        setViews();
        getBundle();
        initCartList();
    }

    private void setViews() {
        recyclerViewCheckOut = findViewById(R.id.recyclerViewCheckOut);
        CheckOut_textViewTotal = findViewById(R.id.CheckOut_textViewTotal);
        CheckOut_textViewOrder = findViewById(R.id.CheckOut_textViewOrder);
        CheckOut_textViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(CartDomain cartItem : cartDomainArrayList) {
                    int userId = CurrentUser.getUser_id();
                    int productId = cartItem.getProduct_id();
                    int quantity = cartItem.getQuantity();
                    Date currentTime = Calendar.getInstance().getTime();
                    DateFormat dateFormat = new SimpleDateFormat("dd:MM:yyyy HH:mm::ss");
                    String transactionTime = dateFormat.format(currentTime);

                    TransactionDomain transactionDomain = new TransactionDomain(userId, productId, quantity, transactionTime);
                    transactionDB.InsertTransaction(transactionDomain);

                    cartDB.DeleteAllItemsInCart();
                }

                Toast.makeText(CheckOutActivity.this, "Your order is being processed", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CheckOutActivity.this, MainActivity.class));
            }
        });
    }
    private void getBundle(){
        double total = getIntent().getDoubleExtra("total", 0);
        total = Math.round(total);
        CheckOut_textViewTotal.setText( "$ " + String.valueOf(total));
    }
    private void initCartList(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewCheckOut.setLayoutManager(linearLayoutManager);

        adapter = new CheckOutAdapter(foodyDBHelper, cartDomainArrayList, CheckOutActivity.this);
        recyclerViewCheckOut.setAdapter(adapter);
    }
}
