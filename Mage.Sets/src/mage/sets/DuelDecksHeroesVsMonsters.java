
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author LevelX2
 */
public final class DuelDecksHeroesVsMonsters extends ExpansionSet {

    private static final DuelDecksHeroesVsMonsters instance = new DuelDecksHeroesVsMonsters();

    public static DuelDecksHeroesVsMonsters getInstance() {
        return instance;
    }

    private DuelDecksHeroesVsMonsters() {
        super("Duel Decks: Heroes vs. Monsters", "DDL", ExpansionSet.buildDate(2013, 9, 6), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Anax and Cymede", 11, Rarity.RARE, mage.cards.a.AnaxAndCymede.class));
        cards.add(new SetCardInfo("Armory Guard", 12, Rarity.COMMON, mage.cards.a.ArmoryGuard.class));
        cards.add(new SetCardInfo("Auramancer", 9, Rarity.COMMON, mage.cards.a.Auramancer.class));
        cards.add(new SetCardInfo("Battle Mastery", 27, Rarity.UNCOMMON, mage.cards.b.BattleMastery.class));
        cards.add(new SetCardInfo("Beast Within", 69, Rarity.UNCOMMON, mage.cards.b.BeastWithin.class));
        cards.add(new SetCardInfo("Blood Ogre", 49, Rarity.COMMON, mage.cards.b.BloodOgre.class));
        cards.add(new SetCardInfo("Bonds of Faith", 24, Rarity.COMMON, mage.cards.b.BondsOfFaith.class));
        cards.add(new SetCardInfo("Boros Guildgate", 33, Rarity.COMMON, mage.cards.b.BorosGuildgate.class));
        cards.add(new SetCardInfo("Cavalry Pegasus", 4, Rarity.COMMON, mage.cards.c.CavalryPegasus.class));
        cards.add(new SetCardInfo("Condemn", 17, Rarity.UNCOMMON, mage.cards.c.Condemn.class));
        cards.add(new SetCardInfo("Conquering Manticore", 55, Rarity.RARE, mage.cards.c.ConqueringManticore.class));
        cards.add(new SetCardInfo("Crater Hellion", 56, Rarity.RARE, mage.cards.c.CraterHellion.class));
        cards.add(new SetCardInfo("Crowned Ceratok", 51, Rarity.UNCOMMON, mage.cards.c.CrownedCeratok.class));
        cards.add(new SetCardInfo("Daily Regimen", 18, Rarity.UNCOMMON, mage.cards.d.DailyRegimen.class));
        cards.add(new SetCardInfo("Dawnstrike Paladin", 14, Rarity.COMMON, mage.cards.d.DawnstrikePaladin.class));
        cards.add(new SetCardInfo("Deadly Recluse", 45, Rarity.COMMON, mage.cards.d.DeadlyRecluse.class));
        cards.add(new SetCardInfo("Destructive Revelry", 66, Rarity.UNCOMMON, mage.cards.d.DestructiveRevelry.class));
        cards.add(new SetCardInfo("Deus of Calamity", 54, Rarity.RARE, mage.cards.d.DeusOfCalamity.class));
        cards.add(new SetCardInfo("Dragon Blood", 67, Rarity.UNCOMMON, mage.cards.d.DragonBlood.class));
        cards.add(new SetCardInfo("Fencing Ace", 5, Rarity.UNCOMMON, mage.cards.f.FencingAce.class));
        cards.add(new SetCardInfo("Figure of Destiny", 3, Rarity.RARE, mage.cards.f.FigureOfDestiny.class));
        cards.add(new SetCardInfo("Fires of Yavimaya", 70, Rarity.UNCOMMON, mage.cards.f.FiresOfYavimaya.class));
        cards.add(new SetCardInfo("Forest", 78, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 79, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 80, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 81, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Freewind Equenaut", 10, Rarity.COMMON, mage.cards.f.FreewindEquenaut.class));
        cards.add(new SetCardInfo("Ghor-Clan Savage", 53, Rarity.COMMON, mage.cards.g.GhorClanSavage.class));
        cards.add(new SetCardInfo("Gorehorn Minotaurs", 52, Rarity.COMMON, mage.cards.g.GorehornMinotaurs.class));
        cards.add(new SetCardInfo("Griffin Guide", 28, Rarity.UNCOMMON, mage.cards.g.GriffinGuide.class));
        cards.add(new SetCardInfo("Gustcloak Sentinel", 13, Rarity.UNCOMMON, mage.cards.g.GustcloakSentinel.class));
        cards.add(new SetCardInfo("Kamahl, Pit Fighter", 16, Rarity.RARE, mage.cards.k.KamahlPitFighter.class));
        cards.add(new SetCardInfo("Kavu Predator", 46, Rarity.UNCOMMON, mage.cards.k.KavuPredator.class));
        cards.add(new SetCardInfo("Kazandu Refuge", 71, Rarity.UNCOMMON, mage.cards.k.KazanduRefuge.class));
        cards.add(new SetCardInfo("Krosan Tusker", 59, Rarity.COMMON, mage.cards.k.KrosanTusker.class));
        cards.add(new SetCardInfo("Llanowar Reborn", 72, Rarity.UNCOMMON, mage.cards.l.LlanowarReborn.class));
        cards.add(new SetCardInfo("Magma Jet", 22, Rarity.UNCOMMON, mage.cards.m.MagmaJet.class));
        cards.add(new SetCardInfo("Miraculous Recovery", 30, Rarity.UNCOMMON, mage.cards.m.MiraculousRecovery.class));
        cards.add(new SetCardInfo("Moment of Heroism", 25, Rarity.COMMON, mage.cards.m.MomentOfHeroism.class));
        cards.add(new SetCardInfo("Mountain", 35, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 36, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 37, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 38, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 74, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 75, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 76, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 77, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("New Benalia", 34, Rarity.UNCOMMON, mage.cards.n.NewBenalia.class));
        cards.add(new SetCardInfo("Nobilis of War", 15, Rarity.RARE, mage.cards.n.NobilisOfWar.class));
        cards.add(new SetCardInfo("Orcish Lumberjack", 44, Rarity.COMMON, mage.cards.o.OrcishLumberjack.class));
        cards.add(new SetCardInfo("Ordeal of Purphoros", 23, Rarity.UNCOMMON, mage.cards.o.OrdealOfPurphoros.class));
        cards.add(new SetCardInfo("Pay No Heed", 19, Rarity.COMMON, mage.cards.p.PayNoHeed.class));
        cards.add(new SetCardInfo("Plains", 39, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 40, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 41, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 42, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Polukranos, World Eater", 43, Rarity.MYTHIC, mage.cards.p.PolukranosWorldEater.class));
        cards.add(new SetCardInfo("Prey Upon", 62, Rarity.COMMON, mage.cards.p.PreyUpon.class));
        cards.add(new SetCardInfo("Pyroclasm", 63, Rarity.UNCOMMON, mage.cards.p.Pyroclasm.class));
        cards.add(new SetCardInfo("Pyrokinesis", 32, Rarity.UNCOMMON, mage.cards.p.Pyrokinesis.class));
        cards.add(new SetCardInfo("Regrowth", 64, Rarity.UNCOMMON, mage.cards.r.Regrowth.class));
        cards.add(new SetCardInfo("Righteousness", 20, Rarity.UNCOMMON, mage.cards.r.Righteousness.class));
        cards.add(new SetCardInfo("Satyr Hedonist", 47, Rarity.COMMON, mage.cards.s.SatyrHedonist.class));
        cards.add(new SetCardInfo("Shower of Sparks", 61, Rarity.COMMON, mage.cards.s.ShowerOfSparks.class));
        cards.add(new SetCardInfo("Skarrgan Firebird", 57, Rarity.RARE, mage.cards.s.SkarrganFirebird.class));
        cards.add(new SetCardInfo("Skarrgan Skybreaker", 60, Rarity.UNCOMMON, mage.cards.s.SkarrganSkybreaker.class));
        cards.add(new SetCardInfo("Skarrg, the Rage Pits", 73, Rarity.UNCOMMON, mage.cards.s.SkarrgTheRagePits.class));
        cards.add(new SetCardInfo("Smite the Monstrous", 29, Rarity.COMMON, mage.cards.s.SmiteTheMonstrous.class));
        cards.add(new SetCardInfo("Somberwald Vigilante", 2, Rarity.COMMON, mage.cards.s.SomberwaldVigilante.class));
        cards.add(new SetCardInfo("Stand Firm", 21, Rarity.COMMON, mage.cards.s.StandFirm.class));
        cards.add(new SetCardInfo("Stun Sniper", 7, Rarity.UNCOMMON, mage.cards.s.StunSniper.class));
        cards.add(new SetCardInfo("Sun Titan", 1, Rarity.MYTHIC, mage.cards.s.SunTitan.class));
        cards.add(new SetCardInfo("Terrifying Presence", 65, Rarity.COMMON, mage.cards.t.TerrifyingPresence.class));
        cards.add(new SetCardInfo("Thraben Valiant", 6, Rarity.COMMON, mage.cards.t.ThrabenValiant.class));
        cards.add(new SetCardInfo("Troll Ascetic", 50, Rarity.RARE, mage.cards.t.TrollAscetic.class));
        cards.add(new SetCardInfo("Truefire Paladin", 8, Rarity.UNCOMMON, mage.cards.t.TruefirePaladin.class));
        cards.add(new SetCardInfo("Undying Rage", 26, Rarity.UNCOMMON, mage.cards.u.UndyingRage.class));
        cards.add(new SetCardInfo("Valley Rannet", 58, Rarity.COMMON, mage.cards.v.ValleyRannet.class));
        cards.add(new SetCardInfo("Volt Charge", 68, Rarity.COMMON, mage.cards.v.VoltCharge.class));
        cards.add(new SetCardInfo("Winds of Rath", 31, Rarity.RARE, mage.cards.w.WindsOfRath.class));
        cards.add(new SetCardInfo("Zhur-Taa Druid", 48, Rarity.COMMON, mage.cards.z.ZhurTaaDruid.class));
    }
}
