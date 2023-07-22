package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author TheElk801
 */
public final class AvacynToken extends TokenImpl {

    public AvacynToken() {
        super("Avacyn", "Avacyn, a legendary 8/8 white Angel creature token with flying, vigilance, and indestructible");
        supertype.add(SuperType.LEGENDARY);
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.ANGEL);
        power = new MageInt(8);
        toughness = new MageInt(8);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(IndestructibleAbility.getInstance());
        this.addAbility(VigilanceAbility.getInstance());
    }

    public AvacynToken(final AvacynToken token) {
        super(token);
    }

    public AvacynToken copy() {
        return new AvacynToken(this);
    }
}
