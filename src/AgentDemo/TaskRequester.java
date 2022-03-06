package AgentDemo;

import PoolPattern.ObjectPool;

import static java.lang.Thread.sleep;

public class TaskRequester implements Runnable{
    private ObjectPool server;

    public TaskRequester(ObjectPool p) {

        this.server = p;
    }

    public void run(){
        Agent_IF agent = null; // what is going on with this line?
        try {
            agent = (Agent_IF)server.waitForObject();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        agent.startTask();
        try {
            sleep(2000); // simulate task processing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        agent.stopTask();
        server.release(agent);
    }
}
