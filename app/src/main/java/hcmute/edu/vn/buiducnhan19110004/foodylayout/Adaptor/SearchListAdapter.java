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

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity.MerchantActivity;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity.ShowDetailActivity;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FavoriteDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FavoriteDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class SearchListAdapter extends RecyclerView.Adapter<SearchListAdapter.ViewHolder> {
    // Context
    Context baseContext;
    // List
    ArrayList<FoodDomain> foodDomainArrayList;
    //Database
    FoodyDBHelper foodyDBHelper;
    FavoriteDB favoriteDB;

    public SearchListAdapter(ArrayList<FoodDomain> foodDomainArrayList, Context context) {
        this.foodDomainArrayList = foodDomainArrayList;
        this.baseContext = context;
        this.foodyDBHelper = new FoodyDBHelper(context);
        this.favoriteDB = new FavoriteDB(foodyDBHelper);
    }

    @NonNull
    @Override
    public SearchListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_food_list, parent, false);

        return new SearchListAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FavoriteDomain newFavorite = new FavoriteDomain(CurrentUser.getUser_id(), foodDomainArrayList.get(position).getId());

        holder.textViewFoodTitle.setText(foodDomainArrayList.get(position).getTitle());
        holder.textViewFee.setText(String.valueOf(foodDomainArrayList.get(position).getFee()));
        if(favoriteDB.isFavoriteExist(newFavorite) == true){
            holder.addFavoriteBtn.setImageResource(R.drawable.red_heart);
        }
        else{
            holder.addFavoriteBtn.setImageResource(R.drawable.like);
        }
        String picUrl = foodDomainArrayList.get(position).getPic();
        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(picUrl, "drawable", holder.itemView.getContext().getPackageName());
        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.imageViewFoodPicture);
        holder.FoodList_buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("object", foodDomainArrayList.get(position));
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.addFavoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favoriteDB.isFavoriteExist(newFavorite) != true){
                    holder.addFavoriteBtn.setImageResource(R.drawable.red_heart);
                    favoriteDB.InsertFavorite(newFavorite);
                    Toast.makeText(baseContext, "Insert favorite successfully", Toast.LENGTH_SHORT).show();
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
                intent.putExtra("productId", foodDomainArrayList.get(position).getId());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodDomainArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewFoodPicture, addFavoriteBtn;
        TextView textViewFoodTitle, textViewFee;
        TextView FoodList_buttonAdd;
        ConstraintLayout openMerchantBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.imageViewFoodPicture = itemView.findViewById(R.id.imageViewFoodPicture);
            this.textViewFoodTitle = itemView.findViewById(R.id.textViewFoodTitle);
            this.textViewFee = itemView.findViewById(R.id.textViewFee);
            this.FoodList_buttonAdd = itemView.findViewById(R.id.FoodList_buttonAdd);
            this.addFavoriteBtn = itemView.findViewById(R.id.addFavoriteButton);
            this.openMerchantBtn = itemView.findViewById(R.id.foodListViewHolder);
        }
    }
}
