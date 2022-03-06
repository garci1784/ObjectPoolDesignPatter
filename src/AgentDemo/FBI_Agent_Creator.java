package AgentDemo;

public class FBI_Agent_Creator {
    private String[] footPrints = {"@", "#", "$", "*", ".", "?"};
    private int index;

    public Object create() {
        FBI_Agent agent = new FBI_Agent(footPrints[index++]);
        new Thread(agent).start();
        return agent;
    }
}
