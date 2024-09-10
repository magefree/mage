package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class DinosaurFlyingHasteToken extends TokenImpl {

    public DinosaurFlyingHasteToken() {
        super("Dinosaur Token", "2/2 red and white Dinosaur creature token with flying and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setWhite(true);
        subtype.add(SubType.DINOSAUR);
        power = new MageInt(2);
        toughness = new MageInt(2);

        addAbility(FlyingAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }

    private DinosaurFlyingHasteToken(final DinosaurFlyingHasteToken token) {
        super(token);
    }

    public DinosaurFlyingHasteToken copy() {
        return new DinosaurFlyingHasteToken(this);
    }
}
