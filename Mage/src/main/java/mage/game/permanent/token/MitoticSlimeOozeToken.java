package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class MitoticSlimeOozeToken extends TokenImpl {

    public MitoticSlimeOozeToken() {
        super("Ooze Token", "2/2 green Ooze creature tokens. They have \"When this creature dies, create two 1/1 green Ooze creature tokens.\"");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.OOZE);
        color.setGreen(true);
        power = new MageInt(2);
        toughness = new MageInt(2);
        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new OozeToken(1, 1), 2), false));
    }

    private MitoticSlimeOozeToken(final MitoticSlimeOozeToken token) {
        super(token);
    }

    public MitoticSlimeOozeToken copy() {
        return new MitoticSlimeOozeToken(this);
    }
}
