package hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CartDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.ProductDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CartDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.ViewHolder>{
    // lists
    private ArrayList<CartDomain> cartDomainArrayList;
    private ArrayList<FoodDomain> foodDomainArrayList;
    // Parent's context
    private Context context;
    // DB classes
    private FoodyDBHelper foodyDBHelper;
    private CartDB cartDB;
    private ProductDB productDB;
    // Constructor
    public CheckOutAdapter(FoodyDBHelper foodyDBHelper, ArrayList<CartDomain> cartDomainArrayList, Context context) {
        this.context = context;

        this.foodyDBHelper = foodyDBHelper;
        this.cartDB = new CartDB(this.foodyDBHelper);
        this.productDB = new ProductDB(this.foodyDBHelper);
        this.cartDomainArrayList = cartDomainArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_checkout_cart_list, parent, false);

        return new CheckOutAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartDomain cartItem = cartDomainArrayList.get(position);
        if(cartItem != null){
            FoodDomain accordingFood = productDB.SelectProductByID(cartItem.getProduct_id());
            if(accordingFood != null){
                holder.textViewQuantity.setText( String.valueOf(cartItem.getQuantity() ));
                holder.textViewTitle.setText(accordingFood.getTitle());
                double itemTotal = accordingFood.getFee() * Double.valueOf( cartItem.getQuantity() );
                holder.textViewItemTotal.setText(Double.toString(itemTotal));
            }

        }
    }

    @Override
    public int getItemCount() {
        return cartDomainArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewQuantity, textViewTitle, textViewItemTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewItemTotal = itemView.findViewById(R.id.textViewItemTotal);
        }
    }
}
