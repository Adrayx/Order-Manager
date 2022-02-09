package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import com.itextpdf.text.DocumentException;
import model.Client;
import model.Product;
import model.Orders;
import model.Receipt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;

public class Controller {
    private final View m_view;
    private final ClientBLL clientBll = new ClientBLL();
    private final ProductBLL productBll = new ProductBLL();
    private final OrderBLL orderBll = new OrderBLL();

    public Controller(View view)
    {
        m_view = view;

        view.addOpenClientsListener(new OpenClientsListener());
        view.addOpenProductsListener(new OpenProductsListener());
        view.addOpenOrdersListener(new OpenOrdersListener());
        view.addInsertClientsListener(new InsertClientListener());
        view.addInsertProductsListener(new InsertProductListener());
        view.addInsertOrdersListener(new InsertOrderListener());
        view.addUpdateClientsListener(new UpdateClientListener());
        view.addUpdateProductsListener(new UpdateProductListener());
        view.addUpdateOrdersListener(new UpdateOrderListener());
        view.addDeleteClientsListener(new DeleteClientListener());
        view.addDeleteProductsListener(new DeleteProductListener());
        view.addDeleteOrdersListener(new DeleteOrderListener());
        view.addViewClientsListener(new ViewClientListener());
        view.addViewOrderListener(new ViewOrderListener());
        view.addViewProductListener(new ViewProductListener());
    }

    private class ViewClientListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e)
        {
            m_view.viewClients();
        }
    }

    private class ViewOrderListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e)
        {
            m_view.viewOrders();
        }
    }

    private class ViewProductListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e)
        {
            m_view.viewProducts();
        }
    }

    private class InsertClientListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(m_view.getIdClient());
                Client cl = new Client(id, m_view.getNameClient(), m_view.getEmailClient(), m_view.getAddressClient());
                System.out.println(cl);
                clientBll.insert(cl);
            }
            catch(IllegalArgumentException err)
            {
                m_view.showError(err.getMessage());
            }
        }
    }

    private class InsertProductListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(m_view.getIdProduct());
                int quantity = Integer.parseInt(m_view.getQuantityProduct());
                double price = Double.parseDouble(m_view.getPriceProduct());
                Product pr = new Product(id, m_view.getNameProduct(), quantity, price);
                productBll.insert(pr);
            } catch(IllegalArgumentException err) {
                m_view.showError(err.getMessage());
            }
        }
    }

    private class InsertOrderListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(m_view.getIdOrder());
                int quantity = Integer.parseInt(m_view.getQuantityOrder());
                String clientName = m_view.getClientNameOrder();
                String productName = m_view.getProductNameOrder();
                Client cl = clientBll.findByName(clientName);
                Product pr = productBll.findByName(productName);
                double price = pr.getPrice() * quantity;

                Orders or = new Orders(id, cl.getId(), pr.getId(), quantity, price);
                orderBll.insert(or);
                pr.setQuantity(pr.getQuantity() - quantity);
                productBll.update(pr);
                Receipt.pdf(or, cl, pr);
            } catch(IllegalArgumentException | NoSuchElementException | DocumentException | FileNotFoundException err){
                m_view.showError(err.getMessage());
            }
        }
    }

    private class UpdateClientListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(m_view.getIdClient());
                Client cl = new Client(id, m_view.getNameClient(), m_view.getEmailClient(), m_view.getAddressClient());
                System.out.println(cl);
                clientBll.update(cl);
            }
            catch(IllegalArgumentException | NoSuchElementException err)
            {
                m_view.showError(err.getMessage());
            }
        }
    }

    private class UpdateProductListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int id = Integer.parseInt(m_view.getIdProduct());
                int quantity = Integer.parseInt(m_view.getQuantityProduct());
                double price = Double.parseDouble(m_view.getPriceProduct());
                Product pr = new Product(id, m_view.getNameProduct(), quantity, price);
                productBll.update(pr);
            } catch(IllegalArgumentException | NoSuchElementException err) {
                m_view.showError(err.getMessage());
            }
        }
    }

    private class UpdateOrderListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                int id = Integer.parseInt(m_view.getIdOrder());
                int quantity = Integer.parseInt(m_view.getQuantityOrder());
                String clientName = m_view.getClientNameOrder();
                String productName = m_view.getProductNameOrder();
                Client cl = clientBll.findByName(clientName);
                Product pr = productBll.findByName(productName);
                Orders or = orderBll.findById(id);
                pr.setQuantity(pr.getQuantity() + or.getQuantity());
                or.setQuantity(quantity);
                or.setTotalPrice(quantity * pr.getPrice());
                productBll.update(pr);
                orderBll.update(or);
                pr.setQuantity(pr.getQuantity() - or.getQuantity());
                productBll.update(pr);
                Receipt.pdf(or, cl, pr);
            } catch(IllegalArgumentException | NoSuchElementException | DocumentException | FileNotFoundException err){
                m_view.showError(err.getMessage());
            }
        }
    }

    private class DeleteClientListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(m_view.getIdClient());
            clientBll.delete(id);
        }
    }

    private class DeleteProductListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            int id = Integer.parseInt(m_view.getIdProduct());
            productBll.delete(id);
        }
    }

    private class DeleteOrderListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                int id = Integer.parseInt(m_view.getIdOrder());
                Orders or = orderBll.findById(id);
                Product pr = productBll.findById(or.getProduct_id());
                pr.setQuantity(pr.getQuantity() + or.getQuantity());
                orderBll.deleteById(id);
                productBll.update(pr);
            } catch(IllegalArgumentException | NoSuchElementException err) {
                m_view.showError(err.getMessage());
            }
        }
    }

    private class OpenClientsListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            m_view.openClients();
        }
    }

    private class OpenProductsListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            m_view.openProducts();
        }
    }

    private class OpenOrdersListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            m_view.openOrders();
        }
    }
}
