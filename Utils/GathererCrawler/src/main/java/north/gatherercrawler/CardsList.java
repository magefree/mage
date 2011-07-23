package north.gatherercrawler;

import java.util.Iterator;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 *
 * @author North
 */
public class CardsList {

    private static final CardsList instance = new CardsList();
    private ConcurrentSkipListSet<Card> list;

    public CardsList() {
        list = new ConcurrentSkipListSet<Card>();
    }

    public static void add(Card element) {
        instance.list.add(element);
    }

    public static Iterator<Card> iterator() {
        return instance.list.iterator();
    }
}
