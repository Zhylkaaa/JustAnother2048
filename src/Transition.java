import javafx.animation.AnimationTimer;

public abstract class Transition extends AnimationTimer {
    protected long lastTime = 0;
    protected int i;
    protected int j;
    protected int i_start;
    protected int j_start;
    protected int curr_i;
    protected int curr_j;
    protected boolean made_move = false;

    protected void reset(){
        i = i_start;
        j = j_start;
        curr_i = i;
        curr_j = j;
    }

    @Override
    public void start() {
        reset();
        super.start();
    }

    public Transition(int i, int j){
        this.i_start = i;
        this.j_start = j;
    }

    protected boolean toDraw(long now){
        return now - lastTime >= MainWindow.timediff;
    }

    @Override
    public void stop() {
        super.stop();
        if(made_move){
            MainWindow.place_square();
            made_move = false;
        }
    }
}
