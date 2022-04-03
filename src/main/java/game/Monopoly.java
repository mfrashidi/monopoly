package game;

import lands.EmptyLands;
import lands.Lands;
import lands.LandsWithRent;
import players.Player;
import utilities.Actions;
import utilities.Jui;
import utilities.Property;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Monopoly {
    private static Jui jui;
    private static Player[] players = new Player[0];
    private static int option = 0;
    private static int actionNumber = 0;
    private static Game game;
    private static String message;
    private static Jui.Colors msgColor;

    public static void main(String[] args) throws IOException, InterruptedException {
        jui = new Jui();
//        printPoster();
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
        String title = "                                                                 /$$          \n" +
                "                                                                | $$          \n" +
                " /$$$$$$/$$$$   /$$$$$$  /$$$$$$$   /$$$$$$   /$$$$$$   /$$$$$$ | $$ /$$   /$$\n" +
                "| $$_  $$_  $$ /$$__  $$| $$__  $$ /$$__  $$ /$$__  $$ /$$__  $$| $$| $$  | $$\n" +
                "| $$ \\ $$ \\ $$| $$  \\ $$| $$  \\ $$| $$  \\ $$| $$  \\ $$| $$  \\ $$| $$| $$  | $$\n" +
                "| $$ | $$ | $$| $$  | $$| $$  | $$| $$  | $$| $$  | $$| $$  | $$| $$| $$  | $$\n" +
                "| $$ | $$ | $$|  $$$$$$/| $$  | $$|  $$$$$$/| $$$$$$$/|  $$$$$$/| $$|  $$$$$$$\n" +
                "|__/ |__/ |__/ \\______/ |__/  |__/ \\______/ | $$____/  \\______/ |__/ \\____  $$\n" +
                "                                            | $$                     /$$  | $$\n" +
                "                                            | $$                    |  $$$$$$/\n" +
                "                                            |__/                     \\______/ ";
        jui.changeColor(Jui.Colors.values()[new Random().nextInt(Jui.Colors.values().length)]);
        String[] lines = title.split("\n");
        for (int i = 0; i < lines.length; i++){
            jui.changeCursorPosition(2 + i, (jui.getColumns() - lines[i].length()) / 2);
            System.out.print(lines[i]);
        }

        String authors = "By @mfrashidi & @MohammadNikfallah";
        jui.changeCursorPosition(15, (jui.getColumns() - authors.length()) / 2);
        jui.changeBackgroundColor(Jui.Colors.BOLD_CYAN);
        jui.customPrint(authors, Jui.Colors.BOLD_CYAN);
        jui.changeBackgroundColor(Jui.Colors.DEFAULT);
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
            jui.customPrint(name, Jui.Position.CENTER, colors[players.length]);
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
                jui.customPrint((i + 1) + ") ", Jui.Colors.BOLD_WHITE);
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
        String startGame = "Start Game";
        jui.changeCursorPosition(jui.getRows() - 2, (jui.getColumns() - startGame.length()) / 2);
        jui.customPrint(startGame, color);
        if(option == 1) jui.changeBackgroundColor(Jui.Colors.DEFAULT);

        jui.changeCursorPosition(0, 0);
    }

    public static void printPoster() throws InterruptedException, IOException {
        String poster = "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNXK00KXNWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWXd;'.:ddddxxxdxOXWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWO.  .xWWWWWNd.  .,:dKWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNNNWNKKKXNNl   :XWWWWWx.       .lKWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNkxxdooccol:cOk.  .lOO0XNd.       .llcKWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWOcok0Kkdxd:c0Xk'..,ccclloc.       .xxlOWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWk:kW0:lxlcc'.ol. .:k0KKXNO:'..   .,ddcKWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNW0cxWNxl00occ,..;:::cc:'.,;,..........'xWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWXxl:oNMN00K0kkd;:oONWNNKxlc;lkkxdo:;;;:clldXWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWk;;loxxkkxkO0KXooOOXWWWWWOONKONWWWKoco:.;OKccXWWWWWWWWKkdolkWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWX;  .cdxxxodNWWx:0N O0WWWW  WWWWWWWWO:lc,'lk:lNMWNK00Okxx0Kd:OMWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWO'..    ...cNMWlcK0xcl0OOKWWWWWWWWWWXOKXXo,:oxO00xc;.,x0Okxd::o0WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWk;o;       'kxl::dx0 O0Kxl KNWNXNWWWK0koc,;xO0XXxcol..oKO0NNX0c;0WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWK;ll        ,:;oOX XOx0NWXxd l;lXWWXc..:okXWWWWWXXO::..;lxkOK0:'cokNWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWd;d;         ...'coloxkkxddolxXWXx:,;xOOxxKWWWWWWKKx.  .;;...  ':ccOWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNo;o:            .lOXWWNKKKXXNNkcc:;:;....c0WWWWWWWNk,           ;c;xWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNkccl;.            ;lccolc:d0o:Od,o;.. .:xXWWWWWWWNOxo.          :c;KWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWXxll:;,..       .c; .,.'loclXN:,xkc. ...dWWNWWXkOc:d'.,c.      ,l:0WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW0xddoc;,,''''.;l,.lOxdldXMNc'oo:.  .'dNkcdkxxdodxkxcok:     :clNWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWX0xocc:;;.,'  .oNMNOkKWO':KXOooxXNXkoxkxd0WWWWWXll0;  .:clKWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWXkdoodoodddddddolccoxxkxkOOx,,d0X0Okkxx0Oxo'oWWWWWWx;kc .;lkNXd::dKWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWXxllox0XWWWWWWWWWWWWWWNX0kkkdokd;...';:oo:;:looxKWWWW0,.'..:xO0k;    'xNWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNxclONWWWWWWWWWWWWWWWWWWWWWWWWNNWWN0xodxdoc;,:lddxXWN0l.    'd00XXOdoodOXkldOKWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWX  WWWWWKlc0WWWWWWWWWWWWWWWWWWWWWWWWWWWWWXXWWWWWWWWWX0kxk0KK0kl:cloxOKNWWWWWWWWWWK;.';ldx0NWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWW0l,':ldkOooXWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWXxkNWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW0'   .,;ldONWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWXko:.    .;KWWWWWWWWWWWWWWN0Ok0KkxkKWWWWWWWXdc KXWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNklcll:;'.':l0WWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWNKOdc,.. 'OWWWWWWWWWWWXkdoxO0OO0Kkdodk0KNWWNOc: kXWWWWWWWWWWWWWWWWWWWWWWWWWXOdodONWWWWN0kdONWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWX00Ox:  XKKXWWWWXkoookXWWWWWWWWWKkolodxkOKNN0dc   dk0XNWWWWWWWWWWWNX0OxooodkXWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNXXd'...;oOOxxONWWWWWWWWWWWWWWWWWWWWWWWWWWWKko                     oWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNOl,..:OKXWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNXK0OkxxxxxkkOKXNWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWNKKNWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW\n" +
                "WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW";

        jui.changeColor(Jui.Colors.BOLD_YELLOW);

        String[] lines = poster.split("\n");
        int length = 0;
        for (int i=0; i < lines.length ; i++)
            if (lines[i].length() > length)
                length = lines[i].length();
        int width = poster.split("\n").length;
        if (jui.getColumns() >= length && jui.getRows() >= width) {
            for (int i = 0; i < poster.split("\n").length; i++) {
                length = lines[i].length();
                jui.changeCursorPosition(((jui.getRows() - width)  / 2) + i, (jui.getColumns() - length) / 2);
                System.out.print(lines[i]);
                jui.sleep(100);
            }
        }

        jui.changeColor(Jui.Colors.DEFAULT);
        jui.sleep(4000);

    }

    public static void startGame() throws IOException {
        int input;
        while (true){
            jui.clearScreen();
            updateActions();
            printTable();
            updateHeader();
            updateLeaderboard();
            updateFooter();
            jui.changeCursorPosition(jui.getRows(), jui.getColumns());
            input = jui.getInput();
            if (input == 65){
                for (int i = actionNumber - 1; i >= 0; i--){
                    if (game.getCurrentPLayer().getActions().contains(Actions.values()[i])) {
                        actionNumber = i;
                        break;
                    }
                }
            }
            else if (input == 66){
                for (int i = actionNumber + 1; i < Actions.values().length; i++){
                    if (game.getCurrentPLayer().getActions().contains(Actions.values()[i])) {
                        actionNumber = i;
                        break;
                    }
                }
            }
            else if (input == 13) handleActions();
            else if (input == 127) break;
        }

    }

    public static void printTable() throws IOException {
        int height = 5;
        int width = 12;
        int x, y, number;
        for (int i = 0; i < 7; i++){
            for (int j = 0;j < 7; j++){
                number = 6 - i + j;
                if (i > 0 && i < 6 && j > 0 && j < 6) continue;
                y = ((jui.getRows() - (height * 7)) / 2) + height * i;
                x = ((jui.getColumns() - (width * 7)) / 2) + width * j;
                if (i > 0 && i < 6 && j == 6) number += 2 * i;
                else if (i == 6 && j > 0) number = 24 - j;

                jui.drawRectangle(height, width, game.getLands()[number].getColor(), x, y);

                jui.changeBackgroundColor(Jui.Colors.BLACK);
                jui.changeCursorPosition(y + height - 1, x + width - ((number + 1 < 10) ? 1 : 2));
                jui.customPrint(String.valueOf(number + 1), Jui.Colors.BOLD_WHITE);
                jui.changeBackgroundColor(Jui.Colors.DEFAULT);

                jui.changeBackgroundColor(game.getLands()[number].getColor());
                jui.changeCursorPosition(y + (height / 2), x + (width / 2) - 1);
                System.out.println(game.getLands()[number].getIcon());
                jui.changeBackgroundColor(Jui.Colors.DEFAULT);

                if (number + 1 == game.getCurrentPLayer().getCurrentPosition()){
                    if (number <= 6){
                        x -= 2;
                        y += height / 2;
                    } else if (number < 12){
                        y -= 1;
                        x += width / 2;
                    } else if (number <= 18){
                        x += width + 1;
                        y += height / 2;
                    } else{
                        y += height + 1;
                        x += width / 2;
                    }
                    jui.changeColor(game.getCurrentPLayer().getColor());
                    jui.changeCursorPosition(y, x);
                    System.out.print("*");
                }
            }
        }
        String title = "  __  __  ____  _   _  ____  _____   ____  _  __     __\n" +
                " |  \\/  |/ __ \\| \\ | |/ __ \\|  __ \\ / __ \\| | \\ \\   / /\n" +
                " | \\  / | |  | |  \\| | |  | | |__) | |  | | |  \\ \\_/ / \n" +
                " | |\\/| | |  | | . ` | |  | |  ___/| |  | | |   \\   /  \n" +
                " | |  | | |__| | |\\  | |__| | |    | |__| | |____| |   \n" +
                " |_|  |_|\\____/|_| \\_|\\____/|_|     \\____/|______|_|   ";
//        String spaces = "          ";

//        jui.changeBackgroundColor(Jui.Colors.RED);
//
//        jui.changeCursorPosition((jui.getRows() / 2) - 1, (jui.getColumns() - spaces.length()) / 2);
//        System.out.print(spaces);
//
//        jui.changeCursorPosition((jui.getRows() / 2), (jui.getColumns() - monopoly.length()) / 2);
//        System.out.print(monopoly);
//
//        jui.changeCursorPosition((jui.getRows() / 2) + 1, (jui.getColumns() - spaces.length()) / 2);
//        System.out.print(spaces);
//
//        jui.changeBackgroundColor(Jui.Colors.DEFAULT);

        jui.changeColor(Jui.Colors.BOLD_RED);
        String[] lines = title.split("\n");
        y = (jui.getRows() - lines.length) / 2;
        for (int i = 0; i < lines.length; i++){
            jui.changeCursorPosition(y + i, (jui.getColumns() - lines[i].length()) / 2);
            System.out.print(lines[i]);
        }
        jui.changeBackgroundColor(Jui.Colors.DEFAULT);
    }

    public static void updateLeaderboard() throws IOException {
        int x = 2;
        int y = ((jui.getRows()) - (game.getPlayers().length + 2)) / 2;
        Player tmpPlayer;
        Player[] sortedPlayers = game.getPlayers();
        for (int i = 0; i < sortedPlayers.length - 1; i++){
            for (int j = 0;j < sortedPlayers.length - i - 1; j++){
                if (sortedPlayers[j].getBalance() < sortedPlayers[j + 1].getBalance()){
                    tmpPlayer = sortedPlayers[j];
                    sortedPlayers[j] = sortedPlayers[j + 1];
                    sortedPlayers[j + 1] = tmpPlayer;
                }
            }
        }
        Player player;
        jui.changeCursorPosition(y, x);
        jui.customPrint("Leaderboard:", Jui.Colors.BOLD_YELLOW);
        y += 2;
        for (int i = 0; i < sortedPlayers.length; i++){
            player = sortedPlayers[i];
            jui.changeCursorPosition(y, x);
            jui.changeColor(Jui.Colors.BOLD_RED);
            System.out.print((i + 1) + ") ");
            jui.changeColor(player.getColor());
            System.out.print(player.getName());
            jui.changeColor(((player.getBalance() > 0) ? Jui.Colors.GREEN : Jui.Colors.RED));
            jui.italic();
            System.out.print(" $" + player.getBalance());
            jui.italic();
            y++;
        }
    }

    public static void updateHeader() throws IOException {
        jui.changeCursorPosition(2, 2);
        jui.changeColor(game.getCurrentPLayer().getColor());
        jui.italic();
        System.out.print(game.getCurrentPLayer().getName());
        jui.italic();
        jui.changeColor(game.getCurrentPLayer().getColor());
        System.out.println("'s turn");
        jui.changeColor(Jui.Colors.DEFAULT);
    }

    public static void updateFooter() throws IOException {
        int position = game.getCurrentPLayer().getCurrentPosition() - 1;
        jui.changeCursorPosition(jui.getRows() - 2, 2);
        jui.changeColor(Jui.Colors.BOLD_RED);
        System.out.print("📍 Place: ");
        jui.changeColor(Jui.Colors.RED);
        jui.italic();
        System.out.print(game.getLands()[position].getName());
        jui.italic();


        jui.changeCursorPosition(jui.getRows() - 3, 2);
        jui.changeColor(Jui.Colors.BOLD_GREEN);
        System.out.print("💵 Balance: ");
        jui.changeColor(Jui.Colors.GREEN);
        jui.underline();
        System.out.print("$" + game.getCurrentPLayer().getBalance());
        jui.underline();
        jui.changeColor(Jui.Colors.DEFAULT);

        jui.changeCursorPosition(jui.getRows() - 4, 2);
        jui.changeColor(Jui.Colors.BOLD_YELLOW);
        System.out.print("🏠 Properties: ");
        jui.changeColor(Jui.Colors.YELLOW);
        String properties = "";
        for (Lands land: game.getCurrentPLayer().getOwnLands()) properties += land.getName() + ", ";
        if (properties.length() == 0) System.out.print("None");
        else System.out.print("[" + properties.substring(0, properties.length() - 2) + "]");
        jui.changeColor(Jui.Colors.DEFAULT);

        jui.changeCursorPosition(jui.getRows() - 5, 2);
        jui.changeColor(Jui.Colors.BOLD_GRAY);
        System.out.print("🎲 Dice: ");
        jui.changeColor(Jui.Colors.GRAY);
        System.out.print((game.getCurrentPLayer().getDiceRoll() != -1) ? game.getCurrentPLayer().getDiceRoll() : "Not rolled");
        jui.changeColor(Jui.Colors.DEFAULT);
    }

    public static void updateActions() throws IOException {
        Player currentPlayer = game.getCurrentPLayer();
        List<Actions> actions = new ArrayList<>();
        if (game.isChoosingPriorityMode()){
            if (currentPlayer.getDiceRoll() == -1) actions.add(Actions.RollDice);
            else actions.add(Actions.Next);
        } else {
            if (currentPlayer.getDiceRoll() == -1) actions.add(Actions.RollDice);
            else {
                if (currentPlayer.isActionsDone()){
                    actions.add(Actions.Next);
                } else {
                    Property property = game.getLands()[currentPlayer.getCurrentPosition() - 1].getType();
                    if (property.equals(Property.Parking)) currentPlayer.setActionsDone(true);
                    if (property.equals(Property.Airport)){
                        if (currentPlayer.getBalance() >= 50) actions.add(Actions.Fly);
                        actions.add(Actions.Next);
                    }
                    else if (property.equals(Property.Tax)) {
                        currentPlayer.pay((int) (currentPlayer.getBalance() * 0.1));
                        currentPlayer.setActionsDone(true);
                    } else if (property.equals(Property.Road)) {
                        if (currentPlayer.getBalance() >= 100){
                            currentPlayer.pay(100);
                        } else {
                            currentPlayer.pay(currentPlayer.getBalance());
                            currentPlayer.setGotBroke(true);
                        }
                        currentPlayer.setActionsDone(true);
                    } else if (property.equals(Property.Cinema)) {
                        LandsWithRent land = (LandsWithRent) game.getLands()[currentPlayer.getCurrentPosition() - 1];
                        Player owner = land.getOwner();
                        if (owner == null){
                            if (currentPlayer.getBalance() >= land.getCost()) actions.add(Actions.Buy);
                            actions.add(Actions.Next);
                        } else {
                            if (currentPlayer.getBalance() >= land.getRent()){
                                currentPlayer.pay(land.getRent());
                                owner.getPaid(land.getRent());
                                currentPlayer.setActionsDone(true);
                            } else {
                                currentPlayer.pay(currentPlayer.getBalance());
                                currentPlayer.setGotBroke(true);
                            }
                        }
                    } else if (property.equals(Property.Award)){
                        currentPlayer.getPaid(200);
                        currentPlayer.setActionsDone(true);
                    } else if (property.equals(Property.Empty)){
                        EmptyLands land = (EmptyLands) game.getLands()[currentPlayer.getCurrentPosition() - 1];
                        Player owner = land.getOwner();
                        if (owner == null){
                            if (currentPlayer.getBalance() >= land.getCost()) actions.add(Actions.Buy);
                            actions.add(Actions.Next);
                        } else if (owner.equals(currentPlayer)) {
                            actions.add(Actions.Build);
                            actions.add(Actions.Next);
                        } else {
                            if (currentPlayer.getBalance() >= land.getRent()){
                                currentPlayer.pay(land.getRent());
                                owner.getPaid(land.getRent());
                                currentPlayer.setActionsDone(true);
                            } else {
                                currentPlayer.pay(currentPlayer.getBalance());
                                currentPlayer.setGotBroke(true);
                            }
                        }
                    } else if (property.equals(Property.Jail)){
                        currentPlayer.setInJail(true);
                        actions.add(Actions.Free);
                        actions.add(Actions.Next);
                    } else if (property.equals(Property.Bank)){
                        actions.add(Actions.Invest);
                        actions.add(Actions.Next);
                    }
                }
            }
        }
        currentPlayer.setActions(actions);


        int lines = 9;
        int x = jui.getColumns() - 20;
        int y = (jui.getRows() - lines) / 2;

        jui.changeCursorPosition(y, x);
        jui.customPrint("Actions:", Jui.Colors.BOLD_YELLOW);
        y += 2;

        Jui.Colors color;
        for (int i = 0; i < Actions.values().length; i++){
            color = ((currentPlayer.getActions().contains(Actions.values()[i])) ? Jui.Colors.BOLD_YELLOW : Jui.Colors.GRAY);
            if(actionNumber == i && color.equals(Jui.Colors.GRAY)) color = Jui.Colors.BOLD_GRAY;
            if(actionNumber == i) jui.changeBackgroundColor(color);
            else jui.changeBackgroundColor(Jui.Colors.DEFAULT);
            jui.changeCursorPosition(y, x);
            jui.customPrint(Actions.values()[i].toString(), color);
            y++;
        }

    }

    public static void handleActions() throws IOException {
        Player currentPlayer = game.getCurrentPLayer();
        if (currentPlayer.getActions().contains(Actions.values()[actionNumber])){
            switch (actionNumber){
                case 0:
                    String getCustomDiceRoll = "Enter a valid number between 1 to 6";
                    jui.changeCursorPosition(jui.getRows() - 2, (jui.getColumns() - getCustomDiceRoll.length()) / 2);
                    jui.customPrint(getCustomDiceRoll, Jui.Colors.BOLD_WHITE);
                    jui.changeCursorPosition(0, 0);
                    currentPlayer.diceRoll();
                    if (!game.isChoosingPriorityMode() && !currentPlayer.isInJail())
                        currentPlayer.move(currentPlayer.getDiceRoll());
                    break;
                case 1:
                    String ans;
                    Lands land = game.getLands()[currentPlayer.getCurrentPosition() - 1];
                    while (true){
                        List<String> acceptedAns = new ArrayList<>(Arrays.asList("Y", "N"));
                        ans = askQuestion("Wanna buy here? $" + land.getCost() + " " + acceptedAns);
                        if (acceptedAns.contains(ans)){
                            if (ans.equals("Y")){
                                currentPlayer.buyProperty(land);
                                currentPlayer.setActionsDone(true);
                                break;
                            } else break;
                        }
                    }
                    break;
                case 4:
                    String dest;
                    while (true){
                        List<String> acceptedPlaces = new ArrayList<>(Arrays.asList("3", "11", "20"));
                        acceptedPlaces.remove(String.valueOf(currentPlayer.getCurrentPosition()));
                        dest = askQuestion("Which airport do you wanna go? " + acceptedPlaces);
                        if (acceptedPlaces.contains(dest)) break;
                    }
                    currentPlayer.fly(Integer.parseInt(dest));
                    currentPlayer.setActionsDone(true);
                    break;
                case 7:
                    game.nextTurn();
                    actionNumber=0;
                    if (!game.isChoosingPriorityMode()) currentPlayer.setDiceRoll(-1);
                    currentPlayer.setActionsDone(false);
                    break;
            }
        } else jui.playSound();
    }

    public static String askQuestion(String question) throws IOException {
        String answer = "";
        int letter;

        while (true){
            jui.clearScreen();
            printTable();
            updateHeader();
            updateLeaderboard();
            updateFooter();
            updateActions();

            jui.changeCursorPosition(jui.getRows() - 3, (jui.getColumns() - question.length()) / 2);
            jui.customPrint(question, Jui.Colors.BOLD_YELLOW);

            jui.changeCursorPosition(jui.getRows() - 2, (jui.getColumns() - answer.length()) / 2);
            jui.customPrint(answer,  Jui.Colors.BOLD_WHITE);

            letter = jui.getInput();

            if (letter == 127) {
                if (answer.length() > 0) answer = answer.substring(0, answer.length() - 1);
            }
            else if ((letter >=65 && letter <= 90) || (letter >=97 && letter <= 122) || (letter >=48 && letter <= 57)) answer += (char) letter;
            else if (letter == 13) break;
        }

        return answer;
    }

    public static void updateMessage() throws IOException {
        jui.changeCursorPosition(jui.getRows() - 2, (jui.getColumns() - message.length()) / 2);
        jui.customPrint(message, msgColor);
    }
}
