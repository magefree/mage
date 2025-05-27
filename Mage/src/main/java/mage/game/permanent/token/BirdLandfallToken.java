package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 * @author LoneFox
 */
public final class BirdLandfallToken extends TokenImpl {

    public BirdLandfallToken() {
        super("Bird Token", "2/2 green Bird creature token with \"Whenever a land you control enters, this token gets +1/+0 until end of turn.\"");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.BIRD);
        power = new MageInt(2);
        toughness = new MageInt(2);

        addAbility(new LandfallAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn)).setAbilityWord(null));
    }

    private BirdLandfallToken(final BirdLandfallToken token) {
        super(token);
    }

    @Override
    public BirdLandfallToken copy() {
        return new BirdLandfallToken(this);
    }
}
