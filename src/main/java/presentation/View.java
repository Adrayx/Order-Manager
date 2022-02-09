package presentation;

import bll.ClientBLL;
import bll.OrderBLL;
import bll.ProductBLL;
import model.Orders;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

import static java.awt.Font.BOLD;

/**
 * In această clasă se crează interfața grafică a aplicației
 */

public class View extends JFrame {
    private final JFrame tableClients = new JFrame("Clients");
    private final JFrame tableOrders = new JFrame("Orders");
    private final JFrame tableProducts = new JFrame("Products");

    private final JButton open_tableClients = new JButton("Clients");
    private final JButton open_tableOrders = new JButton("Orders");
    private final JButton open_tableProducts = new JButton("Products");

    private JTable clients;
    private final JTextField idClient = new JTextField(20);
    private final JTextField nameClient = new JTextField(20);
    private final JTextField emailClient = new JTextField(20);
    private final JTextField addressClient = new JTextField(20);
    private final JButton insertClient = new JButton("INSERT");
    private final JButton deleteClient = new JButton("DELETE");
    private final JButton updateClient = new JButton("UPDATE");
    private final JButton viewClient = new JButton("VIEW");

    private JTable orders;
    private final JTextField idOrder = new JTextField(20);
    private final JTextField clientNameOrder = new JTextField(20);
    private final JTextField productNameOrder = new JTextField(20);
    private final JTextField quantityOrder = new JTextField(20);
    private final JButton insertOrder = new JButton("INSERT");
    private final JButton deleteOrder = new JButton("DELETE");
    private final JButton updateOrder = new JButton("UPDATE");
    private final JButton viewOrder = new JButton("VIEW");

    private JTable products;
    private final JTextField idProduct = new JTextField(20);
    private final JTextField nameProduct = new JTextField(20);
    private final JTextField quantityProduct = new JTextField(20);
    private final JTextField priceProduct = new JTextField(20);
    private final JButton insertProduct = new JButton("INSERT");
    private final JButton deleteProduct = new JButton("DELETE");
    private final JButton updateProduct = new JButton("UPDATE");
    private final JButton viewProduct = new JButton("VIEW");

    private final ClientBLL clientBLL = new ClientBLL();
    private final ProductBLL productBLL = new ProductBLL();

