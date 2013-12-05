package continousMining;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import static java.util.concurrent.TimeUnit.*;

public class Scheduler {
  private final ScheduledExecutorService scheduler =
    Executors.newScheduledThreadPool(1);

  public static void main(String[] args) {
    Scheduler sch = new Scheduler();
    sch.beepForAnHour();
  }
  public MiningTheContinuousStream beepForAnHour() {
    final Runnable beeper = new MiningTheContinuousStream();
    final ScheduledFuture<?> continousMiner =
    scheduler.scheduleAtFixedRate(beeper, 2, 30, SECONDS);
    scheduler.schedule(new Runnable() {
      public void run() { continousMiner.cancel(true); }
    }, 60*2, SECONDS);
    return (MiningTheContinuousStream) beeper;
  }
}
