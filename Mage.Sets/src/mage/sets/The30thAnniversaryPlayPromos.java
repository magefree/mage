package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/p30a
 *
 * @author Jmlundeen
 */
public class The30thAnniversaryPlayPromos extends ExpansionSet {

    private static final The30thAnniversaryPlayPromos instance = new The30thAnniversaryPlayPromos();

    public static The30thAnniversaryPlayPromos getInstance() {
        return instance;
    }

    private The30thAnniversaryPlayPromos() {
        super("30th Anniversary Play Promos", "P30A", ExpansionSet.buildDate(2022, 9, 2), SetType.PROMOTIONAL);
        hasBasicLands = false;


        cards.add(new SetCardInfo("Acidic Slime", 17, Rarity.RARE, mage.cards.a.AcidicSlime.class, RETRO_ART));
        cards.add(new SetCardInfo("Ball Lightning", 2, Rarity.RARE, mage.cards.b.BallLightning.class, RETRO_ART));
        cards.add(new SetCardInfo("Beast Whisperer", 26, Rarity.RARE, mage.cards.b.BeastWhisperer.class, RETRO_ART));
        cards.add(new SetCardInfo("Chord of Calling", 13, Rarity.RARE, mage.cards.c.ChordOfCalling.class, RETRO_ART));
        cards.add(new SetCardInfo("Deadly Dispute", 29, Rarity.RARE, mage.cards.d.DeadlyDispute.class, RETRO_ART));
        cards.add(new SetCardInfo("Dovin's Veto", 27, Rarity.RARE, mage.cards.d.DovinsVeto.class, RETRO_ART));
        cards.add(new SetCardInfo("Dragonlord Atarka", 23, Rarity.MYTHIC, mage.cards.d.DragonlordAtarka.class, RETRO_ART));
        cards.add(new SetCardInfo("Dramatic Reversal", 24, Rarity.RARE, mage.cards.d.DramaticReversal.class, RETRO_ART));
        cards.add(new SetCardInfo("Eternal Witness", 12, Rarity.RARE, mage.cards.e.EternalWitness.class, RETRO_ART));
        cards.add(new SetCardInfo("Exalted Angel", 10, Rarity.RARE, mage.cards.e.ExaltedAngel.class, RETRO_ART));
        cards.add(new SetCardInfo("Fyndhorn Elves", 3, Rarity.RARE, mage.cards.f.FyndhornElves.class, RETRO_ART));
        cards.add(new SetCardInfo("Glen Elendra Archmage", 16, Rarity.RARE, mage.cards.g.GlenElendraArchmage.class, RETRO_ART));
        cards.add(new SetCardInfo("Goblin Rabblemaster", 22, Rarity.RARE, mage.cards.g.GoblinRabblemaster.class, RETRO_ART));
        cards.add(new SetCardInfo("Harvester of Souls", 20, Rarity.RARE, mage.cards.h.HarvesterOfSouls.class, RETRO_ART));
        cards.add(new SetCardInfo("Hornet Queen", 19, Rarity.RARE, mage.cards.h.HornetQueen.class, RETRO_ART));
        cards.add(new SetCardInfo("Kalonian Hydra", 21, Rarity.MYTHIC, mage.cards.k.KalonianHydra.class, RETRO_ART));
        cards.add(new SetCardInfo("Kor Haven", 8, Rarity.RARE, mage.cards.k.KorHaven.class, RETRO_ART));
        cards.add(new SetCardInfo("Loyal Retainers", 7, Rarity.RARE, mage.cards.l.LoyalRetainers.class, RETRO_ART));
        cards.add(new SetCardInfo("Niv-Mizzet, the Firemind", 14, Rarity.RARE, mage.cards.n.NivMizzetTheFiremind.class, RETRO_ART));
        cards.add(new SetCardInfo("Path of Ancestry", 25, Rarity.RARE, mage.cards.p.PathOfAncestry.class, RETRO_ART));
        cards.add(new SetCardInfo("Serra Angel", 1, Rarity.RARE, mage.cards.s.SerraAngel.class, RETRO_ART));
        cards.add(new SetCardInfo("Tarmogoyf", 15, Rarity.RARE, mage.cards.t.Tarmogoyf.class));
        cards.add(new SetCardInfo("Temple of the False God", 11, Rarity.RARE, mage.cards.t.TempleOfTheFalseGod.class, RETRO_ART));
        cards.add(new SetCardInfo("Terastodon", 18, Rarity.RARE, mage.cards.t.Terastodon.class, RETRO_ART));
        cards.add(new SetCardInfo("Urza, Prince of Kroog", 30, Rarity.RARE, mage.cards.u.UrzaPrinceOfKroog.class, RETRO_ART));
        cards.add(new SetCardInfo("Vindicate", 9, Rarity.RARE, mage.cards.v.Vindicate.class, RETRO_ART));
        cards.add(new SetCardInfo("Vito, Thorn of the Dusk Rose", 28, Rarity.RARE, mage.cards.v.VitoThornOfTheDuskRose.class, RETRO_ART));
        cards.add(new SetCardInfo("Wall of Roots", 4, Rarity.RARE, mage.cards.w.WallOfRoots.class, RETRO_ART));
        cards.add(new SetCardInfo("Windfall", 6, Rarity.RARE, mage.cards.w.Windfall.class, RETRO_ART));
        cards.add(new SetCardInfo("Wood Elves", 5, Rarity.RARE, mage.cards.w.WoodElves.class, RETRO_ART));
    }
}
