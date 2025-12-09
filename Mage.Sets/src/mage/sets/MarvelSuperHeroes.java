package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class MarvelSuperHeroes extends ExpansionSet {

    private static final MarvelSuperHeroes instance = new MarvelSuperHeroes();

    public static MarvelSuperHeroes getInstance() {
        return instance;
    }

    private MarvelSuperHeroes() {
        super("Marvel Super Heroes", "MSH", ExpansionSet.buildDate(2026, 6, 26), SetType.EXPANSION);
        this.blockName = "Marvel Super Heroes"; // for sorting in GUI
        this.hasBasicLands = false; // temporary

        cards.add(new SetCardInfo("Attuma, Atlantean Warlord", 47, Rarity.UNCOMMON, mage.cards.a.AttumaAtlanteanWarlord.class));
        cards.add(new SetCardInfo("Captain America, Super-Soldier", 387, Rarity.MYTHIC, mage.cards.c.CaptainAmericaSuperSoldier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Captain America, Super-Soldier", 9, Rarity.MYTHIC, mage.cards.c.CaptainAmericaSuperSoldier.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Moon Girl and Devil Dinosaur", 223, Rarity.RARE, mage.cards.m.MoonGirlAndDevilDinosaur.class));
        cards.add(new SetCardInfo("Quicksilver, Brash Blur", 148, Rarity.RARE, mage.cards.q.QuicksilverBrashBlur.class));
        cards.add(new SetCardInfo("Super-Skrull", 115, Rarity.RARE, mage.cards.s.SuperSkrull.class));
        cards.add(new SetCardInfo("The Coming of Galactus", 212, Rarity.MYTHIC, mage.cards.t.TheComingOfGalactus.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("The Coming of Galactus", 307, Rarity.MYTHIC, mage.cards.t.TheComingOfGalactus.class, NON_FULL_USE_VARIOUS));
    }
}
