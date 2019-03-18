
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class FromTheVaultRelics extends ExpansionSet {

    private static final FromTheVaultRelics instance = new FromTheVaultRelics();

    public static FromTheVaultRelics getInstance() {
        return instance;
    }

    private FromTheVaultRelics() {
        super("From the Vault: Relics", "V10", ExpansionSet.buildDate(2010, 8, 27), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Aether Vial", 1, Rarity.MYTHIC, mage.cards.a.AetherVial.class));
        cards.add(new SetCardInfo("Black Vise", 2, Rarity.MYTHIC, mage.cards.b.BlackVise.class));
        cards.add(new SetCardInfo("Isochron Scepter", 3, Rarity.MYTHIC, mage.cards.i.IsochronScepter.class));
        cards.add(new SetCardInfo("Ivory Tower", 4, Rarity.MYTHIC, mage.cards.i.IvoryTower.class));
        cards.add(new SetCardInfo("Jester's Cap", 5, Rarity.MYTHIC, mage.cards.j.JestersCap.class));
        cards.add(new SetCardInfo("Karn, Silver Golem", 6, Rarity.MYTHIC, mage.cards.k.KarnSilverGolem.class));
        cards.add(new SetCardInfo("Masticore", 7, Rarity.MYTHIC, mage.cards.m.Masticore.class));
        cards.add(new SetCardInfo("Memory Jar", 8, Rarity.MYTHIC, mage.cards.m.MemoryJar.class));
        cards.add(new SetCardInfo("Mirari", 9, Rarity.MYTHIC, mage.cards.m.Mirari.class));
        cards.add(new SetCardInfo("Mox Diamond", 10, Rarity.MYTHIC, mage.cards.m.MoxDiamond.class));
        cards.add(new SetCardInfo("Nevinyrral's Disk", 11, Rarity.MYTHIC, mage.cards.n.NevinyrralsDisk.class));
        cards.add(new SetCardInfo("Sol Ring", 12, Rarity.MYTHIC, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Sundering Titan", 13, Rarity.MYTHIC, mage.cards.s.SunderingTitan.class));
        cards.add(new SetCardInfo("Sword of Body and Mind", 14, Rarity.MYTHIC, mage.cards.s.SwordOfBodyAndMind.class));
        cards.add(new SetCardInfo("Zuran Orb", 15, Rarity.MYTHIC, mage.cards.z.ZuranOrb.class));
    }
}
