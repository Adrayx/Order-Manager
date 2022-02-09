package bll;

import bll.validators.PriceValidator;
import bll.validators.QuantityValidator;
import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Aceasta este clasa în care se realizează interogările pe datele din tabelul Product
 */

public class ProductBLL {
    private List<Validator<Product>> validators;
    private ProductDAO productDAO;

    public ProductBLL()
    {
        validators = new ArrayList<>();
        validators.add(new QuantityValidator());
        validators.add(new PriceValidator());

        productDAO = new ProductDAO();
    }

    /**
     * Se găsește produsul cu id-ul introdus
     * @param id
     * @return pr
     * @throws NoSuchElementException
     */
    public Product findById(int id)
    {
        Product pr = productDAO.findById(id);
        if(pr == null)
        {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return pr;
    }

    /**
     * Se returnează primul produs cu numele introdus
     * @param name
     * @return pr
     * @throws NoSuchElementException
     */
    public Product findByName(String name)
    {
        Product pr = productDAO.findByName(name);
        if(pr == null)
        {
            throw new NoSuchElementException("The product with the name" + name + " was not found!");
        }
        return pr;
    }

    /**
     * Se returnează o listă cu toate produsele din baza de date
     * @return list
     * @throws Exception
     */
    public List<Product> findAll() throws Exception {
        List<Product> list = productDAO.findAll();
        if(list == null)
        {
            throw new Exception("Product table is empty");
        }
        return list;
    }

    /**
     * Se inserează un nou produs în baza de date
     * @param product
     * @throws NoSuchElementException
     */
    public void insert(Product product)
    {
        for(Validator<Product> validator : validators)
            validator.validate(product);
        if(!productDAO.insert(product))
            throw new NoSuchElementException("Product with this id already exists!");
    }

    /**
     * Se actualizează datele produsului cu id-ul itnrodus
     * @param product
     * @throws NoSuchElementException
     */
    public void update(Product product)
    {
        for(Validator<Product> validator : validators)
            validator.validate(product);
        if(!productDAO.update(product))
            throw new NoSuchElementException("Product with id = " + product.getId() + " doesn't exists!");
    }

    /**
     * Se șterge produsul cu id-ul introdus din baza de date
     * @param id
     * @throws NoSuchElementException
     */
    public void delete(int id)
    {
        if(!productDAO.deleteById(id))
            throw new NoSuchElementException("Product with id = " + id + " doesn't exists!");
    }

    public JTable createTable() throws IllegalAccessException {
        return productDAO.createTable(productDAO.findAll());
    }
}
