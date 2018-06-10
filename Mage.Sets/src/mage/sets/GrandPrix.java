
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public final class GrandPrix extends ExpansionSet {

    private static final GrandPrix instance = new GrandPrix();

    public static GrandPrix getInstance() {
        return instance;
    }

    private GrandPrix() {
        super("Grand Prix", "GPX", ExpansionSet.buildDate(2011, 6, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("All Is Dust", 9, Rarity.MYTHIC, mage.cards.a.AllIsDust.class));
        cards.add(new SetCardInfo("Batterskull", 10, Rarity.MYTHIC, mage.cards.b.Batterskull.class));
        cards.add(new SetCardInfo("Call of the Herd", 2, Rarity.RARE, mage.cards.c.CallOfTheHerd.class));
        cards.add(new SetCardInfo("Chrome Mox", 3, Rarity.RARE, mage.cards.c.ChromeMox.class));
        cards.add(new SetCardInfo("Progenitus", 13, Rarity.MYTHIC, mage.cards.p.Progenitus.class));
        cards.add(new SetCardInfo("Goblin Guide", 6, Rarity.RARE, mage.cards.g.GoblinGuide.class));
        cards.add(new SetCardInfo("Griselbrand", 11, Rarity.MYTHIC, mage.cards.g.Griselbrand.class));
        cards.add(new SetCardInfo("Lotus Cobra", 7, Rarity.MYTHIC, mage.cards.l.LotusCobra.class));
        cards.add(new SetCardInfo("Maelstrom Pulse", 5, Rarity.RARE, mage.cards.m.MaelstromPulse.class));
        cards.add(new SetCardInfo("Primeval Titan", 8, Rarity.MYTHIC, mage.cards.p.PrimevalTitan.class));
        cards.add(new SetCardInfo("Spiritmonger", 1, Rarity.RARE, mage.cards.s.Spiritmonger.class));
        cards.add(new SetCardInfo("Stoneforge Mystic", 12, Rarity.RARE, mage.cards.s.StoneforgeMystic.class));
        cards.add(new SetCardInfo("Umezawa's Jitte", 4, Rarity.RARE, mage.cards.u.UmezawasJitte.class));
    }

}
