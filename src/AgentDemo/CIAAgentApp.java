package AgentDemo;

import PoolPattern.ObjectCreation_IF;
import PoolPattern.ObjectPool;

public class CIAAgentApp {
    public static void main(String[] args) {
        System.out.println("Main is running..."); // debugging
        ObjectPool server = ObjectPool.getPoolInstance(new CIA_Agent_Creator(),5);
        for(int i = 0; i<10; i++){
            Thread client = new Thread(new TaskRequester(server));
            client.start();
        }
        System.out.println("Reached the end.");
    }
}
