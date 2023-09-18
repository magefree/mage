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

        banned.add("Arcum's Astrolabe");
        banned.add("Atog");
        banned.add("Bonder's Ornament");
        banned.add("Chatterstorm");
        banned.add("Cloud of Faeries");
        banned.add("Cloudpost");
        banned.add("Cranial Plating");
        banned.add("Daze");
        banned.add("Disciple of the Vault");
        banned.add("Empty the Warrens");
        banned.add("Fall from Favor");
        banned.add("Frantic Search");
        banned.add("Galvanic Relay");
        banned.add("Gitaxian Probe");
        banned.add("Grapeshot");
        banned.add("Gush");
        banned.add("High Tide");
        banned.add("Hymn to Tourach");
        banned.add("Invigorate");
        banned.add("Mystic Sanctuary");
        banned.add("Peregrine Drake");
        banned.add("Prophetic Prism");
        banned.add("Sinkhole");
        banned.add("Sojourner's Companion");
        banned.add("Temporal Fissure");
        banned.add("Treasure Cruise");
    }
}
