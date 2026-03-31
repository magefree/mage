package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class SecretsOfStrixhaven extends ExpansionSet {

    private static final SecretsOfStrixhaven instance = new SecretsOfStrixhaven();

    public static SecretsOfStrixhaven getInstance() {
        return instance;
    }

    private SecretsOfStrixhaven() {
        super("Secrets of Strixhaven", "SOS", ExpansionSet.buildDate(2026, 4, 24), SetType.EXPANSION);
        this.blockName = "Secrets of Strixhaven"; // for sorting in GUI
        this.hasBasicLands = false; // temporary

        cards.add(new SetCardInfo("Arcane Omens", 73, Rarity.UNCOMMON, mage.cards.a.ArcaneOmens.class));
        cards.add(new SetCardInfo("Archaic's Agony", 107, Rarity.UNCOMMON, mage.cards.a.ArchaicsAgony.class));
        cards.add(new SetCardInfo("Banishing Betrayal", 38, Rarity.COMMON, mage.cards.b.BanishingBetrayal.class));
        cards.add(new SetCardInfo("Bogwater Lumaret", 177, Rarity.COMMON, mage.cards.b.BogwaterLumaret.class));
        cards.add(new SetCardInfo("Grapple with Death", 192, Rarity.COMMON, mage.cards.g.GrappleWithDeath.class));
        cards.add(new SetCardInfo("Heated Argument", 118, Rarity.COMMON, mage.cards.h.HeatedArgument.class));
        cards.add(new SetCardInfo("Lorehold Charm", 200, Rarity.UNCOMMON, mage.cards.l.LoreholdCharm.class));
        cards.add(new SetCardInfo("Mathemagics", 320, Rarity.MYTHIC, mage.cards.m.Mathemagics.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Mathemagics", 58, Rarity.MYTHIC, mage.cards.m.Mathemagics.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Oracle's Restoration", 156, Rarity.COMMON, mage.cards.o.OraclesRestoration.class));
        cards.add(new SetCardInfo("Pull from the Grave", 95, Rarity.COMMON, mage.cards.p.PullFromTheGrave.class));
        cards.add(new SetCardInfo("Shattered Acolyte", 31, Rarity.COMMON, mage.cards.s.ShatteredAcolyte.class));
        cards.add(new SetCardInfo("Silverquill Charm", 225, Rarity.UNCOMMON, mage.cards.s.SilverquillCharm.class));
        cards.add(new SetCardInfo("Together as One", 307, Rarity.RARE, mage.cards.t.TogetherAsOne.class, NON_FULL_USE_VARIOUS));
        cards.add(new SetCardInfo("Together as One", 4, Rarity.RARE, mage.cards.t.TogetherAsOne.class, NON_FULL_USE_VARIOUS));
    }
}
