package controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Optional;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import logic.Cart;
import logic.Kitchen;
import logic.Product;

public class Controller {

    static int index;
    static boolean inCart = false;
    static boolean inReceipt = false;
    static int cashAmount;
    static int changeAmount;
    static boolean run = true;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private ImageView waffleImg;
    @FXML
    private ImageView toastImg;
    @FXML
    private ImageView yogurtImg;
    @FXML
    private ImageView mochaImg;
    @FXML
    private ImageView latteImg;

    @FXML
    private Label count;

    @FXML
    private Button btnShowMenu;
    @FXML
    private Button minus;
    @FXML
    private Button add;

    @FXML
    private ImageView img1;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;
    @FXML
    private ImageView img4;
    @FXML
    private ImageView img5;

    @FXML
    private Label foodName1;
    @FXML
    private Label foodName2;
    @FXML
    private Label foodName3;
    @FXML
    private Label foodName4;
    @FXML
    private Label foodName5;

    @FXML
    private Label price1;
    @FXML
    private Label price2;
    @FXML
    private Label price3;
    @FXML
    private Label price4;
    @FXML
    private Label price5;

    @FXML
    private Label qtyLabel1;
    @FXML
    private Label qtyLabel2;
    @FXML
    private Label qtyLabel3;
    @FXML
    private Label qtyLabel4;
    @FXML
    private Label qtyLabel5;

    @FXML
    private Label productQty1;
    @FXML
    private Label productQty2;
    @FXML
    private Label productQty3;
    @FXML
    private Label productQty4;
    @FXML
    private Label productQty5;

    @FXML
    private Label totalLabel;
    @FXML
    private Label total;

    @FXML
    private Label cartEmpty;

    @FXML
    private Button checkoutBtn;

    @FXML
    private Label receiptFoodName1;
    @FXML
    private Label receiptFoodName2;
    @FXML
    private Label receiptFoodName3;
    @FXML
    private Label receiptFoodName4;
    @FXML
    private Label receiptFoodName5;

    @FXML
    private Label receiptPrice1;
    @FXML
    private Label receiptPrice2;
    @FXML
    private Label receiptPrice3;
    @FXML
    private Label receiptPrice4;
    @FXML
    private Label receiptPrice5;

    @FXML
    private Label receiptPriceTotal1;
    @FXML
    private Label receiptPriceTotal2;
    @FXML
    private Label receiptPriceTotal3;
    @FXML
    private Label receiptPriceTotal4;
    @FXML
    private Label receiptPriceTotal5;

    @FXML
    private Label receiptTotal;
    @FXML
    private Label cash;
    @FXML
    private Label change;
    @FXML
    private Label receptTotal;

    private Image waffle = new Image(getClass().getResourceAsStream("../img/waffle.png"));
    private Image toast = new Image(getClass().getResourceAsStream("../img/toast.png"));
    private Image yogurt = new Image(getClass().getResourceAsStream("../img/yogurt.png"));
    private Image mocha = new Image(getClass().getResourceAsStream("../img/mocha.png"));
    private Image latte = new Image(getClass().getResourceAsStream("../img/latte.png"));

    String css = this.getClass().getResource("..\\styles\\style.css").toExternalForm();

    @FXML
    public void initialize() {

        if (inCart) {
            setupCart();
        } else if (inReceipt) {
            setupReceipt();
        } else {
            setupSubpage();
        }

    }

    // METHODS TO INITIALIZE DIFFERENT PAGES

    private void setupSubpage() {
        waffleImg.setImage(waffle);
        toastImg.setImage(toast);
        yogurtImg.setImage(yogurt);
        mochaImg.setImage(mocha);
        latteImg.setImage(latte);

        count.setText((Kitchen.getProduct(index).getQuantity() == 0) ? "1"
                : Integer.toString(Kitchen.getProduct(index).getQuantity()));
    }

    private void setupCart() {

        if (!Cart.cart.isEmpty()) {
            cartEmpty.setVisible(false);

            Image[] images = { waffle, mocha, toast, latte, yogurt };
            ImageView[] imagesViews = { img1, img2, img3, img4, img5 };
            Label[] foodNames = { foodName1, foodName2, foodName3, foodName4, foodName5 };
            Label[] prices = { price1, price2, price3, price4, price5 };
            Label[] qtyLabels = { qtyLabel1, qtyLabel2, qtyLabel3, qtyLabel4, qtyLabel5 };
            Label[] productQtys = { productQty1, productQty2, productQty3, productQty4, productQty5 };

            int position = 0;

            for (Product product : Cart.cart) {
                int index = Kitchen.products.indexOf(product);

                ImageView imageView = imagesViews[position];
                imageView.setImage(images[index]);
                imageView.setVisible(true);

                Label foodName = foodNames[position];
                foodName.setText(product.getName());
                foodName.setVisible(true);

                Label price = prices[position];
                price.setText("Php " + Integer.toString(product.getPrice()));
                price.setVisible(true);

                Label qtyLabel = qtyLabels[position];
                qtyLabel.setVisible(true);

                Label productQty = productQtys[position];
                productQty.setText(Integer.toString(product.getQuantity()));
                productQty.setVisible(true);

                totalLabel.setVisible(true);
                total.setText("Php " + Cart.getTotal());
                total.setVisible(true);

                checkoutBtn.setVisible(true);

                position++;
            }
        }
    }

