package hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity.CartListActivity;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CartDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.ProductDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CartDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

import java.util.ArrayList;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> {
    private ArrayList<FoodDomain> foodDomains = new ArrayList<>();
    private ArrayList<CartDomain> cartDomains;

    FoodyDBHelper foodyDBHelper;
    private CartDB cartDB;
    private ProductDB productDB;

    private Context context;

    CartListActivity cartListActivity;

    public CartListAdapter(FoodyDBHelper foodyDBHelper, ArrayList<CartDomain> cartDomains, Context context) {

        this.context = context;
        this.foodyDBHelper = foodyDBHelper;
        this.cartDB = new CartDB(foodyDBHelper);
        this.productDB = new ProductDB(foodyDBHelper);
        this.cartDomains = cartDomains;

        if(cartDomains.size() > 0){
            for(CartDomain cartItem: cartDomains){
                FoodDomain food;
                food = productDB.SelectProductByID(cartItem.getProduct_id());
                if(food == null) {
                    System.out.println("Can't get food from DB!");
                }
                this.foodDomains.add(food);
            }
        }

        this.cartListActivity = (CartListActivity) context;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_cart, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try {
            FoodDomain foodItem = productDB.SelectProductByID(foodDomains.get(position).getId());
            CartDomain cartItem = cartDB.SelectCartByProductID(foodDomains.get(position).getId());

            holder.title.setText(foodItem.getTitle());
            holder.feeEachItem.setText(String.valueOf(foodItem.getFee()));
            holder.totalEachItem.setText(String.valueOf((cartItem.getQuantity() * foodItem.getFee() * 100) / 100));
            holder.numberItemTxt.setText(String.valueOf(cartItem.getQuantity()));
            int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(foodItem.getPic(), "drawable", holder.itemView.getContext().getPackageName());

            Glide.with(holder.itemView.getContext())
                    .load(drawableResourceId)
                    .into(holder.pic);

            holder.plusItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int current_quantity = cartDomains.get(position).getQuantity();
                    cartDomains.get(position).setQuantity(current_quantity + 1);
                    holder.numberItemTxt.setText(String.valueOf(current_quantity + 1));
                    cartDB.PlusOneQuantity(foodItem.getId());
                    cartListActivity.CalculateCart();
                    notifyDataSetChanged();
                }
            });

            holder.minusItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cartItem.getQuantity() > 1) {
                        int current_quantity = cartDomains.get(position).getQuantity();
                        cartDomains.get(position).setQuantity(current_quantity - 1);
                        holder.numberItemTxt.setText(String.valueOf(cartItem.getQuantity() - 1));
                        cartDB.MinusOneQuantity(foodDomains.get(position).getId());
                        cartListActivity.CalculateCart();
                        notifyDataSetChanged();
                    }
                }
            });
        }
        catch (Exception e){
            System.out.println("No list read");
        }
    }

    @Override
    public int getItemCount() {
        return foodDomains.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {
        TextView title, feeEachItem;
        ImageView pic, plusItem, minusItem;
        TextView totalEachItem, numberItemTxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.textViewTitle);
            feeEachItem = itemView.findViewById(R.id.feeEachItem);
            pic = itemView.findViewById(R.id.picCart);
            totalEachItem = itemView.findViewById(R.id.textViewItemTotal);
            numberItemTxt = itemView.findViewById(R.id.numberItemTxt);
            plusItem = itemView.findViewById(R.id.plusCartBtn);
            minusItem = itemView.findViewById(R.id.minusCartBtn);
        }
    }
}
