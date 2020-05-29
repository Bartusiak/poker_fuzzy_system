import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AppMain extends Application {


    @Override
    public void start(Stage mainStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("view/player_type_view2.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("view/css/stylesheet.css").toExternalForm());


        mainStage.setTitle("CheckPlayerType");
        mainStage.setScene(scene);
        mainStage.show();

    }


    public static void main(String[] args){
        launch();
    }
}
