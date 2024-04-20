public class SuperVisor extends Thread {
    private final AbstractClass abs_cl = new AbstractClass();
    private String state;

    @Override
    public void run()
    {
        abs_cl.setDaemon(true);
        abs_cl.start();
        while (true)
        {
            synchronized (abs_cl)
            {
                state = abs_cl.get_State();
                boolean equalsUnknown = "UNKNOWN".equals(state);
                if (equalsUnknown) {
                    abs_cl.print();
                    abs_cl.set_state(2);
                }
                if (abs_cl.isChanged()) {
                    try {
                        sleep(100);
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                        break;
                    }
                    boolean equalsFatal = "FATAL ERROR".equals(state);
                    boolean equalsStopping = "STOPPING".equals(state);
                    if (equalsStopping) {
                        abs_cl.print();
                        abs_cl.set_state(2);
                        abs_cl.notifyAll();
                    } else if (equalsFatal) {
                        abs_cl.print();
                        break;
                    }
                    abs_cl.print();
                }
            }
        }
    }
}
