package opg10x4x1;

public class StopWatch { 

    private final long start;

   /**
     * Create a stopWatch object.
     */
    public StopWatch() {
        start = System.currentTimeMillis();
    } 


   /**
     * Return elapsed time (in seconds) since this object was created.
     */
    public double elapsedTime() {
        long now = System.currentTimeMillis();
        return (now - start) / 1000.0;
    }

} 