    private void setupReceipt() {
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        setReceiptInfo();

        Label[] foodNames = { receiptFoodName1, receiptFoodName2, receiptFoodName3, receiptFoodName4,
                receiptFoodName5 };
        Label[] prices = { receiptPrice1, receiptPrice2, receiptPrice3, receiptPrice4, receiptPrice5 };
        Label[] priceTotals = { receiptPriceTotal1, receiptPriceTotal2, receiptPriceTotal3, receiptPriceTotal4,
                receiptPriceTotal5 };

        int position = 0;

        for (Product product : Cart.cart) {
            Label foodName = foodNames[position];
            foodName.setText(String.format("%s  %s", Integer.toString(product.getQuantity()), product.getName()));
            foodName.setVisible(true);

            Label price = prices[position];
            price.setText("@ Php " + Integer.toString(product.getPrice()));
            price.setVisible(true);

            Label priceTotal = priceTotals[position];
            priceTotal.setText("Php " + numberFormat.format(product.getTotalPrice()));
            priceTotal.setVisible(true);

            receiptTotal.setText("Php " + Cart.getTotal());
            cash.setText("Php " + numberFormat.format(cashAmount));
            change.setText("Php " + numberFormat.format(changeAmount));

            position++;
        }
    }

    // METHODS TO TRACK WHAT PAGE WE ARE IN

    private void setIndex(int idx) {
        index = idx;
    }

    private void inCart(boolean page) {
        inCart = page;
    }

    private void inReceipt(boolean page) {
        inReceipt = page;
    }

    // NAVIGATION METHODS

    public void openWafflePage(ActionEvent event) throws IOException {
        setIndex(0);

        root = FXMLLoader.load(getClass().getResource("..\\ui\\waffle-page.fxml"));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void openMochaPage(ActionEvent event) throws IOException {
        setIndex(1);

        root = FXMLLoader.load(getClass().getResource("..\\ui\\mocha-page.fxml"));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void openToastPage(ActionEvent event) throws IOException {
        setIndex(2);

        root = FXMLLoader.load(getClass().getResource("..\\ui\\toast-page.fxml"));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void openLattePage(ActionEvent event) throws IOException {
        setIndex(3);

        root = FXMLLoader.load(getClass().getResource("..\\ui\\latte-page.fxml"));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void openYogurtPage(ActionEvent event) throws IOException {
        setIndex(4);

        root = FXMLLoader.load(getClass().getResource("..\\ui\\yogurt-page.fxml"));

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void openCart(ActionEvent event) throws IOException {
        inCart(true);

        root = FXMLLoader.load(getClass().getResource("..\\ui\\cart.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    private void setReceiptInfo() {
        while (run) {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Cash");
            dialog.setHeaderText("Please enter Cash Amount:");
            dialog.setContentText("Cash:");

            // Get the text field from the dialog
            Optional<String> result = dialog.showAndWait();

            // Process the user's input
            result.ifPresent(input -> {
                try {
                    // Try to parse the input as an integer
                    int integerValue = Integer.parseInt(input);

                    if (Cart.cartTotal > integerValue)
                        throw new NumberFormatException();
                    // If parsing is successful, you can use the integer value
                    cashAmount = integerValue;
                    changeAmount = (integerValue - Cart.cartTotal);
                    run = false;
                } catch (NumberFormatException e) {
                    // Display an error message for invalid input
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Invalid Input");
                    alert.setContentText("Please enter a valid input that is also more than your cart total.");
                    alert.showAndWait();
                }
            });
        }
    }

    public void openReceipt(ActionEvent event) throws IOException {
        inCart(false);
        inReceipt(true);

        root = FXMLLoader.load(getClass().getResource("..\\ui\\receipt.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }

    public void backToHome(ActionEvent event) throws IOException {
        inCart(false);

        root = FXMLLoader.load(getClass().getResource("..\\ui\\menu.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();

    }

    // METHODS TO CHANGE PRODUCT QUANTITY

    public void minusCount(ActionEvent event) throws IOException {
        int quantity = Integer.parseInt(count.getText());

        quantity = (quantity > 0) ? --quantity : 0;

        count.setText(Integer.toString(quantity));
    }

    public void addCount(ActionEvent event) throws IOException {
        count.setText(Integer.toString(Integer.parseInt(count.getText()) + 1));
    }

    // METHODS TO ADD EACH PRODUCT TO CART

    public void addToCartAlert(ActionEvent e) {
        Alert alert = new Alert(AlertType.NONE,
                "Order Added to Cart", ButtonType.OK);
        alert.showAndWait();
    }

    public void addWaffleToCart(ActionEvent event) throws IOException {
        addToCartAlert(event);
        int quantity = Integer.parseInt(count.getText());
        Cart.addToCart(0, quantity);
        backToHome(event);
    }

    public void addMochaToCart(ActionEvent event) throws IOException {
        addToCartAlert(event);
        int quantity = Integer.parseInt(count.getText());
        Cart.addToCart(1, quantity);
        backToHome(event);
    }

    public void addToastToCart(ActionEvent event) throws IOException {
        addToCartAlert(event);
        int quantity = Integer.parseInt(count.getText());
        Cart.addToCart(2, quantity);
        backToHome(event);
    }

    public void addLatteToCart(ActionEvent event) throws IOException {
        addToCartAlert(event);
        int quantity = Integer.parseInt(count.getText());
        Cart.addToCart(3, quantity);
        backToHome(event);
    }

    public void addYogurtToCart(ActionEvent event) throws IOException {
        addToCartAlert(event);
        int quantity = Integer.parseInt(count.getText());
        Cart.addToCart(4, quantity);
        backToHome(event);
    }

    // METHODS TO GET CASH AS INPUT

    public void getCash() {

    }

}
