package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pgtc
 */
public class GatecrashPromos extends ExpansionSet {

    private static final GatecrashPromos instance = new GatecrashPromos();

    public static GatecrashPromos getInstance() {
        return instance;
    }

    private GatecrashPromos() {
        super("Gatecrash Promos", "PGTC", ExpansionSet.buildDate(2013, 1, 26), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Consuming Aberration", "152*", Rarity.RARE, mage.cards.c.ConsumingAberration.class));
        cards.add(new SetCardInfo("Fathom Mage", "162*", Rarity.RARE, mage.cards.f.FathomMage.class));
        cards.add(new SetCardInfo("Firemane Avenger", 163, Rarity.RARE, mage.cards.f.FiremaneAvenger.class));
        cards.add(new SetCardInfo("Foundry Champion", "165*", Rarity.RARE, mage.cards.f.FoundryChampion.class));
        cards.add(new SetCardInfo("Nightveil Specter", "222*", Rarity.RARE, mage.cards.n.NightveilSpecter.class));
        cards.add(new SetCardInfo("Rubblehulk", "191*", Rarity.RARE, mage.cards.r.Rubblehulk.class));
        cards.add(new SetCardInfo("Skarrg Goliath", "133*", Rarity.RARE, mage.cards.s.SkarrgGoliath.class));
        cards.add(new SetCardInfo("Treasury Thrull", "201*", Rarity.RARE, mage.cards.t.TreasuryThrull.class));
        cards.add(new SetCardInfo("Zameck Guildmage", 209, Rarity.UNCOMMON, mage.cards.z.ZameckGuildmage.class));
    }
}
