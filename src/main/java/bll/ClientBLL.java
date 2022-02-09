package bll;

import bll.validators.EmailValidator;
import bll.validators.NameValidator;
import bll.validators.Validator;
import dao.ClientDAO;
import model.Client;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Aici se realizează verificarea datelor introduse de către utilizator,
 * iar mai apoi se realizează interogările, dacă datele sunt valide
 */

public class ClientBLL {
    private List<Validator<Client>> validators;
    private ClientDAO clientDAO;

    public ClientBLL(){
        validators = new ArrayList<Validator<Client>>();
        validators.add(new EmailValidator());
        validators.add(new NameValidator());

        clientDAO = new ClientDAO();
    }

    /**
     * Se caută clientul cu id-ul respectiv si se returnează in caz ca acesta se regăsește în baza de date
     * @param id
     * @return cl
     * @throws NoSuchElementException
     */
    public Client findById(int id)
    {
        Client cl = clientDAO.findById(id);
        if(cl == null)
        {
            throw new NoSuchElementException("The client with id =" + id + " was not found!");
        }
        return cl;
    }

    /**
     * Se găsește primul client cu numele dorit
     * @param name
     * @return cl
     * @throws NoSuchElementException
     */
    public Client findByName(String name)
    {
        Client cl = clientDAO.findByName(name);
        if(cl == null)
        {
            throw new NoSuchElementException("The client with the name" + name + " was not found!");
        }
        return cl;
    }

    /**
     * Se returnează o lista cu toți clienții din baza de date
     * @return list
     * @throws Exception
     */
    public List<Client> findAll() throws Exception {
        List<Client> list = clientDAO.findAll();
        if(list == null)
        {
            throw new Exception("The clients table is empty");
        }
        return list;
    }

    /**
     * Se inserează clientul în baza de date
     * @param c
     * @throws NoSuchElementException
     */
    public void insert(Client c)
    {
        for(Validator<Client> validator : validators)
            validator.validate(c);
        if(!clientDAO.insert(c))
            throw new NoSuchElementException("Client with this id already exists!");
    }

    /**
     * Se actualizează datele clientului cu id-ul introdus de către utilizator
     * @param c
     * @throws NoSuchElementException
     */
    public void update(Client c)
    {
        for(Validator<Client> validator : validators)
            validator.validate(c);
        if(!clientDAO.update(c))
            throw new NoSuchElementException("Product with id = " + c.getId() + " doesn't exists!");
    }

    /**
     * Se realizează stergerea clientului din baza de date după id-ul dat
     * @param id
     * @throws NoSuchElementException
     */
    public void delete(int id)
    {
        if(!clientDAO.deleteById(id))
            throw new NoSuchElementException("Product with id = " + id + " doesn't exists!");
    }

    public JTable createTable() throws IllegalAccessException {
        return clientDAO.createTable(clientDAO.findAll());
    }
}
