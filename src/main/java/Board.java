import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Board implements Cloneable{
    ListOfPieces pieceImageList = new ListOfPieces();
    public Square[][] allSquares = new Square[9][9];
    public String printedPosition;
    public int squareDimension = 60;
    public boolean alternativeColor = true;

    //MAIN GAME FRAME
    public JFrame gameFrame = new JFrame();

    public void initializeSquares(GameLogic gameLogic) throws IOException {

        int generatedSquare = 1; //will be a number from 1 to 64
        for( int i=1; i<=8; i++){
            alternativeColor = !alternativeColor;
            for ( int j=1; j<=8; j++) {
                this.allSquares[i][j] = new Square(gameLogic);    // INITIALIZING A NEW SQUARE OBJECT !!!
                this.allSquares[i][j].setLayout(new BorderLayout());
                if(alternativeColor == true) {
                        this.allSquares[i][j].setBackground(new Color(120,60,24));
                        alternativeColor = false;
                    } else {
                        this.allSquares[i][j].setBackground(new Color(210,147,88));
                        alternativeColor = true;
                    }
                this.allSquares[i][j].setBounds(j*60,i*60,squareDimension,squareDimension); //set dims and position for each square
                this.allSquares[i][j].setRowPosition(i);
                this.allSquares[i][j].setColumnPosition(j);
                this.printedPosition = i+""+j;

                String generatedSquareString = String.valueOf(generatedSquare);
                JLabel positionLabel = new JLabel(generatedSquareString+"  "+this.printedPosition);
                positionLabel.setHorizontalAlignment(JLabel.CENTER);
                this.allSquares[i][j].add(positionLabel, BorderLayout.SOUTH); //Finalization of generation of squares. Square[][] allSquares;
                gameFrame.add(allSquares[i][j]); //add squares to game frame
                generatedSquare++;
            }
        };
        gameFrame.setTitle("VALI's CHESS GAME");
        gameFrame.setResizable(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLayout(null);
        gameFrame.setSize(600,600);
        gameFrame.setVisible(true);
    }

    public void initializePieces() throws IOException {
        //WHITE PIECES
        this.allSquares[8][1].setPiece(new Rook("rook", new Position(8,1), new ImageIcon(this.pieceImageList.getListOfPieceImages()[4]), "white", true));
        this.allSquares[8][8].setPiece(new Rook("rook", new Position(8,8), new ImageIcon(this.pieceImageList.getListOfPieceImages()[4]), "white", true));
        this.allSquares[8][2].setPiece(new Knight("knight", new Position(8,2), new ImageIcon(this.pieceImageList.getListOfPieceImages()[3]), "white",true));
        this.allSquares[8][7].setPiece(new Knight("knight", new Position(8,7), new ImageIcon(this.pieceImageList.getListOfPieceImages()[3]), "white",true));
        this.allSquares[8][3].setPiece(new Bishop("bishop", new Position(8,3), new ImageIcon(this.pieceImageList.getListOfPieceImages()[2]), "white",true));
        this.allSquares[8][6].setPiece(new Bishop("bishop", new Position(8,6), new ImageIcon(this.pieceImageList.getListOfPieceImages()[2]), "white",true));
        this.allSquares[8][4].setPiece(new Queen("queen", new Position(8,4), new ImageIcon(this.pieceImageList.getListOfPieceImages()[1]), "white", true));
        this.allSquares[8][5].setPiece(new King("king", new Position(8,5), new ImageIcon(this.pieceImageList.getListOfPieceImages()[0]), "white", true));

        //BLACK PIECES
        this.allSquares[1][8].setPiece(new Rook("rook", new Position(1,8), new ImageIcon(this.pieceImageList.getListOfPieceImages()[10]), "black", false));
        this.allSquares[1][1].setPiece(new Rook("rook", new Position(1,1), new ImageIcon(this.pieceImageList.getListOfPieceImages()[10]), "black", false));
        this.allSquares[1][2].setPiece(new Knight("knight", new Position(1,2), new ImageIcon(this.pieceImageList.getListOfPieceImages()[9]), "black", false));
        this.allSquares[1][7].setPiece(new Knight("knight", new Position(1,7), new ImageIcon(this.pieceImageList.getListOfPieceImages()[9]), "black", false));
        this.allSquares[1][3].setPiece(new Bishop("bishop", new Position(1,3), new ImageIcon(this.pieceImageList.getListOfPieceImages()[8]), "black", false));
        this.allSquares[1][6].setPiece(new Bishop("bishop", new Position(1,6), new ImageIcon(this.pieceImageList.getListOfPieceImages()[8]), "black", false));
        this.allSquares[1][4].setPiece(new Queen("queen", new Position(1,4), new ImageIcon(this.pieceImageList.getListOfPieceImages()[7]), "black",false));
        this.allSquares[1][5].setPiece(new King("king", new Position(1,5), new ImageIcon(this.pieceImageList.getListOfPieceImages()[6]), "black", false));

        gameFrame.setVisible(true);
    }

    public Square[][] getAllSquares() {
            return allSquares;
    }

    @Override
    public Board clone() {
        try {
            Board clone = (Board) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

}
