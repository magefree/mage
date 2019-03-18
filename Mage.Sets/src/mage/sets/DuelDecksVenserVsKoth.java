package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author LevelX2
 */
public final class DuelDecksVenserVsKoth extends ExpansionSet {

    private static final DuelDecksVenserVsKoth instance = new DuelDecksVenserVsKoth();

    public static DuelDecksVenserVsKoth getInstance() {
        return instance;
    }

    private DuelDecksVenserVsKoth() {
        super("Duel Decks: Venser vs. Koth", "DDI", ExpansionSet.buildDate(2012, 3, 30), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Aether Membrane", 48, Rarity.UNCOMMON, mage.cards.a.AetherMembrane.class));
        cards.add(new SetCardInfo("Angelic Shield", 27, Rarity.UNCOMMON, mage.cards.a.AngelicShield.class));
        cards.add(new SetCardInfo("Anger", 51, Rarity.UNCOMMON, mage.cards.a.Anger.class));
        cards.add(new SetCardInfo("Armillary Sphere", 64, Rarity.COMMON, mage.cards.a.ArmillarySphere.class));
        cards.add(new SetCardInfo("Augury Owl", 3, Rarity.COMMON, mage.cards.a.AuguryOwl.class));
        cards.add(new SetCardInfo("Azorius Chancery", 33, Rarity.COMMON, mage.cards.a.AzoriusChancery.class));
        cards.add(new SetCardInfo("Bloodfire Colossus", 62, Rarity.RARE, mage.cards.b.BloodfireColossus.class));
        cards.add(new SetCardInfo("Bloodfire Kavu", 54, Rarity.UNCOMMON, mage.cards.b.BloodfireKavu.class));
        cards.add(new SetCardInfo("Cache Raiders", 18, Rarity.UNCOMMON, mage.cards.c.CacheRaiders.class));
        cards.add(new SetCardInfo("Chartooth Cougar", 59, Rarity.COMMON, mage.cards.c.ChartoothCougar.class));
        cards.add(new SetCardInfo("Clone", 14, Rarity.RARE, mage.cards.c.Clone.class));
        cards.add(new SetCardInfo("Coral Fighters", 4, Rarity.UNCOMMON, mage.cards.c.CoralFighters.class));
        cards.add(new SetCardInfo("Cosi's Ravager", 52, Rarity.COMMON, mage.cards.c.CosisRavager.class));
        cards.add(new SetCardInfo("Cryptic Annelid", 15, Rarity.UNCOMMON, mage.cards.c.CrypticAnnelid.class));
        cards.add(new SetCardInfo("Downhill Charge", 69, Rarity.COMMON, mage.cards.d.DownhillCharge.class));
        cards.add(new SetCardInfo("Earth Servant", 60, Rarity.UNCOMMON, mage.cards.e.EarthServant.class));
        cards.add(new SetCardInfo("Fiery Hellhound", 49, Rarity.COMMON, mage.cards.f.FieryHellhound.class));
        cards.add(new SetCardInfo("Flood Plain", 34, Rarity.UNCOMMON, mage.cards.f.FloodPlain.class));
        cards.add(new SetCardInfo("Galepowder Mage", 12, Rarity.RARE, mage.cards.g.GalepowderMage.class));
        cards.add(new SetCardInfo("Geyser Glider", 56, Rarity.UNCOMMON, mage.cards.g.GeyserGlider.class));
        cards.add(new SetCardInfo("Greater Stone Spirit", 61, Rarity.UNCOMMON, mage.cards.g.GreaterStoneSpirit.class));
        cards.add(new SetCardInfo("Island", 41, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 42, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 43, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jaws of Stone", 72, Rarity.UNCOMMON, mage.cards.j.JawsOfStone.class));
        cards.add(new SetCardInfo("Jedit's Dragoons", 20, Rarity.COMMON, mage.cards.j.JeditsDragoons.class));
        cards.add(new SetCardInfo("Journeyer's Kite", 65, Rarity.RARE, mage.cards.j.JourneyersKite.class));
        cards.add(new SetCardInfo("Kor Cartographer", 13, Rarity.COMMON, mage.cards.k.KorCartographer.class));
        cards.add(new SetCardInfo("Koth of the Hammer", 44, Rarity.MYTHIC, mage.cards.k.KothOfTheHammer.class));
        cards.add(new SetCardInfo("Lithophage", 57, Rarity.RARE, mage.cards.l.Lithophage.class));
        cards.add(new SetCardInfo("Minamo Sightbender", 5, Rarity.UNCOMMON, mage.cards.m.MinamoSightbender.class));
        cards.add(new SetCardInfo("Mistmeadow Witch", 6, Rarity.UNCOMMON, mage.cards.m.MistmeadowWitch.class));
        cards.add(new SetCardInfo("Mountain", 74, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 75, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 76, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 77, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Neurok Invisimancer", 8, Rarity.COMMON, mage.cards.n.NeurokInvisimancer.class));
        cards.add(new SetCardInfo("New Benalia", 35, Rarity.UNCOMMON, mage.cards.n.NewBenalia.class));
        cards.add(new SetCardInfo("Oblivion Ring", 28, Rarity.UNCOMMON, mage.cards.o.OblivionRing.class));
        cards.add(new SetCardInfo("Overrule", 32, Rarity.COMMON, mage.cards.o.Overrule.class));
        cards.add(new SetCardInfo("Path to Exile", 23, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Pilgrim's Eye", 47, Rarity.COMMON, mage.cards.p.PilgrimsEye.class));
        cards.add(new SetCardInfo("Plains", 38, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 39, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plains", 40, Rarity.LAND, mage.cards.basiclands.Plains.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Plated Geopede", 45, Rarity.COMMON, mage.cards.p.PlatedGeopede.class));
        cards.add(new SetCardInfo("Preordain", 24, Rarity.COMMON, mage.cards.p.Preordain.class));
        cards.add(new SetCardInfo("Primal Plasma", 16, Rarity.COMMON, mage.cards.p.PrimalPlasma.class));
        cards.add(new SetCardInfo("Pygmy Pyrosaur", 46, Rarity.COMMON, mage.cards.p.PygmyPyrosaur.class));
        cards.add(new SetCardInfo("Revoke Existence", 26, Rarity.COMMON, mage.cards.r.RevokeExistence.class));
        cards.add(new SetCardInfo("Safe Passage", 29, Rarity.COMMON, mage.cards.s.SafePassage.class));
        cards.add(new SetCardInfo("Sawtooth Loon", 17, Rarity.UNCOMMON, mage.cards.s.SawtoothLoon.class));
        cards.add(new SetCardInfo("Scroll Thief", 7, Rarity.COMMON, mage.cards.s.ScrollThief.class));
        cards.add(new SetCardInfo("Searing Blaze", 67, Rarity.COMMON, mage.cards.s.SearingBlaze.class));
        cards.add(new SetCardInfo("Seismic Strike", 70, Rarity.COMMON, mage.cards.s.SeismicStrike.class));
        cards.add(new SetCardInfo("Sejiri Refuge", 36, Rarity.UNCOMMON, mage.cards.s.SejiriRefuge.class));
        cards.add(new SetCardInfo("Sigil of Sleep", 25, Rarity.COMMON, mage.cards.s.SigilOfSleep.class));
        cards.add(new SetCardInfo("Sky Spirit", 10, Rarity.UNCOMMON, mage.cards.s.SkySpirit.class));
        cards.add(new SetCardInfo("Slith Strider", 9, Rarity.UNCOMMON, mage.cards.s.SlithStrider.class));
        cards.add(new SetCardInfo("Soaring Seacliff", 37, Rarity.COMMON, mage.cards.s.SoaringSeacliff.class));
        cards.add(new SetCardInfo("Sphinx of Uthuun", 22, Rarity.RARE, mage.cards.s.SphinxOfUthuun.class));
        cards.add(new SetCardInfo("Spire Barrage", 71, Rarity.COMMON, mage.cards.s.SpireBarrage.class));
        cards.add(new SetCardInfo("Steel of the Godhead", 30, Rarity.COMMON, mage.cards.s.SteelOfTheGodhead.class));
        cards.add(new SetCardInfo("Stone Giant", 55, Rarity.UNCOMMON, mage.cards.s.StoneGiant.class));
        cards.add(new SetCardInfo("Sunblast Angel", 21, Rarity.RARE, mage.cards.s.SunblastAngel.class));
        cards.add(new SetCardInfo("Torchling", 58, Rarity.RARE, mage.cards.t.Torchling.class));
        cards.add(new SetCardInfo("Vanish into Memory", 31, Rarity.UNCOMMON, mage.cards.v.VanishIntoMemory.class));
        cards.add(new SetCardInfo("Venser, the Sojourner", 1, Rarity.MYTHIC, mage.cards.v.VenserTheSojourner.class));
        cards.add(new SetCardInfo("Volley of Boulders", 73, Rarity.RARE, mage.cards.v.VolleyOfBoulders.class));
        cards.add(new SetCardInfo("Vulshok Battlegear", 68, Rarity.UNCOMMON, mage.cards.v.VulshokBattlegear.class));
        cards.add(new SetCardInfo("Vulshok Berserker", 53, Rarity.COMMON, mage.cards.v.VulshokBerserker.class));
        cards.add(new SetCardInfo("Vulshok Morningstar", 66, Rarity.UNCOMMON, mage.cards.v.VulshokMorningstar.class));
        cards.add(new SetCardInfo("Vulshok Sorcerer", 50, Rarity.COMMON, mage.cards.v.VulshokSorcerer.class));
        cards.add(new SetCardInfo("Wall of Denial", 11, Rarity.UNCOMMON, mage.cards.w.WallOfDenial.class));
        cards.add(new SetCardInfo("Wayfarer's Bauble", 63, Rarity.COMMON, mage.cards.w.WayfarersBauble.class));
        cards.add(new SetCardInfo("Whitemane Lion", 2, Rarity.COMMON, mage.cards.w.WhitemaneLion.class));
        cards.add(new SetCardInfo("Windreaver", 19, Rarity.RARE, mage.cards.w.Windreaver.class));
    }
}
