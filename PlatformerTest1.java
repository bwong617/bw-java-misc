import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class PlatformerTest1 extends Applet implements KeyListener, Runnable
{
    boolean pressed, left, right, up, down;
    int width = 1000, height = 650;

    int ax = 100;
    int ay = 480;
    int h = 20;
    int s = 10;
    boolean gup = true, jump = false;



    Image backbuffer;
    Graphics bg;
	Thread t = null;


    public void start (){
		if (t == null){
		    t = new Thread (this);
		    t.start ();
		}
    }


    public void run (){
		try{
	    	while (true){
				bg.setColor (Color.white);
				bg.fillRect (0, 0, width, height);
				bg.setColor (Color.gray);

				if (down)
					bg.setColor (Color.red);
				if (left){
					if (down)	{s=30;bg.setColor (Color.red);}
					else		{s=10;bg.setColor (Color.blue);}
					if (ax > 5)
						ax-=5;
				}
				if (right){
					if (down)	{s=30;bg.setColor (Color.red);}
					else		{s=10;bg.setColor (Color.green);}
					if(ax < 975)
						ax+=5;
					if(left)
						bg.setColor (Color.gray);
				}
				if (jump == false){
					if (up)
						jump = true;
				}
				if (jump == true){
					if (h == 0)					{h = 1;gup = false;}
					else if (h == 21 && !gup)	{h = 20;gup = true;jump = false;}
					else if (gup){
						ay-=h;
						h--;
					}
					else if (!gup){
						ay+=h;
						h++;
					}
					bg.setColor (Color.orange);
				}

				bg.fillRect (ax,ay,20,20);
				bg.setColor (Color.black);
				bg.fillRect (0,500,1000,150);
				bg.drawString ("x: " + ax,200,200);
				bg.drawString ("y: " + ay,200,215);
				bg.drawString ("s: " + s,200,230);

				repaint ();
				t.sleep (s);
	    	}
		}
	catch (InterruptedException ie){
		}
    }


    public void init (){
		setSize (width, height);
		addKeyListener (this);
		backbuffer = createImage (width, height);
		bg = backbuffer.getGraphics ();
    }


    public void keyPressed (KeyEvent e){
		int key = e.getKeyCode ();
		if (key == 37){pressed = true;left = true;}
		if (key == 38){pressed = true;up = true;}
		if (key == 39){pressed = true;right = true;}
		if (key == 40){pressed = true;down = true;}
    }


    public void keyReleased (KeyEvent e){
		int key = e.getKeyCode ();
		if (key == 37){pressed = false;left = false;}
		if (key == 38){pressed = true;up = false;}
		if (key == 39){pressed = true;right = false;}
		if (key == 40){pressed = true;down = false;}
    }

    public void keyTyped (KeyEvent e){
    }

    public void update (Graphics g){
		g.drawImage (backbuffer, 0, 0, this);
    }

    public void paint (Graphics g){
		update (g);
    }
}