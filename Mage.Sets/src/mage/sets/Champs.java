
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author fireshoes
 */
public final class Champs extends ExpansionSet {

    private static final Champs instance = new Champs();

    public static Champs getInstance() {
        return instance;
    }

    private Champs() {
        super("Champs", "CP", ExpansionSet.buildDate(2006, 3, 18), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Blood Knight", 7, Rarity.SPECIAL, mage.cards.b.BloodKnight.class));
        cards.add(new SetCardInfo("Bramblewood Paragon", 11, Rarity.SPECIAL, mage.cards.b.BramblewoodParagon.class));
        cards.add(new SetCardInfo("Doran, the Siege Tower", 10, Rarity.SPECIAL, mage.cards.d.DoranTheSiegeTower.class));
        cards.add(new SetCardInfo("Electrolyze", 1, Rarity.SPECIAL, mage.cards.e.Electrolyze.class));
        cards.add(new SetCardInfo("Groundbreaker", 8, Rarity.SPECIAL, mage.cards.g.Groundbreaker.class));
        cards.add(new SetCardInfo("Imperious Perfect", 9, Rarity.SPECIAL, mage.cards.i.ImperiousPerfect.class));
        cards.add(new SetCardInfo("Mutavault", 12, Rarity.SPECIAL, mage.cards.m.Mutavault.class));
        cards.add(new SetCardInfo("Niv-Mizzet, the Firemind", 2, Rarity.SPECIAL, mage.cards.n.NivMizzetTheFiremind.class));
        cards.add(new SetCardInfo("Rakdos Guildmage", 3, Rarity.SPECIAL, mage.cards.r.RakdosGuildmage.class));
        cards.add(new SetCardInfo("Serra Avenger", 6, Rarity.SPECIAL, mage.cards.s.SerraAvenger.class));
        cards.add(new SetCardInfo("Urza's Factory", 5, Rarity.SPECIAL, mage.cards.u.UrzasFactory.class));
        cards.add(new SetCardInfo("Voidslime", 4, Rarity.SPECIAL, mage.cards.v.Voidslime.class));
    }
}
