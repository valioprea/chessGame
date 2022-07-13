import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to my chess game");
        GameLogic gameLogic = new GameLogic();
        Board board = new Board();
        board.initializeGame(gameLogic);
    }
}
