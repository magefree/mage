package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/ha2
 *
 * @author mikalinn777
 */
public final class HistoricAnthology2 extends ExpansionSet {

    private static final HistoricAnthology2 instance = new HistoricAnthology2();

    public static HistoricAnthology2 getInstance() {
        return instance;
    }

    private HistoricAnthology2() {
        super("Historic Anthology 2", "HA2", ExpansionSet.buildDate(2020, 3, 12), SetType.MAGIC_ARENA);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Ancestral Mask", 13, Rarity.COMMON, mage.cards.a.AncestralMask.class));
        cards.add(new SetCardInfo("Barren Moor", 19, Rarity.COMMON, mage.cards.b.BarrenMoor.class));
        cards.add(new SetCardInfo("Bojuka Bog", 20, Rarity.COMMON, mage.cards.b.BojukaBog.class));
        cards.add(new SetCardInfo("Brain Maggot", 7, Rarity.UNCOMMON, mage.cards.b.BrainMaggot.class));
        cards.add(new SetCardInfo("Dragonmaster Outcast", 11, Rarity.MYTHIC, mage.cards.d.DragonmasterOutcast.class));
        cards.add(new SetCardInfo("Forgotten Cave", 21, Rarity.COMMON, mage.cards.f.ForgottenCave.class));
        cards.add(new SetCardInfo("Ghost Quarter", 22, Rarity.UNCOMMON, mage.cards.g.GhostQuarter.class));
        cards.add(new SetCardInfo("Goblin Ruinblaster", 12, Rarity.UNCOMMON, mage.cards.g.GoblinRuinblaster.class));
        cards.add(new SetCardInfo("Inexorable Tide", 6, Rarity.RARE, mage.cards.i.InexorableTide.class));
        cards.add(new SetCardInfo("Knight of the Reliquary", 15, Rarity.RARE, mage.cards.k.KnightOfTheReliquary.class));
        cards.add(new SetCardInfo("Lonely Sandbar", 23, Rarity.COMMON, mage.cards.l.LonelySandbar.class));
        cards.add(new SetCardInfo("Maelstrom Pulse", 16, Rarity.RARE, mage.cards.m.MaelstromPulse.class));
        cards.add(new SetCardInfo("Meddling Mage", 17, Rarity.RARE, mage.cards.m.MeddlingMage.class));
        cards.add(new SetCardInfo("Merrow Reejerey", 5, Rarity.UNCOMMON, mage.cards.m.MerrowReejerey.class));
        cards.add(new SetCardInfo("Nyx-Fleece Ram", 1, Rarity.UNCOMMON, mage.cards.n.NyxFleeceRam.class));
        cards.add(new SetCardInfo("Pack Rat", 8, Rarity.RARE, mage.cards.p.PackRat.class));
        cards.add(new SetCardInfo("Platinum Angel", 18, Rarity.MYTHIC, mage.cards.p.PlatinumAngel.class));
        cards.add(new SetCardInfo("Ranger of Eos", 2, Rarity.RARE, mage.cards.r.RangerOfEos.class));
        cards.add(new SetCardInfo("Secluded Steppe", 24, Rarity.COMMON, mage.cards.s.SecludedSteppe.class));
        cards.add(new SetCardInfo("Sigil of the Empty Throne", 3, Rarity.RARE, mage.cards.s.SigilOfTheEmptyThrone.class));
        cards.add(new SetCardInfo("Terravore", 14, Rarity.RARE, mage.cards.t.Terravore.class));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 4, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class));
        cards.add(new SetCardInfo("Tranquil Thicket", 25, Rarity.COMMON, mage.cards.t.TranquilThicket.class));
        cards.add(new SetCardInfo("Virulent Plague", 9, Rarity.UNCOMMON, mage.cards.v.VirulentPlague.class));
        cards.add(new SetCardInfo("Waste Not", 10, Rarity.RARE, mage.cards.w.WasteNot.class));
    }
}
