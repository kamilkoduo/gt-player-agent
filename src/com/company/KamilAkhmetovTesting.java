package com.company;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

public class KamilAkhmetovTesting {

    public static class PlayerImpl implements Player {
        @Override
        public void reset() {
            System.out.println("reset");
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            System.out.printf(
                    "move: | opponent last move: %d | xA: %d | xB: %d | xC: %d \n",
                    opponentLastMove, xA, xB, xC
            );
            return 0;
        }
    }

    /**
     * Random
     */
    public static class PlayerImplRAND extends PlayerImpl {
        @Override
        public void reset() {
            System.out.println("reset" + this.getClass().getSimpleName());
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            Random random = new Random();
            int x = 1 + random.nextInt(3);
            return x;
        }
    }

    /**
     * Random except min
     */
    public static class PlayerImplRANDNOTMIN extends PlayerImpl {
        @Override
        public void reset() {
            System.out.println("reset" + this.getClass().getSimpleName());
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            int[] cells = {xA, xB, xC};
            int min = Arrays.stream(cells).min().getAsInt();
            Random random = new Random();
            int x = random.nextInt(3);
            while (cells[x] == min && !(cells[0] == cells[1] && cells[0] == cells[2])) {
                System.out.println(Arrays.toString(cells));
                System.out.println(min);
                x = random.nextInt(3);
            }
            return 1 + x;
        }
    }

    public static class PlayerImplRANDNOT0 extends PlayerImpl {
        @Override
        public void reset() {
            System.out.println("reset" + this.getClass().getSimpleName());
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            int[] cells = {xA, xB, xC};
            int min = Arrays.stream(cells).min().getAsInt();
            Random random = new Random();
            int x = random.nextInt(3);
            while (cells[x] == 0 && !(cells[0] == cells[1] && cells[0] == cells[2])) {
                System.out.println(Arrays.toString(cells));
                System.out.println(min);
                x = random.nextInt(3);
            }
            return 1 + x;
        }
    }

    /**
     * Cyclic forward
     */
    public static class PlayerImplCF extends PlayerImpl {
        int k;

        PlayerImplCF() {
            reset();
        }

        @Override
        public void reset() {
            System.out.println("reset");
            k = 0;
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            k++;
            return 1 + (k - 1) % 3;
        }
    }

    /**
     * Cyclic backward
     */
    public static class PlayerImplCB extends PlayerImpl {
        int k = 0;

        PlayerImplCB() {
            reset();
        }

        @Override
        public void reset() {
            System.out.println("reset");
            k = 0;
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            k--;
            return 1 + (3 + (k + 1) % 3) % 3;
        }
    }

    /**
     * Random init Cyclic forward
     */
    public static class PlayerImplRANDCF extends PlayerImpl {
        int k;

        PlayerImplRANDCF() {
            reset();
        }

        @Override
        public void reset() {
            System.out.println("reset");
            k = new Random().nextInt(3);
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            k++;
            return 1 + (k - 1) % 3;
        }
    }

    /**
     * Random init Cyclic backward
     */
    public static class PlayerImplRANDCB extends PlayerImpl {
        int k;

        PlayerImplRANDCB() {
            reset();
        }

        @Override
        public void reset() {
            System.out.println("reset");
            k = new Random().nextInt(3);
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            k--;
            return 1 + (3 + (k + 1) % 3) % 3;
        }
    }

    public static class PlayerImplCOPY extends PlayerImpl {
        @Override
        public void reset() {
            System.out.println("reset");
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            if (opponentLastMove == 0) {
                return 1 + new Random().nextInt(3);
            }
            return opponentLastMove;
        }
    }

    public static class PlayerImplCOPYNOT0 extends PlayerImpl {
        int lastMove;

        PlayerImplCOPYNOT0() {
            reset();
        }

        @Override
        public void reset() {
            System.out.println("reset");
            lastMove = 0;
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            if (opponentLastMove == 0) {
                lastMove = 1 + new Random().nextInt(3);
            } else {
                int[] cells = {xA, xB, xC};
                lastMove = opponentLastMove;
                while (cells[lastMove - 1] == 0 && !(cells[0] == cells[1] && cells[0] == cells[2])) {
                    lastMove = 1 + new Random().nextInt(3);
                }
            }
            return lastMove;
        }
    }

    public static class PlayerImplANTICOPYNOTMIN extends PlayerImpl {
        int lastMove;

        PlayerImplANTICOPYNOTMIN() {
            reset();
        }

