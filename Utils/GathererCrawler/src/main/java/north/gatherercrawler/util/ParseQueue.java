package north.gatherercrawler.util;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author North
 */
public class ParseQueue {

    private static final ParseQueue instance = new ParseQueue();
    private ConcurrentLinkedQueue<Integer> queue;

    public ParseQueue() {
        queue = new ConcurrentLinkedQueue<Integer>();
    }

    public static void add(Integer element) {
        instance.queue.add(element);
    }

    public static Integer remove() {
        return instance.queue.remove();
    }

    public static boolean isEmpty(){
        return instance.queue.isEmpty();
    }
}
