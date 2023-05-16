package hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.sql.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity.MerchantActivity;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CartDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.ProductDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.TransactionDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CartDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.TransactionDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.ViewHolder>{
    private ArrayList<FoodDomain> foodDomains = new ArrayList<>();
    private ArrayList<TransactionDomain> transactionDomains = new ArrayList<TransactionDomain>();

    FoodyDBHelper foodyDBHelper;
    private TransactionDB transactionDB;
    private ProductDB productDB;

    private Context context;

    public OrderHistoryAdapter(FoodyDBHelper foodyDBHelper, ArrayList<TransactionDomain> transactionDomains, Context context) {

        this.context = context;
        this.foodyDBHelper = foodyDBHelper;
        this.transactionDB = new TransactionDB(foodyDBHelper);
        this.productDB = new ProductDB(foodyDBHelper);
        this.transactionDomains = transactionDomains;

        if(transactionDomains.size() > 0){
            for(TransactionDomain transactionItem: transactionDomains){
                FoodDomain food;
                food = productDB.SelectProductByID(transactionItem.getProduct_id());
                this.foodDomains.add(food);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_order_history, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            FoodDomain foodItem = foodDomains.get(position);
            TransactionDomain transactionItem = transactionDomains.get(position);

            holder.titleOrderHistory.setText(foodItem.getTitle());
            holder.quantityOrderHistory.setText(String.valueOf(transactionItem.getQuantity()));
            holder.orderDateOrderHistory.setText(transactionItem.getTransaction_time());

            holder.feeOrderHistory.setText(String.valueOf(foodItem.getFee()));
            int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier( foodItem.getPic(), "drawable", holder.itemView.getContext().getPackageName());

            Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.picOrderHistory);

            holder.openMerchantBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.itemView.getContext(), MerchantActivity.class);
                    intent.putExtra("productId", foodDomains.get(position).getId());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
        catch (Exception e) {
            System.out.println("No list read");
        }
    }

    @Override
    public int getItemCount() {
        return foodDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView picOrderHistory;
        TextView titleOrderHistory, quantityOrderHistory, orderDateOrderHistory, feeOrderHistory;
        ConstraintLayout openMerchantBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleOrderHistory = itemView.findViewById(R.id.titleOrderHistoryTextView);
            quantityOrderHistory = itemView.findViewById(R.id.quantityOrderHistoryTextView);
            orderDateOrderHistory = itemView.findViewById(R.id.orderDateOrderHistoryTextView);
            feeOrderHistory = itemView.findViewById(R.id.feeOrderHistoryTextView);
            picOrderHistory = itemView.findViewById(R.id.picOrderHistoryImageView);
            openMerchantBtn = itemView.findViewById(R.id.orderHistoryConstraintLayout);
        }
    }
}
