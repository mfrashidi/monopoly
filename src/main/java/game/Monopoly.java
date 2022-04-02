package game;

import players.Player;
import utilities.Jui;

import java.io.IOException;

public class Monopoly {
    private static Jui jui;
    private static Player[] players = new Player[0];
    private static int option = 0;
    private static Game game;

    public static void main(String[] args) throws IOException {
        jui = new Jui();
        while (true){
            jui.clearScreen();
            printTitle();
            printMenu();

            int input = jui.getInput();
            if (input == 65 && option == 1) option--;
            else if (input == 66 && option == 0) option++;
            else if(input == 127) break;
            else if(input == 13){
                if (option == 0) {
                    if (players.length < 4) addPlayer();
                    else jui.playSound();
                }
                else if (option == 1){
                    if (players.length > 1){
                        game = new Game(players);
                        startGame();
                    } else {
                        jui.playSound();
                    }
                }
            }
        }
    }

    public static void printTitle() throws IOException {
        String gameName = "game.Monopoly";
        jui.changeCursorPosition(2, (jui.getColumns() - gameName.length()) / 2);
        jui.customPrint(gameName, Jui.Colors.BOLD_GREEN);

        String authors = "By @mfrashidi & @MohammadNikfallah";
        jui.changeCursorPosition(3, (jui.getColumns() - authors.length()) / 2);
        jui.customPrint(authors, Jui.Colors.BOLD_CYAN);
    }

    public static void addPlayer() throws IOException {
        Jui.Colors[] colors = {Jui.Colors.BOLD_CYAN, Jui.Colors.BOLD_GREEN, Jui.Colors.BOLD_RED, Jui.Colors.BOLD_YELLOW};
        String name = "";
        String request = "Enter your name and then press ENTER";
        int letter;
        while (true){
            jui.clearScreen();
            printTitle();
            jui.changeCursorPosition(jui.getRows() - 2, (jui.getColumns() - request.length()) / 2);
            jui.customPrint(request, Jui.Colors.BOLD_YELLOW);
            jui.customPrint(name, Jui.Position.CENTER, Jui.Colors.BOLD_GREEN);
            letter = jui.getInput();
            if (letter == 127) {
                if (name.length() > 0) name = name.substring(0, name.length() - 1);
            }
            else if (letter == 13 && name.length() > 2) break;
            else if ((letter >=65 && letter <= 90) || (letter >=97 && letter <= 122)) name += (char) letter;
        }
        Player player = new Player(players.length, name, colors[players.length]);

        Player[] newPlayers = new Player[players.length + 1];
        System.arraycopy(players, 0, newPlayers, 0, players.length);
        newPlayers[players.length] = player;
        players = newPlayers;
        option = 0;
    }

    public static void printMenu() throws IOException {
        if (players.length > 0){
            int row = (jui.getRows() - (players.length + 2)) / 2;

            String playersText = "List of players:";
            jui.changeCursorPosition(row, (jui.getColumns() - playersText.length()) / 2);
            jui.customPrint(playersText, Jui.Colors.BOLD_YELLOW);
            row += 2;

            for (int i = 0; i < players.length; i++){
                jui.changeCursorPosition(row + i, (jui.getColumns() - (players[i].getName().length() + 3)) / 2);
                jui.customPrint("#" + (i + 1) + " ", Jui.Colors.BOLD_RED);
                jui.italic();
                jui.customPrint(players[i].getName(), players[i].getColor());
                jui.italic();
            }
        }

        Jui.Colors color = ((players.length < 4) ? Jui.Colors.BOLD_YELLOW : Jui.Colors.BOLD_GRAY);
        if(option == 0) jui.changeBackgroundColor(color);
        String addPlayer = "Add Player";
        jui.changeCursorPosition(jui.getRows() - 3, (jui.getColumns() - addPlayer.length()) / 2);
        jui.customPrint(addPlayer, color);
        if(option == 0) jui.changeBackgroundColor(Jui.Colors.DEFAULT);

        color = ((players.length > 1) ? Jui.Colors.BOLD_YELLOW : Jui.Colors.BOLD_GRAY);
        if(option == 1) jui.changeBackgroundColor(color);
        String startGame = "Start game.Game";
        jui.changeCursorPosition(jui.getRows() - 2, (jui.getColumns() - startGame.length()) / 2);
        jui.customPrint(startGame, color);
        if(option == 1) jui.changeBackgroundColor(Jui.Colors.DEFAULT);

        jui.changeCursorPosition(0, 0);
    }

    public static void startGame(){

    }
}
