import javax.swing.*;
import java.io.IOException;

public class Board {

    public void initializeGame(GameLogic gameLogic) throws IOException {
        //MAIN GAME FRAME
        JFrame gameFrame = new JFrame();
        BoardSquares boardSquares = new BoardSquares();
        boardSquares.generate(gameLogic);

        Square[][] allSquares = boardSquares.getAllSquares();
        PieceInitializer pieceInitializer = new PieceInitializer(allSquares, new ListOfPieces(), gameLogic);
        pieceInitializer.injectImages();
        for( int j=0; j<8; j++){
            for ( int i=0; i<8; i++) {
                gameFrame.add(allSquares[j][i]); //add squares to game frame
            }
        }


        gameFrame.setTitle("VALI's CHESS GAME");
        gameFrame.setResizable(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(null);
        gameFrame.setSize(600,600);
        gameFrame.setVisible(true);




    }
}
