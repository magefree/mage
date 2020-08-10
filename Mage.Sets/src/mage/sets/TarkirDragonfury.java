package mage.sets;

import mage.cards.ExpansionSet;
import mage.constants.Rarity;
import mage.constants.SetType;

/**
 * https://scryfall.com/sets/ptkdf
 */
public class TarkirDragonfury extends ExpansionSet {

    private static final TarkirDragonfury instance = new TarkirDragonfury();

    public static TarkirDragonfury getInstance() {
        return instance;
    }

    private TarkirDragonfury() {
        super("Tarkir Dragonfury", "PTKDF", ExpansionSet.buildDate(2015, 4, 3), SetType.PROMOTIONAL);
        this.hasBoosters = false;
        this.hasBasicLands = false;

        cards.add(new SetCardInfo("Dragon Fodder", 135, Rarity.COMMON, mage.cards.d.DragonFodder.class));
        cards.add(new SetCardInfo("Dragonlord's Servant", 138, Rarity.UNCOMMON, mage.cards.d.DragonlordsServant.class));
        cards.add(new SetCardInfo("Evolving Wilds", 248, Rarity.COMMON, mage.cards.e.EvolvingWilds.class));
        cards.add(new SetCardInfo("Foe-Razer Regent", 187, Rarity.RARE, mage.cards.f.FoeRazerRegent.class));
     }
}
