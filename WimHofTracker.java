import javafx.application.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import java.sql.*;


public class WimHofTracker extends Application{
	
	public static Stage window;
	public static Scene scene;
	private Session session;

    public static void createNewDatabase(String fileName){
		String url = "jdbc:sqlite:" + fileName;
		try (Connection conn = DriverManager.getConnection(url)){
			if(conn != null){
				DatabaseMetaData meta = conn.getMetaData();
				System.out.println("Driver name: " + meta.getDriverName());
				System.out.println("New Database created");
			}
		} catch (SQLException e){
			e.printStackTrace();
		}

		String sql = "CREATE TABLE IF NOT EXISTS progress " +
				"(day integer PRIMARY KEY, " +
				"round1 text NOT NULL, " +
				"round2 text NOT NULL, " +
				"round3 text NOT NULL);";

		try (Connection conn = DriverManager.getConnection(url);
			 Statement stmt = conn.createStatement()){
			stmt.execute(sql);

		} catch (SQLException e){
			e.printStackTrace();
		}
	}

	public static void insertData(String[] times){
		String url = "jdbc:sqlite:progress.sqlite3";
		try (Connection conn = DriverManager.getConnection(url)){
			PreparedStatement stmt = conn.prepareStatement("INSERT INTO progress" +
					"(round1,round2,round3) VALUES (?,?,?)");
			stmt.setString(1, times[0]);
			stmt.setString(2, times[1]);
			stmt.setString(3, times[2]);
			stmt.executeUpdate();

		} catch(SQLException e){
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		createNewDatabase("progress.sqlite3");

		window = primaryStage;
		window.setTitle("Wim Hof Progress Tracker");
		
		Button startSession = new Button("Start Session");
		session = new Session();
		startSession.setOnAction(e -> window.setScene(session.setSceneClock()));
		
		Button checkResults = new Button("Check Results");
		checkResults.setOnAction(e -> CheckResults.check());

		Button help = new Button("Help");
		Help helpClass = new Help();
		help.setOnAction(e -> window.setScene(helpClass.helpScene()));
		
		Label welcome = new Label("Welcome to the Wim Hof Activity Tracker");
		
		VBox layout = new VBox();
		layout.getChildren().addAll(welcome, startSession, checkResults, help);
		
		scene = new Scene(layout, 300, 200);
		window.setScene(scene);
		window.show();
	}

    @Override
    public void stop() throws Exception {
        session.stop();
    }
}
