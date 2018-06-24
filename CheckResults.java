import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;

public class CheckResults {

    private static Connection connect(){
        Connection conn = null;
        try{
            String url = "jdbc:sqlite:progress.sqlite3";
            conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite database established.");
        } catch (SQLException e){
            e.printStackTrace();
        }
        return conn;
    }

    public static void check(){
        VBox layout = new VBox();
        Label header = new Label("Day\t\tRound1\t\tRound2\t\tRound3");
        layout.getChildren().add(header);

        try(Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT day, round1, round2, round3 " +
                    "FROM progress")){
            while(rs.next()){
                layout.getChildren().add(new Label((rs.getInt("day")) + "\t|\t"
                        + rs.getString("round1") + "\t\t\t"
                        + rs.getString("round2") + "\t\t\t"
                        + rs.getString("round3")));
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

        Stage stg = new Stage();
        stg.setTitle("Results");
        Scene scene = new Scene(layout, 300, 600);
        stg.setScene(scene);
        stg.initModality(Modality.APPLICATION_MODAL);
        stg.show();


    }
}
