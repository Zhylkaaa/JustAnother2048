import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.util.ArrayList;

public class TransitionY extends Transition {

    //TODO: simultaneously change columns

    private ArrayList<Tile> tiles;
    private int i_delta;
    private int j_delta;
    private int next_delta;
    private int next_j_bound;
    private int next_bound;

    public TransitionY(int i, int j, int i_delta, int j_delta, int next_delta, int next_j_bound, ArrayList<Tile> tiles){
        super(i, j);
        this.i_delta = i_delta;
        this.j_delta = j_delta;
        this.next_delta = next_delta;
        this.tiles = tiles;
        this.next_j_bound = next_j_bound;
        this.next_bound = next_j_bound;

        if(i_delta != 1 && i_delta != -1)throw new ValueException("i_delta should take only value 1 or -1");
        if(j_delta != 1 && j_delta != -1)throw new ValueException("j_delta should take only value 1 or -1");
        if(next_delta != 1 && next_delta != -1)throw new ValueException("next_delta should take only value 1 or -1");
    }

    @Override
    public void handle(long now) {


        if(curr_j == next_j_bound){
            j+=j_delta;
            curr_j = j;
        }

        if(j > 3 || j < 0){
            j = j_start;
            curr_j = j;
            next_j_bound = next_bound;
            i+=i_delta;
            curr_i = i;
        }

        if(i > 3){
            MainWindow.is_runing = false;
            stop();
            return;
        }

        if(tiles.get(curr_i + curr_j*4).getNumber() == 0){
            j+=j_delta;
            curr_j = j;
            return;
        }

        if(tiles.get(curr_i + (curr_j+next_delta)*4).getNumber() == 0){
            tiles.get(curr_i + (curr_j+next_delta)*4).setNumber(tiles.get(curr_i + curr_j*4).getNumber());
            tiles.get(curr_i + curr_j*4).setNumber(0);
            curr_j += next_delta;
            made_move = true;
            return;
        }

        if(tiles.get(curr_i + (curr_j+next_delta)*4).getNumber() == tiles.get(curr_i + curr_j*4).getNumber()){
            tiles.get(curr_i + (curr_j+next_delta)*4).setNumber(tiles.get(curr_i + curr_j*4).getNumber() * 2);
            tiles.get(curr_i + curr_j*4).setNumber(0);
            next_j_bound += j_delta;
            j+=j_delta;
            curr_j = j;
            made_move = true;
            return;
        }

        if(tiles.get(curr_i + (curr_j+next_delta)*4).getNumber() != tiles.get(curr_i + curr_j*4).getNumber()){
            j+=j_delta;
            curr_j = j;
        }
    }
}
