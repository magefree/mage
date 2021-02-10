package mage.collation;

/**
 * @author TheElk801
 */
public abstract class CardRun extends Rotater<String> {

    public CardRun(boolean keepOrder, String... names) {
        super(keepOrder, names);
    }
}
