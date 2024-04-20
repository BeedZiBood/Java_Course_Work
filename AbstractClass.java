import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class AbstractClass extends Thread
{
    private volatile Integer state = 0;
    private boolean changed = false;
    private final Random rand = new Random();
    private Map<Integer, String> state_map = new HashMap<>();
    public AbstractClass()
    {
        state_map.put(0, "UNKNOWN");
        state_map.put(1, "STOPPING");
        state_map.put(2, "RUNNING");
        state_map.put(3, "FATAL ERROR");
    }
    /*public Runnable task = () ->
    {
        while (true)
        {
            synchronized (lock)
            {
                if (state == 0)
                {
                    System.out.println(state_map.get(state));
                    state = 1;
                }
                System.out.println(state_map.get(state));
                if (state == 2)
                {
                    try {
                        wait();
                    }
                    catch (InterruptedException e){
                    }
                }
                try
                {
                    Thread.sleep(1000);
                }
                catch (InterruptedException e)
                {
                    this.state = 3;
                    System.out.println(state_map.get(state));
                    break;
                }
                change_state();
            }
        }
    };*/
    @Override
    public void run()
    {
        while (true)
        {
            try{
                synchronized (this)
                {
                    change_state();
                    if (state == 1) {
                        wait();
                    }
                }
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                break;
            }
        }
    }
    public void print()
    {
        System.out.println(state_map.get(state));
    }
    public void set_state(Integer state)
    {
        this.state = state;
        changed = true;
    }
    private void change_state()
    {
        int temp = rand.nextInt(3) + 1;
        if (this.state == temp)
        {
            changed = false;
        }
        else
        {
            this.state = temp;
            changed = true;
        }
    }
    public boolean isChanged()
    {
        return changed;
    }
    public String get_State()
    {
        return state_map.get(state);
    }
}
