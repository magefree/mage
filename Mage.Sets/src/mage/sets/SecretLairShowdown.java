package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/slp
 */
public class SecretLairShowdown extends ExpansionSet {

    private static final SecretLairShowdown instance = new SecretLairShowdown();

    public static SecretLairShowdown getInstance() {
        return instance;
    }

    private SecretLairShowdown() {
        super("Secret Lair Showdown", "SLP", ExpansionSet.buildDate(2023, 2, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;
		
        cards.add(new SetCardInfo("An Offer You Can't Refuse", 7, Rarity.RARE, mage.cards.a.AnOfferYouCantRefuse.class));
        cards.add(new SetCardInfo("Brainstorm", 1, Rarity.RARE, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Dark Ritual", 16, Rarity.RARE, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Death's Shadow", 8, Rarity.RARE, mage.cards.d.DeathsShadow.class));
        cards.add(new SetCardInfo("Dragonlord Silumgar", 9, Rarity.MYTHIC, mage.cards.d.DragonlordSilumgar.class));
        cards.add(new SetCardInfo("Eldritch Evolution", 5, Rarity.RARE, mage.cards.e.EldritchEvolution.class));
        cards.add(new SetCardInfo("Explore", 12, Rarity.RARE, mage.cards.e.Explore.class));
        cards.add(new SetCardInfo("Expressive Iteration", 13, Rarity.RARE, mage.cards.e.ExpressiveIteration.class));
        cards.add(new SetCardInfo("Fatal Push", 3, Rarity.RARE, mage.cards.f.FatalPush.class));
        cards.add(new SetCardInfo("Fauna Shaman", 41, Rarity.RARE, mage.cards.f.FaunaShaman.class));
        cards.add(new SetCardInfo("Flowerfoot Swordmaster", 46, Rarity.RARE, mage.cards.f.FlowerfootSwordmaster.class));
        cards.add(new SetCardInfo("Force of Despair", 29, Rarity.RARE, mage.cards.f.ForceOfDespair.class, FULL_ART));
        cards.add(new SetCardInfo("Garruk Wildspeaker", 42, Rarity.MYTHIC, mage.cards.g.GarrukWildspeaker.class));
        cards.add(new SetCardInfo("Goblin Guide", 23, Rarity.RARE, mage.cards.g.GoblinGuide.class));
        cards.add(new SetCardInfo("Innkeeper's Talent", 45, Rarity.RARE, mage.cards.i.InnkeepersTalent.class, FULL_ART));
        cards.add(new SetCardInfo("Island", 32, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Karn Liberated", 36, Rarity.MYTHIC, mage.cards.k.KarnLiberated.class));
        cards.add(new SetCardInfo("Laughing Jasper Flint", 44, Rarity.RARE, mage.cards.l.LaughingJasperFlint.class));
        cards.add(new SetCardInfo("Lightning Bolt", 21, Rarity.RARE, mage.cards.l.LightningBolt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lightning Bolt", 37, Rarity.RARE, mage.cards.l.LightningBolt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Living End", 30, Rarity.MYTHIC, mage.cards.l.LivingEnd.class));
        cards.add(new SetCardInfo("Manifold Mouse", 47, Rarity.RARE, mage.cards.m.ManifoldMouse.class));
        cards.add(new SetCardInfo("Mayhem Devil", 28, Rarity.RARE, mage.cards.m.MayhemDevil.class));
        cards.add(new SetCardInfo("Mountain", 34, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Murktide Regent", 17, Rarity.MYTHIC, mage.cards.m.MurktideRegent.class));
        cards.add(new SetCardInfo("Nexus of Fate", 27, Rarity.RARE, mage.cards.n.NexusOfFate.class, FULL_ART));
        cards.add(new SetCardInfo("Plains", 31, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Ponder", 19, Rarity.RARE, mage.cards.p.Ponder.class, FULL_ART));
        cards.add(new SetCardInfo("Prosperous Innkeeper", 40, Rarity.RARE, mage.cards.p.ProsperousInnkeeper.class, FULL_ART));
        cards.add(new SetCardInfo("Questing Druid", 38, Rarity.RARE, mage.cards.q.QuestingDruid.class));
        cards.add(new SetCardInfo("Ragavan, Nimble Pilferer", 2, Rarity.MYTHIC, mage.cards.r.RagavanNimblePilferer.class));
        cards.add(new SetCardInfo("Relentless Rats", 10, Rarity.RARE, mage.cards.r.RelentlessRats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Relentless Rats", 11, Rarity.RARE, mage.cards.r.RelentlessRats.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seasoned Pyromancer", 24, Rarity.MYTHIC, mage.cards.s.SeasonedPyromancer.class));
        cards.add(new SetCardInfo("Shoot the Sheriff", 43, Rarity.RARE, mage.cards.s.ShootTheSheriff.class, FULL_ART));
        cards.add(new SetCardInfo("Sleight of Hand", 25, Rarity.RARE, mage.cards.s.SleightOfHand.class, FULL_ART));
        cards.add(new SetCardInfo("Spell Pierce", 18, Rarity.RARE, mage.cards.s.SpellPierce.class));
        cards.add(new SetCardInfo("Springleaf Drum", 22, Rarity.RARE, mage.cards.s.SpringleafDrum.class));
        cards.add(new SetCardInfo("Sudden Edict", 39, Rarity.RARE, mage.cards.s.SuddenEdict.class, FULL_ART));
        cards.add(new SetCardInfo("Supreme Verdict", 26, Rarity.RARE, mage.cards.s.SupremeVerdict.class, FULL_ART));
        cards.add(new SetCardInfo("Swamp", 33, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Swords to Plowshares", 20, Rarity.RARE, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Ugin, the Spirit Dragon", 6, Rarity.MYTHIC, mage.cards.u.UginTheSpiritDragon.class));
        cards.add(new SetCardInfo("Unholy Heat", 4, Rarity.RARE, mage.cards.u.UnholyHeat.class));
        cards.add(new SetCardInfo("Valakut, the Molten Pinnacle", 14, Rarity.RARE, mage.cards.v.ValakutTheMoltenPinnacle.class));
        cards.add(new SetCardInfo("Wrenn and Six", 15, Rarity.MYTHIC, mage.cards.w.WrennAndSix.class));
    }
}
