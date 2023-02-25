package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarchOfTheMachineCommander extends ExpansionSet {

    private static final MarchOfTheMachineCommander instance = new MarchOfTheMachineCommander();

    public static MarchOfTheMachineCommander getInstance() {
        return instance;
    }

    private MarchOfTheMachineCommander() {
        super("March of the Machine Commander", "MOC", ExpansionSet.buildDate(2023, 4, 21), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Slimefoot and Squee", 447, Rarity.MYTHIC, mage.cards.s.SlimefootAndSquee.class));
    }
}
