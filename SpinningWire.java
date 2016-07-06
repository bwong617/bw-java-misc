// The "Test" class.
import java.applet.*;
import java.awt.*;

public class SpinningWire extends Applet implements Runnable
{
    Thread t = null;

    Image backBuffer;
    Graphics bg;

    int width = 500, height = 500;
    int inc6x = 20, inc6y = 1;
    int inc3x = 2, inc3y = 10;
    int inc12x = 20, inc12y = 1;
    int inc9x = 2, inc9y = 10;
    int t6x = 250, t6y = 250;
    int t3x = 360, t3y = 184;
    int t12x = 228, t12y = 129;
    int t9x = 118, t9y = 195;
    int b6x = 250, b6y = 400;
    int b3x = 360, b3y = 334;
    int b12x = 228, b12y = 279;
    int b9x = 118, b9y = 345;
    int cond = 1;

    public void init ()
    {
	setSize (width, height);

	backBuffer = createImage (width, height);
	bg = backBuffer.getGraphics ();
    }


    public void start ()
    {
	if (t == null)
	{
	    t = new Thread (this);
	    t.start ();
	}
    }


    public void run ()
    {
	try
	{
	    while (true)
	    {
		bg.setColor (Color.white);
		bg.fillRect (0, 0, width, height);

		if (t6x == 228 && t6y == 250)
			cond = 1;
		if (t6x == 360 && t6y == 195)
		    cond = 2;
		if (t6y == 129)
		    cond = 3;
		if (t6x == 118 && t6y == 184)
		    cond = 4;
		
		if (cond == 1)
		{
		    t6x += inc6x;
		    t6y -= inc6y;
		    b6x += inc6x;
		    b6y -= inc6y;
		    inc6x -= 2;
		    inc6y += 1;

		    t3x -= inc3x;
		    t3y -= inc3y;
		    b3x -= inc3x;
		    b3y -= inc3y;
		    inc3x += 2;
		    inc3y -= 1;

		    t12x -= inc12x;
		    t12y += inc12y;
		    b12x -= inc12x;
		    b12y += inc12y;
		    inc12x -= 2;
		    inc12y += 1;

		    t9x += inc9x;
		    t9y += inc9y;
		    b9x += inc9x;
		    b9y += inc9y;
		    inc9x += 2;
		    inc9y -= 1;
		}
		if (cond == 2)
		{
		    t6x -= inc6x;
		    t6y -= inc6y;
		    b6x -= inc6x;
		    b6y -= inc6y;
		    inc6x += 2;
		    inc6y -= 1;

		    t3x -= inc3x;
		    t3y += inc3y;
		    b3x -= inc3x;
		    b3y += inc3y;
		    inc3x -= 2;
		    inc3y += 1;

		    t12x += inc12x;
		    t12y += inc12y;
		    b12x += inc12x;
		    b12y += inc12y;
		    inc12x += 2;
		    inc12y -= 1;

		    t9x += inc9x;
		    t9y -= inc9y;
		    b9x += inc9x;
		    b9y -= inc9y;
		    inc9x -= 2;
		    inc9y += 1;
		}
		if (cond == 3)
		{
		    t6x -= inc6x;
		    t6y += inc6y;
		    b6x -= inc6x;
		    b6y += inc6y;
		    inc6x -= 2;
		    inc6y += 1;

		    t3x += inc3x;
		    t3y += inc3y;
		    b3x += inc3x;
		    b3y += inc3y;
		    inc3x += 2;
		    inc3y -= 1;

		    t12x += inc12x;
		    t12y -= inc12y;
		    b12x += inc12x;
		    b12y -= inc12y;
		    inc12x -= 2;
		    inc12y += 1;

		    t9x -= inc9x;
		    t9y -= inc9y;
		    b9x -= inc9x;
		    b9y -= inc9y;
		    inc9x += 2;
		    inc9y -= 1;
		}
		if (cond == 4)
		{
		    t6x += inc6x;
		    t6y += inc6y;
		    b6x += inc6x;
		    b6y += inc6y;
		    inc6x += 2;
		    inc6y -= 1;

		    t3x += inc3x;
		    t3y -= inc3y;
		    b3x += inc3x;
		    b3y -= inc3y;
		    inc3x -= 2;
		    inc3y += 1;

		    t12x -= inc12x;
		    t12y -= inc12y;
		    b12x -= inc12x;
		    b12y -= inc12y;
		    inc12x += 2;
		    inc12y -= 1;

		    t9x -= inc9x;
		    t9y += inc9y;
		    b9x -= inc9x;
		    b9y += inc9y;
		    inc9x -= 2;
		    inc9y += 1;
		}

		bg.setColor (Color.black);
		bg.drawLine (t6x, t6y, t3x, t3y);
		bg.drawLine (t3x, t3y, t12x, t12y);
		bg.drawLine (t12x, t12y, t9x, t9y);
		bg.drawLine (t9x, t9y, t6x, t6y);

		bg.drawLine (b6x, b6y, b3x, b3y);
		bg.drawLine (b3x, b3y, b12x, b12y);
		bg.drawLine (b12x, b12y, b9x, b9y);
		bg.drawLine (b9x, b9y, b6x, b6y);
		
		bg.drawLine (t6x, t6y, b6x, b6y);
		bg.drawLine (t3x, t3y, b3x, b3y);
		bg.drawLine (t12x, t12y, b12x, b12y);
		bg.drawLine (t9x, t9y, b9x, b9y);

		repaint ();
		t.sleep (75);
	    }
	}
	catch (InterruptedException ie){}
    }

    public void update (Graphics bg)
    {
	bg.drawImage (backBuffer, 0, 0, this);
    }

    public void paint (Graphics g)
    {
    update (g);
    }
}