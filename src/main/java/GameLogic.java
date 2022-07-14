import javax.swing.*;

public class GameLogic {
    public JFrame frame;
    public Board board;
    public Piece selectedPiece;
    public int sequence = 1;

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public int getSequence() {
        return sequence;
    }

    public GameLogic(Board board){
        this.board = board;
    }


    public Piece getPiece() {
        return selectedPiece;
    }

    public void grabPiece(Piece objectPiece) {
        this.selectedPiece = objectPiece;
        this.setSequence(2);
        System.out.println("Na de aici coordonatele piesei cand o grabuiesc");
        System.out.println("PE Y "+ selectedPiece.getSquaresArrayRowPosition());
        System.out.println("PE X "+ selectedPiece.getSquaresArrayColumnPosition()); //TODO: cand placePiece, ar trebui sa ii setez si noile coordonate
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

//                    this.board.getAllSquares()[i][j].getxPosition()

                    ((Piece)((Square)this.board.getAllSquares()[i][j]).getComponents()[1]).setSquaresArrayRowPosition(j);
                    ((Piece)((Square)this.board.getAllSquares()[i][j]).getComponents()[1]).setSquaresArrayColumnPosition(i);

                    this.board.getAllSquares()[i][j].setContainsPiece(true);
                    this.frame.repaint();
                    this.board.getAllSquares()[selectedPiece.getSquaresArrayColumnPosition()][selectedPiece.getSquaresArrayRowPosition()].setContainsPiece(false); //TODO: Asta cred ca seteaza fals doar ptr pozitia initiala

                    System.out.println(board.getAllSquares()[7][7].getComponents().length);
                    System.out.println(board.getAllSquares()[0][7].getComponents().length);


                    this.selectedPiece = null;
                    this.setSequence(1);
                    System.out.println("Sequence will be: "+sequence);
                }
            }
        }
    }

    public void setFrame(JFrame gameFrame) {
        this.frame = gameFrame;
    }

    public void whyAmIPressing(char xPosition, int yPosition){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){

                //FIND PRESSED SQUARE IN SQUARE ARRAY
                if((board.getAllSquares()[i][j].getxPosition() == xPosition) &&   //Identify the board square that was pressed
                        board.getAllSquares()[i][j].getyPosition() == yPosition){

                    //DO I HAVE A PIECE IN MY HAND?
                    if(this.selectedPiece != null){

                        //IS THE SQUARE THAT I HAVE CHOSEN EMPTY?
                        if(board.getAllSquares()[i][j].getContainsPiece() == false){
                            placePiece(xPosition, yPosition); //if empty, I will place my piece here
                            System.out.println("Sequence will be: "+sequence);
                        } else {

                            //SO THE SQUARE IS NOT EMPTY. DOES THE COLOR OF THE PIECE ON THIS SQUARE MATCH THE ONE THAT I HAVE IN MY HAND ?
                            if( ((Piece)board.getAllSquares()[i][j].getComponents()[1]).isWhite() == this.selectedPiece.isWhite() ){
                                System.out.println("I can't place a piece of my own over another piece of my own");
                                System.out.println("I need to reselect my piece");
                                this.setSequence(1);
                                System.out.println("Sequence will be: "+sequence);
                            } else {
                                System.out.println("I will capture here -> I need to write the logic for that");
                                placePiece(xPosition, yPosition);
                                this.setSequence(1);
                                System.out.println("Sequence will be: "+sequence);
                            }

                        }

                    } else {
                        System.out.println("I was just pressing on the board (from game logic)");
                        this.setSequence(1);
                        System.out.println("Sequence will be: "+sequence);
                    }

                }
            }
        }
    }

//    public void whyAmIPressing(char xPosition, int yPosition){
//        System.out.println("ba aici printez amus");
//        System.out.println(
//                board.getAllSquares()[7][7].getComponents().length
//        );
//        for(int i=0;i<8;i++){
//            for(int j=0;j<8;j++){
//                System.out.println("La pozitia COL i "+i+" ROW j "+j +" avem "+board.getAllSquares()[i][j].getComponents().length +" elemente inauntru");
//            }
//        }
//        System.out.println(board.getAllSquares()[0][7].getComponents()[0]); //asta e labelul
//        System.out.println(board.getAllSquares()[0][7].getComponents()[1]); //asta e Rook - obiectul
//        System.out.println(
//                ((Piece)board.getAllSquares()[0][7].getComponents()[1]).isWhite()
//        );
//    }
}
