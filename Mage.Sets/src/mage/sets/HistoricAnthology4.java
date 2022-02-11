package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class HistoricAnthology4 extends ExpansionSet {

    private static final HistoricAnthology4 instance = new HistoricAnthology4();

    public static HistoricAnthology4 getInstance() {
        return instance;
    }

    private HistoricAnthology4() {
        super("Historic Anthology 4", "HA4", ExpansionSet.buildDate(2021, 3, 11), SetType.MAGIC_ARENA);
        this.blockName = "Reprint";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Abomination of Llanowar", 20, Rarity.UNCOMMON, mage.cards.a.AbominationOfLlanowar.class));
        cards.add(new SetCardInfo("Adorned Pouncer", 1, Rarity.RARE, mage.cards.a.AdornedPouncer.class));
        cards.add(new SetCardInfo("Ammit Eternal", 8, Rarity.RARE, mage.cards.a.AmmitEternal.class));
        cards.add(new SetCardInfo("Blinkmoth Nexus", 25, Rarity.RARE, mage.cards.b.BlinkmothNexus.class));
        cards.add(new SetCardInfo("Bonesplitter", 21, Rarity.COMMON, mage.cards.b.Bonesplitter.class));
        cards.add(new SetCardInfo("Coldsteel Heart", 22, Rarity.UNCOMMON, mage.cards.c.ColdsteelHeart.class));
        cards.add(new SetCardInfo("Collected Conjuring", 19, Rarity.RARE, mage.cards.c.CollectedConjuring.class));
        cards.add(new SetCardInfo("Death's Shadow", 9, Rarity.RARE, mage.cards.d.DeathsShadow.class));
        cards.add(new SetCardInfo("Declaration in Stone", 2, Rarity.RARE, mage.cards.d.DeclarationInStone.class));
        cards.add(new SetCardInfo("Faith of the Devoted", 10, Rarity.UNCOMMON, mage.cards.f.FaithOfTheDevoted.class));
        cards.add(new SetCardInfo("Flameblade Adept", 12, Rarity.UNCOMMON, mage.cards.f.FlamebladeAdept.class));
        cards.add(new SetCardInfo("Goblin Gaveleer", 13, Rarity.COMMON, mage.cards.g.GoblinGaveleer.class));
        cards.add(new SetCardInfo("Hamza, Guardian of Arashin", 18, Rarity.UNCOMMON, mage.cards.h.HamzaGuardianOfArashin.class));
        cards.add(new SetCardInfo("Harmless Offering", 14, Rarity.RARE, mage.cards.h.HarmlessOffering.class));
        cards.add(new SetCardInfo("Iceberg Cancrix", 5, Rarity.COMMON, mage.cards.i.IcebergCancrix.class));
        cards.add(new SetCardInfo("Inspiring Statuary", 23, Rarity.RARE, mage.cards.i.InspiringStatuary.class));
        cards.add(new SetCardInfo("Lys Alana Huntmaster", 15, Rarity.COMMON, mage.cards.l.LysAlanaHuntmaster.class));
        cards.add(new SetCardInfo("Marit Lage's Slumber", 6, Rarity.RARE, mage.cards.m.MaritLagesSlumber.class));
        // cards.add(new SetCardInfo("Sawtusk Demolisher", 16, Rarity.RARE, mage.cards.s.SawtuskDemolisher.class)); TODO: reenable when mutate is implemented
        cards.add(new SetCardInfo("Spider Spawning", 17, Rarity.UNCOMMON, mage.cards.s.SpiderSpawning.class));
        cards.add(new SetCardInfo("Sword of Body and Mind", 24, Rarity.MYTHIC, mage.cards.s.SwordOfBodyAndMind.class));
        cards.add(new SetCardInfo("Think Twice", 7, Rarity.COMMON, mage.cards.t.ThinkTwice.class));
        cards.add(new SetCardInfo("Thraben Inspector", 3, Rarity.COMMON, mage.cards.t.ThrabenInspector.class));
        cards.add(new SetCardInfo("Torment of Scarabs", 11, Rarity.UNCOMMON, mage.cards.t.TormentOfScarabs.class));
        cards.add(new SetCardInfo("Triumphant Reckoning", 4, Rarity.MYTHIC, mage.cards.t.TriumphantReckoning.class));
    }
}
