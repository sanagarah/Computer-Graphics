/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgproject;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.gl2.GLUT;
import com.jogamp.opengl.util.FPSAnimator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CGProject {

    static boolean robotOn = false;
    static boolean mickeyOn = false;

    public static void main(String[] args) {

        // ------- Robot initialization -------
        // getting the capabilities object of GL2 profile    
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities capabilities = new GLCapabilities(profile);
        GLCanvas robot_glcanvas = new GLCanvas(capabilities);
        FPSAnimator robot_animator = new FPSAnimator(robot_glcanvas, 300, true);

        // ------- Mickey initialization -------
        GLCanvas mickey_glcanvas = new GLCanvas();
        FPSAnimator mickey_animator = new FPSAnimator(mickey_glcanvas, 300, true);

        // initialize frame
        JFrame frame = new JFrame("CGProject");
        frame.setResizable(false);
        frame.setSize(900, 820);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // initialize panel
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // add button to buttonPanel
        JPanel buttonPanel = new JPanel();
        JButton buttonRobot = new JButton();
        buttonRobot.setText("Robot 🤖");
        buttonPanel.add(buttonRobot);

        JButton buttonMickey = new JButton();
        buttonMickey.setText("Mickey 🐭");
        buttonPanel.add(buttonMickey);

        // add buttonPanel to panel
        panel.add(buttonPanel, BorderLayout.NORTH);
        // add panel to frame
        frame.add(panel);

        // handle robot button click
        buttonRobot.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (mickeyOn) {
                    // if mickey_glcanvas is on frame remove it
                    frame.getContentPane().remove(mickey_glcanvas);
                }

                // setup robot canvas
                Robot prog = new Robot();
                robot_glcanvas.addGLEventListener(prog);
                robot_glcanvas.addKeyListener(prog);
                robot_glcanvas.addMouseListener(prog);
                robot_glcanvas.setSize(800, 700);
                robot_glcanvas.setLocation(40, 50);
                // add canvas to frame
                frame.getContentPane().add(robot_glcanvas, BorderLayout.CENTER);
                robotOn = true;

                // start the animation
                robot_animator.start();
            }
        });

         // handle mickey button click
        buttonMickey.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {

                if (robotOn) {
                    // if robot_glcanvas is on frame remove it
                    frame.getContentPane().remove(robot_glcanvas);
                }

                // setup mickey canvas
                Mickey prog2 = new Mickey();
                mickey_glcanvas.addGLEventListener(prog2);
                mickey_glcanvas.addKeyListener(prog2);
                mickey_glcanvas.addMouseListener(prog2);
                mickey_glcanvas.setSize(800, 700);
                mickey_glcanvas.setLocation(40, 50);
                // add canvas to frame
                frame.getContentPane().add(mickey_glcanvas, BorderLayout.CENTER);
                mickeyOn = true;

                // start the animation
                mickey_animator.start();
            }
        });

        // display the frame
        frame.setVisible(true);

    } //end of main

}

class Mickey implements GLEventListener, KeyListener, MouseListener {

    GLUT glut = new GLUT();
    GLU glu;
    float r = 1;
    float g = 1;
    float b = 1;
    float d = 1;    //defult background is white (1,1,1,1)
    float x = 0;    //defult 

    public Mickey() {

    }


    @Override
    public void init(GLAutoDrawable g0) {
    }

