import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class MainWindow extends Application {

    // TODO: check terminal state

    private BorderPane root = new BorderPane();

    private static Random sampler = new Random();

    private boolean move_left = false;
    private boolean move_right = false;
    private boolean move_down = false;
    private boolean move_up = false;

    public static boolean is_runing = false;
    public static final long timediff = 300000000L;

    private static ArrayList<Tile> tiles = new ArrayList<>();

    private TransitionX transition_right = new TransitionX(2, 0, -1, 1, 1, 3, tiles);
    private TransitionX transition_left = new TransitionX(1, 0, 1, 1, -1, 0, tiles);
    private TransitionY transition_up = new TransitionY(0, 1, 1, 1, -1, 0, tiles);
    private TransitionY transition_down = new TransitionY(0, 2, 1, -1, 1, 3, tiles);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));

        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(is_runing)return;

                switch (event.getCode()) {
                    case LEFT:
                        is_runing = true;
                        transition_left.start();
                        break;
                    case RIGHT:
                        is_runing = true;
                        transition_right.start();
                        break;
                    case DOWN:
                        is_runing = true;
                        transition_down.start();
                        break;
                    case UP:
                        is_runing = true;
                        transition_up.start();
                        break;
                }
            }
        });

        primaryStage.show();
    }

    public static void place_square(){
        ArrayList<Tile> emptyTiles = new ArrayList<>();
        for(int i = 0;i<4;i++){
            for(int j = 0; j<4;j++){
                if(tiles.get(i + j*4).isEmpty())emptyTiles.add(tiles.get(i + j*4));
            }
        }

        if(emptyTiles.size() == 0)return;

        int index = sampler.nextInt(emptyTiles.size());
        emptyTiles.get(index).setNumber(2);
    }

    private Parent createContent() {
        root.setPrefSize(4 * Tile.TILE_X_SIZE + 4 * Tile.STROKE_WIDTH, 4 * Tile.TILE_Y_SIZE + 4 * Tile.STROKE_WIDTH);

        root.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                tiles.add(new Tile(i, j, 0, Color.GRAY));
            }
        }

        Pane tilesPane = new Pane();
        tilesPane.getChildren().addAll(tiles);

        root.setCenter(tilesPane);

        place_square();
        place_square();

        return root;
    }
}
