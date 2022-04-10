package game;

import lands.EmptyLands;
import lands.Lands;
import lands.LandsWithRent;
import players.Banker;
import players.Player;
import utilities.*;

import java.io.IOException;
import java.lang.Math;
import java.util.*;

public class Game {
    private Jui jui = null;
    private int actionNumber = 0;
    private Player[] players;
    private Banker banker = new Banker();
    private Lands[] lands = new Lands[24];
    private Player currentPLayer;
    private boolean choosingPriorityMode = true;
    private static String message = "Welcome to the Monopoly!";
    private static Jui.Colors msgColor = Jui.Colors.BOLD_YELLOW;
    private static Player winner;

    {
        Player banker = new Player(-1,"banker", Jui.Colors.DEFAULT);
        lands[0]=new Lands(banker,Property.Parking,null, Jui.Colors.BLACK, "Parking", "🅿️");
        lands[1]=new EmptyLands(null,Property.Empty,100, Jui.Colors.GREEN, "Empty Land 1", "🔨");
        lands[2]=new LandsWithRent(banker,Property.Airport,null, Jui.Colors.GRAY, "Airport 1", "✈️");
        lands[3]=new LandsWithRent(null,Property.Cinema,200, Jui.Colors.RED, "Cinema 1", "🎦");
        lands[4]= new Lands(banker,Property.Road,null, Jui.Colors.GRAY, "Road", "🚧");
        lands[5]= new Lands(banker,Property.Award,null, Jui.Colors.GRAY, "Reward Land", "🤑");
        lands[6]=new EmptyLands(null,Property.Empty,100, Jui.Colors.YELLOW, "Empty Land 2", "🔨");
        lands[7]=new LandsWithRent(null,Property.Cinema,200, Jui.Colors.BLUE, "Cinema 2", "🎦");
        lands[8]=new EmptyLands(null,Property.Empty,100, Jui.Colors.RED, "Empty Land 3", "🔨");
        lands[9]= new Lands(banker,Property.Road,null, Jui.Colors.GRAY, "Road", "🚧");
        lands[10]=new LandsWithRent(banker,Property.Airport,null, Jui.Colors.GRAY, "Airport 2", "✈️");
        lands[11]=new EmptyLands(null,Property.Empty,100, Jui.Colors.GREEN, "Empty Land 4", "🔨");
        lands[12]= new Lands(banker,Property.Jail,null, Jui.Colors.GRAY, "Jail", "🛑");
        lands[13]=new EmptyLands(null,Property.Empty,100, Jui.Colors.BLUE, "Empty Land 5", "🔨");
        lands[14]=new LandsWithRent(null,Property.Cinema,200, Jui.Colors.GREEN, "Cinema 2", "🎦");
        lands[15]= new Lands(banker,Property.Road,null, Jui.Colors.GRAY, "Road", "🚧");
        lands[16]= new Lands(banker,Property.Tax,null, Jui.Colors.GRAY, "Tax Land", "💸");
        lands[17]=new EmptyLands(null,Property.Empty,100, Jui.Colors.RED, "Empty Land 6", "🔨");
        lands[18]=new EmptyLands(null,Property.Empty,100, Jui.Colors.YELLOW, "Empty Land 7", "🔨");
        lands[19]=new LandsWithRent(banker,Property.Airport,null, Jui.Colors.GRAY, "Airport 3", "✈️");
        lands[20]= new Lands(banker,Property.Bank,null, Jui.Colors.GRAY, "Bank", "🏦");
        lands[21]=new LandsWithRent(null,Property.Cinema,200, Jui.Colors.YELLOW, "Cinema 3", "🎦");
        lands[22]=new EmptyLands(null,Property.Empty,100, Jui.Colors.BLUE, "Empty Land 8", "🔨");
        lands[23]= new Lands(banker,Property.RandomCard,null, Jui.Colors.GRAY, "Wonderland", "❓");
    }

