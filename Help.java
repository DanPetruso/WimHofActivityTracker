import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class Help {

    Button back;

    public Scene helpScene(){
        VBox layout = new VBox();
        back = new Button("Back");
        back.setOnAction(e -> WimHofTracker.window.setScene(WimHofTracker.scene));

        Label description = new Label("To start a session, the user must start with taking 30-40\n" +
                "deep breathes all the way in and out.  After that, the \n" +
                "user will exhale all air out of the lungs and see how long\n" +
                "he or she can retain that with the built in clock.\n" +
                "After doing this 3 times, do this another fourth time \n" +
                "except instead of retaining breathe, hold your breathe \n" +
                "and see how many push ups you can do in a row.");

        layout.getChildren().addAll(description, back);
        Scene helpScene = new Scene(layout, 300, 200);
        return helpScene;
    }
}
