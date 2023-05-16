package hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain;

public class MerchantProductDomain {
    private int merchant_id;
    private int product_id;

    public MerchantProductDomain(int merchant_id, int product_id) {
        this.merchant_id = merchant_id;
        this.product_id = product_id;
    }

    public int getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(int merchant_id) {
        this.merchant_id = merchant_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }
}
