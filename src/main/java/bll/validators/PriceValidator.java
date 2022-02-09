package bll.validators;

import model.Product;

/**
 * Aici se verifică dacă prețul produsului introdus este poztiv
 */

public class PriceValidator implements Validator<Product> {
    /**
     * Aici se realizează verificarea pretului
     * @param product
     */
    @Override
    public void validate(Product product) {
        if(product.getPrice() <= 0)
            throw new IllegalArgumentException("Price is not valid");
    }
}
