package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://mtg.fandom.com/wiki/Mystery_Booster/Test_cards
 * https://magic.wizards.com/en/articles/archive/feature/unraveling-mystery-booster-2019-11-14
 * https://scryfall.com/sets/cmb1
 *
 */
public class MysteryBoosterPlaytest extends ExpansionSet {

    private static final MysteryBoosterPlaytest instance = new MysteryBoosterPlaytest();

    public static MysteryBoosterPlaytest getInstance() {
        return instance;
    }

    private MysteryBoosterPlaytest() {
        super("Mystery Booster Playtest", "CMB1", ExpansionSet.buildDate(2019, 11, 7), SetType.JOKESET);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Banding Sliver", 2, Rarity.RARE, mage.cards.b.BandingSliver.class));
        cards.add(new SetCardInfo("Evil Boros Charm", 90, Rarity.RARE, mage.cards.e.EvilBorosCharm.class));
        cards.add(new SetCardInfo("Frogkin Kidnapper", 42, Rarity.RARE, mage.cards.f.FrogkinKidnapper.class));
        cards.add(new SetCardInfo("How to Keep an Izzet Mage Busy", 93, Rarity.RARE, mage.cards.h.HowToKeepAnIzzetMageBusy.class));
        cards.add(new SetCardInfo("Innocuous Insect", 23, Rarity.RARE, mage.cards.i.InnocuousInsect.class));
        cards.add(new SetCardInfo("Lazier Goblin", 56, Rarity.RARE, mage.cards.l.LazierGoblin.class));
        cards.add(new SetCardInfo("Mirrored Lotus", 107, Rarity.RARE, mage.cards.m.MirroredLotus.class));
        cards.add(new SetCardInfo("Recycla-bird", 28, Rarity.RARE, mage.cards.r.RecyclaBird.class));
        cards.add(new SetCardInfo("Slivdrazi Monstrosity", 102, Rarity.RARE, mage.cards.s.SlivdraziMonstrosity.class));
        cards.add(new SetCardInfo("Unicycle", 110, Rarity.RARE, mage.cards.u.Unicycle.class));
    }

}
