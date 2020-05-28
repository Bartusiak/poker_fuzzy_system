import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {


    @Override
    public void start(Stage mainStage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("view/scene_player_view/scene_player_type.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("view/scene_player_view/stylesheet.css").toExternalForm());
        mainStage.setTitle("CheckPlayerType");
        mainStage.setScene(scene);
        mainStage.show();
        //mainStage.setFullScreen(true);

    }

    public static void main(String[] args){
        launch();
    }
}
