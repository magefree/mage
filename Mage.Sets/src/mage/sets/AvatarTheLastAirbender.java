package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class AvatarTheLastAirbender extends ExpansionSet {

    private static final AvatarTheLastAirbender instance = new AvatarTheLastAirbender();

    public static AvatarTheLastAirbender getInstance() {
        return instance;
    }

    private AvatarTheLastAirbender() {
        super("Avatar: The Last Airbender", "TLA", ExpansionSet.buildDate(2025, 11, 21), SetType.EXPANSION);
        this.blockName = "Avatar: The Last Airbender"; // for sorting in GUI
        this.rotationSet = true;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Appa, Steadfast Guardian", 10, Rarity.MYTHIC, mage.cards.a.AppaSteadfastGuardian.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Appa, Steadfast Guardian", 316, Rarity.MYTHIC, mage.cards.a.AppaSteadfastGuardian.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Avatar Enthusiasts", 11, Rarity.COMMON, mage.cards.a.AvatarEnthusiasts.class));
        cards.add(new SetCardInfo("Earthbending Lesson", 176, Rarity.COMMON, mage.cards.e.EarthbendingLesson.class));
        cards.add(new SetCardInfo("Fire Nation Attacks", 133, Rarity.UNCOMMON, mage.cards.f.FireNationAttacks.class));
        cards.add(new SetCardInfo("Forest", 291, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 288, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Katara, the Fearless", 230, Rarity.RARE, mage.cards.k.KataraTheFearless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Katara, the Fearless", 350, Rarity.RARE, mage.cards.k.KataraTheFearless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Katara, the Fearless", 361, Rarity.RARE, mage.cards.k.KataraTheFearless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 290, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 287, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Redirect Lightning", 151, Rarity.RARE, mage.cards.r.RedirectLightning.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Redirect Lightning", 343, Rarity.RARE, mage.cards.r.RedirectLightning.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sokka's Haiku", 71, Rarity.UNCOMMON, mage.cards.s.SokkasHaiku.class));
        cards.add(new SetCardInfo("Sokka, Bold Boomeranger", 240, Rarity.RARE, mage.cards.s.SokkaBoldBoomeranger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sokka, Bold Boomeranger", 383, Rarity.RARE, mage.cards.s.SokkaBoldBoomeranger.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Southern Air Temple", 36, Rarity.UNCOMMON, mage.cards.s.SouthernAirTemple.class));
        cards.add(new SetCardInfo("Swamp", 289, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
    }
}
