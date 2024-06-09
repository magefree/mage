package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class DogVigilanceToken extends TokenImpl {

    public DogVigilanceToken() {
        super("Dog Token", "3/1 green Dog creature token with vigilance");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.DOG);

        color.setGreen(true);
        power = new MageInt(3);
        toughness = new MageInt(1);

        this.addAbility(VigilanceAbility.getInstance());
    }

    private DogVigilanceToken(final DogVigilanceToken token) {
        super(token);
    }

    public DogVigilanceToken copy() {
        return new DogVigilanceToken(this);
    }
}
