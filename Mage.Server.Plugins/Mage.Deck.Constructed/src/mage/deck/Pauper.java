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

        // All attractions & sticker cards are banned. (to add if/when implemented)
        // this also includes the mtgo replacement "Name Sticker" Goblin.
        banned.add("\"Name Sticker\" Goblin");

        banned.add("Aarakocra Sneak");
        banned.add("All That Glitters");
        banned.add("Arcum's Astrolabe");
        banned.add("Atog");
        banned.add("Bonder's Ornament");
        banned.add("Chatterstorm");
        banned.add("Cloud of Faeries");
        banned.add("Cloudpost");
        banned.add("Cranial Plating");
        banned.add("Cranial Ram");
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
        banned.add("Monastery Swiftspear");
        banned.add("Mystic Sanctuary");
        banned.add("Peregrine Drake");
        banned.add("Prophetic Prism");
        banned.add("Sinkhole");
        banned.add("Stirring Bard");
        banned.add("Sojourner's Companion");
        banned.add("Temporal Fissure");
        banned.add("Treasure Cruise");
        banned.add("Underdark Explorer");
        banned.add("Vicious Battlerager");
    }
}
