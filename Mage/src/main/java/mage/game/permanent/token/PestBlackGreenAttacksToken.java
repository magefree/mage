package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class PestBlackGreenAttacksToken extends TokenImpl {

    public PestBlackGreenAttacksToken() {
        super("Pest Token", "1/1 black and green Pest creature token with \"Whenever this token attacks, you gain 1 life.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        color.setGreen(true);
        subtype.add(SubType.PEST);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new AttacksTriggeredAbility(new GainLifeEffect(1)));
    }

    private PestBlackGreenAttacksToken(final PestBlackGreenAttacksToken token) {
        super(token);
    }

    public PestBlackGreenAttacksToken copy() {
        return new PestBlackGreenAttacksToken(this);
    }
}
