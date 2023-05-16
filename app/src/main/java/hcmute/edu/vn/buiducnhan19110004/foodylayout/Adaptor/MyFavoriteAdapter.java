package hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity.MerchantActivity;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity.MainActivity;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity.MyFavoritesActivity;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FavoriteDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.ProductDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CartDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FavoriteDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class MyFavoriteAdapter extends RecyclerView.Adapter<MyFavoriteAdapter.ViewHolder>{
    private ArrayList<FoodDomain> foodDomains = new ArrayList<>();
    private ArrayList<FavoriteDomain> favortieDomains = new ArrayList<FavoriteDomain>();

    FoodyDBHelper foodyDBHelper;
    private FavoriteDB favoriteDB;
    private ProductDB productDB;

    MainActivity mainActivity;
    private Context context;

    public MyFavoriteAdapter(FoodyDBHelper foodyDBHelper, ArrayList<FavoriteDomain> favoriteDomains, Context context) {
        this.favortieDomains = favoriteDomains;
        this.foodyDBHelper = foodyDBHelper;
        this.context = context;

        this.favoriteDB = new FavoriteDB(foodyDBHelper);
        this.productDB = new ProductDB(foodyDBHelper);

        if(favoriteDomains.size() > 0) {
            for(FavoriteDomain favoriteDomain : favoriteDomains) {
                FoodDomain food = productDB.SelectProductByID(favoriteDomain.getProduct_id());
                this.foodDomains.add(food);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_favorites, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        try{
            FoodDomain foodItem = foodDomains.get(position);

            holder.favoriteTitle.setText(foodItem.getTitle());
            holder.favoriteFee.setText(String.valueOf(foodItem.getFee()));
            int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier( foodItem.getPic(), "drawable", holder.itemView.getContext().getPackageName());

            Glide.with(holder.itemView.getContext()).load(drawableResourceId).into(holder.favoritePic);

            holder.removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    favoriteDB.DeleteFavoriteByProductId(foodItem.getId());
                    foodDomains.remove(position);
                    favortieDomains.remove(position);
                    notifyItemRemoved(position);
                    notifyDataSetChanged();
                }
            });
            holder.openMerchantBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(holder.itemView.getContext(), MerchantActivity.class);
                    intent.putExtra("productId", foodDomains.get(position).getId());
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        }
        catch (Exception e){
            System.out.println("No list read");
        }
    }

    @Override
    public int getItemCount() {
        return favortieDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView favoritePic, removeBtn;
        TextView favoriteTitle, favoriteFee;
        ConstraintLayout openMerchantBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favoritePic = itemView.findViewById(R.id.favoritePic);
            removeBtn = itemView.findViewById(R.id.removeFavoriteImageView);
            favoriteTitle = itemView.findViewById(R.id.favoriteTitleTxt);
            favoriteFee = itemView.findViewById(R.id.favoriteFeeTxt);
            openMerchantBtn = itemView.findViewById(R.id.favoriteViewHolder);
        }
    }
}
