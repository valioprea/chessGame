import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to my chess game");
        System.out.println("White moves first");

        GameLogic gameLogic = new GameLogic();

        gameLogic.generateSquares();
        gameLogic.initializePieces();


    }
}