    public Game(Jui jui, Player... players){
        this.players=players;
        this.currentPLayer= players[0];
        this.jui = jui;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Banker getBanker() {
        return banker;
    }

    public Lands[] getLands() {
        return lands;
    }

    public Player getCurrentPLayer() {
        return currentPLayer;
    }

    public boolean isChoosingPriorityMode() {
        return choosingPriorityMode;
    }

    //next player to play
    public void nextTurn(){
        int index = -1;
        for (int i=0;i<players.length;i++){
            if (players[i].equals(currentPLayer)){
                if (i == players.length - 1) index = 0;
                else index = i + 1;
                break;
            }
        }
        while (true) {
            if (!players[index].isGotBroke()) {
                currentPLayer = players[index];
                break;
            } else {
                index++;
                if (index == players.length) index = 0;
            }
        }
        setPriorities();
    }

    //giving random card
    public Cards giveCard(){
        int a = (int) (Math.random()*(7));
        switch (a){
            case 0: return Cards.PayTenDollarToEveryone;
            case 1: return Cards.DollarGift;
            case 2: return Cards.GoToJail;
            case 3: return Cards.TenPercentPenalty;
            case 4: return Cards.ThreePlaceGoForward;
            case 5: return Cards.TicketToLeaveJail;
            case 6: return Cards.TicketToNotPayTax;
        }
        return null;
    }

    public void setPriorities(){
        if (this.choosingPriorityMode) {
            for (Player player: players)
                if (player.getDiceRoll() == -1) return;
            Player tmpPlayer;
            for (int i = 0; i < players.length - 1; i++) {
                for (int j = 0; j < players.length - i - 1; j++) {
                    if (players[j + 1].getDiceRoll() > players[j].getDiceRoll()){
                        tmpPlayer = players[j + 1];
                        players[j + 1] = players[j];
                        players[j] = tmpPlayer;
                    }
                }
            }
            for (Player player: players) player.setDiceRoll(-1);
            currentPLayer = players[0];
            this.choosingPriorityMode = false;
        }
    }

    public Player getWinner(){
        int nonBrokens = 0;
        Player winner = null;
        for (Player player: players) {
            if (!player.isGotBroke()) {
                winner = player;
                nonBrokens++;
            }
        }
        if (nonBrokens == 1) return winner;
        else return null;
    }

    public void startGame() throws IOException {
        int input;
        while (true){
            jui.clearScreen();
            updateActions();
            if (winner != null){
                players = new Player[0];
                break;
            }
            printTable();
            updateHeader();
            updateLeaderboard();
            updateFooter();
            updateMessage();
            jui.changeCursorPosition(jui.getRows(), jui.getColumns());
            input = jui.getInput();
            if (input == 65){
                for (int i = actionNumber - 1; i >= 0; i--){
                    if (this.getCurrentPLayer().getActions().contains(Actions.values()[i])) {
                        actionNumber = i;
                        break;
                    }
                }
            }
            else if (input == 66){
                for (int i = actionNumber + 1; i < Actions.values().length; i++){
                    if (this.getCurrentPLayer().getActions().contains(Actions.values()[i])) {
                        actionNumber = i;
                        break;
                    }
                }
            }
            else if (input == 13) handleActions();
            else if (input == 127) break;
        }

    }

    public void printTable() throws IOException {
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

                jui.drawRectangle(height, width, this.getLands()[number].getColor(), x, y);

                jui.changeBackgroundColor(Jui.Colors.BLACK);
                jui.changeCursorPosition(y + height - 1, x + width - ((number + 1 < 10) ? 1 : 2));
                jui.customPrint(String.valueOf(number + 1), Jui.Colors.BOLD_WHITE);
                jui.changeBackgroundColor(Jui.Colors.DEFAULT);

                jui.changeBackgroundColor(this.getLands()[number].getColor());
                jui.changeCursorPosition(y + (height / 2), x + (width / 2) - 1);
                System.out.println(this.getLands()[number].getIcon());
                jui.changeBackgroundColor(Jui.Colors.DEFAULT);

                if (number + 1 == this.getCurrentPLayer().getCurrentPosition()){
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
                    jui.changeColor(this.getCurrentPLayer().getColor());
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

        jui.changeColor(Jui.Colors.BOLD_RED);
        String[] lines = title.split("\n");
        y = (jui.getRows() - lines.length) / 2;
        for (int i = 0; i < lines.length; i++){
            jui.changeCursorPosition(y + i, (jui.getColumns() - lines[i].length()) / 2);
            System.out.print(lines[i]);
        }
        jui.changeBackgroundColor(Jui.Colors.DEFAULT);
    }

    public void updateLeaderboard() throws IOException {
        int x = 2;
        int y = ((jui.getRows()) - (this.getPlayers().length + 2)) / 2;
        Player tmpPlayer;
        Player[] sortedPlayers = new Player[this.getPlayers().length];
        System.arraycopy(this.getPlayers(), 0, sortedPlayers, 0, this.getPlayers().length);
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
            if (player.isGotBroke()) jui.strikethrough();
            jui.changeColor(player.getColor());
            System.out.print(player.getName());
            jui.changeColor(((player.getBalance() > 0) ? Jui.Colors.GREEN : Jui.Colors.RED));
            if (!player.isGotBroke()) jui.italic();
            System.out.print(" $" + player.getBalance());
            if (!player.isGotBroke()) jui.italic();
            if (player.isGotBroke()) jui.strikethrough();
            y++;
        }
    }

    public void updateHeader() throws IOException {
        jui.changeCursorPosition(2, 2);
        jui.changeColor(this.getCurrentPLayer().getColor());
        jui.italic();
        System.out.print(this.getCurrentPLayer().getName());
        jui.italic();
        jui.changeColor(this.getCurrentPLayer().getColor());
        System.out.println("'s turn");
        jui.changeColor(Jui.Colors.DEFAULT);
    }

    public void updateFooter() throws IOException {
        int position = this.getCurrentPLayer().getCurrentPosition() - 1;
        jui.changeCursorPosition(jui.getRows() - 2, 2);
        jui.changeColor(Jui.Colors.BOLD_RED);
        System.out.print("📍 Place: ");
        jui.changeColor(Jui.Colors.RED);
        jui.italic();
        System.out.print(this.getLands()[position].getName());
        jui.italic();


        jui.changeCursorPosition(jui.getRows() - 3, 2);
        jui.changeColor(Jui.Colors.BOLD_GREEN);
        System.out.print("💵 Balance: ");
        jui.changeColor(Jui.Colors.GREEN);
        jui.underline();
        System.out.print("$" + this.getCurrentPLayer().getBalance());
        jui.underline();
        jui.changeColor(Jui.Colors.DEFAULT);

        jui.changeCursorPosition(jui.getRows() - 4, 2);
        jui.changeColor(Jui.Colors.BOLD_YELLOW);
        System.out.print("🏠 Properties: ");
        jui.changeColor(Jui.Colors.YELLOW);
        String properties = "";
        for (Lands land: this.getCurrentPLayer().getOwnLands()){
            properties += land.getName();
            if (land instanceof EmptyLands){
                int numberOfStructures = ((EmptyLands) land).getStructures().size();
                if (numberOfStructures == 5) properties += " (Hotel)";
                else properties += " (" + numberOfStructures + " Regular)";
            }
            properties += ", ";
        }
        if (properties.length() == 0) System.out.print("None");
        else System.out.print("[" + properties.substring(0, properties.length() - 2) + "]");
        jui.changeColor(Jui.Colors.DEFAULT);

        jui.changeCursorPosition(jui.getRows() - 5, 2);
        jui.changeColor(Jui.Colors.BOLD_GRAY);
        System.out.print("🎲 Dice: ");
        jui.changeColor(Jui.Colors.GRAY);
        System.out.print((this.getCurrentPLayer().getDiceRoll() != -1) ? this.getCurrentPLayer().getDiceRoll() : "Not rolled");
        jui.changeColor(Jui.Colors.DEFAULT);
    }

    public void updateActions() throws IOException {
        Player currentPlayer = this.getCurrentPLayer();
        List<Actions> actions = new ArrayList<>();
        if (this.isChoosingPriorityMode()){
            if (currentPlayer.getDiceRoll() == -1) actions.add(Actions.RollDice);
            else actions.add(Actions.Next);
        } else {
            if (currentPlayer.getDiceRoll() == -1) actions.add(Actions.RollDice);
            else {
                if (currentPlayer.isActionsDone()){
                    if (currentPlayer.getOwnLands().size() > 0) actions.add(Actions.Sell);
                    actions.add(Actions.Next);
                } else {
                    if (currentPlayer.getOwnLands().size() > 0) actions.add(Actions.Sell);
                    if (currentPlayer.getDiceRoll() == 6 && !currentPlayer.isInJail()) actions.add(Actions.RollDice);
                    Property property = this.getLands()[currentPlayer.getCurrentPosition() - 1].getType();
                    if (property.equals(Property.Parking)) currentPlayer.setActionsDone(true);
                    if (property.equals(Property.Airport)){
                        if (currentPlayer.getBalance() >= 50) actions.add(Actions.Fly);
                        actions.add(Actions.Next);
                    }
                    else if (property.equals(Property.Tax)) {
                        if (currentPlayer.getTaxTicket() > 0){
                            currentPlayer.setTaxTicket(currentPlayer.getTaxTicket() - 1);
                            message = "You used one of your tickets. You have " + currentPlayer.getTaxTicket() + " tickets now!";
                            msgColor = Jui.Colors.BOLD_GREEN;
                        } else currentPlayer.pay((int) (currentPlayer.getBalance() * 0.1));
                        currentPlayer.setActionsDone(true);
                    } else if (property.equals(Property.Road)) {
                        if (currentPlayer.getBalance() >= 100){
                            currentPlayer.pay(100);
                        } else {
                            sellProperties(100);
                        }
                        currentPlayer.setActionsDone(true);
                    } else if (property.equals(Property.Cinema)) {
                        LandsWithRent land = (LandsWithRent) this.getLands()[currentPlayer.getCurrentPosition() - 1];
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
                                sellProperties(land.getRent());
                            }
                        }
                    } else if (property.equals(Property.Award)){
                        currentPlayer.getPaid(200);
                        currentPlayer.setActionsDone(true);
                    } else if (property.equals(Property.Empty)){
                        EmptyLands land = (EmptyLands) this.getLands()[currentPlayer.getCurrentPosition() - 1];
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
                                sellProperties(land.getRent());
                            }
                        }
                    } else if (property.equals(Property.Jail)){
                        if (currentPlayer.getDiceRoll() == 1){
                            if (currentPlayer.isInJail()){
                                currentPlayer.setInJail(false);
                                actions.add(Actions.RollDice);
                            } else actions.add(Actions.Next);
                        } else {
                            if (currentPlayer.isInJail()) actions.add(Actions.Free);
                            actions.add(Actions.Next);
                        }
                    } else if (property.equals(Property.Bank)){
                        if (currentPlayer.isMoneyDeposited()){
                            this.getBanker().checkDeposit(currentPlayer);
                            message = "You got your money with its profit. Go get ice cream for yourself!";
                            msgColor = Jui.Colors.BOLD_GREEN;
                        }
                        actions.add(Actions.Invest);
                        actions.add(Actions.Next);
                    } else if (property.equals(Property.RandomCard)){
                        Cards card = this.giveCard();
                        if (card.equals(Cards.DollarGift)){
                            currentPlayer.getPaid(200);
                            message = "You won a $200 ticket. Have Fun!";
                            msgColor = Jui.Colors.BOLD_GREEN;
                        } else if (card.equals(Cards.GoToJail)) {
                            currentPlayer.setInJail(true);
                            currentPlayer.setCurrentPosition(13);
                            message = "Welcome to the jail, my amigo. May you'll learn a few things.";
                            msgColor = Jui.Colors.BOLD_YELLOW;
                        } else if (card.equals(Cards.TenPercentPenalty)) {
                            currentPlayer.pay((int) (currentPlayer.getBalance() * 0.1));
                            message = "Bank wants 10 percent of your money man";
                            msgColor = Jui.Colors.BOLD_RED;
                        } else if (card.equals(Cards.ThreePlaceGoForward)) {
                            currentPlayer.setCurrentPosition(3);
                            message = "How do you get here?";
                            msgColor = Jui.Colors.BOLD_YELLOW;
                        } else if (card.equals(Cards.TicketToLeaveJail)) {
                            currentPlayer.setJailTicket(currentPlayer.getJailTicket() + 1);
                            message = "Who doesn't want free bail?";
                            msgColor = Jui.Colors.BOLD_GREEN;
                        } else if (card.equals(Cards.TicketToNotPayTax)) {
                            currentPlayer.setTaxTicket(currentPlayer.getTaxTicket() + 1);
                            message = "You got a free tax ticket";
                            msgColor = Jui.Colors.BOLD_GREEN;
                        } else {
                            if (currentPlayer.getBalance() >= this.getPlayers().length * 10) {
                                for (Player player: this.getPlayers()){
                                    if (!player.equals(currentPlayer)){
                                        player.getPaid(10);
                                        currentPlayer.pay(10);
                                    }
                                }
                                message = "You pay each player 10 bucks. Sorry!";
                                msgColor = Jui.Colors.BOLD_RED;
                            } else {
                                sellProperties(this.getPlayers().length * 10);
                            }
                        }
                        currentPlayer.setActionsDone(true);
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

    public void handleActions() throws IOException {
        Player currentPlayer = this.getCurrentPLayer();
        if (currentPlayer.getActions().contains(Actions.values()[actionNumber])){
            String ans;
            switch (actionNumber){
                case 0:
                    String getCustomDiceRoll = "Enter a valid number between 1 to 6";
                    jui.changeCursorPosition(jui.getRows() - 2, (jui.getColumns() - getCustomDiceRoll.length()) / 2);
                    jui.customPrint(getCustomDiceRoll, Jui.Colors.BOLD_WHITE);
                    jui.changeCursorPosition(0, 0);
                    currentPlayer.diceRoll();
                    if (!this.isChoosingPriorityMode() && !currentPlayer.isInJail())
                        currentPlayer.move(currentPlayer.getDiceRoll());
                    if (currentPlayer.isInJail()){
                        if (currentPlayer.getDaysInJail() == 5){
                            message = "You escaped from the jail. Enjoy your freedom!";
                            msgColor = Jui.Colors.BOLD_GREEN;
                            currentPlayer.setDaysInJail(0);
                            currentPlayer.setInJail(false);
                            currentPlayer.move(currentPlayer.getDiceRoll());
                        } else {
                            if (currentPlayer.getDaysInJail() > 0) message = "Another day at the jail. Another 10 bucks :(";
                            else message = "Welcome to the jail, my amigo. May you'll learn a few things.";
                            msgColor = Jui.Colors.BOLD_YELLOW;
                            currentPlayer.setInJail(true);
                        }
                    }
                    break;
                case 1:
                    Lands land = this.getLands()[currentPlayer.getCurrentPosition() - 1];
                    while (true){
                        List<String> acceptedAns = new ArrayList<>(Arrays.asList("Y", "N"));
                        ans = askQuestion("Wanna buy here? $" + land.getCost() + " " + acceptedAns);
                        if (acceptedAns.contains(ans)){
                            if (ans.equals("Y")){
                                message = "You bought " + land.getName() + ". Take your keys!";
                                msgColor = Jui.Colors.BOLD_YELLOW;
                                currentPlayer.buyProperty(land);
                                currentPlayer.setActionsDone(true);
                                break;
                            } else break;
                        }
                    }
                    break;
                case 2:
                    EmptyLands emptyLand = (EmptyLands) this.getLands()[currentPlayer.getCurrentPosition() - 1];
                    while (true){
                        List<String> acceptedAns = new ArrayList<>(Arrays.asList("Regular", "Hotel", "Exit"));
                        ans = askQuestion("What kind of building do you want? (Regular: $150, Hotel: $100) " + acceptedAns);
                        if (acceptedAns.contains(ans)){
                            if (ans.equals("Regular")){
                                int minBuildings = -1;
                                Vector<Lands> playerLands = currentPlayer.getOwnLands();
                                for (Lands ownLand: playerLands) {
                                    if (ownLand instanceof EmptyLands) {
                                        if (minBuildings == -1){
                                            minBuildings = ((EmptyLands) ownLand).getStructures().size();
                                            continue;
                                        }
                                        if (((EmptyLands) ownLand).getStructures().size() < minBuildings)
                                            minBuildings = ((EmptyLands) ownLand).getStructures().size();
                                    }
                                }
                                if (emptyLand.getStructures().size() == minBuildings){
                                    if (emptyLand.getStructures().size() > 3){
                                        message = "You have enough regular buildings. Go for a hotel!";
                                        msgColor = Jui.Colors.BOLD_YELLOW;
                                    } else {
                                        if (currentPlayer.getBalance() >= 150){
                                            emptyLand.getStructures().add(Structures.Buildings);
                                            currentPlayer.pay(150);
                                            emptyLand.setRent(emptyLand.getRent() + 100);
                                            message = "Another building in your property my friend.";
                                            msgColor = Jui.Colors.BOLD_YELLOW;
                                            currentPlayer.setActionsDone(true);
                                            break;
                                        } else {
                                            message = "You don't have enough money :(";
                                            msgColor = Jui.Colors.BOLD_RED;
                                        }
                                    }
                                } else {
                                    message = "All of your properties should have " + (minBuildings + 1) + " buildings";
                                    msgColor = Jui.Colors.BOLD_RED;
                                }
                            } else if (ans.equals("Hotel")){
                                boolean canBuild = true;
                                Vector<Lands> playerLands = currentPlayer.getOwnLands();
                                for (Lands ownLand: playerLands) {
                                    if (ownLand instanceof EmptyLands) {
                                        if (((EmptyLands) ownLand).getStructures().size() < 4) {
                                            canBuild = false;
                                            break;
                                        }
                                    }
                                }
                                if (canBuild) {
                                    if (currentPlayer.getBalance() >= 100){
                                        emptyLand.getStructures().add(Structures.Buildings);
                                        currentPlayer.pay(100);
                                        emptyLand.setRent(600);
                                        message = "You have a hotel in this area. Noice!";
                                        msgColor = Jui.Colors.BOLD_YELLOW;
                                        currentPlayer.setActionsDone(true);
                                        break;
                                    } else {
                                        message = "You don't have enough money :(";
                                        msgColor = Jui.Colors.BOLD_RED;
                                    }

                                } else {
                                    message = "All of your properties should have 4 buildings";
                                    msgColor = Jui.Colors.BOLD_RED;
                                }

                            } else break;
                        }
                    }
                    break;
                case 3:
                    sellProperties();
                    break;
                case 4:
                    while (true){
                        List<String> acceptedAns = new ArrayList<>(Arrays.asList("3", "11", "20", "Exit"));
                        acceptedAns.remove(String.valueOf(currentPlayer.getCurrentPosition()));
                        ans = askQuestion("Which airport do you wanna go? ($50) " + acceptedAns);
                        if (acceptedAns.contains(ans)){
                            if (ans.equals("Exit")) break;
                            else {
                                message = "Passengers, we are landing at Airport " + ans + ". Fasten your seat belt, please!";
                                msgColor = Jui.Colors.BOLD_CYAN;
                                currentPlayer.fly(Integer.parseInt(ans));
                                currentPlayer.setActionsDone(true);
                                break;
                            }
                        } else {
                            message = "Please enter a valid airport";
                            msgColor = Jui.Colors.BOLD_RED;
                        }
                    }
                    break;
                case 5:
                    while (true){
                        List<String> acceptedAns = new ArrayList<>(Arrays.asList("Money", "Card", "Exit"));
                        ans = askQuestion("How do you wanna get out of here? " + acceptedAns);
                        if (acceptedAns.contains(ans)){
                            if (ans.equals("Money")){
                                if (currentPlayer.getBalance() >= 50){
                                    if (currentPlayer.getDaysInJail() > 0 ) currentPlayer.move(currentPlayer.getDiceRoll());
                                    currentPlayer.pay(50);
                                    currentPlayer.setDaysInJail(0);
                                    currentPlayer.setInJail(false);
                                    currentPlayer.setActionsDone(true);
                                    message = "You spent 50 bucks. Go explore the world!";
                                    msgColor = Jui.Colors.BOLD_BLUE;
                                    break;
                                } else {
                                    message = "You don't have enough money";
                                    msgColor = Jui.Colors.BOLD_RED;
                                }
                            } if (ans.equals("Card")) {
                                if (currentPlayer.getJailTicket() >= 1){
                                    if (currentPlayer.getDaysInJail() > 0 ) currentPlayer.move(currentPlayer.getDiceRoll());
                                    currentPlayer.setJailTicket(currentPlayer.getJailTicket() - 1);
                                    currentPlayer.setDaysInJail(0);
                                    currentPlayer.setInJail(false);
                                    currentPlayer.setActionsDone(true);
                                    message = "You used your jail ticket. Go explore the world!";
                                    msgColor = Jui.Colors.BOLD_BLUE;
                                    break;
                                } else {
                                    message = "You don't have enough card";
                                    msgColor = Jui.Colors.BOLD_RED;
                                }
                            } else break;
                        }
                    }
                    break;
                case 6:
                    while (true){
                        List<String> acceptedAns = new ArrayList<>(Arrays.asList("Invest", "Exit"));
                        ans = askQuestion("Do you want to invest half of your money ($" + (currentPlayer.getBalance() / 2) + ") in the bank? " + acceptedAns);
                        if (acceptedAns.contains(ans)){
                            if (ans.equals("Invest")){
                                this.getBanker().addDeposit(currentPlayer);
                                message = "Your money is safe with us!";
                                msgColor = Jui.Colors.BOLD_BLUE;
                                currentPlayer.setActionsDone(true);
                            }
                            break;
                        }
                    }
                    break;
                case 7:
                    message = " ";
                    if (currentPlayer.isInJail()) {
                        if (currentPlayer.getBalance() >= 10){
                            currentPlayer.pay(10);
                            currentPlayer.setDaysInJail(currentPlayer.getDaysInJail() + 1);
                        } else {
                            sellProperties(10);
                        }
                    }
                    Player tmpWinner = this.getWinner();
                    if (tmpWinner == null){
                        this.nextTurn();
                        actionNumber = 0;
                        if (!this.isChoosingPriorityMode()) currentPlayer.setDiceRoll(-1);
                        currentPlayer.setActionsDone(false);
                    } else {
                        winner = tmpWinner;
                        jui.clearScreen();
                        jui.customPrint(winner.getName() + " won the game!", Jui.Position.CENTER, Jui.Colors.BOLD_GREEN);
                        String exitMsg = "Press any button to exit";
                        jui.changeCursorPosition(jui.getRows() - 2, (jui.getColumns() - exitMsg.length()) / 2);
                        System.out.println(exitMsg);
                        jui.changeCursorPosition(0, 0);
                        jui.getInput();
                    }
                    break;
            }
        } else jui.playSound();
    }

    public String askQuestion(String question) throws IOException {
        String answer = "";
        int letter;

        while (true){
            jui.clearScreen();
            printTable();
            updateHeader();
            updateLeaderboard();
            updateFooter();
            updateMessage();

            jui.changeCursorPosition(jui.getRows() - 3, (jui.getColumns() - question.length()) / 2);
            jui.customPrint(question, Jui.Colors.BOLD_YELLOW);

            jui.changeCursorPosition(jui.getRows() - 2, (jui.getColumns() - answer.length()) / 2);
            jui.customPrint(answer,  Jui.Colors.BOLD_WHITE);

            letter = jui.getInput();

            if (letter == 127) {
                if (answer.length() > 0) answer = answer.substring(0, answer.length() - 1);
            }
            else if ((letter >=65 && letter <= 90) || (letter >=97 && letter <= 122) || (letter >=48 && letter <= 57) || letter == 32) answer += (char) letter;
            else if (letter == 13) break;
        }

        return answer;
    }

    public void updateMessage() throws IOException {
        jui.changeCursorPosition(2, (jui.getColumns() - message.length()) / 2);
        jui.customPrint(message, msgColor);
    }

    public void sellProperties() throws IOException {
        Player currentPlayer = this.getCurrentPLayer();
        String ans;
        TreeMap<String,Integer> properties = new TreeMap<>();
        for (Lands land: currentPlayer.getOwnLands()) {
            if (land instanceof LandsWithRent) {
                LandsWithRent landsWithRent = (LandsWithRent) land;
                if (land instanceof EmptyLands){
                    EmptyLands emptyLand = (EmptyLands) landsWithRent;
                    int numberOfStructures = emptyLand.getStructures().size();
                    properties.put(land.getName(), 50 + ((numberOfStructures < 5) ? numberOfStructures * 75 : 350));
                } else {
                    properties.put(land.getName(), 100);
                }
            }
        }
        while (true) {
            List<String> acceptedAns = new ArrayList<>();
            String propertiesWithValues = "[";
            for (Map.Entry<String,Integer> entry : properties.entrySet()) {
                propertiesWithValues += entry.getKey() + "=$" + entry.getValue() + ", ";
                acceptedAns.add(entry.getKey());
            }
            acceptedAns.add("Exit");
            propertiesWithValues = propertiesWithValues.substring(0, propertiesWithValues.length() - 2) + ", Exit]";
            message = propertiesWithValues;
            msgColor = Jui.Colors.BOLD_YELLOW;
            ans = askQuestion("Which property do you wanna sell? (Just type the name of it)");
            if (acceptedAns.contains(ans)){
                if (ans.equals("Exit")){
                    message = "";
                    break;
                }
                int value = properties.get(ans);
                currentPlayer.getPaid(value);
                Lands land = null;
                for (Lands gameLand: this.getLands()) {
                    if (gameLand.getName().equals(ans)) {
                        land = gameLand;
                        break;
                    }
                }
                assert land != null;
                currentPlayer.sell(land);
                message = "You sold " + ans + " with the value of " + value;
                msgColor = Jui.Colors.BOLD_YELLOW;
                break;
            }
        }
    }

    public void sellProperties(int amount) throws IOException {
        int sold = 0;
        Player currentPlayer = this.getCurrentPLayer();
        String ans;
        TreeMap<String,Integer> properties = new TreeMap<>();
        for (Lands land: currentPlayer.getOwnLands()) {
            if (land instanceof LandsWithRent landsWithRent) {
                if (land instanceof EmptyLands){
                    EmptyLands emptyLand = (EmptyLands) landsWithRent;
                    int numberOfStructures = emptyLand.getStructures().size();
                    properties.put(land.getName(), 50 + ((numberOfStructures < 5) ? numberOfStructures * 75 : 350));
                } else {
                    properties.put(land.getName(), 100);
                }
            }
        }
        while (sold < amount) {
            amount = amount - currentPlayer.getBalance();
            List<String> acceptedAns = new ArrayList<>();
            String propertiesWithValues = "";
            if (properties.size() > 0) {
                propertiesWithValues += "[";
                for (Map.Entry<String,Integer> entry : properties.entrySet()) {
                    propertiesWithValues += entry.getKey() + "=$" + entry.getValue() + ", ";
                    acceptedAns.add(entry.getKey());
                }
                propertiesWithValues = propertiesWithValues.substring(0, propertiesWithValues.length() - 2) + "]";
            }

            message = "You are at a bad position man. You need $" + amount + " to save yourself. If you can't afford it just type 'Broken' and say goodbye!";
            msgColor = Jui.Colors.BOLD_YELLOW;
            acceptedAns.add("Broken");

            ans = askQuestion("Which property do you wanna sell? " + propertiesWithValues);
            if (acceptedAns.contains(ans)){
                if (ans.equals("Exit")){
                    message = "You saved yourself but, at what cost?";
                    msgColor = Jui.Colors.BOLD_GREEN;
                    break;
                } else if (ans.equals("Broken")) {
                    for (Lands land: currentPlayer.getOwnLands())
                        currentPlayer.sell(land);
                    currentPlayer.pay(currentPlayer.getBalance());
                    currentPlayer.setGotBroke(true);
                    currentPlayer.setActionsDone(true);
                    message = "We had good times, " + currentPlayer.getName() + ". Rest in peace!";
                    msgColor = Jui.Colors.BOLD_RED;
                    break;
                } else {
                    int value = properties.get(ans);
                    currentPlayer.pay(value);
                    sold += value;
                    Lands land = null;
                    for (Lands gameLand: this.getLands()) {
                        if (gameLand.getName().equals(ans)) {
                            land = gameLand;
                            break;
                        }
                    }
                    assert land != null;
                    currentPlayer.sell(land);
                    message = "You sold " + ans + " with the value of $" + value;
                    msgColor = Jui.Colors.BOLD_RED;
                }
            }
        }
    }

}
