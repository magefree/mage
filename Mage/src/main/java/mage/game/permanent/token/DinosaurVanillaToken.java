package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class DinosaurVanillaToken extends TokenImpl {

    public DinosaurVanillaToken() {
        super("Dinosaur Token", "3/3 green Dinosaur creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.DINOSAUR);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    protected DinosaurVanillaToken(final DinosaurVanillaToken token) {
        super(token);
    }

    public DinosaurVanillaToken copy() {
        return new DinosaurVanillaToken(this);
    }
}
