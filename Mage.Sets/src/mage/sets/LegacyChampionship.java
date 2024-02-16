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
        cards.add(new SetCardInfo("Bayou", "2019A", Rarity.SPECIAL, mage.cards.b.Bayou.class));
        cards.add(new SetCardInfo("Brainstorm", 2012, Rarity.RARE, mage.cards.b.Brainstorm.class));
        cards.add(new SetCardInfo("City of Traitors", "2019NA", Rarity.SPECIAL, mage.cards.c.CityOfTraitors.class));
        cards.add(new SetCardInfo("Force of Will", 2011, Rarity.RARE, mage.cards.f.ForceOfWill.class));
        cards.add(new SetCardInfo("Gaea's Cradle", 2014, Rarity.SPECIAL, mage.cards.g.GaeasCradle.class));
        cards.add(new SetCardInfo("Plateau", "2018A", Rarity.SPECIAL, mage.cards.p.Plateau.class));
        cards.add(new SetCardInfo("Savannah", "2017NA", Rarity.SPECIAL, mage.cards.s.Savannah.class));
        cards.add(new SetCardInfo("Scrubland", 2018, Rarity.SPECIAL, mage.cards.s.Scrubland.class));
        cards.add(new SetCardInfo("Taiga", "2017EU", Rarity.SPECIAL, mage.cards.t.Taiga.class));
        cards.add(new SetCardInfo("Tundra", 2015, Rarity.SPECIAL, mage.cards.t.Tundra.class));
        cards.add(new SetCardInfo("Underground Sea", "2016EU", Rarity.SPECIAL, mage.cards.u.UndergroundSea.class));
        cards.add(new SetCardInfo("Volcanic Island", "2018NA", Rarity.SPECIAL, mage.cards.v.VolcanicIsland.class));
        cards.add(new SetCardInfo("Wasteland", 2013, Rarity.UNCOMMON, mage.cards.w.Wasteland.class));
    }
}
