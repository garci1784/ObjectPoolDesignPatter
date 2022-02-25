package PoolPattern;

public interface ObjectPool_IF {
    public int getSize();
    public int getCapacity();
    public void setCapacity(int c);
    public Object getObject();
    public Object waitForObject();
    public void release(Object o);
}
