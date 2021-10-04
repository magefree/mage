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

    public RarityConfiguration(BoosterStructure... items) {
        // change to false if we ever decide to generate sequential boosters
        super(true, items);
    }
}
