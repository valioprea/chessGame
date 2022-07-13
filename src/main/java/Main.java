import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to my chess game");

        Board board = new Board();

        GameLogic gameLogic = new GameLogic(board);

        board.initializeSquares(gameLogic);
        board.initializePieces(gameLogic);
        gameLogic.setFrame(board.gameFrame);
    }
}
