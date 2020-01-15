import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.ArrayList;

// right 2, 0, -1, 1, 1
public class TransitionX extends Transition {

    //TODO: simultaneously change rows

    private ArrayList<Tile> tiles;
    private int i_delta;
    private int j_delta;
    private int next_delta;
    private int next_i_bound;
    private int next_bound;

    public TransitionX(int i, int j, int i_delta, int j_delta, int next_delta, int next_i_bound, ArrayList<Tile> tiles){
        super(i, j);
        this.i_delta = i_delta;
        this.j_delta = j_delta;
        this.next_delta = next_delta;
        this.tiles = tiles;
        this.next_i_bound = next_i_bound;
        this.next_bound = next_i_bound;

        if(i_delta != 1 && i_delta != -1)throw new ValueException("i_delta should take only value 1 or -1");
        if(j_delta != 1 && j_delta != -1)throw new ValueException("j_delta should take only value 1 or -1");
        if(next_delta != 1 && next_delta != -1)throw new ValueException("next_delta should take only value 1 or -1");
    }

    @Override
    public void handle(long now) {
        if(toDraw(now)){
            //System.out.println("("+i+", "+j+")");
            if(curr_i == next_i_bound){
                i += i_delta;
                curr_i = i;
            }

            if(i < 0 || i > 3){
                i = i_start;
                curr_i = i;
                next_i_bound = next_bound;
                j += j_delta;
                curr_j = j;
            }

            if(j > 3){
                MainWindow.is_runing = false;
                stop();
                return;
            }

            if(tiles.get(curr_i + curr_j*4).getNumber() == 0){
                i += i_delta;
                curr_i = i;
                return;
            }

            if(tiles.get(curr_i + next_delta + curr_j*4).getNumber() == 0){
                tiles.get(curr_i + next_delta + curr_j*4).setNumber(tiles.get(curr_i + curr_j*4).getNumber());
                tiles.get(curr_i + curr_j*4).setNumber(0);
                curr_i += next_delta;
                made_move = true;
                return;
            }

            if(tiles.get(curr_i + next_delta + curr_j*4).getNumber() == tiles.get(curr_i + curr_j*4).getNumber()){
                tiles.get(curr_i + next_delta + curr_j*4).setNumber(tiles.get(curr_i + curr_j*4).getNumber() * 2);
                tiles.get(curr_i + curr_j*4).setNumber(0);
                next_i_bound += i_delta;
                i += i_delta;
                curr_i = i;
                made_move = true;
                return;
            }

            if(tiles.get(curr_i + next_delta + curr_j*4).getNumber() != tiles.get(curr_i + curr_j*4).getNumber()){
                i += i_delta;
                curr_i = i;
            }
        }
    }
}
