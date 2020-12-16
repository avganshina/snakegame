import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class Panel extends JPanel implements ActionListener{
  
  public static final int  WIDTH = 600, HEIGHT = 600; // Screen dimensions
  public static final int UNIT_SIZE = 25;
  public static final int GAME_UNITS = (WIDTH*HEIGHT)/UNIT_SIZE;
  public static final int DELAY = 200;
  private final int x[] = new int[GAME_UNITS];
  private final int y[] = new int[GAME_UNITS];
  private int SnakeBodyParts = 6; // initially snake has  parts, then will increase
  int applesEaten; // will be implemented in score
  int appleX;
  int appleY;
  char direction = 'R'; // initial direction
  private boolean running = false;
  Timer timer;
  Random random;

  Panel(){
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    this.setBackground(Color.black);
    this.setFocusable(true);  
    this.addKeyListener(new MyKeyAdapter());
    random = new Random();
    start();
  }

  public void start(){
      newApple();
      running = true;
      timer = new Timer(DELAY, this);
      timer.start();
  }

  public void paintComponent(Graphics g){
    super.paintComponent(g);
    draw(g);
  }

  public void draw(Graphics g){
      if (running){
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        for (int i = 0; i<SnakeBodyParts; i++){
          if (i == 0){
            g.setColor(Color.green);
            g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
        else{
          g.setColor(Color.green);
          g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
        }
      }
    }
    else{
      gameOver(g);
    }
  }

  public void newApple(){ // put an apple in random place within boarder
      appleX = random.nextInt((int)(WIDTH/UNIT_SIZE))*UNIT_SIZE;
      appleY = random.nextInt((int)(HEIGHT/UNIT_SIZE))*UNIT_SIZE;
  }

  public void move(){ // For snake, move part by part
      for (int i = SnakeBodyParts; i>0; i--){
        x[i] = x[i-1];
        y[i] = y[i-1];
      }

      switch(direction){
        case 'U':
          y[0] = y[0] - UNIT_SIZE;
          break;
        case 'D':
          y[0] = y[0] + UNIT_SIZE;
          break;
        case 'L':
          x[0] = x[0] - UNIT_SIZE;
          break;
        case 'R':
          x[0] = x[0] + UNIT_SIZE;
          break;
        
      }
  }

  public void checkApple() {
      if((x[0] == appleX) && (y[0] == appleY)){
        SnakeBodyParts++;
        applesEaten++;
        newApple();
      }
  }

  public void checkCollisions(){

      for (int i = SnakeBodyParts; i>0; i--){
        if ((x[0] == x[i])&&(y[0] == y[i])){
          running = false;
        }
      }

      if (x[0] < 0 ){
        running = false;
      }

      if (x[0] > WIDTH){
        running = false;
      }

      if (y[0] < 0 ){
        running = false;
      }

      if (y[0] > HEIGHT){
        running = false;
      }

      if (running == false){
        timer.stop();
      }
  }

  public void gameOver(Graphics g){
    g.setColor(Color.red);
    g.setFont(new Font("Ink Free", Font.BOLD,75));
    FontMetrics metrics = getFontMetrics(g.getFont());
    g.drawString("Game Over", (WIDTH - metrics.stringWidth("Game Over"))/2, HEIGHT/2);
    g.drawString("Score: "+ applesEaten, (WIDTH - metrics.stringWidth("Score: "+ applesEaten))/2, g.getFont().getSize());
  }

  @Override
  public void actionPerformed(ActionEvent e){
      if(running) {
        move();
        checkApple();
        checkCollisions();
      }
      repaint();
  }

  public class MyKeyAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      switch (e.getKeyCode()) {
      case KeyEvent.VK_LEFT:
        if (direction != 'R') {
          direction = 'L';
        }
        break;
      case KeyEvent.VK_RIGHT:
        if (direction != 'L') {
          direction = 'R';
        }
        break;
      case KeyEvent.VK_UP:
        if (direction != 'D') {
          direction = 'U';
        }
        break;
      case KeyEvent.VK_DOWN:
        if (direction != 'U') {
          direction = 'D';
        }
        break;
      }
    }
  }
}