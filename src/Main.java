import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("edu/icet/view/dash-board.fxml"));
        Scene scene = new Scene(root);
        String css = this.getClass().getResource("edu/icet/view/styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Thoga Kade");
        primaryStage.show();

//        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("edu/icet/view/customer-form.fxml"))));
//        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("edu/icet/view/item-form.fxml"))));
//        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("edu/icet/view/dash-board.fxml"))));
//        primaryStage.show();
    }
}