        @Override
        public void reset() {
            System.out.println("reset");
            lastMove = 0;
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            if (opponentLastMove == 0) {
                lastMove = 1 + new Random().nextInt(3);
            } else {
                int[] cells = {xA, xB, xC};

                int min = Arrays.stream(cells).min().getAsInt();
                lastMove = new Random().nextInt(3);
                if (opponentLastMove == lastMove+1) {
                    lastMove++;
                    lastMove%=3;
                }

                if (cells[lastMove]==min){
                    lastMove++;
                    lastMove%=3;
                }
                lastMove++;
            }
            return lastMove;
        }
    }

    public static class PlayerImplALLD extends PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int lastMove;

        PlayerImplALLD() {
            reset();
        }

        @Override
        public void reset() {
            System.out.println("reset");
            lastMove = 0;
            environment = new KamilAkhmetovCode.Environment();
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            // game starts
            if (opponentLastMove == 0) {
                lastMove = 1 + new Random().nextInt(3);
            } else {
                int[] moves = {lastMove, opponentLastMove};
                environment.play(moves);
                int[] cells = environment.sortedCells();
                System.out.println("sdasdasdsad" + Arrays.toString(cells));
                lastMove = 1 + cells[0];
            }
            return lastMove;
        }
    }

    public static class PlayerImplALLC1 extends PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int last_move;

        PlayerImplALLC1() {
            reset();
        }

        @Override
        public void reset() {
            System.out.println("reset");
            environment = new KamilAkhmetovCode.Environment();
            last_move = 0;
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            // game starts
            if (opponentLastMove == 0) {
                last_move = 1 + new Random().nextInt(3);
            } else {
                int[] moves = {last_move, opponentLastMove};
                environment.play(moves);
                int[] cells = environment.sortedCells();

                last_move = 1 + cells[1];
            }
            return last_move;
        }

        /**
         * ALLC - Always Cooperate
         * Play randomly in the start
         * <p>
         * Take Max if we did not take Max previous round
         * Take other otherwise
         */
    }

    public static class PlayerImplALLMIN extends PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int last_move;

        PlayerImplALLMIN() {
            reset();
        }

        @Override
        public void reset() {
            System.out.println("reset");
            environment = new KamilAkhmetovCode.Environment();
            last_move = 0;
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            // game starts
            if (opponentLastMove == 0) {
                last_move = 1 + new Random().nextInt(3);
            } else {
                int[] moves = {last_move, opponentLastMove};
                environment.play(moves);
                int[] cells = environment.sortedCells();

                last_move = 1 + cells[2];
            }
            return last_move;
        }
    }

    public static class PlayerImplALLC1C2 extends PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int last_move;
        int state;

        PlayerImplALLC1C2() {
            reset();
        }

        @Override
        public void reset() {
            System.out.println("reset");
            environment = new KamilAkhmetovCode.Environment();
            last_move = 0;
            state = 0;
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            // game starts
            if (opponentLastMove == 0) {
                last_move = 1 + new Random().nextInt(3);
            } else {
                int[] moves = {last_move, opponentLastMove};
                environment.play(moves);
                int[] cells = environment.sortedCells();

                if (state == 0) {
                    state = 1;
                    last_move = 1 + cells[1];
                } else {
                    state = 0;
                    last_move = 1 + cells[2];
                }

            }
            return last_move;
        }
    }

    public static class PlayerImplALLC1D0 extends PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int last_move;
        int state;

        PlayerImplALLC1D0() {
            reset();
        }

        @Override
        public void reset() {
            System.out.println("reset");
            environment = new KamilAkhmetovCode.Environment();
            last_move = 0;
            state = 0;
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            // game starts
            if (opponentLastMove == 0) {
                last_move = 1 + new Random().nextInt(3);
            } else {
                int[] moves = {last_move, opponentLastMove};
                environment.play(moves);
                int[] cells = environment.sortedCells();

                if (state == 0) {
                    state = 1;
                    last_move = 1 + cells[1];
                } else {
                    state = 0;
                    last_move = 1 + cells[0];
                }
            }
            return last_move;
        }
    }

    public static class PlayerImplTFT extends PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int last_move;
        int state;
        int cell;
        int last_max_cell;

        PlayerImplTFT() {
            reset();
        }

        @Override
        public void reset() {
            System.out.println("reset");
            environment = new KamilAkhmetovCode.Environment();
            last_move = 0;
            state = 0;
            cell = 1;
            last_max_cell = 0;
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            // game starts
            if (opponentLastMove == 0) {
                last_move = 1 + new Random().nextInt(3);
            } else {
                int[] moves = {last_move, opponentLastMove};
                environment.play(moves);
                int[] cells = environment.sortedCells();

                if (state == 0) {
                    last_move = 1 + cells[1];
                    state = 1;
                } else if (state == 1) {
                    last_move = 1 + cells[0];
                    state = 2;
                } else {
                    if (last_max_cell == opponentLastMove - 1) {
                        last_move = 1 + cells[0];
                    } else {
                        last_move = 1 + cells[1];
                        state = 1;
                    }
                }
                last_max_cell = cells[0];
            }
            return last_move;
        }
    }

    public static class PlayerImplKEEPER extends PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int last_move;
        int state;
        int cell;
        int last_max_cell;
        int offense;

        PlayerImplKEEPER() {
            reset();
        }

        @Override
        public void reset() {
            System.out.println("reset");
            environment = new KamilAkhmetovCode.Environment();
            last_move = 0;
            state = 0;
            cell = 1;
            last_max_cell = 0;
            offense = 0;
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            // game starts
            if (opponentLastMove == 0) {
                last_move = 1 + new Random().nextInt(3);
            } else {
                int[] moves = {last_move, opponentLastMove};
                environment.play(moves);
                int[] cells = environment.sortedCells();

                if (state == 0) {
                    last_move = 1 + cells[1];
                    state = 1;
                } else if (state == 1) {
                    last_move = 1 + cells[0];
                    state = 2;
                } else {
                    if (last_max_cell == opponentLastMove - 1) {
                        offense++;
                    }
                    if (offense > 0) {
                        last_move = 1 + cells[0];
                        offense--;
                    } else {
                        last_move = 1 + cells[1];
                        state = 1;
                    }
                }
                last_max_cell = cells[0];
            }
            return last_move;
        }
    }

    public static class PlayerImplPATKEEPER extends PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int last_move;
        int state;
        int cell;
        int last_max_cell;
        int offense;

        PlayerImplPATKEEPER() {
            reset();
        }

        @Override
        public void reset() {
            System.out.println("reset");
            environment = new KamilAkhmetovCode.Environment();
            last_move = 0;
            state = 0;
            cell = 1;
            last_max_cell = 0;
            offense = 7;
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            // game starts
            if (opponentLastMove == 0) {
                last_move = 1 + new Random().nextInt(3);
            } else {
                int[] moves = {last_move, opponentLastMove};
                environment.play(moves);
                int[] cells = environment.sortedCells();

                if (state == 0) {
                    last_move = 1 + cells[1];
                    state = 1;
                } else if (state == 1) {
                    last_move = 1 + cells[0];
                    state = 2;
                } else {
                    if (last_max_cell == opponentLastMove - 1) {
                        offense++;
                    }
                    if (offense > 0) {
                        last_move = 1 + cells[0];
                        offense--;
                    } else {
                        last_move = 1 + cells[1];
                        state = 1;
                    }
                }
                last_max_cell = cells[0];
            }
            return last_move;
        }
    }

    public static class PlayerImplFORTN extends PlayerImpl {
        KamilAkhmetovCode.Environment environment = new KamilAkhmetovCode.Environment();
        int last_move;
        int N;
        int count;
        int last_max_cell;
        int state;

        PlayerImplFORTN() {
        }

        PlayerImplFORTN(int n) {
            N = n;
            reset();
        }

        @Override
        public void reset() {
            System.out.println("reset");
            environment = new KamilAkhmetovCode.Environment();
            last_move = 0;
            count = N;
            last_max_cell = 0;
            state = 0;
        }

        @Override
        public int move(int opponentLastMove, int xA, int xB, int xC) {
            // game starts
            if (opponentLastMove == 0) {
                last_move = 1 + new Random().nextInt(3);
            } else {
                int[] moves = {last_move, opponentLastMove};
                environment.play(moves);
                int[] cells = environment.sortedCells();

                if (count > 0) {
                    if (last_max_cell != opponentLastMove - 1) {
                        count = N;
                    }
                    last_move = 1 + cells[0];
                    count--;
                } else {
                    if (count != 0 && last_max_cell == opponentLastMove - 1) {
                        state = 0;
                        count = N;
                    } else {
                        if (state == 0) {
                            last_move = 1 + cells[1];
                            state = 1;
                        } else {
                            last_move = 1 + cells[0];
                            state = 0;
                        }
                    }
                }
                last_max_cell = cells[0];
            }
            count--;
            return last_move;
        }
    }

    public static class PlayerImplFORT5 extends PlayerImplFORTN {
        PlayerImplFORT5() {
            super(5);
        }
    }

    public static class PlayerImplFORT9 extends PlayerImplFORTN {
        PlayerImplFORT9() {
            super(9);
        }
    }

    public static class PlayerImplFORT45 extends PlayerImplFORTN {
        PlayerImplFORT45() {
            super(45);
        }
    }

    /**
     * Class to simulate tournament for several players
     * Each pair of the players plays K rounds
     * Scores of a all games of a single player are summed
     * They are evaluated to determine the winner
     */
    static class Tournament {
        Player[] players;
        double[] scores;
        int K;

        /**
         * Constructor
         *
         * @param players - Tournament participants
         * @param K       - number of rounds in a single game
         */
        Tournament(Player[] players, int K) {
            this.players = players;
            this.K = K;
            this.scores = new double[this.players.length];
            Arrays.fill(this.scores, 0);
        }

        /**
         * Calculate the scores
         */
        void hold() {
            for (int i = 0; i < players.length; i++) {
                for (int j = i + 1; j < players.length; j++) {
                    Player[] pair = {players[i], players[j]};


                    System.out.println(
                            "Tournament   :\t" + pair[0].getClass().getSimpleName() +
                                    " vs. " + pair[1].getClass().getSimpleName()
                    );

                    KamilAkhmetovCode.Game game = new KamilAkhmetovCode.Game(pair);
                    for (int k = 0; k < K; k++) {
                        game.play();
                    }

                    scores[i] += game.scores[0];
                    scores[j] += game.scores[1];

                    System.out.println("\t" + pair[0].getClass().getSimpleName() + " gains " + game.scores[0] + " => " + scores[i]);
                    System.out.println("\t" + pair[1].getClass().getSimpleName() + " gains " + game.scores[1] + " => " + scores[j]);
                }
            }
        }

        /**
         * Return the winner by finding the maximum score
         *
         * @return winner index from the initial list passed in constructor
         */
        int winner() {
            System.out.println("\nTotal Scores:");

            Pair<Integer, Double>[] places = new Pair[scores.length];
            for (int i = 0; i < places.length; i++) {
                places[i] = new Pair<>(i, scores[i]);
            }

            final Comparator<Pair<Integer, Double>> c = Collections.reverseOrder(Comparator.comparing(Pair::getValue));
            Arrays.sort(places, c);

            for (int i = 0; i < places.length; i++) {
                System.out.println("\t Place # " + i + " || " + places[i].getKey() + " || " +
                        players[places[i].getKey()].getClass().getSimpleName() +
                        " => " + places[i].getValue());
            }

            return places[0].getKey();
        }

    }

    public static void main(String[] args) {
        System.out.println("Testing");

        Player[] players = {
                new PlayerImplRAND(),
                new PlayerImplRANDNOTMIN(),
                new PlayerImplRANDNOT0(),
                new PlayerImplCF(),
                new PlayerImplCB(),
                new PlayerImplRANDCF(),
                new PlayerImplRANDCB(),
                new PlayerImplCOPY(),
                new PlayerImplCOPYNOT0(),
                new PlayerImplALLD(),
                new PlayerImplALLC1(),
                new PlayerImplALLMIN(),
                new PlayerImplALLC1C2(),
                new PlayerImplALLC1D0(),
                new PlayerImplTFT(),
                new PlayerImplFORT5(),
                new PlayerImplFORT9(),
                new PlayerImplFORT45(),
                new PlayerImplKEEPER(),
                new PlayerImplPATKEEPER(),
                new PlayerImplANTICOPYNOTMIN(),


                new PlayerImplRAND(),
                new PlayerImplRANDNOTMIN(),
                new PlayerImplRANDNOT0(),
                new PlayerImplCF(),
                new PlayerImplCB(),
                new PlayerImplRANDCF(),
                new PlayerImplRANDCB(),
                new PlayerImplCOPY(),
                new PlayerImplCOPYNOT0(),
                new PlayerImplALLD(),
                new PlayerImplALLC1(),
                new PlayerImplALLMIN(),
                new PlayerImplALLC1C2(),
                new PlayerImplALLC1D0(),
                new PlayerImplTFT(),
                new PlayerImplFORT5(),
                new PlayerImplFORT9(),
                new PlayerImplFORT45(),
                new PlayerImplKEEPER(),
                new PlayerImplPATKEEPER(),
                new PlayerImplANTICOPYNOTMIN(),
        };


        Tournament tournament = new Tournament(players, 1000);
        tournament.hold();
        int winner = tournament.winner();

        System.out.println("The winner is " + players[winner].getClass().getSimpleName() + " !!!");


    }
}
