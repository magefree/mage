package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pf25
 *
 * @author Jmlundeen
 */
public class MagicFest2025 extends ExpansionSet {

    private static final MagicFest2025 instance = new MagicFest2025();

    public static MagicFest2025 getInstance() {
        return instance;
    }

    private MagicFest2025() {
        super("MagicFest 2025", "PF25", ExpansionSet.buildDate(2025, 2, 3), SetType.PROMOTIONAL);
        hasBasicLands = false;

        cards.add(new SetCardInfo("Arcane Signet", 10, Rarity.RARE, mage.cards.a.ArcaneSignet.class));
        cards.add(new SetCardInfo("Avacyn's Pilgrim", "1F", Rarity.RARE, mage.cards.a.AvacynsPilgrim.class, FULL_ART));
        cards.add(new SetCardInfo("Lightning Bolt", 13, Rarity.RARE, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Ponder", 2, Rarity.RARE, mage.cards.p.Ponder.class));
        cards.add(new SetCardInfo("Rograkh, Son of Rohgahh", 11, Rarity.RARE, mage.cards.r.RograkhSonOfRohgahh.class));
        cards.add(new SetCardInfo("Scourge of Valkas", 14, Rarity.RARE, mage.cards.s.ScourgeOfValkas.class, RETRO_ART));
        cards.add(new SetCardInfo("Serra the Benevolent", 1, Rarity.MYTHIC, mage.cards.s.SerraTheBenevolent.class, RETRO_ART));
        cards.add(new SetCardInfo("Sliver Hive", 7, Rarity.RARE, mage.cards.s.SliverHive.class, RETRO_ART));
        cards.add(new SetCardInfo("Swords to Plowshares", 12, Rarity.RARE, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("The First Sliver", 3, Rarity.MYTHIC, mage.cards.t.TheFirstSliver.class));
        cards.add(new SetCardInfo("The Ur-Dragon", 15, Rarity.MYTHIC, mage.cards.t.TheUrDragon.class));
        cards.add(new SetCardInfo("Tifa Lockhart", 9, Rarity.RARE, mage.cards.t.TifaLockhart.class));
        cards.add(new SetCardInfo("Ugin, the Spirit Dragon", 6, Rarity.MYTHIC, mage.cards.u.UginTheSpiritDragon.class, RETRO_ART));
        cards.add(new SetCardInfo("Yoshimaru, Ever Faithful", 5, Rarity.MYTHIC, mage.cards.y.YoshimaruEverFaithful.class));
    }
}
