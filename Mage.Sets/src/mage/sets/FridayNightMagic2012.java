package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f12
 */
public class FridayNightMagic2012 extends ExpansionSet {

    private static final FridayNightMagic2012 instance = new FridayNightMagic2012();

    public static FridayNightMagic2012 getInstance() {
        return instance;
    }

    private FridayNightMagic2012() {
        super("Friday Night Magic 2012", "F12", ExpansionSet.buildDate(2012, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Acidic Slime", 6, Rarity.RARE, mage.cards.a.AcidicSlime.class));
        cards.add(new SetCardInfo("Ancient Grudge", 5, Rarity.RARE, mage.cards.a.AncientGrudge.class));
        cards.add(new SetCardInfo("Avacyn's Pilgrim", 8, Rarity.RARE, mage.cards.a.AvacynsPilgrim.class));
        cards.add(new SetCardInfo("Despise", 2, Rarity.RARE, mage.cards.d.Despise.class));
        cards.add(new SetCardInfo("Dismember", 4, Rarity.RARE, mage.cards.d.Dismember.class));
        cards.add(new SetCardInfo("Evolving Wilds", 10, Rarity.RARE, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Forbidden Alchemy", 7, Rarity.RARE, mage.cards.f.ForbiddenAlchemy.class));
        cards.add(new SetCardInfo("Gitaxian Probe", 12, Rarity.RARE, mage.cards.g.GitaxianProbe.class));
        cards.add(new SetCardInfo("Glistener Elf", 1, Rarity.RARE, mage.cards.g.GlistenerElf.class));
        cards.add(new SetCardInfo("Lingering Souls", 9, Rarity.RARE, mage.cards.l.LingeringSouls.class));
        cards.add(new SetCardInfo("Pillar of Flame", 11, Rarity.RARE, mage.cards.p.PillarOfFlame.class));
        cards.add(new SetCardInfo("Tectonic Edge", 3, Rarity.RARE, mage.cards.t.TectonicEdge.class));
     }
}
