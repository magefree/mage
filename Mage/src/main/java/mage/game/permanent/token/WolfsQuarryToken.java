package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class WolfsQuarryToken extends TokenImpl {

    public WolfsQuarryToken() {
        super("Boar Token", "1/1 green Boar creature token with \"When this creature dies, create a Food token.\"");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BOAR);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new DiesSourceTriggeredAbility(new CreateTokenEffect(new FoodToken())));
    }

    private WolfsQuarryToken(final WolfsQuarryToken token) {
        super(token);
    }

    public WolfsQuarryToken copy() {
        return new WolfsQuarryToken(this);
    }
}
