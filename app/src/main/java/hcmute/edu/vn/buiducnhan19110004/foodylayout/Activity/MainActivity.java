package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.CategoryAdaptor;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Adaptor.PopularAdaptor;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CategoryDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.ProductDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Helper.CurrentUser;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.*;
public class MainActivity extends AppCompatActivity {
    //Views
    TextView textViewUsername;

    EditText editTextSearch;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList, recyclerViewTeen, recyclerViewFastFood;

    // Adapters
    private RecyclerView.Adapter categoryAdapter, popularAdapter, teenAdapter, fastFoodAdapter;

    //Lists
    private ArrayList<FoodDomain> popularList = new ArrayList<>();
    private ArrayList<FoodDomain> teenList = new ArrayList<>();
    private ArrayList<FoodDomain> fastFoodList = new ArrayList<>();

    // DB classes

    private FoodyDBHelper foodyDBHelper = new FoodyDBHelper(this);
    private ProductDB productDB = new ProductDB(foodyDBHelper);
    private CategoryDB categoryDB = new CategoryDB(foodyDBHelper);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetViews();
        recyclerViewCategory();
        recyclerViewPopular();
        recyclerViewTeen();
        recyclerViewFastFood();
        bottomNavigation();
    }

    private void SetViews(){
        this.textViewUsername = findViewById(R.id.textViewUsername);
        textViewUsername.setText("Hello " + CurrentUser.getFull_name());
        this.editTextSearch = findViewById(R.id.editTextSearch);
        /*
        editTextSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    String searching_text = editTextSearch.getText().toString();
                    Toast toast = Toast.makeText(MainActivity.this, "Incorrect username or password!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        */

        editTextSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keycode, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keycode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    String searching_text = editTextSearch.getText().toString();
                    Intent intent = new Intent(MainActivity.this, SearchProductActivity.class);
                    intent.putExtra("searching_text", searching_text);
                    startActivity(intent);
                    onRestart();
                }
                return false;
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity( new Intent(this, MainActivity.class));
    }
    private void bottomNavigation() {
        FloatingActionButton floatingActionButton = findViewById(R.id.cartBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout profileButton = findViewById(R.id.profileBtn);
        LinearLayout favoriteButton = findViewById(R.id.favoriteMainBtn);
        LinearLayout yourOrderButton = findViewById(R.id.yourOrderBtn);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CartListActivity.class));
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                onRestart();
            }
        });
        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(CurrentUser.isIsLogin() == false){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                    onRestart();
                }
                else {
                    Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                    intent.putExtra("profile_phone", CurrentUser.getPhone());
                    intent.putExtra("profile_name", CurrentUser.getFull_name());
                    intent.putExtra("profile_email", CurrentUser.getEmail());
                    intent.putExtra("profile_password", CurrentUser.getPassword());
                    startActivity(intent);
                    onRestart();
                }
            }
        });
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MyFavoritesActivity.class);
                startActivity(intent);
                onRestart();
            }
        });
        yourOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, OrderHistoryActivity.class));
            }
        });
    }

    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.recyclerViewCategories);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);

        ArrayList<CategoryDomain> categoryList = categoryDB.SelectAllCategories();
        categoryAdapter = new CategoryAdaptor(categoryList);
        recyclerViewCategoryList.setAdapter(categoryAdapter);
    }

    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.recyclerViewPopular);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);

        popularList = productDB.SearchProductByKeyWord("burger");
        popularAdapter = new PopularAdaptor(popularList, MainActivity.this);
        recyclerViewPopularList.setAdapter(popularAdapter);
    }
    private void recyclerViewTeen() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTeen = findViewById(R.id.recyclerViewTeen);
        recyclerViewTeen.setLayoutManager(linearLayoutManager);

        teenList = productDB.SearchProductByKeyWord("drink");
        teenList.addAll(productDB.SearchProductByKeyWord("donut"));
        teenAdapter = new PopularAdaptor(teenList, MainActivity.this);
        recyclerViewTeen.setAdapter(teenAdapter);
    }
    private void recyclerViewFastFood() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewFastFood = findViewById(R.id.recyclerViewFastFood);
        recyclerViewFastFood.setLayoutManager(linearLayoutManager);

        fastFoodList = productDB.SearchProductByKeyWord("pizza");
        fastFoodList.addAll(productDB.SearchProductByKeyWord("hotdog"));
        popularAdapter = new PopularAdaptor( fastFoodList, MainActivity.this);
        recyclerViewFastFood.setAdapter(popularAdapter);
    }
}