
package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pwcq
 * https://mtg.gamepedia.com/World_Magic_Cup_Qualifiers
 * @author fireshoes
 */
public final class WorldMagicCupQualifier extends ExpansionSet {

    private static final WorldMagicCupQualifier instance = new WorldMagicCupQualifier();

    public static WorldMagicCupQualifier getInstance() {
        return instance;
    }

    private WorldMagicCupQualifier() {
        super("World Magic Cup Qualifier", "PWCQ", ExpansionSet.buildDate(2011, 6, 17), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Abrupt Decay", 2016, Rarity.RARE, mage.cards.a.AbruptDecay.class));
        cards.add(new SetCardInfo("Geist of Saint Traft", 2014, Rarity.MYTHIC, mage.cards.g.GeistOfSaintTraft.class));
        cards.add(new SetCardInfo("Inkmoth Nexus", 2017, Rarity.SPECIAL, mage.cards.i.InkmothNexus.class));
        cards.add(new SetCardInfo("Thalia, Guardian of Thraben", 2015, Rarity.RARE, mage.cards.t.ThaliaGuardianOfThraben.class));
        cards.add(new SetCardInfo("Vengevine", 2013, Rarity.MYTHIC, mage.cards.v.Vengevine.class));
    }

}
