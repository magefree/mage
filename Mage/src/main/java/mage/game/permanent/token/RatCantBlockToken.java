package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 * @author Susucr
 */
public final class RatCantBlockToken extends TokenImpl {

    public RatCantBlockToken() {
        super("Rat Token", "1/1 black Rat creature token with \"This creature can't block.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.RAT);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new SimpleStaticAbility(
                new CantBlockSourceEffect(Duration.WhileOnBattlefield)
                        .setText("this creature can't block")
        ));
    }

    protected RatCantBlockToken(final RatCantBlockToken token) {
        super(token);
    }

    public RatCantBlockToken copy() {
        return new RatCantBlockToken(this);
    }
}
