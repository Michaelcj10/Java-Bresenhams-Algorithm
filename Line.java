import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

public class Line implements GLEventListener {

    public static void main(String[] args) {

        GLProfile glp = GLProfile.getDefault();
        GLCapabilities caps = new GLCapabilities(glp);
        GLCanvas canvas = new GLCanvas(caps);

        Frame frame = new Frame("AWT Window Test");
        frame.setSize(600, 600);
        frame.add(canvas);
        frame.setVisible(true);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        canvas.addGLEventListener(new Line());

        Animator animator = new FPSAnimator(canvas, 60);
        animator.add(canvas);
        animator.start();
    }

    @Override
    public void display(GLAutoDrawable drawable) {

        render(drawable);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }

    @Override
    public void init(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();

        gl.glLoadIdentity();
        gl.glOrtho(0, 600, 0, 600, -1.0, 1.0);
    }

    protected void drawPoint(GL2 gl, float x, float y) {

        gl.glBegin(GL.GL_POINTS);
        gl.glColor3f(1, 1, 1);
        gl.glVertex2d(x, y);
        gl.glEnd();
    }

    private void render(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();
        int x0 = 0;
        int x1 = 200;
        int y0 = 0;
        int y1 = 170;
        int dy = y1 - y0;
        int dx = x1 - x0;
        int m = 0;

        bresenhamsLine(x0, x1, y0, y1, dx, dy, m, gl);
    }

    public void bresenhamsLine(int x0, int x1, int y0, int y1, int dx, int dy, int m, GL2 gl) {

        drawPoint(gl, x0, y0);
        int p = 2 * dy - dx;
        int X;
        int Y;
        X = 0;
        Y = 0;

        drawPoint(gl, X, Y);
        while (X < x1) {

            X += 1;
            if (p < 0) {
                p = p + 2 * dy;
            } else {
                Y += 1;
                p = p + 2 * (dy - dx);
            }

            drawPoint(gl, X, Y);
        }
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
                        int height) {
    }
}
