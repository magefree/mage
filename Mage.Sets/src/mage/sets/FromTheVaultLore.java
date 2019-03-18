
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class FromTheVaultLore extends ExpansionSet {

    private static final FromTheVaultLore instance = new FromTheVaultLore();

    public static FromTheVaultLore getInstance() {
        return instance;
    }

    private FromTheVaultLore() {
        super("From the Vault: Lore", "V16", ExpansionSet.buildDate(2016, 8, 19), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Beseech the Queen", 1, Rarity.MYTHIC, mage.cards.b.BeseechTheQueen.class));
        cards.add(new SetCardInfo("Cabal Ritual", 2, Rarity.MYTHIC, mage.cards.c.CabalRitual.class));
        cards.add(new SetCardInfo("Conflux", 3, Rarity.MYTHIC, mage.cards.c.Conflux.class));
        cards.add(new SetCardInfo("Dark Depths", 4, Rarity.MYTHIC, mage.cards.d.DarkDepths.class));
        cards.add(new SetCardInfo("Glissa, the Traitor", 5, Rarity.MYTHIC, mage.cards.g.GlissaTheTraitor.class));
        cards.add(new SetCardInfo("Helvault", 6, Rarity.MYTHIC, mage.cards.h.Helvault.class));
        cards.add(new SetCardInfo("Memnarch", 7, Rarity.MYTHIC, mage.cards.m.Memnarch.class));
        cards.add(new SetCardInfo("Mind's Desire", 8, Rarity.MYTHIC, mage.cards.m.MindsDesire.class));
        cards.add(new SetCardInfo("Momir Vig, Simic Visionary", 9, Rarity.MYTHIC, mage.cards.m.MomirVigSimicVisionary.class));
        cards.add(new SetCardInfo("Near-Death Experience", 10, Rarity.MYTHIC, mage.cards.n.NearDeathExperience.class));
        cards.add(new SetCardInfo("Obliterate", 11, Rarity.MYTHIC, mage.cards.o.Obliterate.class));
        cards.add(new SetCardInfo("Phyrexian Processor", 12, Rarity.MYTHIC, mage.cards.p.PhyrexianProcessor.class));
        cards.add(new SetCardInfo("Tolaria West", 13, Rarity.MYTHIC, mage.cards.t.TolariaWest.class));
        cards.add(new SetCardInfo("Umezawa's Jitte", 14, Rarity.MYTHIC, mage.cards.u.UmezawasJitte.class));
        cards.add(new SetCardInfo("Unmask", 15, Rarity.MYTHIC, mage.cards.u.Unmask.class));
    }
}
