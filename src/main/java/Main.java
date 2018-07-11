import org.apache.commons.collections4.CollectionUtils;

import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static java.util.Objects.nonNull;

/**
 * Created by mtumilowicz on 2018-07-11.
 */
public class Main {
    public static void main(String[] args) {
        ReferenceQueue<LargeObject> referenceQueue = new ReferenceQueue<>();
        List<LargeObjectPhantomReference> references = new ArrayList<>();
        List<LargeObject> largeObjects = new ArrayList<>();

        final HashSet<Integer> ids = new HashSet<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        
        for (Integer id : ids) {
            LargeObject largeObject = new LargeObject(id);
            largeObjects.add(largeObject);
            references.add(new LargeObjectPhantomReference(largeObject, referenceQueue));
        }

        // eligible for garbage collection
        largeObjects = null;
        
        // notify garbage collection
        System.gc();

        // waiting for garbage collection
        while (CollectionUtils.isNotEmpty(ids)) {
            for (LargeObjectPhantomReference reference : references) {
                if (reference.isEnqueued()) {
                    ids.remove(reference.getId());
                }
            }
        }

        // read the queue and clear references
        Reference<?> referenceFromQueue;
        while (nonNull((referenceFromQueue = referenceQueue.poll()))) {
            System.out.println(referenceFromQueue);
            // remove permanently (object will not be enqueued)
            referenceFromQueue.clear();
        }
    }
}
