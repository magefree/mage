package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

public final class DinosaurCatToken extends TokenImpl {
    public DinosaurCatToken() {
        super("Dinosaur Cat Token", "2/2 red and white Dinosaur Cat creature token");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setWhite(true);
        subtype.add(SubType.DINOSAUR);
        subtype.add(SubType.CAT);
        power = new MageInt(2);
        toughness = new MageInt(2);
        setOriginalExpansionSetCode("C20");
    }

    public DinosaurCatToken(final DinosaurCatToken token) {
        super(token);
    }

    @Override
    public DinosaurCatToken copy() {
        return new DinosaurCatToken(this);
    }
}
