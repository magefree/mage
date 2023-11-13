
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author Susucr
 */
public final class SpecialGuests extends ExpansionSet {

    private static final SpecialGuests instance = new SpecialGuests();

    public static SpecialGuests getInstance() {
        return instance;
    }

    private SpecialGuests() {
        super("Special Guests", "SPG", ExpansionSet.buildDate(2023, 11, 17), SetType.SUPPLEMENTAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Breeches, Brazen Plunderer", 6, Rarity.UNCOMMON, mage.cards.b.BreechesBrazenPlunderer.class));
        cards.add(new SetCardInfo("Bridge from Below", 3, Rarity.RARE, mage.cards.b.BridgeFromBelow.class));
        cards.add(new SetCardInfo("Carnage Tyrant", 10, Rarity.MYTHIC, mage.cards.c.CarnageTyrant.class));
        cards.add(new SetCardInfo("Dargo, the Shipwrecker", 7, Rarity.UNCOMMON, mage.cards.d.DargoTheShipwrecker.class));
        cards.add(new SetCardInfo("Ghalta, Primal Hunger", 11, Rarity.RARE, mage.cards.g.GhaltaPrimalHunger.class));
        cards.add(new SetCardInfo("Kalamax, the Stormsire", 13, Rarity.MYTHIC, mage.cards.k.KalamaxTheStormsire.class));
        cards.add(new SetCardInfo("Lord Windgrace", 14, Rarity.MYTHIC, mage.cards.l.LordWindgrace.class));
        cards.add(new SetCardInfo("Lord of Atlantis", 1, Rarity.RARE, mage.cards.l.LordOfAtlantis.class));
        cards.add(new SetCardInfo("Malcolm, Keen-Eyed Navigator", 2, Rarity.UNCOMMON, mage.cards.m.MalcolmKeenEyedNavigator.class));
        cards.add(new SetCardInfo("Mana Crypt", "17a", Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Crypt", "17b", Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Crypt", "17c", Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Crypt", "17d", Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Crypt", "17e", Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Crypt", "17f", Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mana Crypt", 17, Rarity.MYTHIC, mage.cards.m.ManaCrypt.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mephidross Vampire", 4, Rarity.RARE, mage.cards.m.MephidrossVampire.class));
        cards.add(new SetCardInfo("Mirri, Weatherlight Duelist", 15, Rarity.MYTHIC, mage.cards.m.MirriWeatherlightDuelist.class));
        cards.add(new SetCardInfo("Pitiless Plunderer", 5, Rarity.UNCOMMON, mage.cards.p.PitilessPlunderer.class));
        cards.add(new SetCardInfo("Polyraptor", 12, Rarity.MYTHIC, mage.cards.p.Polyraptor.class));
        cards.add(new SetCardInfo("Rampaging Ferocidon", 8, Rarity.RARE, mage.cards.r.RampagingFerocidon.class));
        cards.add(new SetCardInfo("Star Compass", 18, Rarity.UNCOMMON, mage.cards.s.StarCompass.class));
        cards.add(new SetCardInfo("Thrasios, Triton Hero", 16, Rarity.RARE, mage.cards.t.ThrasiosTritonHero.class));
        cards.add(new SetCardInfo("Underworld Breach", 9, Rarity.RARE, mage.cards.u.UnderworldBreach.class));
    }
}
