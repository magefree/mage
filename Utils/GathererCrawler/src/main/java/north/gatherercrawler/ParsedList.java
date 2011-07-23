package north.gatherercrawler;

import java.util.concurrent.ConcurrentSkipListSet;

/**
 *
 * @author North
 */
public class ParsedList {

    private static final ParsedList instance = new ParsedList();
    private ConcurrentSkipListSet<Integer> list;

    public ParsedList() {
        list = new ConcurrentSkipListSet<Integer>();
    }

    public static void add(Integer element) {
        instance.list.add(element);
    }

    public static boolean contains(Integer value) {
        return instance.list.contains(value);
    }
}
