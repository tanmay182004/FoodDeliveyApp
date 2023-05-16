package hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain;

import java.io.Serializable;

public class FoodVariationDomain implements Serializable {
    private int category_id;
    private int product_id;

    public FoodVariationDomain() {
    }

    public FoodVariationDomain(int category_id, int product_id) {
        this.category_id = category_id;
        this.product_id = product_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public int getProduct_id() {
        return product_id;
    }
}
