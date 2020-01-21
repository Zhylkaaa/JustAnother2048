import java.util.ArrayList;

public class TranslationX extends Translation {

    public TranslationX(int default_anchor_i, int default_bound_i, int i_delta, int next_delta, ArrayList<Tile> tiles){
        this.default_anchor_i = default_anchor_i;
        this.default_bound_i = default_bound_i;
        this.i_delta = i_delta;
        this.next_delta = next_delta;
        this.tiles = tiles;

        reset();
    }

    @Override
    public void handle(long now) {
        if (toDraw(now)) {
            lastTime = now;

            for (int j = 0; j < 4; j++) {

                if (curr_i[j] == bound_i[j]) {
                    i_anchor[j] += i_delta;
                    curr_i[j] = i_anchor[j];
                }

                while (curr_i[j] <= 3 && curr_i[j] >= 0 && tiles.get(curr_i[j] + j * 4).getNumber() == 0) {
                    i_anchor[j] += i_delta;
                    curr_i[j] = i_anchor[j];
                }

                // TODO: ^ or if and continue (?)

                if (curr_i[j] > 3 || curr_i[j] < 0) continue;

                if (tiles.get(curr_i[j] + next_delta + j * 4).getNumber() == 0) {
                    tiles.get(curr_i[j] + next_delta + j * 4).setNumber(tiles.get(curr_i[j] + j * 4).getNumber());
                    tiles.get(curr_i[j] + j * 4).setNumber(0);
                    curr_i[j] += next_delta;
                    madeMove = true;
                    continue;
                }

                if (tiles.get(curr_i[j] + next_delta + j * 4).getNumber() == tiles.get(curr_i[j] + j * 4).getNumber()) {
                    tiles.get(curr_i[j] + next_delta + j * 4).setNumber(tiles.get(curr_i[j] + j * 4).getNumber() * 2);
                    MainWindow.score.addToScore(tiles.get(curr_i[j] + next_delta + j * 4).getNumber());
                    tiles.get(curr_i[j] + j * 4).setNumber(0);
                    bound_i[j] += i_delta;
                    i_anchor[j] += i_delta;
                    curr_i[j] = i_anchor[j];
                    madeMove = true;
                    continue;
                }

                if (tiles.get(curr_i[j] + next_delta + j * 4).getNumber() != tiles.get(curr_i[j] + j * 4).getNumber()) {
                    i_anchor[j] += i_delta;
                    curr_i[j] = i_anchor[j];
                }
            }

            for (int j = 0; j < 4; j++) {
                if (curr_i[j] <= 3 && curr_i[j] >= 0) return;
            }

            MainWindow.is_runing = false;
            stop();
        }
    }
}
