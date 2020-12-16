import java.awt.*;
import javax.swing.*;


public class Frame extends JFrame{
  Frame(){
    this.add(new Panel());
    this.setTitle("Anastasia's Snake");
    this.setResizable(false);
    this.pack();
    this.setVisible(true);
    this.setLocationRelativeTo(null);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
