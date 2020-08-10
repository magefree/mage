package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/cp3
 */
public class MagicOriginsClashPack extends ExpansionSet {

    private static final MagicOriginsClashPack instance = new MagicOriginsClashPack();

    public static MagicOriginsClashPack getInstance() {
        return instance;
    }

    private MagicOriginsClashPack() {
        super("Magic Origins Clash Pack", "CP3", ExpansionSet.buildDate(2015, 7, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Dromoka, the Eternal", 4, Rarity.RARE, mage.cards.d.DromokaTheEternal.class));
        cards.add(new SetCardInfo("Honored Hierarch", 1, Rarity.RARE, mage.cards.h.HonoredHierarch.class));
        cards.add(new SetCardInfo("Sandsteppe Citadel", 6, Rarity.UNCOMMON, mage.cards.s.SandsteppeCitadel.class));
        cards.add(new SetCardInfo("Seeker of the Way", 2, Rarity.UNCOMMON, mage.cards.s.SeekerOfTheWay.class));
        cards.add(new SetCardInfo("Siege Rhino", 5, Rarity.RARE, mage.cards.s.SiegeRhino.class));
        cards.add(new SetCardInfo("Valorous Stance", 3, Rarity.UNCOMMON, mage.cards.v.ValorousStance.class));
     }
}
