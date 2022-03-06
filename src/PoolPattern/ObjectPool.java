package PoolPattern;
// check this site: https://git-scm.com/book/en/v2/Git-Branching-Rebasing

import java.lang.ref.SoftReference;
import java.util.ArrayList;

/**
 * Singleton ObjectPool, to ensure desirable behavior or consistent state.
 * Manages costly object creation in a single place.
 */
public class ObjectPool implements ObjectPool_IF{

    // Dr. Fan told me to make this static, from the UML this is the -instance part
    // pointing back to the objectPool class.
    private static ObjectPool poolInstance; // static poolInstance variable *reflective assoc.


    ObjectCreation_IF creator;
    private Object lockObject = new Object(); // from textbook pg.171 limited pool - NOT FOR THIS LAB

    private int instanceCount; // how many objects have been created
    private int maxInstances; // maximum objects to be created

    private ArrayList pool; // privately accessed pool of objects stored in an array.

    private Class poolClass; //???

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
        pool = new ArrayList();
    }


    @Override
    /**
     * Return the number of objects in the pool awaiting reuse.
     *
     */
    public int getSize() {

        synchronized (pool){
            return pool.size();
        }
    }

    @Override
    /**
     * return the maximum number of objects that may be in the pool awaiting reuse.
     */
    public int getCapacity() {
        return maxInstances; // pool is stored in Object array - calling Array.length from java ADT.
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
        }
        else
        maxInstances = newValue;
        // if
        /*synchronized (lockObject){
            Object[] newPool = new Object[newValue];
            System.arraycopy(poolInstance,0, newPool,0,newValue); // look here*/
        //}// synchronized
    }

    /**
     * create object to be managed by this pool
     */
    public Object createObject(){
        Object newObject = creator.create();
        instanceCount++;
        return newObject;
    }
    /**
     *
     * @return
     */
    @Override
    public Object getObject() {
        synchronized (pool) {
            Object thisObject = removeObject();
            if (thisObject != null) {
                return thisObject;
            }
            if (getInstanceCount() < getCapacity()) {
                return createObject();
            }
            else{
                return null;
            } // if
        } // synchronized
    }// getObject

    public int getInstanceCount(){
        return instanceCount;
    }

    /**
     * returns an object from the pool, if instance count is greater than max amount,
     * then this method will wait until an object becomes available for reuse
     *
     * @return
     */
    @Override
    public Object waitForObject() throws InterruptedException{
        synchronized (pool) {
            Object thisObject = removeObject();
            if(thisObject!=null){
                return thisObject;
            }
            if(getInstanceCount() < getCapacity()){ // getCapacity returns maxInstances
                return createObject();
            }
            else{
                do{
                    pool.wait();
                    thisObject = removeObject();
                }while(thisObject==null);
                return thisObject;
            }
        }
    }

    /**
     * release an object to the pool for reuse
     *
     * @param obj
     */
    @Override
    public void release(Object obj) {
        if(obj == null){
            throw new NullPointerException();
        }
        if(!poolClass.isInstance(obj)){ // check if that instance of obj matches
            String actualClassName = obj.getClass().getName(); // way to call getter on an unknown class, class knows about itself.
            throw new ArrayStoreException(actualClassName); // throws exception and shows user the name of class they passed
        }
        synchronized (pool){
            pool.add(obj);
            pool.notify(); // interesting
        }
    }

    /**
     * remove an object from the pool array and return it
     *
     * @return
     */
    private Object removeObject(){
        while(pool.size() > 0){
            SoftReference thisRef = (SoftReference) pool.remove(pool.size()-1);
            Object thisObject = thisRef.get();
            if(thisObject!=null){
                return thisObject;
            }
            instanceCount--;
        } // while
        return null; // keep compiler happy
    }
}
