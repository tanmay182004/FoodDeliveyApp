package hcmute.edu.vn.buiducnhan19110004.foodylayout.Domain;

public class MerchantDomain {
    private int id;
    private String name;
    private String address;
    private String pic;

    public MerchantDomain(int id, String name, String address, String pic) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.pic = pic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
