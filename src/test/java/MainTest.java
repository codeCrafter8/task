import java.io.IOException;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class MainTest {
    @org.testng.annotations.Test
    public void testGetUsers() throws IOException {
        List<User> users = Main.getUsers();
        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    @org.testng.annotations.Test
    public void testGetCarts() throws IOException {
        List<Cart> carts = Main.getCarts();
        assertNotNull(carts);
        assertTrue(carts.size() > 0);
    }

    @org.testng.annotations.Test
    public void testGetProducts() throws IOException {
        List<Product> products = Main.getProducts();
        assertNotNull(products);
        assertTrue(products.size() > 0);
    }

    @org.testng.annotations.Test
    public void testGetCategoryValues() throws IOException {
        List<Product> products = Main.getProducts();
        Map<String, Double> categoryValues = Main.getCategoryValues(products);
        assertNotNull(categoryValues);
        assertTrue(categoryValues.size() > 0);
    }

    @org.testng.annotations.Test
    public void testGetMostExpensiveCart() throws IOException {
        List<Cart> carts = Main.getCarts();
        List<Product> products = Main.getProducts();
        Cart mostExpensiveCart = Main.getMostExpensiveCart(carts, products);
        assertNotNull(mostExpensiveCart);
        assertTrue(mostExpensiveCart.getTotal() > 0);
    }

    @org.testng.annotations.Test
    public void testCalculateDistance() {
        Geo geo1 = new Geo(-37.3159, 81.1496);
        Geo geo2 = new Geo(-43.9509, -34.4618);
        double distance = Main.calculateDistance(geo1, geo2);
        assertEquals(8898.278635448845, distance, 0.000001);
    }
}

