import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import java.util.*;
import java.util.concurrent.*;
import javax.swing.JPanel;

import com.sun.tools.javac.Main;

public class parallelMandelbrot implements Runnable {

  private static int MAX_ITER = 100000;
  private static double ZOOM = 150;
  public static BufferedImage Imagen;
  private static int width;
  private static int height;

  private double zx, zy, cX, cY, tmp;
  private int iter;
  private int realnucleos;

  private static boolean parar = false;
  private static MainCanvas canvasRef;
  public static AnalyticsMultiChart population_chart_ref;

  private static int tamPool;
  private int tarea;
  private int in, fn;
  private static int numtareas;
  private static ThreadPoolExecutor miPool;
  private static CyclicBarrier barrera = null;

  private static int[] alives;
  //  public static XYSeries[] series;

  public void plug(MainCanvas ref) {
    canvasRef = ref;
  }

  public void ini_bm(int z, int mi) {

    width = 800;
    height = 600;

    Imagen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    ZOOM = z;
    MAX_ITER = mi;

    alives = new int[2];
    alives[0] = 0;
    alives[1] = 0;
  }

  public int[] get_alives() {
    return alives;
  }

  public void stop() {
    miPool.shutdownNow();
    parar = true;
  }

  public void start() {
    parar = false;
  }

  public void run() {

    // aqui comienza la rutina a paralelizar
    for (int y = in; y < fn; y++) {
      if (parar) break;

      for (int x = 0; x < width; x++) {
        if (parar) break;

        zx = zy = 0;
        cX = (x - 400) / ZOOM;
        cY = (y - 300) / ZOOM;
        iter = MAX_ITER;
        while (zx * zx + zy * zy < 4 && iter > 0) {
          if (parar) break;

          tmp = zx * zx - zy * zy + cX;
          zy = 2.0 * zx * zy + cY;
          zx = tmp;
          iter--;
        }

        Imagen.setRGB(x, y, iter | (iter << 8));
        canvasRef.validate();
        canvasRef.repaint();
      }
    } // aqui finaliza la rutina a paralelizar
  }

  public static void next_gen_concurrent(int nt) {

    tamPool = nt;

    // barrera = new CyclicBarrier (tamPool);
    numtareas = tamPool;

    miPool =
        new ThreadPoolExecutor(
            tamPool, tamPool, 60000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    parallelMandelbrot[] tareas = new parallelMandelbrot[nt];

    for (int t = 0; t < nt; t++) {
      tareas[t] = new parallelMandelbrot(t + 1);
      miPool.execute(tareas[t]);
    }

    miPool.shutdown();
    try {
      miPool.awaitTermination(10, TimeUnit.HOURS);
    } catch (Exception e) {
    }
  }

  public parallelMandelbrot(int i) {
    tarea = i;

    int paso = height / numtareas;

    fn = paso * tarea;
    in = fn - paso;

    if (numtareas == tarea) fn = height;

    System.out.println(in + " " + fn);
  }

  public parallelMandelbrot() {
      width = 800;
      height = 600;
      
  }

  public parallelMandelbrot(int z, int mi) {

    width = 800;
    height = 600;

    Imagen = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    // realnucleos = Runtime.getRuntime().availableProcessors();

    // System.out.println(realnucleos);

    ZOOM = z;
    MAX_ITER = mi;
    alives = new int[2];
    alives[0] = 0;
    alives[1] = 0;

    // long times[] = new long[4];

    // for(int i = 0 ; i < 4; i++)
    // {

    //   long startTime = System.currentTimeMillis();

    //   next_gen_concurrent(i+1);

    //   long endTime = System.currentTimeMillis();

    //   times[i] =endTime - startTime;
    // }

    // for(long time:times)
    //   System.out.println(time+'\n');

  }
}
