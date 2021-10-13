package mage.sets;

import mage.cards.Card;
import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetType;
import mage.util.RandomUtil;

import java.util.List;

/**
 * @author LevelX2
 */
public final class DragonsMaze extends ExpansionSet {

    private static final DragonsMaze instance = new DragonsMaze();

    public static DragonsMaze getInstance() {
        return instance;
    }

    private DragonsMaze() {
        super("Dragon's Maze", "DGM", ExpansionSet.buildDate(2013, 5, 3), SetType.EXPANSION);
        this.blockName = "Return to Ravnica";
        this.hasBoosters = true;
        this.numBoosterSpecial = 1;
        this.numBoosterLands = 0;
        this.numBoosterCommon = 10;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.parentSet = ReturnToRavnica.getInstance();
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Advent of the Wurm", 51, Rarity.RARE, mage.cards.a.AdventOfTheWurm.class));
        cards.add(new SetCardInfo("Aetherling", 11, Rarity.RARE, mage.cards.a.Aetherling.class));
        cards.add(new SetCardInfo("Alive // Well", 121, Rarity.UNCOMMON, mage.cards.a.AliveWell.class));
        cards.add(new SetCardInfo("Armed // Dangerous", 122, Rarity.UNCOMMON, mage.cards.a.ArmedDangerous.class));
        cards.add(new SetCardInfo("Armored Wolf-Rider", 52, Rarity.COMMON, mage.cards.a.ArmoredWolfRider.class));
        cards.add(new SetCardInfo("Ascended Lawmage", 53, Rarity.UNCOMMON, mage.cards.a.AscendedLawmage.class));
        cards.add(new SetCardInfo("Awe for the Guilds", 31, Rarity.COMMON, mage.cards.a.AweForTheGuilds.class));
        cards.add(new SetCardInfo("Azorius Cluestone", 136, Rarity.COMMON, mage.cards.a.AzoriusCluestone.class));
        cards.add(new SetCardInfo("Azorius Guildgate", 146, Rarity.COMMON, mage.cards.a.AzoriusGuildgate.class));
        cards.add(new SetCardInfo("Bane Alley Blackguard", 21, Rarity.COMMON, mage.cards.b.BaneAlleyBlackguard.class));
        cards.add(new SetCardInfo("Battering Krasis", 41, Rarity.COMMON, mage.cards.b.BatteringKrasis.class));
        cards.add(new SetCardInfo("Beck // Call", 123, Rarity.RARE, mage.cards.b.BeckCall.class));
        cards.add(new SetCardInfo("Beetleform Mage", 54, Rarity.COMMON, mage.cards.b.BeetleformMage.class));
        cards.add(new SetCardInfo("Blast of Genius", 55, Rarity.UNCOMMON, mage.cards.b.BlastOfGenius.class));
        cards.add(new SetCardInfo("Blaze Commando", 56, Rarity.UNCOMMON, mage.cards.b.BlazeCommando.class));
        cards.add(new SetCardInfo("Blood Baron of Vizkopa", 57, Rarity.MYTHIC, mage.cards.b.BloodBaronOfVizkopa.class));
        cards.add(new SetCardInfo("Blood Scrivener", 22, Rarity.RARE, mage.cards.b.BloodScrivener.class));
        cards.add(new SetCardInfo("Boros Battleshaper", 58, Rarity.RARE, mage.cards.b.BorosBattleshaper.class));
        cards.add(new SetCardInfo("Boros Cluestone", 137, Rarity.COMMON, mage.cards.b.BorosCluestone.class));
        cards.add(new SetCardInfo("Boros Guildgate", 147, Rarity.COMMON, mage.cards.b.BorosGuildgate.class));
        cards.add(new SetCardInfo("Boros Mastiff", 1, Rarity.COMMON, mage.cards.b.BorosMastiff.class));
        cards.add(new SetCardInfo("Breaking // Entering", 124, Rarity.RARE, mage.cards.b.BreakingEntering.class));
        cards.add(new SetCardInfo("Bred for the Hunt", 59, Rarity.UNCOMMON, mage.cards.b.BredForTheHunt.class));
        cards.add(new SetCardInfo("Bronzebeak Moa", 60, Rarity.UNCOMMON, mage.cards.b.BronzebeakMoa.class));
        cards.add(new SetCardInfo("Carnage Gladiator", 61, Rarity.UNCOMMON, mage.cards.c.CarnageGladiator.class));
        cards.add(new SetCardInfo("Catch // Release", 125, Rarity.RARE, mage.cards.c.CatchRelease.class));
        cards.add(new SetCardInfo("Clear a Path", 32, Rarity.COMMON, mage.cards.c.ClearAPath.class));
        cards.add(new SetCardInfo("Council of the Absolute", 62, Rarity.MYTHIC, mage.cards.c.CouncilOfTheAbsolute.class));
        cards.add(new SetCardInfo("Crypt Incursion", 23, Rarity.COMMON, mage.cards.c.CryptIncursion.class));
        cards.add(new SetCardInfo("Deadbridge Chant", 63, Rarity.MYTHIC, mage.cards.d.DeadbridgeChant.class));
        cards.add(new SetCardInfo("Debt to the Deathless", 64, Rarity.UNCOMMON, mage.cards.d.DebtToTheDeathless.class));
        cards.add(new SetCardInfo("Deputy of Acquittals", 65, Rarity.COMMON, mage.cards.d.DeputyOfAcquittals.class));
        cards.add(new SetCardInfo("Dimir Cluestone", 138, Rarity.COMMON, mage.cards.d.DimirCluestone.class));
        cards.add(new SetCardInfo("Dimir Guildgate", 148, Rarity.COMMON, mage.cards.d.DimirGuildgate.class));
        cards.add(new SetCardInfo("Down // Dirty", 126, Rarity.UNCOMMON, mage.cards.d.DownDirty.class));
        cards.add(new SetCardInfo("Dragonshift", 66, Rarity.RARE, mage.cards.d.Dragonshift.class));
        cards.add(new SetCardInfo("Drown in Filth", 67, Rarity.COMMON, mage.cards.d.DrownInFilth.class));
        cards.add(new SetCardInfo("Emmara Tandris", 68, Rarity.RARE, mage.cards.e.EmmaraTandris.class));
        cards.add(new SetCardInfo("Exava, Rakdos Blood Witch", 69, Rarity.RARE, mage.cards.e.ExavaRakdosBloodWitch.class));
        cards.add(new SetCardInfo("Far // Away", 127, Rarity.UNCOMMON, mage.cards.f.FarAway.class));
        cards.add(new SetCardInfo("Fatal Fumes", 24, Rarity.COMMON, mage.cards.f.FatalFumes.class));
        cards.add(new SetCardInfo("Feral Animist", 70, Rarity.UNCOMMON, mage.cards.f.FeralAnimist.class));
        cards.add(new SetCardInfo("Flesh // Blood", 128, Rarity.RARE, mage.cards.f.FleshBlood.class));
        cards.add(new SetCardInfo("Fluxcharger", 71, Rarity.UNCOMMON, mage.cards.f.Fluxcharger.class));
        cards.add(new SetCardInfo("Gaze of Granite", 72, Rarity.RARE, mage.cards.g.GazeOfGranite.class));
        cards.add(new SetCardInfo("Give // Take", 129, Rarity.UNCOMMON, mage.cards.g.GiveTake.class));
        cards.add(new SetCardInfo("Gleam of Battle", 73, Rarity.UNCOMMON, mage.cards.g.GleamOfBattle.class));
        cards.add(new SetCardInfo("Goblin Test Pilot", 74, Rarity.UNCOMMON, mage.cards.g.GoblinTestPilot.class));
        cards.add(new SetCardInfo("Golgari Cluestone", 139, Rarity.COMMON, mage.cards.g.GolgariCluestone.class));
        cards.add(new SetCardInfo("Golgari Guildgate", 149, Rarity.COMMON, mage.cards.g.GolgariGuildgate.class));
        cards.add(new SetCardInfo("Gruul Cluestone", 140, Rarity.COMMON, mage.cards.g.GruulCluestone.class));
        cards.add(new SetCardInfo("Gruul Guildgate", 150, Rarity.COMMON, mage.cards.g.GruulGuildgate.class));
        cards.add(new SetCardInfo("Gruul War Chant", 75, Rarity.UNCOMMON, mage.cards.g.GruulWarChant.class));
        cards.add(new SetCardInfo("Haazda Snare Squad", 2, Rarity.COMMON, mage.cards.h.HaazdaSnareSquad.class));
        cards.add(new SetCardInfo("Haunter of Nightveil", 76, Rarity.UNCOMMON, mage.cards.h.HaunterOfNightveil.class));
        cards.add(new SetCardInfo("Hidden Strings", 12, Rarity.COMMON, mage.cards.h.HiddenStrings.class));
        cards.add(new SetCardInfo("Hired Torturer", 25, Rarity.COMMON, mage.cards.h.HiredTorturer.class));
        cards.add(new SetCardInfo("Izzet Cluestone", 141, Rarity.COMMON, mage.cards.i.IzzetCluestone.class));
        cards.add(new SetCardInfo("Izzet Guildgate", 151, Rarity.COMMON, mage.cards.i.IzzetGuildgate.class));
        cards.add(new SetCardInfo("Jelenn Sphinx", 77, Rarity.UNCOMMON, mage.cards.j.JelennSphinx.class));
        cards.add(new SetCardInfo("Korozda Gorgon", 78, Rarity.UNCOMMON, mage.cards.k.KorozdaGorgon.class));
        cards.add(new SetCardInfo("Krasis Incubation", 79, Rarity.UNCOMMON, mage.cards.k.KrasisIncubation.class));
        cards.add(new SetCardInfo("Kraul Warrior", 42, Rarity.COMMON, mage.cards.k.KraulWarrior.class));
        cards.add(new SetCardInfo("Lavinia of the Tenth", 80, Rarity.RARE, mage.cards.l.LaviniaOfTheTenth.class));
        cards.add(new SetCardInfo("Legion's Initiative", 81, Rarity.MYTHIC, mage.cards.l.LegionsInitiative.class));
        cards.add(new SetCardInfo("Lyev Decree", 3, Rarity.COMMON, mage.cards.l.LyevDecree.class));
        cards.add(new SetCardInfo("Master of Cruelties", 82, Rarity.MYTHIC, mage.cards.m.MasterOfCruelties.class));
        cards.add(new SetCardInfo("Maw of the Obzedat", 83, Rarity.UNCOMMON, mage.cards.m.MawOfTheObzedat.class));
        cards.add(new SetCardInfo("Maze Abomination", 26, Rarity.COMMON, mage.cards.m.MazeAbomination.class));
        cards.add(new SetCardInfo("Maze Behemoth", 43, Rarity.COMMON, mage.cards.m.MazeBehemoth.class));
        cards.add(new SetCardInfo("Maze Glider", 13, Rarity.COMMON, mage.cards.m.MazeGlider.class));
        cards.add(new SetCardInfo("Maze Rusher", 33, Rarity.COMMON, mage.cards.m.MazeRusher.class));
        cards.add(new SetCardInfo("Maze's End", 152, Rarity.MYTHIC, mage.cards.m.MazesEnd.class));
        cards.add(new SetCardInfo("Maze Sentinel", 4, Rarity.COMMON, mage.cards.m.MazeSentinel.class));
        cards.add(new SetCardInfo("Melek, Izzet Paragon", 84, Rarity.RARE, mage.cards.m.MelekIzzetParagon.class));
        cards.add(new SetCardInfo("Mending Touch", 44, Rarity.COMMON, mage.cards.m.MendingTouch.class));
        cards.add(new SetCardInfo("Mindstatic", 14, Rarity.COMMON, mage.cards.m.Mindstatic.class));
        cards.add(new SetCardInfo("Mirko Vosk, Mind Drinker", 85, Rarity.RARE, mage.cards.m.MirkoVoskMindDrinker.class));
        cards.add(new SetCardInfo("Morgue Burst", 86, Rarity.COMMON, mage.cards.m.MorgueBurst.class));
        cards.add(new SetCardInfo("Murmuring Phantasm", 15, Rarity.COMMON, mage.cards.m.MurmuringPhantasm.class));
        cards.add(new SetCardInfo("Mutant's Prey", 45, Rarity.COMMON, mage.cards.m.MutantsPrey.class));
        cards.add(new SetCardInfo("Nivix Cyclops", 87, Rarity.COMMON, mage.cards.n.NivixCyclops.class));
        cards.add(new SetCardInfo("Notion Thief", 88, Rarity.RARE, mage.cards.n.NotionThief.class));
        cards.add(new SetCardInfo("Obzedat's Aid", 89, Rarity.RARE, mage.cards.o.ObzedatsAid.class));
        cards.add(new SetCardInfo("Opal Lake Gatekeepers", 16, Rarity.COMMON, mage.cards.o.OpalLakeGatekeepers.class));
        cards.add(new SetCardInfo("Orzhov Cluestone", 142, Rarity.COMMON, mage.cards.o.OrzhovCluestone.class));
        cards.add(new SetCardInfo("Orzhov Guildgate", 153, Rarity.COMMON, mage.cards.o.OrzhovGuildgate.class));
        cards.add(new SetCardInfo("Phytoburst", 46, Rarity.COMMON, mage.cards.p.Phytoburst.class));
        cards.add(new SetCardInfo("Pilfered Plans", 90, Rarity.COMMON, mage.cards.p.PilferedPlans.class));
        cards.add(new SetCardInfo("Plasm Capture", 91, Rarity.RARE, mage.cards.p.PlasmCapture.class));
        cards.add(new SetCardInfo("Pontiff of Blight", 27, Rarity.RARE, mage.cards.p.PontiffOfBlight.class));
        cards.add(new SetCardInfo("Possibility Storm", 34, Rarity.RARE, mage.cards.p.PossibilityStorm.class));
        cards.add(new SetCardInfo("Profit // Loss", 130, Rarity.UNCOMMON, mage.cards.p.ProfitLoss.class));
        cards.add(new SetCardInfo("Progenitor Mimic", 92, Rarity.MYTHIC, mage.cards.p.ProgenitorMimic.class));
        cards.add(new SetCardInfo("Protect // Serve", 131, Rarity.UNCOMMON, mage.cards.p.ProtectServe.class));
        cards.add(new SetCardInfo("Punish the Enemy", 35, Rarity.COMMON, mage.cards.p.PunishTheEnemy.class));
        cards.add(new SetCardInfo("Putrefy", 93, Rarity.UNCOMMON, mage.cards.p.Putrefy.class));
        cards.add(new SetCardInfo("Pyrewild Shaman", 36, Rarity.RARE, mage.cards.p.PyrewildShaman.class));
        cards.add(new SetCardInfo("Rakdos Cluestone", 143, Rarity.COMMON, mage.cards.r.RakdosCluestone.class));
        cards.add(new SetCardInfo("Rakdos Drake", 28, Rarity.COMMON, mage.cards.r.RakdosDrake.class));
        cards.add(new SetCardInfo("Rakdos Guildgate", 154, Rarity.COMMON, mage.cards.r.RakdosGuildgate.class));
        cards.add(new SetCardInfo("Ral Zarek", 94, Rarity.MYTHIC, mage.cards.r.RalZarek.class));
        cards.add(new SetCardInfo("Ready // Willing", 132, Rarity.RARE, mage.cards.r.ReadyWilling.class));
        cards.add(new SetCardInfo("Reap Intellect", 95, Rarity.MYTHIC, mage.cards.r.ReapIntellect.class));
        cards.add(new SetCardInfo("Render Silent", 96, Rarity.RARE, mage.cards.r.RenderSilent.class));
        cards.add(new SetCardInfo("Renegade Krasis", 47, Rarity.RARE, mage.cards.r.RenegadeKrasis.class));
        cards.add(new SetCardInfo("Renounce the Guilds", 5, Rarity.RARE, mage.cards.r.RenounceTheGuilds.class));
        cards.add(new SetCardInfo("Restore the Peace", 97, Rarity.UNCOMMON, mage.cards.r.RestoreThePeace.class));
        cards.add(new SetCardInfo("Riot Control", 6, Rarity.COMMON, mage.cards.r.RiotControl.class));
        cards.add(new SetCardInfo("Riot Piker", 37, Rarity.COMMON, mage.cards.r.RiotPiker.class));
        cards.add(new SetCardInfo("Rot Farm Skeleton", 98, Rarity.UNCOMMON, mage.cards.r.RotFarmSkeleton.class));
        cards.add(new SetCardInfo("Rubblebelt Maaka", 38, Rarity.COMMON, mage.cards.r.RubblebeltMaaka.class));
        cards.add(new SetCardInfo("Runner's Bane", 17, Rarity.COMMON, mage.cards.r.RunnersBane.class));
        cards.add(new SetCardInfo("Ruric Thar, the Unbowed", 99, Rarity.RARE, mage.cards.r.RuricTharTheUnbowed.class));
        cards.add(new SetCardInfo("Saruli Gatekeepers", 48, Rarity.COMMON, mage.cards.s.SaruliGatekeepers.class));
        cards.add(new SetCardInfo("Savageborn Hydra", 100, Rarity.MYTHIC, mage.cards.s.SavagebornHydra.class));
        cards.add(new SetCardInfo("Scab-Clan Giant", 101, Rarity.UNCOMMON, mage.cards.s.ScabClanGiant.class));
        cards.add(new SetCardInfo("Scion of Vitu-Ghazi", 7, Rarity.RARE, mage.cards.s.ScionOfVituGhazi.class));
        cards.add(new SetCardInfo("Selesnya Cluestone", 144, Rarity.COMMON, mage.cards.s.SelesnyaCluestone.class));
        cards.add(new SetCardInfo("Selesnya Guildgate", 155, Rarity.COMMON, mage.cards.s.SelesnyaGuildgate.class));
        cards.add(new SetCardInfo("Showstopper", 102, Rarity.UNCOMMON, mage.cards.s.Showstopper.class));
        cards.add(new SetCardInfo("Simic Cluestone", 145, Rarity.COMMON, mage.cards.s.SimicCluestone.class));
        cards.add(new SetCardInfo("Simic Guildgate", 156, Rarity.COMMON, mage.cards.s.SimicGuildgate.class));
        cards.add(new SetCardInfo("Sin Collector", 103, Rarity.UNCOMMON, mage.cards.s.SinCollector.class));
        cards.add(new SetCardInfo("Sinister Possession", 29, Rarity.COMMON, mage.cards.s.SinisterPossession.class));
        cards.add(new SetCardInfo("Sire of Insanity", 104, Rarity.RARE, mage.cards.s.SireOfInsanity.class));
        cards.add(new SetCardInfo("Skylasher", 49, Rarity.RARE, mage.cards.s.Skylasher.class));
        cards.add(new SetCardInfo("Smelt-Ward Gatekeepers", 39, Rarity.COMMON, mage.cards.s.SmeltWardGatekeepers.class));
        cards.add(new SetCardInfo("Species Gorger", 105, Rarity.UNCOMMON, mage.cards.s.SpeciesGorger.class));
        cards.add(new SetCardInfo("Spike Jester", 106, Rarity.UNCOMMON, mage.cards.s.SpikeJester.class));
        cards.add(new SetCardInfo("Steeple Roc", 8, Rarity.COMMON, mage.cards.s.SteepleRoc.class));
        cards.add(new SetCardInfo("Sunspire Gatekeepers", 9, Rarity.COMMON, mage.cards.s.SunspireGatekeepers.class));
        cards.add(new SetCardInfo("Tajic, Blade of the Legion", 107, Rarity.RARE, mage.cards.t.TajicBladeOfTheLegion.class));
        cards.add(new SetCardInfo("Teysa, Envoy of Ghosts", 108, Rarity.RARE, mage.cards.t.TeysaEnvoyOfGhosts.class));
        cards.add(new SetCardInfo("Thrashing Mossdog", 50, Rarity.COMMON, mage.cards.t.ThrashingMossdog.class));
        cards.add(new SetCardInfo("Tithe Drinker", 109, Rarity.COMMON, mage.cards.t.TitheDrinker.class));
        cards.add(new SetCardInfo("Toil // Trouble", 133, Rarity.UNCOMMON, mage.cards.t.ToilTrouble.class));
        cards.add(new SetCardInfo("Trostani's Summoner", 110, Rarity.UNCOMMON, mage.cards.t.TrostanisSummoner.class));
        cards.add(new SetCardInfo("Turn // Burn", 134, Rarity.UNCOMMON, mage.cards.t.TurnBurn.class));
        cards.add(new SetCardInfo("Ubul Sar Gatekeepers", 30, Rarity.COMMON, mage.cards.u.UbulSarGatekeepers.class));
        cards.add(new SetCardInfo("Uncovered Clues", 19, Rarity.COMMON, mage.cards.u.UncoveredClues.class));
        cards.add(new SetCardInfo("Unflinching Courage", 111, Rarity.UNCOMMON, mage.cards.u.UnflinchingCourage.class));
        cards.add(new SetCardInfo("Varolz, the Scar-Striped", 112, Rarity.RARE, mage.cards.v.VarolzTheScarStriped.class));
        cards.add(new SetCardInfo("Viashino Firstblade", 113, Rarity.COMMON, mage.cards.v.ViashinoFirstblade.class));
        cards.add(new SetCardInfo("Voice of Resurgence", 114, Rarity.MYTHIC, mage.cards.v.VoiceOfResurgence.class));
        cards.add(new SetCardInfo("Vorel of the Hull Clade", 115, Rarity.RARE, mage.cards.v.VorelOfTheHullClade.class));
        cards.add(new SetCardInfo("Wake the Reflections", 10, Rarity.COMMON, mage.cards.w.WakeTheReflections.class));
        cards.add(new SetCardInfo("Warleader's Helix", 116, Rarity.UNCOMMON, mage.cards.w.WarleadersHelix.class));
        cards.add(new SetCardInfo("Warped Physique", 117, Rarity.UNCOMMON, mage.cards.w.WarpedPhysique.class));
        cards.add(new SetCardInfo("Weapon Surge", 40, Rarity.COMMON, mage.cards.w.WeaponSurge.class));
        cards.add(new SetCardInfo("Wear // Tear", 135, Rarity.UNCOMMON, mage.cards.w.WearTear.class));
        cards.add(new SetCardInfo("Wind Drake", 20, Rarity.COMMON, mage.cards.w.WindDrake.class));
        cards.add(new SetCardInfo("Woodlot Crawler", 118, Rarity.UNCOMMON, mage.cards.w.WoodlotCrawler.class));
        cards.add(new SetCardInfo("Zhur-Taa Ancient", 119, Rarity.RARE, mage.cards.z.ZhurTaaAncient.class));
        cards.add(new SetCardInfo("Zhur-Taa Druid", 120, Rarity.COMMON, mage.cards.z.ZhurTaaDruid.class));
    }

