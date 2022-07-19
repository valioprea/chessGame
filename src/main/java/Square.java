import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Square extends JPanel {
    private GameLogic gameLogic = new GameLogic();
    private int rowPosition; //(ROW, COLUMN)
    private int columnPosition; //(ROW, COLUMN)

    private Piece piece;

    //CONSTRUCTOR
    public Square(){
        eventHandler();
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public Piece getPiece() {
        return this.piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        add(piece);
    }

    public void eliminatePiece(Piece piece) {
        this.piece = null;
        remove(1);
    }

    @Override
    public String toString() {
        return "Square{" +
                "xPosition=" + rowPosition +
                ", yPosition=" + columnPosition +
                '}';
    }

    public void eventHandler() {
        this.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

                //FIRST SEQUENCE OF CLICK -> you should grab a piece
                if(gameLogic.getSequence() == 1) {

                    //Did I click on a square that contains a piece ?
                    if(getPiece() != null) {

                        //Can the piece be moved ?
                        if( ((Piece) ((Square) e.getComponent()).getComponents()[1]).isCanBeMoved() ){

                            //Let's grab it!
                            gameLogic.grabPiece( ((Piece)((Square)e.getComponent()).getComponents()[1]) );

                        } else {
                            System.out.println("Wait for your turn please");
                        }
                    } else {
                        System.out.println("I was just pressing on the board (from piece)");
                        System.out.println("Sequence will be "+gameLogic.getSequence()+": you need to grab a piece");
                        System.out.println(gameLogic.getGameTurn());
                    }

                } else {
                    //SECOND SEQUENCE OF CLICK (on another square). Let's see what things can we do:
                    gameLogic.whyAmIPressing(getRowPosition(), getColumnPosition());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                gameLogic.ungrabPiece();
            }
            @Override
            public void mouseEntered(MouseEvent e) {
//                e.getComponent().setBackground(Color.RED);
            }
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }
}
