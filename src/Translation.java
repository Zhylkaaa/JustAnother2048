import javafx.animation.AnimationTimer;

import java.util.ArrayList;

public abstract class Translation extends AnimationTimer {
    protected long lastTime = 0;
    protected boolean madeMove = false;

    protected ArrayList<Tile> tiles;
    protected int[] curr_i = new int[4];
    protected int[] curr_j = new int[4];
    protected int[] bound_i = new int[4];
    protected int[] bound_j = new int[4];
    protected int[] i_anchor = new int[4];
    protected int[] j_anchor = new int[4];
    protected int default_anchor_i;
    protected int default_bound_i;
    protected int default_anchor_j;
    protected int default_bound_j;
    protected int next_delta;
    protected int i_delta;
    protected int j_delta;

    protected void reset() {
        for (int i = 0; i < 4; i++) {
            curr_i[i] = i_anchor[i] = default_anchor_i;
            curr_j[i] = j_anchor[i] = default_anchor_j;
            bound_i[i] = default_bound_i;
            bound_j[i] = default_bound_j;
        }
    }

    @Override
    public void start() {
        reset();
        super.start();
    }

    protected boolean toDraw(long now) {
        return now - lastTime >= MainWindow.timediff;
    }

    @Override
    public void stop() {
        super.stop();
        if (madeMove) {
            MainWindow.place_square();
            madeMove = false;
        }
    }
}