    @Override
    public void dispose(GLAutoDrawable g0) {
    }
    float i = -1, theta = 0;

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
        gl.glRotatef(180, 0, 0, 1);
        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        {
            double angle = 0;
            double angleIncrement = Math.PI / numVertices;
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
            double angleIncrement = Math.PI / numVertices;
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
        gl.glRotatef(theta, 1, 1, 0); // rotate around x and y

        //translation
        i += 0.005f;
        if (i >= 0.47f) {
            i = 0.47f;
        }

        //rotation
        theta += 0.5;
        if (theta >= 360) {
            theta = 0;
        }

        gl.glColor3f(0, 0, 0);

        gl.glBegin(GL2.GL_TRIANGLE_FAN);
        glut.glutSolidSphere(0.3, 100, 100);//3D
        gl.glPopMatrix();

        //medium black circle 
        gl.glPushMatrix();
        gl.glTranslatef(-0.6f, i, 0);
        gl.glRotatef(theta, 1, -1, 0); // rotate around x and y

        //translation
        i += 0.005f;
        if (i >= 0.47f) {
            i = 0.47f;
        }

        //rotation
        theta += 0.5;
        if (theta >= 360) {
            theta = 0;
        }

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
        if (me.getButton() == MouseEvent.BUTTON1) {
            x += 0.1;
        }
        if (me.getButton() == MouseEvent.BUTTON3) {
            x -= 0.1;
        }

        System.out.println("(" + me.getX() + "," + me.getY() + ")");
    }

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

class Robot implements GLEventListener, KeyListener, MouseListener {

    static boolean robotOn = false;
    static boolean mickeyOn = false;
    GLUT glut = new GLUT();
    GLU glu;
    float PI = 3.14154f;
    double theta, x, y;
    int r = 0, g = 0, b = 0;
    int XX = 1, YY = 1, ZZ = 1;

    float xx = 0;

    void drawCircle(GL2 gl, float xPos, float yPos, float rx, float ry) {

        gl.glBegin(GL2.GL_POLYGON);
        for (theta = 0; theta <= (2 * PI); theta += 2 * PI / 1000) {
            x = xPos + rx * Math.cos(theta);
            y = yPos + ry * Math.sin(theta);
            gl.glVertex2d(x, y);
        }
        gl.glEnd();

    }

