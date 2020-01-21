import java.util.ArrayList;

public class TranslationY extends Translation {

    public TranslationY(int default_anchor_j, int default_bound_j, int j_delta, int next_delta, ArrayList<Tile> tiles) {
        this.default_anchor_j = default_anchor_j;
        this.default_bound_j = default_bound_j;
        this.j_delta = j_delta;
        this.next_delta = next_delta;
        this.tiles = tiles;

        reset();
    }

    @Override
    public void handle(long now) {
        if (toDraw(now)) {
            lastTime = now;

            for (int i = 0; i < 4; i++) {

                if (curr_j[i] == bound_j[i]) {
                    j_anchor[i] += j_delta;
                    curr_j[i] = j_anchor[i];
                }

                while (curr_j[i] <= 3 && curr_j[i] >= 0 && tiles.get(i + curr_j[i] * 4).getNumber() == 0) {
                    j_anchor[i] += j_delta;
                    curr_j[i] = j_anchor[i];
                }

                // TODO: ^ or if and continue (?)

                if (curr_j[i] > 3 || curr_j[i] < 0) continue;

                if (tiles.get(i + (next_delta + curr_j[i]) * 4).getNumber() == 0) {
                    tiles.get(i + (next_delta + curr_j[i]) * 4).setNumber(tiles.get(i + curr_j[i] * 4).getNumber());
                    tiles.get(i + curr_j[i] * 4).setNumber(0);
                    curr_j[i] += next_delta;
                    madeMove = true;
                    continue;
                }

                if (tiles.get(i + (next_delta + curr_j[i]) * 4).getNumber() == tiles.get(i + curr_j[i] * 4).getNumber()) {
                    tiles.get(i + (next_delta + curr_j[i]) * 4).setNumber(tiles.get(i + curr_j[i] * 4).getNumber() * 2);
                    MainWindow.score.addToScore(tiles.get(i + (next_delta + curr_j[i]) * 4).getNumber());
                    tiles.get(i + curr_j[i] * 4).setNumber(0);
                    bound_j[i] += j_delta;
                    j_anchor[i] += j_delta;
                    curr_j[i] = j_anchor[i];
                    madeMove = true;
                    continue;
                }

                if (tiles.get(i + (next_delta + curr_j[i]) * 4).getNumber() != tiles.get(i + curr_j[i] * 4).getNumber()) {
                    j_anchor[i] += j_delta;
                    curr_j[i] = j_anchor[i];
                }
            }

            for (int i = 0; i < 4; i++) {
                if (curr_j[i] <= 3 && curr_j[i] >= 0) return;
            }

            MainWindow.is_runing = false;
            stop();
        }
    }
}
