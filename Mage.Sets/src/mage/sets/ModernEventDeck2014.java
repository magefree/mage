package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/md1
 */
public class ModernEventDeck2014 extends ExpansionSet {

    private static final ModernEventDeck2014 instance = new ModernEventDeck2014();

    public static ModernEventDeck2014 getInstance() {
        return instance;
    }

    private ModernEventDeck2014() {
        super("Modern Event Deck 2014", "MD1", ExpansionSet.buildDate(2014, 5, 30), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = true;

        cards.add(new SetCardInfo("Burrenton Forge-Tender", 22, Rarity.UNCOMMON, mage.cards.b.BurrentonForgeTender.class));
        cards.add(new SetCardInfo("Caves of Koilos", 14, Rarity.RARE, mage.cards.c.CavesOfKoilos.class));
        cards.add(new SetCardInfo("City of Brass", 15, Rarity.RARE, mage.cards.c.CityOfBrass.class));
        cards.add(new SetCardInfo("Dismember", 25, Rarity.UNCOMMON, mage.cards.d.Dismember.class));
        cards.add(new SetCardInfo("Duress", 23, Rarity.COMMON, mage.cards.d.Duress.class));
        cards.add(new SetCardInfo("Elspeth, Knight-Errant", 13, Rarity.MYTHIC, mage.cards.e.ElspethKnightErrant.class));
        cards.add(new SetCardInfo("Ghost Quarter", 26, Rarity.UNCOMMON, mage.cards.g.GhostQuarter.class));
        cards.add(new SetCardInfo("Honor of the Pure", 6, Rarity.RARE, mage.cards.h.HonorOfThePure.class));
        cards.add(new SetCardInfo("Inquisition of Kozilek", 4, Rarity.UNCOMMON, mage.cards.i.InquisitionOfKozilek.class));
        cards.add(new SetCardInfo("Intangible Virtue", 7, Rarity.UNCOMMON, mage.cards.i.IntangibleVirtue.class));
        cards.add(new SetCardInfo("Isolated Chapel", 16, Rarity.RARE, mage.cards.i.IsolatedChapel.class));
        cards.add(new SetCardInfo("Kataki, War's Wage", 24, Rarity.RARE, mage.cards.k.KatakiWarsWage.class));
        cards.add(new SetCardInfo("Lingering Souls", 11, Rarity.UNCOMMON, mage.cards.l.LingeringSouls.class));
        cards.add(new SetCardInfo("Path to Exile", 3, Rarity.UNCOMMON, mage.cards.p.PathToExile.class));
        cards.add(new SetCardInfo("Plains", 19, Rarity.LAND, mage.cards.basiclands.Plains.class));
        cards.add(new SetCardInfo("Raise the Alarm", 8, Rarity.COMMON, mage.cards.r.RaiseTheAlarm.class));
        cards.add(new SetCardInfo("Relic of Progenitus", 21, Rarity.UNCOMMON, mage.cards.r.RelicOfProgenitus.class));
        cards.add(new SetCardInfo("Shrine of Loyal Legions", 5, Rarity.UNCOMMON, mage.cards.s.ShrineOfLoyalLegions.class));
        cards.add(new SetCardInfo("Soul Warden", 1, Rarity.COMMON, mage.cards.s.SoulWarden.class));
        cards.add(new SetCardInfo("Spectral Procession", 12, Rarity.UNCOMMON, mage.cards.s.SpectralProcession.class));
        cards.add(new SetCardInfo("Swamp", 20, Rarity.LAND, mage.cards.basiclands.Swamp.class));
        cards.add(new SetCardInfo("Sword of Feast and Famine", 10, Rarity.MYTHIC, mage.cards.s.SwordOfFeastAndFamine.class));
        cards.add(new SetCardInfo("Tidehollow Sculler", 2, Rarity.UNCOMMON, mage.cards.t.TidehollowSculler.class));
        cards.add(new SetCardInfo("Vault of the Archangel", 17, Rarity.RARE, mage.cards.v.VaultOfTheArchangel.class));
        cards.add(new SetCardInfo("Windbrisk Heights", 18, Rarity.RARE, mage.cards.w.WindbriskHeights.class));
        cards.add(new SetCardInfo("Zealous Persecution", 9, Rarity.UNCOMMON, mage.cards.z.ZealousPersecution.class));
     }
}
