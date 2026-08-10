import java.io.File;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    private static final String WINDOW_TITLE =
            Constants.APP_TITLE;

    private static final double WINDOW_WIDTH =
            1000;

    private static final double WINDOW_HEIGHT =
            650;

    @Override
    public void start(Stage primaryStage) {

        try {

            File fxmlFile =
                new File("MainView.fxml");

            URL fxmlLocation =
                fxmlFile.toURI().toURL();

            FXMLLoader loader =
                new FXMLLoader(fxmlLocation);

            Parent root = loader.load();

            Scene scene =
                    new Scene(
                            root,
                            WINDOW_WIDTH,
                            WINDOW_HEIGHT
                    );

            /*
             * Load the CSS file if it exists.
             */
            if (getClass().getResource(
                    "style.css") != null) {

                scene.getStylesheets().add(
                        getClass()
                                .getResource(
                                        "style.css"
                                )
                                .toExternalForm()
                );
            }

            primaryStage.setTitle(
                    WINDOW_TITLE
            );

            primaryStage.setScene(scene);

            primaryStage.setMinWidth(850);
            primaryStage.setMinHeight(550);

            primaryStage.setOnCloseRequest(
                    event -> {

                        boolean saved =
                                ApplicationData
                                        .getInstance()
                                        .saveAllData();

                        if (!saved) {

                            System.out.println(
                                    "Some data could not "
                                            + "be saved."
                            );
                        }
                    }
            );

            primaryStage.show();

        } catch (Exception exception) {

            System.out.println(
                    "StudentSphere could not start."
            );

            System.out.println(
                    "Reason: "
                            + exception.getMessage()
            );

            exception.printStackTrace();
        }
    }

    public static void main(String[] args) {

        launch(args);
    }
}