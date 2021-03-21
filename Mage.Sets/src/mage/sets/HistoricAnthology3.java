package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author mikalinn777
 */
public final class HistoricAnthology3 extends ExpansionSet {

    private static final HistoricAnthology3 instance = new HistoricAnthology3();

    public static HistoricAnthology3 getInstance() {
        return instance;
    }

    private HistoricAnthology3() {
        super("Historic Anthology 3", "HA3", ExpansionSet.buildDate(2020, 5, 21), SetType.MAGIC_ARENA);
        this.blockName = "Reprint";
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ancient Ziggurat", 26, Rarity.UNCOMMON, mage.cards.a.AncientZiggurat.class));
        cards.add(new SetCardInfo("Akroma's Memorial", 24, Rarity.MYTHIC, mage.cards.a.AkromasMemorial.class));
        cards.add(new SetCardInfo("Body Double", 6, Rarity.RARE, mage.cards.b.BodyDouble.class));
        cards.add(new SetCardInfo("Chainer's Edict", 10, Rarity.UNCOMMON, mage.cards.c.ChainersEdict.class));
        cards.add(new SetCardInfo("Devil's Play", 15, Rarity.RARE, mage.cards.d.DevilsPlay.class));
        cards.add(new SetCardInfo("Enchantress's Presence", 19, Rarity.RARE, mage.cards.e.EnchantresssPresence.class));
        cards.add(new SetCardInfo("Gempalm Incinerator", 16, Rarity.UNCOMMON, mage.cards.g.GempalmIncinerator.class));
        cards.add(new SetCardInfo("Gempalm Polluter", 11, Rarity.COMMON, mage.cards.g.GempalmPolluter.class));
        cards.add(new SetCardInfo("Honden of Cleansing Fire", 2, Rarity.UNCOMMON, mage.cards.h.HondenOfCleansingFire.class));
        cards.add(new SetCardInfo("Honden of Infinite Rage", 17, Rarity.UNCOMMON, mage.cards.h.HondenOfInfiniteRage.class));
        cards.add(new SetCardInfo("Honden of Life's Web", 20, Rarity.UNCOMMON, mage.cards.h.HondenOfLifesWeb.class));
        cards.add(new SetCardInfo("Honden of Night's Reach", 12, Rarity.UNCOMMON, mage.cards.h.HondenOfNightsReach.class));
        cards.add(new SetCardInfo("Honden of Seeing Winds", 7, Rarity.UNCOMMON, mage.cards.h.HondenOfSeeingWinds.class));
        cards.add(new SetCardInfo("Krosan Tusker", 21, Rarity.COMMON, mage.cards.k.KrosanTusker.class));
        cards.add(new SetCardInfo("Maze's End", 27, Rarity.MYTHIC, mage.cards.m.MazesEnd.class));
        cards.add(new SetCardInfo("Mirari's Wake", 23, Rarity.RARE, mage.cards.m.MirarisWake.class));
        cards.add(new SetCardInfo("Momentary Blink", 3, Rarity.COMMON, mage.cards.m.MomentaryBlink.class));
        cards.add(new SetCardInfo("Phyrexian Obliterator", 13, Rarity.MYTHIC, mage.cards.p.PhyrexianObliterator.class));
        cards.add(new SetCardInfo("Ratchet Bomb", 25, Rarity.RARE, mage.cards.r.RatchetBomb.class));
        cards.add(new SetCardInfo("Roar of the Wurm", 22, Rarity.UNCOMMON, mage.cards.r.RoarOfTheWurm.class));
        cards.add(new SetCardInfo("Silent Departure", 8, Rarity.COMMON, mage.cards.s.SilentDeparture.class));
        cards.add(new SetCardInfo("Swan Song", 9, Rarity.RARE, mage.cards.s.SwanSong.class));
        cards.add(new SetCardInfo("Tectonic Reformation", 18, Rarity.RARE, mage.cards.t.TectonicReformation.class));
        cards.add(new SetCardInfo("Tempered Steel", 4, Rarity.RARE, mage.cards.t.TemperedSteel.class));
        cards.add(new SetCardInfo("Timely Reinforcements", 5, Rarity.UNCOMMON, mage.cards.t.TimelyReinforcements.class));
        cards.add(new SetCardInfo("Ulamog, the Ceaseless Hunger", 1, Rarity.MYTHIC, mage.cards.u.UlamogTheCeaselessHunger.class));
        cards.add(new SetCardInfo("Unburial Rites", 14, Rarity.UNCOMMON, mage.cards.u.UnburialRites.class));
    }
}
