package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pakh
 */
public class AmonkhetPromos extends ExpansionSet {

    private static final AmonkhetPromos instance = new AmonkhetPromos();

    public static AmonkhetPromos getInstance() {
        return instance;
    }

    private AmonkhetPromos() {
        super("Amonkhet Promos", "PAKH", ExpansionSet.buildDate(2017, 4, 28), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Angel of Sanctions", "1s", Rarity.MYTHIC, mage.cards.a.AngelOfSanctions.class));
        cards.add(new SetCardInfo("Anointed Procession", "2s", Rarity.RARE, mage.cards.a.AnointedProcession.class));
        cards.add(new SetCardInfo("Approach of the Second Sun", "4s", Rarity.RARE, mage.cards.a.ApproachOfTheSecondSun.class));
        cards.add(new SetCardInfo("Archfiend of Ifnir", "78s", Rarity.RARE, mage.cards.a.ArchfiendOfIfnir.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Archfiend of Ifnir", 78, Rarity.RARE, mage.cards.a.ArchfiendOfIfnir.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("As Foretold", "42s", Rarity.MYTHIC, mage.cards.a.AsForetold.class));
        cards.add(new SetCardInfo("Aven Mindcensor", "5p", Rarity.RARE, mage.cards.a.AvenMindcensor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aven Mindcensor", "5s", Rarity.RARE, mage.cards.a.AvenMindcensor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bontu the Glorified", "82s", Rarity.MYTHIC, mage.cards.b.BontuTheGlorified.class));
        cards.add(new SetCardInfo("Bounty of the Luxa", "196s", Rarity.RARE, mage.cards.b.BountyOfTheLuxa.class));
        cards.add(new SetCardInfo("Canyon Slough", "239s", Rarity.RARE, mage.cards.c.CanyonSlough.class));
        cards.add(new SetCardInfo("Cascading Cataracts", "240s", Rarity.RARE, mage.cards.c.CascadingCataracts.class));
        cards.add(new SetCardInfo("Champion of Rhonas", "159s", Rarity.RARE, mage.cards.c.ChampionOfRhonas.class));
        cards.add(new SetCardInfo("Channeler Initiate", "160s", Rarity.RARE, mage.cards.c.ChannelerInitiate.class));
        cards.add(new SetCardInfo("Combat Celebrant", "125s", Rarity.MYTHIC, mage.cards.c.CombatCelebrant.class));
        cards.add(new SetCardInfo("Commit // Memory", "211p", Rarity.RARE, mage.cards.c.CommitMemory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Commit // Memory", "211s", Rarity.RARE, mage.cards.c.CommitMemory.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Cruel Reality", "84s", Rarity.MYTHIC, mage.cards.c.CruelReality.class));
        cards.add(new SetCardInfo("Curator of Mysteries", "49s", Rarity.RARE, mage.cards.c.CuratorOfMysteries.class));
        cards.add(new SetCardInfo("Cut // Ribbons", "223s", Rarity.RARE, mage.cards.c.CutRibbons.class));
        cards.add(new SetCardInfo("Dispossess", "86s", Rarity.RARE, mage.cards.d.Dispossess.class));
        cards.add(new SetCardInfo("Drake Haven", "51s", Rarity.RARE, mage.cards.d.DrakeHaven.class));
        cards.add(new SetCardInfo("Dread Wanderer", "88s", Rarity.RARE, mage.cards.d.DreadWanderer.class));
        cards.add(new SetCardInfo("Dusk // Dawn", "210s", Rarity.RARE, mage.cards.d.DuskDawn.class));
        cards.add(new SetCardInfo("Failure // Comply", "221s", Rarity.RARE, mage.cards.f.FailureComply.class));
        cards.add(new SetCardInfo("Fetid Pools", "243s", Rarity.RARE, mage.cards.f.FetidPools.class));
        cards.add(new SetCardInfo("Gideon of the Trials", "14s", Rarity.MYTHIC, mage.cards.g.GideonOfTheTrials.class));
        cards.add(new SetCardInfo("Gideon's Intervention", "15s", Rarity.RARE, mage.cards.g.GideonsIntervention.class));
        cards.add(new SetCardInfo("Glorious End", "133s", Rarity.MYTHIC, mage.cards.g.GloriousEnd.class));
        cards.add(new SetCardInfo("Glory-Bound Initiate", "16s", Rarity.RARE, mage.cards.g.GloryBoundInitiate.class));
        cards.add(new SetCardInfo("Glorybringer", "134s", Rarity.RARE, mage.cards.g.Glorybringer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glorybringer", 134, Rarity.RARE, mage.cards.g.Glorybringer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Glyph Keeper", "55s", Rarity.RARE, mage.cards.g.GlyphKeeper.class));
        cards.add(new SetCardInfo("Hapatra, Vizier of Poisons", "199s", Rarity.RARE, mage.cards.h.HapatraVizierOfPoisons.class));
        cards.add(new SetCardInfo("Harsh Mentor", "135s", Rarity.RARE, mage.cards.h.HarshMentor.class));
        cards.add(new SetCardInfo("Harvest Season", "170p", Rarity.RARE, mage.cards.h.HarvestSeason.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Harvest Season", "170s", Rarity.RARE, mage.cards.h.HarvestSeason.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Hazoret the Fervent", "136s", Rarity.MYTHIC, mage.cards.h.HazoretTheFervent.class));
        cards.add(new SetCardInfo("Hazoret's Favor", "137s", Rarity.RARE, mage.cards.h.HazoretsFavor.class));
        cards.add(new SetCardInfo("Heart-Piercer Manticore", "138s", Rarity.RARE, mage.cards.h.HeartPiercerManticore.class));
        cards.add(new SetCardInfo("Heaven // Earth", "224s", Rarity.RARE, mage.cards.h.HeavenEarth.class));
        cards.add(new SetCardInfo("Honored Hydra", "172s", Rarity.RARE, mage.cards.h.HonoredHydra.class));
        cards.add(new SetCardInfo("Insult // Injury", "213s", Rarity.RARE, mage.cards.i.InsultInjury.class));
        cards.add(new SetCardInfo("Irrigated Farmland", "245s", Rarity.RARE, mage.cards.i.IrrigatedFarmland.class));
        cards.add(new SetCardInfo("Kefnet the Mindful", "59s", Rarity.MYTHIC, mage.cards.k.KefnetTheMindful.class));
        cards.add(new SetCardInfo("Liliana's Mastery", "98s", Rarity.RARE, mage.cards.l.LilianasMastery.class));
        cards.add(new SetCardInfo("Liliana, Death's Majesty", "97s", Rarity.MYTHIC, mage.cards.l.LilianaDeathsMajesty.class));
        cards.add(new SetCardInfo("Mouth // Feed", "214s", Rarity.RARE, mage.cards.m.MouthFeed.class));
        cards.add(new SetCardInfo("Neheb, the Worthy", "203s", Rarity.RARE, mage.cards.n.NehebTheWorthy.class));
        cards.add(new SetCardInfo("Never // Return", "212s", Rarity.RARE, mage.cards.n.NeverReturn.class));
        cards.add(new SetCardInfo("New Perspectives", "63s", Rarity.RARE, mage.cards.n.NewPerspectives.class));
        cards.add(new SetCardInfo("Nissa, Steward of Elements", "204s", Rarity.MYTHIC, mage.cards.n.NissaStewardOfElements.class));
        cards.add(new SetCardInfo("Oketra the True", "21s", Rarity.MYTHIC, mage.cards.o.OketraTheTrue.class));
        cards.add(new SetCardInfo("Oracle's Vault", "234s", Rarity.RARE, mage.cards.o.OraclesVault.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oracle's Vault", 234, Rarity.RARE, mage.cards.o.OraclesVault.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plague Belcher", "104s", Rarity.RARE, mage.cards.p.PlagueBelcher.class));
        cards.add(new SetCardInfo("Prepare // Fight", "220s", Rarity.RARE, mage.cards.p.PrepareFight.class));
        cards.add(new SetCardInfo("Prowling Serpopard", "180s", Rarity.RARE, mage.cards.p.ProwlingSerpopard.class));
        cards.add(new SetCardInfo("Pull from Tomorrow", "65p", Rarity.RARE, mage.cards.p.PullFromTomorrow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pull from Tomorrow", "65s", Rarity.RARE, mage.cards.p.PullFromTomorrow.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyramid of the Pantheon", "235s", Rarity.RARE, mage.cards.p.PyramidOfThePantheon.class));
        cards.add(new SetCardInfo("Rags // Riches", "222s", Rarity.RARE, mage.cards.r.RagsRiches.class));
        cards.add(new SetCardInfo("Regal Caracal", "24s", Rarity.RARE, mage.cards.r.RegalCaracal.class));
        cards.add(new SetCardInfo("Rhonas the Indomitable", "182s", Rarity.MYTHIC, mage.cards.r.RhonasTheIndomitable.class));
        cards.add(new SetCardInfo("Samut, Voice of Dissent", "205s", Rarity.MYTHIC, mage.cards.s.SamutVoiceOfDissent.class));
        cards.add(new SetCardInfo("Sandwurm Convergence", "183s", Rarity.RARE, mage.cards.s.SandwurmConvergence.class));
        cards.add(new SetCardInfo("Scattered Groves", "247s", Rarity.RARE, mage.cards.s.ScatteredGroves.class));
        cards.add(new SetCardInfo("Shadow of the Grave", "107s", Rarity.RARE, mage.cards.s.ShadowOfTheGrave.class));
        cards.add(new SetCardInfo("Sheltered Thicket", "248s", Rarity.RARE, mage.cards.s.ShelteredThicket.class));
        cards.add(new SetCardInfo("Soul-Scar Mage", "148s", Rarity.RARE, mage.cards.s.SoulScarMage.class));
        cards.add(new SetCardInfo("Sweltering Suns", "149s", Rarity.RARE, mage.cards.s.SwelteringSuns.class));
        cards.add(new SetCardInfo("Temmet, Vizier of Naktamun", "207s", Rarity.RARE, mage.cards.t.TemmetVizierOfNaktamun.class));
        cards.add(new SetCardInfo("Throne of the God-Pharaoh", "237s", Rarity.RARE, mage.cards.t.ThroneOfTheGodPharaoh.class));
        cards.add(new SetCardInfo("Trueheart Duelist", 35, Rarity.UNCOMMON, mage.cards.t.TrueheartDuelist.class));
        cards.add(new SetCardInfo("Vizier of Many Faces", "74s", Rarity.RARE, mage.cards.v.VizierOfManyFaces.class));
        cards.add(new SetCardInfo("Vizier of the Menagerie", "192s", Rarity.MYTHIC, mage.cards.v.VizierOfTheMenagerie.class));
    }
}
