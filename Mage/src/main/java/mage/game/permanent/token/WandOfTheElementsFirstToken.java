package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class WandOfTheElementsFirstToken extends TokenImpl {

    public WandOfTheElementsFirstToken() {
        super("Elemental Token", "2/2 blue Elemental creature token with flying");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.ELEMENTAL);
        this.color.setBlue(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());

        setTokenType(1);
    }

    public WandOfTheElementsFirstToken(final WandOfTheElementsFirstToken token) {
        super(token);
    }

    public WandOfTheElementsFirstToken copy() {
        return new WandOfTheElementsFirstToken(this);
    }
}