    public View()
    {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Order Management");

        tableClients.setDefaultCloseOperation(HIDE_ON_CLOSE);
        tableOrders.setDefaultCloseOperation(HIDE_ON_CLOSE);
        tableProducts.setDefaultCloseOperation(HIDE_ON_CLOSE);

        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.add(open_tableClients);
        content.add(open_tableOrders);
        content.add(open_tableProducts);

        this.setContentPane(content);
        this.pack();

        JPanel idClientContent = new JPanel();
        idClientContent.setLayout(new FlowLayout(FlowLayout.CENTER));
        idClientContent.add(new JLabel("ID:"));
        idClientContent.add(idClient);

        JPanel nameClientContent = new JPanel();
        nameClientContent.setLayout(new FlowLayout(FlowLayout.CENTER));
        nameClientContent.add(new JLabel("Name: "));
        nameClientContent.add(nameClient);

        JPanel emailClientContent = new JPanel();
        emailClientContent.setLayout(new FlowLayout(FlowLayout.CENTER));
        emailClientContent.add(new JLabel("Email: "));
        emailClientContent.add(emailClient);

        JPanel addressClientContent = new JPanel();
        addressClientContent.setLayout(new FlowLayout(FlowLayout.CENTER));
        addressClientContent.add(new JLabel("Address: "));
        addressClientContent.add(addressClient);

        JLabel titleClients = new JLabel("Clients");
        titleClients.setFont(new Font("Times New Roman", BOLD, 20));

        JPanel contentClient = new JPanel();
        contentClient.setLayout(new BoxLayout(contentClient, BoxLayout.PAGE_AXIS));
        contentClient.add(titleClients, CENTER_ALIGNMENT);
        contentClient.add(idClientContent, CENTER_ALIGNMENT);
        contentClient.add(nameClientContent, CENTER_ALIGNMENT);
        contentClient.add(emailClientContent, CENTER_ALIGNMENT);
        contentClient.add(addressClientContent, CENTER_ALIGNMENT);
        contentClient.add(insertClient, CENTER_ALIGNMENT);
        contentClient.add(updateClient, CENTER_ALIGNMENT);
        contentClient.add(deleteClient, CENTER_ALIGNMENT);
        contentClient.add(viewClient, CENTER_ALIGNMENT);

        JPanel idOrderContent = new JPanel();
        idOrderContent.setLayout(new FlowLayout(FlowLayout.CENTER));
        idOrderContent.add(new JLabel("ID: "));
        idOrderContent.add(idOrder);

        JPanel clientNameContent = new JPanel();
        clientNameContent.setLayout(new FlowLayout(FlowLayout.CENTER));
        clientNameContent.add(new JLabel("Client name: "));
        clientNameContent.add(clientNameOrder);

        JPanel productNameContent = new JPanel();
        productNameContent.setLayout(new FlowLayout(FlowLayout.CENTER));
        productNameContent.add(new JLabel("Product name: "));
        productNameContent.add(productNameOrder);

        JPanel quantityOrderContent = new JPanel();
        quantityOrderContent.setLayout(new FlowLayout(FlowLayout.CENTER));
        quantityOrderContent.add(new JLabel("Quantity: "));
        quantityOrderContent.add(quantityOrder);

        JLabel titleOrders = new JLabel("Orders");
        titleOrders.setFont(new Font("Times New Roman", BOLD, 20));

        JPanel contentOrder = new JPanel();
        contentOrder.setLayout(new BoxLayout(contentOrder, BoxLayout.PAGE_AXIS));
        contentOrder.add(titleOrders, CENTER_ALIGNMENT);
        contentOrder.add(idOrderContent, CENTER_ALIGNMENT);
        contentOrder.add(clientNameContent, CENTER_ALIGNMENT);
        contentOrder.add(productNameContent, CENTER_ALIGNMENT);
        contentOrder.add(quantityOrderContent, CENTER_ALIGNMENT);
        contentOrder.add(insertOrder, CENTER_ALIGNMENT);
        contentOrder.add(updateOrder, CENTER_ALIGNMENT);
        contentOrder.add(deleteOrder, CENTER_ALIGNMENT);
        contentOrder.add(viewOrder, CENTER_ALIGNMENT);

        JPanel idProductContent = new JPanel();
        idProductContent.setLayout(new FlowLayout(FlowLayout.CENTER));
        idProductContent.add(new JLabel("ID: "));
        idProductContent.add(idProduct);

        JPanel nameProductContent = new JPanel();
        nameProductContent.setLayout(new FlowLayout(FlowLayout.CENTER));
        nameProductContent.add(new JLabel("Name: "));
        nameProductContent.add(nameProduct);

        JPanel quantityProductContent = new JPanel();
        quantityProductContent.setLayout(new FlowLayout(FlowLayout.CENTER));
        quantityProductContent.add(new JLabel("Quantity: "));
        quantityProductContent.add(quantityProduct);

        JPanel priceProductContent = new JPanel();
        priceProductContent.setLayout(new FlowLayout(FlowLayout.CENTER));
        priceProductContent.add(new JLabel("Price: "));
        priceProductContent.add(priceProduct);

        JLabel titleProducts = new JLabel("Products");
        titleProducts.setFont(new Font("Times New Roman", BOLD, 20));

        JPanel contentProduct = new JPanel();
        contentProduct.setLayout(new BoxLayout(contentProduct, BoxLayout.PAGE_AXIS));
        contentProduct.add(titleProducts, CENTER_ALIGNMENT);
        contentProduct.add(idProductContent, CENTER_ALIGNMENT);
        contentProduct.add(nameProductContent, CENTER_ALIGNMENT);
        contentProduct.add(quantityProductContent, CENTER_ALIGNMENT);
        contentProduct.add(priceProductContent, CENTER_ALIGNMENT);
        contentProduct.add(insertProduct, CENTER_ALIGNMENT);
        contentProduct.add(updateProduct, CENTER_ALIGNMENT);
        contentProduct.add(deleteProduct, CENTER_ALIGNMENT);
        contentProduct.add(viewProduct, CENTER_ALIGNMENT);

        tableProducts.setContentPane(contentProduct);
        tableProducts.pack();
        tableClients.setContentPane(contentClient);
        tableClients.pack();
        tableOrders.setContentPane(contentOrder);
        tableOrders.pack();
    }

    public String getIdClient()
    {
        return idClient.getText();
    }

    public String getNameClient()
    {
        return nameClient.getText();
    }

    public String getEmailClient()
    {
        return emailClient.getText();
    }

    public String getAddressClient()
    {
        return addressClient.getText();
    }

    public String getIdOrder()
    {
        return idOrder.getText();
    }

    public String getClientNameOrder()
    {
        return clientNameOrder.getText();
    }

    public String getProductNameOrder()
    {
        return productNameOrder.getText();
    }

    public String getQuantityOrder()
    {
        return quantityOrder.getText();
    }

    public String getIdProduct()
    {
        return idProduct.getText();
    }

