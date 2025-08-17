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
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Aang's Defense", 211, Rarity.COMMON, mage.cards.a.AangsDefense.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aang's Defense", 266, Rarity.COMMON, mage.cards.a.AangsDefense.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aang, Air Nomad", 210, Rarity.RARE, mage.cards.a.AangAirNomad.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aang, Air Nomad", 265, Rarity.RARE, mage.cards.a.AangAirNomad.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aang, Airbending Master", 74, Rarity.MYTHIC, mage.cards.a.AangAirbendingMaster.class));
        cards.add(new SetCardInfo("Aardvark Sloth", 212, Rarity.COMMON, mage.cards.a.AardvarkSloth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Aardvark Sloth", 267, Rarity.COMMON, mage.cards.a.AardvarkSloth.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Allied Teamwork", 213, Rarity.RARE, mage.cards.a.AlliedTeamwork.class));
        cards.add(new SetCardInfo("Appa, Aang's Companion", 214, Rarity.UNCOMMON, mage.cards.a.AppaAangsCompanion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Appa, Aang's Companion", 268, Rarity.UNCOMMON, mage.cards.a.AppaAangsCompanion.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bumi, Eclectic Earthbender", 248, Rarity.RARE, mage.cards.b.BumiEclecticEarthbender.class));
        cards.add(new SetCardInfo("Capital Guard", 234, Rarity.COMMON, mage.cards.c.CapitalGuard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Capital Guard", 277, Rarity.COMMON, mage.cards.c.CapitalGuard.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Deny Entry", 222, Rarity.COMMON, mage.cards.d.DenyEntry.class));
        cards.add(new SetCardInfo("Dragon Moose", 235, Rarity.COMMON, mage.cards.d.DragonMoose.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Dragon Moose", 278, Rarity.COMMON, mage.cards.d.DragonMoose.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Earthbending Student", 249, Rarity.UNCOMMON, mage.cards.e.EarthbendingStudent.class));
        cards.add(new SetCardInfo("Eel-Hounds", 250, Rarity.UNCOMMON, mage.cards.e.EelHounds.class));
        cards.add(new SetCardInfo("Elephant-Rat", 228, Rarity.COMMON, mage.cards.e.ElephantRat.class));
        cards.add(new SetCardInfo("Explore", 259, Rarity.COMMON, mage.cards.e.Explore.class));
        cards.add(new SetCardInfo("Explosive Shot", 236, Rarity.COMMON, mage.cards.e.ExplosiveShot.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Explosive Shot", 279, Rarity.COMMON, mage.cards.e.ExplosiveShot.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Feed the Swarm", 257, Rarity.COMMON, mage.cards.f.FeedTheSwarm.class));
        cards.add(new SetCardInfo("Fire Nation Ambushers", 229, Rarity.COMMON, mage.cards.f.FireNationAmbushers.class));
        cards.add(new SetCardInfo("Fire Nation Archers", 237, Rarity.RARE, mage.cards.f.FireNationArchers.class));
        cards.add(new SetCardInfo("Fire Nation Sentinels", 230, Rarity.RARE, mage.cards.f.FireNationSentinels.class));
        cards.add(new SetCardInfo("Fire Nation Soldier", 238, Rarity.COMMON, mage.cards.f.FireNationSoldier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fire Nation Soldier", 280, Rarity.COMMON, mage.cards.f.FireNationSoldier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fire Nation's Conquest", 239, Rarity.UNCOMMON, mage.cards.f.FireNationsConquest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Fire Nation's Conquest", 281, Rarity.UNCOMMON, mage.cards.f.FireNationsConquest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Flying Dolphin-Fish", 223, Rarity.COMMON, mage.cards.f.FlyingDolphinFish.class));
        cards.add(new SetCardInfo("Force of Negation", 13, Rarity.MYTHIC, mage.cards.f.ForceOfNegation.class));
        cards.add(new SetCardInfo("Frog-Squirrels", 251, Rarity.COMMON, mage.cards.f.FrogSquirrels.class));
        cards.add(new SetCardInfo("Gilacorn", 231, Rarity.COMMON, mage.cards.g.Gilacorn.class));
        cards.add(new SetCardInfo("Hippo-Cows", 252, Rarity.COMMON, mage.cards.h.HippoCows.class));
        cards.add(new SetCardInfo("Iroh, Firebending Instructor", 240, Rarity.UNCOMMON, mage.cards.i.IrohFirebendingInstructor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Iroh, Firebending Instructor", 282, Rarity.UNCOMMON, mage.cards.i.IrohFirebendingInstructor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Katara, Heroic Healer", 215, Rarity.UNCOMMON, mage.cards.k.KataraHeroicHealer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Katara, Heroic Healer", 269, Rarity.UNCOMMON, mage.cards.k.KataraHeroicHealer.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Katara, Waterbending Master", 93, Rarity.MYTHIC, mage.cards.k.KataraWaterbendingMaster.class));
        cards.add(new SetCardInfo("Komodo Rhino", 241, Rarity.COMMON, mage.cards.k.KomodoRhino.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Komodo Rhino", 283, Rarity.COMMON, mage.cards.k.KomodoRhino.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kyoshi Warrior Guard", 216, Rarity.COMMON, mage.cards.k.KyoshiWarriorGuard.class));
        cards.add(new SetCardInfo("Lion Vulture", 232, Rarity.RARE, mage.cards.l.LionVulture.class));
        cards.add(new SetCardInfo("Lost in the Spirit World", 224, Rarity.UNCOMMON, mage.cards.l.LostInTheSpiritWorld.class));
        cards.add(new SetCardInfo("Loyal Fire Sage", 242, Rarity.UNCOMMON, mage.cards.l.LoyalFireSage.class));
        cards.add(new SetCardInfo("Match the Odds", 253, Rarity.UNCOMMON, mage.cards.m.MatchTheOdds.class));
        cards.add(new SetCardInfo("Mechanical Glider", 256, Rarity.COMMON, mage.cards.m.MechanicalGlider.class));
        cards.add(new SetCardInfo("Momo, Rambunctious Rascal", 217, Rarity.UNCOMMON, mage.cards.m.MomoRambunctiousRascal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Momo, Rambunctious Rascal", 270, Rarity.UNCOMMON, mage.cards.m.MomoRambunctiousRascal.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 289, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 290, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 291, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 292, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 293, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 294, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 295, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 296, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Path to Redemption", 271, Rarity.COMMON, mage.cards.p.PathToRedemption.class));
        cards.add(new SetCardInfo("Plains", 297, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 298, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 299, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 300, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 301, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 302, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 303, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 304, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Purple Pentapus", 233, Rarity.COMMON, mage.cards.p.PurplePentapus.class));
        cards.add(new SetCardInfo("Razor Rings", 272, Rarity.COMMON, mage.cards.r.RazorRings.class));
        cards.add(new SetCardInfo("Roku's Mastery", 243, Rarity.UNCOMMON, mage.cards.r.RokusMastery.class));
        cards.add(new SetCardInfo("Run Amok", 258, Rarity.COMMON, mage.cards.r.RunAmok.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Run Amok", 284, Rarity.COMMON, mage.cards.r.RunAmok.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seismic Tutelage", 254, Rarity.RARE, mage.cards.s.SeismicTutelage.class));
        cards.add(new SetCardInfo("Sledding Otter-Penguin", 218, Rarity.COMMON, mage.cards.s.SleddingOtterPenguin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sledding Otter-Penguin", 273, Rarity.COMMON, mage.cards.s.SleddingOtterPenguin.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sokka, Wolf Cove's Protector", 219, Rarity.UNCOMMON, mage.cards.s.SokkaWolfCovesProtector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sokka, Wolf Cove's Protector", 274, Rarity.UNCOMMON, mage.cards.s.SokkaWolfCovesProtector.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Cabbage Merchant", 134, Rarity.RARE, mage.cards.t.TheCabbageMerchant.class));
        cards.add(new SetCardInfo("The Great Henge", 41, Rarity.MYTHIC, mage.cards.t.TheGreatHenge.class));
        cards.add(new SetCardInfo("The Terror of Serpent's Pass", 225, Rarity.RARE, mage.cards.t.TheTerrorOfSerpentsPass.class));
        cards.add(new SetCardInfo("Thriving Bluff", 260, Rarity.COMMON, mage.cards.t.ThrivingBluff.class));
        cards.add(new SetCardInfo("Thriving Grove", 261, Rarity.COMMON, mage.cards.t.ThrivingGrove.class));
        cards.add(new SetCardInfo("Thriving Heath", 262, Rarity.COMMON, mage.cards.t.ThrivingHeath.class));
        cards.add(new SetCardInfo("Thriving Isle", 263, Rarity.COMMON, mage.cards.t.ThrivingIsle.class));
        cards.add(new SetCardInfo("Thriving Moor", 264, Rarity.COMMON, mage.cards.t.ThrivingMoor.class));
        cards.add(new SetCardInfo("Tundra Wall", 220, Rarity.COMMON, mage.cards.t.TundraWall.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Tundra Wall", 275, Rarity.COMMON, mage.cards.t.TundraWall.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Turtle-Seals", 226, Rarity.COMMON, mage.cards.t.TurtleSeals.class));
        cards.add(new SetCardInfo("Warship Scout", 244, Rarity.COMMON, mage.cards.w.WarshipScout.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Warship Scout", 285, Rarity.COMMON, mage.cards.w.WarshipScout.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Water Whip", 227, Rarity.RARE, mage.cards.w.WaterWhip.class));
        cards.add(new SetCardInfo("Wolf Cove Villager", 221, Rarity.COMMON, mage.cards.w.WolfCoveVillager.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wolf Cove Villager", 276, Rarity.COMMON, mage.cards.w.WolfCoveVillager.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zhao, the Seething Flame", 245, Rarity.UNCOMMON, mage.cards.z.ZhaoTheSeethingFlame.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zhao, the Seething Flame", 286, Rarity.UNCOMMON, mage.cards.z.ZhaoTheSeethingFlame.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zuko's Offense", 247, Rarity.COMMON, mage.cards.z.ZukosOffense.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zuko's Offense", 288, Rarity.COMMON, mage.cards.z.ZukosOffense.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zuko, Avatar Hunter", 246, Rarity.RARE, mage.cards.z.ZukoAvatarHunter.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Zuko, Avatar Hunter", 287, Rarity.RARE, mage.cards.z.ZukoAvatarHunter.class, NON_FULL_USE_VARIOUS));

        cards.removeIf(setCardInfo -> unfinished.contains(setCardInfo.getName()));
    }
}
