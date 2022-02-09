package bll.validators;

import model.Product;

/**
 * Ca și la validatorul de cantitate, aici se verifică dacă stocul introdus este pozitiv
 */

public class QuantityValidator implements Validator<Product>{
    /**
     * Aici se realizează verificarea cantității introduse
     * @param product
     */
    @Override
    public void validate(Product product) {
        if(product.getQuantity() < 0)
            throw new IllegalArgumentException("Quantity is not valid!");
    }
}
