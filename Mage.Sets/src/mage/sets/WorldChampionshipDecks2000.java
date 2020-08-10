package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/wc00
 */
public class WorldChampionshipDecks2000 extends ExpansionSet {

    private static final WorldChampionshipDecks2000 instance = new WorldChampionshipDecks2000();

    public static WorldChampionshipDecks2000 getInstance() {
        return instance;
    }

    private WorldChampionshipDecks2000() {
        super("World Championship Decks 2000", "WC00", ExpansionSet.buildDate(2000, 8, 2), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Absolute Law", "nl2sba", Rarity.UNCOMMON, mage.cards.a.AbsoluteLaw.class));
        cards.add(new SetCardInfo("Academy Rector", "nl1", Rarity.RARE, mage.cards.a.AcademyRector.class));
        cards.add(new SetCardInfo("Adarkar Wastes", "tvdl319", Rarity.RARE, mage.cards.a.AdarkarWastes.class));
        cards.add(new SetCardInfo("Annul", "jf59sb", Rarity.COMMON, mage.cards.a.Annul.class));
        cards.add(new SetCardInfo("Arc Lightning", "jk174", Rarity.COMMON, mage.cards.a.ArcLightning.class));
        cards.add(new SetCardInfo("Armageddon", "nl4sb", Rarity.RARE, mage.cards.a.Armageddon.class));
        cards.add(new SetCardInfo("Ashnod's Altar", "nl274", Rarity.UNCOMMON, mage.cards.a.AshnodsAltar.class));
        cards.add(new SetCardInfo("Attunement", "tvdl61", Rarity.RARE, mage.cards.a.Attunement.class));
        cards.add(new SetCardInfo("Aura Fracture", "nl2sbb", Rarity.COMMON, mage.cards.a.AuraFracture.class));
        cards.add(new SetCardInfo("Avalanche Riders", "jk74", Rarity.UNCOMMON, mage.cards.a.AvalancheRiders.class));
        cards.add(new SetCardInfo("Birds of Paradise", "nl217", Rarity.RARE, mage.cards.b.BirdsOfParadise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Birds of Paradise", "jk217", Rarity.RARE, mage.cards.b.BirdsOfParadise.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Blastoderm", "jk102", Rarity.COMMON, mage.cards.b.Blastoderm.class));
        cards.add(new SetCardInfo("Blaze", "nl168sb", Rarity.UNCOMMON, mage.cards.b.Blaze.class));
        cards.add(new SetCardInfo("Boil", "jk169sb", Rarity.UNCOMMON, mage.cards.b.Boil.class));
        cards.add(new SetCardInfo("Brainstorm", "jf61", Rarity.COMMON, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("Brushland", "nl320", Rarity.RARE, mage.cards.b.Brushland.class));
        cards.add(new SetCardInfo("Chill", "tvdl60sb", Rarity.UNCOMMON, mage.cards.c.Chill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chill", "jf60sb", Rarity.UNCOMMON, mage.cards.c.Chill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Circle of Protection: Black", "tvdl8sb", Rarity.COMMON, mage.cards.c.CircleOfProtectionBlack.class));
        cards.add(new SetCardInfo("City of Brass", "nl321", Rarity.RARE, mage.cards.c.CityOfBrass.class));
        cards.add(new SetCardInfo("Confiscate", "nl66", Rarity.UNCOMMON, mage.cards.c.Confiscate.class));
        cards.add(new SetCardInfo("Counterspell", "tvdl61b", Rarity.COMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Creeping Mold", "jk220sb", Rarity.UNCOMMON, mage.cards.c.CreepingMold.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Creeping Mold", "jk220", Rarity.UNCOMMON, mage.cards.c.CreepingMold.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Crumbling Sanctuary", "jf292", Rarity.RARE, mage.cards.c.CrumblingSanctuary.class));
        cards.add(new SetCardInfo("Crystal Vein", "jf322", Rarity.UNCOMMON, mage.cards.c.CrystalVein.class));
        cards.add(new SetCardInfo("Cursed Totem", "tvdl278sb", Rarity.RARE, mage.cards.c.CursedTotem.class));
        cards.add(new SetCardInfo("Daze", "tvdl30sb", Rarity.COMMON, mage.cards.d.Daze.class));
        cards.add(new SetCardInfo("Defense Grid", "nl125sb", Rarity.RARE, mage.cards.d.DefenseGrid.class));
        cards.add(new SetCardInfo("Dust Bowl", "jk316", Rarity.RARE, mage.cards.d.DustBowl.class));
        cards.add(new SetCardInfo("Energy Field", "tvdl73", Rarity.RARE, mage.cards.e.EnergyField.class));
        cards.add(new SetCardInfo("Energy Flux", "nl78sb", Rarity.UNCOMMON, mage.cards.e.EnergyFlux.class));
        cards.add(new SetCardInfo("Enlightened Tutor", "tvdl19", Rarity.UNCOMMON, mage.cards.e.EnlightenedTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Enlightened Tutor", "nl19", Rarity.UNCOMMON, mage.cards.e.EnlightenedTutor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Erase", "tvdl7sb", Rarity.COMMON, mage.cards.e.Erase.class));
        cards.add(new SetCardInfo("Fecundity", "nl251", Rarity.UNCOMMON, mage.cards.f.Fecundity.class));
        cards.add(new SetCardInfo("Flameshot", "jk90sb", Rarity.UNCOMMON, mage.cards.f.Flameshot.class));
        cards.add(new SetCardInfo("Forest", "nl347", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Forest", "jk347", Rarity.LAND, mage.cards.basiclands.Forest.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Frantic Search", "tvdl32", Rarity.COMMON, mage.cards.f.FranticSearch.class));
        cards.add(new SetCardInfo("Grim Monolith", "jf126", Rarity.RARE, mage.cards.g.GrimMonolith.class));
        cards.add(new SetCardInfo("Heart of Ramos", "nl296sb", Rarity.RARE, mage.cards.h.HeartOfRamos.class));
        cards.add(new SetCardInfo("High Market", "nl320b", Rarity.RARE, mage.cards.h.HighMarket.class));
        cards.add(new SetCardInfo("Island", "tvdl335", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", "jf335", Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karplusan Forest", "jk326", Rarity.RARE, mage.cards.k.KarplusanForest.class));
        cards.add(new SetCardInfo("Light of Day", "nl29sb", Rarity.UNCOMMON, mage.cards.l.LightOfDay.class));
        cards.add(new SetCardInfo("Lilting Refrain", "tvdl83sb", Rarity.UNCOMMON, mage.cards.l.LiltingRefrain.class));
        cards.add(new SetCardInfo("Llanowar Elves", "nl239", Rarity.COMMON, mage.cards.l.LlanowarElves.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Llanowar Elves", "jk239", Rarity.COMMON, mage.cards.l.LlanowarElves.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Masticore", "jk134sb", Rarity.RARE, mage.cards.m.Masticore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Masticore", "jk134", Rarity.RARE, mage.cards.m.Masticore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Masticore", "jf134", Rarity.RARE, mage.cards.m.Masticore.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Meekstone", "nl299sb", Rarity.RARE, mage.cards.m.Meekstone.class));
        cards.add(new SetCardInfo("Metalworker", "jf135", Rarity.RARE, mage.cards.m.Metalworker.class));
        cards.add(new SetCardInfo("Miscalculation", "jf36sb", Rarity.COMMON, mage.cards.m.Miscalculation.class));
        cards.add(new SetCardInfo("Mishra's Helix", "jf302sb", Rarity.RARE, mage.cards.m.MishrasHelix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mishra's Helix", "jf302", Rarity.RARE, mage.cards.m.MishrasHelix.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", "jk343", Rarity.LAND, mage.cards.basiclands.Mountain.class));
        cards.add(new SetCardInfo("Mystical Tutor", "tvdl83", Rarity.UNCOMMON, mage.cards.m.MysticalTutor.class));
        cards.add(new SetCardInfo("Opalescence", "tvdl13", Rarity.RARE, mage.cards.o.Opalescence.class));
        cards.add(new SetCardInfo("Parallax Tide", "tvdl37", Rarity.RARE, mage.cards.p.ParallaxTide.class));
        cards.add(new SetCardInfo("Parallax Wave", "tvdl17", Rarity.RARE, mage.cards.p.ParallaxWave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Parallax Wave", "nl17sb", Rarity.RARE, mage.cards.p.ParallaxWave.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Pattern of Rebirth", "nl115", Rarity.RARE, mage.cards.p.PatternOfRebirth.class));
        cards.add(new SetCardInfo("Phyrexian Colossus", "jf305", Rarity.RARE, mage.cards.p.PhyrexianColossus.class));
        cards.add(new SetCardInfo("Phyrexian Processor", "nl306sb", Rarity.RARE, mage.cards.p.PhyrexianProcessor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Processor", "jk306sb", Rarity.RARE, mage.cards.p.PhyrexianProcessor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Processor", "jk306", Rarity.RARE, mage.cards.p.PhyrexianProcessor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Processor", "jf306b", Rarity.RARE, mage.cards.p.PhyrexianProcessor.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Phyrexian Tower", "nl322", Rarity.RARE, mage.cards.p.PhyrexianTower.class));
        cards.add(new SetCardInfo("Plains", "tvdl331", Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Plow Under", "jk117", Rarity.RARE, mage.cards.p.PlowUnder.class));
        cards.add(new SetCardInfo("Priest of Titania", "nl270", Rarity.COMMON, mage.cards.p.PriestOfTitania.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Priest of Titania", "jk270", Rarity.COMMON, mage.cards.p.PriestOfTitania.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rack and Ruin", "jk89sb", Rarity.UNCOMMON, mage.cards.r.RackAndRuin.class));
        cards.add(new SetCardInfo("Replenish", "tvdl15", Rarity.RARE, mage.cards.r.Replenish.class));
        cards.add(new SetCardInfo("Rishadan Port", "tvdl324", Rarity.RARE, mage.cards.r.RishadanPort.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rishadan Port", "jk324", Rarity.RARE, mage.cards.r.RishadanPort.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rishadan Port", "jf324", Rarity.RARE, mage.cards.r.RishadanPort.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Rising Waters", "jf38sb", Rarity.RARE, mage.cards.r.RisingWaters.class));
        cards.add(new SetCardInfo("Saprazzan Skerry", "jf328", Rarity.COMMON, mage.cards.s.SaprazzanSkerry.class));
        cards.add(new SetCardInfo("Saproling Burst", "jk113", Rarity.RARE, mage.cards.s.SaprolingBurst.class));
        cards.add(new SetCardInfo("Saproling Cluster", "nl114", Rarity.RARE, mage.cards.s.SaprolingCluster.class));
        cards.add(new SetCardInfo("Seal of Cleansing", "tvdl18sb", Rarity.COMMON, mage.cards.s.SealOfCleansing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seal of Cleansing", "tvdl18", Rarity.COMMON, mage.cards.s.SealOfCleansing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seal of Cleansing", "nl18sb", Rarity.COMMON, mage.cards.s.SealOfCleansing.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Seal of Removal", "tvdl42", Rarity.COMMON, mage.cards.s.SealOfRemoval.class));
        cards.add(new SetCardInfo("Serra Avatar", "nl45", Rarity.RARE, mage.cards.s.SerraAvatar.class));
        cards.add(new SetCardInfo("Sky Diamond", "tvdl311", Rarity.UNCOMMON, mage.cards.s.SkyDiamond.class));
        cards.add(new SetCardInfo("Snake Basket", "nl312", Rarity.RARE, mage.cards.s.SnakeBasket.class));
        cards.add(new SetCardInfo("Splinter", "jk121sb", Rarity.UNCOMMON, mage.cards.s.Splinter.class));
        cards.add(new SetCardInfo("Stone Rain", "jk209", Rarity.COMMON, mage.cards.s.StoneRain.class));
        cards.add(new SetCardInfo("Submerge", "tvdl48sb", Rarity.UNCOMMON, mage.cards.s.Submerge.class));
        cards.add(new SetCardInfo("Tangle Wire", "jf139a", Rarity.RARE, mage.cards.t.TangleWire.class));
        cards.add(new SetCardInfo("Thran Dynamo", "jf139b", Rarity.UNCOMMON, mage.cards.t.ThranDynamo.class));
        cards.add(new SetCardInfo("Thran Quarry", "nl329", Rarity.RARE, mage.cards.t.ThranQuarry.class));
        cards.add(new SetCardInfo("Tinker", "jf45", Rarity.UNCOMMON, mage.cards.t.Tinker.class));
        cards.add(new SetCardInfo("Uktabi Orangutan", "jk260sb", Rarity.UNCOMMON, mage.cards.u.UktabiOrangutan.class));
        cards.add(new SetCardInfo("Voltaic Key", "jf314", Rarity.UNCOMMON, mage.cards.v.VoltaicKey.class));
        cards.add(new SetCardInfo("Whetstone", "nl316", Rarity.RARE, mage.cards.w.Whetstone.class));
        cards.add(new SetCardInfo("Worship", "nl57sb", Rarity.RARE, mage.cards.w.Worship.class));
        cards.add(new SetCardInfo("Wrath of God", "tvdl54sb", Rarity.RARE, mage.cards.w.WrathOfGod.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wrath of God", "tvdl54", Rarity.RARE, mage.cards.w.WrathOfGod.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Yawgmoth's Bargain", "nl75", Rarity.RARE, mage.cards.y.YawgmothsBargain.class));
        cards.add(new SetCardInfo("Yawgmoth's Will", "nl171", Rarity.RARE, mage.cards.y.YawgmothsWill.class));
     }
}
