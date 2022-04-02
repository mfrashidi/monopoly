package game;

import players.Player;
import utilities.Jui;

import java.io.IOException;
import java.util.Random;

public class Monopoly {
    private static Jui jui;
    private static Player[] players = new Player[0];
    private static int option = 0;
    private static Game game;

    public static void main(String[] args) throws IOException, InterruptedException {
        jui = new Jui();
        printPoster();
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
        jui.clearScreen();
        while (true){
            printTable();
            updateHeader();
            updateFooter();
            jui.getInput();
            break;
        }

    }

    public static void printTable() throws IOException {
        int height = 3;
        int width = 7;
        int x, y, number;
        for (int i = 0; i < 7; i++){
            for (int j = 0;j < 7; j++){
                number = 6 - i + j;
                if (i > 0 && i < 6 && j > 0 && j < 6) continue;
                x = ((jui.getRows() - (height * 7)) / 2) + height * i;
                y = ((jui.getColumns() - (width * 7)) / 2) + width * j;
                if (i > 0 && i < 6 && j == 6) number += 2 * i;
                else if (i == 6 && j > 0) number = 24 - j;
                jui.drawRectangle(height, width, game.getLands()[number].getColor(), x, y);
            }
        }
        jui.changeBackgroundColor(Jui.Colors.RED);
        String monopoly = "MONOPOLY";
        jui.changeCursorPosition((jui.getRows() - 1) / 2, (jui.getColumns() - monopoly.length()) / 2);
        System.out.print(monopoly);
        jui.changeBackgroundColor(Jui.Colors.DEFAULT);
        jui.changeCursorPosition(0, 0);
    }

    public static void updateHeader() throws IOException {
        jui.changeCursorPosition(2, 2);
        jui.changeColor(Jui.Colors.BOLD_GREEN);
        jui.italic();
        System.out.print(game.getCurrentPLayer().getName());
        jui.italic();
        jui.changeColor(Jui.Colors.GREEN);
        System.out.println("'s turn");
        jui.changeColor(Jui.Colors.DEFAULT);
    }

    public static void updateFooter() throws IOException {
        int position = game.getCurrentPLayer().getCurrentPosition() - 1;
        jui.changeCursorPosition(jui.getRows() - 2, 2);
        jui.changeColor(Jui.Colors.BOLD_YELLOW);
        System.out.print("You are at: ");
        jui.changeColor(Jui.Colors.BOLD_MAGENTA);
        jui.italic();
        System.out.print(game.getLands()[position].getName());
        jui.italic();
        jui.changeColor(Jui.Colors.DEFAULT);
    }
}
