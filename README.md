# phantom-reference
The main goal of this project is to give simple example of Phantom References
in java.

# details
[Java Reference Types](https://github.com/mtumilowicz/clean-code-impressions/wiki/Reference-types)

# description
Take a look at comments in `Main`:
1. We create list of large objects.
1. We create list of phantom references of large objects (with shared queue).
1. Then we make large objects eligible for garbage collecting, by nulling 
reference to list
    ```
    largeObjects = null;
    ``` 
1. Then we notify garbage collection:
    ```
    System.gc();
    ```
1. We wait for garbage collector of all large objects by reading list of
phantom references till all phantom references will be queued:
    ```
    reference.isEnqueued()
    ```
1. At the end we reading the queue, printing all references and clearing them
    ```
    referenceFromQueue.clear();
    ```