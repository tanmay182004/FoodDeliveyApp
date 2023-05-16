package hcmute.edu.vn.buiducnhan19110004.foodylayout.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CartDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.CategoryDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FavoriteDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodVariationDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.FoodyDBHelper;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.MerchantDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.MerchantProductDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.ProductDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Database.TransactionDB;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.CategoryDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.FoodVariationDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.MerchantDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain.MerchantProductDomain;
import hcmute.edu.vn.buiducnhan19110004.foodylayout.R;

public class IntroActivity extends AppCompatActivity {
    // Views
    private ConstraintLayout startBtn;

    // DB classes
    FoodyDBHelper foodyDBHelper = new FoodyDBHelper(this);
    private ProductDB productDB = new ProductDB(foodyDBHelper);;
    private CategoryDB categoryDB = new CategoryDB(foodyDBHelper);;
    private FoodVariationDB food_variationDB = new FoodVariationDB(foodyDBHelper);
    private CartDB cartDB = new CartDB(foodyDBHelper);
    private TransactionDB transactionDB = new TransactionDB(foodyDBHelper);
    private FavoriteDB favoriteDB = new FavoriteDB(foodyDBHelper);
    private MerchantDB merchantDB = new MerchantDB(foodyDBHelper);
    private MerchantProductDB merchantProductDB = new MerchantProductDB(foodyDBHelper);

    // Lists
    ArrayList<FoodDomain> foodList = new ArrayList<>();
    ArrayList<CategoryDomain> categoryList = new ArrayList<>();
    ArrayList<FoodVariationDomain> food_variationList = new ArrayList<>();
    ArrayList<MerchantDomain> merchantList = new ArrayList<>();
    ArrayList<MerchantProductDomain> merchant_productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);


