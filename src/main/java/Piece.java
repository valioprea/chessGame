import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Piece extends JLabel {


    private String name;

    GameLogic gameLogic;

    public Piece(String name, Icon icon, GameLogic gameLogic) {
        super(icon);
        this.name = name;
        this.gameLogic = gameLogic;
        this.eventHandler();
    }

    @Override
    public String getName() {
        return name;
    }

    private void eventHandler(){
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("clicked");
                System.out.println(e.getComponent());
                gameLogic.grabPiece((Piece)e.getComponent());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    };
}
