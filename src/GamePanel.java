import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener{
	
	static final int SCREEN_WIDTH=800;
	static final int SCREEN_HEIGHT=700;
	static final int UNITS=25;
	static final int GAME_UNITS=(SCREEN_HEIGHT*SCREEN_WIDTH)/UNITS;
	static final int DELAY=85;
	final int x[]=new int[GAME_UNITS];
	final int y[]=new int[GAME_UNITS];
	int bodyParts=6;
	int appleEaten;
	int appleX;
	int appleY;
	char directions='D';
	boolean running=false;
	Timer timer;
	Random random;
	
   public GamePanel() {
	   random=new Random();
	   this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
	   this.setBackground(Color.black);
	   this.setFocusable(true);
	   this.addKeyListener(new MyKeyAdapter());
	   startGame();
   }
   public void startGame() {
	   newApple();
	   running=true; 
	   timer=new Timer(DELAY,this);
	   timer.start();
   }
   public void paintComponent(Graphics g) {
	   super.paintComponent(g);
	   draw(g);  
   }
   public void draw(Graphics g) {
		if(running) {
			 g.setColor(Color.RED);
			   g.fillOval(appleX, appleY, UNITS, UNITS);
			 for(int i=0;i<bodyParts;i++) {
				   if(i==0) {
					   g.setColor(Color.GREEN);
					   g.fillRect(x[i], y[i], UNITS, UNITS);
				   }else {
					   g.setColor(new Color(45,180,0));
					   g.fillRect(x[i], y[i], UNITS, UNITS);
				   }
			   }
			   g.setColor(Color.red);
			   g.setFont(new Font("Ink Free",Font.BOLD,40));
			   FontMetrics metrics=getFontMetrics(g.getFont());
			   g.drawString("Score:"+appleEaten, (SCREEN_WIDTH-metrics.stringWidth("appleEaten"))/2, g.getFont().getSize());
		   
		}else {
			gameOver(g);
		}
	
   }

   
   public void newApple() {
	   appleX=random.nextInt((int)SCREEN_WIDTH/UNITS)*UNITS;
	   appleY=random.nextInt((int)SCREEN_HEIGHT/UNITS)*UNITS;
	   
   }
   public void move() {
	   for(int i=bodyParts;i>0;i--) {
		   x[i]=x[i-1];
		   y[i]=y[i-1];
	   }
	   switch(directions) {
	   case 'U':
		   y[0]=y[0]-UNITS;
		   break;
	   case 'D':
		   y[0]=y[0]+UNITS;
		   break;
	   case 'L':
		   x[0]=x[0]-UNITS;
		   break;
	   case 'R':
		   x[0]=x[0]+UNITS;
		   break;
	   }
   }
   public void checkApple() {
	   if((x[0]==appleX)&&(y[0]==appleY)) {
		   bodyParts++;
		   appleEaten++;
		   newApple();
	   }
   }
   public void checkCollisions() {
	   //to check collision head with body 
	   for(int i=bodyParts;i>0;i--) {
		   if((x[0]==x[i])&&(y[i]==y[0])) {
			   running=false;
		   }
	   }
	   //to check collision with left border
	   if(x[0]<0) {
		   running=false;
	   }
	   //right
	   if(x[0]>SCREEN_WIDTH) {
		   running=false;
	   }
	   //top
	   if(y[0]<0) {
		   running=false;
	   }
	   //bottom
	   if(y[0]>SCREEN_HEIGHT) {
		   running=false;
	   }
	   
	   //if false stop
	   if(!running) {
		   timer.stop();
	   }
	  
   }
   public void gameOver(Graphics g) {
	   
	   g.setColor(Color.red);
	   g.setFont(new Font("Ink Free",Font.BOLD,40));
	   FontMetrics metrics1=getFontMetrics(g.getFont());
	   g.drawString("Score:"+appleEaten, (SCREEN_WIDTH-metrics1.stringWidth("appleEaten"))/2, g.getFont().getSize());
	   
	   g.setColor(Color.red);
	   g.setFont(new Font("Ink Free",Font.BOLD,75));
	   FontMetrics metrics=getFontMetrics(g.getFont());
	   g.drawString("Game Over", (SCREEN_WIDTH-metrics.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);
	   
	 
   }
   @Override
	public void actionPerformed(ActionEvent e) {
		if(running) {
			    move();
				checkApple();
				checkCollisions();
			
		}
		repaint();
	}
	public class MyKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				if(directions!='R') {
					directions='L';
				}
				break;
			case KeyEvent.VK_RIGHT:
				if(directions!='L') {
					directions='R';
				}
				break;
			case KeyEvent.VK_UP:
				if(directions!='D') {
					directions='U';
				}
				break;
			case KeyEvent.VK_DOWN:
				if(directions!='U') {
					directions='D';
				}
				break;
			}
		}
	}
	

}