    void drawSolidCircle(GL2 gl, float xPos, float yPos, float rx, float ry) {
        gl.glBegin(GL2.GL_LINE_LOOP);
        for (theta = 0; theta <= (2 * PI); theta += 2 * PI / 1000) {
            x = xPos + rx * Math.cos(theta);
            y = yPos + ry * Math.sin(theta);
            gl.glVertex2d(x, y);
        }
        gl.glEnd();
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        final GL2 gl = drawable.getGL().getGL2();
        glu = GLU.createGLU(gl);
        gl.glClearColor(1, 1, 1, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();

        gl.glPushMatrix();
        gl.glTranslatef(xx, 0, 0);
        // Draw the hesd      
        gl.glColor3f(0.9f, 0.8f, 0.5f);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(-0.14f, 0.47f);
        gl.glVertex2f(-0.14f, 0.74f);
        gl.glVertex2f(0.14f, 0.74f);
        gl.glVertex2f(0.12f, 0.47f);
        gl.glEnd();
        // Draw the eyes
        // External eyes
        gl.glColor3f(1, 1, 1);
        drawCircle(gl, -0.07f, 0.65f, .035f, .025f);
        drawCircle(gl, 0.07f, 0.65f, .035f, .025f);
        gl.glColor3f(0, 0, 0);
        drawSolidCircle(gl, -0.07f, 0.65f, .035f, .025f);
        drawSolidCircle(gl, 0.07f, 0.65f, .035f, .025f);
        //Internal eyes
        gl.glColor3f(0, 0, 1);
        drawCircle(gl, -0.07f, 0.65f, .015f, .010f);
        drawCircle(gl, 0.07f, 0.65f, .015f, .010f);
        //Draw the Mouth
        // Mouth 
        gl.glColor3f(1, 1, 1);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(-0.08f, 0.51f);
        gl.glVertex2f(-0.07f, 0.55f);
        gl.glVertex2f(0.04f, 0.55f);
        gl.glVertex2f(0.04f, 0.51f);
        gl.glEnd();
        // Teeth
        gl.glColor3f(XX, YY, ZZ);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(-0.07f, 0.523f);
        gl.glVertex2f(-0.07f, 0.533f);
        gl.glVertex2f(0.04f, 0.533f);
        gl.glVertex2f(0.04f, 0.523f);
        gl.glEnd();
        // Anternnae
        gl.glColor3f(0, 0, 0);
        gl.glLineWidth(3.0f);
        gl.glBegin(GL2.GL_LINES);
        gl.glVertex2f(-0.01f, 0.74f);
        gl.glVertex2f(-0.11f, 0.84f);
        gl.glVertex2f(0.01f, 0.74f);
        gl.glVertex2f(0.11f, 0.84f);
        gl.glEnd();
        gl.glColor3f(r, g, b);
        drawCircle(gl, -0.11f, 0.84f, .015f, .010f);
        drawCircle(gl, 0.11f, 0.84f, .015f, .010f);
//Body      
        gl.glColor3f(0.6f, 0.7f, 0.7f);
        gl.glBegin(GL2.GL_POLYGON);

        gl.glVertex2f(-0.185f, 0f);
        gl.glVertex2f(-0.18f, 0.45f);
        gl.glVertex2f(0.18f, 0.45f);
        gl.glVertex2f(0.18f, 0.0f);
        gl.glEnd();
//Left Arm

        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(-0.29f, 0.05f);
        gl.glVertex2f(-0.27f, 0.37f);
        gl.glVertex2f(-0.21f, 0.37f);
        gl.glVertex2f(-0.22f, 0.05f);
        gl.glEnd();
// Right Arm

        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(0.29f, 0.05f);
        gl.glVertex2f(0.27f, 0.37f);
        gl.glVertex2f(0.21f, 0.37f);
        gl.glVertex2f(0.22f, 0.05f);
        gl.glEnd();
// Hand

        //Left Hand
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(-0.31f, 0.026f); //2
        gl.glVertex2f(-0.31f, -0.1f); //1
        gl.glVertex2f(-0.29f, -0.0325f);//10
        gl.glVertex2f(-0.29f, 0f);//9
        gl.glEnd();

        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(-0.28f, 0.053f);//3
        gl.glVertex2f(-0.31f, 0.026f); //2
        gl.glVertex2f(-0.29f, 0f);//9
        gl.glVertex2f(-0.28f, 0.016f);//8
        gl.glEnd();
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(-0.24f, 0.026f);//4
        gl.glVertex2f(-0.28f, 0.053f);//3
        gl.glVertex2f(-0.28f, 0.016f);//8
        gl.glVertex2f(-0.26f, 0f);//7
        gl.glEnd();

        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(-0.24f, 0.026f);//4
        gl.glVertex2f(-0.26f, 0f);//7
        gl.glVertex2f(-0.27f, -0.03f);//6
        gl.glVertex2f(-0.25f, -0.095f);//5
        gl.glEnd();
        //Right Hand
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(0.31f, 0.026f); //2
        gl.glVertex2f(0.31f, -0.1f); //1
        gl.glVertex2f(0.29f, -0.0325f);//10
        gl.glVertex2f(0.29f, 0f);//9
        gl.glEnd();

        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(0.28f, 0.053f);//3
        gl.glVertex2f(0.31f, 0.026f); //2
        gl.glVertex2f(0.29f, 0f);//9
        gl.glVertex2f(0.28f, 0.016f);//8
        gl.glEnd();
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(0.24f, 0.026f);//4
        gl.glVertex2f(0.28f, 0.053f);//3
        gl.glVertex2f(0.28f, 0.016f);//8
        gl.glVertex2f(0.26f, 0f);//7
        gl.glEnd();

        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(0.24f, 0.026f);//4
        gl.glVertex2f(0.26f, 0f);//7
        gl.glVertex2f(0.27f, -0.03f);//6
        gl.glVertex2f(0.25f, -0.095f);//5
        gl.glEnd();
//Left Leg

        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(-0.04f, -0.42f);
        gl.glVertex2f(-0.04f, -0.05f);
        gl.glVertex2f(-0.14f, -0.05f);
        gl.glVertex2f(-0.14f, -0.42f);
        gl.glEnd();
// Right Lag

        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(0.04f, -0.42f);
        gl.glVertex2f(0.04f, -0.05f);
        gl.glVertex2f(0.14f, -0.05f);
        gl.glVertex2f(0.14f, -0.42f);
        gl.glEnd();

//left Foot
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(-0.167f, -0.53f);
        gl.glVertex2f(-0.119f, -0.47f);
        gl.glVertex2f(-0.05f, -0.47f);
        gl.glVertex2f(-0.005f, -0.53f);
        gl.glEnd();

//Right Foot
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(0.167f, -0.53f);
        gl.glVertex2f(0.119f, -0.47f);
        gl.glVertex2f(0.05f, -0.47f);
        gl.glVertex2f(0.005f, -0.53f);
        gl.glEnd();
//Breaks
        //Breaks Body
        // Brearks Left Body
        gl.glColor3f(0, 0, 0);
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(-0.12f, 0.49f);
        gl.glVertex2f(-0.13f, 0.43f);
        gl.glVertex2f(-0.1f, 0.43f);
        gl.glVertex2f(-0.1f, 0.49f);
        gl.glEnd();
        //Brearks Right Body
        gl.glColor3f(0, 0, 0);
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(0.072f, 0.49f);
        gl.glVertex2f(0.072f, 0.43f);
        gl.glVertex2f(0.095f, 0.43f);
        gl.glVertex2f(0.095f, 0.49f);
        gl.glEnd();
        //Breaks Arm
        // Brearks Left Arm
        gl.glColor3f(0, 0, 0);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(-0.23f, 0.34f);
        gl.glVertex2f(-0.23f, 0.32f);
        gl.glVertex2f(-0.17f, 0.32f);
        gl.glVertex2f(-0.17f, 0.34f);
        gl.glEnd();
        //Brearks Right Arm
        gl.glColor3f(0, 0, 0);
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex2f(0.23f, 0.34f);
        gl.glVertex2f(0.23f, 0.32f);
        gl.glVertex2f(0.17f, 0.32f);
        gl.glVertex2f(0.17f, 0.34f);
        gl.glEnd();
        //Breaks leg
        // Brearks Left leg
        gl.glColor3f(0, 0, 0);
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(-0.10f, 0.042f);
        gl.glVertex2f(-0.10f, -0.07f);
        gl.glVertex2f(-0.07f, -0.07f);
        gl.glVertex2f(-0.07f, 0.026f);
        gl.glEnd();
        //Brearks Right leg
        gl.glColor3f(0, 0, 0);
        gl.glBegin(GL2.GL_LINE_LOOP);
        gl.glVertex2f(0.10f, 0.042f);
        gl.glVertex2f(0.10f, -0.07f);
        gl.glVertex2f(0.07f, -0.07f);
        gl.glVertex2f(0.07f, 0.026f);
        gl.glEnd();

        gl.glPopMatrix();
        xx += 0.0005;
        if (xx >= 1.4) {
            xx = -xx;
        }
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {

    }

    @Override
    public void init(GLAutoDrawable drawable) {

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {

    }

    @Override
    public void keyTyped(KeyEvent ke) {
        char key = ke.getKeyChar();

        System.out.printf("Key typed: %c\n", key);

        switch (key) {

            case 'r':
                System.out.println("Changing Color to RED....");
                r = 1;
                g = 0;
                b = 0;
                break;
            case 'g':
                System.out.println("Changing Color to GREEN....");
                r = 0;
                g = 1;
                b = 0;

                break;
            case 'b':
                System.out.println("Changing Color to BLUE....");
                r = 0;
                g = 0;
                b = 1;
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
        if (me.getButton() == MouseEvent.BUTTON1) {
            XX = 1;
        }
        YY = 0;
        ZZ = 0;
        if (me.getButton() == MouseEvent.BUTTON3) {
            XX = 0;
        }
        YY = 0;
        ZZ = 0;

        System.out.println("(" + me.getX() + "," + me.getY() + ")");

    }

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
