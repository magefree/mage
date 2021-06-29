package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/pisd
 */
public class InnistradPromos extends ExpansionSet {

    private static final InnistradPromos instance = new InnistradPromos();

    public static InnistradPromos getInstance() {
        return instance;
    }

    private InnistradPromos() {
        super("Innistrad Promos", "PISD", ExpansionSet.buildDate(2011, 9, 24), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Devil's Play", 140, Rarity.RARE, mage.cards.d.DevilsPlay.class));
        cards.add(new SetCardInfo("Diregraf Ghoul", 97, Rarity.UNCOMMON, mage.cards.d.DiregrafGhoul.class));
        cards.add(new SetCardInfo("Elite Inquisitor", 13, Rarity.RARE, mage.cards.e.EliteInquisitor.class));
        cards.add(new SetCardInfo("Howlpack Alpha", "193*", Rarity.RARE, mage.cards.h.HowlpackAlpha.class));
        cards.add(new SetCardInfo("Ludevic's Abomination", "64*", Rarity.RARE, mage.cards.l.LudevicsAbomination.class));
        cards.add(new SetCardInfo("Ludevic's Test Subject", "64*", Rarity.RARE, mage.cards.l.LudevicsTestSubject.class));
        cards.add(new SetCardInfo("Mayor of Avabruck", "193*", Rarity.RARE, mage.cards.m.MayorOfAvabruck.class));

    }
}
