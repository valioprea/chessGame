import javax.swing.*;

public class GameLogic {
    JFrame frame;
    Board board;

    public GameLogic(Board board){
        this.board = board;
    }

    public Piece selectedPiece;
    public Piece getPiece() {
        return selectedPiece;
    }

    public void grabPiece(Piece objectPiece) {
        this.selectedPiece = objectPiece;
    }

    public void ungrabPiece(){
        this.selectedPiece = null;
    }

    public void placePiece(char xPosition, int yPosition){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if((board.getAllSquares()[i][j].getxPosition() == xPosition) &&
                        board.getAllSquares()[i][j].getyPosition() == yPosition){
                    this.board.getAllSquares()[i][j].add(this.selectedPiece); //TODO: why is the initial piece leaving the initial square? I did not implement any logic for that
                    this.board.getAllSquares()[i][j].setContainsPiece(true);
                    this.frame.repaint();
                    this.board.getAllSquares()[selectedPiece.getSquaresArrayColumnPosition()][selectedPiece.getSquaresArrayRowPosition()].setContainsPiece(false);
                    this.selectedPiece = null;
                }
            }
        }
    }

    public void setFrame(JFrame gameFrame) {
        this.frame = gameFrame;
    }

//    public void whyAmIPressing(char xPosition, int yPosition){
//        for(int i=0;i<8;i++){
//            for(int j=0;j<8;j++){
//                if((board.getAllSquares()[i][j].getxPosition() == xPosition) &&
//                        board.getAllSquares()[i][j].getyPosition() == yPosition){
//                    if(board.getAllSquares()[i][j].getContainsPiece() == false && this.selectedPiece == null){
//                        System.out.println("I am just pressing on the board");
//                    } else if (board.getAllSquares()[i][j].getContainsPiece() == true
//                            && this.selectedPiece != null
//                            && this.selectedPiece.isWhite() == board.getAllSquares()[i][j].getComponents()[0]  ){ //TODO: I need to find a way to put my hands on the piece that is lying on that specific square, not selected
//                        placePiece(xPosition, yPosition);
//                    }
//                }
//            }
//        }
//    }

    public void whyAmIPressing(char xPosition, int yPosition){
        System.out.println("ba aici printez amus");
        System.out.println(
                board.getAllSquares()[7][7].getComponents().length //TODO: Doar de curiozitate -> de ce primesc 2 chiar si cand apas pe un square care contine un rook ?
        );
    }
}
