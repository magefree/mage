package mage.deck;

import mage.cards.decks.Constructed;

/**
 *
 * @author LevelX2
 */
public class StarWarsBlock extends Constructed {

    public StarWarsBlock() {
        super("Constructed Custom - Star Wars Block");
        setCodes.add(mage.sets.StarWars.getInstance().getCode());
    }

}
