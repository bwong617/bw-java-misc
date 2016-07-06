import java.applet.*;
import java.awt.*;

public class AwesomeFace extends Applet implements Runnable
{
    Thread t = null;

    Image backBuffer;
    Graphics bg;

    int width = 700, height = 500;
    int inf = 0;                                                // variables declared
    int a = 0;
    int lx = 230;
    int rx = 378;

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
		//bg.setColor (Color.white);
		//bg.fillRect (0, 0, width, height);

		Color skin = new Color (255, 230, 100);                 // creates facial skin color
		Color mouth = new Color (145, 26, 82);                  // creates inner mouth color
		Color tongue = new Color (254, 197, 249);               // creates tongue color

		bg.setColor (Color.black);
		bg.fillOval (150, 75, 350, 350);                         // outline of the head

		bg.setColor (skin);
		bg.fillOval (165, 90, 320, 320);                         // yellow face

		do                                                      // animation loop
		{

		    if (a == 10)
		    {
			bg.setColor (skin);
			bg.fillArc (180, 90, 270, 310, 180, 180);        // skin in place of mouth

			bg.setColor (Color.black);
			bg.fillRect (215, 300, 125, 10);                 // closed mouth
			repaint ();
			t.sleep (1500);                            // short pause between eye movements
		    }
		    else
		    {
			bg.setColor (Color.black);                       // BLACK OUTLINE OF MOUTH:
			bg.fillArc (200, 110, 210, 280, 180, 90);        // left side of mouth
			bg.fillArc (170, 110, 270, 280, 270, 45);        // bottom right side of mouth
			bg.fillArc (321, 232, 100, 130, 155, 250);       // top right side of mouth
			bg.fillRect (300, 250, 105, 75);                 // middle of mouth

			bg.setColor (mouth);                             // INSIDE OF MOUTH:
			bg.fillArc (210, 120, 190, 260, 180, 90);        // left side of mouth
			bg.fillArc (180, 120, 250, 260, 270, 45);        // bottom right side of mouth
			bg.fillArc (331, 242, 80, 110, 155, 250);        // top right side of mouth
			bg.fillRect (310, 260, 90, 73);                  // middle of mouth

			bg.setColor (Color.black);
			bg.fillRect (190, 250, 215, 10);                 // upper lip

			bg.setColor (tongue);
			bg.fillArc (275, 320, 120, 120, 28, 152);        // tongue
			bg.setColor (Color.black);                       // [IMPRACTICAL] COVERUP OF TONGUE OVERLAP ONTO BOTTOM LIP:
			bg.drawArc (210, 119, 192, 262, 225, 45);        // left side of mouth
			bg.drawArc (209, 118, 194, 264, 225, 45);        // left side of mouth
			bg.drawArc (208, 117, 196, 266, 225, 45);        // left side of mouth
			bg.drawArc (207, 116, 198, 268, 225, 45);        // left side of mouth
			bg.drawArc (206, 115, 200, 270, 225, 45);        // left side of mouth
			bg.drawArc (205, 114, 202, 272, 225, 45);        // left side of mouth
			bg.drawArc (180, 120, 250, 260, 270, 45);        // bottom right side of mouth
			bg.drawArc (179, 119, 252, 262, 270, 45);        // bottom right side of mouth
			bg.drawArc (178, 118, 254, 264, 270, 45);        // bottom right side of mouth
			bg.drawArc (177, 117, 256, 266, 270, 45);        // bottom right side of mouth
			bg.drawArc (176, 116, 258, 268, 270, 45);        // bottom right side of mouth
			bg.drawArc (175, 115, 260, 270, 270, 45);        // bottom right side of mouth
			bg.fillRect (276, 376, 1, 1);                    // bad pixel fix
			bg.fillRect (278, 379, 1, 1);                    // bad pixel fix
			bg.fillRect (281, 379, 1, 1);                    // bad pixel fix
			bg.fillRect (382, 353, 1, 1);                    // bad pixel fix
			bg.fillRect (383, 352, 1, 1);                    // bad pixel fix
			bg.fillRect (386, 351, 1, 1);                    // bad pixel fix
		    }

		    if (a >= 10)                                        // pupils move left to right
		    {
			lx += 3;                                        // left pupil movement
			rx += 3;                                        // right pupil movement
		    }
		    else                                                // pupils move right to left
		    {
			lx -= 3;                                        // left pupil movement
			rx -= 3;                                        // right pupil movement
		    }

		    bg.setColor (Color.black);                           // LEFT EYE:
		    bg.fillArc (185, 155, 90, 105, 335, 230);            // black outline
		    bg.setColor (Color.white);
		    bg.fillArc (195, 165, 70, 85, 335, 230);             // white fill
		    bg.fillRect (200, 205, 60, 20);                      // white fill
		    bg.setColor (Color.black);
		    bg.fillRect (190, 222, 80, 8);                       // lower eyelid

		    bg.setColor (Color.black);                           // RIGHT EYE
		    bg.fillArc (325, 155, 100, 105, 335, 230);           // black outline
		    bg.setColor (Color.white);
		    bg.fillArc (335, 165, 80, 85, 335, 230);             // white fill
		    bg.fillRect (340, 205, 70, 20);                      // white fill
		    bg.setColor (Color.black);
		    bg.fillRect (330, 222, 90, 8);                       // lower eyelid

		    bg.setColor (Color.black);                           // MOVING PUPILS
		    bg.fillOval (lx, 165, 35, 35);                       // left pupil
		    bg.fillOval (rx, 165, 35, 35);                       // right pupil

		    a += 1;                                             // controls direction/distance of pupil movements; controls loop
		    repaint ();
		    t.sleep (30);                                  // controls speed of pupil movements

		}
		while (a != 20);                                        // loop ends

		a = 0;                                                  // resets loop conditions

		t.sleep (3000);                                    // long pause between animation loops

		repaint ();
	    }
	}
	catch (InterruptedException ie)
	{
	}
    }


    public void update (Graphics bg)
    {
	bg.drawImage (backBuffer, 0, 0, this);
    }


    public void paint (Graphics g)
    {
	update (g);
    }



} // Combo class