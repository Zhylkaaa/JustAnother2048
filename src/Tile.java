import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.shape.Rectangle;
import sun.plugin2.util.ColorUtil;

import java.util.HashMap;

public class Tile extends StackPane {

    private static HashMap<Integer, Color> int_to_color = new HashMap<Integer, Color>(){
        {
            put(2, Color.WHITE);
            put(4, Color.LIGHTYELLOW);
            put(8, Color.ORANGE);
            put(16, Color.DARKORANGE);
            put(32, Color.ORANGERED);
            put(64, Color.RED);
            put(128, Color.LIGHTGOLDENRODYELLOW);
            put(256, Color.YELLOW);
            put(512, Color.YELLOW);
            put(1024, Color.YELLOW);
            put(2048, Color.YELLOW);
        }
    };

    public static final int TILE_X_SIZE = 150;
    public static final int TILE_Y_SIZE = 150;
    public static final double STROKE_WIDTH = 5.0;

    private Rectangle border;
    private Text numberText;
    private int number;
    private int x;
    private int y;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
        setText(number);
    }

    public Tile(int x, int y, int number, Color color){
        border = new Rectangle(TILE_X_SIZE, TILE_Y_SIZE);
        border.setFill(color);
        border.setStrokeWidth(STROKE_WIDTH);
        border.setStroke(Color.WHITE);
        numberText = new Text();
        this.x = x;
        this.y = y;

        setTranslateX(x * (TILE_X_SIZE + STROKE_WIDTH));
        setTranslateY(y * (TILE_Y_SIZE + STROKE_WIDTH));

        numberText.setFont(Font.font(null, FontWeight.BOLD, 48));
        numberText.setFill(Color.WHITE);

        setNumber(number);

        getChildren().addAll(border, numberText);
    }

    public void setText(int number){
        if(number == 0){
            numberText.setText("");
            border.setFill(Color.GRAY);
        } else {
            numberText.setText(Integer.toString(number));
            border.setFill(int_to_color.get(number));
            if(number == 2 || number == 4 || number >= 128){
                numberText.setFill(Color.BLACK);
            } else {
                numberText.setFill(Color.WHITE);
            }
        }
    }

    public boolean isEmpty(){
        return number == 0;
    }
}