    @Override
    protected void addSpecialCards(List<Card> booster, int number) {
        // number is here always 1
        // the land print sheets are believed to contain 23 copies of each Guildgate,
        // one copy of each RTR and GTC shockland, and two copies of Maze's End
        Rarity rarity;
        int rarityKey = RandomUtil.nextInt(242);
        if (rarityKey < 230) {
            rarity = Rarity.COMMON;
        } else if (rarityKey < 240) {
            rarity = Rarity.RARE;
        } else {
            rarity = Rarity.MYTHIC;
        }
        addToBooster(booster, getSpecialCardsByRarity(rarity));
    }

    @Override
    protected List<CardInfo> findSpecialCardsByRarity(Rarity rarity) {
        CardCriteria criteria = new CardCriteria();
        criteria.rarities(rarity).types(CardType.LAND);
        if (rarity == Rarity.RARE) {
            // shocklands
            criteria.setCodes("RTR", "GTC");
        } else {
            // Guildgates and Maze's End
            criteria.setCodes(this.code);
        }
        List<CardInfo> cardInfos = CardRepository.instance.findCards(criteria);
        cardInfos.removeIf(cardInfo -> (cardInfo.getName().equals("Grove of the Guardian")
                                    || cardInfo.getName().equals("Thespian's Stage")));
        return cardInfos;
    }
}
