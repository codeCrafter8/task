import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class Main {
    private static final String USERS_API_URL = "https://fakestoreapi.com/users";
    private static final String CARTS_API_URL = "https://fakestoreapi.com/carts/?startdate=2000-01-01&enddate=2023-04-07";
    private static final String PRODUCTS_API_URL = "https://fakestoreapi.com/products";

    public static void main(String[] args) throws IOException {
        List<User> users = getUsers();
        List<Cart> carts = getCarts();
        List<Product> products = getProducts();

        // Find all available product categories and the total value of products of a given category
        Map<String, Double> categoryValues = getCategoryValues(products);
        System.out.println("Product categories and the total value products of a given category:");
        for (Map.Entry<String, Double> entry : categoryValues.entrySet()) {
            System.out.printf("%s: %.2f\n", entry.getKey(), entry.getValue());
        }
        System.out.print("\n");

        // Find a cart with the highest value, determine its value and full name of its owner
        Cart mostExpensiveCart = getMostExpensiveCart(carts, products);
        System.out.println("Cart with the highest value:");
        System.out.println("Value: " + mostExpensiveCart.getTotal() + " and it's owner: " + getUserFullName(mostExpensiveCart.getUserId(), users) + "\n");

        //Find the two users living furthest away from each other
        double maxDistance = 0;
        User user1 = null;
        User user2 = null;
        for (int i = 0; i < users.size(); i++) {
            User u1 = users.get(i);
            for (int j = i + 1; j < users.size(); j++) {
                User u2 = users.get(j);
                double distance = calculateDistance(u1.getAddress().getGeo(), u2.getAddress().getGeo());
                if (distance > maxDistance) {
                    maxDistance = distance;
                    user1 = u1;
                    user2 = u2;
                }
            }
        }
        assert user1 != null;
        System.out.println("The two users living furthest away from each other are " + user1.getFullName() + " and " + user2.getFullName());
    }

    static List<User> getUsers() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode usersNode = objectMapper.readTree(new URL(USERS_API_URL));
        List<User> users = new ArrayList<>();
        for (JsonNode userNode : usersNode) {
            User user = new User(
                    Integer.parseInt(String.valueOf(userNode.get("id"))),
                    String.valueOf(userNode.get("name").get("firstname")),
                    String.valueOf(userNode.get("name").get("lastname")),
                    String.valueOf(userNode.get("email")),
                    String.valueOf(userNode.get("username")),
                    String.valueOf(userNode.get("password")),
                    String.valueOf(userNode.get("address").get("city")),
                    String.valueOf(userNode.get("address").get("street")),
                    Integer.parseInt(String.valueOf(userNode.get("address").get("number"))),
                    String.valueOf(userNode.get("address").get("zipcode")),
                    userNode.get("address").get("geolocation").get("lat").asDouble(),
                    userNode.get("address").get("geolocation").get("long").asDouble(),
                    String.valueOf(userNode.get("phone")),
                    Integer.parseInt(String.valueOf(userNode.get("__v")))
            );
            users.add(user);
        }
        return users;
    }

    static List<Cart> getCarts() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode cartsNode = objectMapper.readTree(new URL(CARTS_API_URL));
        List<Cart> carts = new ArrayList<>();
        for (JsonNode cartNode : cartsNode) {
            Cart cart = objectMapper.treeToValue(cartNode, Cart.class);
            carts.add(cart);
        }
        return carts;
    }

    static List<Product> getProducts() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode productsNode = objectMapper.readTree(new URL(PRODUCTS_API_URL));
        List<Product> products = new ArrayList<>();
        for (JsonNode productNode : productsNode) {
            Product product = new Product(
                    productNode.get("id").asInt(),
                    productNode.get("title").asText(),
                    productNode.get("price").asDouble(),
                    productNode.get("description").asText(),
                    productNode.get("category").asText(),
                    productNode.get("image").asText(),
                    productNode.get("rating").get("rate").asDouble(),
                    productNode.get("rating").get("count").asInt()
            );
            products.add(product);
        }
        return products;
    }
    static Map<String, Double> getCategoryValues(List<Product> products) {
        Map<String, Double> categoryValues = new HashMap<>();
        for (Product product : products) {
            String category = product.getCategory();
            double price = product.getPrice();
            categoryValues.merge(category, price, Double::sum);
        }
        return categoryValues;
    }

    static Cart getMostExpensiveCart(List<Cart> carts, List<Product> products) {
        double total = 0.0;
        double price = 0.0;
        for(Cart cart : carts){
            List<CartItem> cartItems  = cart.getProducts();
            for(CartItem cartItem : cartItems){
                int id = cartItem.getProductId();
                for(Product product : products){
                    if(product.getId() == id)
                        price = product.getPrice();
                }
                total += price * cartItem.getQuantity();
                cart.setTotal(total);
            }
            total = 0;
        }
        return Collections.max(carts, Comparator.comparingDouble(Cart::getTotal));
    }

    private static String getUserFullName(int userId, List<User> users) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user.getFullName();
            }
        }
        return "";
    }

    static double calculateDistance(Geo geo1, Geo geo2) {
        double lat1 = geo1.getLat();
        double lon1 = geo1.getLng();
        double lat2 = geo2.getLat();
        double lon2 = geo2.getLng();
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double deltaPhi = Math.toRadians(lat2 - lat1);
        double deltaLambda = Math.toRadians(lon2 - lon1);
        double a = Math.sin(deltaPhi / 2) * Math.sin(deltaPhi / 2) +
                Math.cos(phi1) * Math.cos(phi2) * Math.sin(deltaLambda / 2) * Math.sin(deltaLambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return 6371 * c;
    }
}