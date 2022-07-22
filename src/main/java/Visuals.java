import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Visuals extends JFrame{

    GameLogic gameLogic = new GameLogic();

    ListOfPieces pieceImageList = new ListOfPieces();

    VisualSquare[][] allVisualSquares = new VisualSquare[9][9];
    public int squareDimension = 60;

    public VisualPiece selectedVisualPiece;

    public VisualPiece getSelectedVisualPiece() {
        return selectedVisualPiece;
    }

    public void setSelectedVisualPiece(VisualPiece selectedVisualPiece) {
        this.selectedVisualPiece = selectedVisualPiece;
    }

    public Visuals(){
        this.setTitle("VALI's CHESS GAME");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(600,600);
        this.setVisible(true);
//        this.addMouseListener(this);
    }

    public void generateVisualSquares() {
        boolean alternativeColor = true;
        String printedPosition;
        int generatedSquare = 1; //will be a number from 1 to 64
        for( int i=1; i<=8; i++){
            alternativeColor = !alternativeColor;
            for ( int j=1; j<=8; j++) {
                this.allVisualSquares[i][j] = new VisualSquare();    // INITIALIZING A NEW SQUARE OBJECT !!!
                this.allVisualSquares[i][j].setLayout(new BorderLayout());
                if(alternativeColor == true) {
                    this.allVisualSquares[i][j].setBackground(new Color(120,60,24));
                    alternativeColor = false;
                } else {
                    this.allVisualSquares[i][j].setBackground(new Color(210,147,88));
                    alternativeColor = true;
                }
                this.allVisualSquares[i][j].setBounds(j*60,i*60,squareDimension,squareDimension); //set dims and position for each square
                this.allVisualSquares[i][j].setVisualSquarePosition(new Position(i,j));

                printedPosition = i+""+j;

                String generatedSquareString = String.valueOf(generatedSquare);
                JLabel positionLabel = new JLabel(generatedSquareString+"  "+printedPosition);
                positionLabel.setHorizontalAlignment(JLabel.CENTER);
                this.allVisualSquares[i][j].add(positionLabel, BorderLayout.SOUTH); //Finalization of generation of squares. Square[][] allSquares;
                this.add(allVisualSquares[i][j]); //add squares to game frame
                generatedSquare++;
            }
        };
        gameLogic.generateSquares();
        gameLogic.initializePieces();
    }

    public VisualSquare[][] getAllVisualSquares() {
        return allVisualSquares;
    }

    public void drawInitial() throws IOException {

        this.allVisualSquares[8][1].setVisualPiece(new VisualPiece("rook", new Position(8,1), new ImageIcon(this.pieceImageList.getListOfPieceImages()[4]), "white"));



//        //WHITE PIECES
//        this.allVisualSquares[8][1].setPiece(new Rook("rook", new Position(8,1), new ImageIcon(this.pieceImageList.getListOfPieceImages()[4]), "white", true));
//        this.allVisualSquares[8][8].setPiece(new Rook("rook", new Position(8,8), new ImageIcon(this.pieceImageList.getListOfPieceImages()[4]), "white", true));
//        this.allVisualSquares[8][2].setPiece(new Knight("knight", new Position(8,2), new ImageIcon(this.pieceImageList.getListOfPieceImages()[3]), "white",true));
//        this.allVisualSquares[8][7].setPiece(new Knight("knight", new Position(8,7), new ImageIcon(this.pieceImageList.getListOfPieceImages()[3]), "white",true));
//        this.allVisualSquares[8][3].setPiece(new Bishop("bishop", new Position(8,3), new ImageIcon(this.pieceImageList.getListOfPieceImages()[2]), "white",true));
//        this.allVisualSquares[8][6].setPiece(new Bishop("bishop", new Position(8,6), new ImageIcon(this.pieceImageList.getListOfPieceImages()[2]), "white",true));
//        this.allVisualSquares[8][4].setPiece(new Queen("queen", new Position(8,4), new ImageIcon(this.pieceImageList.getListOfPieceImages()[1]), "white", true));
//        this.allVisualSquares[8][5].setPiece(new King("king", new Position(8,5), new ImageIcon(this.pieceImageList.getListOfPieceImages()[0]), "white", true));
//
//        //BLACK PIECES
//        this.allVisualSquares[1][8].setPiece(new Rook("rook", new Position(1,8), new ImageIcon(this.pieceImageList.getListOfPieceImages()[10]), "black", false));
//        this.allVisualSquares[1][1].setPiece(new Rook("rook", new Position(1,1), new ImageIcon(this.pieceImageList.getListOfPieceImages()[10]), "black", false));
//        this.allVisualSquares[1][2].setPiece(new Knight("knight", new Position(1,2), new ImageIcon(this.pieceImageList.getListOfPieceImages()[9]), "black", false));
//        this.allVisualSquares[1][7].setPiece(new Knight("knight", new Position(1,7), new ImageIcon(this.pieceImageList.getListOfPieceImages()[9]), "black", false));
//        this.allVisualSquares[1][3].setPiece(new Bishop("bishop", new Position(1,3), new ImageIcon(this.pieceImageList.getListOfPieceImages()[8]), "black", false));
//        this.allVisualSquares[1][6].setPiece(new Bishop("bishop", new Position(1,6), new ImageIcon(this.pieceImageList.getListOfPieceImages()[8]), "black", false));
//        this.allVisualSquares[1][4].setPiece(new Queen("queen", new Position(1,4), new ImageIcon(this.pieceImageList.getListOfPieceImages()[7]), "black",false));
//        this.allVisualSquares[1][5].setPiece(new King("king", new Position(1,5), new ImageIcon(this.pieceImageList.getListOfPieceImages()[6]), "black", false));

    }

    public ArrayList<InfoTransfer> getInfoFromGameLogic(){
        return gameLogic.transferInformation();
    }

    //MOUSE THINGS

    public void eventVali(JFrame currentFrame){
        //Dynamic movements
        currentFrame.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if(selectedVisualPiece != null){

                    selectedVisualPiece.setX( e.getY() );
                    selectedVisualPiece.setY( e.getX() );

                    currentFrame.repaint();
                }

//                currentFrame.validate();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });

        //Static things
        currentFrame.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                System.out.println("clicked bro yes marfa");

//                Component frame = e.getComponent();

                System.out.println( e.getX()+" "+e.getY() ); //asta e poz unde dau click cu mouseul
//
//                System.out.println( allVisualSquares[1][1].getX() );
//                System.out.println( allVisualSquares[1][1].getY() );


                for (int i = 1; i <= 8 ; i++) {
                    for (int j = 1; j <= 8 ; j++) {

                        if (    allVisualSquares[i][j].getX()+5 <= e.getX() &&
                                allVisualSquares[i][j].getX()+55 >= e.getX() &&
                                allVisualSquares[i][j].getY()+5 <= e.getY()-30 &&
                                allVisualSquares[i][j].getY()+55 >= e.getY()-30
                        ) {
                            System.out.println("Am apasat pe -> "+ allVisualSquares[i][j] );
                            selectedVisualPiece = allVisualSquares[i][j].getVisualPiece();
                        }

                    }
                }
                currentFrame.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {


                for (int i = 1; i <= 8 ; i++) {
                    for (int j = 1; j <= 8 ; j++) {

                        if (    allVisualSquares[i][j].getX()+5 <= e.getX() &&
                                allVisualSquares[i][j].getX()+55 >= e.getX() &&
                                allVisualSquares[i][j].getY()+5 <= e.getY()-30 &&
                                allVisualSquares[i][j].getY()+55 >= e.getY()-30
                        ) {
                            allVisualSquares[i][j].setVisualPiece( selectedVisualPiece);
                        }

                    }
                }
                System.out.println("E piesa la final?"+ allVisualSquares[1][1].getVisualPiece() );
                currentFrame.repaint();
                System.out.println("E piesa la initial ?"+ allVisualSquares[8][1].getVisualPiece() );
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });


//        currentFrame.setDefaultCloseOperation(3);
//        currentFrame.setVisible(true);
    }


}
