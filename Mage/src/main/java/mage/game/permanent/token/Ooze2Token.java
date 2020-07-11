

package mage.game.permanent.token;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;

/**
 *
 * @author spjspj
 */
public final class Ooze2Token extends TokenImpl {

    public Ooze2Token() {
        super("Ooze", "2/2 green Ooze creature tokens with \"When this creature is put into a graveyard, create two 1/1 green Ooze creature tokens.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.OOZE);
        color.setGreen(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new OozeToken(1, 1), 2), false));
    }

    public Ooze2Token(final Ooze2Token token) {
        super(token);
    }

    public Ooze2Token copy() {
        return new Ooze2Token(this);
    }
}
