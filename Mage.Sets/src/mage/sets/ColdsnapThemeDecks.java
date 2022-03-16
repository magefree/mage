package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/cst
 */
public class ColdsnapThemeDecks extends ExpansionSet {

    private static final ColdsnapThemeDecks instance = new ColdsnapThemeDecks();

    public static ColdsnapThemeDecks getInstance() {
        return instance;
    }

    private ColdsnapThemeDecks() {
        super("Coldsnap Theme Decks", "CST", ExpansionSet.buildDate(2006, 7, 21), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        //cards.add(new SetCardInfo("Arcum's Weathervane", 310, Rarity.UNCOMMON, mage.cards.a.ArcumsWeathervane.class));
        cards.add(new SetCardInfo("Ashen Ghoul", 114, Rarity.UNCOMMON, mage.cards.a.AshenGhoul.class));
        cards.add(new SetCardInfo("Aurochs", 225, Rarity.COMMON, mage.cards.a.Aurochs.class));
        cards.add(new SetCardInfo("Balduvian Dead", 43, Rarity.UNCOMMON, mage.cards.b.BalduvianDead.class));
        cards.add(new SetCardInfo("Barbed Sextant", 312, Rarity.COMMON, mage.cards.b.BarbedSextant.class));
        cards.add(new SetCardInfo("Binding Grasp", 60, Rarity.UNCOMMON, mage.cards.b.BindingGrasp.class));
        cards.add(new SetCardInfo("Bounty of the Hunt", 85, Rarity.UNCOMMON, mage.cards.b.BountyOfTheHunt.class));
        cards.add(new SetCardInfo("Brainstorm", 61, Rarity.COMMON, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Browse", 25, Rarity.UNCOMMON, mage.cards.b.Browse.class));
        cards.add(new SetCardInfo("Casting of Bones", "44b", Rarity.COMMON, mage.cards.c.CastingOfBones.class));
        cards.add(new SetCardInfo("Dark Banishing", 119, Rarity.COMMON, mage.cards.d.DarkBanishing.class));
        cards.add(new SetCardInfo("Dark Ritual", 120, Rarity.COMMON, mage.cards.d.DarkRitual.class));
        cards.add(new SetCardInfo("Deadly Insect", "86a", Rarity.COMMON, mage.cards.d.DeadlyInsect.class));
        cards.add(new SetCardInfo("Death Spark", 70, Rarity.UNCOMMON, mage.cards.d.DeathSpark.class));
        cards.add(new SetCardInfo("Disenchant", 20, Rarity.COMMON, mage.cards.d.Disenchant.class));
        cards.add(new SetCardInfo("Drift of the Dead", 123, Rarity.UNCOMMON, mage.cards.d.DriftOfTheDead.class));
        cards.add(new SetCardInfo("Essence Flare", 69, Rarity.COMMON, mage.cards.e.EssenceFlare.class));
        cards.add(new SetCardInfo("Forest", 381, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 382, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", 383, Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gangrenous Zombies", 127, Rarity.COMMON, mage.cards.g.GangrenousZombies.class));
        cards.add(new SetCardInfo("Giant Trap Door Spider", 293, Rarity.UNCOMMON, mage.cards.g.GiantTrapDoorSpider.class));
        cards.add(new SetCardInfo("Gorilla Shaman", "72a", Rarity.COMMON, mage.cards.g.GorillaShaman.class));
        cards.add(new SetCardInfo("Iceberg", 73, Rarity.UNCOMMON, mage.cards.i.Iceberg.class));
        cards.add(new SetCardInfo("Incinerate", 194, Rarity.COMMON, mage.cards.i.Incinerate.class));
        cards.add(new SetCardInfo("Insidious Bookworms", "51a", Rarity.COMMON, mage.cards.i.InsidiousBookworms.class));
        cards.add(new SetCardInfo("Island", 372, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 373, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 374, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Kjeldoran Dead", 137, Rarity.COMMON, mage.cards.k.KjeldoranDead.class));
        cards.add(new SetCardInfo("Kjeldoran Elite Guard", 34, Rarity.UNCOMMON, mage.cards.k.KjeldoranEliteGuard.class));
        cards.add(new SetCardInfo("Kjeldoran Home Guard", 8, Rarity.UNCOMMON, mage.cards.k.KjeldoranHomeGuard.class));
        cards.add(new SetCardInfo("Kjeldoran Pride", "9b", Rarity.COMMON, mage.cards.k.KjeldoranPride.class));
        cards.add(new SetCardInfo("Lat-Nam's Legacy", "30b", Rarity.COMMON, mage.cards.l.LatNamsLegacy.class));
        cards.add(new SetCardInfo("Legions of Lim-Dul", 142, Rarity.COMMON, mage.cards.l.LegionsOfLimDul.class));
        cards.add(new SetCardInfo("Mistfolk", 84, Rarity.COMMON, mage.cards.m.Mistfolk.class));
        cards.add(new SetCardInfo("Mountain", 378, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 379, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 380, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Orcish Healer", 208, Rarity.UNCOMMON, mage.cards.o.OrcishHealer.class));
        cards.add(new SetCardInfo("Orcish Lumberjack", 210, Rarity.COMMON, mage.cards.o.OrcishLumberjack.class));
        cards.add(new SetCardInfo("Phantasmal Fiend", "57a", Rarity.COMMON, mage.cards.p.PhantasmalFiend.class));
        cards.add(new SetCardInfo("Plains", 369, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 370, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 371, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Portent", 90, Rarity.COMMON, mage.cards.p.Portent.class));
        cards.add(new SetCardInfo("Reinforcements", "12b", Rarity.COMMON, mage.cards.r.Reinforcements.class));
        //ards.add(new SetCardInfo("Scars of the Veteran", 16, Rarity.UNCOMMON, mage.cards.s.ScarsOfTheVeteran.class));
        cards.add(new SetCardInfo("Skull Catapult", 336, Rarity.UNCOMMON, mage.cards.s.SkullCatapult.class));
        cards.add(new SetCardInfo("Snow Devil", 100, Rarity.COMMON, mage.cards.s.SnowDevil.class));
        cards.add(new SetCardInfo("Soul Burn", 161, Rarity.COMMON, mage.cards.s.SoulBurn.class));
        cards.add(new SetCardInfo("Storm Elemental", 37, Rarity.UNCOMMON, mage.cards.s.StormElemental.class));
        cards.add(new SetCardInfo("Swamp", 375, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 376, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swamp", 377, Rarity.LAND, mage.cards.basiclands.Swamp.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Swords to Plowshares", 54, Rarity.UNCOMMON, mage.cards.s.SwordsToPlowshares.class));
        cards.add(new SetCardInfo("Tinder Wall", 270, Rarity.COMMON, mage.cards.t.TinderWall.class));
        cards.add(new SetCardInfo("Viscerid Drone", 42, Rarity.UNCOMMON, mage.cards.v.VisceridDrone.class));
        cards.add(new SetCardInfo("Whalebone Glider", 349, Rarity.UNCOMMON, mage.cards.w.WhaleboneGlider.class));
        cards.add(new SetCardInfo("Wings of Aesthir", 305, Rarity.UNCOMMON, mage.cards.w.WingsOfAesthir.class));
        cards.add(new SetCardInfo("Woolly Mammoths", 278, Rarity.COMMON, mage.cards.w.WoollyMammoths.class));
        cards.add(new SetCardInfo("Zuran Spellcaster", 112, Rarity.COMMON, mage.cards.z.ZuranSpellcaster.class));
     }
}
