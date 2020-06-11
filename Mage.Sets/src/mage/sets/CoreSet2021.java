package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardInfo;
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

    private final List<CardInfo> savedSpecialLand = new ArrayList<>();

    private CoreSet2021() {
        super("Core Set 2021", "M21", ExpansionSet.buildDate(2020, 7, 3), SetType.CORE);
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterSpecial = 0;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.maxCardNumberInBooster = 274;

        cards.add(new SetCardInfo("Adherent of Hope", 321, Rarity.COMMON, mage.cards.a.AdherentOfHope.class));
        cards.add(new SetCardInfo("Alpine Houndmaster", 215, Rarity.UNCOMMON, mage.cards.a.AlpineHoundmaster.class));
        cards.add(new SetCardInfo("Alpine Watchdog", 2, Rarity.COMMON, mage.cards.a.AlpineWatchdog.class));
        cards.add(new SetCardInfo("Angelic Ascension", 3, Rarity.UNCOMMON, mage.cards.a.AngelicAscension.class));
        cards.add(new SetCardInfo("Azusa, Lost but Seeking", 173, Rarity.RARE, mage.cards.a.AzusaLostButSeeking.class));
        cards.add(new SetCardInfo("Bad Deal", 89, Rarity.UNCOMMON, mage.cards.b.BadDeal.class));
        cards.add(new SetCardInfo("Baneslayer Angel", 6, Rarity.MYTHIC, mage.cards.b.BaneslayerAngel.class));
        cards.add(new SetCardInfo("Basri's Acolyte", 8, Rarity.COMMON, mage.cards.b.BasrisAcolyte.class));
        cards.add(new SetCardInfo("Basri's Aegis", 322, Rarity.RARE, mage.cards.b.BasrisAegis.class));
        cards.add(new SetCardInfo("Basri's Solidarity", 10, Rarity.UNCOMMON, mage.cards.b.BasrisSolidarity.class));
        cards.add(new SetCardInfo("Basri, Devoted Paladin", 320, Rarity.MYTHIC, mage.cards.b.BasriDevotedPaladin.class));
        cards.add(new SetCardInfo("Bolt Hound", 131, Rarity.UNCOMMON, mage.cards.b.BoltHound.class));
        cards.add(new SetCardInfo("Carrion Grub", 92, Rarity.UNCOMMON, mage.cards.c.CarrionGrub.class));
        cards.add(new SetCardInfo("Chandra's Firemaw", 333, Rarity.RARE, mage.cards.c.ChandrasFiremaw.class));
        cards.add(new SetCardInfo("Chandra's Incinerator", 136, Rarity.RARE, mage.cards.c.ChandrasIncinerator.class));
        cards.add(new SetCardInfo("Chandra's Magmutt", 137, Rarity.COMMON, mage.cards.c.ChandrasMagmutt.class));
        cards.add(new SetCardInfo("Chandra, Flame's Catalyst", 332, Rarity.MYTHIC, mage.cards.c.ChandraFlamesCatalyst.class));
        cards.add(new SetCardInfo("Chromatic Orrery", 228, Rarity.MYTHIC, mage.cards.c.ChromaticOrrery.class));
        cards.add(new SetCardInfo("Containment Priest", 314, Rarity.RARE, mage.cards.c.ContainmentPriest.class));
        cards.add(new SetCardInfo("Cultivate", 177, Rarity.UNCOMMON, mage.cards.c.Cultivate.class));
        cards.add(new SetCardInfo("Double Vision", 142, Rarity.RARE, mage.cards.d.DoubleVision.class));
        cards.add(new SetCardInfo("Elder Gargaroth", 179, Rarity.MYTHIC, mage.cards.e.ElderGargaroth.class));
        cards.add(new SetCardInfo("Eliminate", 97, Rarity.UNCOMMON, mage.cards.e.Eliminate.class));
        cards.add(new SetCardInfo("Fabled Passage", 246, Rarity.RARE, mage.cards.f.FabledPassage.class));
        cards.add(new SetCardInfo("Faith's Fetters", 17, Rarity.UNCOMMON, mage.cards.f.FaithsFetters.class));
        cards.add(new SetCardInfo("Fierce Empath", 181, Rarity.UNCOMMON, mage.cards.f.FierceEmpath.class));
        cards.add(new SetCardInfo("Fungal Rebirth", 182, Rarity.UNCOMMON, mage.cards.f.FungalRebirth.class));
        cards.add(new SetCardInfo("Furious Rise", 144, Rarity.UNCOMMON, mage.cards.f.FuriousRise.class));
        cards.add(new SetCardInfo("Gadrak, the Crown-Scourge", 146, Rarity.RARE, mage.cards.g.GadrakTheCrownScourge.class));
        cards.add(new SetCardInfo("Garruk's Warsteed", 337, Rarity.RARE, mage.cards.g.GarruksWarsteed.class));
        cards.add(new SetCardInfo("Garruk, Savage Herald", 336, Rarity.MYTHIC, mage.cards.g.GarrukSavageHerald.class));
        cards.add(new SetCardInfo("Goblin Arsonist", 147, Rarity.COMMON, mage.cards.g.GoblinArsonist.class));
        cards.add(new SetCardInfo("Grasp of Darkness", 102, Rarity.COMMON, mage.cards.g.GraspOfDarkness.class));
        cards.add(new SetCardInfo("Grim Tutor", 103, Rarity.MYTHIC, mage.cards.g.GrimTutor.class));
        cards.add(new SetCardInfo("Havoc Jester", 149, Rarity.UNCOMMON, mage.cards.h.HavocJester.class));
        cards.add(new SetCardInfo("Heartfire Immolator", 150, Rarity.UNCOMMON, mage.cards.h.HeartfireImmolator.class));
        cards.add(new SetCardInfo("Hellkite Punisher", 151, Rarity.UNCOMMON, mage.cards.h.HellkitePunisher.class));
        cards.add(new SetCardInfo("Heroic Intervention", 188, Rarity.RARE, mage.cards.h.HeroicIntervention.class));
        cards.add(new SetCardInfo("Historian of Zhalfir", 325, Rarity.UNCOMMON, mage.cards.h.HistorianOfZhalfir.class));
        cards.add(new SetCardInfo("Igneous Cur", 153, Rarity.COMMON, mage.cards.i.IgneousCur.class));
        cards.add(new SetCardInfo("Indulging Patrician", 219, Rarity.UNCOMMON, mage.cards.i.IndulgingPatrician.class));
        cards.add(new SetCardInfo("Island", 310, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jeskai Elder", 53, Rarity.UNCOMMON, mage.cards.j.JeskaiElder.class));
        cards.add(new SetCardInfo("Jolrael, Mwonvuli Recluse", 191, Rarity.RARE, mage.cards.j.JolraelMwonvuliRecluse.class));
        cards.add(new SetCardInfo("Keral Keep Disciples", 334, Rarity.UNCOMMON, mage.cards.k.KeralKeepDisciples.class));
        cards.add(new SetCardInfo("Liliana's Scorn", 329, Rarity.RARE, mage.cards.l.LilianasScorn.class));
        cards.add(new SetCardInfo("Liliana's Scrounger", 330, Rarity.UNCOMMON, mage.cards.l.LilianasScrounger.class));
        cards.add(new SetCardInfo("Liliana, Death Mage", 328, Rarity.MYTHIC, mage.cards.l.LilianaDeathMage.class));
        cards.add(new SetCardInfo("Liliana, Waker of the Dead", 108, Rarity.MYTHIC, mage.cards.l.LilianaWakerOfTheDead.class));
        cards.add(new SetCardInfo("Llanowar Visionary", 193, Rarity.COMMON, mage.cards.l.LlanowarVisionary.class));
        cards.add(new SetCardInfo("Lorescale Coatl", 221, Rarity.UNCOMMON, mage.cards.l.LorescaleCoatl.class));
        cards.add(new SetCardInfo("Malefic Scythe", 112, Rarity.UNCOMMON, mage.cards.m.MaleficScythe.class));
        cards.add(new SetCardInfo("Mangara, the Diplomat", 27, Rarity.MYTHIC, mage.cards.m.MangaraTheDiplomat.class));
        cards.add(new SetCardInfo("Massacre Wurm", 114, Rarity.MYTHIC, mage.cards.m.MassacreWurm.class));
        cards.add(new SetCardInfo("Mazemind Tome", 232, Rarity.RARE, mage.cards.m.MazemindTome.class));
        cards.add(new SetCardInfo("Meteorite", 233, Rarity.UNCOMMON, mage.cards.m.Meteorite.class));
        cards.add(new SetCardInfo("Mountain", 312, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mystic Skyfish", 326, Rarity.COMMON, mage.cards.m.MysticSkyfish.class));
        cards.add(new SetCardInfo("Necromentia", 116, Rarity.RARE, mage.cards.n.Necromentia.class));
        cards.add(new SetCardInfo("Pack Leader", 29, Rarity.RARE, mage.cards.p.PackLeader.class));
        cards.add(new SetCardInfo("Peer into the Abyss", 117, Rarity.RARE, mage.cards.p.PeerIntoTheAbyss.class));
        cards.add(new SetCardInfo("Pestilent Haze", 118, Rarity.UNCOMMON, mage.cards.p.PestilentHaze.class));
        cards.add(new SetCardInfo("Plains", 309, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Predatory Wurm", 338, Rarity.UNCOMMON, mage.cards.p.PredatoryWurm.class));
        cards.add(new SetCardInfo("Pridemalkin", 196, Rarity.COMMON, mage.cards.p.Pridemalkin.class));
        cards.add(new SetCardInfo("Pursued Whale", 60, Rarity.RARE, mage.cards.p.PursuedWhale.class));
        cards.add(new SetCardInfo("Quirion Dryad", 198, Rarity.UNCOMMON, mage.cards.q.QuirionDryad.class));
        cards.add(new SetCardInfo("Radiant Fountain", 248, Rarity.COMMON, mage.cards.r.RadiantFountain.class));
        cards.add(new SetCardInfo("Rain of Revelation", 61, Rarity.UNCOMMON, mage.cards.r.RainOfRevelation.class));
        cards.add(new SetCardInfo("Revitalize", 31, Rarity.COMMON, mage.cards.r.Revitalize.class));
        cards.add(new SetCardInfo("Rewind", 63, Rarity.UNCOMMON, mage.cards.r.Rewind.class));
        cards.add(new SetCardInfo("Riddleform", 64, Rarity.UNCOMMON, mage.cards.r.Riddleform.class));
        cards.add(new SetCardInfo("Rin and Seri, Inseparable", 278, Rarity.MYTHIC, mage.cards.r.RinAndSeriInseparable.class));
        cards.add(new SetCardInfo("Runed Halo", 32, Rarity.RARE, mage.cards.r.RunedHalo.class));
        cards.add(new SetCardInfo("Seasoned Hallowblade", 34, Rarity.UNCOMMON, mage.cards.s.SeasonedHallowblade.class));
        cards.add(new SetCardInfo("See the Truth", 69, Rarity.RARE, mage.cards.s.SeeTheTruth.class));
        cards.add(new SetCardInfo("Selfless Savior", 36, Rarity.UNCOMMON, mage.cards.s.SelflessSavior.class));
        cards.add(new SetCardInfo("Shipwreck Dowser", 71, Rarity.UNCOMMON, mage.cards.s.ShipwreckDowser.class));
        cards.add(new SetCardInfo("Sigiled Contender", 323, Rarity.UNCOMMON, mage.cards.s.SigiledContender.class));
        cards.add(new SetCardInfo("Solemn Simulacrum", 239, Rarity.RARE, mage.cards.s.SolemnSimulacrum.class));
        cards.add(new SetCardInfo("Soul Sear", 160, Rarity.UNCOMMON, mage.cards.s.SoulSear.class));
        cards.add(new SetCardInfo("Sparkhunter Masticore", 240, Rarity.RARE, mage.cards.s.SparkhunterMasticore.class));
        cards.add(new SetCardInfo("Spined Megalodon", 72, Rarity.COMMON, mage.cards.s.SpinedMegalodon.class));
        cards.add(new SetCardInfo("Spirit of Malevolence", 331, Rarity.COMMON, mage.cards.s.SpiritOfMalevolence.class));
        cards.add(new SetCardInfo("Storm Caller", 335, Rarity.COMMON, mage.cards.s.StormCaller.class));
        cards.add(new SetCardInfo("Teferi's Ageless Insight", 76, Rarity.RARE, mage.cards.t.TeferisAgelessInsight.class));
        cards.add(new SetCardInfo("Teferi's Protege", 77, Rarity.COMMON, mage.cards.t.TeferisProtege.class));
        cards.add(new SetCardInfo("Teferi's Tutelage", 78, Rarity.UNCOMMON, mage.cards.t.TeferisTutelage.class));
        cards.add(new SetCardInfo("Teferi's Wavecaster", 327, Rarity.RARE, mage.cards.t.TeferisWavecaster.class));
        cards.add(new SetCardInfo("Teferi, Master of Time", 75, Rarity.MYTHIC, mage.cards.t.TeferiMasterOfTime.class));
        cards.add(new SetCardInfo("Temple of Epiphany", 252, Rarity.RARE, mage.cards.t.TempleOfEpiphany.class));
        cards.add(new SetCardInfo("Temple of Malady", 253, Rarity.RARE, mage.cards.t.TempleOfMalady.class));
        cards.add(new SetCardInfo("Temple of Mystery", 254, Rarity.RARE, mage.cards.t.TempleOfMystery.class));
        cards.add(new SetCardInfo("Temple of Silence", 255, Rarity.RARE, mage.cards.t.TempleOfSilence.class));
        cards.add(new SetCardInfo("Temple of Triumph", 256, Rarity.RARE, mage.cards.t.TempleOfTriumph.class));
        cards.add(new SetCardInfo("Tide Skimmer", 79, Rarity.UNCOMMON, mage.cards.t.TideSkimmer.class));
        cards.add(new SetCardInfo("Tormod's Crypt", 241, Rarity.UNCOMMON, mage.cards.t.TormodsCrypt.class));
        cards.add(new SetCardInfo("Ugin, the Spirit Dragon", 1, Rarity.MYTHIC, mage.cards.u.UginTheSpiritDragon.class));
        cards.add(new SetCardInfo("Valorous Steed", 42, Rarity.COMMON, mage.cards.v.ValorousSteed.class));
        cards.add(new SetCardInfo("Village Rites", 126, Rarity.COMMON, mage.cards.v.VillageRites.class));
        cards.add(new SetCardInfo("Vito, Thorn of the Dusk Rose", 127, Rarity.RARE, mage.cards.v.VitoThornOfTheDuskRose.class));
        cards.add(new SetCardInfo("Warden of the Woods", 213, Rarity.UNCOMMON, mage.cards.w.WardenOfTheWoods.class));
        cards.add(new SetCardInfo("Wildwood Patrol", 339, Rarity.COMMON, mage.cards.w.WildwoodPatrol.class));
    }
}
