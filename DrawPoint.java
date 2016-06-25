import com.sun.opengl.util.Animator;
import static java.lang.System.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.Math;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import javax.media.opengl.fixedfunc.GLMatrixFunc;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class DrawPoint extends JFrame
        implements  GLEventListener, Runnable {

    protected String title;
    protected int height, width;
    protected GL2 gl;
    protected GLCanvas canvas;
    protected GLCapabilities capabilities;
    protected boolean startAnimator ;
    protected Animator animator;

    public DrawPoint(int width, int height, String title, boolean startAnimator) {

        this.title = title;
        this.height = height;
        this.width = width;
        this.startAnimator = startAnimator;

        capabilities = new GLCapabilities(GLProfile.getDefault());
        capabilities.setDoubleBuffered(false);
        out.println(capabilities.toString());

        canvas = new GLCanvas(capabilities);
        canvas.addGLEventListener(this);

        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosed(WindowEvent e) {

                super.windowClosed(e);
                if(animator.isAnimating()) animator.stop();
                exit(0);
            }
        });
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new DrawPoint(300, 300, "Draw Point", true));
    }

    public void run() {

        setTitle(title);
        setSize(height, width);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().add(canvas, BorderLayout.CENTER);
        canvas.requestFocusInWindow();
        setVisible(true);
    }

    @Override
    public void init(GLAutoDrawable glAutoDrawable) {

        gl = canvas.getGL().getGL2();
        gl.glClearColor(0, 0, 0, 0);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        animator = new Animator(canvas);
        if(startAnimator) animator.start();
    }

    @Override
    public void reshape(GLAutoDrawable glAutoDrawable, int x, int y, int width, int height) {

        gl.glClear(GL.GL_COLOR_BUFFER_BIT);
        gl.glMatrixMode(GLMatrixFunc.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrtho(0, width, 0, height, -1.0, 1.0);
        this.width = width;
        this.height = height;
    }

    @Override
    public void display(GLAutoDrawable glAutoDrawable) {

        drawPoint(random(width), random(height), random(1f), random(1f), random(1f));
    }

    protected void drawPoint(int x, int y, float red, float green, float blue) {

        gl.glBegin(GL.GL_POINTS);
        gl.glColor3f(red, green, blue);
        gl.glVertex2i(x, y);
        gl.glEnd();
    }

    protected float random(float range) {

        return (float) Math.round((range) * Math.random());
    }

    protected int random(int range) {

        return (int) Math.round((range) * Math.random());
    }

    @Override
    public void dispose(GLAutoDrawable glAutoDrawable) {

    }
}
