package hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain;

public class TransactionDomain {
    private int user_id;
    private int product_id;
    private int quantity;
    private String transaction_time;

    public TransactionDomain(int user_id, int product_id, int quantity, String transaction_time) {
        this.user_id = user_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.transaction_time = transaction_time;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTransaction_time() {
        return transaction_time;
    }

    public void setTransaction_time(String transaction_time) {
        this.transaction_time = transaction_time;
    }
}
