import javax.swing.*;

public class GameLogic {
    JFrame frame;
    Board board;

    public GameLogic(Board board){
        this.board = board;
    }

    String piece;
    Piece objectPiece;
    public Piece getPiece() {
        return objectPiece;
    }

    public void grabPiece(Piece objectPiece) {
        this.objectPiece = objectPiece;
    }

    public void placePiece(char xPosition, int yPosition){
//        this.board.getAllSquares()[4][4].add(this.objectPiece);
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if((board.getAllSquares()[i][j].getxPosition() == xPosition) &&
                        board.getAllSquares()[i][j].getyPosition() == yPosition){
                    this.board.getAllSquares()[i][j].add(this.objectPiece);
                    this.frame.repaint();
                }
            }
        }
    }

    public void setFrame(JFrame gameFrame) {
        this.frame = gameFrame;
    }
}
