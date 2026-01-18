package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class TeenageMutantNinjaTurtlesSourceMaterial extends ExpansionSet {

    private static final TeenageMutantNinjaTurtlesSourceMaterial instance = new TeenageMutantNinjaTurtlesSourceMaterial();

    public static TeenageMutantNinjaTurtlesSourceMaterial getInstance() {
        return instance;
    }

    private TeenageMutantNinjaTurtlesSourceMaterial() {
        super("Teenage Mutant Ninja Turtles Source Material", "PZA", ExpansionSet.buildDate(2026, 3, 6), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        this.maxCardNumberInBooster = 64; // TODO: Update once more info is available

        cards.add(new SetCardInfo("Doubling Season", 11, Rarity.MYTHIC, mage.cards.d.DoublingSeason.class));
    }
}
