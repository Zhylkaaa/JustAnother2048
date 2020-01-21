import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Score extends Text {
    public int score;

    public Score(){
        setScore(0);
        setFill(Color.ORANGE);
        setFont(Font.font(null, FontWeight.BOLD, 48));
        setTranslateX(1.5 * Tile.TILE_X_SIZE);
    }

    public void setScore(int score) {
        this.score = score;
        setText("Score : " + this.score);
    }

    public void addToScore(int score){
        this.score += score;
        setText("Score : " + this.score);
    }
}
