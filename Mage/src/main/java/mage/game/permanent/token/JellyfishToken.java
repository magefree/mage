package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class JellyfishToken extends TokenImpl {

    public JellyfishToken() {
        super("Jellyfish Token", "1/1 blue Jellyfish creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.JELLYFISH);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
    }

    private JellyfishToken(final JellyfishToken token) {
        super(token);
    }

    public JellyfishToken copy() {
        return new JellyfishToken(this);
    }
}
