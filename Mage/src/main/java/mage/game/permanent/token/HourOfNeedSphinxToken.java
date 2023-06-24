package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class HourOfNeedSphinxToken extends TokenImpl {

    public HourOfNeedSphinxToken() {
        super("Sphinx Token", "4/4 blue Sphinx creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.SPHINX);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }

    public HourOfNeedSphinxToken(final HourOfNeedSphinxToken token) {
        super(token);
    }

    public HourOfNeedSphinxToken copy() {
        return new HourOfNeedSphinxToken(this);
    }
}
