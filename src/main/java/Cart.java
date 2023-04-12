import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
class Cart {
    private int id;
    private int userId;
    private String date;
    private List<CartItem> products;
    private int __v;
    private double total;

    public int getUserId() {
        return userId;
    }

    public List<CartItem> getProducts() {
        return products;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}