package MickyMouse;
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.gl2.GLUT;
import javax.swing.JFrame;


import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class CG implements GLEventListener , KeyListener , MouseListener {
   GLUT glut = new GLUT();
   GLU glu;
   float r=1; float g=1;float b=1; float d=1;    //defult background is white (1,1,1,1)
   float x=0;    //defult 
 public Mickey() {

    }
    public static void main(String[] args) {
        // The canvas

        final GLCanvas glcanvas = new GLCanvas();

        Mickey prog = new Mickey();
        glcanvas.addGLEventListener(prog);
        glcanvas.addKeyListener(prog);
        glcanvas.addMouseListener(prog);
        glcanvas.setSize(800, 700);
        final JFrame frame = new JFrame("Micky Mouse");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        //animation code
        FPSAnimator animator = new FPSAnimator(glcanvas, 600,true);
        animator.start();
    }

    @Override
    public void init(GLAutoDrawable g0) {
    }

    @Override
    public void dispose(GLAutoDrawable g0) {
    }
    float i=-1,theta=0;


    @Override
    public void display(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();
               
        glu = GLU.createGLU(gl);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        //
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustum(-1.0f, 1.0f, -1f, 1.0f, 1.5f, 20.0f);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        
        //camera viewing and x for mouse affect
        glu.gluLookAt(x, 0, 2.0, 0, 0, 0, 0.0f, 1, 0.7f);

        
         //background colour
        gl.glClearColor(r, b, g, d);
        gl.glFlush();
       
        
        int numVertices = 600;
        double radius = 0.5;

        // clear the window
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        

        
        //RED half circle
       // gl.glColor3f(r,g,b); 
        gl.glColor3f(1, 0, 0);
        gl.glPushMatrix(); 
        gl.glRotatef(180,0,0,1);
        gl.glBegin(GL2.GL_TRIANGLE_FAN); 
        {
            double angle = 0;
            double angleIncrement =  Math.PI / numVertices;
            for (int i = 0; i < numVertices; i++) {
                angle = i * angleIncrement;
                double x = radius * Math.cos(angle);
                double y = radius * Math.sin(angle);
                gl.glVertex2d(x, y);
            }
        }
        gl.glEnd();
        gl.glPopMatrix();
        
        
        //BLACK half circle

        gl.glColor3f(0, 0, 0);
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            double angle = 0;
            double angleIncrement =  Math.PI / numVertices;
            for (int i = 0; i < numVertices; i++) {
                angle = i * angleIncrement;
                double x = radius * Math.cos(angle);
                double y = radius * Math.sin(angle);
                gl.glVertex2d(x, y);
            }
        }
        gl.glEnd();
        
        //medium black circle 
        gl.glPushMatrix();
        gl.glTranslatef(0.6f, i, 0);
        gl.glRotatef(theta,1,1,0); // rotate around x and y
 
        //translation
        i+=0.005f;
        if(i>=0.47f)
        i=0.47f;

         //rotation
         theta+=0.5;
         if(theta >= 360)
         theta = 0;
  
        gl.glColor3f(0, 0, 0);

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        glut.glutSolidSphere(0.3, 100, 100);//3D
        gl.glPopMatrix();
        
        //medium black circle 
        gl.glPushMatrix();
        gl.glTranslatef(-0.6f, i, 0);
        gl.glRotatef(theta,1,-1,0); // rotate around x and y
 
        //translation
        i+=0.005f;
        if(i>=0.47f)
        i=0.47f;

         //rotation
         theta+=0.5;
         if(theta >= 360)
         theta = 0;
         
        gl.glColor3f(0, 0, 0);
        glut.glutSolidSphere(0.3, 100, 100); //3D
        gl.glPopMatrix();
        
        //right small white circle 
        gl.glPushMatrix();
        gl.glTranslatef(0.19f, -0.2f, 0);
        gl.glColor3f(1, 1, 1);

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            double angle = 0;
            double angleIncrement = 2 * Math.PI / numVertices;
            for (int i = 0; i < numVertices; i++) {
                angle = i * angleIncrement;
                double x = 0.05 * Math.cos(angle);
                double y = 0.05 * Math.sin(angle);
                gl.glVertex2d(x, y);
            }
        }
        gl.glEnd();
        gl.glPopMatrix();
        
        //left small white circle 
        gl.glPushMatrix();
        gl.glTranslatef(-0.19f, -0.2f, 0);
        gl.glColor3f(1, 1, 1);

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            double angle = 0;
            double angleIncrement = 2 * Math.PI / numVertices;
            for (int i = 0; i < numVertices; i++) {
                angle = i * angleIncrement;
                double x = 0.05 * Math.cos(angle);
                double y = 0.05 * Math.sin(angle);
                gl.glVertex2d(x, y);
            }
        }
        gl.glEnd();
        gl.glPopMatrix();
 }
  
    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
    }

    @Override
    public void keyTyped(KeyEvent ke) {
       char key = ke.getKeyChar();

        System.out.printf("Key typed: %c\n", key);

        switch (key) {

            case 'r':
                System.out.println("Changing the background Color to RED....");
                r = 0.7f;
                g = 0.2f;
                b = 0.12f;
                d = 0;
                break;
            case 'g':
                System.out.println("Changing the background Color to GREEN....");
                r = 0.2f;
                g = 0.5f;
                b = 0.7f;
                d = 0;
                break;
            case 'b':
                System.out.println("Changing the background Color to BLUE....");
                r = 0.2f;
                g = 0.8f;
                b = 0.5f;
                d = 0.3f;
                break;
             case 'p':
                System.out.println("Changing the background Color to PINK....");
                r = 1f;
                g = 0.6f;
                b = 0.5f;
                d = 0f;
                break;
                
              case 'y':
                System.out.println("Changing the background Color to YELLOW....");
                r = 0.9f;
                g = 0.6f;
                b = 1f;
                d = 0f;
                break;

            case 27:
                System.out.println("Exit!");
                System.exit(0);
        }
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
    }

    @Override
    public void mouseClicked(MouseEvent me) {
 if(me.getButton()==MouseEvent.BUTTON1)
        x+=0.1;
         if(me.getButton()==MouseEvent.BUTTON3)
        x-=0.1;
         
        
         System.out.println("("+me.getX()+","+me.getY()+")");    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }

}