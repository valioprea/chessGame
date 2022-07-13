import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BoardSquares {

    private Square[][] allSquares = new Square[8][8];
    String printedPosition;

    Piece selectedPiece;

    public void generate(GameLogic gameLogic) throws IOException {
        boolean alternativeColor = true;
        int generatedSquare=1; //will be a number from 1 to 64
        int squareDimension = 60; //in pixels
        int k = 7;
        for( int j=0; j<8; j++){
            if(alternativeColor == true){
                    alternativeColor = false;
                } else {
                    alternativeColor = true;
                }
            for ( int i=0; i<8; i++) {
                this.allSquares[i][j] = new Square(gameLogic);

                allSquares[i][j].setLayout(new BorderLayout());
                if(alternativeColor == true) {
                    this.allSquares[i][j].setBackground(new Color(120,60,24));
                    alternativeColor = false;
                } else {
                    this.allSquares[i][j].setBackground(new Color(210,147,88));
                    alternativeColor = true;
                }
                this.allSquares[i][j].setBounds(i*60,j*60,squareDimension,squareDimension); //set dims and position for each square

                char xPosition = Positions.COLUMNS[i]; //get chess letter
                int yPosition = Positions.ROWS[k]; //get chess number
                allSquares[i][j].setxPosition(Positions.COLUMNS[i]); //sets chess letter for square
                allSquares[i][j].setyPosition(Positions.ROWS[k]); //set chess number for square

                this.printedPosition = Character.toString(xPosition)+Integer.toString(yPosition); //transforms to string to write on board

                String text = String.valueOf(generatedSquare);
                JLabel positionLabel = new JLabel();
                positionLabel.setText(text+"  "+this.printedPosition);
                positionLabel.setHorizontalAlignment(JLabel.CENTER);
                this.allSquares[i][j].add(positionLabel, BorderLayout.SOUTH); //Finalization of generation of squares. Square[][] allSquares;
                generatedSquare++;
            }
            k--;
        };




    }
    public Square[][] getAllSquares() {
        return allSquares;
    }
}
