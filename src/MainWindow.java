import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.Random;

public class MainWindow extends Application {

    private BorderPane root = new BorderPane();

    private static Random sampler = new Random();

    public static Score score = new Score();

    public static boolean is_runing = false;
    public static final long timediff = 50000000L;

    private static ArrayList<Tile> tiles;

    private TranslationX transition_right;
    private TranslationX transition_left;
    private TranslationY transition_down;
    private TranslationY transition_up;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));

        primaryStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (is_runing) return;

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
                boolean isTerminal = checkTerminalState();
                if (isTerminal) {
                    handleTerminalState();
                }
            }
        });

        primaryStage.show();
    }

    private void handleTerminalState() {
        is_runing = true;
        Button reset = new Button("Reset");
        reset.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                createContent();
            }
        });

        reset.setTranslateX(2*Tile.TILE_X_SIZE);
        reset.setTranslateY(2*Tile.TILE_Y_SIZE);

        ((Pane)root.getCenter()).getChildren().add(reset);
    }

    private boolean checkMove(int i, int j, int ii, int jj) {
        return tiles.get(ii + jj * 4).getNumber() == 0 || tiles.get(ii + jj * 4).getNumber() == tiles.get(i + j * 4).getNumber();
    }

    private boolean checkTerminalState() {
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                if (i > 0 && checkMove(i, j, i - 1, j)) return false;
                if (j > 0 && checkMove(i, j, i, j - 1)) return false;
                if (i < 3 && checkMove(i, j, i + 1, j)) return false;
                if (j < 3 && checkMove(i, j, i, j + 1)) return false;
            }
        }

        return true;
    }

    public static void place_square() {
        ArrayList<Tile> emptyTiles = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tiles.get(i + j * 4).isEmpty()) emptyTiles.add(tiles.get(i + j * 4));
            }
        }

        if (emptyTiles.size() == 0) return;

        int index = sampler.nextInt(emptyTiles.size());
        emptyTiles.get(index).setNumber(2);
    }

    private Parent createContent() {
        root.setPrefSize(4 * Tile.TILE_X_SIZE + 4 * Tile.STROKE_WIDTH, 4 * Tile.TILE_Y_SIZE + 4 * Tile.STROKE_WIDTH + 68);

        root.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));

        tiles = new ArrayList<>();

        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < 4; i++) {
                tiles.add(new Tile(i, j, 0, Color.GRAY));
            }
        }

        transition_right = new TranslationX(2, 3, -1, 1, tiles);
        transition_left = new TranslationX(1, 0, 1, -1, tiles);
        transition_down = new TranslationY(2, 3, -1, 1, tiles);
        transition_up = new TranslationY(1, 0, 1, -1, tiles);

        score.setScore(0);

        Pane tilesPane = new Pane();
        tilesPane.getChildren().addAll(tiles);

        root.setCenter(tilesPane);
        root.setTop(score);
        place_square();
        place_square();
        is_runing = false;

        return root;
    }
}
