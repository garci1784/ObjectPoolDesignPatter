package AgentDemo;

import static java.lang.Thread.sleep;

/**
 *
 */
@SuppressWarnings("DuplicatedCode")
public class CIA_Agent implements Agent_IF, Runnable{
    private Boolean workingInProgress;
    private String myFootPrint;
    private static int id; // static??

    public CIA_Agent(String myFootPrint) {
        this.myFootPrint = myFootPrint;
    }

    @Override
    public void run() {
        while(true){
            if(workingInProgress) {
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(myFootPrint);
            }
            else{
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void processing(){
        System.out.println("\nProcessing...\n");
    }

    @Override
    public void startTask() {
        workingInProgress = true;
    }

    @Override
    public void stopTask() {
        workingInProgress = false;
    }

    @Override
    public void setTask(int id) {
        this.id = id;
    }
}
