package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class AlchemyInnistrad extends ExpansionSet {

    private static final AlchemyInnistrad instance = new AlchemyInnistrad();

    public static AlchemyInnistrad getInstance() {
        return instance;
    }

    private AlchemyInnistrad() {
        super("Alchemy: Innistrad", "YMID", ExpansionSet.buildDate(2021, 12, 9), SetType.MAGIC_ARENA);
        this.blockName = "Alchemy";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Arms Scavenger", 35, Rarity.RARE, mage.cards.a.ArmsScavenger.class));
        cards.add(new SetCardInfo("Citystalker Connoisseur", 27, Rarity.RARE, mage.cards.c.CitystalkerConnoisseur.class));
        cards.add(new SetCardInfo("Cursebound Witch", 24, Rarity.UNCOMMON, mage.cards.c.CurseboundWitch.class));
        cards.add(new SetCardInfo("Expedition Supplier", 6, Rarity.RARE, mage.cards.e.ExpeditionSupplier.class));
        cards.add(new SetCardInfo("Faithful Disciple", 7, Rarity.UNCOMMON, mage.cards.f.FaithfulDisciple.class));
        cards.add(new SetCardInfo("Geistpack Alpha", 48, Rarity.RARE, mage.cards.g.GeistpackAlpha.class));
        cards.add(new SetCardInfo("Ishkanah, Broodmother", 52, Rarity.MYTHIC, mage.cards.i.IshkanahBroodmother.class));
        cards.add(new SetCardInfo("Key to the Archive", 59, Rarity.RARE, mage.cards.k.KeyToTheArchive.class));
        cards.add(new SetCardInfo("Kindred Denial", 18, Rarity.UNCOMMON, mage.cards.k.KindredDenial.class));
        cards.add(new SetCardInfo("Obsessive Collector", 19, Rarity.RARE, mage.cards.o.ObsessiveCollector.class));
        cards.add(new SetCardInfo("Settle the Wilds", 55, Rarity.UNCOMMON, mage.cards.s.SettleTheWilds.class));
        cards.add(new SetCardInfo("Soulstealer Axe", 60, Rarity.UNCOMMON, mage.cards.s.SoulstealerAxe.class));
        cards.add(new SetCardInfo("Suntail Squadron", 11, Rarity.RARE, mage.cards.s.SuntailSquadron.class));
        cards.add(new SetCardInfo("Tibalt, Wicked Tormentor", 43, Rarity.MYTHIC, mage.cards.t.TibaltWickedTormentor.class));
        cards.add(new SetCardInfo("Tireless Angler", 23, Rarity.RARE, mage.cards.t.TirelessAngler.class));
        cards.add(new SetCardInfo("Toralf's Disciple", 44, Rarity.RARE, mage.cards.t.ToralfsDisciple.class));
    }
}
