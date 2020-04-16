package com.company;

import java.util.*;

public class KamilAkhmetovTesting {
    public static class KamilAkhmetovPlayerImplRAND extends KamilAkhmetovCode.PlayerImpl {
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

    public static class KamilAkhmetovPlayerImplRANDNOTMIN extends KamilAkhmetovCode.PlayerImpl {
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

    public static class KamilAkhmetovPlayerImplRANDNOT0 extends KamilAkhmetovCode.PlayerImpl {
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
    public static class KamilAkhmetovPlayerImplCF extends KamilAkhmetovCode.PlayerImpl {
        int k;

        KamilAkhmetovPlayerImplCF() {
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
    public static class KamilAkhmetovPlayerImplCB extends KamilAkhmetovCode.PlayerImpl {
        int k = 0;

        KamilAkhmetovPlayerImplCB() {
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
    public static class KamilAkhmetovPlayerImplRANDCF extends KamilAkhmetovCode.PlayerImpl {
        int k;

        KamilAkhmetovPlayerImplRANDCF() {
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
    public static class KamilAkhmetovPlayerImplRANDCB extends KamilAkhmetovCode.PlayerImpl {
        int k;

        KamilAkhmetovPlayerImplRANDCB() {
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

    public static class KamilAkhmetovPlayerImplCOPY extends KamilAkhmetovCode.PlayerImpl {
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

    public static class KamilAkhmetovPlayerImplCOPYNOT0 extends KamilAkhmetovCode.PlayerImpl {
        int lastMove;

        KamilAkhmetovPlayerImplCOPYNOT0() {
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



    public static class KamilAkhmetovPlayerImplALLD extends KamilAkhmetovCode.PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int lastMove;

        KamilAkhmetovPlayerImplALLD() {
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

    public static class KamilAkhmetovPlayerImplALLMID extends KamilAkhmetovCode.PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int last_move;

        KamilAkhmetovPlayerImplALLMID() {
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


    }

    public static class KamilAkhmetovPlayerImplALLMIN extends KamilAkhmetovCode.PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int last_move;

        KamilAkhmetovPlayerImplALLMIN() {
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

    public static class KamilAkhmetovPlayerImplALLMIDMIN extends KamilAkhmetovCode.PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int last_move;
        int state;

        KamilAkhmetovPlayerImplALLMIDMIN() {
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

    public static class KamilAkhmetovPlayerImplALLMIDMAX extends KamilAkhmetovCode.PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int last_move;
        int state;

        KamilAkhmetovPlayerImplALLMIDMAX() {
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

    public static class KamilAkhmetovPlayerImplTFT extends KamilAkhmetovCode.PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int last_move;
        int state;
        int cell;
        int last_max_cell;

        KamilAkhmetovPlayerImplTFT() {
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

    public static class KamilAkhmetovPlayerImplKEEPER extends KamilAkhmetovCode.PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int last_move;
        int state;
        int cell;
        int last_max_cell;
        int offense;

        KamilAkhmetovPlayerImplKEEPER() {
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

    public static class KamilAkhmetovPlayerImplPATKEEPER extends KamilAkhmetovCode.PlayerImpl {
        KamilAkhmetovCode.Environment environment;
        int last_move;
        int state;
        int cell;
        int last_max_cell;
        int offense;

        KamilAkhmetovPlayerImplPATKEEPER() {
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

    public static class KamilAkhmetovPlayerImplFORTN extends KamilAkhmetovCode.PlayerImpl {
        KamilAkhmetovCode.Environment environment = new KamilAkhmetovCode.Environment();
        int last_move;
        int N;
        int count;
        int last_max_cell;
        int state;

        KamilAkhmetovPlayerImplFORTN() {
        }

        KamilAkhmetovPlayerImplFORTN(int n) {
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

    public static class KamilAkhmetovPlayerImplFORT5 extends KamilAkhmetovPlayerImplFORTN {
        KamilAkhmetovPlayerImplFORT5() {
            super(5);
        }
    }

    public static class KamilAkhmetovPlayerImplFORT9 extends KamilAkhmetovPlayerImplFORTN {
        KamilAkhmetovPlayerImplFORT9() {
            super(9);
        }
    }

    public static class KamilAkhmetovPlayerImplFORT45 extends KamilAkhmetovPlayerImplFORTN {
        KamilAkhmetovPlayerImplFORT45() {
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

            AbstractMap.SimpleEntry<Integer, Double>[] places = new AbstractMap.SimpleEntry[scores.length];
            for (int i = 0; i < places.length; i++) {
                places[i] = new AbstractMap.SimpleEntry<>(i, scores[i]);
            }

            final Comparator<AbstractMap.SimpleEntry<Integer, Double>> c = Collections.reverseOrder(Comparator.comparing(AbstractMap.SimpleEntry::getValue));
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
                new KamilAkhmetovCode(),
                new KamilAkhmetovPlayerImplCF(),
                new KamilAkhmetovPlayerImplCB(),

                new KamilAkhmetovPlayerImplRAND(),
                new KamilAkhmetovPlayerImplRANDNOTMIN(),
                new KamilAkhmetovPlayerImplRANDNOT0(),
                new KamilAkhmetovPlayerImplRANDCF(),
                new KamilAkhmetovPlayerImplRANDCB(),

                new KamilAkhmetovPlayerImplCOPY(),
                new KamilAkhmetovPlayerImplCOPYNOT0(),
                new KamilAkhmetovCode.KamilAkhmetovPlayerImplANTICOPYNOTMIN(),

                new KamilAkhmetovPlayerImplALLD(),
                new KamilAkhmetovPlayerImplALLMID(),
                new KamilAkhmetovPlayerImplALLMIN(),
                new KamilAkhmetovPlayerImplALLMIDMIN(),
                new KamilAkhmetovPlayerImplALLMIDMAX(),

                new KamilAkhmetovPlayerImplTFT(),
                new KamilAkhmetovPlayerImplFORT5(),
                new KamilAkhmetovPlayerImplFORT9(),
                new KamilAkhmetovPlayerImplFORT45(),
                new KamilAkhmetovPlayerImplKEEPER(),
                new KamilAkhmetovPlayerImplPATKEEPER(),

                new KamilAkhmetovPlayerImplCF(),
                new KamilAkhmetovPlayerImplCB(),

                new KamilAkhmetovPlayerImplRAND(),
                new KamilAkhmetovPlayerImplRANDNOTMIN(),
                new KamilAkhmetovPlayerImplRANDNOT0(),
                new KamilAkhmetovPlayerImplRANDCF(),
                new KamilAkhmetovPlayerImplRANDCB(),

                new KamilAkhmetovPlayerImplCOPY(),
                new KamilAkhmetovPlayerImplCOPYNOT0(),
                new KamilAkhmetovCode.KamilAkhmetovPlayerImplANTICOPYNOTMIN(),

                new KamilAkhmetovPlayerImplALLD(),
                new KamilAkhmetovPlayerImplALLMID(),
                new KamilAkhmetovPlayerImplALLMIN(),
                new KamilAkhmetovPlayerImplALLMIDMIN(),
                new KamilAkhmetovPlayerImplALLMIDMAX(),

                new KamilAkhmetovPlayerImplTFT(),
                new KamilAkhmetovPlayerImplFORT5(),
                new KamilAkhmetovPlayerImplFORT9(),
                new KamilAkhmetovPlayerImplFORT45(),
                new KamilAkhmetovPlayerImplKEEPER(),
                new KamilAkhmetovPlayerImplPATKEEPER(),
        };


        Tournament tournament = new Tournament(players, 1000);
        tournament.hold();
        int winner = tournament.winner();

        System.out.println("The winner is " + players[winner].getClass().getSimpleName() + " !!!");

    }
}
