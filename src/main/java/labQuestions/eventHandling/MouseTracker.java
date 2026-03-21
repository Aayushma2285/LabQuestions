package labQuestions.eventHandling;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class MouseTracker extends JFrame implements MouseListener{
    JLabel label;

    MouseTracker() {
        setTitle("Mouse Event Tracker");
        setSize(400, 300);
        setLayout(new FlowLayout());

        label = new JLabel("Click anywhere inside window");
        add(label);

        addMouseListener(this);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void mouseClicked(MouseEvent e) {
        label.setText("Mouse Clicked at X=" + e.getX() + " Y=" + e.getY());
    }

    public void mousePressed(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}

    public static void main(String[] args) {
        new MouseTracker();
    }
}
