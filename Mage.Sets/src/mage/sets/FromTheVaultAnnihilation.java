
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class FromTheVaultAnnihilation extends ExpansionSet {

    private static final FromTheVaultAnnihilation instance = new FromTheVaultAnnihilation();

    public static FromTheVaultAnnihilation getInstance() {
        return instance;
    }

    private FromTheVaultAnnihilation() {
        super("From the Vault: Annihilation", "V14", ExpansionSet.buildDate(2014, 8, 22), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Armageddon", 1, Rarity.MYTHIC, mage.cards.a.Armageddon.class));
        cards.add(new SetCardInfo("Burning of Xinye", 2, Rarity.MYTHIC, mage.cards.b.BurningOfXinye.class));
        cards.add(new SetCardInfo("Cataclysm", 3, Rarity.MYTHIC, mage.cards.c.Cataclysm.class));
        cards.add(new SetCardInfo("Child of Alara", 4, Rarity.MYTHIC, mage.cards.c.ChildOfAlara.class));
        cards.add(new SetCardInfo("Decree of Annihilation", 5, Rarity.MYTHIC, mage.cards.d.DecreeOfAnnihilation.class));
        cards.add(new SetCardInfo("Firespout", 6, Rarity.MYTHIC, mage.cards.f.Firespout.class));
        cards.add(new SetCardInfo("Fracturing Gust", 7, Rarity.MYTHIC, mage.cards.f.FracturingGust.class));
        cards.add(new SetCardInfo("Living Death", 8, Rarity.MYTHIC, mage.cards.l.LivingDeath.class));
        cards.add(new SetCardInfo("Martial Coup", 9, Rarity.MYTHIC, mage.cards.m.MartialCoup.class));
        cards.add(new SetCardInfo("Rolling Earthquake", 10, Rarity.MYTHIC, mage.cards.r.RollingEarthquake.class));
        cards.add(new SetCardInfo("Smokestack", 11, Rarity.MYTHIC, mage.cards.s.Smokestack.class));
        cards.add(new SetCardInfo("Terminus", 12, Rarity.MYTHIC, mage.cards.t.Terminus.class));
        cards.add(new SetCardInfo("Upheaval", 13, Rarity.MYTHIC, mage.cards.u.Upheaval.class));
        cards.add(new SetCardInfo("Virtue's Ruin", 14, Rarity.MYTHIC, mage.cards.v.VirtuesRuin.class));
        cards.add(new SetCardInfo("Wrath of God", 15, Rarity.MYTHIC, mage.cards.w.WrathOfGod.class));
    }
}
