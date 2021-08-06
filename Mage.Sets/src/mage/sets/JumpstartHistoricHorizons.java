package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class JumpstartHistoricHorizons extends ExpansionSet {

    private static final JumpstartHistoricHorizons instance = new JumpstartHistoricHorizons();

    public static JumpstartHistoricHorizons getInstance() {
        return instance;
    }

    private JumpstartHistoricHorizons() {
        super("Jumpstart: Historic Horizons", "J21", ExpansionSet.buildDate(2021, 8, 12), SetType.MAGIC_ARENA);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Archfiend of Sorrows", "999-AOS", Rarity.UNCOMMON, mage.cards.a.ArchfiendOfSorrows.class));
        cards.add(new SetCardInfo("Blade Splicer", "999-BS", Rarity.RARE, mage.cards.b.BladeSplicer.class));
        cards.add(new SetCardInfo("Bounty of the Deep", "999-BOTD", Rarity.UNCOMMON, mage.cards.b.BountyOfTheDeep.class));
        cards.add(new SetCardInfo("Faceless Agent", "999-FA", Rarity.COMMON, mage.cards.f.FacelessAgent.class));
        cards.add(new SetCardInfo("First-Sphere Gargantua", "999-FSG", Rarity.COMMON, mage.cards.f.FirstSphereGargantua.class));
        cards.add(new SetCardInfo("Goblin Dark-Dwellers", "999-GDD", Rarity.RARE, mage.cards.g.GoblinDarkDwellers.class));
        cards.add(new SetCardInfo("Hardened Scales", "999-HS", Rarity.RARE, mage.cards.h.HardenedScales.class));
        cards.add(new SetCardInfo("Manor Guardian", "999-MG", Rarity.UNCOMMON, mage.cards.m.ManorGuardian.class));
        cards.add(new SetCardInfo("Ponder", "999-P", Rarity.COMMON, mage.cards.p.Ponder.class));
        cards.add(new SetCardInfo("Ranger-Captain of Eos", "999-RCOE", Rarity.MYTHIC, mage.cards.r.RangerCaptainOfEos.class));
        cards.add(new SetCardInfo("Return to the Ranks", "999-RTTR", Rarity.RARE, mage.cards.r.ReturnToTheRanks.class));
        cards.add(new SetCardInfo("Seasoned Pyromancer", "999-SP", Rarity.MYTHIC, mage.cards.s.SeasonedPyromancer.class));
        cards.add(new SetCardInfo("Skyshroud Lookout", "999-SL", Rarity.UNCOMMON, mage.cards.s.SkyshroudLookout.class));
        cards.add(new SetCardInfo("Stormfront Pegasus", "999-SFP", Rarity.UNCOMMON, mage.cards.s.StormfrontPegasus.class));
        cards.add(new SetCardInfo("Thalia's Lieutenant", "999-TL", Rarity.RARE, mage.cards.t.ThaliasLieutenant.class));
        cards.add(new SetCardInfo("Tropical Island", "999-TI", Rarity.RARE, mage.cards.t.TropicalIsland.class));
        cards.add(new SetCardInfo("Yawgmoth, Thran Physician", "999-YTP", Rarity.MYTHIC, mage.cards.y.YawgmothThranPhysician.class));
    }
}
