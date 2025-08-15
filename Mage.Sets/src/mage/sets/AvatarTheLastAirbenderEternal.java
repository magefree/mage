package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

import java.util.Arrays;
import java.util.List;

/**
 * @author TheElk801
 */
public final class AvatarTheLastAirbenderEternal extends ExpansionSet {

    private static final List<String> unfinished = Arrays.asList("Water Whip");

    private static final AvatarTheLastAirbenderEternal instance = new AvatarTheLastAirbenderEternal();

    public static AvatarTheLastAirbenderEternal getInstance() {
        return instance;
    }

    private AvatarTheLastAirbenderEternal() {
        super("Avatar: The Last Airbender Eternal", "TLE", ExpansionSet.buildDate(2025, 11, 21), SetType.SUPPLEMENTAL);
        this.blockName = "Avatar: The Last Airbender"; // for sorting in GUI
        this.rotationSet = true;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Aang's Defense", 211, Rarity.COMMON, mage.cards.a.AangsDefense.class));
        cards.add(new SetCardInfo("Aang, Air Nomad", 210, Rarity.RARE, mage.cards.a.AangAirNomad.class));
        cards.add(new SetCardInfo("Aang, Airbending Master", 74, Rarity.MYTHIC, mage.cards.a.AangAirbendingMaster.class));
        cards.add(new SetCardInfo("Aardvark Sloth", 212, Rarity.COMMON, mage.cards.a.AardvarkSloth.class));
        cards.add(new SetCardInfo("Allied Teamwork", 213, Rarity.RARE, mage.cards.a.AlliedTeamwork.class));
        cards.add(new SetCardInfo("Appa, Aang's Companion", 214, Rarity.UNCOMMON, mage.cards.a.AppaAangsCompanion.class));
        cards.add(new SetCardInfo("Bumi, Eclectic Earthbender", 248, Rarity.RARE, mage.cards.b.BumiEclecticEarthbender.class));
        cards.add(new SetCardInfo("Capital Guard", 234, Rarity.COMMON, mage.cards.c.CapitalGuard.class));
        cards.add(new SetCardInfo("Deny Entry", 222, Rarity.COMMON, mage.cards.d.DenyEntry.class));
        cards.add(new SetCardInfo("Dragon Moose", 235, Rarity.COMMON, mage.cards.d.DragonMoose.class));
        cards.add(new SetCardInfo("Eel-Hounds", 250, Rarity.UNCOMMON, mage.cards.e.EelHounds.class));
        cards.add(new SetCardInfo("Elephant-Rat", 228, Rarity.COMMON, mage.cards.e.ElephantRat.class));
        cards.add(new SetCardInfo("Explore", 259, Rarity.COMMON, mage.cards.e.Explore.class));
        cards.add(new SetCardInfo("Explosive Shot", 236, Rarity.COMMON, mage.cards.e.ExplosiveShot.class));
        cards.add(new SetCardInfo("Feed the Swarm", 257, Rarity.COMMON, mage.cards.f.FeedTheSwarm.class));
        cards.add(new SetCardInfo("Fire Nation Ambushers", 229, Rarity.COMMON, mage.cards.f.FireNationAmbushers.class));
        cards.add(new SetCardInfo("Fire Nation Soldier", 238, Rarity.COMMON, mage.cards.f.FireNationSoldier.class));
        cards.add(new SetCardInfo("Force of Negation", 13, Rarity.MYTHIC, mage.cards.f.ForceOfNegation.class));
        cards.add(new SetCardInfo("Katara, Waterbending Master", 93, Rarity.MYTHIC, mage.cards.k.KataraWaterbendingMaster.class));
        cards.add(new SetCardInfo("Run Amok", 258, Rarity.COMMON, mage.cards.r.RunAmok.class));
        cards.add(new SetCardInfo("The Cabbage Merchant", 134, Rarity.RARE, mage.cards.t.TheCabbageMerchant.class));
        cards.add(new SetCardInfo("The Great Henge", 41, Rarity.MYTHIC, mage.cards.t.TheGreatHenge.class));
        cards.add(new SetCardInfo("The Terror of Serpent's Pass", 225, Rarity.RARE, mage.cards.t.TheTerrorOfSerpentsPass.class));
        cards.add(new SetCardInfo("Thriving Bluff", 260, Rarity.COMMON, mage.cards.t.ThrivingBluff.class));
        cards.add(new SetCardInfo("Thriving Grove", 261, Rarity.COMMON, mage.cards.t.ThrivingGrove.class));
        cards.add(new SetCardInfo("Thriving Heath", 262, Rarity.COMMON, mage.cards.t.ThrivingHeath.class));
        cards.add(new SetCardInfo("Thriving Isle", 263, Rarity.COMMON, mage.cards.t.ThrivingIsle.class));
        cards.add(new SetCardInfo("Thriving Moor", 264, Rarity.COMMON, mage.cards.t.ThrivingMoor.class));
        cards.add(new SetCardInfo("Tundra Wall", 220, Rarity.COMMON, mage.cards.t.TundraWall.class));
        cards.add(new SetCardInfo("Water Whip", 227, Rarity.RARE, mage.cards.w.WaterWhip.class));
        cards.add(new SetCardInfo("Wolf Cove Villager", 221, Rarity.COMMON, mage.cards.w.WolfCoveVillager.class));
        cards.add(new SetCardInfo("Zhao, the Seething Flame", 245, Rarity.UNCOMMON, mage.cards.z.ZhaoTheSeethingFlame.class));
        cards.add(new SetCardInfo("Zuko's Offense", 247, Rarity.COMMON, mage.cards.z.ZukosOffense.class));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName()));
    }
}
