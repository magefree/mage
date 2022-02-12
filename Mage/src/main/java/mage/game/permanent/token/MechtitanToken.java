package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 * @author TheElk801
 */
public final class MechtitanToken extends TokenImpl {

    public MechtitanToken() {
        super("Mechtitan", "Mechtitan, a legendary 10/10 Construct artifact creature token with flying, vigilance, trample, lifelink, and haste that's all colors");
        addSuperType(SuperType.LEGENDARY);
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.CONSTRUCT);
        color.setWhite(true);
        color.setBlue(true);
        color.setBlack(true);
        color.setRed(true);
        color.setGreen(true);
        power = new MageInt(10);
        toughness = new MageInt(10);
        addAbility(FlyingAbility.getInstance());
        addAbility(VigilanceAbility.getInstance());
        addAbility(TrampleAbility.getInstance());
        addAbility(LifelinkAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }

    public MechtitanToken(final MechtitanToken token) {
        super(token);
    }

    public MechtitanToken copy() {
        return new MechtitanToken(this);
    }
}
