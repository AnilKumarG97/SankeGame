import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.Graphics;
import java.util.*;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

//to main calss for game
public class SnakeGame
{
	public SnakeGame()
	{
		JFrame frame=new JFrame();
		GamePanel gamepanel= new GamePanel();

		 frame.add(gamepanel);
		 frame.setTitle("SankeCoding");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		


          frame.pack();
		  frame.setVisible(true);
		  frame.setLocationRelativeTo(null);

	}

	public static void main(String[] args)
	{
		new SnakeGame();
	}
}


//GamePanel for external panel to apper 
 class GamePanel extends JPanel implements Runnable,KeyListener
{
	private static final long serialVersionUID = 1L;
	public  static final int WIDTH = 500, HEIGHT =500;

	private Thread thread;
	private boolean running;
	private boolean  right=true,left=false,up=false,down=false;

 //body of snake

	private BodyPart b;
	private ArrayList<BodyPart> snake;


//apple 

	private Apple apple;
	private ArrayList<Apple> apples;

//to appear the apple in random postion

	private Random r;
//sanke length 
	private int xCoor=10,yCoor=10,size=5;
	private int ticks=0;


	  public GamePanel()
		  {
          setFocusable(true);
         setPreferredSize(new Dimension(WIDTH,HEIGHT));
		 addKeyListener(this);

		 snake = new ArrayList<BodyPart>();
		 apples = new ArrayList<Apple>();
           
		   r=new Random();


		 start();

	     }

		 public void start()
	{
			  running=true;
	   thread =new Thread(this);
	   thread.start();

	}
	 
	  public void stop()
	{
		   running=false;
		 try{
	   thread.join();
		 }
		 catch(InterruptedException e){
			 //TOOO Auto-genertaed catch block
			 e.printStackTrace();
		 }
	}

	 public void tick()
	{
		 if(snake.size()==0)
		{
			 b=new BodyPart(xCoor,yCoor,10);
			 snake.add(b);
		}
		ticks++;
		if (ticks>250000)
		{
			if(right)xCoor++;
			if(left)xCoor--;
			if(up)yCoor--;
			if(down)yCoor++;

			ticks=0;

			b=new BodyPart(xCoor,yCoor,10);
			snake.add(b);

			if (snake.size() > size)
			{
				snake.remove(0);
			}
		}

		if (apples.size() == 0)
		{
			int xCoor = r.nextInt(49);
			int yCoor = r.nextInt(49);

			apple = new Apple(xCoor, yCoor,10);
			apples.add(apple);
		}

		for (int i=0;i<apples.size() ;i++ )
		{
			if (xCoor == apples.get(i).getxCoor() && yCoor == apples.get(i).getyCoor())
			{
				size++;
				apples.remove(i);
					i++;
			}
		}

// if snake collosion with its body

		for (int i=0;i<snake.size() ;i++ )
		{
			if (xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor())
			{
				if (i !=snake.size()-1)
				{
					System.out.println("GAME OVER");
			stop();

				}
			}
		}
// if sanke colloision with boundries

		if (xCoor < 0 || xCoor >49 || yCoor< 0 || yCoor>49)
		{
			System.out.println("GAME OVER");
			stop();
		}
		
	}

	public void paint(Graphics g)
	{
		g.clearRect(0,0,WIDTH,HEIGHT);
		g.setColor(Color.white);
		g.fillRect(0,0,WIDTH,HEIGHT);
    for (int i=0;i<WIDTH/10;i++ )
    {
		g.drawLine(i*10,0,i*10,HEIGHT);
    }
	for (int i=0;i<HEIGHT/10;i++ )
    {
		g.drawLine(0,i*10,HEIGHT,i*10);
    }
	for (int i=0;i<snake.size() ;i++ )
	{
		snake.get(i).draw(g);
	}
	for (int i=0;i<apples.size() ;i++ )
	{
        apples.get(i).draw(g);
	}
	}

	@Override
		public void run()
	{
		while(running)
		{
			tick();
			repaint();
	}

}

    @Override
	public void keyPressed(KeyEvent e)
	{
	int key=e.getKeyCode();
	if(key == KeyEvent.VK_RIGHT && !left)
		{
		right=true;
		up=false;
		down=false;
		}
		if(key == KeyEvent.VK_LEFT && !right)
		{
		left=true;
		up=false;
		down=false;
		}
		if(key == KeyEvent.VK_UP && !down)
		{
		up=true;
		right=false;
		left=false;
		}
		if(key == KeyEvent.VK_DOWN && !up)
		{
		down=true;
		right=false;
		left=false;
		}

	}

@Override
	public void keyReleased(KeyEvent arg0)
	{

	}

	@Override
	public void keyTyped(KeyEvent arg0)
	{

	}

// class for body
 class BodyPart
	{
				 private int xCoor, yCoor, width, height;
				 public  BodyPart(int xCoor,int yCoor,int tilesize)
				 {
					 this.xCoor=xCoor;
					 this.yCoor=yCoor;
					 width=tilesize;
					 height=tilesize;
				 }
			 

			 public void tick1()
	       {
		   }
				 public void draw(Graphics g)
			   {
                 g.setColor(Color.black);
				 g.fillRect(xCoor * width,yCoor * height, width, height);
		       }
			    public void setxCoor(int xCoor)
			 {
				 this.xCoor=xCoor;
			 }
		   public int getxCoor()
			 {
			   return xCoor;
			 }
			 public void setyCoor(int yCoor)
			 {
				 this.yCoor=yCoor;
			 }
			  public int getyCoor()
			 {
			   return yCoor;
			 }
			 
		 }
}
	
// apple class body

		 class Apple
	{
				 private int xCoor, yCoor, width, height;
				 public  Apple(int xCoor,int yCoor,int tilesize)
				 {
					 this.xCoor=xCoor;
					 this.yCoor=yCoor;
					 width=tilesize;
					 height=tilesize;
				 }
			 

			 public void tick2()
	       {
		   }
				 public void draw(Graphics g)
			   {
                 g.setColor(Color.red);
				 g.fillRect(xCoor * width,yCoor * height, width, height);
		       }
			   public void setxCoor(int xCoor)
			 {
				 this.xCoor=xCoor;
			 }
		   public int getxCoor()
			 {
			   return xCoor;
			 }
			 public void setyCoor(int yCoor)
			 {
				 this.yCoor=yCoor;
			 }
			  public int getyCoor()
			 {
			   return yCoor;
			 }
}