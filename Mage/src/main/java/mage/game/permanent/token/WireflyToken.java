package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class WireflyToken extends TokenImpl {

    public WireflyToken() {
        super("Wirefly", "2/2 colorless Insect artifact creature token with flying named Wirefly");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.subtype.add(SubType.INSECT);
        this.cardType.add(CardType.ARTIFACT);
        this.cardType.add(CardType.CREATURE);
        this.addAbility(FlyingAbility.getInstance());
    }

    public WireflyToken(final WireflyToken token) {
        super(token);
    }

    public WireflyToken copy() {
        return new WireflyToken(this);
    }
}
