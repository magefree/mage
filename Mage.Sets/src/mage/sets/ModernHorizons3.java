package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class ModernHorizons3 extends ExpansionSet {

    private static final ModernHorizons3 instance = new ModernHorizons3();

    public static ModernHorizons3 getInstance() {
        return instance;
    }

    private ModernHorizons3() {
        super("Modern Horizons 3", "MH3", ExpansionSet.buildDate(2024, 6, 7), SetType.SUPPLEMENTAL_MODERN_LEGAL);
        this.blockName = "Modern Horizons 3";
        this.hasBasicLands = true;
        this.hasBoosters = false; // temporary

        cards.add(new SetCardInfo("Ajani, Nacatl Avenger", 237, Rarity.MYTHIC, mage.cards.a.AjaniNacatlAvenger.class));
        cards.add(new SetCardInfo("Ajani, Nacatl Pariah", 237, Rarity.MYTHIC, mage.cards.a.AjaniNacatlPariah.class));
        cards.add(new SetCardInfo("Bloodstained Mire", 216, Rarity.RARE, mage.cards.b.BloodstainedMire.class));
        cards.add(new SetCardInfo("Emrakul, the World Anew", 6, Rarity.MYTHIC, mage.cards.e.EmrakulTheWorldAnew.class));
        cards.add(new SetCardInfo("Flare of Cultivation", 154, Rarity.RARE, mage.cards.f.FlareOfCultivation.class));
        cards.add(new SetCardInfo("Flare of Denial", 62, Rarity.RARE, mage.cards.f.FlareOfDenial.class));
        cards.add(new SetCardInfo("Flooded Strand", 220, Rarity.RARE, mage.cards.f.FloodedStrand.class));
        cards.add(new SetCardInfo("Forest", 308, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Island", 305, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("It That Heralds the End", 9, Rarity.UNCOMMON, mage.cards.i.ItThatHeraldsTheEnd.class));
        cards.add(new SetCardInfo("Kappa Cannoneer", 270, Rarity.RARE, mage.cards.k.KappaCannoneer.class));
        cards.add(new SetCardInfo("Kudo, King Among Bears", 192, Rarity.RARE, mage.cards.k.KudoKingAmongBears.class));
        cards.add(new SetCardInfo("Laelia, the Blade Reforged", 281, Rarity.RARE, mage.cards.l.LaeliaTheBladeReforged.class));
        cards.add(new SetCardInfo("Meltdown", 282, Rarity.UNCOMMON, mage.cards.m.Meltdown.class));
        cards.add(new SetCardInfo("Mountain", 307, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Null Elemental Blast", 12, Rarity.UNCOMMON, mage.cards.n.NullElementalBlast.class));
        cards.add(new SetCardInfo("Nulldrifter", 13, Rarity.RARE, mage.cards.n.Nulldrifter.class));
        cards.add(new SetCardInfo("Orim's Chant", 265, Rarity.RARE, mage.cards.o.OrimsChant.class));
        cards.add(new SetCardInfo("Plains", 304, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Polluted Delta", 224, Rarity.RARE, mage.cards.p.PollutedDelta.class));
        cards.add(new SetCardInfo("Priest of Titania", 286, Rarity.UNCOMMON, mage.cards.p.PriestOfTitania.class));
        cards.add(new SetCardInfo("Psychic Frog", 199, Rarity.RARE, mage.cards.p.PsychicFrog.class));
        cards.add(new SetCardInfo("Scurry of Gremlins", 203, Rarity.UNCOMMON, mage.cards.s.ScurryOfGremlins.class));
        cards.add(new SetCardInfo("Snow-Covered Wastes", 229, Rarity.UNCOMMON, mage.cards.s.SnowCoveredWastes.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Spawn-Gang Commander", 140, Rarity.UNCOMMON, mage.cards.s.SpawnGangCommander.class));
        cards.add(new SetCardInfo("Swamp", 306, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Sylvan Safekeeper", 287, Rarity.RARE, mage.cards.s.SylvanSafekeeper.class));
        cards.add(new SetCardInfo("Urza's Cave", 234, Rarity.UNCOMMON, mage.cards.u.UrzasCave.class));
        cards.add(new SetCardInfo("Windswept Heath", 235, Rarity.RARE, mage.cards.w.WindsweptHeath.class));
        cards.add(new SetCardInfo("Winter Moon", 213, Rarity.RARE, mage.cards.w.WinterMoon.class));
        cards.add(new SetCardInfo("Wooded Foothills", 236, Rarity.RARE, mage.cards.w.WoodedFoothills.class));
    }
}
