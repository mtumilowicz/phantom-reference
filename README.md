# phantom-reference
The main goal of this project is to give simple example of Phantom References
in java.

# reference types
1. **Strong Reference** - any object with an active strong reference to it, will never be garbage collected.
1. **Soft Reference** - the garbage collector may optionally choose to reclaim the memory occupied by the employee object.
   ```
   SoftReference<X> ex = new SoftReference<>(x);
   ```
   _Remark_: Garbage collector will always reclaim the memory of objects that have only soft references pointing to them before it throws an `OutOfMemory Error`.
1. **Weak Reference** - an object that only has a week reference is eligible for garbage collection.
   ```
   WeakReference<X> ex = new WeakReference<>(x);
   ```
   _Remark_: [WeakHashMap](http://www.baeldung.com/java-weakhashmap)  
   _Remark_: [Lapsed listener problem](https://en.wikipedia.org/wiki/Lapsed_listener_problem)
1. **Phantom Reference** - phantom reference objects are enqueued after the collector determines that their referents may otherwise be reclaimed.
   ```
   final ReferenceQueue<X> queue = new ReferenceQueue<>();
   PhantomReference<X> ex = new PhantomReference<>(x, queue);
   ```

   _Remark_: It is used to determine when an object was removed from the memory which helps to schedule memory-sensitive tasks. For example, we can wait for a large object to be removed (reference will appear on the queue) before loading another one.  
   _Remark_: Calling a `get()` on the Phantom reference always returns `null`.

# description
Take a look at comments in `Main`:
1. We create list of large objects.
1. We create list of phantom references of large objects (with shared queue).
1. Then we make large objects eligible for garbage collecting, by nulling 
reference to list:
    ```
    largeObjects = null;
    ``` 
1. Then we notify garbage collection:
    ```
    System.gc();
    ```
1. We wait for garbage collector to remove all large objects - we 
are reading list of phantom references till all are queued:
    ```
    reference.isEnqueued()
    ```
1. At the end we are reading the queue, printing all references and 
clearing them:
    ```
    referenceFromQueue.clear();
    ```