package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pf23
 *
 * @author resech
 */
public class MagicFest2023 extends ExpansionSet {

    private static final MagicFest2023 instance = new MagicFest2023();

    public static MagicFest2023 getInstance() {
        return instance;
    }

    private MagicFest2023() {
        super("MagicFest 2023", "PF23", ExpansionSet.buildDate(2023, 7, 1), SetType.PROMOTIONAL);
        hasBasicLands = false;

        cards.add(new SetCardInfo("Gandalf, Friend of the Shire", 1, Rarity.RARE, mage.cards.g.GandalfFriendOfTheShire.class));
        cards.add(new SetCardInfo("Reliquary Tower", 3, Rarity.RARE, mage.cards.r.ReliquaryTower.class, FULL_ART));
        cards.add(new SetCardInfo("TARDIS", 4, Rarity.RARE, mage.cards.t.TARDIS.class));
        cards.add(new SetCardInfo("Tranquil Thicket", 2, Rarity.RARE, mage.cards.t.TranquilThicket.class));
    }
}
