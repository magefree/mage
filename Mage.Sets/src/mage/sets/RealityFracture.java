package mage.sets;

import java.util.Arrays;
import java.util.List;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author muz
 */
public final class RealityFracture extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Bloodline Recollector", "Paradox Shaper", "Prudent Fateseer", "Stingerquill Voxmancer", "Vigorbloom Vanguard", "Woodwork Prodigy");
    private static final RealityFracture instance = new RealityFracture();

    public static RealityFracture getInstance() {
        return instance;
    }

    private RealityFracture() {
        super("Reality Fracture", "FRA", ExpansionSet.buildDate(2026, 10, 2), SetType.EXPANSION);
        this.blockName = "Reality Fracture"; // for sorting in GUI
        this.hasBasicLands = false; // TODO: Enable once basic collector numbers are known

        // this.enablePlayBooster(305); TODO: Enable later

        cards.add(new SetCardInfo("Ajani Resolute", 195, Rarity.MYTHIC, mage.cards.a.AjaniResolute.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Ajani Resolute", 291, Rarity.MYTHIC, mage.cards.a.AjaniResolute.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodline Recollector", 402, Rarity.MYTHIC, mage.cards.b.BloodlineRecollector.class, FULL_ART_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodline Recollector", 427, Rarity.MYTHIC, mage.cards.b.BloodlineRecollector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bloodline Recollector", 49, Rarity.MYTHIC, mage.cards.b.BloodlineRecollector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra, Chill of Compliance", 212, Rarity.MYTHIC, mage.cards.c.ChandraChillOfCompliance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra, Chill of Compliance", 297, Rarity.MYTHIC, mage.cards.c.ChandraChillOfCompliance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra, Torch of Defiance", 244, Rarity.MYTHIC, mage.cards.c.ChandraTorchOfDefiance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra, Torch of Defiance", 309, Rarity.MYTHIC, mage.cards.c.ChandraTorchOfDefiance.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Craterclaw Colossus", 327, Rarity.MYTHIC, mage.cards.c.CraterclawColossus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Craterclaw Colossus", 446, Rarity.MYTHIC, mage.cards.c.CraterclawColossus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Craterclaw Colossus", 455, Rarity.MYTHIC, mage.cards.c.CraterclawColossus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Craterclaw Colossus", 78, Rarity.MYTHIC, mage.cards.c.CraterclawColossus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana the Faultless", 200, Rarity.RARE, mage.cards.l.LilianaTheFaultless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana the Faultless", 293, Rarity.RARE, mage.cards.l.LilianaTheFaultless.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana the Repentant", 231, Rarity.RARE, mage.cards.l.LilianaTheRepentant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Liliana the Repentant", 305, Rarity.RARE, mage.cards.l.LilianaTheRepentant.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Prudent Fateseer", 146, Rarity.UNCOMMON, mage.cards.p.PrudentFateseer.class));
        cards.add(new SetCardInfo("Stingcaster Mage", 329, Rarity.MYTHIC, mage.cards.s.StingcasterMage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stingcaster Mage", 447, Rarity.MYTHIC, mage.cards.s.StingcasterMage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stingcaster Mage", 457, Rarity.MYTHIC, mage.cards.s.StingcasterMage.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Stingcaster Mage", 93, Rarity.MYTHIC, mage.cards.s.StingcasterMage.class, NON_FULL_USE_VARIOUS));  
        cards.add(new SetCardInfo("The Theorist, Jace Beleren", 363, Rarity.MYTHIC, mage.cards.t.TheTheoristJaceBeleren.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Theorist, Jace Beleren", 405, Rarity.MYTHIC, mage.cards.t.TheTheoristJaceBeleren.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Theorist, Jace Beleren", 415, Rarity.MYTHIC, mage.cards.t.TheTheoristJaceBeleren.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Theorist, Jace Beleren", 43, Rarity.MYTHIC, mage.cards.t.TheTheoristJaceBeleren.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Theorist, Jace Beleren", 443, Rarity.MYTHIC, mage.cards.t.TheTheoristJaceBeleren.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tinybones, Pocket Nuisance", 237, Rarity.UNCOMMON, mage.cards.t.TinybonesPocketNuisance.class));
        cards.add(new SetCardInfo("Titanbones, Towering Heart", 266, Rarity.UNCOMMON, mage.cards.t.TitanbonesToweringHeart.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName()));
    }
}
