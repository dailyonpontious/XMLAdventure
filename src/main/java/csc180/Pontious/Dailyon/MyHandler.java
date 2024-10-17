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
    private String  email, name;
    private int customerId, age, orderId, orderTotal, orderLineId, orderLineQty, orderLineProductId;
    private double orderLinePrice, orderLineTotal;
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
                age = Integer.parseInt(content);
                break;
            case "CustomerId":
                customerId =Integer.parseInt(content);
                break;
            case "Email":
                email = content;
                break;
            case "Name":
                name = content;
                break;
            case "OrderId":
                orderId = Integer.parseInt(content);
                break;
            case "Total":
                if (orderId == -1){
                    orderTotal = Integer.parseInt(content);
                }else{
                    orderLineTotal = Double.parseDouble(content);
                }
                break;
            case "OrderLineId":
                orderLineId = Integer.parseInt(content);
                break;
            case "Price":
                orderLinePrice = Double.parseDouble(content);
                break;
            case "ProductId":
                orderLineProductId = Integer.parseInt(content);
                break;
            case "Qty":
                orderLineQty = Integer.parseInt(content);
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
                orderId = -1;
                orderTotal = Integer.parseInt(null);
                orderLineTotal = Double.parseDouble(null);
                break;
            case "OrderLine":
                insertOrderLineIntoDatabase(orderId,orderLineId,orderLinePrice,orderLineProductId,orderLineQty,orderLineTotal);
                orderId = -1;
                orderLineId = Integer.parseInt(null);
                orderLinePrice = Double.parseDouble(null);
                orderLineProductId = Integer.parseInt(null);
                orderLineQty = Integer.parseInt(null);
                orderLineTotal = Double.parseDouble(null);
                break;
        }
    }

    private void insertCustomerIntoDatabase(int customerId, String name, String email, int age) {
        String sql = "INSERT INTO customer (CustomerId, Name, Email, Age) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setInt(4, age);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void insertOrderIntoDatabase(int customerId, int orderId, double orderTotal) {
        String sql = "INSERT INTO orders (OrderID, CustomerID, Total) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, orderId);
            pstmt.setDouble(3, orderTotal);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void insertOrderLineIntoDatabase(int orderId, int orderLineId, double orderLinePrice, int orderLineProductId, int orderLineQty, double orderLineTotal) {
        String sql = "INSERT INTO orderlines (OrderLinesID, OrderID, Qty, Price, LineTotal, ProductID) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            pstmt.setInt(2, orderLineId);
            pstmt.setDouble(3, orderLinePrice);
            pstmt.setInt(4, orderLineProductId);
            pstmt.setInt(5, orderLineQty);
            pstmt.setDouble(6, orderLineTotal);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
