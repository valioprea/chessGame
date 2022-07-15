import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Square extends JPanel {

    private GameLogic gameLogic;

    public int rowPosition; //(ROW, COLUMN)
    public int columnPosition; //(ROW, COLUMN)

    private boolean containsPiece=false;

    public void setRowPosition(int rowPosition) {
        this.rowPosition = rowPosition;
    }

    public void setColumnPosition(int columnPosition) {
        this.columnPosition = columnPosition;
    }

    public boolean getContainsPiece() {
        return containsPiece;
    }

    public void setContainsPiece(boolean containsPiece) {
        this.containsPiece = containsPiece;
    }

    public int getRowPosition() {
        return rowPosition;
    }

    public int getColumnPosition() {
        return columnPosition;
    }

    public Square(GameLogic gameLogic){
        this.gameLogic = gameLogic;
        eventHandler();
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
                if(gameLogic.getSequence() == 1) {

                    if(getContainsPiece() == true) {
                        System.out.println("i have a piece");

                        if( ((Piece)((Square)e.getComponent()).getComponents()[1]).isCanBeMoved()==true ){
                            gameLogic.grabPiece( ((Piece)((Square)e.getComponent()).getComponents()[1]) );
                            System.out.println("Sequence will be: "+gameLogic.getSequence());
                        } else {
                            System.out.println("Wait for your turn please");
                        }
                    } else {
                        System.out.println("I was just pressing on the board (from piece)");
//                        gameLogic.getAllPiecesOfThisColor(false);
                        System.out.println("Sequence will be: "+gameLogic.getSequence());
                    }

                } else {
                    gameLogic.whyAmIPressing(getRowPosition(), getColumnPosition());
                    System.out.println("Sequence will be: "+gameLogic.getSequence());
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
