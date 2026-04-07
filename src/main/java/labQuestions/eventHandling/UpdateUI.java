package labQuestions.eventHandling;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class UpdateUI {
    public static void main(String[] args) {

        JFrame frame = new JFrame("UI Change Demo");
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());

        JLabel label = new JLabel("Click the button");
        JButton button = new JButton("Click Me");

        frame.add(label);
        frame.add(button);

        button.addActionListener(new ActionListener() {
            int count = 0;

            public void actionPerformed(ActionEvent e) {
                count++;
                label.setText("Button clicked " + count + " times");
                frame.getContentPane().setBackground(
                        new Color((int)(Math.random()*255),
                                (int)(Math.random()*255),
                                (int)(Math.random()*255))
                );
            }
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
