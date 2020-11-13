package MickyMouse;


 
import com.jogamp.opengl.*;
import com.jogamp.opengl.util.FPSAnimator;
import com.jogamp.opengl.awt.GLCanvas;
import javax.swing.JFrame;


public class Circle implements GLEventListener {

    public static void main(String[] args) {
        // The canvas
        final GLCanvas glcanvas = new GLCanvas();
        Circle l = new Circle();
        glcanvas.addGLEventListener(l);
        glcanvas.setSize(800, 700);

        //creating frame
        final JFrame frame = new JFrame("Micky Mouse");
        //adding canvas to frame
        frame.getContentPane().add(glcanvas);
        frame.setSize(frame.getContentPane().getPreferredSize());
        frame.setVisible(true);
        //animation code
        FPSAnimator animator = new FPSAnimator(glcanvas, 600);
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
        gl.glClearColor(1, 1, 1, 1);
        int numVertices = 600;
        double radius = 0.5;

        // clear the window
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);

       
        
        
        //RED half circle
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
        {
            double angle = 0;
            double angleIncrement = 2 * Math.PI / numVertices;
            for (int i = 0; i < numVertices; i++) {
                angle = i * angleIncrement;
                double x = 0.3 * Math.cos(angle);
                double y = 0.3 * Math.sin(angle);
                gl.glVertex2d(x, y);
            }
        }
        gl.glEnd();
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

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            double angle = 0;
            double angleIncrement = 2 * Math.PI / numVertices;
            for (int i = 0; i < numVertices; i++) {
                angle = i * angleIncrement;
                double x = 0.3 * Math.cos(angle);
                double y = 0.3 * Math.sin(angle);
                gl.glVertex2d(x, y);
            }
        }
        gl.glEnd();
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

}