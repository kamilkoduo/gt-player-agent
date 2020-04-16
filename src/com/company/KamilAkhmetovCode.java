package com.company;

import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class KamilAkhmetovCode {
    /**
     * Class to model the environment of the game: field cells with their X values
     * We model the growth and exhausting of vegetation
     */
    static class Environment {
        private int[] XCells = {1, 1, 1};
        private int playsCount = 0;

        /**
         * Vegetation function
         */
        private double f(int x) {
            double e = Math.exp(x);
            return (10 * e) / (1 + e);
        }

        /**
         * Payoff function
         */
        private double gain(int cell) {
            return f(XCells[cell]) - f(0);
        }

        int[] sortedCells(){
            Pair<Integer, Integer>[] cellPairs = sortedCellPairs();
            int[] cells = new int[cellPairs.length];
            for (int i =0;i<cells.length; i++){
                cells[i] = cellPairs[i].getKey();
            }
            return cells;
        }

        Pair<Integer, Integer>[] sortedCellPairs(){
            Pair<Integer, Integer>[] cellPairs = new Pair[XCells.length];
            for (int i =0;i<cellPairs.length;i++){
                cellPairs[i]= new Pair<>(i,XCells[i]);
            }
            final Comparator<Pair<Integer, Integer>> c = Collections.reverseOrder(Comparator.comparing(Pair::getValue));
            Arrays.sort(cellPairs, c);
            return cellPairs;
        }


        /**
         * Updates state of the cells according to moves
         * We first mark the cells where a Moose is present in the mask
         * Apply the mask to determine the vegetation value change for each of the cells
         */
        private void updateCells(int[] moves) {
            assert moves.length == 2;

            boolean[] mask = new boolean[XCells.length];
            Arrays.fill(mask, true);
            for (int move : moves) {
                mask[move-1] = false;
            }
            for (int i = 0; i < XCells.length; i++) {
                if (mask[i]) {
                    XCells[i] += 1;
                } else {
                    XCells[i] = Math.max(0, XCells[i] - 1);
                }
            }
        }

        /**
         * Calculates payoffs and updates cells for a single play
         *
         * @param moves - moves of the 1st and 2nd players
         * @return payoffs
         */
        double[] play(int[] moves) {
            assert moves.length == 2;
            double[] payoffs = new double[2];

            if (moves[0] == moves[1]) {
                payoffs[0] = 0;
                payoffs[1] = 0;
            } else {
                payoffs[0] = gain(moves[0]-1);
                payoffs[1] = gain(moves[1]-1);
            }

            updateCells(moves);
            playsCount++;

            return payoffs;
        }
    }

    /**
     * Class to model a single game between two players
     * It incorporates a single environment for a number of plays
     */
    static class Game {
        Player[] players;
        int[] lastMoves = {0, 0};
        double[] scores = {0, 0};
        Environment environment = new Environment();

        /**
         * Constructor
         *
         * @param players two opponents
         */
        Game(Player[] players) {
            assert players.length == 2;
            this.players = players;
            for (Player pl : players) {
                pl.reset();
            }
        }

        /**
         * Simulates a single play in a game
         */
        void play() {
            System.out.printf("\nMove #%d\n", environment.playsCount);
            System.out.println("Field   :\t" + Arrays.toString(environment.XCells));
            System.out.println("Last Moves   :\t" + Arrays.toString(lastMoves));

            int xA = environment.XCells[0];
            int xB = environment.XCells[1];
            int xC = environment.XCells[2];

            int[] moves = {
                    players[0].move(lastMoves[1], xA, xB, xC),
                    players[1].move(lastMoves[0], xA, xB, xC),
            };
            lastMoves = moves;

            double[] p = environment.play(moves);
            Arrays.setAll(scores, i -> scores[i] + p[i]);

            System.out.println("Moves   :\t" + Arrays.toString(moves));
            System.out.println("Field   :\t" + Arrays.toString(environment.XCells));
            System.out.println("Payoffs :\t" + Arrays.toString(p));
            System.out.println("Scores :\t" + Arrays.toString(scores));
        }

    }


}