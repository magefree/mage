
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 *
 * @author fireshoes
 */
public final class ArenaLeague extends ExpansionSet {

    private static final ArenaLeague instance = new ArenaLeague();

    public static ArenaLeague getInstance() {
        return instance;
    }

    private ArenaLeague() {
        super("Arena League", "ARENA", ExpansionSet.buildDate(1996, 7, 4), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        cards.add(new SetCardInfo("Arc Lightning", 42, Rarity.COMMON, mage.cards.a.ArcLightning.class));
        cards.add(new SetCardInfo("Bonesplitter", 52, Rarity.COMMON, mage.cards.b.Bonesplitter.class));
        cards.add(new SetCardInfo("Castigate", 80, Rarity.COMMON, mage.cards.c.Castigate.class));
        cards.add(new SetCardInfo("Chill", 19, Rarity.SPECIAL, mage.cards.c.Chill.class));
        cards.add(new SetCardInfo("Coiling Oracle", 82, Rarity.COMMON, mage.cards.c.CoilingOracle.class));
        cards.add(new SetCardInfo("Creeping Mold", 28, Rarity.SPECIAL, mage.cards.c.CreepingMold.class));
        cards.add(new SetCardInfo("Darksteel Ingot", 58, Rarity.COMMON, mage.cards.d.DarksteelIngot.class));
        cards.add(new SetCardInfo("Dauthi Slayer", 43, Rarity.SPECIAL, mage.cards.d.DauthiSlayer.class));
        cards.add(new SetCardInfo("Diabolic Edict", 37, Rarity.COMMON, mage.cards.d.DiabolicEdict.class));
        cards.add(new SetCardInfo("Disenchant", 6, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Dismiss", 29, Rarity.SPECIAL, mage.cards.d.Dismiss.class));
        cards.add(new SetCardInfo("Duress", 17, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Elvish Aberration", 51, Rarity.COMMON, mage.cards.e.ElvishAberration.class));
        cards.add(new SetCardInfo("Empyrial Armor", 31, Rarity.SPECIAL, mage.cards.e.EmpyrialArmor.class));
        cards.add(new SetCardInfo("Enlightened Tutor", 21, Rarity.SPECIAL, mage.cards.e.EnlightenedTutor.class));
        cards.add(new SetCardInfo("Fireball", 7, Rarity.SPECIAL, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Fling", 30, Rarity.COMMON, mage.cards.f.Fling.class));
        cards.add(new SetCardInfo("Forest", 5, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 12, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 27, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 36, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 40, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 49, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 57, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 71, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 79, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gaea's Blessing", 38, Rarity.SPECIAL, mage.cards.g.GaeasBlessing.class));
        cards.add(new SetCardInfo("Genju of the Spires", 72, Rarity.SPECIAL, mage.cards.g.GenjuOfTheSpires.class));
        cards.add(new SetCardInfo("Glacial Ray", 60, Rarity.COMMON, mage.cards.g.GlacialRay.class));
        cards.add(new SetCardInfo("Island", 2, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 9, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 24, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 33, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 39, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 46, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 54, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 68, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 76, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karn, Silver Golem", 16, Rarity.SPECIAL, mage.cards.k.KarnSilverGolem.class));
        cards.add(new SetCardInfo("Mana Leak", 44, Rarity.COMMON, mage.cards.m.ManaLeak.class));
        cards.add(new SetCardInfo("Man-o'-War", 41, Rarity.COMMON, mage.cards.m.ManOWar.class));
        cards.add(new SetCardInfo("Mountain", 4, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 11, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 26, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 35, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 48, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 56, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 70, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 78, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Okina Nightwatch", 73, Rarity.COMMON, mage.cards.o.OkinaNightwatch.class));
        cards.add(new SetCardInfo("Pillage", 20, Rarity.SPECIAL, mage.cards.p.Pillage.class));
        cards.add(new SetCardInfo("Plains", 1, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 8, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 23, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 32, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 45, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 53, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 67, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 75, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pouncing Jaguar", 13, Rarity.COMMON, mage.cards.p.PouncingJaguar.class));
        cards.add(new SetCardInfo("Rewind", 15, Rarity.COMMON, mage.cards.r.Rewind.class));
        cards.add(new SetCardInfo("Serum Visions", 59, Rarity.COMMON, mage.cards.s.SerumVisions.class));
        cards.add(new SetCardInfo("Skirk Marauder", 50, Rarity.COMMON, mage.cards.s.SkirkMarauder.class));
        cards.add(new SetCardInfo("Skittering Skirge", 14, Rarity.COMMON, mage.cards.s.SkitteringSkirge.class));
        cards.add(new SetCardInfo("Skyknight Legionnaire", 74, Rarity.COMMON, mage.cards.s.SkyknightLegionnaire.class));
        cards.add(new SetCardInfo("Stupor", 22, Rarity.COMMON, mage.cards.s.Stupor.class));
        cards.add(new SetCardInfo("Surging Flame", 83, Rarity.COMMON, mage.cards.s.SurgingFlame.class));
        cards.add(new SetCardInfo("Swamp", 3, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 10, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 25, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 34, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 47, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 55, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 69, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 77, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Uktabi Orangutan", 18, Rarity.SPECIAL, mage.cards.u.UktabiOrangutan.class));
        cards.add(new SetCardInfo("Wee Dragonauts", 81, Rarity.COMMON, mage.cards.w.WeeDragonauts.class));
    }
}
