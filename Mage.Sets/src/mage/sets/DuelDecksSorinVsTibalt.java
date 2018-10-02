
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author LevelX2
 */
public final class DuelDecksSorinVsTibalt extends ExpansionSet {

    private static final DuelDecksSorinVsTibalt instance = new DuelDecksSorinVsTibalt();

    public static DuelDecksSorinVsTibalt getInstance() {
        return instance;
    }

    private DuelDecksSorinVsTibalt() {
        super("Duel Decks: Sorin vs. Tibalt", "DDK", ExpansionSet.buildDate(2013, 3, 15), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Absorb Vis", 31, Rarity.COMMON, mage.cards.a.AbsorbVis.class));
        cards.add(new SetCardInfo("Akoum Refuge", 73, Rarity.UNCOMMON, mage.cards.a.AkoumRefuge.class));
        cards.add(new SetCardInfo("Ancient Craving", 28, Rarity.RARE, mage.cards.a.AncientCraving.class));
        cards.add(new SetCardInfo("Ashmouth Hound", 45, Rarity.COMMON, mage.cards.a.AshmouthHound.class));
        cards.add(new SetCardInfo("Blazing Salvo", 58, Rarity.COMMON, mage.cards.b.BlazingSalvo.class));
        cards.add(new SetCardInfo("Blightning", 69, Rarity.COMMON, mage.cards.b.Blightning.class));
        cards.add(new SetCardInfo("Bloodrage Vampire", 10, Rarity.COMMON, mage.cards.b.BloodrageVampire.class));
        cards.add(new SetCardInfo("Breaking Point", 67, Rarity.RARE, mage.cards.b.BreakingPoint.class));
        cards.add(new SetCardInfo("Browbeat", 66, Rarity.UNCOMMON, mage.cards.b.Browbeat.class));
        cards.add(new SetCardInfo("Bump in the Night", 57, Rarity.COMMON, mage.cards.b.BumpInTheNight.class));
        cards.add(new SetCardInfo("Butcher of Malakir", 18, Rarity.RARE, mage.cards.b.ButcherOfMalakir.class));
        cards.add(new SetCardInfo("Child of Night", 5, Rarity.COMMON, mage.cards.c.ChildOfNight.class));
        cards.add(new SetCardInfo("Coal Stoker", 49, Rarity.COMMON, mage.cards.c.CoalStoker.class));
        cards.add(new SetCardInfo("Corpse Connoisseur", 54, Rarity.UNCOMMON, mage.cards.c.CorpseConnoisseur.class));
        cards.add(new SetCardInfo("Death Grasp", 32, Rarity.RARE, mage.cards.d.DeathGrasp.class));
        cards.add(new SetCardInfo("Decompose", 20, Rarity.UNCOMMON, mage.cards.d.Decompose.class));
        cards.add(new SetCardInfo("Devil's Play", 72, Rarity.RARE, mage.cards.d.DevilsPlay.class));
        cards.add(new SetCardInfo("Doomed Traveler", 2, Rarity.COMMON, mage.cards.d.DoomedTraveler.class));
        cards.add(new SetCardInfo("Duskhunter Bat", 6, Rarity.COMMON, mage.cards.d.DuskhunterBat.class));
        cards.add(new SetCardInfo("Evolving Wilds", 33, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Faithless Looting", 59, Rarity.COMMON, mage.cards.f.FaithlessLooting.class));
        cards.add(new SetCardInfo("Field of Souls", 30, Rarity.RARE, mage.cards.f.FieldOfSouls.class));
        cards.add(new SetCardInfo("Fiend Hunter", 11, Rarity.UNCOMMON, mage.cards.f.FiendHunter.class));
        cards.add(new SetCardInfo("Flame Javelin", 70, Rarity.UNCOMMON, mage.cards.f.FlameJavelin.class));
        cards.add(new SetCardInfo("Flame Slash", 60, Rarity.COMMON, mage.cards.f.FlameSlash.class));
        cards.add(new SetCardInfo("Gang of Devils", 56, Rarity.UNCOMMON, mage.cards.g.GangOfDevils.class));
        cards.add(new SetCardInfo("Gatekeeper of Malakir", 8, Rarity.UNCOMMON, mage.cards.g.GatekeeperOfMalakir.class));
        cards.add(new SetCardInfo("Geistflame", 61, Rarity.COMMON, mage.cards.g.Geistflame.class));
        cards.add(new SetCardInfo("Goblin Arsonist", 42, Rarity.COMMON, mage.cards.g.GoblinArsonist.class));
        cards.add(new SetCardInfo("Hellrider", 52, Rarity.RARE, mage.cards.h.Hellrider.class));
        cards.add(new SetCardInfo("Hellspark Elemental", 46, Rarity.UNCOMMON, mage.cards.h.HellsparkElemental.class));
        cards.add(new SetCardInfo("Lavaborn Muse", 50, Rarity.RARE, mage.cards.l.LavabornMuse.class));
        cards.add(new SetCardInfo("Lingering Souls", 24, Rarity.UNCOMMON, mage.cards.l.LingeringSouls.class));
        cards.add(new SetCardInfo("Mad Prophet", 51, Rarity.COMMON, mage.cards.m.MadProphet.class));
        cards.add(new SetCardInfo("Mark of the Vampire", 29, Rarity.COMMON, mage.cards.m.MarkOfTheVampire.class));
        cards.add(new SetCardInfo("Mausoleum Guard", 13, Rarity.UNCOMMON, mage.cards.m.MausoleumGuard.class));
        cards.add(new SetCardInfo("Mesmeric Fiend", 7, Rarity.COMMON, mage.cards.m.MesmericFiend.class));
        cards.add(new SetCardInfo("Mortify", 25, Rarity.UNCOMMON, mage.cards.m.Mortify.class));
        cards.add(new SetCardInfo("Mountain", 75, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 76, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 77, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phantom General", 14, Rarity.UNCOMMON, mage.cards.p.PhantomGeneral.class));
        cards.add(new SetCardInfo("Plains", 38, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 39, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 40, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pyroclasm", 62, Rarity.UNCOMMON, mage.cards.p.Pyroclasm.class));
        cards.add(new SetCardInfo("Rakdos Carnarium", 74, Rarity.COMMON, mage.cards.r.RakdosCarnarium.class));
        cards.add(new SetCardInfo("Reassembling Skeleton", 44, Rarity.UNCOMMON, mage.cards.r.ReassemblingSkeleton.class));
        cards.add(new SetCardInfo("Recoup", 63, Rarity.UNCOMMON, mage.cards.r.Recoup.class));
        cards.add(new SetCardInfo("Revenant Patriarch", 16, Rarity.UNCOMMON, mage.cards.r.RevenantPatriarch.class));
        cards.add(new SetCardInfo("Scorched Rusalka", 43, Rarity.UNCOMMON, mage.cards.s.ScorchedRusalka.class));
        cards.add(new SetCardInfo("Scourge Devil", 55, Rarity.UNCOMMON, mage.cards.s.ScourgeDevil.class));
        cards.add(new SetCardInfo("Sengir Vampire", 17, Rarity.UNCOMMON, mage.cards.s.SengirVampire.class));
        cards.add(new SetCardInfo("Shambling Remains", 48, Rarity.UNCOMMON, mage.cards.s.ShamblingRemains.class));
        cards.add(new SetCardInfo("Skirsdag Cultist", 53, Rarity.UNCOMMON, mage.cards.s.SkirsdagCultist.class));
        cards.add(new SetCardInfo("Sorin, Lord of Innistrad", 1, Rarity.MYTHIC, mage.cards.s.SorinLordOfInnistrad.class));
        cards.add(new SetCardInfo("Sorin's Thirst", 21, Rarity.COMMON, mage.cards.s.SorinsThirst.class));
        cards.add(new SetCardInfo("Spectral Procession", 26, Rarity.UNCOMMON, mage.cards.s.SpectralProcession.class));
        cards.add(new SetCardInfo("Strangling Soot", 65, Rarity.COMMON, mage.cards.s.StranglingSoot.class));
        cards.add(new SetCardInfo("Sulfuric Vortex", 68, Rarity.RARE, mage.cards.s.SulfuricVortex.class));
        cards.add(new SetCardInfo("Swamp", 35, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 36, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 37, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 78, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 79, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 80, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tainted Field", 34, Rarity.UNCOMMON, mage.cards.t.TaintedField.class));
        cards.add(new SetCardInfo("Terminate", 64, Rarity.COMMON, mage.cards.t.Terminate.class));
        cards.add(new SetCardInfo("Tibalt, the Fiend-Blooded", 41, Rarity.MYTHIC, mage.cards.t.TibaltTheFiendBlooded.class));
        cards.add(new SetCardInfo("Torrent of Souls", 71, Rarity.UNCOMMON, mage.cards.t.TorrentOfSouls.class));
        cards.add(new SetCardInfo("Twilight Drover", 9, Rarity.RARE, mage.cards.t.TwilightDrover.class));
        cards.add(new SetCardInfo("Unmake", 27, Rarity.COMMON, mage.cards.u.Unmake.class));
        cards.add(new SetCardInfo("Urge to Feed", 22, Rarity.UNCOMMON, mage.cards.u.UrgeToFeed.class));
        cards.add(new SetCardInfo("Vampire Lacerator", 3, Rarity.COMMON, mage.cards.v.VampireLacerator.class));
        cards.add(new SetCardInfo("Vampire Nighthawk", 12, Rarity.UNCOMMON, mage.cards.v.VampireNighthawk.class));
        cards.add(new SetCardInfo("Vampire Outcasts", 15, Rarity.UNCOMMON, mage.cards.v.VampireOutcasts.class));
        cards.add(new SetCardInfo("Vampire's Bite", 19, Rarity.COMMON, mage.cards.v.VampiresBite.class));
        cards.add(new SetCardInfo("Vithian Stinger", 47, Rarity.COMMON, mage.cards.v.VithianStinger.class));
        cards.add(new SetCardInfo("Wall of Omens", 4, Rarity.UNCOMMON, mage.cards.w.WallOfOmens.class));
        cards.add(new SetCardInfo("Zealous Persecution", 23, Rarity.UNCOMMON, mage.cards.z.ZealousPersecution.class));
    }
}
