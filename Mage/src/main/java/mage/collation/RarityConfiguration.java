package mage.collation;

/**
 * @author TheElk801
 */
public class RarityConfiguration extends Rotater<BoosterStructure> {

    public RarityConfiguration(BoosterStructure item) {
        super(item);
    }

    public RarityConfiguration(BoosterStructure item1, BoosterStructure item2) {
        super(item1, item2);
    }

    public RarityConfiguration(boolean keepOrder, BoosterStructure... items) {
        super(keepOrder, items);
    }

    @Override
    public void shuffle() {
        for (BoosterStructure structure : this.items) {
            structure.shuffle();
        }
        super.shuffle();
    }
}
