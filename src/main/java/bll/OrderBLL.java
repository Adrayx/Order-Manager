package bll;

import bll.validators.CheckQuantityValidator;
import bll.validators.Validator;
import dao.OrderDAO;
import model.Orders;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * În această clasa se realizeză operatiile pe baza de date legate de tabelul Orders
 */

public class OrderBLL {
    private List<Validator<Orders>> validators;
    private OrderDAO orderDAO;

    public OrderBLL()
    {
        validators = new ArrayList<>();
        validators.add(new CheckQuantityValidator());

        orderDAO = new OrderDAO();
    }

    /**
     * Aici se găsește comanda cu id-ul dat
     * @param id
     * @return or
     * @throws NoSuchElementException
     */
    public Orders findById(int id)
    {
        Orders or = orderDAO.findById(id);
        if(or == null)
            throw new NoSuchElementException("The order with id =" + id + " was not found!");
        return or;
    }

    /**
     * Aici se returnează o listă cu toate comenzile făcute
     * @return list
     * @throws NoSuchElementException
     */
    public List<Orders> findAll()
    {
        List<Orders> list = orderDAO.findAll();
        if(list == null)
            throw new NoSuchElementException("The order table is empty");
        return list;
    }

    /**
     * Aici se realizează inserarea în baza de date a unei noi comenzi
     * @param orders
     * @throws NoSuchElementException
     */
    public void insert(Orders orders)
    {
        for(Validator<Orders> validator : validators)
            validator.validate(orders);
        if(!orderDAO.insert(orders))
            throw new NoSuchElementException("The order could not been processed because the client or the product doesn't exist or the id of the order is already used!");
    }

    /**
     * Aici se realizează actualizarea unei comenzi după id
     * @param orders
     * @throws NoSuchElementException
     */
    public void update(Orders orders)
    {
        for(Validator<Orders> validator : validators)
            validator.validate(orders);
        if(!orderDAO.update(orders))
            throw new NoSuchElementException("The order with id =" + orders.getId()  + " was not found!");
    }

    /**
     * Aici se realizează ștergerea comenzii cu id-ul introdus
     * @param id
     * @throws NoSuchElementException
     */
    public void deleteById(int id)
    {
        if(!orderDAO.deleteById(id))
            throw new NoSuchElementException("The order with id =" + id  + " was not found!");
    }
}
