package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class DinosaurEggToken extends TokenImpl {

    public DinosaurEggToken() {
        super("Dinosaur Egg Token", "0/1 green Dinosaur Egg creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.DINOSAUR);
        subtype.add(SubType.EGG);
        color.setGreen(true);
        power = new MageInt(0);
        toughness = new MageInt(1);
    }

    private DinosaurEggToken(final DinosaurEggToken token) {
        super(token);
    }

    public DinosaurEggToken copy() {
        return new DinosaurEggToken(this);
    }
}
