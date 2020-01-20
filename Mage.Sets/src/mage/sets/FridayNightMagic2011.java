package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/f11
 */
public class FridayNightMagic2011 extends ExpansionSet {

    private static final FridayNightMagic2011 instance = new FridayNightMagic2011();

    public static FridayNightMagic2011 getInstance() {
        return instance;
    }

    private FridayNightMagic2011() {
        super("Friday Night Magic 2011", "F11", ExpansionSet.buildDate(2011, 1, 1), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Artisan of Kozilek", 4, Rarity.RARE, mage.cards.a.ArtisanOfKozilek.class));
        cards.add(new SetCardInfo("Contagion Clasp", 10, Rarity.RARE, mage.cards.c.ContagionClasp.class));
        cards.add(new SetCardInfo("Cultivate", 8, Rarity.RARE, mage.cards.c.Cultivate.class));
        cards.add(new SetCardInfo("Everflowing Chalice", 1, Rarity.RARE, mage.cards.e.EverflowingChalice.class));
        cards.add(new SetCardInfo("Go for the Throat", 11, Rarity.RARE, mage.cards.g.GoForTheThroat.class));
        cards.add(new SetCardInfo("Jace's Ingenuity", 7, Rarity.RARE, mage.cards.j.JacesIngenuity.class));
        cards.add(new SetCardInfo("Rhox War Monk", 6, Rarity.RARE, mage.cards.r.RhoxWarMonk.class));
        cards.add(new SetCardInfo("Savage Lands", 12, Rarity.RARE, mage.cards.s.SavageLands.class));
        cards.add(new SetCardInfo("Spellstutter Sprite", 2, Rarity.RARE, mage.cards.s.SpellstutterSprite.class));
        cards.add(new SetCardInfo("Squadron Hawk", 5, Rarity.RARE, mage.cards.s.SquadronHawk.class));
        cards.add(new SetCardInfo("Teetering Peaks", 9, Rarity.RARE, mage.cards.t.TeeteringPeaks.class));
        cards.add(new SetCardInfo("Wall of Omens", 3, Rarity.RARE, mage.cards.w.WallOfOmens.class));
     }
}
