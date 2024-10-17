package csc180.Pontious.Dailyon;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        try{
            String url = "jdbc:mysql://localhost:3306/xmladventure"; // database link
            String user = "root";
            String pass = "test";
            Connection connection = DriverManager.getConnection(url,user,pass);

            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();

            MyHandler handler = new MyHandler(connection);

            parser.parse("C:\\Users\\tsoutherland\\Downloads\\customers\\customers.xml", handler);

            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}