package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/purl
 */
public class URLConventionPromos extends ExpansionSet {

    private static final URLConventionPromos instance = new URLConventionPromos();

    public static URLConventionPromos getInstance() {
        return instance;
    }

    private URLConventionPromos() {
        super("URL/Convention Promos", "PURL", ExpansionSet.buildDate(2015, 1, 23), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Aeronaut Tinkerer", 8, Rarity.COMMON, mage.cards.a.AeronautTinkerer.class));
        cards.add(new SetCardInfo("Bloodthrone Vampire", 3, Rarity.RARE, mage.cards.b.BloodthroneVampire.class));
        cards.add(new SetCardInfo("Chandra's Fury", 5, Rarity.RARE, mage.cards.c.ChandrasFury.class));
        cards.add(new SetCardInfo("Kor Skyfisher", 2, Rarity.RARE, mage.cards.k.KorSkyfisher.class));
        cards.add(new SetCardInfo("Merfolk Mesmerist", 4, Rarity.RARE, mage.cards.m.MerfolkMesmerist.class));
        // Italian-only printing
        //cards.add(new SetCardInfo("Relentless Rats", 9, Rarity.RARE, mage.cards.r.RelentlessRats.class));
        // Japanese-only printing
        //cards.add(new SetCardInfo("Shepherd of the Lost", "34*", Rarity.UNCOMMON, mage.cards.s.ShepherdOfTheLost.class));
        cards.add(new SetCardInfo("Stealer of Secrets", 7, Rarity.RARE, mage.cards.s.StealerOfSecrets.class));
        cards.add(new SetCardInfo("Steward of Valeron", 1, Rarity.RARE, mage.cards.s.StewardOfValeron.class));
     }
}
