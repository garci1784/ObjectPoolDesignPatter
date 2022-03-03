package AgentDemo;

import PoolPattern.ObjectPool;

import static java.lang.Thread.sleep;

public class TaskRequester {
    private ObjectPool server;

    public TaskRequester(ObjectPool p) {

        this.server = p;
    }

    public void run(){
        //TODO
        Agent_IF agent = (Agent_IF)server.waitForObject(); // what is going on with this line?
        agent.startTask();
        try {
            //
            sleep(2000); // simulate task processing
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        agent.stopTask();
        server.release(agent);
    }
}
