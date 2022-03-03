package PoolPattern;

/**
 *
 */
public class ObjectPool implements ObjectPool_IF{
    private static ObjectPool poolInstance; // static poolInstance variable *reflective assoc.
    ObjectCreation_IF creator;
    private Object lockObject;
    private int size; // how many free objects
    private int instanceCount; // how many objects have been created
    private int maxInstances; // maximum objects to be created
    private Object[] pool;

    public synchronized static ObjectPool getPoolInstance(ObjectCreation_IF c, int max){
        if (poolInstance==null)
            poolInstance = new ObjectPool(c, max);
        return poolInstance;
    }

    /**
     * Constructor
     *
     * @param c same as last lab. Interface's concrete class object is passed in and set to the
     *          current creator object of THIS(Object Pool) class.
     * @param max the maximum number of instances that this pool should allow to exist at one time.
     */
    private ObjectPool(ObjectCreation_IF c, int max){
        instanceCount = 0;

//        creator = c; // how is this used??
        this.creator = c; // LIKE THIS - less goooooo
        maxInstances=max;
        pool = new Object[maxInstances];
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
