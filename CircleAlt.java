package lab_1;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.*;
import javax.media.opengl.awt.GLCanvas;
import com.sun.opengl.util.Animator;
import com.sun.opengl.util.FPSAnimator;

public class CircleAlt implements GLEventListener {

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

        canvas.addGLEventListener(new CircleAlt());

        Animator animator = new FPSAnimator(canvas,60);
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
        gl.glOrtho(0, 300, 0, 300, -1.0, 1.0);

    }

    protected void drawPoint(GL2 gl, float x, float y) {

        gl.glBegin (GL.GL_POINTS);
        gl.glColor3f(1, 1, 1);
        gl.glVertex2d(x, y);
        gl.glEnd();
    }

    private void render(GLAutoDrawable drawable) {

        GL2 gl = drawable.getGL().getGL2();

        int r= 100;
        int xc=150;
        int yc=150;

        drawPoint(gl,150,150);
        bresenhamsCircle(r,xc,yc,gl);
    }

    public void bresenhamsCircle( int r , int xc, int yc ,GL2 gl)
    {
        int x = r;
        int y = 0;
        int err = 1-x;

        while(x >= y)
        {
            drawPoint(gl,xc+x,yc+y);
            drawPoint(gl,xc+x,yc-y);
            drawPoint(gl,xc-x,yc+y);
            drawPoint(gl,xc-x,yc-y);

            drawPoint(gl,xc+y,yc+x);
            drawPoint(gl,xc+y,yc-x);
            drawPoint(gl,xc-y,yc+x);
            drawPoint(gl,xc-y,yc-x);

            y++;
            if (err<0.5)
            {
                err += 2 * y + 1;
            }

            else
            {
                x--;

                err = err +2* (y - x) + 1;
            }
        }
    }
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width,
                        int height) {
    }
}