    public String getNameProduct()
    {
        return nameProduct.getText();
    }

    public String getQuantityProduct()
    {
        return quantityProduct.getText();
    }

    public String getPriceProduct()
    {
        return priceProduct.getText();
    }

    public void addOpenClientsListener(ActionListener ocl)
    {
        open_tableClients.addActionListener(ocl);
    }

    public void addOpenOrdersListener(ActionListener ool)
    {
        open_tableOrders.addActionListener(ool);
    }

    public void addOpenProductsListener(ActionListener opl)
    {
        open_tableProducts.addActionListener(opl);
    }

    public void addInsertClientsListener(ActionListener icl)
    {
        insertClient.addActionListener(icl);
    }

    public void addInsertProductsListener(ActionListener icl)
    {
        insertProduct.addActionListener(icl);
    }

    public void addInsertOrdersListener(ActionListener icl)
    {
        insertOrder.addActionListener(icl);
    }

    public void addUpdateClientsListener(ActionListener icl)
    {
        updateClient.addActionListener(icl);
    }

    public void addUpdateProductsListener(ActionListener icl)
    {
        updateProduct.addActionListener(icl);
    }

    public void addUpdateOrdersListener(ActionListener icl)
    {
        updateOrder.addActionListener(icl);
    }

    public void addDeleteClientsListener(ActionListener icl)
    {
        deleteClient.addActionListener(icl);
    }

    public void addDeleteProductsListener(ActionListener icl)
    {
        deleteProduct.addActionListener(icl);
    }

    public void addDeleteOrdersListener(ActionListener icl)
    {
        deleteOrder.addActionListener(icl);
    }

    public void addViewClientsListener(ActionListener vcl) {
        viewClient.addActionListener(vcl);
    }

    public void addViewOrderListener(ActionListener vol){
        viewOrder.addActionListener(vol);
    }

    public void addViewProductListener(ActionListener vpl) {
        viewProduct.addActionListener(vpl);
    }

    public void openClients()
    {
        tableClients.setVisible(true);
    }

    public void openOrders()
    {
        tableOrders.setVisible(true);
    }

    public void openProducts()
    {
        tableProducts.setVisible(true);
    }

    public void showError(String value)
    {
        JOptionPane.showMessageDialog(this, value);
    }

    public JTable createTableOrders()
    {
        try {
            OrderBLL bll = new OrderBLL();
            ClientBLL clientBLL = new ClientBLL();
            ProductBLL productBLL = new ProductBLL();
            List<Orders> orders = bll.findAll();

            Object[] columns = new Object[]{"ID", "Client Name", "Product Name", "Quantity", "Total Price"};

            Object[][] rows = new Object[orders.size()][5];
            for(int i = 0; i < orders.size(); i++)
            {
                Orders or = orders.get(i);
                String clientName = clientBLL.findById(or.getClient_id()).getName();
                String productName = productBLL.findById(or.getProduct_id()).getName();
                rows[i] = new Object[]{or.getId(), clientName, productName, or.getQuantity(), or.getTotalPrice()};

            }
            return new JTable(rows, columns);
        } catch(Exception e)
        {
            showError(e.getMessage());
        }
        return null;
    }


    public void viewClients()
    {
        try {
            JFrame frame = new JFrame("Clients");
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            ClientBLL bll = new ClientBLL();
            clients = bll.createTable();
            JScrollPane clientsSP = new JScrollPane(clients);
            JPanel content = new JPanel();
            content.setLayout(new FlowLayout());
            content.add(clientsSP);
            frame.setContentPane(content);
            frame.pack();
            frame.setVisible(true);
        } catch(IllegalAccessException iae)
        {
            System.out.println(iae.getMessage());
        }
    }

    public void viewOrders()
    {
        JFrame frame = new JFrame("Orders");
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        orders = createTableOrders();
        JScrollPane ordersSP = new JScrollPane(orders);
        JPanel content = new JPanel();
        content.setLayout(new FlowLayout());
        content.add(ordersSP);
        frame.setContentPane(content);
        frame.pack();
        frame.setVisible(true);
    }

    public void viewProducts()
    {
        try {
            JFrame frame = new JFrame("Products");
            frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            ProductBLL bll = new ProductBLL();
            products = bll.createTable();
            JScrollPane productsSP = new JScrollPane(products);
            JPanel content = new JPanel();
            content.setLayout(new FlowLayout());
            content.add(productsSP);
            frame.setContentPane(content);
            frame.pack();
            frame.setVisible(true);
        } catch(IllegalAccessException iae)
        {
            System.out.println(iae.getMessage());
        }
    }

}
