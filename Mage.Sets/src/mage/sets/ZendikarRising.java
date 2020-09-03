package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class ZendikarRising extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList(
            "Acquisitions Expert",
            "Akoum Warrior",
            "Akoum Teeth",
            "Archpriest of Iona",
            "Ardent Electromancer",
            "Bala Ged Recovery",
            "Bala Ged Sanctuary",
            "Branchloft Pathway",
            "Boulderloft Pathway",
            "Brightclimb Pathway",
            "Grimclimb Pathway",
            "Cascade Seer",
            "Clearwater Pathway",
            "Murkwater Pathway",
            "Coveted Prize",
            "Cragcrown Pathway",
            "Timbercrown Pathway",
            "Deadly Alliance",
            "Emeria Captain",
            "Emeria's Call",
            "Emeria, Shattered Skyclave",
            "Grotag Bug-Catcher",
            "Kabira Outrider",
            "Khalni Ambush",
            "Khalni Territory",
            "Linvala, Shield of Sea Gate",
            "Needleverge Pathway",
            "Pillarverge Pathway",
            "Pelakka Predation",
            "Pelakka Caverns",
            "Ravager's Mace",
            "Riverglide Pathway",
            "Lavaglide Pathway",
            "Seafloor Stalker",
            "Sea Gate Colossus",
            "Sejiri Shelter",
            "Sejiri Glacier",
            "Shepherd of Heroes",
            "Spoils of Adventure",
            "Tangled Florahedron",
            "Tangled Vale",
            "Tazri, Beacon of Unity",
            "Thwart the Grave",
            "Umara Wizard",
            "Umara Skyfalls",
            "Valakut Awakening",
            "Valakut Stoneforge",
            "Zagras, Thief of Heartbeats"
    );

    private static final ZendikarRising instance = new ZendikarRising();

    public static ZendikarRising getInstance() {
        return instance;
    }

    private ZendikarRising() {
        super("Zendikar Rising", "ZNR", ExpansionSet.buildDate(2020, 9, 25), SetType.EXPANSION);
        this.blockName = "Zendikar Rising";
        this.hasBasicLands = true;
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 280;

        cards.add(new SetCardInfo("Acquisitions Expert", 89, Rarity.UNCOMMON, mage.cards.a.AcquisitionsExpert.class));
        cards.add(new SetCardInfo("Akoum Hellhound", 133, Rarity.COMMON, mage.cards.a.AkoumHellhound.class));
        cards.add(new SetCardInfo("Archpriest of Iona", 5, Rarity.RARE, mage.cards.a.ArchpriestOfIona.class));
        cards.add(new SetCardInfo("Ardent Electromancer", 135, Rarity.COMMON, mage.cards.a.ArdentElectromancer.class));
        cards.add(new SetCardInfo("Ashaya, Soul of the Wild", 179, Rarity.MYTHIC, mage.cards.a.AshayaSoulOfTheWild.class));
        cards.add(new SetCardInfo("Bloodchief's Thirst", 94, Rarity.UNCOMMON, mage.cards.b.BloodchiefsThirst.class));
        cards.add(new SetCardInfo("Boulderloft Pathway", 258, Rarity.RARE, mage.cards.b.BoulderloftPathway.class));
        cards.add(new SetCardInfo("Branchloft Pathway", 258, Rarity.RARE, mage.cards.b.BranchloftPathway.class));
        cards.add(new SetCardInfo("Brightclimb Pathway", 259, Rarity.RARE, mage.cards.b.BrightclimbPathway.class));
        cards.add(new SetCardInfo("Clearwater Pathway", 260, Rarity.RARE, mage.cards.c.ClearwaterPathway.class));
        cards.add(new SetCardInfo("Cleric of Life's Bond", 222, Rarity.UNCOMMON, mage.cards.c.ClericOfLifesBond.class));
        cards.add(new SetCardInfo("Cliffhaven Sell-Sword", 8, Rarity.COMMON, mage.cards.c.CliffhavenSellSword.class));
        cards.add(new SetCardInfo("Cragcrown Pathway", 261, Rarity.RARE, mage.cards.c.CragcrownPathway.class));
        cards.add(new SetCardInfo("Demon's Disciple", 97, Rarity.UNCOMMON, mage.cards.d.DemonsDisciple.class));
        cards.add(new SetCardInfo("Emeria Captain", 11, Rarity.UNCOMMON, mage.cards.e.EmeriaCaptain.class));
        cards.add(new SetCardInfo("Farsight Adept", 14, Rarity.COMMON, mage.cards.f.FarsightAdept.class));
        cards.add(new SetCardInfo("Forest", 278, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Gnarlid Colony", 185, Rarity.COMMON, mage.cards.g.GnarlidColony.class));
        cards.add(new SetCardInfo("Grimclimb Pathway", 259, Rarity.RARE, mage.cards.g.GrimclimbPathway.class));
        cards.add(new SetCardInfo("Grotag Bug-Catcher", 142, Rarity.COMMON, mage.cards.g.GrotagBugCatcher.class));
        cards.add(new SetCardInfo("Into the Roil", 62, Rarity.COMMON, mage.cards.i.IntoTheRoil.class));
        cards.add(new SetCardInfo("Island", 269, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Jace, Mirror Mage", 63, Rarity.MYTHIC, mage.cards.j.JaceMirrorMage.class));
        cards.add(new SetCardInfo("Kabira Outrider", 18, Rarity.COMMON, mage.cards.k.KabiraOutrider.class));
        cards.add(new SetCardInfo("Kitesail Cleric", 20, Rarity.UNCOMMON, mage.cards.k.KitesailCleric.class));
        cards.add(new SetCardInfo("Kor Blademaster", 21, Rarity.UNCOMMON, mage.cards.k.KorBlademaster.class));
        cards.add(new SetCardInfo("Lavaglide Pathway", 264, Rarity.RARE, mage.cards.l.LavaglidePathway.class));
        cards.add(new SetCardInfo("Legion Angel", 23, Rarity.RARE, mage.cards.l.LegionAngel.class));
        cards.add(new SetCardInfo("Linvala, Shield of Sea Gate", 226, Rarity.RARE, mage.cards.l.LinvalaShieldOfSeaGate.class));
        cards.add(new SetCardInfo("Lotus Cobra", 193, Rarity.RARE, mage.cards.l.LotusCobra.class));
        cards.add(new SetCardInfo("Might of Murasa", 194, Rarity.COMMON, mage.cards.m.MightOfMurasa.class));
        cards.add(new SetCardInfo("Mountain", 275, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Murasa Rootgrazer", 229, Rarity.UNCOMMON, mage.cards.m.MurasaRootgrazer.class));
        cards.add(new SetCardInfo("Murkwater Pathway", 260, Rarity.RARE, mage.cards.m.MurkwaterPathway.class));
        cards.add(new SetCardInfo("Nahiri, Heir of the Ancients", 230, Rarity.MYTHIC, mage.cards.n.NahiriHeirOfTheAncients.class));
        cards.add(new SetCardInfo("Needleverge Pathway", 263, Rarity.RARE, mage.cards.n.NeedlevergePathway.class));
        cards.add(new SetCardInfo("Omnath, Locus of Creation", 232, Rarity.MYTHIC, mage.cards.o.OmnathLocusOfCreation.class));
        cards.add(new SetCardInfo("Orah, Skyclave Hierophant", 233, Rarity.RARE, mage.cards.o.OrahSkyclaveHierophant.class));
        cards.add(new SetCardInfo("Pillarverge Pathway", 263, Rarity.RARE, mage.cards.p.PillarvergePathway.class));
        cards.add(new SetCardInfo("Plains", 266, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Prowling Felidar", 34, Rarity.COMMON, mage.cards.p.ProwlingFelidar.class));
        cards.add(new SetCardInfo("Riverglide Pathway", 264, Rarity.RARE, mage.cards.r.RiverglidePathway.class));
        cards.add(new SetCardInfo("Ruin Crab", 75, Rarity.UNCOMMON, mage.cards.r.RuinCrab.class));
        cards.add(new SetCardInfo("Shell Shield", 79, Rarity.COMMON, mage.cards.s.ShellShield.class));
        cards.add(new SetCardInfo("Shepherd of Heroes", 38, Rarity.COMMON, mage.cards.s.ShepherdOfHeroes.class));
        cards.add(new SetCardInfo("Sneaking Guide", 164, Rarity.COMMON, mage.cards.s.SneakingGuide.class));
        cards.add(new SetCardInfo("Spitfire Lagac", 167, Rarity.COMMON, mage.cards.s.SpitfireLagac.class));
        cards.add(new SetCardInfo("Spoils of Adventure", 237, Rarity.UNCOMMON, mage.cards.s.SpoilsOfAdventure.class));
        cards.add(new SetCardInfo("Swamp", 273, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Tajuru Blightblade", 208, Rarity.COMMON, mage.cards.t.TajuruBlightblade.class));
        cards.add(new SetCardInfo("Tajuru Snarecaster", 210, Rarity.COMMON, mage.cards.t.TajuruSnarecaster.class));
        cards.add(new SetCardInfo("Taunting Arbormage", 212, Rarity.UNCOMMON, mage.cards.t.TauntingArbormage.class));
        cards.add(new SetCardInfo("Tazri, Beacon of Unity", 44, Rarity.MYTHIC, mage.cards.t.TazriBeaconOfUnity.class));
        cards.add(new SetCardInfo("Timbercrown Pathway", 261, Rarity.RARE, mage.cards.t.TimbercrownPathway.class));
        cards.add(new SetCardInfo("Windrider Wizard", 87, Rarity.UNCOMMON, mage.cards.w.WindriderWizard.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanics are fully implemented
    }
}
