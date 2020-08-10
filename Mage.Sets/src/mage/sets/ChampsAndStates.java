package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pcmp
 * https://mtg.gamepedia.com/Magic_Champs_and_States
 */
public class ChampsAndStates extends ExpansionSet {

    private static final ChampsAndStates instance = new ChampsAndStates();

    public static ChampsAndStates getInstance() {
        return instance;
    }

    private ChampsAndStates() {
        super("Champs and States", "PCMP", ExpansionSet.buildDate(2008, 3, 29), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Blood Knight", 7, Rarity.RARE, mage.cards.b.BloodKnight.class));
        cards.add(new SetCardInfo("Bramblewood Paragon", 11, Rarity.RARE, mage.cards.b.BramblewoodParagon.class));
        cards.add(new SetCardInfo("Doran, the Siege Tower", 10, Rarity.RARE, mage.cards.d.DoranTheSiegeTower.class));
        cards.add(new SetCardInfo("Electrolyze", 1, Rarity.RARE, mage.cards.e.Electrolyze.class));
        cards.add(new SetCardInfo("Groundbreaker", 8, Rarity.RARE, mage.cards.g.Groundbreaker.class));
        cards.add(new SetCardInfo("Imperious Perfect", 9, Rarity.RARE, mage.cards.i.ImperiousPerfect.class));
        cards.add(new SetCardInfo("Mutavault", 12, Rarity.RARE, mage.cards.m.Mutavault.class));
        cards.add(new SetCardInfo("Niv-Mizzet, the Firemind", 2, Rarity.RARE, mage.cards.n.NivMizzetTheFiremind.class));
        cards.add(new SetCardInfo("Rakdos Guildmage", 3, Rarity.RARE, mage.cards.r.RakdosGuildmage.class));
        cards.add(new SetCardInfo("Serra Avenger", 6, Rarity.RARE, mage.cards.s.SerraAvenger.class));
        cards.add(new SetCardInfo("Urza's Factory", 5, Rarity.RARE, mage.cards.u.UrzasFactory.class));
        cards.add(new SetCardInfo("Voidslime", 4, Rarity.RARE, mage.cards.v.Voidslime.class));
     }
}
