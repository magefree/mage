package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class TheBrothersWarCommander extends ExpansionSet {

    private static final TheBrothersWarCommander instance = new TheBrothersWarCommander();

    public static TheBrothersWarCommander getInstance() {
        return instance;
    }

    private TheBrothersWarCommander() {
        super("The Brothers' War Commander", "BRC", ExpansionSet.buildDate(2022, 11, 18), SetType.SUPPLEMENTAL);
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Baleful Strix", 120, Rarity.RARE, mage.cards.b.BalefulStrix.class));
        cards.add(new SetCardInfo("Command Tower", 178, Rarity.COMMON, mage.cards.c.CommandTower.class));
        cards.add(new SetCardInfo("Mishra, Eminent One", 1, Rarity.MYTHIC, mage.cards.m.MishraEminentOne.class));
        cards.add(new SetCardInfo("Preordain", 92, Rarity.COMMON, mage.cards.p.Preordain.class));
        cards.add(new SetCardInfo("Reliquary Tower", 196, Rarity.UNCOMMON, mage.cards.r.ReliquaryTower.class));
        cards.add(new SetCardInfo("Skullclamp", 159, Rarity.UNCOMMON, mage.cards.s.Skullclamp.class));
        cards.add(new SetCardInfo("Sol Ring", 160, Rarity.UNCOMMON, mage.cards.s.SolRing.class));
        cards.add(new SetCardInfo("Urza, Chief Artificer", 2, Rarity.MYTHIC, mage.cards.u.UrzaChiefArtificer.class));
    }
}
