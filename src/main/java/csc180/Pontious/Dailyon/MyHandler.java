package csc180.Pontious.Dailyon;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Stack;

public class MyHandler extends DefaultHandler {
    private String currentElement;
    private String age, customerId, email, name;
    private String orderId, orderTotal;
    private String orderLineId, orderLinePrice, orderLineProductId, orderLineQty, orderLineTotal;
    private Connection connection;

    public MyHandler(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        currentElement = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String content = new String(ch, start, length).trim();
        switch (currentElement) {
            case "Age":
                age = content;
                break;
            case "CustomerId":
                customerId = content;
                break;
            case "Email":
                email = content;
                break;
            case "Name":
                name = content;
                break;
            case "OrderId":
                orderId = content;
                break;
            case "Total":
                if (orderId != null){
                    orderTotal = content;
                }else{
                    orderLineTotal = content;
                }
                break;
            case "OrderLineId":
                orderLineId = content;
                break;
            case "Price":
                orderLinePrice = content;
                break;
            case "ProductId":
                orderLineProductId = content;
                break;
            case "Qty":
                orderLineQty = content;
                break;


        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch(qName){
            case "Customer":
                insertCustomerIntoDatabase(customerId,name,email,age);
                break;
            case "Order":
                insertOrderIntoDatabase(orderId,orderLineId,orderLineTotal);
                orderTotal = null;
                orderLineTotal = null;
                break;
            case "OrderLine":
                insertOrderLineIntoDatabase(orderId,orderLineId,orderLinePrice,orderLineProductId,orderLineQty,orderLineTotal);
                orderLineId = null;
                orderLinePrice = null;
                orderLineProductId = null;
                orderLineQty = null;
                orderLineTotal = null;
                break;
        }
    }

    private void insertCustomerIntoDatabase(String customerId, String name, String email, String age) {
        String sql = "INSERT INTO customers (customerId, name, email, age) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, customerId);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setString(4, age);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertOrderIntoDatabase(String customerId, String orderId, String orderTotal) {
        String sql = "INSERT INTO orders (customerId, orderId, total) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, customerId);
            pstmt.setString(2, orderId);
            pstmt.setString(3, orderTotal);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void insertOrderLineIntoDatabase(String orderId, String orderLineId, String orderLinePrice, String orderLineProductId, String orderLineQty, String orderLineTotal) {
        String sql = "INSERT INTO orderlines (orderId, orderLineId, price, productId, qty, total) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, orderId);
            pstmt.setString(2, orderLineId);
            pstmt.setString(3, orderLinePrice);
            pstmt.setString(4, orderLineProductId);
            pstmt.setString(5, orderLineQty);
            pstmt.setString(6, orderLineTotal);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
