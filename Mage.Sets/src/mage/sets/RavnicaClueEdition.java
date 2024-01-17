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

        cards.add(new SetCardInfo("Commander Mustard", 6, Rarity.RARE, mage.cards.c.CommanderMustard.class));
        cards.add(new SetCardInfo("Dining Room", 15, Rarity.UNCOMMON, mage.cards.d.DiningRoom.class));
        cards.add(new SetCardInfo("Hallowed Fountain", 277, Rarity.RARE, mage.cards.h.HallowedFountain.class));
        cards.add(new SetCardInfo("Knife", 10, Rarity.UNCOMMON, mage.cards.k.Knife.class));
        cards.add(new SetCardInfo("Lavinia, Foil to Conspiracy", 36, Rarity.RARE, mage.cards.l.LaviniaFoilToConspiracy.class));
        cards.add(new SetCardInfo("Lead Pipe", 9, Rarity.UNCOMMON, mage.cards.l.LeadPipe.class));
        cards.add(new SetCardInfo("Library", 18, Rarity.UNCOMMON, mage.cards.l.Library.class));
        cards.add(new SetCardInfo("Senator Peacock", 2, Rarity.RARE, mage.cards.s.SenatorPeacock.class));
        cards.add(new SetCardInfo("Steam Vents", 280, Rarity.RARE, mage.cards.s.SteamVents.class));
    }
}
