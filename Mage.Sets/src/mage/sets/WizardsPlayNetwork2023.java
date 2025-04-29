package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pw23
 */
public class WizardsPlayNetwork2023 extends ExpansionSet {

    private static final WizardsPlayNetwork2023 instance = new WizardsPlayNetwork2023();

    public static WizardsPlayNetwork2023 getInstance() {
        return instance;
    }

    private WizardsPlayNetwork2023() {
        super("Wizards Play Network 2023", "PW23", ExpansionSet.buildDate(2023, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Beast Within", 3, Rarity.RARE, mage.cards.b.BeastWithin.class));
        cards.add(new SetCardInfo("Cultivate", 6, Rarity.RARE, mage.cards.c.Cultivate.class));
        cards.add(new SetCardInfo("Drown in the Loch", 4, Rarity.RARE, mage.cards.d.DrownInTheLoch.class, RETRO_ART));
        cards.add(new SetCardInfo("Ice Out", 7, Rarity.RARE, mage.cards.i.IceOut.class));
        cards.add(new SetCardInfo("Lifecrafter's Bestiary", 2, Rarity.RARE, mage.cards.l.LifecraftersBestiary.class, RETRO_ART));
        cards.add(new SetCardInfo("Norn's Annex", 1, Rarity.RARE, mage.cards.n.NornsAnnex.class));
        cards.add(new SetCardInfo("Pyroblast", 8, Rarity.RARE, mage.cards.p.Pyroblast.class));
        cards.add(new SetCardInfo("Rampant Growth", 9, Rarity.RARE, mage.cards.r.RampantGrowth.class));
        cards.add(new SetCardInfo("Ravenous Chupacabra", 10, Rarity.RARE, mage.cards.r.RavenousChupacabra.class, RETRO_ART));
        cards.add(new SetCardInfo("Syr Konrad, the Grim", 5, Rarity.RARE, mage.cards.s.SyrKonradTheGrim.class, RETRO_ART));
        cards.add(new SetCardInfo("Unclaimed Territory", 11, Rarity.RARE, mage.cards.u.UnclaimedTerritory.class, RETRO_ART));
    }
}
