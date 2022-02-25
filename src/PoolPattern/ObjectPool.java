package PoolPattern;

public class ObjectPool implements ObjectPool_IF{
    private Object lockObject;
    private int size; // how many free objects
    private int instanceCount; // how many objects have been created
    private int maxInstances; // maximum objects to be created
    private Object[] pool;

    public ObjectPool(ObjectCreation_IF c, int max) {
    }

    @Override
    public int getSize() {
        return size;
    }

    // TODO:
    @Override
    public int getCapacity() {
        return maxInstances;//  ????
    }

    @Override
    public void setCapacity(int c) {
        this.maxInstances = c;
    }

    @Override
    public Object getObject() {
        return null;
    }

    @Override
    public Object waitForObject() {
        return null;
    }

    @Override
    public void release(Object o) {

    }
}
