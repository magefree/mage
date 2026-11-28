package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/olgc
 */
public class LegacyChampionship extends ExpansionSet {

    private static final LegacyChampionship instance = new LegacyChampionship();

    public static LegacyChampionship getInstance() {
        return instance;
    }

    private LegacyChampionship() {
        super("Legacy Championship", "OLGC", ExpansionSet.buildDate(2019, 11, 3), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Badlands", "2016NA", Rarity.SPECIAL, mage.cards.b.Badlands.class));
        cards.add(new SetCardInfo("Bayou", "2019A", Rarity.SPECIAL, mage.cards.b.Bayou.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Bayou", "2021B", Rarity.MYTHIC, mage.cards.b.Bayou.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Brainstorm", 2012, Rarity.RARE, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("City of Traitors", "2019NA", Rarity.SPECIAL, mage.cards.c.CityOfTraitors.class));
        cards.add(new SetCardInfo("Force of Will", 2011, Rarity.RARE, mage.cards.f.ForceOfWill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Force of Will", "2023EU", Rarity.SPECIAL, mage.cards.f.ForceOfWill.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gaea's Cradle", 2014, Rarity.SPECIAL, mage.cards.g.GaeasCradle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Gaea's Cradle", "2022A", Rarity.SPECIAL, mage.cards.g.GaeasCradle.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Karakas", "2020A", Rarity.MYTHIC, mage.cards.k.Karakas.class));
        cards.add(new SetCardInfo("Lightning Bolt", 2023, Rarity.SPECIAL, mage.cards.l.LightningBolt.class));
        cards.add(new SetCardInfo("Maze of Ith", "2020C", Rarity.MYTHIC, mage.cards.m.MazeOfIth.class));
        cards.add(new SetCardInfo("Plateau", "2018A", Rarity.SPECIAL, mage.cards.p.Plateau.class));
        cards.add(new SetCardInfo("Rishadan Port", "2020B", Rarity.MYTHIC, mage.cards.r.RishadanPort.class));
        cards.add(new SetCardInfo("Savannah", "2017NA", Rarity.SPECIAL, mage.cards.s.Savannah.class));
        cards.add(new SetCardInfo("Scrubland", 2018, Rarity.SPECIAL, mage.cards.s.Scrubland.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Scrubland", "2022B", Rarity.SPECIAL, mage.cards.s.Scrubland.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Sylvan Library", "2021C", Rarity.MYTHIC, mage.cards.s.SylvanLibrary.class));
        cards.add(new SetCardInfo("Taiga", "2017EU", Rarity.SPECIAL, mage.cards.t.Taiga.class));
        cards.add(new SetCardInfo("The Tabernacle at Pendrell Vale", "2022C", Rarity.SPECIAL, mage.cards.t.TheTabernacleAtPendrellVale.class));
        cards.add(new SetCardInfo("Tropical Island", 2019, Rarity.MYTHIC, mage.cards.t.TropicalIsland.class));
        cards.add(new SetCardInfo("Tundra", 2015, Rarity.SPECIAL, mage.cards.t.Tundra.class));
        cards.add(new SetCardInfo("Underground Sea", "2016EU", Rarity.SPECIAL, mage.cards.u.UndergroundSea.class));
        cards.add(new SetCardInfo("Volcanic Island", "2018NA", Rarity.SPECIAL, mage.cards.v.VolcanicIsland.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Volcanic Island", "2023A", Rarity.SPECIAL, mage.cards.v.VolcanicIsland.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wasteland", 2013, Rarity.UNCOMMON, mage.cards.w.Wasteland.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Wasteland", "2021A", Rarity.MYTHIC, mage.cards.w.Wasteland.class, NON_FULL_USE_VARIOUS));
    }
}
