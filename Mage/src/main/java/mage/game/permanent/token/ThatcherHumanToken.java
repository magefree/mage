
package mage.game.permanent.token;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class ThatcherHumanToken extends TokenImpl {

    public ThatcherHumanToken() {
        super("Human Token", "1/1 red Human creature token with haste");
        this.cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.HUMAN);
        addAbility(HasteAbility.getInstance());

        this.color.setRed(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
    }

    public ThatcherHumanToken(final ThatcherHumanToken token) {
        super(token);
    }

    public ThatcherHumanToken copy() {
        return new ThatcherHumanToken(this);
    }
}
