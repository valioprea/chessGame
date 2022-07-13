import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Board {
    ListOfPieces pieceImageList = new ListOfPieces();
    public Square[][] allSquares = new Square[8][8];
    public String printedPosition;
    public int squareDimension = 60;
    public boolean alternativeColor = true;

    //MAIN GAME FRAME
    public JFrame gameFrame = new JFrame();

    public void initializeSquares(GameLogic gameLogic) throws IOException {



        int generatedSquare = 1; //will be a number from 1 to 64
        int k = 7;
        for( int j=0; j<8; j++){
            alternativeColor = !alternativeColor;
            for ( int i=0; i<8; i++) {
                this.allSquares[i][j] = new Square(gameLogic);    // INITIALIZING A NEW SQUARE OBJECT !!!
                this.allSquares[i][j].setLayout(new BorderLayout());
                if(alternativeColor == true) {
                        this.allSquares[i][j].setBackground(new Color(120,60,24));
                        alternativeColor = false;
                    } else {
                        this.allSquares[i][j].setBackground(new Color(210,147,88));
                        alternativeColor = true;
                    }
                this.allSquares[i][j].setBounds(i*60,j*60,squareDimension,squareDimension); //set dims and position for each square
                this.allSquares[i][j].setxPosition(Positions.COLUMNS[i]); //sets chess letter for square
                this.allSquares[i][j].setyPosition(Positions.ROWS[k]); //set chess number for square

                this.printedPosition = Positions.COLUMNS[i]+""+Positions.ROWS[k]; //transforms to string to write on board

                String generatedSquareString = String.valueOf(generatedSquare);
                JLabel positionLabel = new JLabel(generatedSquareString+"  "+this.printedPosition);
                positionLabel.setHorizontalAlignment(JLabel.CENTER);
                this.allSquares[i][j].add(positionLabel, BorderLayout.SOUTH); //Finalization of generation of squares. Square[][] allSquares;
                generatedSquare++;
            }
            k--;
        };
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


    public void initializePieces(GameLogic gameLogic, Square[][] allSquares) throws IOException {
        this.allSquares[0][7].setContainsPiece(true);
//        this.allSquares[0][7].setPiece();
        this.allSquares[0][7].add(new Rook("rook", new ImageIcon(this.pieceImageList.getListOfPieceImages()[4]), gameLogic, true, allSquares,0,7));
        this.allSquares[7][7].setContainsPiece(true);
        this.allSquares[7][7].add(new Rook("rook", new ImageIcon(this.pieceImageList.getListOfPieceImages()[4]), gameLogic, true, allSquares,7,7));

        gameFrame.setVisible(true);
    }

    public Square[][] getAllSquares() {
        return allSquares;
    }
}
