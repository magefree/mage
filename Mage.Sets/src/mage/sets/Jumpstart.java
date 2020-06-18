package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class Jumpstart extends ExpansionSet {

    private static final Jumpstart instance = new Jumpstart();

    public static Jumpstart getInstance() {
        return instance;
    }

    private Jumpstart() {
        super("Jumpstart", "JMP", ExpansionSet.buildDate(2020, 7, 17), SetType.SUPPLEMENTAL);
        this.blockName = "Jumpstart";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Aether Spellbomb", 456, Rarity.COMMON, mage.cards.a.AetherSpellbomb.class));
        cards.add(new SetCardInfo("Affectionate Indrik", 373, Rarity.UNCOMMON, mage.cards.a.AffectionateIndrik.class));
        cards.add(new SetCardInfo("Aggressive Urge", 374, Rarity.COMMON, mage.cards.a.AggressiveUrge.class));
        cards.add(new SetCardInfo("Agonizing Syphon", 199, Rarity.COMMON, mage.cards.a.AgonizingSyphon.class));
        cards.add(new SetCardInfo("Alloy Myr", 457, Rarity.COMMON, mage.cards.a.AlloyMyr.class));
        cards.add(new SetCardInfo("Ambassador Oak", 375, Rarity.COMMON, mage.cards.a.AmbassadorOak.class));
        cards.add(new SetCardInfo("Ancestral Statue", 458, Rarity.COMMON, mage.cards.a.AncestralStatue.class));
        cards.add(new SetCardInfo("Auger Spree", 449, Rarity.COMMON, mage.cards.a.AugerSpree.class));
        cards.add(new SetCardInfo("Awakener Druid", 379, Rarity.UNCOMMON, mage.cards.a.AwakenerDruid.class));
        cards.add(new SetCardInfo("Bake into a Pie", 201, Rarity.COMMON, mage.cards.b.BakeIntoAPie.class));
        cards.add(new SetCardInfo("Befuddle", 140, Rarity.COMMON, mage.cards.b.Befuddle.class));
        cards.add(new SetCardInfo("Black Cat", 203, Rarity.COMMON, mage.cards.b.BlackCat.class));
        cards.add(new SetCardInfo("Blighted Bat", 205, Rarity.COMMON, mage.cards.b.BlightedBat.class));
        cards.add(new SetCardInfo("Blood Artist", 206, Rarity.UNCOMMON, mage.cards.b.BloodArtist.class));
        cards.add(new SetCardInfo("Blood Divination", 207, Rarity.UNCOMMON, mage.cards.b.BloodDivination.class));
        cards.add(new SetCardInfo("Blood Host", 208, Rarity.UNCOMMON, mage.cards.b.BloodHost.class));
        cards.add(new SetCardInfo("Bloodbond Vampire", 209, Rarity.UNCOMMON, mage.cards.b.BloodbondVampire.class));
        cards.add(new SetCardInfo("Bloodhunter Bat", 210, Rarity.COMMON, mage.cards.b.BloodhunterBat.class));
        cards.add(new SetCardInfo("Bogbrew Witch", 211, Rarity.RARE, mage.cards.b.BogbrewWitch.class));
        cards.add(new SetCardInfo("Bone Picker", 212, Rarity.UNCOMMON, mage.cards.b.BonePicker.class));
        cards.add(new SetCardInfo("Brushstrider", 381, Rarity.UNCOMMON, mage.cards.b.Brushstrider.class));
        cards.add(new SetCardInfo("Bubbling Cauldron", 460, Rarity.UNCOMMON, mage.cards.b.BubblingCauldron.class));
        cards.add(new SetCardInfo("Buried Ruin", 491, Rarity.UNCOMMON, mage.cards.b.BuriedRuin.class));
        cards.add(new SetCardInfo("Cemetery Recruitment", 217, Rarity.COMMON, mage.cards.c.CemeteryRecruitment.class));
        cards.add(new SetCardInfo("Chamber Sentry", 461, Rarity.RARE, mage.cards.c.ChamberSentry.class));
        cards.add(new SetCardInfo("Child of Night", 218, Rarity.COMMON, mage.cards.c.ChildOfNight.class));
        cards.add(new SetCardInfo("Chromatic Sphere", 462, Rarity.COMMON, mage.cards.c.ChromaticSphere.class));
        cards.add(new SetCardInfo("Crookclaw Transmuter", 145, Rarity.COMMON, mage.cards.c.CrookclawTransmuter.class));
        cards.add(new SetCardInfo("Crushing Canopy", 386, Rarity.COMMON, mage.cards.c.CrushingCanopy.class));
        cards.add(new SetCardInfo("Dinrova Horror", 450, Rarity.UNCOMMON, mage.cards.d.DinrovaHorror.class));
        cards.add(new SetCardInfo("Drana, Liberator of Malakir", 225, Rarity.MYTHIC, mage.cards.d.DranaLiberatorOfMalakir.class));
        cards.add(new SetCardInfo("Dutiful Attendant", 226, Rarity.COMMON, mage.cards.d.DutifulAttendant.class));
        cards.add(new SetCardInfo("Entomber Exarch", 227, Rarity.UNCOMMON, mage.cards.e.EntomberExarch.class));
        cards.add(new SetCardInfo("Erratic Visionary", 150, Rarity.COMMON, mage.cards.e.ErraticVisionary.class));
        cards.add(new SetCardInfo("Eternal Thirst", 229, Rarity.COMMON, mage.cards.e.EternalThirst.class));
        cards.add(new SetCardInfo("Exclusion Mage", 153, Rarity.UNCOMMON, mage.cards.e.ExclusionMage.class));
        cards.add(new SetCardInfo("Explore", 393, Rarity.COMMON, mage.cards.e.Explore.class));
        cards.add(new SetCardInfo("Exquisite Blood", 231, Rarity.RARE, mage.cards.e.ExquisiteBlood.class));
        cards.add(new SetCardInfo("Falkenrath Noble", 232, Rarity.UNCOMMON, mage.cards.f.FalkenrathNoble.class));
        cards.add(new SetCardInfo("Fanatical Firebrand", 315, Rarity.COMMON, mage.cards.f.FanaticalFirebrand.class));
        cards.add(new SetCardInfo("Feral Hydra", 395, Rarity.UNCOMMON, mage.cards.f.FeralHydra.class));
        cards.add(new SetCardInfo("Fertilid", 398, Rarity.COMMON, mage.cards.f.Fertilid.class));
        cards.add(new SetCardInfo("Festering Newt", 234, Rarity.COMMON, mage.cards.f.FesteringNewt.class));
        cards.add(new SetCardInfo("Flames of the Firebrand", 357, Rarity.UNCOMMON, mage.cards.f.FlamesOfTheFirebrand.class));
        cards.add(new SetCardInfo("Forest", 70, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fusion Elemental", 451, Rarity.UNCOMMON, mage.cards.f.FusionElemental.class));
        cards.add(new SetCardInfo("Ghoulcaller's Accomplice", 237, Rarity.COMMON, mage.cards.g.GhoulcallersAccomplice.class));
        cards.add(new SetCardInfo("Ghoulraiser", 238, Rarity.COMMON, mage.cards.g.Ghoulraiser.class));
        cards.add(new SetCardInfo("Gravewaker", 241, Rarity.RARE, mage.cards.g.Gravewaker.class));
        cards.add(new SetCardInfo("Hedron Archive", 468, Rarity.UNCOMMON, mage.cards.h.HedronArchive.class));
        cards.add(new SetCardInfo("Hungry Flames", 336, Rarity.COMMON, mage.cards.h.HungryFlames.class));
        cards.add(new SetCardInfo("Innocent Blood", 244, Rarity.COMMON, mage.cards.i.InnocentBlood.class));
        cards.add(new SetCardInfo("Ironroot Warlord", 452, Rarity.UNCOMMON, mage.cards.i.IronrootWarlord.class));
        cards.add(new SetCardInfo("Isamaru, Hound of Konda", 113, Rarity.RARE, mage.cards.i.IsamaruHoundOfKonda.class));
        cards.add(new SetCardInfo("Island", 49, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Juggernaut", 471, Rarity.UNCOMMON, mage.cards.j.Juggernaut.class));
        cards.add(new SetCardInfo("Kalastria Nightwatch", 245, Rarity.COMMON, mage.cards.k.KalastriaNightwatch.class));
        cards.add(new SetCardInfo("Kels, Fight Fixer", 15, Rarity.RARE, mage.cards.k.KelsFightFixer.class));
        cards.add(new SetCardInfo("Knight of the Tusk", 114, Rarity.COMMON, mage.cards.k.KnightOfTheTusk.class));
        cards.add(new SetCardInfo("Last Gasp", 247, Rarity.COMMON, mage.cards.l.LastGasp.class));
        cards.add(new SetCardInfo("Lawmage's Binding", 453, Rarity.COMMON, mage.cards.l.LawmagesBinding.class));
        cards.add(new SetCardInfo("Liliana's Elite", 250, Rarity.UNCOMMON, mage.cards.l.LilianasElite.class));
        cards.add(new SetCardInfo("Maelstrom Archangel", 454, Rarity.MYTHIC, mage.cards.m.MaelstromArchangel.class));
        cards.add(new SetCardInfo("Mark of the Vampire", 254, Rarity.COMMON, mage.cards.m.MarkOfTheVampire.class));
        cards.add(new SetCardInfo("Meteor Golem", 474, Rarity.UNCOMMON, mage.cards.m.MeteorGolem.class));
        cards.add(new SetCardInfo("Mire Triton", 257, Rarity.UNCOMMON, mage.cards.m.MireTriton.class));
        cards.add(new SetCardInfo("Mirrodin's Core", 492, Rarity.UNCOMMON, mage.cards.m.MirrodinsCore.class));
        cards.add(new SetCardInfo("Nature's Way", 412, Rarity.UNCOMMON, mage.cards.n.NaturesWay.class));
        cards.add(new SetCardInfo("Nocturnal Feeder", 16, Rarity.COMMON, mage.cards.n.NocturnalFeeder.class));
        cards.add(new SetCardInfo("Nyxathid", 259, Rarity.RARE, mage.cards.n.Nyxathid.class));
        cards.add(new SetCardInfo("Oracle of Mul Daya", 415, Rarity.RARE, mage.cards.o.OracleOfMulDaya.class));
        cards.add(new SetCardInfo("Peel from Reality", 163, Rarity.COMMON, mage.cards.p.PeelFromReality.class));
        cards.add(new SetCardInfo("Pillar of Flame", 355, Rarity.RARE, mage.cards.p.PillarOfFlame.class));
        cards.add(new SetCardInfo("Plains", 45, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Primordial Sage", 422, Rarity.RARE, mage.cards.p.PrimordialSage.class));
        cards.add(new SetCardInfo("Prophetic Prism", 478, Rarity.COMMON, mage.cards.p.PropheticPrism.class));
        cards.add(new SetCardInfo("Pyroclastic Elemental", 356, Rarity.UNCOMMON, mage.cards.p.PyroclasticElemental.class));
        cards.add(new SetCardInfo("Raging Regisaur", 455, Rarity.UNCOMMON, mage.cards.r.RagingRegisaur.class));
        cards.add(new SetCardInfo("Reanimate", 270, Rarity.RARE, mage.cards.r.Reanimate.class));
        cards.add(new SetCardInfo("Release the Dogs", 4, Rarity.UNCOMMON, mage.cards.r.ReleaseTheDogs.class));
        cards.add(new SetCardInfo("Riptide Laboratory", 494, Rarity.RARE, mage.cards.r.RiptideLaboratory.class));
        cards.add(new SetCardInfo("Rupture Spire", 495, Rarity.COMMON, mage.cards.r.RuptureSpire.class));
        cards.add(new SetCardInfo("Sage's Row Savant", 171, Rarity.COMMON, mage.cards.s.SagesRowSavant.class));
        cards.add(new SetCardInfo("Sangromancer", 272, Rarity.RARE, mage.cards.s.Sangromancer.class));
        cards.add(new SetCardInfo("Sea Gate Oracle", 173, Rarity.COMMON, mage.cards.s.SeaGateOracle.class));
        cards.add(new SetCardInfo("Sengir Vampire", 275, Rarity.UNCOMMON, mage.cards.s.SengirVampire.class));
        cards.add(new SetCardInfo("Settle the Score", 276, Rarity.UNCOMMON, mage.cards.s.SettleTheScore.class));
        cards.add(new SetCardInfo("Shambling Goblin", 277, Rarity.COMMON, mage.cards.s.ShamblingGoblin.class));
        cards.add(new SetCardInfo("Skittering Surveyor", 486, Rarity.COMMON, mage.cards.s.SkitteringSurveyor.class));
        cards.add(new SetCardInfo("Soul of the Harvest", 432, Rarity.RARE, mage.cards.s.SoulOfTheHarvest.class));
        cards.add(new SetCardInfo("Sporemound", 433, Rarity.COMMON, mage.cards.s.Sporemound.class));
        cards.add(new SetCardInfo("Storm Sculptor", 179, Rarity.COMMON, mage.cards.s.StormSculptor.class));
        cards.add(new SetCardInfo("Swamp", 54, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swarm of Bloodflies", 282, Rarity.UNCOMMON, mage.cards.s.SwarmOfBloodflies.class));
        cards.add(new SetCardInfo("Sylvan Ranger", 435, Rarity.COMMON, mage.cards.s.SylvanRanger.class));
        cards.add(new SetCardInfo("Talrand's Invocation", 182, Rarity.UNCOMMON, mage.cards.t.TalrandsInvocation.class));
        cards.add(new SetCardInfo("Talrand, Sky Summoner", 181, Rarity.RARE, mage.cards.t.TalrandSkySummoner.class));
        cards.add(new SetCardInfo("Tempting Witch", 283, Rarity.UNCOMMON, mage.cards.t.TemptingWitch.class));
        cards.add(new SetCardInfo("Terramorphic Expanse", 78, Rarity.COMMON, mage.cards.t.TerramorphicExpanse.class));
        cards.add(new SetCardInfo("Thirst for Knowledge", 183, Rarity.UNCOMMON, mage.cards.t.ThirstForKnowledge.class));
        cards.add(new SetCardInfo("Thriving Bluff", 33, Rarity.COMMON, mage.cards.t.ThrivingBluff.class));
        cards.add(new SetCardInfo("Thriving Grove", 34, Rarity.COMMON, mage.cards.t.ThrivingGrove.class));
        cards.add(new SetCardInfo("Thriving Heath", 35, Rarity.COMMON, mage.cards.t.ThrivingHeath.class));
        cards.add(new SetCardInfo("Thriving Isle", 36, Rarity.COMMON, mage.cards.t.ThrivingIsle.class));
        cards.add(new SetCardInfo("Thriving Moor", 37, Rarity.COMMON, mage.cards.t.ThrivingMoor.class));
        cards.add(new SetCardInfo("Ulvenwald Hydra", 439, Rarity.MYTHIC, mage.cards.u.UlvenwaldHydra.class));
        cards.add(new SetCardInfo("Vampire Neonate", 285, Rarity.COMMON, mage.cards.v.VampireNeonate.class));
        cards.add(new SetCardInfo("Vastwood Zendikon", 440, Rarity.COMMON, mage.cards.v.VastwoodZendikon.class));
        cards.add(new SetCardInfo("Verdant Embrace", 441, Rarity.RARE, mage.cards.v.VerdantEmbrace.class));
        cards.add(new SetCardInfo("Wailing Ghoul", 286, Rarity.COMMON, mage.cards.w.WailingGhoul.class));
        cards.add(new SetCardInfo("Wall of Blossoms", 442, Rarity.UNCOMMON, mage.cards.w.WallOfBlossoms.class));
        cards.add(new SetCardInfo("Winged Words", 196, Rarity.COMMON, mage.cards.w.WingedWords.class));
        cards.add(new SetCardInfo("Wizard's Retort", 198, Rarity.UNCOMMON, mage.cards.w.WizardsRetort.class));
        cards.add(new SetCardInfo("Woodborn Behemoth", 446, Rarity.UNCOMMON, mage.cards.w.WoodbornBehemoth.class));
        cards.add(new SetCardInfo("Young Pyromancer", 372, Rarity.UNCOMMON, mage.cards.y.YoungPyromancer.class));
        cards.add(new SetCardInfo("Zendikar's Roil", 448, Rarity.UNCOMMON, mage.cards.z.ZendikarsRoil.class));
    }
}
