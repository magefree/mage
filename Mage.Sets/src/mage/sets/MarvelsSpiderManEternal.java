package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarvelsSpiderManEternal extends ExpansionSet {

    private static final MarvelsSpiderManEternal instance = new MarvelsSpiderManEternal();

    public static MarvelsSpiderManEternal getInstance() {
        return instance;
    }

    private MarvelsSpiderManEternal() {
        super("Marvel's Spider-Man Eternal", "SPE", ExpansionSet.buildDate(2025, 9, 26), SetType.SUPPLEMENTAL);
        this.blockName = "Marvel's Spider-Man"; // for sorting in GUI
        this.hasBasicLands = false; // temporary

        cards.add(new SetCardInfo("Alchemax Slayer-Bots", 5, Rarity.COMMON, mage.cards.a.AlchemaxSlayerBots.class));
        cards.add(new SetCardInfo("Amateur Hero", 1, Rarity.COMMON, mage.cards.a.AmateurHero.class));
        cards.add(new SetCardInfo("Amazing Alliance", 2, Rarity.RARE, mage.cards.a.AmazingAlliance.class));
        cards.add(new SetCardInfo("Doc Ock, Evil Inventor", 24, Rarity.RARE, mage.cards.d.DocOckEvilInventor.class));
        cards.add(new SetCardInfo("Double Trouble", 13, Rarity.RARE, mage.cards.d.DoubleTrouble.class));
        cards.add(new SetCardInfo("Future Flight", 6, Rarity.RARE, mage.cards.f.FutureFlight.class));
        cards.add(new SetCardInfo("Ghost-Spider, Gwen Stacy", 14, Rarity.MYTHIC, mage.cards.g.GhostSpiderGwenStacy.class));
        cards.add(new SetCardInfo("Grasping Tentacles", 21, Rarity.RARE, mage.cards.g.GraspingTentacles.class));
        cards.add(new SetCardInfo("Green Goblin, Nemesis", 23, Rarity.RARE, mage.cards.g.GreenGoblinNemesis.class));
        cards.add(new SetCardInfo("Grendel, Spawn of Knull", 9, Rarity.UNCOMMON, mage.cards.g.GrendelSpawnOfKnull.class));
        cards.add(new SetCardInfo("Lethal Protection", 10, Rarity.RARE, mage.cards.l.LethalProtection.class));
        cards.add(new SetCardInfo("Lyla, Holographic Assistant", 7, Rarity.UNCOMMON, mage.cards.l.LylaHolographicAssistant.class));
        cards.add(new SetCardInfo("MJ, Rising Star", 3, Rarity.UNCOMMON, mage.cards.m.MJRisingStar.class));
        cards.add(new SetCardInfo("Prowler, Misguided Mentor", 17, Rarity.UNCOMMON, mage.cards.p.ProwlerMisguidedMentor.class));
        cards.add(new SetCardInfo("Pumpkin Bombs", 26, Rarity.RARE, mage.cards.p.PumpkinBombs.class));
        cards.add(new SetCardInfo("Rampaging Classmate", 16, Rarity.COMMON, mage.cards.r.RampagingClassmate.class));
        cards.add(new SetCardInfo("Sensational Spider-Man", 25, Rarity.RARE, mage.cards.s.SensationalSpiderMan.class));
        cards.add(new SetCardInfo("Spider-Man 2099, Miguel O'Hara", 8, Rarity.MYTHIC, mage.cards.s.SpiderMan2099MiguelOHara.class));
        cards.add(new SetCardInfo("Spider-Man, Miles Morales", 18, Rarity.MYTHIC, mage.cards.s.SpiderManMilesMorales.class));
        cards.add(new SetCardInfo("Spider-Man, Peter Parker", 4, Rarity.MYTHIC, mage.cards.s.SpiderManPeterParker.class));
        cards.add(new SetCardInfo("Symbiote Spawn", 11, Rarity.COMMON, mage.cards.s.SymbioteSpawn.class));
        cards.add(new SetCardInfo("The Mary Janes", 15, Rarity.UNCOMMON, mage.cards.t.TheMaryJanes.class));
        cards.add(new SetCardInfo("Twisted Spider-Clone", 19, Rarity.COMMON, mage.cards.t.TwistedSpiderClone.class));
        cards.add(new SetCardInfo("Venom Blast", 20, Rarity.RARE, mage.cards.v.VenomBlast.class));
        cards.add(new SetCardInfo("Venom, Deadly Devourer", 22, Rarity.RARE, mage.cards.v.VenomDeadlyDevourer.class));
        cards.add(new SetCardInfo("Venom, Eddie Brock", 12, Rarity.MYTHIC, mage.cards.v.VenomEddieBrock.class));
    }
}
