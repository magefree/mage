

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.MillCardsControllerEffect;

/**
 * @author muz
 */
public final class MoloidToken extends TokenImpl {

    public MoloidToken() {
        super("Moloid", "1/1 green Minion creature token named Moloid with \"Whenever this token attacks, you may mill a card.\"");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.MINION);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new AttacksTriggeredAbility(new MillCardsControllerEffect(1), true));
    }

    private MoloidToken(final MoloidToken token) {
        super(token);
    }

    public MoloidToken copy() {
        return new MoloidToken(this);
    }
}
