package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author LevelX2
 */
public final class DuelDecksJaceVsChandra extends ExpansionSet {

    private static final DuelDecksJaceVsChandra instance = new DuelDecksJaceVsChandra();

    public static DuelDecksJaceVsChandra getInstance() {
        return instance;
    }

    private DuelDecksJaceVsChandra() {
        super("Duel Decks: Jace vs. Chandra", "DD2", ExpansionSet.buildDate(2008, 11, 7), SetType.SUPPLEMENTAL);
        this.blockName = "Duel Decks";
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Aethersnipe", 17, Rarity.COMMON, mage.cards.a.Aethersnipe.class));
        cards.add(new SetCardInfo("Air Elemental", 13, Rarity.UNCOMMON, mage.cards.a.AirElemental.class));
        cards.add(new SetCardInfo("Ancestral Vision", 21, Rarity.RARE, mage.cards.a.AncestralVision.class));
        cards.add(new SetCardInfo("Bottle Gnomes", 7, Rarity.UNCOMMON, mage.cards.b.BottleGnomes.class));
        cards.add(new SetCardInfo("Brine Elemental", 18, Rarity.UNCOMMON, mage.cards.b.BrineElemental.class));
        cards.add(new SetCardInfo("Chandra Nalaar", "34*", Rarity.MYTHIC, mage.cards.c.ChandraNalaar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chandra Nalaar", 34, Rarity.MYTHIC, mage.cards.c.ChandraNalaar.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Chartooth Cougar", 47, Rarity.COMMON, mage.cards.c.ChartoothCougar.class));
        cards.add(new SetCardInfo("Condescend", 28, Rarity.COMMON, mage.cards.c.Condescend.class));
        cards.add(new SetCardInfo("Cone of Flame", 54, Rarity.UNCOMMON, mage.cards.c.ConeOfFlame.class));
        cards.add(new SetCardInfo("Counterspell", 24, Rarity.COMMON, mage.cards.c.Counterspell.class));
        cards.add(new SetCardInfo("Daze", 23, Rarity.COMMON, mage.cards.d.Daze.class));
        cards.add(new SetCardInfo("Demonfire", 57, Rarity.RARE, mage.cards.d.Demonfire.class));
        cards.add(new SetCardInfo("Errant Ephemeron", 20, Rarity.COMMON, mage.cards.e.ErrantEphemeron.class));
        cards.add(new SetCardInfo("Fact or Fiction", 26, Rarity.UNCOMMON, mage.cards.f.FactOrFiction.class));
        cards.add(new SetCardInfo("Fathom Seer", 3, Rarity.COMMON, mage.cards.f.FathomSeer.class));
        cards.add(new SetCardInfo("Fireball", 56, Rarity.UNCOMMON, mage.cards.f.Fireball.class));
        cards.add(new SetCardInfo("Fireblast", 55, Rarity.COMMON, mage.cards.f.Fireblast.class));
        cards.add(new SetCardInfo("Firebolt", 49, Rarity.COMMON, mage.cards.f.Firebolt.class));
        cards.add(new SetCardInfo("Fireslinger", 36, Rarity.COMMON, mage.cards.f.Fireslinger.class));
        cards.add(new SetCardInfo("Flame Javelin", 53, Rarity.UNCOMMON, mage.cards.f.FlameJavelin.class));
        cards.add(new SetCardInfo("Flamekin Brawler", 35, Rarity.COMMON, mage.cards.f.FlamekinBrawler.class));
        cards.add(new SetCardInfo("Flametongue Kavu", 42, Rarity.UNCOMMON, mage.cards.f.FlametongueKavu.class));
        cards.add(new SetCardInfo("Flamewave Invoker", 40, Rarity.UNCOMMON, mage.cards.f.FlamewaveInvoker.class));
        cards.add(new SetCardInfo("Fledgling Mawcor", 10, Rarity.UNCOMMON, mage.cards.f.FledglingMawcor.class));
        cards.add(new SetCardInfo("Furnace Whelp", 43, Rarity.UNCOMMON, mage.cards.f.FurnaceWhelp.class));
        cards.add(new SetCardInfo("Guile", 14, Rarity.RARE, mage.cards.g.Guile.class));
        cards.add(new SetCardInfo("Gush", 27, Rarity.COMMON, mage.cards.g.Gush.class));
        cards.add(new SetCardInfo("Hostility", 48, Rarity.RARE, mage.cards.h.Hostility.class));
        cards.add(new SetCardInfo("Incinerate", 51, Rarity.COMMON, mage.cards.i.Incinerate.class));
        cards.add(new SetCardInfo("Ingot Chewer", 45, Rarity.COMMON, mage.cards.i.IngotChewer.class));
        cards.add(new SetCardInfo("Inner-Flame Acolyte", 41, Rarity.COMMON, mage.cards.i.InnerFlameAcolyte.class));
        cards.add(new SetCardInfo("Island", 30, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 31, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 32, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Island", 33, Rarity.LAND, mage.cards.basiclands.Island.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace Beleren", "1*", Rarity.MYTHIC, mage.cards.j.JaceBeleren.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Jace Beleren", 1, Rarity.MYTHIC, mage.cards.j.JaceBeleren.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Keldon Megaliths", 58, Rarity.UNCOMMON, mage.cards.k.KeldonMegaliths.class));
        cards.add(new SetCardInfo("Magma Jet", 52, Rarity.UNCOMMON, mage.cards.m.MagmaJet.class));
        cards.add(new SetCardInfo("Man-o'-War", 8, Rarity.COMMON, mage.cards.m.ManOWar.class));
        cards.add(new SetCardInfo("Martyr of Frost", 2, Rarity.COMMON, mage.cards.m.MartyrOfFrost.class));
        cards.add(new SetCardInfo("Mind Stone", 22, Rarity.UNCOMMON, mage.cards.m.MindStone.class));
        cards.add(new SetCardInfo("Mountain", 59, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 60, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 61, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mountain", 62, Rarity.LAND, mage.cards.basiclands.Mountain.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mulldrifter", 12, Rarity.COMMON, mage.cards.m.Mulldrifter.class));
        cards.add(new SetCardInfo("Ophidian", 9, Rarity.COMMON, mage.cards.o.Ophidian.class));
        cards.add(new SetCardInfo("Oxidda Golem", 46, Rarity.COMMON, mage.cards.o.OxiddaGolem.class));
        cards.add(new SetCardInfo("Pyre Charger", 38, Rarity.UNCOMMON, mage.cards.p.PyreCharger.class));
        cards.add(new SetCardInfo("Quicksilver Dragon", 19, Rarity.RARE, mage.cards.q.QuicksilverDragon.class));
        cards.add(new SetCardInfo("Rakdos Pit Dragon", 44, Rarity.RARE, mage.cards.r.RakdosPitDragon.class));
        cards.add(new SetCardInfo("Repulse", 25, Rarity.COMMON, mage.cards.r.Repulse.class));
        cards.add(new SetCardInfo("Riftwing Cloudskate", 15, Rarity.UNCOMMON, mage.cards.r.RiftwingCloudskate.class));
        cards.add(new SetCardInfo("Seal of Fire", 50, Rarity.COMMON, mage.cards.s.SealOfFire.class));
        cards.add(new SetCardInfo("Slith Firewalker", 39, Rarity.UNCOMMON, mage.cards.s.SlithFirewalker.class));
        cards.add(new SetCardInfo("Soulbright Flamekin", 37, Rarity.COMMON, mage.cards.s.SoulbrightFlamekin.class));
        cards.add(new SetCardInfo("Spire Golem", 16, Rarity.COMMON, mage.cards.s.SpireGolem.class));
        cards.add(new SetCardInfo("Terrain Generator", 29, Rarity.UNCOMMON, mage.cards.t.TerrainGenerator.class));
        cards.add(new SetCardInfo("Voidmage Apprentice", 4, Rarity.COMMON, mage.cards.v.VoidmageApprentice.class));
        cards.add(new SetCardInfo("Wall of Deceit", 5, Rarity.UNCOMMON, mage.cards.w.WallOfDeceit.class));
        cards.add(new SetCardInfo("Waterspout Djinn", 11, Rarity.UNCOMMON, mage.cards.w.WaterspoutDjinn.class));
        cards.add(new SetCardInfo("Willbender", 6, Rarity.UNCOMMON, mage.cards.w.Willbender.class));
    }
}
