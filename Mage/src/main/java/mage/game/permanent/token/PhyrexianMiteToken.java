package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.abilities.keyword.ToxicAbility;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.Arrays;

/**
 * @author TheElk801
 */
public final class PhyrexianMiteToken extends TokenImpl {

    public PhyrexianMiteToken() {
        super("Phyrexian Mite Token", "1/1 colorless Phyrexian Mite artifact creature token with toxic 1 and \"This creature can't block.\"");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PHYREXIAN);
        subtype.add(SubType.MITE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new ToxicAbility(1));
        this.addAbility(new SimpleStaticAbility(new CantBlockSourceEffect(Duration.WhileOnBattlefield).setText("this creature can't block")));

        availableImageSetCodes = Arrays.asList("ONE");
    }

    public PhyrexianMiteToken(final PhyrexianMiteToken token) {
        super(token);
    }

    @Override
    public PhyrexianMiteToken copy() {
        return new PhyrexianMiteToken(this);
    }
}
