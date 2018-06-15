package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author TheElk801
 */
public final class CoreSet2019 extends ExpansionSet {

    private static final CoreSet2019 instance = new CoreSet2019();

    public static CoreSet2019 getInstance() {
        return instance;
    }

    private CoreSet2019() {
        super("Core Set 2019", "M19", ExpansionSet.buildDate(2018, 7, 13), SetType.CORE);
        this.hasBoosters = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 280;
        cards.add(new SetCardInfo("Act of Treason", 127, Rarity.COMMON, mage.cards.a.ActOfTreason.class));
        cards.add(new SetCardInfo("Aggressive Mammoth", 302, Rarity.RARE, mage.cards.a.AggressiveMammoth.class));
        cards.add(new SetCardInfo("Air Elemental", 308, Rarity.UNCOMMON, mage.cards.a.AirElemental.class));
        cards.add(new SetCardInfo("Anticipate", 44, Rarity.COMMON, mage.cards.a.Anticipate.class));
        cards.add(new SetCardInfo("Apex of Power", 129, Rarity.MYTHIC, mage.cards.a.ApexOfPower.class));
        cards.add(new SetCardInfo("Arisen Gorgon", 292, Rarity.UNCOMMON, mage.cards.a.ArisenGorgon.class));
        cards.add(new SetCardInfo("Aven Wind Mage", 45, Rarity.COMMON, mage.cards.a.AvenWindMage.class));
        cards.add(new SetCardInfo("Befuddle", 309, Rarity.COMMON, mage.cards.b.Befuddle.class));
        cards.add(new SetCardInfo("Bogstomper", 87, Rarity.COMMON, mage.cards.b.Bogstomper.class));
        cards.add(new SetCardInfo("Bristling Boar", 170, Rarity.COMMON, mage.cards.b.BristlingBoar.class));
        cards.add(new SetCardInfo("Cancel", 48, Rarity.COMMON, mage.cards.c.Cancel.class));
        cards.add(new SetCardInfo("Catalyst Elemental", 132, Rarity.COMMON, mage.cards.c.CatalystElemental.class));
        cards.add(new SetCardInfo("Centaur Courser", 171, Rarity.COMMON, mage.cards.c.CentaurCourser.class));
        cards.add(new SetCardInfo("Colossal Dreadmaw", 172, Rarity.COMMON, mage.cards.c.ColossalDreadmaw.class));
        cards.add(new SetCardInfo("Court Cleric", 283, Rarity.UNCOMMON, mage.cards.c.CourtCleric.class));
        cards.add(new SetCardInfo("Daybreak Chaplain", 10, Rarity.COMMON, mage.cards.d.DaybreakChaplain.class));
        cards.add(new SetCardInfo("Death Baron", 90, Rarity.RARE, mage.cards.d.DeathBaron.class));
        cards.add(new SetCardInfo("Demon of Catastrophes", 91, Rarity.RARE, mage.cards.d.DemonOfCatastrophes.class));
        cards.add(new SetCardInfo("Desecrated Tomb", 230, Rarity.RARE, mage.cards.d.DesecratedTomb.class));
        cards.add(new SetCardInfo("Diregraf Ghoul", 92, Rarity.UNCOMMON, mage.cards.d.DiregrafGhoul.class));
        cards.add(new SetCardInfo("Disperse", 50, Rarity.COMMON, mage.cards.d.Disperse.class));
        cards.add(new SetCardInfo("Divination", 51, Rarity.COMMON, mage.cards.d.Divination.class));
        cards.add(new SetCardInfo("Dwarven Priest", 11, Rarity.COMMON, mage.cards.d.DwarvenPriest.class));
        cards.add(new SetCardInfo("Dwindle", 53, Rarity.COMMON, mage.cards.d.Dwindle.class));
        cards.add(new SetCardInfo("Electrify", 139, Rarity.COMMON, mage.cards.e.Electrify.class));
        cards.add(new SetCardInfo("Elvish Rejuvenator", 180, Rarity.COMMON, mage.cards.e.ElvishRejuvenator.class));
        cards.add(new SetCardInfo("Fiery Finish", 140, Rarity.UNCOMMON, mage.cards.f.FieryFinish.class));
        cards.add(new SetCardInfo("Fire Elemental", 141, Rarity.COMMON, mage.cards.f.FireElemental.class));
        cards.add(new SetCardInfo("Fountain of Renewal", 235, Rarity.UNCOMMON, mage.cards.f.FountainOfRenewal.class));
        cards.add(new SetCardInfo("Frilled Sea Serpent", 56, Rarity.COMMON, mage.cards.f.FrilledSeaSerpent.class));
        cards.add(new SetCardInfo("Gearsmith Prodigy", 57, Rarity.COMMON, mage.cards.g.GearsmithProdigy.class));
        cards.add(new SetCardInfo("Ghastbark Twins", 181, Rarity.UNCOMMON, mage.cards.g.GhastbarkTwins.class));
        cards.add(new SetCardInfo("Ghostform", 58, Rarity.COMMON, mage.cards.g.Ghostform.class));
        cards.add(new SetCardInfo("Giant Spider", 183, Rarity.COMMON, mage.cards.g.GiantSpider.class));
        cards.add(new SetCardInfo("Gigantosaurus", 185, Rarity.RARE, mage.cards.g.Gigantosaurus.class));
        cards.add(new SetCardInfo("Goblin Instigator", 142, Rarity.COMMON, mage.cards.g.GoblinInstigator.class));
        cards.add(new SetCardInfo("Goblin Motivator", 143, Rarity.COMMON, mage.cards.g.GoblinMotivator.class));
        cards.add(new SetCardInfo("Grasping Scoundrel", 312, Rarity.COMMON, mage.cards.g.GraspingScoundrel.class));
        cards.add(new SetCardInfo("Gravedigger", 98, Rarity.UNCOMMON, mage.cards.g.Gravedigger.class));
        cards.add(new SetCardInfo("Gravewaker", 293, Rarity.RARE, mage.cards.g.Gravewaker.class));
        cards.add(new SetCardInfo("Guttersnipe", 145, Rarity.UNCOMMON, mage.cards.g.Guttersnipe.class));
        cards.add(new SetCardInfo("Herald of Faith", 13, Rarity.UNCOMMON, mage.cards.h.HeraldOfFaith.class));
        cards.add(new SetCardInfo("Highland Game", 188, Rarity.COMMON, mage.cards.h.HighlandGame.class));
        cards.add(new SetCardInfo("Horizon Scholar", 59, Rarity.UNCOMMON, mage.cards.h.HorizonScholar.class));
        cards.add(new SetCardInfo("Hostile Minotaur", 147, Rarity.COMMON, mage.cards.h.HostileMinotaur.class));
        cards.add(new SetCardInfo("Infernal Judgment", 102, Rarity.RARE, mage.cards.i.InfernalJudgment.class));
        cards.add(new SetCardInfo("Infernal Scarring", 103, Rarity.COMMON, mage.cards.i.InfernalScarring.class));
        cards.add(new SetCardInfo("Inspired Charge", 15, Rarity.COMMON, mage.cards.i.InspiredCharge.class));
        cards.add(new SetCardInfo("Kargan Dragonrider", 297, Rarity.COMMON, mage.cards.k.KarganDragonrider.class));
        cards.add(new SetCardInfo("Knight of the Tusk", 18, Rarity.COMMON, mage.cards.k.KnightOfTheTusk.class));
        cards.add(new SetCardInfo("Knight's Pledge", 19, Rarity.COMMON, mage.cards.k.KnightsPledge.class));
        cards.add(new SetCardInfo("Lava Axe", 150, Rarity.COMMON, mage.cards.l.LavaAxe.class));
        cards.add(new SetCardInfo("Lich's Caress", 105, Rarity.COMMON, mage.cards.l.LichsCaress.class));
        cards.add(new SetCardInfo("Lightning Strike", 152, Rarity.COMMON, mage.cards.l.LightningStrike.class));
        cards.add(new SetCardInfo("Liliana's Spoils", 294, Rarity.RARE, mage.cards.l.LilianasSpoils.class));
        cards.add(new SetCardInfo("Llanowar Elves", 314, Rarity.COMMON, mage.cards.l.LlanowarElves.class));
        cards.add(new SetCardInfo("Loxodon Line Breaker", 24, Rarity.COMMON, mage.cards.l.LoxodonLineBreaker.class));
        cards.add(new SetCardInfo("Luminous Bonds", 25, Rarity.COMMON, mage.cards.l.LuminousBonds.class));
        cards.add(new SetCardInfo("Manalith", 239, Rarity.COMMON, mage.cards.m.Manalith.class));
        cards.add(new SetCardInfo("Marauder's Axe", 240, Rarity.COMMON, mage.cards.m.MaraudersAxe.class));
        cards.add(new SetCardInfo("Mentor of the Meek", 27, Rarity.RARE, mage.cards.m.MentorOfTheMeek.class));
        cards.add(new SetCardInfo("Meteor Golem", 241, Rarity.UNCOMMON, mage.cards.m.MeteorGolem.class));
        cards.add(new SetCardInfo("Mighty Leap", 28, Rarity.COMMON, mage.cards.m.MightyLeap.class));
        cards.add(new SetCardInfo("Militia Bugler", 29, Rarity.UNCOMMON, mage.cards.m.MilitiaBugler.class));
        cards.add(new SetCardInfo("Mist-Cloaked Herald", 310, Rarity.COMMON, mage.cards.m.MistCloakedHerald.class));
        cards.add(new SetCardInfo("Murder", 110, Rarity.UNCOMMON, mage.cards.m.Murder.class));
        cards.add(new SetCardInfo("Nexus of Fate", 306, Rarity.MYTHIC, mage.cards.n.NexusOfFate.class));
        cards.add(new SetCardInfo("Oakenform", 191, Rarity.COMMON, mage.cards.o.Oakenform.class));
        cards.add(new SetCardInfo("Omenspeaker", 64, Rarity.COMMON, mage.cards.o.Omenspeaker.class));
        cards.add(new SetCardInfo("Onakke Ogre", 153, Rarity.COMMON, mage.cards.o.OnakkeOgre.class));
        cards.add(new SetCardInfo("Oreskos Swiftclaw", 31, Rarity.COMMON, mage.cards.o.OreskosSwiftclaw.class));
        cards.add(new SetCardInfo("Patient Rebuilding", 67, Rarity.RARE, mage.cards.p.PatientRebuilding.class));
        cards.add(new SetCardInfo("Pegasus Courser", 32, Rarity.COMMON, mage.cards.p.PegasusCourser.class));
        cards.add(new SetCardInfo("Plummet", 193, Rarity.COMMON, mage.cards.p.Plummet.class));
        cards.add(new SetCardInfo("Rabid Bite", 195, Rarity.COMMON, mage.cards.r.RabidBite.class));
        cards.add(new SetCardInfo("Radiating Lightning", 313, Rarity.COMMON, mage.cards.r.RadiatingLightning.class));
        cards.add(new SetCardInfo("Reassembling Skeleton", 116, Rarity.UNCOMMON, mage.cards.r.ReassemblingSkeleton.class));
        cards.add(new SetCardInfo("Reclamation Sage", 196, Rarity.UNCOMMON, mage.cards.r.ReclamationSage.class));
        cards.add(new SetCardInfo("Recollect", 197, Rarity.UNCOMMON, mage.cards.r.Recollect.class));
        cards.add(new SetCardInfo("Reliquary Tower", 254, Rarity.UNCOMMON, mage.cards.r.ReliquaryTower.class));
        cards.add(new SetCardInfo("Revitalize", 35, Rarity.COMMON, mage.cards.r.Revitalize.class));
        cards.add(new SetCardInfo("Riddlemaster Sphinx", 287, Rarity.RARE, mage.cards.r.RiddlemasterSphinx.class));
        cards.add(new SetCardInfo("Rogue's Gloves", 243, Rarity.UNCOMMON, mage.cards.r.RoguesGloves.class));
        cards.add(new SetCardInfo("Rustwing Falcon", 36, Rarity.COMMON, mage.cards.r.RustwingFalcon.class));
        cards.add(new SetCardInfo("Salvager of Secrets", 70, Rarity.COMMON, mage.cards.s.SalvagerOfSecrets.class));
        cards.add(new SetCardInfo("Scholar of Stars", 71, Rarity.COMMON, mage.cards.s.ScholarOfStars.class));
        cards.add(new SetCardInfo("Serra's Guardian", 284, Rarity.RARE, mage.cards.s.SerrasGuardian.class));
        cards.add(new SetCardInfo("Shivan Dragon", 300, Rarity.RARE, mage.cards.s.ShivanDragon.class));
        cards.add(new SetCardInfo("Shock", 156, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Silverbeak Griffin", 285, Rarity.COMMON, mage.cards.s.SilverbeakGriffin.class));
        cards.add(new SetCardInfo("Skeleton Archer", 118, Rarity.COMMON, mage.cards.s.SkeletonArcher.class));
        cards.add(new SetCardInfo("Skymarch Bloodletter", 119, Rarity.COMMON, mage.cards.s.SkymarchBloodletter.class));
        cards.add(new SetCardInfo("Skyscanner", 245, Rarity.COMMON, mage.cards.s.Skyscanner.class));
        cards.add(new SetCardInfo("Sleep", 74, Rarity.UNCOMMON, mage.cards.s.Sleep.class));
        cards.add(new SetCardInfo("Smelt", 158, Rarity.COMMON, mage.cards.s.Smelt.class));
        cards.add(new SetCardInfo("Snapping Drake", 75, Rarity.COMMON, mage.cards.s.SnappingDrake.class));
        cards.add(new SetCardInfo("Sovereign's Bite", 120, Rarity.COMMON, mage.cards.s.SovereignsBite.class));
        cards.add(new SetCardInfo("Sparktongue Dragon", 159, Rarity.COMMON, mage.cards.s.SparktongueDragon.class));
        cards.add(new SetCardInfo("Star-Crowned Stag", 38, Rarity.COMMON, mage.cards.s.StarCrownedStag.class));
        cards.add(new SetCardInfo("Strangling Spores", 122, Rarity.COMMON, mage.cards.s.StranglingSpores.class));
        cards.add(new SetCardInfo("Sun Sentinel", 307, Rarity.COMMON, mage.cards.s.SunSentinel.class));
        cards.add(new SetCardInfo("Sure Strike", 161, Rarity.COMMON, mage.cards.s.SureStrike.class));
        cards.add(new SetCardInfo("Take Vengeance", 40, Rarity.COMMON, mage.cards.t.TakeVengeance.class));
        cards.add(new SetCardInfo("Tattered Mummy", 295, Rarity.COMMON, mage.cards.t.TatteredMummy.class));
        cards.add(new SetCardInfo("Tezzeret's Strider", 290, Rarity.UNCOMMON, mage.cards.t.TezzeretsStrider.class));
        cards.add(new SetCardInfo("Thornhide Wolves", 204, Rarity.COMMON, mage.cards.t.ThornhideWolves.class));
        cards.add(new SetCardInfo("Thud", 163, Rarity.UNCOMMON, mage.cards.t.Thud.class));
        cards.add(new SetCardInfo("Timber Gorge", 258, Rarity.COMMON, mage.cards.t.TimberGorge.class));
        cards.add(new SetCardInfo("Titanic Growth", 205, Rarity.COMMON, mage.cards.t.TitanicGrowth.class));
        cards.add(new SetCardInfo("Tolarian Scholar", 80, Rarity.COMMON, mage.cards.t.TolarianScholar.class));
        cards.add(new SetCardInfo("Tormenting Voice", 164, Rarity.COMMON, mage.cards.t.TormentingVoice.class));
        cards.add(new SetCardInfo("Totally Lost", 81, Rarity.COMMON, mage.cards.t.TotallyLost.class));
        cards.add(new SetCardInfo("Trumpet Blast", 165, Rarity.COMMON, mage.cards.t.TrumpetBlast.class));
        cards.add(new SetCardInfo("Trusty Packbeast", 41, Rarity.COMMON, mage.cards.t.TrustyPackbeast.class));
        cards.add(new SetCardInfo("Two-Headed Zombie", 123, Rarity.COMMON, mage.cards.t.TwoHeadedZombie.class));
        cards.add(new SetCardInfo("Uncomfortable Chill", 82, Rarity.COMMON, mage.cards.u.UncomfortableChill.class));
        cards.add(new SetCardInfo("Ursine Champion", 304, Rarity.COMMON, mage.cards.u.UrsineChampion.class));
        cards.add(new SetCardInfo("Vampire Sovereign", 125, Rarity.UNCOMMON, mage.cards.v.VampireSovereign.class));
        cards.add(new SetCardInfo("Vivien Reid", 208, Rarity.MYTHIC, mage.cards.v.VivienReid.class));
        cards.add(new SetCardInfo("Vivien's Invocation", 209, Rarity.RARE, mage.cards.v.ViviensInvocation.class));
        cards.add(new SetCardInfo("Vivien's Jaguar", 305, Rarity.UNCOMMON, mage.cards.v.ViviensJaguar.class));
        cards.add(new SetCardInfo("Volcanic Dragon", 167, Rarity.UNCOMMON, mage.cards.v.VolcanicDragon.class));
        cards.add(new SetCardInfo("Walking Corpse", 126, Rarity.COMMON, mage.cards.w.WalkingCorpse.class));
        cards.add(new SetCardInfo("Wall of Mist", 83, Rarity.COMMON, mage.cards.w.WallOfMist.class));
        cards.add(new SetCardInfo("Wall of Vines", 210, Rarity.COMMON, mage.cards.w.WallOfVines.class));
        cards.add(new SetCardInfo("Waterknot", 311, Rarity.COMMON, mage.cards.w.Waterknot.class));
    }
}
