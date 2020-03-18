
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/s00
 * @author LevelX2
 */
public final class Starter2000 extends ExpansionSet {

    private static final Starter2000 instance = new Starter2000();

    public static Starter2000 getInstance() {
        return instance;
    }

    private Starter2000() {
        super("Starter 2000", "S00", ExpansionSet.buildDate(2000, 7, 1), SetType.SUPPLEMENTAL);
        this.blockName = "Beginner";
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Angelic Blessing", 1, Rarity.COMMON, mage.cards.a.AngelicBlessing.class));
        cards.add(new SetCardInfo("Breath of Life", 3, Rarity.UNCOMMON, mage.cards.b.BreathOfLife.class));
        cards.add(new SetCardInfo("Durkwood Boars", 38, Rarity.COMMON, mage.cards.d.DurkwoodBoars.class));
        cards.add(new SetCardInfo("Eager Cadet", 5, Rarity.COMMON, mage.cards.e.EagerCadet.class));
        cards.add(new SetCardInfo("Giant Octopus", 14, Rarity.COMMON, mage.cards.g.GiantOctopus.class));
        cards.add(new SetCardInfo("Hand of Death", 25, Rarity.COMMON, mage.cards.h.HandOfDeath.class));
        cards.add(new SetCardInfo("Knight Errant", 7, Rarity.COMMON, mage.cards.k.KnightErrant.class));
        cards.add(new SetCardInfo("Lava Axe", 31, Rarity.COMMON, mage.cards.l.LavaAxe.class));
        cards.add(new SetCardInfo("Mons's Goblin Raiders", 32, Rarity.COMMON, mage.cards.m.MonssGoblinRaiders.class));
        cards.add(new SetCardInfo("Monstrous Growth", 41, Rarity.COMMON, mage.cards.m.MonstrousGrowth.class));
        cards.add(new SetCardInfo("Moon Sprite", 42, Rarity.UNCOMMON, mage.cards.m.MoonSprite.class));
        cards.add(new SetCardInfo("Ogre Warrior", 33, Rarity.COMMON, mage.cards.o.OgreWarrior.class));
        cards.add(new SetCardInfo("Rhox", 43, Rarity.RARE, mage.cards.r.Rhox.class));
        cards.add(new SetCardInfo("Royal Falcon", 8, Rarity.COMMON, mage.cards.r.RoyalFalcon.class));
        cards.add(new SetCardInfo("Sea Eagle", 18, Rarity.COMMON, mage.cards.s.SeaEagle.class));
        cards.add(new SetCardInfo("Time Ebb", 19, Rarity.COMMON, mage.cards.t.TimeEbb.class));
        cards.add(new SetCardInfo("Trained Orgg", 37, Rarity.RARE, mage.cards.t.TrainedOrgg.class));
        cards.add(new SetCardInfo("Vizzerdrix", 20, Rarity.RARE, mage.cards.v.Vizzerdrix.class));
        cards.add(new SetCardInfo("Wild Griffin", 11, Rarity.COMMON, mage.cards.w.WildGriffin.class));
        cards.add(new SetCardInfo("Willow Elf", 45, Rarity.COMMON, mage.cards.w.WillowElf.class));

    }
}
