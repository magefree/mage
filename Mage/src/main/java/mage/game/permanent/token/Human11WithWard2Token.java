package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.keyword.WardAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 * @author padfoothelix
 */
public final class Human11WithWard2Token extends TokenImpl {

    public Human11WithWard2Token() {
        super("Human Token", "1/1 white Human creature token with ward {2}");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.HUMAN);
        power = new MageInt(1);
        toughness = new MageInt(1);
	this.addAbility(new WardAbility(new GenericManaCost(2)));
    }

    private Human11WithWard2Token(final Human11WithWard2Token token) {
        super(token);
    }

    @Override
    public Human11WithWard2Token copy() {
        return new Human11WithWard2Token(this);
    }
}
