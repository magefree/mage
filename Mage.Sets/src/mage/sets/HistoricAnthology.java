
package mage.sets;

import mage.cards.ExpansionSet;
import mage.cards.repository.CardCriteria;
import mage.cards.repository.CardInfo;
import mage.cards.repository.CardRepository;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mikalinn777
 */
public final class HistoricAnthology extends ExpansionSet {

    private static final HistoricAnthology instance = new HistoricAnthology();

    public static HistoricAnthology getInstance() {
        return instance;
    }

    private HistoricAnthology() {
        super("Historic Anthology", "HA1", ExpansionSet.buildDate(2020, 5, 21), SetType.SUPPLEMENTAL);
        this.blockName = "Reprint";
        this.hasBoosters = false;
        this.hasBasicLands = false;
        cards.add(new SetCardInfo("Ancestral Mask", 34, Rarity.COMMON, mage.cards.a.AncestralMask.class));
        cards.add(new SetCardInfo("Ancient Ziggurat", 72, Rarity.UNCOMMON, mage.cards.a.AncientZiggurat.class));
        cards.add(new SetCardInfo("Akroma's Memorial", 70, Rarity.MYTHIC, mage.cards.a.AkromasMemorial.class));
        cards.add(new SetCardInfo("Barren Moor", 40, Rarity.COMMON, mage.cards.b.BarrenMoor.class));
        cards.add(new SetCardInfo("Body Double", 52, Rarity.RARE, mage.cards.b.BodyDouble.class));
        cards.add(new SetCardInfo("Bojuka Bog", 41, Rarity.COMMON, mage.cards.b.BojukaBog.class));
        cards.add(new SetCardInfo("Brain Maggot", 28, Rarity.UNCOMMON, mage.cards.b.BrainMaggot.class));
        cards.add(new SetCardInfo("Burning Tree Emissary", 16, Rarity.UNCOMMON, mage.cards.b.BurningTreeEmissary.class));
        cards.add(new SetCardInfo("Captain Sisay", 17, Rarity.RARE, mage.cards.c.CaptainSisay.class));
        cards.add(new SetCardInfo("Chainer's Edict", 56, Rarity.UNCOMMON, mage.cards.c.ChainersEdict.class));
        cards.add(new SetCardInfo("Cryptbreaker", 6, Rarity.RARE, mage.cards.c.Cryptbreaker.class));
        cards.add(new SetCardInfo("Darksteel Reactor", 20, Rarity.RARE, mage.cards.d.DarksteelReactor.class));
        cards.add(new SetCardInfo("Devil's Play", 61, Rarity.RARE, mage.cards.d.DevilsPlay.class));
        cards.add(new SetCardInfo("Distant Melody", 5, Rarity.COMMON, mage.cards.d.DistantMelody.class));
        cards.add(new SetCardInfo("Dragonmaster Outcast", 32, Rarity.COMMON, mage.cards.d.DragonmasterOutcast.class));
        cards.add(new SetCardInfo("Enchantress's Presence", 65, Rarity.RARE, mage.cards.e.EnchantresssPresence.class));
        cards.add(new SetCardInfo("Elvish Visionary", 13, Rarity.COMMON, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("Fauna Shaman", 14, Rarity.RARE, mage.cards.f.FaunaShaman.class));
        cards.add(new SetCardInfo("Forgotten Cave", 42, Rarity.COMMON, mage.cards.f.ForgottenCave.class));
        cards.add(new SetCardInfo("Gempalm Incinerator", 62, Rarity.UNCOMMON, mage.cards.g.GempalmIncinerator.class));
        cards.add(new SetCardInfo("Gempalm Polluter", 57, Rarity.COMMON, mage.cards.g.GempalmPolluter.class));
        cards.add(new SetCardInfo("Ghost Quarter", 43, Rarity.UNCOMMON, mage.cards.g.GhostQuarter.class));
        cards.add(new SetCardInfo("Goblin Matron", 11, Rarity.UNCOMMON, mage.cards.g.GoblinMatron.class));
        cards.add(new SetCardInfo("Goblin Ruinblaster", 33, Rarity.UNCOMMON, mage.cards.g.GoblinRuinblaster.class));
        cards.add(new SetCardInfo("Hidetsugu's Second Rite", 12, Rarity.RARE, mage.cards.h.HidetsugusSecondRite.class));
        cards.add(new SetCardInfo("Honden of Cleansing Fire", 48, Rarity.UNCOMMON, mage.cards.h.HondenOfCleansingFire.class));
        cards.add(new SetCardInfo("Honden of Infinite Rage", 63, Rarity.UNCOMMON, mage.cards.h.HondenOfInfiniteRage.class));
        cards.add(new SetCardInfo("Honden of Life's Web", 66, Rarity.UNCOMMON, mage.cards.h.HondenOfLifesWeb.class));
        cards.add(new SetCardInfo("Honden of Night's Reach", 58, Rarity.UNCOMMON, mage.cards.h.HondenOfNightsReach.class));
        cards.add(new SetCardInfo("Honden of Seeing Winds", 53, Rarity.UNCOMMON, mage.cards.h.HondenOfSeeingWinds.class));
        cards.add(new SetCardInfo("Hypnotic Specter", 7, Rarity.RARE, mage.cards.h.HypnoticSpecter.class));
        cards.add(new SetCardInfo("Imperious Perfect", 15, Rarity.UNCOMMON, mage.cards.i.ImperiousPerfect.class));
        cards.add(new SetCardInfo("Inexorable Tide", 27, Rarity.RARE, mage.cards.i.InexorableTide.class));
        cards.add(new SetCardInfo("Kiln Fiend", 10, Rarity.COMMON, mage.cards.k.KilnFiend.class));
        cards.add(new SetCardInfo("Kinsbaile Cavalier", 3, Rarity.COMMON, mage.cards.k.KinsbaileCavalier.class));
        cards.add(new SetCardInfo("Knight of the Reliquary", 36, Rarity.RARE, mage.cards.k.KnightOfTheReliquary.class));
        cards.add(new SetCardInfo("Krosan Tusker", 67, Rarity.COMMON, mage.cards.k.KrosanTusker.class));
        cards.add(new SetCardInfo("Lonely Sandbar", 44, Rarity.COMMON, mage.cards.l.LonelySandbar.class));
        cards.add(new SetCardInfo("Maelstrom Pulse", 37, Rarity.RARE, mage.cards.m.MaelstromPulse.class));
        cards.add(new SetCardInfo("Maze's End", 73, Rarity.MYTHIC, mage.cards.m.MazesEnd.class));
        cards.add(new SetCardInfo("Meddling Mage", 38, Rarity.RARE, mage.cards.m.MeddlingMage.class));
        cards.add(new SetCardInfo("Merrow Reejerey", 26, Rarity.UNCOMMON, mage.cards.m.MerrowReejerey.class));
        cards.add(new SetCardInfo("Mind Stone", 19, Rarity.COMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Mirari's Wake", 69, Rarity.RARE, mage.cards.m.MirarisWake.class));
        cards.add(new SetCardInfo("Momentary Blink", 49, Rarity.COMMON, mage.cards.m.MomentaryBlink.class));
        cards.add(new SetCardInfo("Nyxfleece Ram", 22, Rarity.UNCOMMON, mage.cards.n.NyxFleeceRam.class));
        cards.add(new SetCardInfo("Ornithopter", 18, Rarity.UNCOMMON, mage.cards.o.Ornithopter.class));
        cards.add(new SetCardInfo("Pack Rat", 29, Rarity.RARE, mage.cards.p.PackRat.class));
        cards.add(new SetCardInfo("Phrexian Arena", 8, Rarity.RARE, mage.cards.p.PhyrexianArena.class));
        cards.add(new SetCardInfo("Phyrexian Obliterator", 59, Rarity.MYTHIC, mage.cards.p.PhyrexianObliterator.class));
        cards.add(new SetCardInfo("Platinum Angel", 39, Rarity.MYTHIC, mage.cards.p.PlatinumAngel.class));
        cards.add(new SetCardInfo("Ranger of Eos", 23, Rarity.RARE, mage.cards.r.RangerOfEos.class));
        cards.add(new SetCardInfo("Ratchet Bomb", 71, Rarity.RARE, mage.cards.r.RatchetBomb.class));
        cards.add(new SetCardInfo("Roar of the Wurm", 68, Rarity.UNCOMMON, mage.cards.r.RoarOfTheWurm.class));
        cards.add(new SetCardInfo("Rhys The Redeemed", 21, Rarity.RARE, mage.cards.r.RhysTheRedeemed.class));
        cards.add(new SetCardInfo("Secluded Steppe", 45, Rarity.COMMON, mage.cards.s.SecludedSteppe.class));
        cards.add(new SetCardInfo("Serra Ascendant", 1, Rarity.RARE, mage.cards.s.SerraAscendant.class));
        cards.add(new SetCardInfo("Sigil of the Empty Throne", 24, Rarity.RARE, mage.cards.s.SigilOfTheEmptyThrone.class));
        cards.add(new SetCardInfo("Silent Departure", 54, Rarity.COMMON, mage.cards.s.SilentDeparture.class));
        cards.add(new SetCardInfo("Soul Warden", 2, Rarity.COMMON, mage.cards.s.SoulWarden.class));
        cards.add(new SetCardInfo("Swan Song", 55, Rarity.RARE, mage.cards.s.SwanSong.class));
        cards.add(new SetCardInfo("Tectonic Reformation", 64, Rarity.RARE, mage.cards.t.TectonicReformation.class));
        cards.add(new SetCardInfo("Tempered Steel", 50, Rarity.RARE, mage.cards.t.TemperedSteel.class));
        cards.add(new SetCardInfo("Tendrils of Corruption", 9, Rarity.COMMON, mage.cards.t.TendrilsOfCorruption.class));
        cards.add(new SetCardInfo("Terravore", 35, Rarity.RARE, mage.cards.t.Terravore.class));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 25, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class));
        cards.add(new SetCardInfo("Timely Reinforcements", 51, Rarity.UNCOMMON, mage.cards.t.TimelyReinforcements.class));
        cards.add(new SetCardInfo("Tranquil Thicket", 46, Rarity.COMMON, mage.cards.t.TranquilThicket.class));
        cards.add(new SetCardInfo("Treasure Hunt", 4, Rarity.COMMON, mage.cards.t.TreasureHunt.class));
        cards.add(new SetCardInfo("Ulamog, the Ceaseless Hunger", 47, Rarity.MYTHIC, mage.cards.u.UlamogTheCeaselessHunger.class));
        cards.add(new SetCardInfo("Unburial Rites", 60, Rarity.UNCOMMON, mage.cards.u.UnburialRites.class));
        cards.add(new SetCardInfo("Virulent Plague", 30, Rarity.UNCOMMON, mage.cards.v.VirulentPlague.class));
        cards.add(new SetCardInfo("Waste Not", 31, Rarity.RARE, mage.cards.w.WasteNot.class));


    }

}
