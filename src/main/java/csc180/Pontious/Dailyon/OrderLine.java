/**
 * @author tsoutherland
 * @createdOn 10/16/2024 at 8:35 PM
 * @projectName XMLAdventure
 * @packageName csc180.Pontious.Dailyon;
 */
package csc180.Pontious.Dailyon;

public class OrderLine {
    int OrderLinesID, OrderID, Qty, ProductID;
    double Price, LineTotal;


    public OrderLine(int orderLinesID, int orderID, int qty, int productID, double price, double lineTotal) {
        OrderLinesID = orderLinesID;
        OrderID = orderID;
        Qty = qty;
        ProductID = productID;
        Price = price;
        LineTotal = lineTotal;
    }

    public int getOrderLinesID() {
        return OrderLinesID;
    }

    public void setOrderLinesID(int orderLinesID) {
        OrderLinesID = orderLinesID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public double getLineTotal() {
        return LineTotal;
    }

    public void setLineTotal(double lineTotal) {
        LineTotal = lineTotal;
    }
}
