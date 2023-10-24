
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author Susucr
 */
public final class LostCavernsOfIxalan extends ExpansionSet {

    private static final LostCavernsOfIxalan instance = new LostCavernsOfIxalan();

    public static LostCavernsOfIxalan getInstance() {
        return instance;
    }

    private LostCavernsOfIxalan() {
        super("Lost Caverns of Ixalan", "LCI", ExpansionSet.buildDate(2023, 11, 17), SetType.EXPANSION);
        this.hasBoosters = false; // TODO: enable boosters
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Breeches, Eager Pillager", 137, Rarity.RARE, mage.cards.b.BreechesEagerPillager.class));
        cards.add(new SetCardInfo("Cavern of Souls", 269, Rarity.MYTHIC, mage.cards.c.CavernOfSouls.class));
        cards.add(new SetCardInfo("Cenote Scout", 178, Rarity.UNCOMMON, mage.cards.c.CenoteScout.class));
        cards.add(new SetCardInfo("Forest", 291, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Ghalta, Stampede Tyrant", 185, Rarity.MYTHIC, mage.cards.g.GhaltaStampedeTyrant.class));
        cards.add(new SetCardInfo("Huatli, Poet of Unity", 189, Rarity.MYTHIC, mage.cards.h.HuatliPoetOfUnity.class));
        cards.add(new SetCardInfo("Island", 288, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Kellan, Daring Traveler", 231, Rarity.RARE, mage.cards.k.KellanDaringTraveler.class));
        cards.add(new SetCardInfo("Mountain", 290, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Ojer Axonil, Deepest Might", 317, Rarity.MYTHIC, mage.cards.o.OjerAxonilDeepestMight.class));
        cards.add(new SetCardInfo("Plains", 287, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Roar of the Fifth People", 189, Rarity.MYTHIC, mage.cards.r.RoarOfTheFifthPeople.class));
        cards.add(new SetCardInfo("Spyglass Siren", 78, Rarity.UNCOMMON, mage.cards.s.SpyglassSiren.class));
        cards.add(new SetCardInfo("Swamp", 289, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Temple of Power", 317, Rarity.MYTHIC, mage.cards.t.TempleOfPower.class));
        cards.add(new SetCardInfo("The Skullspore Nexus", 212, Rarity.MYTHIC, mage.cards.t.TheSkullsporeNexus.class));
    }
}
