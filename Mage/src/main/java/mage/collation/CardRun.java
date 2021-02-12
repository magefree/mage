package mage.collation;

/**
 * @author TheElk801
 */
public abstract class CardRun extends Rotater<Integer> {

    public CardRun(boolean keepOrder, Integer... names) {
        super(keepOrder, names);
    }
}
