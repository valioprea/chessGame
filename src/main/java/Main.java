import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to my chess game");
        System.out.println("White moves first");

        Board board = new Board(); //Physical board

        GameLogic gameLogic = new GameLogic(board);

        board.initializeSquares(gameLogic);
        board.initializePieces();

        gameLogic.setFrame(board.gameFrame);
    }
}
