package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class DinosaurSoldierToken extends TokenImpl {

    public DinosaurSoldierToken() {
        super("Dinosaur Soldier Token", "2/2 white Dinosaur Soldier creature token");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.DINOSAUR);
        subtype.add(SubType.SOLDIER);
        power = new MageInt(2);
        toughness = new MageInt(2);
    }

    private DinosaurSoldierToken(final DinosaurSoldierToken token) {
        super(token);
    }

    @Override
    public DinosaurSoldierToken copy() {
        return new DinosaurSoldierToken(this);
    }
}
