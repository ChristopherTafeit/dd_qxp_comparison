package common.utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Timer {
  private long startTime;
  private long endTime;

  public Timer() {
    startTime = 0;
    endTime = 0;
  }

  public void startTimer() {
    endTime = 0;
    startTime = System.currentTimeMillis();
  }

  public void endTimer() {
    endTime = System.currentTimeMillis();
  }

  public long getDuration() {
    if(endTime == 0)
      throw new RuntimeException("Stop timer first");
    return endTime - startTime;
  }
}
