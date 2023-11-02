package logic;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Cart {

    public static int cartTotal;
    public static ArrayList<Product> cart = new ArrayList<>();

    public static void addToCart(int productIdx, int quantity) {
        Product product = Kitchen.getProduct(productIdx);

        if (!cart.contains(product)) {
            product.setQuantity(quantity);
            cart.add(product);
        } else {
            product.setQuantity(quantity);
        }
    }

    public static void removeFromCart(Product product) {
        cart.remove(product);
    }

    public static String getTotal() {
        if (cart.isEmpty())
            return "0";

        updateCart();
        int total = checkout();

        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
        return numberFormat.format(total);
    }

    public static int checkout() {
        int total = 0;
        for (Product product : cart) {
            total += product.getTotalPrice();
        }

        cartTotal = total;

        return total;
    }

    private static void updateCart() {
        for (int i = 0; i < cart.size(); i++) {
            var product = cart.get(i);

            if (product.getQuantity() == 0) {
                removeFromCart(product);
                i--;
            }
        }
    }
}
