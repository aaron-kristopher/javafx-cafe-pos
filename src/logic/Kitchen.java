package logic;

import java.util.ArrayList;
import java.util.List;

public class Kitchen {

    public static ArrayList<Product> products = new ArrayList<>(List.of(
            new Product("Oreo Bliss Waffle", 150),
            new Product("Caramel Crystal Mocha Latte", 180),
            new Product("Banana Berry Bliss Toast", 120),
            new Product("Inkwell Latte", 140),
            new Product("Strawberry Dreamsicle Yogurt", 160)));

    public static Product getProduct(int productIdx) {
        return products.get(productIdx);
    }
}
