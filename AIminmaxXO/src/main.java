import javax.swing.*;
import java.awt.*;

public class main {
    public static void main(String[] args)  {
        //server
        XO server = new XO();
        JFrame frame = new JFrame("XO");
        frame.setContentPane(server.getMainPanel());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(600,600));
        frame.pack();
        frame.setVisible(true);

    }
}
