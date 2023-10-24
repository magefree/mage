
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author Susucr
 */
public final class LostCavernsOfIxalanCommander extends ExpansionSet {

    private static final LostCavernsOfIxalanCommander instance = new LostCavernsOfIxalanCommander();

    public static LostCavernsOfIxalanCommander getInstance() {
        return instance;
    }

    private LostCavernsOfIxalanCommander() {
        super("Lost Caverns of Ixalan Commander", "LCC", ExpansionSet.buildDate(2023, 11, 17), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Amulet of Vigor", 103, Rarity.RARE, mage.cards.a.AmuletOfVigor.class));
        cards.add(new SetCardInfo("Arcane Signet", 104, Rarity.UNCOMMON, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Chalice of the Void", 105, Rarity.MYTHIC, mage.cards.c.ChaliceOfTheVoid.class));
        cards.add(new SetCardInfo("Coercive Portal", 109, Rarity.MYTHIC, mage.cards.c.CoercivePortal.class));
        cards.add(new SetCardInfo("Expedition Map", 112, Rarity.UNCOMMON, mage.cards.e.ExpeditionMap.class));
    }
}
