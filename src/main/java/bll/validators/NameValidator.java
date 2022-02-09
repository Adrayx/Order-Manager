package bll.validators;

import model.Client;

/**
 * În această clasă verific daca utilizatorul a introdus un nume valid,
 * iar în caz că acesta a introdus și numere in această secțiune, va primi
 * un mesaj de eroare
 */

public class NameValidator implements Validator<Client> {
    /**
     * Aici se realizează verificarea numelui
     * @param client
     */
    @Override
    public void validate(Client client) {
        char[] name = client.getName().toCharArray();
        for(char c: name){
            if(Character.isDigit(c))
                throw new IllegalArgumentException("The name of the client contains numbers!");
        }
    }
}
