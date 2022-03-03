package PoolPattern;
// check this site: https://git-scm.com/book/en/v2/Git-Branching-Rebasing
/**
 * TODO: Implement object pool - this is going to be the largest amount of code.
 * Singleton ObjectPool, to ensure desirable behavior or consistent state.
 * Manages costly object creation in a single place.
 */
public class ObjectPool implements ObjectPool_IF{

    // Dr. Fan told me to make this static, from the UML this is the -instance part
    // pointing back to the objectPool class.
    private static ObjectPool poolInstance; // static poolInstance variable *reflective assoc.


    ObjectCreation_IF creator;
    private Object lockObject = new Object(); // from textbook pg.171
    private int size; // how many free objects
    private int instanceCount; // how many objects have been created
    private int maxInstances; // maximum objects to be created

    private Object[] pool; // privately accessed pool of objects stored in an array.


    public synchronized static ObjectPool getPoolInstance(ObjectCreation_IF c, int max){
        if (poolInstance==null) // ensures that threads have no collisions
            poolInstance = new ObjectPool(c, max); //calls object pool constructor stores in THIS poolInstance(private)
        return poolInstance; // why do we return? poolInstance is static. something to do with singleton w/ mult. threading??
    } // getter

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
    /**
     * Return the number of objects in the pool awaiting reuse.
     *
     */
    public int getSize() {
        return size;
    }

    // TODO:
    @Override
    /**
     * return the maximum number of objects that may be in the pool awaiting reuse.
     */
    public int getCapacity() {
        return pool.length; // pool is stored in Object array - calling Array.length from java ADT.
    }

    @Override
    /**
     * Sets the maximum number of objects that may be in the pool awaiting reuse with error checking.
     *
     * uses synchronized keyword, check against slides from class
     * @param newValue The new value for maximum number of objects in pool awaiting reuse
     * pre: must be greater than zero
     */
    public void setCapacity(int newValue) throws IllegalArgumentException {
        if(newValue <= 0){
            throw new IllegalArgumentException("the value must be greater than zero " + newValue);
        }// if
        synchronized (lockObject){
            Object[] newPool = new Object[newValue];
            System.arraycopy(poolInstance,0, newPool,0,newValue); // look here
        }// synchronized
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
