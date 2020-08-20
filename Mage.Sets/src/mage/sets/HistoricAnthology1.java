package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/ha1
 *
 * @author mikalinn777
 */
public final class HistoricAnthology1 extends ExpansionSet {

    private static final HistoricAnthology1 instance = new HistoricAnthology1();

    public static HistoricAnthology1 getInstance() {
        return instance;
    }

    private HistoricAnthology1() {
        super("Historic Anthology 1", "HA1", ExpansionSet.buildDate(2019, 11, 21), SetType.MAGIC_ARENA);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Burning-Tree Emissary", 16, Rarity.UNCOMMON, mage.cards.b.BurningTreeEmissary.class));
        cards.add(new SetCardInfo("Captain Sisay", 17, Rarity.RARE, mage.cards.c.CaptainSisay.class));
        cards.add(new SetCardInfo("Cryptbreaker", 6, Rarity.RARE, mage.cards.c.Cryptbreaker.class));
        cards.add(new SetCardInfo("Darksteel Reactor", 20, Rarity.RARE, mage.cards.d.DarksteelReactor.class));
        cards.add(new SetCardInfo("Distant Melody", 5, Rarity.COMMON, mage.cards.d.DistantMelody.class));
        cards.add(new SetCardInfo("Elvish Visionary", 13, Rarity.COMMON, mage.cards.e.ElvishVisionary.class));
        cards.add(new SetCardInfo("Fauna Shaman", 14, Rarity.RARE, mage.cards.f.FaunaShaman.class));
        cards.add(new SetCardInfo("Goblin Matron", 11, Rarity.UNCOMMON, mage.cards.g.GoblinMatron.class));
        cards.add(new SetCardInfo("Hidetsugu's Second Rite", 12, Rarity.RARE, mage.cards.h.HidetsugusSecondRite.class));
        cards.add(new SetCardInfo("Hypnotic Specter", 7, Rarity.RARE, mage.cards.h.HypnoticSpecter.class));
        cards.add(new SetCardInfo("Imperious Perfect", 15, Rarity.UNCOMMON, mage.cards.i.ImperiousPerfect.class));
        cards.add(new SetCardInfo("Kiln Fiend", 10, Rarity.COMMON, mage.cards.k.KilnFiend.class));
        cards.add(new SetCardInfo("Kinsbaile Cavalier", 3, Rarity.RARE, mage.cards.k.KinsbaileCavalier.class));
        cards.add(new SetCardInfo("Mind Stone", 19, Rarity.COMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Ornithopter", 18, Rarity.UNCOMMON, mage.cards.o.Ornithopter.class));
        cards.add(new SetCardInfo("Phyrexian Arena", 8, Rarity.RARE, mage.cards.p.PhyrexianArena.class));
        cards.add(new SetCardInfo("Serra Ascendant", 1, Rarity.RARE, mage.cards.s.SerraAscendant.class));
        cards.add(new SetCardInfo("Soul Warden", 2, Rarity.COMMON, mage.cards.s.SoulWarden.class));
        cards.add(new SetCardInfo("Tendrils of Corruption", 9, Rarity.COMMON, mage.cards.t.TendrilsOfCorruption.class));
        cards.add(new SetCardInfo("Treasure Hunt", 4, Rarity.COMMON, mage.cards.t.TreasureHunt.class));
    }
}
