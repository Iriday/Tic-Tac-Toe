package tictactoe;

import java.util.Scanner;

public class Main {

    enum GameState {
        GAME_NOT_FINISHED("Game not finished"), DRAW("Draw"), X_WINS("X wins"), O_WINS("O wins"), IMPOSSIBLE("Impossible");
        String info;

        GameState(String info) {
            this.info = info;
        }
    }

    //represents game field
    private static final char[][] gameFieldData = new char[3][3];
    private static final Scanner scn = new Scanner(System.in);
    private static GameState gameState;
    private static char player = 'O';

    public static void main(String[] args) {

        // set initial field state from terminal
        //setInitialFieldStateFromInput();

        //starts the game again if user types "Yes" after the end of the game
        do {
            // reset/initialize game state
            gameState = GameState.GAME_NOT_FINISHED;

            // initialize/reset array/field with empty cells
            initializeArrayWithEmptyCells();

            // print empty field
            printField(gameFieldData);

            //game loop
            while (gameState == GameState.GAME_NOT_FINISHED) {

                // read input and make next move
                readInputAndMakeNextMove(gameFieldData);// int[] coordinates =

                // check game state
                gameState = checkGameState(gameFieldData);

                // print field data
                printField(gameFieldData);
            }

            //the end of the game, print outcome
            System.out.println(gameState.info);

            System.out.println("Do you want to play again? type Yes or No");
        } while (scn.next().equalsIgnoreCase("Yes"));//, Locale.ENGLISH
    }

    private static void initializeArrayWithEmptyCells() {
        char emptyCell = ' ';
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 3; column++) {
                gameFieldData[row][column] = emptyCell;
            }
        }
    }

    private static void readInputAndMakeNextMove(char[][] gameFieldData) {
        int x = 0;
        int y = 0;
        player = player == 'O' ? 'X' : 'O';

        while (true) {
            System.out.print("Enter the coordinates: ");
            try {
                x = Integer.parseInt(scn.next());
                y = Integer.parseInt(scn.next());
            } catch (Exception e) {
                System.out.println("You should enter numbers!");
                continue;
            }
            if (x < 1 || x > 3 || y < 1 || y > 3) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            } else {
                // convert coordinates to array indexes
                int column = x - 1;
                int row = y == 3 ? 0 : y == 1 ? 2 : 1;
                // check if cell is empty
                if (gameFieldData[row][column] == ' ' || gameFieldData[row][column] == '_' || gameFieldData[row][column] == '-') {
                    gameFieldData[row][column] = player;
                    break;
                } else {
                    System.out.println("This cell is occupied! Choose another one!");
                    continue;
                }
            }
        }
    }


    private static void printField(char[][] gameFieldData) {
        String s1 = " ";
        String s2 = "|";

        System.out.println("---------");
        for (int i = 0; i < 3; i++) {
            System.out.print(s2 + s1);
            for (char val : gameFieldData[i]) {
                System.out.print(val + s1);
            }
            System.out.println(s2);
        }
        System.out.println("---------");
    }

    private static GameState checkGameState(char[][] gameFieldData) {
        int x = 0;
        int o = 0;

        //check correctness
        for (char[] row : gameFieldData) {
            for (char val : row) {
                if (val == 'X') {
                    x++;
                } else if (val == 'O') {
                    o++;
                } else if (val == ' ' || val == '_' || val == '-') {
                    continue;
                } else {
                    return GameState.IMPOSSIBLE;
                }
            }
        }

        if (!(x - o == 0 || x - o == 1 || x - o == -1)) {
            return GameState.IMPOSSIBLE;
        }
        boolean xPlayer = checkWinner('X');
        boolean oPlayer = checkWinner('O');
        if (xPlayer && oPlayer) {
            return GameState.IMPOSSIBLE;
        }
        if (xPlayer) {
            return GameState.X_WINS;
        } else if (oPlayer) {
            return GameState.O_WINS;
        }
        // check draw or game not finished
        if (drawOrNotFinished()) {
            return GameState.DRAW;
        } else {
            return GameState.GAME_NOT_FINISHED;
        }
    }

    private static boolean checkWinner(char player) {

        //check rows
        for (char[] row : gameFieldData) {
            if (row[0] == player && row[1] == player && row[2] == player) {
                return true;
            }
        }
        //check columns
        for (int i = 0; i < 3; i++) {
            if (gameFieldData[0][i] == player && gameFieldData[1][i] == player && gameFieldData[2][i] == player) {
                return true;
            }
        }
        // check diagonals
        if (gameFieldData[0][0] == player && gameFieldData[1][1] == player && gameFieldData[2][2] == player || gameFieldData[0][2] == player && gameFieldData[1][1] == player && gameFieldData[2][0] == player) {
            return true;
        }
        return false;
    }

    private static boolean drawOrNotFinished() {
        for (char[] row : gameFieldData) {
            for (char val : row) {
                if (val == 'X' || val == 'O') {
                    continue;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}

  /*  private static void setInitialFieldStateFromInput() {
        String input;

        for (; true; ) {
            System.out.print("Enter cells: ");
            input = scn.nextLine();
            if (input.length() != 9) {
                System.out.println("Error, incorrect number of arguments, try again");
            } else {
                break;
            }
        }
        // toUpperCase
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            sb.append(Character.toUpperCase(input.charAt(i)));
        }
        input = sb.toString();

        gameFieldData[0] = input.substring(0, 3).toCharArray();
        gameFieldData[1] = input.substring(3, 6).toCharArray();
        gameFieldData[2] = input.substring(6, 9).toCharArray();

    }*/