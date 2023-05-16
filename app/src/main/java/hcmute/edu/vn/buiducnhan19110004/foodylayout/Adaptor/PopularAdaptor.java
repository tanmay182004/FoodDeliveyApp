package hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity.MerchantActivity;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity.MainActivity;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity.ShowDetailActivity;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CartDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FavoriteDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CartDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FavoriteDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

import java.util.ArrayList;

public class PopularAdaptor extends RecyclerView.Adapter<PopularAdaptor.ViewHolder> {
    FoodyDBHelper foodyDBHelper;
    ArrayList<FoodDomain> popularFood;
    Context context;
    CartDB cartDB;
    FavoriteDB favoriteDB;

    public PopularAdaptor(ArrayList<FoodDomain> popularFood, Context context) {
        this.popularFood = popularFood;
        this.context = context;
        this.foodyDBHelper = new FoodyDBHelper(context);
        this.cartDB = new CartDB(foodyDBHelper);
        this.favoriteDB = new FavoriteDB(foodyDBHelper);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_popular, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        FavoriteDomain newFavorite = new FavoriteDomain(CurrentUser.getUser_id(), popularFood.get(position).getId());

        holder.title.setText(popularFood.get(position).getTitle());
        holder.fee.setText(String.valueOf(popularFood.get(position).getFee()));
        if(favoriteDB.isFavoriteExist(newFavorite) == true){
            holder.addFavoriteBtn.setImageResource(R.drawable.red_heart);
        }
        else{
            holder.addFavoriteBtn.setImageResource(R.drawable.like);
        }
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(popularFood.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);

        holder.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", popularFood.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.addFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favoriteDB.isFavoriteExist(newFavorite) != true){
                    holder.addFavoriteBtn.setImageResource(R.drawable.red_heart);
                    favoriteDB.InsertFavorite(newFavorite);
                    Toast.makeText(context, "Insert favorite successfully", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();

                }
                else{
                    holder.addFavoriteBtn.setImageResource(R.drawable.like);
                    favoriteDB.DeleteFavorite(newFavorite);
                    notifyDataSetChanged();
                }
            }
        });
        holder.openMerchantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), MerchantActivity.class);
                intent.putExtra("productId", popularFood.get(position).getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return popularFood.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, fee;
        ImageView pic, addFavoriteBtn;
        TextView addBtn;
        ConstraintLayout openMerchantBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.title);
            this.fee = itemView.findViewById(R.id.fee);
            this.pic = itemView.findViewById(R.id.pic);
            this.addBtn = itemView.findViewById(R.id.addBtn);
            this.addFavoriteBtn = itemView.findViewById(R.id.addFavoriteButton);
            this.openMerchantBtn = itemView.findViewById(R.id.popularViewHolder);
        }
    }
}
