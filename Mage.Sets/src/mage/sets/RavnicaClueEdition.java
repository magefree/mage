package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * @author TheElk801
 */
public final class RavnicaClueEdition extends ExpansionSet {

    private static final RavnicaClueEdition instance = new RavnicaClueEdition();

    public static RavnicaClueEdition getInstance() {
        return instance;
    }

    private RavnicaClueEdition() {
        super("Ravnica: Clue Edition", "CLU", ExpansionSet.buildDate(2024, 2, 9), SetType.EXPANSION);
        this.hasBasicLands = false;
        this.hasBoosters = false;

        cards.add(new SetCardInfo("Hallowed Fountain", 277, Rarity.RARE, mage.cards.h.HallowedFountain.class));
        cards.add(new SetCardInfo("Lead Pipe", 9, Rarity.UNCOMMON, mage.cards.l.LeadPipe.class));
        cards.add(new SetCardInfo("Steam Vents", 280, Rarity.RARE, mage.cards.s.SteamVents.class));
    }
}