//        InsertFood();
//        InsertCategory();
//        InsertMerchant();
//        AutomaticInsertFoodVariation();
//        DeleteAllCart();

        startBtn=findViewById(R.id.startBtn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, LoginActivity.class));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
    private void InsertCategory(){
        categoryDB.DeleteAllCategories();
        categoryDB.SelectAllCategories();
        categoryList.add(new CategoryDomain("Pizza", "cat_1"));
        categoryList.add(new CategoryDomain("Burger", "cat_2"));
        categoryList.add(new CategoryDomain("Hot dog", "cat_3"));
        categoryList.add(new CategoryDomain("Drink", "cat_4"));
        categoryList.add(new CategoryDomain("Donut", "cat_5"));
        categoryList.add(new CategoryDomain("Tea", null));
        categoryList.add(new CategoryDomain("Coffee", null));

        for(CategoryDomain category: categoryList){
            categoryDB.InsertCategory(category);
        }
        categoryDB.SelectAllCategories();
    }
    private void InsertFood(){
        try{
            productDB.DeleteAllProducts();
        }
        catch (Exception e){
            System.out.println(e);
        }

        foodList.add(new FoodDomain(1, "KFC Chicken Burger", "burger1", "The unique combination of the super soft Burger and the crispy crust of the bold Zinger creates an incredibly delicious taste. In addition, the strange harmony between Burger with Kim Chi mixed cabbage and fresh vegetables makes the dish even better.", 8.76));
        foodList.add(new FoodDomain(2, "Lotte Cheese Burger", "burger2", "Burger believers will definitely stand still because Lotteria launches a super quality discount code on VinID Voucher. With only 36,000 VND, you will enjoy a huge Big Star Burger. What are you waiting for, access the VinID application to find out the promotional Lotteria information right away!", 2.9));
        foodList.add(new FoodDomain(3, "Special Cheese Burger", "burger3", "SPAM Spam and eggs and noodles That’s one steamy love triangle. Sizzle Pork And Mmm REDFIN With RedFin, when you see a home you love… You can book a tour on demand, so you can see homes first. And with your local RedFin agent guiding you, the right home is closer than you think. BURGER …", 8.79));
        foodList.add(new FoodDomain(4, "Cheese Burger", "burger4", "beef, Gouda Cheese, Special Sauce, Lettuce, tomato", 8.79));
        foodList.add(new FoodDomain(5, "Juicy Grilled Burger", "burger5", "Beautiful combination of eggs, roasted beef and french fries", 8.79));

        foodList.add(new FoodDomain(6, "Pepperoni pizza", "pizza1", "slices pepperoni,mozzarella cheese,fresh oregano, ground black pepper,pizza sauce", 9.76));
        foodList.add(new FoodDomain(7, "Vegetable pizza", "pizza2", "olive oil, Vegetable oil, pitted kalamata, cherry tomatoes, fresh oregano, basil", 8.5));
        foodList.add(new FoodDomain(8, "PizzaHut's best seller", "pizza3", "At Pizza Hut, we’re blowing the world of Hand-Tossed Pizza… sky high! Introducing our all new blow your mind Hand-Tossed Pizza", 30.5));
        foodList.add(new FoodDomain(9, "PizzaHut's Cheese pizza", "pizza4", "For unlimited time’s right. Any hand tossed for just ten bucks. We guarantee you love it or your next pizza is on us.Go for greatness people! Pizza Hut. Make it great!", 40.5));
        foodList.add(new FoodDomain(10, "Home made pizza", "pizza5", "Premium Traditional Large Size, or Medium Size", 8.5));

        foodList.add(new FoodDomain(11, "HotDog Combos", "hotdog1", "8 packs of hot dogs of different taste and flavor", 50.5));
        foodList.add(new FoodDomain(12, "HotDog and Fries", "hotdog2", "Hot dog with mustache and fry on the side", 10.5));
        foodList.add(new FoodDomain(13, "Korean Hot Dog", "hotdog3", "Premium Pizza Korean Hot Dog", 16.5));
        foodList.add(new FoodDomain(14, "Spicy Hot Dog", "hotdog4", "Hot flaming chili hot dog with special recipe", 30.5));
        foodList.add(new FoodDomain(15, "Italian Hot Dog", "hotdog5", "Juicy taste with sauce on top of a burnt spicy sausage", 18.5));

        foodList.add(new FoodDomain(16, "Taiwan Boba Tea", "drink1", "Bubble tea, tea-based drink that originated in Taiwan in the early 1980s", 10.5));
        foodList.add(new FoodDomain(17, "Tropical Soda", "drink2", "Tropical Fruit Soda Special Drink!", 15.5));
        foodList.add(new FoodDomain(18, "Boozy Whipped Coffee", "drink3", "Mixed coffee till it get to this beautiful shape with dairy, making a unforgettable drink", 12.5));
        foodList.add(new FoodDomain(19, "Keto Mojito", "drink4", "Cocktail with low carb, sugar free, best luxury drink suitable for Keto diets", 18.5));
        foodList.add(new FoodDomain(20, "Cotton Candy Margaritas", "drink5", "This is going to very quickly become a summer classic. This Cotton Candy Margarita recipe is going to be your new favorite drink!", 20.0));

        foodList.add(new FoodDomain(21, "Boston Kreme", "donut1", "Soft and sweet, like a warm hug in your mouth, a Boston Kreme combines chocolate icing and a pudding filling. This is everything a donut can be all wrapped up into one treat. ", 15.5));
        foodList.add(new FoodDomain(22, "Chocolate Glazed", "donut2", "It’s a glazed donut, but with chocolate. Now that’s perfection!", 10.5));
        foodList.add(new FoodDomain(23, "French Cruller", "donut3", "The most distinct donut in terms of texture. The French cruller (which is glazed and far, far superior to a boring old plain cruller) is crunchy on the outside and flaky on the inside. A real delight for the senses. It’s also one of the most satisfying to dunk in milk. All of that plus its unbeatable taste mean I might actually have ranked it too low.", 12.5));
        foodList.add(new FoodDomain(24, "Maple Frosted", "donut4", "The most “mature” of the frosted donut family, the maple donut is still just as sweet and wonderful as all the others icings. ", 18.5));
        foodList.add(new FoodDomain(25, "Chocolate Glazed", "donut5", "A regular blueberry donut is fine. It’s fine. But a glazed version is the only way to go. It’s crispy yet soft, sweet yet….well it’s still really sweet even with the fruit. It’s not like the blueberries make it especially healthy, but we’re not worried about things like “calories” or “living a long life” when we’re digging through a box of donuts.", 16.5));
        for(FoodDomain food: foodList){
            productDB.InsertProduct(food);
        }

        productDB.SelectAllProducts();
    }
    private void InsertMerchant() {
        merchantList.add(new MerchantDomain(1, "Pizza Huts", "210 Nguyễn An Ninh, phường Dĩ An, thị xã Dĩ An, tỉnh Bình Dương", "pizza_merchant"));
        merchantList.add(new MerchantDomain(2, "Bobapop", " 96 Nguyễn An Ninh, Dĩ An, Bình Dương", "drink_merchant"));
        merchantList.add(new MerchantDomain(3, "Hotdog Bigbro", "85 Đ. Hồ Tùng Mậu, Bến Nghé, Quận 1, Thành phố Hồ Chí Minh", "hotdog_merchant"));
        merchantList.add(new MerchantDomain(4, "Pinkbox Doughnuts", "9435 W Tropicana Ave, Las Vegas, NV 89147, United States", "donut_merchant"));
        merchantList.add(new MerchantDomain(5, "Lotteria", "18/7, QL1A, Phường Linh Trung, Thủ Đức, Thành phố Hồ Chí Minh", "burger_merchant"));

        merchant_productList.add(new MerchantProductDomain(1, 6));
        merchant_productList.add(new MerchantProductDomain(1, 7));
        merchant_productList.add(new MerchantProductDomain(1, 8));
        merchant_productList.add(new MerchantProductDomain(1, 9));
        merchant_productList.add(new MerchantProductDomain(1, 10));
        merchant_productList.add(new MerchantProductDomain(2, 16));
        merchant_productList.add(new MerchantProductDomain(2, 17));
        merchant_productList.add(new MerchantProductDomain(2, 18));
        merchant_productList.add(new MerchantProductDomain(2, 19));
        merchant_productList.add(new MerchantProductDomain(2, 20));
        merchant_productList.add(new MerchantProductDomain(3, 11));
        merchant_productList.add(new MerchantProductDomain(3, 12));
        merchant_productList.add(new MerchantProductDomain(3, 13));
        merchant_productList.add(new MerchantProductDomain(3, 14));
        merchant_productList.add(new MerchantProductDomain(3, 15));
        merchant_productList.add(new MerchantProductDomain(4, 21));
        merchant_productList.add(new MerchantProductDomain(4, 22));
        merchant_productList.add(new MerchantProductDomain(4, 23));
        merchant_productList.add(new MerchantProductDomain(4, 24));
        merchant_productList.add(new MerchantProductDomain(4, 25));
        merchant_productList.add(new MerchantProductDomain(5, 1));
        merchant_productList.add(new MerchantProductDomain(5, 2));
        merchant_productList.add(new MerchantProductDomain(5, 3));
        merchant_productList.add(new MerchantProductDomain(5, 4));
        merchant_productList.add(new MerchantProductDomain(5, 5));

        for(MerchantDomain merchantDomain : merchantList) {
            merchantDB.InsertMerchant(merchantDomain);
        }

        for(MerchantProductDomain merchantProductDomain : merchant_productList) {
            merchantProductDB.InsertMerchantProduct(merchantProductDomain);
        }
    }
    private void AutomaticInsertFoodVariation(){
        try{
            food_variationDB.DeleteAllVariations();
        }
        catch (Exception e){
            System.out.println(e);
        }

        try{
            ArrayList<CategoryDomain> categoryDomainArrayList;
            categoryDomainArrayList = categoryDB.SelectAllCategories();

            for(CategoryDomain category: categoryDomainArrayList) {
                String category_title = category.getTitle();
                for(FoodDomain product: productDB.SearchProductByKeyWord(category_title)){
                    FoodVariationDomain variation = new FoodVariationDomain(category.getId(), product.getId());
                    food_variationDB.InsertFoodVariation(variation);
                }
            }

            food_variationDB.SelectAllVariations();
        }

        catch (Exception e){
            System.out.println(e);
        }
    }
    private void DeleteAllCart(){
        cartDB.ClearCartTable();
    }

    private void DeleteAllFavorite() {
        favoriteDB.DeleteAllFavorites();
    }

    private void DeleteAllTransaction() {
        transactionDB.DeleteAllTransactions();
    }
}