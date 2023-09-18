package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
import mage.collation.BoosterCollator;
import mage.collation.BoosterStructure;
import mage.collation.CardRun;
import mage.collation.RarityConfiguration;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author TheElk801
 */
public final class CoreSet2021 extends ExpansionSet {

    private static final CoreSet2021 instance = new CoreSet2021();

    public static CoreSet2021 getInstance() {
        return instance;
    }

    private CoreSet2021() {
        super("Core Set 2021", "M21", ExpansionSet.buildDate(2020, 7, 3), SetType.CORE);
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 274;
        this.ratioBoosterSpecialLand = 2;
        this.ratioBoosterSpecialLandNumerator = 1;

        cards.add(new SetCardInfo("Adherent of Hope", 321, Rarity.COMMON, mage.cards.a.AdherentOfHope.class));
        cards.add(new SetCardInfo("Alchemist's Gift", 87, Rarity.COMMON, mage.cards.a.AlchemistsGift.class));
        cards.add(new SetCardInfo("Alpine Houndmaster", 215, Rarity.UNCOMMON, mage.cards.a.AlpineHoundmaster.class));
        cards.add(new SetCardInfo("Alpine Watchdog", 2, Rarity.COMMON, mage.cards.a.AlpineWatchdog.class));
        cards.add(new SetCardInfo("Angelic Ascension", 3, Rarity.UNCOMMON, mage.cards.a.AngelicAscension.class));
        cards.add(new SetCardInfo("Animal Sanctuary", 242, Rarity.RARE, mage.cards.a.AnimalSanctuary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Animal Sanctuary", 385, Rarity.RARE, mage.cards.a.AnimalSanctuary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Anointed Chorister", 4, Rarity.COMMON, mage.cards.a.AnointedChorister.class));
        cards.add(new SetCardInfo("Archfiend's Vessel", 88, Rarity.UNCOMMON, mage.cards.a.ArchfiendsVessel.class));
        cards.add(new SetCardInfo("Aven Gagglemaster", 5, Rarity.UNCOMMON, mage.cards.a.AvenGagglemaster.class));
        cards.add(new SetCardInfo("Azusa, Lost but Seeking", 173, Rarity.RARE, mage.cards.a.AzusaLostButSeeking.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Azusa, Lost but Seeking", 372, Rarity.RARE, mage.cards.a.AzusaLostButSeeking.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bad Deal", 89, Rarity.UNCOMMON, mage.cards.b.BadDeal.class));
        cards.add(new SetCardInfo("Baneslayer Angel", 340, Rarity.MYTHIC, mage.cards.b.BaneslayerAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Baneslayer Angel", 6, Rarity.MYTHIC, mage.cards.b.BaneslayerAngel.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Barrin, Tolarian Archmage", 348, Rarity.RARE, mage.cards.b.BarrinTolarianArchmage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Barrin, Tolarian Archmage", 45, Rarity.RARE, mage.cards.b.BarrinTolarianArchmage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Basri Ket", 280, Rarity.MYTHIC, mage.cards.b.BasriKet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Basri Ket", 286, Rarity.MYTHIC, mage.cards.b.BasriKet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Basri Ket", 7, Rarity.MYTHIC, mage.cards.b.BasriKet.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Basri's Acolyte", 287, Rarity.COMMON, mage.cards.b.BasrisAcolyte.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Basri's Acolyte", 8, Rarity.COMMON, mage.cards.b.BasrisAcolyte.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Basri's Aegis", 322, Rarity.RARE, mage.cards.b.BasrisAegis.class));
        cards.add(new SetCardInfo("Basri's Lieutenant", 288, Rarity.RARE, mage.cards.b.BasrisLieutenant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Basri's Lieutenant", 9, Rarity.RARE, mage.cards.b.BasrisLieutenant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Basri's Solidarity", 10, Rarity.UNCOMMON, mage.cards.b.BasrisSolidarity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Basri's Solidarity", 289, Rarity.UNCOMMON, mage.cards.b.BasrisSolidarity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Basri, Devoted Paladin", 320, Rarity.MYTHIC, mage.cards.b.BasriDevotedPaladin.class));
        cards.add(new SetCardInfo("Battle-Rattle Shaman", 130, Rarity.UNCOMMON, mage.cards.b.BattleRattleShaman.class));
        cards.add(new SetCardInfo("Blood Glutton", 90, Rarity.COMMON, mage.cards.b.BloodGlutton.class));
        cards.add(new SetCardInfo("Bloodfell Caves", 243, Rarity.COMMON, mage.cards.b.BloodfellCaves.class));
        cards.add(new SetCardInfo("Blossoming Sands", 244, Rarity.COMMON, mage.cards.b.BlossomingSands.class));
        cards.add(new SetCardInfo("Bolt Hound", 131, Rarity.UNCOMMON, mage.cards.b.BoltHound.class));
        cards.add(new SetCardInfo("Bone Pit Brute", 132, Rarity.COMMON, mage.cards.b.BonePitBrute.class));
        cards.add(new SetCardInfo("Brash Taunter", 133, Rarity.RARE, mage.cards.b.BrashTaunter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brash Taunter", 363, Rarity.RARE, mage.cards.b.BrashTaunter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Burlfist Oak", 174, Rarity.UNCOMMON, mage.cards.b.BurlfistOak.class));
        cards.add(new SetCardInfo("Burn Bright", 134, Rarity.COMMON, mage.cards.b.BurnBright.class));
        cards.add(new SetCardInfo("Caged Zombie", 91, Rarity.COMMON, mage.cards.c.CagedZombie.class));
        cards.add(new SetCardInfo("Cancel", 46, Rarity.COMMON, mage.cards.c.Cancel.class));
        cards.add(new SetCardInfo("Canopy Stalker", 175, Rarity.UNCOMMON, mage.cards.c.CanopyStalker.class));
        cards.add(new SetCardInfo("Capture Sphere", 47, Rarity.COMMON, mage.cards.c.CaptureSphere.class));
        cards.add(new SetCardInfo("Carrion Grub", 92, Rarity.UNCOMMON, mage.cards.c.CarrionGrub.class));
        cards.add(new SetCardInfo("Celestial Enforcer", 11, Rarity.COMMON, mage.cards.c.CelestialEnforcer.class));
        cards.add(new SetCardInfo("Chandra's Firemaw", 333, Rarity.RARE, mage.cards.c.ChandrasFiremaw.class));
        cards.add(new SetCardInfo("Chandra's Incinerator", 136, Rarity.RARE, mage.cards.c.ChandrasIncinerator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra's Incinerator", 302, Rarity.RARE, mage.cards.c.ChandrasIncinerator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra's Magmutt", 137, Rarity.COMMON, mage.cards.c.ChandrasMagmutt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra's Magmutt", 303, Rarity.COMMON, mage.cards.c.ChandrasMagmutt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra's Pyreling", 138, Rarity.UNCOMMON, mage.cards.c.ChandrasPyreling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra's Pyreling", 304, Rarity.UNCOMMON, mage.cards.c.ChandrasPyreling.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra, Flame's Catalyst", 332, Rarity.MYTHIC, mage.cards.c.ChandraFlamesCatalyst.class));
        cards.add(new SetCardInfo("Chandra, Heart of Fire", 135, Rarity.MYTHIC, mage.cards.c.ChandraHeartOfFire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra, Heart of Fire", 283, Rarity.MYTHIC, mage.cards.c.ChandraHeartOfFire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra, Heart of Fire", 301, Rarity.MYTHIC, mage.cards.c.ChandraHeartOfFire.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chromatic Orrery", 228, Rarity.MYTHIC, mage.cards.c.ChromaticOrrery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chromatic Orrery", 382, Rarity.MYTHIC, mage.cards.c.ChromaticOrrery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chrome Replicator", 229, Rarity.UNCOMMON, mage.cards.c.ChromeReplicator.class));
        cards.add(new SetCardInfo("Colossal Dreadmaw", 176, Rarity.COMMON, mage.cards.c.ColossalDreadmaw.class));
        cards.add(new SetCardInfo("Conclave Mentor", 216, Rarity.UNCOMMON, mage.cards.c.ConclaveMentor.class));
        cards.add(new SetCardInfo("Concordia Pegasus", 12, Rarity.COMMON, mage.cards.c.ConcordiaPegasus.class));
        cards.add(new SetCardInfo("Conspicuous Snoop", 139, Rarity.RARE, mage.cards.c.ConspicuousSnoop.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Conspicuous Snoop", 364, Rarity.RARE, mage.cards.c.ConspicuousSnoop.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Containment Priest", 13, Rarity.RARE, mage.cards.c.ContainmentPriest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Containment Priest", 314, Rarity.RARE, mage.cards.c.ContainmentPriest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crash Through", 140, Rarity.COMMON, mage.cards.c.CrashThrough.class));
        cards.add(new SetCardInfo("Crypt Lurker", 93, Rarity.COMMON, mage.cards.c.CryptLurker.class));
        cards.add(new SetCardInfo("Cultivate", 177, Rarity.UNCOMMON, mage.cards.c.Cultivate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cultivate", 317, Rarity.RARE, mage.cards.c.Cultivate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Daybreak Charger", 14, Rarity.COMMON, mage.cards.d.DaybreakCharger.class));
        cards.add(new SetCardInfo("Deathbloom Thallid", 94, Rarity.COMMON, mage.cards.d.DeathbloomThallid.class));
        cards.add(new SetCardInfo("Defiant Strike", 15, Rarity.COMMON, mage.cards.d.DefiantStrike.class));
        cards.add(new SetCardInfo("Demonic Embrace", 356, Rarity.RARE, mage.cards.d.DemonicEmbrace.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Demonic Embrace", 95, Rarity.RARE, mage.cards.d.DemonicEmbrace.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Destructive Tampering", 141, Rarity.COMMON, mage.cards.d.DestructiveTampering.class));
        cards.add(new SetCardInfo("Dire Fleet Warmonger", 217, Rarity.UNCOMMON, mage.cards.d.DireFleetWarmonger.class));
        cards.add(new SetCardInfo("Discontinuity", 349, Rarity.MYTHIC, mage.cards.d.Discontinuity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Discontinuity", 48, Rarity.MYTHIC, mage.cards.d.Discontinuity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dismal Backwater", 245, Rarity.COMMON, mage.cards.d.DismalBackwater.class));
        cards.add(new SetCardInfo("Double Vision", 142, Rarity.RARE, mage.cards.d.DoubleVision.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Double Vision", 365, Rarity.RARE, mage.cards.d.DoubleVision.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Drowsing Tyrannodon", 178, Rarity.COMMON, mage.cards.d.DrowsingTyrannodon.class));
        cards.add(new SetCardInfo("Dub", 16, Rarity.COMMON, mage.cards.d.Dub.class));
        cards.add(new SetCardInfo("Duress", 96, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Elder Gargaroth", 179, Rarity.MYTHIC, mage.cards.e.ElderGargaroth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Elder Gargaroth", 373, Rarity.MYTHIC, mage.cards.e.ElderGargaroth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eliminate", 395, Rarity.UNCOMMON, mage.cards.e.Eliminate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Eliminate", 97, Rarity.UNCOMMON, mage.cards.e.Eliminate.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Enthralling Hold", 49, Rarity.UNCOMMON, mage.cards.e.EnthrallingHold.class));
        cards.add(new SetCardInfo("Epitaph Golem", 230, Rarity.UNCOMMON, mage.cards.e.EpitaphGolem.class));
        cards.add(new SetCardInfo("Experimental Overload", 218, Rarity.UNCOMMON, mage.cards.e.ExperimentalOverload.class));
        cards.add(new SetCardInfo("Fabled Passage", 246, Rarity.RARE, mage.cards.f.FabledPassage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fabled Passage", 386, Rarity.RARE, mage.cards.f.FabledPassage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Faith's Fetters", 17, Rarity.UNCOMMON, mage.cards.f.FaithsFetters.class));
        cards.add(new SetCardInfo("Falconer Adept", 18, Rarity.UNCOMMON, mage.cards.f.FalconerAdept.class));
        cards.add(new SetCardInfo("Feat of Resistance", 19, Rarity.COMMON, mage.cards.f.FeatOfResistance.class));
        cards.add(new SetCardInfo("Feline Sovereign", 180, Rarity.RARE, mage.cards.f.FelineSovereign.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Feline Sovereign", 374, Rarity.RARE, mage.cards.f.FelineSovereign.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fetid Imp", 98, Rarity.COMMON, mage.cards.f.FetidImp.class));
        cards.add(new SetCardInfo("Fierce Empath", 181, Rarity.UNCOMMON, mage.cards.f.FierceEmpath.class));
        cards.add(new SetCardInfo("Fiery Emancipation", 143, Rarity.MYTHIC, mage.cards.f.FieryEmancipation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fiery Emancipation", 366, Rarity.MYTHIC, mage.cards.f.FieryEmancipation.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Finishing Blow", 99, Rarity.COMMON, mage.cards.f.FinishingBlow.class));
        cards.add(new SetCardInfo("Forest", 272, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 273, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 274, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 313, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forgotten Sentinel", 231, Rarity.COMMON, mage.cards.f.ForgottenSentinel.class));
        cards.add(new SetCardInfo("Frantic Inventory", 394, Rarity.COMMON, mage.cards.f.FranticInventory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frantic Inventory", 50, Rarity.COMMON, mage.cards.f.FranticInventory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frost Breath", 51, Rarity.COMMON, mage.cards.f.FrostBreath.class));
        cards.add(new SetCardInfo("Fungal Rebirth", 182, Rarity.UNCOMMON, mage.cards.f.FungalRebirth.class));
        cards.add(new SetCardInfo("Furious Rise", 144, Rarity.UNCOMMON, mage.cards.f.FuriousRise.class));
        cards.add(new SetCardInfo("Furor of the Bitten", 145, Rarity.COMMON, mage.cards.f.FurorOfTheBitten.class));
        cards.add(new SetCardInfo("Gadrak, the Crown-Scourge", 146, Rarity.RARE, mage.cards.g.GadrakTheCrownScourge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gadrak, the Crown-Scourge", 367, Rarity.RARE, mage.cards.g.GadrakTheCrownScourge.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gale Swooper", 20, Rarity.COMMON, mage.cards.g.GaleSwooper.class));
        cards.add(new SetCardInfo("Garruk's Gorehorn", 184, Rarity.COMMON, mage.cards.g.GarruksGorehorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garruk's Gorehorn", 306, Rarity.COMMON, mage.cards.g.GarruksGorehorn.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garruk's Harbinger", 185, Rarity.RARE, mage.cards.g.GarruksHarbinger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garruk's Harbinger", 307, Rarity.RARE, mage.cards.g.GarruksHarbinger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garruk's Uprising", 186, Rarity.UNCOMMON, mage.cards.g.GarruksUprising.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garruk's Uprising", 308, Rarity.UNCOMMON, mage.cards.g.GarruksUprising.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garruk's Warsteed", 337, Rarity.RARE, mage.cards.g.GarruksWarsteed.class));
        cards.add(new SetCardInfo("Garruk, Savage Herald", 336, Rarity.MYTHIC, mage.cards.g.GarrukSavageHerald.class));
        cards.add(new SetCardInfo("Garruk, Unleashed", 183, Rarity.MYTHIC, mage.cards.g.GarrukUnleashed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garruk, Unleashed", 284, Rarity.MYTHIC, mage.cards.g.GarrukUnleashed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Garruk, Unleashed", 305, Rarity.MYTHIC, mage.cards.g.GarrukUnleashed.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ghostly Pilferer", 350, Rarity.RARE, mage.cards.g.GhostlyPilferer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ghostly Pilferer", 52, Rarity.RARE, mage.cards.g.GhostlyPilferer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gloom Sower", 100, Rarity.COMMON, mage.cards.g.GloomSower.class));
        cards.add(new SetCardInfo("Glorious Anthem", 21, Rarity.RARE, mage.cards.g.GloriousAnthem.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glorious Anthem", 341, Rarity.RARE, mage.cards.g.GloriousAnthem.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gnarled Sage", 187, Rarity.COMMON, mage.cards.g.GnarledSage.class));
        cards.add(new SetCardInfo("Goblin Arsonist", 147, Rarity.COMMON, mage.cards.g.GoblinArsonist.class));
        cards.add(new SetCardInfo("Goblin Wizardry", 148, Rarity.COMMON, mage.cards.g.GoblinWizardry.class));
        cards.add(new SetCardInfo("Goremand", 101, Rarity.UNCOMMON, mage.cards.g.Goremand.class));
        cards.add(new SetCardInfo("Grasp of Darkness", 102, Rarity.COMMON, mage.cards.g.GraspOfDarkness.class));
        cards.add(new SetCardInfo("Griffin Aerie", 22, Rarity.UNCOMMON, mage.cards.g.GriffinAerie.class));
        cards.add(new SetCardInfo("Grim Tutor", 103, Rarity.MYTHIC, mage.cards.g.GrimTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Grim Tutor", 315, Rarity.MYTHIC, mage.cards.g.GrimTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Havoc Jester", 149, Rarity.UNCOMMON, mage.cards.h.HavocJester.class));
        cards.add(new SetCardInfo("Heartfire Immolator", 150, Rarity.UNCOMMON, mage.cards.h.HeartfireImmolator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heartfire Immolator", 396, Rarity.UNCOMMON, mage.cards.h.HeartfireImmolator.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hellkite Punisher", 151, Rarity.UNCOMMON, mage.cards.h.HellkitePunisher.class));
        cards.add(new SetCardInfo("Heroic Intervention", 188, Rarity.RARE, mage.cards.h.HeroicIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Heroic Intervention", 375, Rarity.RARE, mage.cards.h.HeroicIntervention.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Historian of Zhalfir", 325, Rarity.UNCOMMON, mage.cards.h.HistorianOfZhalfir.class));
        cards.add(new SetCardInfo("Hobblefiend", 152, Rarity.COMMON, mage.cards.h.Hobblefiend.class));
        cards.add(new SetCardInfo("Hooded Blightfang", 104, Rarity.RARE, mage.cards.h.HoodedBlightfang.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hooded Blightfang", 357, Rarity.RARE, mage.cards.h.HoodedBlightfang.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hunter's Edge", 189, Rarity.COMMON, mage.cards.h.HuntersEdge.class));
        cards.add(new SetCardInfo("Idol of Endurance", 23, Rarity.RARE, mage.cards.i.IdolOfEndurance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Idol of Endurance", 342, Rarity.RARE, mage.cards.i.IdolOfEndurance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Igneous Cur", 153, Rarity.COMMON, mage.cards.i.IgneousCur.class));
        cards.add(new SetCardInfo("Indulging Patrician", 219, Rarity.UNCOMMON, mage.cards.i.IndulgingPatrician.class));
        cards.add(new SetCardInfo("Infernal Scarring", 105, Rarity.COMMON, mage.cards.i.InfernalScarring.class));
        cards.add(new SetCardInfo("Invigorating Surge", 190, Rarity.UNCOMMON, mage.cards.i.InvigoratingSurge.class));
        cards.add(new SetCardInfo("Island", 263, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 264, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 265, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 310, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jeskai Elder", 53, Rarity.UNCOMMON, mage.cards.j.JeskaiElder.class));
        cards.add(new SetCardInfo("Jolrael, Mwonvuli Recluse", 191, Rarity.RARE, mage.cards.j.JolraelMwonvuliRecluse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jolrael, Mwonvuli Recluse", 376, Rarity.RARE, mage.cards.j.JolraelMwonvuliRecluse.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jungle Hollow", 247, Rarity.COMMON, mage.cards.j.JungleHollow.class));
        cards.add(new SetCardInfo("Kaervek, the Spiteful", 106, Rarity.RARE, mage.cards.k.KaervekTheSpiteful.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kaervek, the Spiteful", 358, Rarity.RARE, mage.cards.k.KaervekTheSpiteful.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Keen Glidemaster", 54, Rarity.COMMON, mage.cards.k.KeenGlidemaster.class));
        cards.add(new SetCardInfo("Keral Keep Disciples", 334, Rarity.UNCOMMON, mage.cards.k.KeralKeepDisciples.class));
        cards.add(new SetCardInfo("Kinetic Augur", 154, Rarity.UNCOMMON, mage.cards.k.KineticAugur.class));
        cards.add(new SetCardInfo("Kitesail Freebooter", 107, Rarity.UNCOMMON, mage.cards.k.KitesailFreebooter.class));
        cards.add(new SetCardInfo("Leafkin Avenger", 220, Rarity.UNCOMMON, mage.cards.l.LeafkinAvenger.class));
        cards.add(new SetCardInfo("Legion's Judgment", 24, Rarity.COMMON, mage.cards.l.LegionsJudgment.class));
        cards.add(new SetCardInfo("Library Larcenist", 55, Rarity.COMMON, mage.cards.l.LibraryLarcenist.class));
        cards.add(new SetCardInfo("Life Goes On", 192, Rarity.COMMON, mage.cards.l.LifeGoesOn.class));
        cards.add(new SetCardInfo("Light of Promise", 25, Rarity.UNCOMMON, mage.cards.l.LightOfPromise.class));
        cards.add(new SetCardInfo("Liliana's Devotee", 109, Rarity.UNCOMMON, mage.cards.l.LilianasDevotee.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana's Devotee", 298, Rarity.UNCOMMON, mage.cards.l.LilianasDevotee.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana's Scorn", 329, Rarity.RARE, mage.cards.l.LilianasScorn.class));
        cards.add(new SetCardInfo("Liliana's Scrounger", 330, Rarity.UNCOMMON, mage.cards.l.LilianasScrounger.class));
        cards.add(new SetCardInfo("Liliana's Standard Bearer", 110, Rarity.RARE, mage.cards.l.LilianasStandardBearer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana's Standard Bearer", 299, Rarity.RARE, mage.cards.l.LilianasStandardBearer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana's Steward", 111, Rarity.COMMON, mage.cards.l.LilianasSteward.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana's Steward", 300, Rarity.COMMON, mage.cards.l.LilianasSteward.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana, Death Mage", 328, Rarity.MYTHIC, mage.cards.l.LilianaDeathMage.class));
        cards.add(new SetCardInfo("Liliana, Waker of the Dead", 108, Rarity.MYTHIC, mage.cards.l.LilianaWakerOfTheDead.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana, Waker of the Dead", 282, Rarity.MYTHIC, mage.cards.l.LilianaWakerOfTheDead.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana, Waker of the Dead", 297, Rarity.MYTHIC, mage.cards.l.LilianaWakerOfTheDead.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Llanowar Visionary", 193, Rarity.COMMON, mage.cards.l.LlanowarVisionary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Llanowar Visionary", 397, Rarity.COMMON, mage.cards.l.LlanowarVisionary.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Lofty Denial", 56, Rarity.COMMON, mage.cards.l.LoftyDenial.class));
        cards.add(new SetCardInfo("Lorescale Coatl", 221, Rarity.UNCOMMON, mage.cards.l.LorescaleCoatl.class));
        cards.add(new SetCardInfo("Makeshift Battalion", 26, Rarity.COMMON, mage.cards.m.MakeshiftBattalion.class));
        cards.add(new SetCardInfo("Malefic Scythe", 112, Rarity.UNCOMMON, mage.cards.m.MaleficScythe.class));
        cards.add(new SetCardInfo("Mangara, the Diplomat", 27, Rarity.MYTHIC, mage.cards.m.MangaraTheDiplomat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mangara, the Diplomat", 343, Rarity.MYTHIC, mage.cards.m.MangaraTheDiplomat.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Masked Blackguard", 113, Rarity.COMMON, mage.cards.m.MaskedBlackguard.class));
        cards.add(new SetCardInfo("Massacre Wurm", 114, Rarity.MYTHIC, mage.cards.m.MassacreWurm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Massacre Wurm", 316, Rarity.MYTHIC, mage.cards.m.MassacreWurm.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mazemind Tome", 232, Rarity.RARE, mage.cards.m.MazemindTome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mazemind Tome", 383, Rarity.RARE, mage.cards.m.MazemindTome.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Meteorite", 233, Rarity.UNCOMMON, mage.cards.m.Meteorite.class));
        cards.add(new SetCardInfo("Mind Rot", 115, Rarity.COMMON, mage.cards.m.MindRot.class));
        cards.add(new SetCardInfo("Miscast", 57, Rarity.UNCOMMON, mage.cards.m.Miscast.class));
        cards.add(new SetCardInfo("Mistral Singer", 58, Rarity.COMMON, mage.cards.m.MistralSinger.class));
        cards.add(new SetCardInfo("Mountain", 269, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 270, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 271, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 312, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Skyfish", 326, Rarity.COMMON, mage.cards.m.MysticSkyfish.class));
        cards.add(new SetCardInfo("Necromentia", 116, Rarity.RARE, mage.cards.n.Necromentia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Necromentia", 359, Rarity.RARE, mage.cards.n.Necromentia.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Niambi, Esteemed Speaker", 222, Rarity.RARE, mage.cards.n.NiambiEsteemedSpeaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Niambi, Esteemed Speaker", 379, Rarity.RARE, mage.cards.n.NiambiEsteemedSpeaker.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nine Lives", 28, Rarity.RARE, mage.cards.n.NineLives.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Nine Lives", 344, Rarity.RARE, mage.cards.n.NineLives.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Obsessive Stitcher", 223, Rarity.UNCOMMON, mage.cards.o.ObsessiveStitcher.class));
        cards.add(new SetCardInfo("Onakke Ogre", 155, Rarity.COMMON, mage.cards.o.OnakkeOgre.class));
        cards.add(new SetCardInfo("Opt", 59, Rarity.COMMON, mage.cards.o.Opt.class));
        cards.add(new SetCardInfo("Ornery Dilophosaur", 194, Rarity.COMMON, mage.cards.o.OrneryDilophosaur.class));
        cards.add(new SetCardInfo("Pack Leader", 29, Rarity.RARE, mage.cards.p.PackLeader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pack Leader", 345, Rarity.RARE, mage.cards.p.PackLeader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pack Leader", 392, Rarity.RARE, mage.cards.p.PackLeader.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Palladium Myr", 234, Rarity.UNCOMMON, mage.cards.p.PalladiumMyr.class));
        cards.add(new SetCardInfo("Peer into the Abyss", 117, Rarity.RARE, mage.cards.p.PeerIntoTheAbyss.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Peer into the Abyss", 360, Rarity.RARE, mage.cards.p.PeerIntoTheAbyss.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pestilent Haze", 118, Rarity.UNCOMMON, mage.cards.p.PestilentHaze.class));
        cards.add(new SetCardInfo("Pitchburn Devils", 156, Rarity.COMMON, mage.cards.p.PitchburnDevils.class));
        cards.add(new SetCardInfo("Plains", 260, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 261, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 262, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 309, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Portcullis Vine", 195, Rarity.COMMON, mage.cards.p.PortcullisVine.class));
        cards.add(new SetCardInfo("Predatory Wurm", 338, Rarity.UNCOMMON, mage.cards.p.PredatoryWurm.class));
        cards.add(new SetCardInfo("Pridemalkin", 196, Rarity.COMMON, mage.cards.p.Pridemalkin.class));
        cards.add(new SetCardInfo("Primal Might", 197, Rarity.RARE, mage.cards.p.PrimalMight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Primal Might", 377, Rarity.RARE, mage.cards.p.PrimalMight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prismite", 235, Rarity.COMMON, mage.cards.p.Prismite.class));
        cards.add(new SetCardInfo("Pursued Whale", 351, Rarity.RARE, mage.cards.p.PursuedWhale.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pursued Whale", 60, Rarity.RARE, mage.cards.p.PursuedWhale.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Quirion Dryad", 198, Rarity.UNCOMMON, mage.cards.q.QuirionDryad.class));
        cards.add(new SetCardInfo("Radha, Heart of Keld", 224, Rarity.RARE, mage.cards.r.RadhaHeartOfKeld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radha, Heart of Keld", 380, Rarity.RARE, mage.cards.r.RadhaHeartOfKeld.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Radiant Fountain", 248, Rarity.COMMON, mage.cards.r.RadiantFountain.class));
        cards.add(new SetCardInfo("Rain of Revelation", 61, Rarity.UNCOMMON, mage.cards.r.RainOfRevelation.class));
        cards.add(new SetCardInfo("Rambunctious Mutt", 30, Rarity.COMMON, mage.cards.r.RambunctiousMutt.class));
        cards.add(new SetCardInfo("Ranger's Guile", 199, Rarity.COMMON, mage.cards.r.RangersGuile.class));
        cards.add(new SetCardInfo("Read the Tides", 62, Rarity.COMMON, mage.cards.r.ReadTheTides.class));
        cards.add(new SetCardInfo("Return to Nature", 200, Rarity.COMMON, mage.cards.r.ReturnToNature.class));
        cards.add(new SetCardInfo("Revitalize", 31, Rarity.COMMON, mage.cards.r.Revitalize.class));
        cards.add(new SetCardInfo("Rewind", 63, Rarity.UNCOMMON, mage.cards.r.Rewind.class));
        cards.add(new SetCardInfo("Riddleform", 64, Rarity.UNCOMMON, mage.cards.r.Riddleform.class));
        cards.add(new SetCardInfo("Rin and Seri, Inseparable", 278, Rarity.MYTHIC, mage.cards.r.RinAndSeriInseparable.class));
        cards.add(new SetCardInfo("Rise Again", 119, Rarity.COMMON, mage.cards.r.RiseAgain.class));
        cards.add(new SetCardInfo("Roaming Ghostlight", 65, Rarity.COMMON, mage.cards.r.RoamingGhostlight.class));
        cards.add(new SetCardInfo("Rookie Mistake", 66, Rarity.COMMON, mage.cards.r.RookieMistake.class));
        cards.add(new SetCardInfo("Rousing Read", 67, Rarity.COMMON, mage.cards.r.RousingRead.class));
        cards.add(new SetCardInfo("Rugged Highlands", 249, Rarity.COMMON, mage.cards.r.RuggedHighlands.class));
        cards.add(new SetCardInfo("Run Afoul", 201, Rarity.COMMON, mage.cards.r.RunAfoul.class));
        cards.add(new SetCardInfo("Runed Halo", 32, Rarity.RARE, mage.cards.r.RunedHalo.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Runed Halo", 346, Rarity.RARE, mage.cards.r.RunedHalo.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sabertooth Mauler", 202, Rarity.COMMON, mage.cards.s.SabertoothMauler.class));
        cards.add(new SetCardInfo("Sanctum of All", 225, Rarity.RARE, mage.cards.s.SanctumOfAll.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sanctum of All", 381, Rarity.RARE, mage.cards.s.SanctumOfAll.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sanctum of Calm Waters", 68, Rarity.UNCOMMON, mage.cards.s.SanctumOfCalmWaters.class));
        cards.add(new SetCardInfo("Sanctum of Fruitful Harvest", 203, Rarity.UNCOMMON, mage.cards.s.SanctumOfFruitfulHarvest.class));
        cards.add(new SetCardInfo("Sanctum of Shattered Heights", 157, Rarity.UNCOMMON, mage.cards.s.SanctumOfShatteredHeights.class));
        cards.add(new SetCardInfo("Sanctum of Stone Fangs", 120, Rarity.UNCOMMON, mage.cards.s.SanctumOfStoneFangs.class));
        cards.add(new SetCardInfo("Sanctum of Tranquil Light", 33, Rarity.UNCOMMON, mage.cards.s.SanctumOfTranquilLight.class));
        cards.add(new SetCardInfo("Sanguine Indulgence", 121, Rarity.COMMON, mage.cards.s.SanguineIndulgence.class));
        cards.add(new SetCardInfo("Scavenging Ooze", 204, Rarity.RARE, mage.cards.s.ScavengingOoze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scavenging Ooze", 318, Rarity.RARE, mage.cards.s.ScavengingOoze.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scorching Dragonfire", 158, Rarity.COMMON, mage.cards.s.ScorchingDragonfire.class));
        cards.add(new SetCardInfo("Scoured Barrens", 250, Rarity.COMMON, mage.cards.s.ScouredBarrens.class));
        cards.add(new SetCardInfo("Seasoned Hallowblade", 34, Rarity.UNCOMMON, mage.cards.s.SeasonedHallowblade.class));
        cards.add(new SetCardInfo("Secure the Scene", 35, Rarity.COMMON, mage.cards.s.SecureTheScene.class));
        cards.add(new SetCardInfo("See the Truth", 352, Rarity.RARE, mage.cards.s.SeeTheTruth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("See the Truth", 69, Rarity.RARE, mage.cards.s.SeeTheTruth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Selfless Savior", 36, Rarity.UNCOMMON, mage.cards.s.SelflessSavior.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Selfless Savior", 393, Rarity.UNCOMMON, mage.cards.s.SelflessSavior.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Setessan Training", 205, Rarity.COMMON, mage.cards.s.SetessanTraining.class));
        cards.add(new SetCardInfo("Shacklegeist", 353, Rarity.RARE, mage.cards.s.Shacklegeist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shacklegeist", 70, Rarity.RARE, mage.cards.s.Shacklegeist.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Shipwreck Dowser", 71, Rarity.UNCOMMON, mage.cards.s.ShipwreckDowser.class));
        cards.add(new SetCardInfo("Shock", 159, Rarity.COMMON, mage.cards.s.Shock.class));
        cards.add(new SetCardInfo("Short Sword", 236, Rarity.COMMON, mage.cards.s.ShortSword.class));
        cards.add(new SetCardInfo("Siege Striker", 37, Rarity.UNCOMMON, mage.cards.s.SiegeStriker.class));
        cards.add(new SetCardInfo("Sigiled Contender", 323, Rarity.UNCOMMON, mage.cards.s.SigiledContender.class));
        cards.add(new SetCardInfo("Silent Dart", 237, Rarity.COMMON, mage.cards.s.SilentDart.class));
        cards.add(new SetCardInfo("Silversmote Ghoul", 122, Rarity.UNCOMMON, mage.cards.s.SilversmoteGhoul.class));
        cards.add(new SetCardInfo("Skeleton Archer", 123, Rarity.COMMON, mage.cards.s.SkeletonArcher.class));
        cards.add(new SetCardInfo("Skyscanner", 238, Rarity.COMMON, mage.cards.s.Skyscanner.class));
        cards.add(new SetCardInfo("Skyway Sniper", 206, Rarity.UNCOMMON, mage.cards.s.SkywaySniper.class));
        cards.add(new SetCardInfo("Snarespinner", 207, Rarity.COMMON, mage.cards.s.Snarespinner.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 239, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Solemn Simulacrum", 319, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Soul Sear", 160, Rarity.UNCOMMON, mage.cards.s.SoulSear.class));
        cards.add(new SetCardInfo("Sparkhunter Masticore", 240, Rarity.RARE, mage.cards.s.SparkhunterMasticore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sparkhunter Masticore", 384, Rarity.RARE, mage.cards.s.SparkhunterMasticore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Speaker of the Heavens", 347, Rarity.RARE, mage.cards.s.SpeakerOfTheHeavens.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Speaker of the Heavens", 38, Rarity.RARE, mage.cards.s.SpeakerOfTheHeavens.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spellgorger Weird", 161, Rarity.COMMON, mage.cards.s.SpellgorgerWeird.class));
        cards.add(new SetCardInfo("Spined Megalodon", 72, Rarity.COMMON, mage.cards.s.SpinedMegalodon.class));
        cards.add(new SetCardInfo("Spirit of Malevolence", 331, Rarity.COMMON, mage.cards.s.SpiritOfMalevolence.class));
        cards.add(new SetCardInfo("Sporeweb Weaver", 208, Rarity.RARE, mage.cards.s.SporewebWeaver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sporeweb Weaver", 378, Rarity.RARE, mage.cards.s.SporewebWeaver.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Staunch Shieldmate", 39, Rarity.COMMON, mage.cards.s.StaunchShieldmate.class));
        cards.add(new SetCardInfo("Storm Caller", 335, Rarity.COMMON, mage.cards.s.StormCaller.class));
        cards.add(new SetCardInfo("Stormwing Entity", 354, Rarity.RARE, mage.cards.s.StormwingEntity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stormwing Entity", 73, Rarity.RARE, mage.cards.s.StormwingEntity.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Subira, Tulzidi Caravanner", 162, Rarity.RARE, mage.cards.s.SubiraTulzidiCaravanner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Subira, Tulzidi Caravanner", 368, Rarity.RARE, mage.cards.s.SubiraTulzidiCaravanner.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sublime Epiphany", 355, Rarity.RARE, mage.cards.s.SublimeEpiphany.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sublime Epiphany", 74, Rarity.RARE, mage.cards.s.SublimeEpiphany.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sure Strike", 163, Rarity.COMMON, mage.cards.s.SureStrike.class));
        cards.add(new SetCardInfo("Swamp", 266, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 267, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 268, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 311, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swift Response", 40, Rarity.COMMON, mage.cards.s.SwiftResponse.class));
        cards.add(new SetCardInfo("Swiftwater Cliffs", 251, Rarity.COMMON, mage.cards.s.SwiftwaterCliffs.class));
        cards.add(new SetCardInfo("Tavern Swindler", 124, Rarity.UNCOMMON, mage.cards.t.TavernSwindler.class));
        cards.add(new SetCardInfo("Teferi's Ageless Insight", 294, Rarity.RARE, mage.cards.t.TeferisAgelessInsight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi's Ageless Insight", 76, Rarity.RARE, mage.cards.t.TeferisAgelessInsight.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi's Protege", 295, Rarity.COMMON, mage.cards.t.TeferisProtege.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi's Protege", 77, Rarity.COMMON, mage.cards.t.TeferisProtege.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi's Tutelage", 296, Rarity.UNCOMMON, mage.cards.t.TeferisTutelage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi's Tutelage", 78, Rarity.UNCOMMON, mage.cards.t.TeferisTutelage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi's Wavecaster", 327, Rarity.RARE, mage.cards.t.TeferisWavecaster.class));
        cards.add(new SetCardInfo("Teferi, Master of Time", 275, Rarity.MYTHIC, mage.cards.t.TeferiMasterOfTime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi, Master of Time", 276, Rarity.MYTHIC, mage.cards.t.TeferiMasterOfTime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi, Master of Time", 277, Rarity.MYTHIC, mage.cards.t.TeferiMasterOfTime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi, Master of Time", 281, Rarity.MYTHIC, mage.cards.t.TeferiMasterOfTime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi, Master of Time", 290, Rarity.MYTHIC, mage.cards.t.TeferiMasterOfTime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi, Master of Time", 291, Rarity.MYTHIC, mage.cards.t.TeferiMasterOfTime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi, Master of Time", 292, Rarity.MYTHIC, mage.cards.t.TeferiMasterOfTime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi, Master of Time", 293, Rarity.MYTHIC, mage.cards.t.TeferiMasterOfTime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi, Master of Time", 75, Rarity.MYTHIC, mage.cards.t.TeferiMasterOfTime.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Teferi, Timeless Voyager", 324, Rarity.MYTHIC, mage.cards.t.TeferiTimelessVoyager.class));
        cards.add(new SetCardInfo("Tempered Veteran", 41, Rarity.UNCOMMON, mage.cards.t.TemperedVeteran.class));
        cards.add(new SetCardInfo("Temple of Epiphany", 252, Rarity.RARE, mage.cards.t.TempleOfEpiphany.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Epiphany", 387, Rarity.RARE, mage.cards.t.TempleOfEpiphany.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Malady", 253, Rarity.RARE, mage.cards.t.TempleOfMalady.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Malady", 388, Rarity.RARE, mage.cards.t.TempleOfMalady.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Mystery", 254, Rarity.RARE, mage.cards.t.TempleOfMystery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Mystery", 389, Rarity.RARE, mage.cards.t.TempleOfMystery.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Silence", 255, Rarity.RARE, mage.cards.t.TempleOfSilence.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Silence", 390, Rarity.RARE, mage.cards.t.TempleOfSilence.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Triumph", 256, Rarity.RARE, mage.cards.t.TempleOfTriumph.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Temple of Triumph", 391, Rarity.RARE, mage.cards.t.TempleOfTriumph.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terror of the Peaks", 164, Rarity.MYTHIC, mage.cards.t.TerrorOfThePeaks.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Terror of the Peaks", 369, Rarity.MYTHIC, mage.cards.t.TerrorOfThePeaks.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thieves' Guild Enforcer", 125, Rarity.RARE, mage.cards.t.ThievesGuildEnforcer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thieves' Guild Enforcer", 361, Rarity.RARE, mage.cards.t.ThievesGuildEnforcer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Thornwood Falls", 257, Rarity.COMMON, mage.cards.t.ThornwoodFalls.class));
        cards.add(new SetCardInfo("Thrashing Brontodon", 209, Rarity.UNCOMMON, mage.cards.t.ThrashingBrontodon.class));
        cards.add(new SetCardInfo("Thrill of Possibility", 165, Rarity.COMMON, mage.cards.t.ThrillOfPossibility.class));
        cards.add(new SetCardInfo("Tide Skimmer", 79, Rarity.UNCOMMON, mage.cards.t.TideSkimmer.class));
        cards.add(new SetCardInfo("Titanic Growth", 210, Rarity.COMMON, mage.cards.t.TitanicGrowth.class));
        cards.add(new SetCardInfo("Tolarian Kraken", 80, Rarity.UNCOMMON, mage.cards.t.TolarianKraken.class));
        cards.add(new SetCardInfo("Tome Anima", 81, Rarity.COMMON, mage.cards.t.TomeAnima.class));
        cards.add(new SetCardInfo("Tormod's Crypt", 241, Rarity.UNCOMMON, mage.cards.t.TormodsCrypt.class));
        cards.add(new SetCardInfo("Track Down", 211, Rarity.COMMON, mage.cards.t.TrackDown.class));
        cards.add(new SetCardInfo("Traitorous Greed", 166, Rarity.UNCOMMON, mage.cards.t.TraitorousGreed.class));
        cards.add(new SetCardInfo("Tranquil Cove", 258, Rarity.COMMON, mage.cards.t.TranquilCove.class));
        cards.add(new SetCardInfo("Transmogrify", 167, Rarity.RARE, mage.cards.t.Transmogrify.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Transmogrify", 370, Rarity.RARE, mage.cards.t.Transmogrify.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Trufflesnout", 212, Rarity.COMMON, mage.cards.t.Trufflesnout.class));
        cards.add(new SetCardInfo("Turn to Slag", 168, Rarity.COMMON, mage.cards.t.TurnToSlag.class));
        cards.add(new SetCardInfo("Turret Ogre", 169, Rarity.COMMON, mage.cards.t.TurretOgre.class));
        cards.add(new SetCardInfo("Twinblade Assassins", 226, Rarity.UNCOMMON, mage.cards.t.TwinbladeAssassins.class));
        cards.add(new SetCardInfo("Ugin, the Spirit Dragon", 1, Rarity.MYTHIC, mage.cards.u.UginTheSpiritDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ugin, the Spirit Dragon", 279, Rarity.MYTHIC, mage.cards.u.UginTheSpiritDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ugin, the Spirit Dragon", 285, Rarity.MYTHIC, mage.cards.u.UginTheSpiritDragon.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Unleash Fury", 170, Rarity.UNCOMMON, mage.cards.u.UnleashFury.class));
        cards.add(new SetCardInfo("Unsubstantiate", 82, Rarity.UNCOMMON, mage.cards.u.Unsubstantiate.class));
        cards.add(new SetCardInfo("Valorous Steed", 42, Rarity.COMMON, mage.cards.v.ValorousSteed.class));
        cards.add(new SetCardInfo("Village Rites", 126, Rarity.COMMON, mage.cards.v.VillageRites.class));
        cards.add(new SetCardInfo("Vito, Thorn of the Dusk Rose", 127, Rarity.RARE, mage.cards.v.VitoThornOfTheDuskRose.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vito, Thorn of the Dusk Rose", 362, Rarity.RARE, mage.cards.v.VitoThornOfTheDuskRose.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vodalian Arcanist", 83, Rarity.COMMON, mage.cards.v.VodalianArcanist.class));
        cards.add(new SetCardInfo("Volcanic Geyser", 171, Rarity.UNCOMMON, mage.cards.v.VolcanicGeyser.class));
        cards.add(new SetCardInfo("Volcanic Salvo", 172, Rarity.RARE, mage.cards.v.VolcanicSalvo.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Volcanic Salvo", 371, Rarity.RARE, mage.cards.v.VolcanicSalvo.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Vryn Wingmare", 43, Rarity.UNCOMMON, mage.cards.v.VrynWingmare.class));
        cards.add(new SetCardInfo("Waker of Waves", 84, Rarity.UNCOMMON, mage.cards.w.WakerOfWaves.class));
        cards.add(new SetCardInfo("Walking Corpse", 128, Rarity.COMMON, mage.cards.w.WalkingCorpse.class));
        cards.add(new SetCardInfo("Wall of Runes", 85, Rarity.COMMON, mage.cards.w.WallOfRunes.class));
        cards.add(new SetCardInfo("Warded Battlements", 44, Rarity.COMMON, mage.cards.w.WardedBattlements.class));
        cards.add(new SetCardInfo("Warden of the Woods", 213, Rarity.UNCOMMON, mage.cards.w.WardenOfTheWoods.class));
        cards.add(new SetCardInfo("Watcher of the Spheres", 227, Rarity.UNCOMMON, mage.cards.w.WatcherOfTheSpheres.class));
        cards.add(new SetCardInfo("Wildwood Patrol", 339, Rarity.COMMON, mage.cards.w.WildwoodPatrol.class));
        cards.add(new SetCardInfo("Wildwood Scourge", 214, Rarity.UNCOMMON, mage.cards.w.WildwoodScourge.class));
        cards.add(new SetCardInfo("Wind-Scarred Crag", 259, Rarity.COMMON, mage.cards.w.WindScarredCrag.class));
        cards.add(new SetCardInfo("Wishcoin Crab", 86, Rarity.COMMON, mage.cards.w.WishcoinCrab.class));
        cards.add(new SetCardInfo("Witch's Cauldron", 129, Rarity.UNCOMMON, mage.cards.w.WitchsCauldron.class));
    }

    @Override
    protected List<CardInfo> findSpecialCardsByRarity(Rarity rarity) {
        List<CardInfo> cardInfos = super.findSpecialCardsByRarity(rarity);
        if (rarity == Rarity.LAND) {
            // Radiant Fountain is a normal common
            cardInfos.removeIf(cardInfo -> "Radiant Fountain".equals(cardInfo.getName()));
        }
        return cardInfos;
    }

    @Override
    public BoosterCollator createCollator() {
        return new CoreSet2021Collator();
    }
}

// Booster collation info from https://www.lethe.xyz/mtg/collation/m21.html
// Using USA collation for common/uncommon, rare collation inferred from other sets
class CoreSet2021Collator implements BoosterCollator {
    private final CardRun commonA = new CardRun(true, "26", "59", "153", "39", "54", "140", "2", "83", "132", "12", "72", "148", "19", "50", "134", "30", "47", "155", "11", "56", "153", "42", "46", "165", "31", "59", "168", "35", "83", "141", "15", "62", "152", "2", "55", "163", "26", "50", "140", "12", "81", "132", "39", "54", "134", "30", "72", "148", "19", "56", "168", "11", "47", "165", "42", "62", "141", "35", "46", "152", "31", "55", "155", "15", "81", "163");
    private final CardRun commonB = new CardRun(true, "195", "119", "176", "111", "178", "100", "210", "94", "207", "87", "199", "93", "205", "90", "211", "98", "196", "121", "194", "126", "306", "100", "176", "111", "178", "94", "195", "87", "207", "119", "199", "128", "210", "93", "211", "126", "205", "98", "184", "90", "196", "128", "194", "87", "178", "121", "176", "300", "195", "100", "199", "119", "207", "94", "210", "126", "205", "90", "211", "93", "196", "128", "184", "121", "194", "98");
    private final CardRun commonC1 = new CardRun(true, "113", "44", "193", "145", "235", "156", "24", "86", "20", "192", "96", "67", "238", "91", "161", "248", "212", "99", "236", "147", "14", "66", "115", "158", "231", "44", "51", "145", "113", "193", "235", "96", "24", "156", "201", "248", "67", "147", "238", "20", "192", "86", "161", "91", "236", "99", "66", "212", "14", "115", "231", "158", "237", "201", "51");
    private final CardRun commonC2 = new CardRun(true, "16", "65", "200", "123", "4", "58", "105", "189", "237", "102", "303", "40", "85", "123", "77", "169", "187", "287", "105", "65", "159", "202", "16", "58", "137", "200", "102", "77", "189", "85", "40", "169", "8", "187", "65", "4", "202", "105", "159", "58", "16", "189", "137", "123", "200", "4", "8", "187", "169", "85", "102", "295", "202", "159", "40");
    private final CardRun uncommonA = new CardRun(true, "18", "120", "41", "129", "151", "177", "25", "190", "53", "107", "229", "22", "170", "43", "89", "175", "233", "118", "206", "157", "88", "234", "33", "241", "219", "63", "182", "230", "25", "124", "166", "129", "186", "41", "57", "215", "68", "190", "229", "120", "36", "144", "89", "304", "223", "18", "218", "82", "206", "234", "177", "151", "107", "43", "219", "53", "157", "63", "36", "223", "138", "233", "22", "88", "175", "215", "120", "166", "68", "308", "124", "170", "230", "33", "129", "182", "218", "241", "144", "82", "18", "177", "118", "229", "41", "89", "190", "36", "57", "151", "53", "157", "219", "25", "234", "43", "63", "206", "138", "118", "22", "107", "166", "215", "170", "175", "223", "68", "233", "186", "57", "33", "88", "144", "218", "241", "182", "124", "82", "230");
    private final CardRun uncommonB = new CardRun(true, "227", "92", "78", "109", "209", "79", "150", "226", "198", "160", "101", "5", "97", "80", "10", "130", "84", "131", "49", "214", "220", "181", "213", "37", "149", "34", "171", "64", "154", "221", "17", "216", "298", "71", "203", "150", "61", "209", "78", "34", "217", "122", "160", "3", "226", "174", "131", "79", "227", "80", "198", "5", "92", "289", "112", "49", "181", "154", "84", "213", "101", "214", "130", "97", "64", "226", "203", "149", "220", "17", "109", "61", "171", "37", "112", "296", "217", "174", "150", "216", "71", "122", "79", "209", "3", "221", "80", "130", "198", "131", "10", "227", "92", "84", "5", "181", "160", "101", "49", "214", "154", "34", "213", "97", "37", "171", "220", "203", "221", "17", "122", "64", "3", "217", "61", "149", "71", "216", "112", "174");
    private final CardRun rare = new CardRun(false, "9", "13", "21", "23", "28", "29", "32", "38", "45", "52", "60", "69", "70", "73", "74", "76", "95", "104", "106", "110", "116", "117", "125", "127", "133", "136", "139", "142", "146", "162", "167", "172", "173", "180", "185", "188", "191", "197", "204", "208", "222", "224", "225", "232", "239", "240", "242", "246", "252", "253", "254", "255", "256", "9", "13", "21", "23", "28", "29", "32", "38", "45", "52", "60", "69", "70", "73", "74", "76", "95", "104", "106", "110", "116", "117", "125", "127", "133", "136", "139", "142", "146", "162", "167", "172", "173", "180", "185", "188", "191", "197", "204", "208", "222", "224", "225", "232", "239", "240", "242", "246", "252", "253", "254", "255", "256", "1", "6", "7", "27", "48", "75", "103", "108", "114", "135", "143", "164", "179", "183", "228");
    private final CardRun land = new CardRun(true, "247", "269", "245", "273", "259", "261", "251", "265", "257", "244", "266", "249", "270", "250", "274", "258", "309", "243", "263", "259", "268", "313", "247", "271", "245", "272", "251", "311", "259", "262", "257", "244", "264", "258", "249", "243", "260", "247", "267", "251", "310", "250", "269", "273", "258", "261", "244", "265", "245", "266", "259", "270", "257", "249", "274", "247", "309", "250", "263", "251", "268", "243", "313", "245", "271", "272", "258", "311", "244", "262", "259", "264", "249", "312", "257", "247", "260", "245", "267", "250", "310", "312", "269", "243", "273", "251", "261", "243", "265", "244", "266", "249", "270", "250", "274", "258", "309", "263", "247", "268", "245", "313", "251", "271", "259", "272", "257", "244", "311", "250", "262", "249", "264", "243", "312", "258", "260", "257", "267", "310");

    private final BoosterStructure AABBC1C1C1C1C1C1 = new BoosterStructure(
            commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAABBC1C1C1C1C1 = new BoosterStructure(
            commonA, commonA, commonA,
            commonB, commonB,
            commonC1, commonC1, commonC1, commonC1, commonC1
    );
    private final BoosterStructure AAAABBC2C2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB,
            commonC2, commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBC2C2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB,
            commonC2, commonC2, commonC2
    );
    private final BoosterStructure AAAABBBBC2C2 = new BoosterStructure(
            commonA, commonA, commonA, commonA,
            commonB, commonB, commonB, commonB,
            commonC2, commonC2
    );
    private final BoosterStructure AAB = new BoosterStructure(uncommonA, uncommonA, uncommonB);
    private final BoosterStructure ABB = new BoosterStructure(uncommonA, uncommonB, uncommonB);
    private final BoosterStructure R1 = new BoosterStructure(rare);
    private final BoosterStructure L1 = new BoosterStructure(land);

    // In order for equal numbers of each common to exist, the average booster must contain:
    // 3.27 A commons (36 / 11)
    // 2.18 B commons (24 / 11)
    // 2.73 C1 commons (30 / 11, or 60 / 11 in each C1 booster)
    // 1.82 C2 commons (20 / 11, or 40 / 11 in each C2 booster)
    // These numbers are the same for all sets with 101 commons in A/B/C1/C2 print runs
    // and with 10 common slots per booster
    private final RarityConfiguration commonRuns = new RarityConfiguration(
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AABBC1C1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,
            AAABBC1C1C1C1C1,

            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBC2C2C2C2,
            AAAABBBC2C2C2,
            AAAABBBC2C2C2,
            AAAABBBBC2C2
    );
    private final RarityConfiguration uncommonRuns = new RarityConfiguration(AAB, ABB);
    private final RarityConfiguration rareRuns = new RarityConfiguration(R1);
    private final RarityConfiguration landRuns = new RarityConfiguration(L1);

    @Override
    public List<String> makeBooster() {
        List<String> booster = new ArrayList<>();
        booster.addAll(commonRuns.getNext().makeRun());
        booster.addAll(uncommonRuns.getNext().makeRun());
        booster.addAll(rareRuns.getNext().makeRun());
        booster.addAll(landRuns.getNext().makeRun());
        return booster;
    }
}
