package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class NightwingHorrorToken extends TokenImpl {

    public NightwingHorrorToken() {
        super("Horror Token", "1/1 blue and black Horror creature token with flying");
        cardType.add(CardType.CREATURE);
        this.color.setBlue(true);
        this.color.setBlack(true);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
    }

    private NightwingHorrorToken(final NightwingHorrorToken token) {
        super(token);
    }

    public NightwingHorrorToken copy() {
        return new NightwingHorrorToken(this);
    }
}
