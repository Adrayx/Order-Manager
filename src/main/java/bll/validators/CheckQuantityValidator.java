package bll.validators;

import dao.ProductDAO;
import model.Orders;
import model.Product;

import java.util.NoSuchElementException;

/**
 * Aceasta este una din clasele care implementează interfața Validator
 * și aici se verifică dacă utilizatorul a introdus o cantitate validă
 * pentru a se putea realiza tranzacția
 */

public class CheckQuantityValidator implements Validator<Orders> {
    /**
     * Aici se realizeză verificarea comenzii
     * @param orders
     */
    @Override
    public void validate(Orders orders) {
        ProductDAO dao = new ProductDAO();
        Product pr = dao.findById(orders.getProduct_id());
        if(pr == null)
            throw new NoSuchElementException("The product doesn't exist");
        if(orders.getQuantity() > pr.getQuantity())
            throw new IllegalArgumentException("The quantity is larger than the one in the stock");
    }
}
