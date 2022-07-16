import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to my chess game");
        System.out.println("White moves first");

        Board board = new Board(); //Physical board
        Board imaginaryBoard = new Board(); //Imaginary board -> used for computing


        GameLogic gameLogic = new GameLogic(board, imaginaryBoard);

        imaginaryBoard.generateImaginarySquares(gameLogic);
        board.initializeSquares(gameLogic);
        board.initializePieces(gameLogic, board.getAllSquares());



        gameLogic.setFrame(board.gameFrame);
    }
}
