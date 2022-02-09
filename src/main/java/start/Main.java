package start;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import com.mysql.cj.exceptions.CJOperationNotSupportedException;
import model.Client;
import model.Orders;
import model.Product;
import presentation.Controller;
import presentation.View;

public class Main {
    public static void main(String[] args)
    {
//        Orders o = new Orders(1, 1, 1, 1, 3.5);
//        OrderBLL bll = new OrderBLL();
//        bll.insert(o);
//        Product pr = new Product(10, "Masinute", 10, 100);
//        ProductBLL bll = new ProductBLL();
//        bll.update(pr);
//        OrderBLL bll = new OrderBLL();
//        bll.deleteById(1);
//        Client c = new Client(3, "Pop Razvan10", "Horia 21", "pop_razvan32@yahoo.com");
//        ClientBLL bll = new ClientBLL();
//        bll.insert(c);
//        System.out.println(bll.findById(1).toString());
        //List<Client> c = cDAO.findAll();
//        for(Client client : c)
//        {
//            System.out.println(client.toString());
//        }
        //cDAO.deleteById(1);
        View view = new View();
        Controller controller = new Controller(view);
        view.setVisible(true);
    }
}
