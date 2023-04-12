class Product {
    private final int id;
    private final String title;

    private final double price;
    private final String description;
    private final String category;
    private final String image;
    private final double rate;
    private final int count;

    public Product(int id, String title, double price, String description, String category, String image, double rate, int count) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.description = description;
        this.category = category;
        this.image = image;
        this.rate = rate;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }
}