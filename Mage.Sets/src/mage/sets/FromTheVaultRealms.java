
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class FromTheVaultRealms extends ExpansionSet {

    private static final FromTheVaultRealms instance = new FromTheVaultRealms();

    public static FromTheVaultRealms getInstance() {
        return instance;
    }

    private FromTheVaultRealms() {
        super("From the Vault: Realms", "V12", ExpansionSet.buildDate(2012, 8, 31), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ancient Tomb", 1, Rarity.MYTHIC, mage.cards.a.AncientTomb.class));
        cards.add(new SetCardInfo("Boseiju, Who Shelters All", 2, Rarity.MYTHIC, mage.cards.b.BoseijuWhoSheltersAll.class));
        cards.add(new SetCardInfo("Cephalid Coliseum", 3, Rarity.MYTHIC, mage.cards.c.CephalidColiseum.class));
        cards.add(new SetCardInfo("Desert", 4, Rarity.MYTHIC, mage.cards.d.Desert.class));
        cards.add(new SetCardInfo("Dryad Arbor", 5, Rarity.MYTHIC, mage.cards.d.DryadArbor.class));
        cards.add(new SetCardInfo("Forbidden Orchard", 6, Rarity.MYTHIC, mage.cards.f.ForbiddenOrchard.class));
        cards.add(new SetCardInfo("Glacial Chasm", 7, Rarity.MYTHIC, mage.cards.g.GlacialChasm.class));
        cards.add(new SetCardInfo("Grove of the Burnwillows", 8, Rarity.MYTHIC, mage.cards.g.GroveOfTheBurnwillows.class));
        cards.add(new SetCardInfo("High Market", 9, Rarity.MYTHIC, mage.cards.h.HighMarket.class));
        cards.add(new SetCardInfo("Maze of Ith", 10, Rarity.MYTHIC, mage.cards.m.MazeOfIth.class));
        cards.add(new SetCardInfo("Murmuring Bosk", 11, Rarity.MYTHIC, mage.cards.m.MurmuringBosk.class));
        cards.add(new SetCardInfo("Shivan Gorge", 12, Rarity.MYTHIC, mage.cards.s.ShivanGorge.class));
        cards.add(new SetCardInfo("Urborg, Tomb of Yawgmoth", 13, Rarity.MYTHIC, mage.cards.u.UrborgTombOfYawgmoth.class));
        cards.add(new SetCardInfo("Vesuva", 14, Rarity.MYTHIC, mage.cards.v.Vesuva.class));
        cards.add(new SetCardInfo("Windbrisk Heights", 15, Rarity.MYTHIC, mage.cards.w.WindbriskHeights.class));
    }
}
