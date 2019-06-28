package mage.deck;

import mage.cards.ExpansionSet;
import mage.cards.Sets;
import mage.cards.decks.Constructed;
import mage.constants.Rarity;

/**
 * @author LevelX2
 */
public class Pauper extends Constructed {

    public Pauper() {
        super("Constructed - Pauper");

        for (ExpansionSet set : Sets.getInstance().values()) {
            if (set.getSetType().isEternalLegal()) {
                setCodes.add(set.getCode());
            }
        }
        rarities.add(Rarity.COMMON);
        rarities.add(Rarity.LAND);

        banned.add("Cloud of Faeries");
        banned.add("Cloudpost");
        banned.add("Cranial Plating");
        banned.add("Daze");
        banned.add("Empty the Warrens");
        banned.add("Frantic Search");
        banned.add("Gitaxian Probe");
        banned.add("Grapeshot");
        banned.add("Gush");
        banned.add("Hight Tide");
        banned.add("Hymn to Tourach");
        banned.add("Invigorate");
        banned.add("Peregrine Drake");
        banned.add("Sinkhole");
        banned.add("Temporal Fissure");
        banned.add("Treasure Cruise");
    }
}
