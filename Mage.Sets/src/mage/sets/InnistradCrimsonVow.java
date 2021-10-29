package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class InnistradCrimsonVow extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Dorothea, Vengeful Victim", "Dorothea's Retribution", "Drogskol Infantry", "Drogskol Armaments", "Volatile Arsonist", "Dire-Strain Anarchist", "Weary Prisoner", "Wrathful Jailbreaker");
    private static final InnistradCrimsonVow instance = new InnistradCrimsonVow();

    public static InnistradCrimsonVow getInstance() {
        return instance;
    }

    private InnistradCrimsonVow() {
        super("Innistrad: Crimson Vow", "VOW", ExpansionSet.buildDate(2021, 11, 19), SetType.EXPANSION);
        this.blockName = "Innistrad: Midnight Hunt";
        this.hasBoosters = true;
        this.hasBasicLands = true;
        this.numBoosterLands = 1;
        this.numBoosterCommon = 9;
        this.numBoosterUncommon = 3;
        this.numBoosterRare = 1;
        this.ratioBoosterMythic = 8;
        this.numBoosterDoubleFaced = 1;

        cards.add(new SetCardInfo("Apprentice Sharpshooter", 185, Rarity.COMMON, mage.cards.a.ApprenticeSharpshooter.class));
        cards.add(new SetCardInfo("Deathcap Glade", 261, Rarity.RARE, mage.cards.d.DeathcapGlade.class));
        cards.add(new SetCardInfo("Dig Up", 197, Rarity.RARE, mage.cards.d.DigUp.class));
        cards.add(new SetCardInfo("Dreamroot Cascade", 262, Rarity.RARE, mage.cards.d.DreamrootCascade.class));
        cards.add(new SetCardInfo("Forest", 276, Rarity.LAND, mage.cards.basiclands.Forest.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Gluttonous Guest", 114, Rarity.COMMON, mage.cards.g.GluttonousGuest.class));
        cards.add(new SetCardInfo("Gryff Rider", 15, Rarity.COMMON, mage.cards.g.GryffRider.class));
        cards.add(new SetCardInfo("Island", 270, Rarity.LAND, mage.cards.basiclands.Island.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Massive Might", 208, Rarity.COMMON, mage.cards.m.MassiveMight.class));
        cards.add(new SetCardInfo("Mountain", 274, Rarity.LAND, mage.cards.basiclands.Mountain.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Plains", 268, Rarity.LAND, mage.cards.basiclands.Plains.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Shattered Sanctum", 264, Rarity.RARE, mage.cards.s.ShatteredSanctum.class));
        cards.add(new SetCardInfo("Stormcarved Coast", 265, Rarity.RARE, mage.cards.s.StormcarvedCoast.class));
        cards.add(new SetCardInfo("Sundown Pass", 266, Rarity.RARE, mage.cards.s.SundownPass.class));
        cards.add(new SetCardInfo("Swamp", 272, Rarity.LAND, mage.cards.basiclands.Swamp.class, FULL_ART_BFZ_VARIOUS));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 38, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class));
        cards.add(new SetCardInfo("Weary Prisoner", 184, Rarity.COMMON, mage.cards.w.WearyPrisoner.class));
        cards.add(new SetCardInfo("Wrathful Jailbreaker", 184, Rarity.COMMON, mage.cards.w.WrathfulJailbreaker.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName())); // remove when mechanic is fully implemented
    }
}
