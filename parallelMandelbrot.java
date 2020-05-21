import java.awt.image.BufferedImage;
import java.util.concurrent.*;

public class parallelMandelbrot implements Runnable {

  private static int MAX_ITER = 100000;
  private static double ZOOM = 150;
  public static BufferedImage image;
  private static int width;
  private static int height;

  private double zx, zy, cX, cY, tmp;
  private int iter;

  private static boolean abort = false;
  private static MainCanvas canvasRef;
  public static AnalyticsMultiChart population_chart_ref;

  private static int tamPool;
  private int task;
  private int in, fn;
  private static int totalTaskNumber;
  private static ThreadPoolExecutor miPool;
  private static boolean benchmarkMode = false;

  public BufferedImage getData() {
    return image;
  }

  public void plug(MainCanvas ref) {
    canvasRef = ref;
  }

  public void stop() {
    abort = true;
  }

  public void setBenchmarkMode(boolean benchmarkMode) {
    parallelMandelbrot.benchmarkMode = benchmarkMode;
  }

  public void run() {

    for (int y = in; y < fn; y++) {
      if (abort) break;

      for (int x = 0; x < width; x++) {
        if (abort) break;

        zx = zy = 0;
        cX = (x - 400) / ZOOM;
        cY = (y - 300) / ZOOM;
        iter = MAX_ITER;
        while (zx * zx + zy * zy < 4 && iter > 0) {
          if (abort) break;

          tmp = zx * zx - zy * zy + cX;
          zy = 2.0 * zx * zy + cY;
          zx = tmp;
          iter--;
        }

        if (!benchmarkMode) {
          image.setRGB(x, y, iter | (iter << 8));
          canvasRef.validate();
          canvasRef.repaint();
        }
      }
    }
  }

  public static void nextGenConcurrent(int nt) {
    abort = false;
    tamPool = nt;

    totalTaskNumber = tamPool;

    miPool =
        new ThreadPoolExecutor(
            tamPool, tamPool, 60000L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
    parallelMandelbrot[] tasks = new parallelMandelbrot[nt];

    for (int t = 0; t < nt; t++) {
      tasks[t] = new parallelMandelbrot(t + 1);
      miPool.execute(tasks[t]);
    }

    miPool.shutdown();
    try {
      miPool.awaitTermination(10, TimeUnit.HOURS);
    } catch (Exception e) {
    }
  }

  public parallelMandelbrot(int i) {
    task = i;

    int paso = height / totalTaskNumber;

    fn = paso * task;
    in = fn - paso;

    if (totalTaskNumber == task) fn = height;

    System.out.println(in + " " + fn);
  }

  public parallelMandelbrot() {
    width = 800;
    height = 600;
  }

  public void initializer(int z, int mi) {

    width = 800;
    height = 600;

    image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

    ZOOM = z;
    MAX_ITER = mi;
  }
}
