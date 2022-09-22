package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

/**
 * @author PurpleCrowbar
 */
public final class KavuAllColorToken extends TokenImpl {

    public KavuAllColorToken() {
        super("Kavu Token", "3/3 Kavu creature token with trample that's all colors");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.KAVU);
        color.setWhite(true);
        color.setBlue(true);
        color.setBlack(true);
        color.setRed(true);
        color.setGreen(true);
        power = new MageInt(3);
        toughness = new MageInt(3);
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new InfoEffect("This creature is all colors")));
        this.addAbility(TrampleAbility.getInstance());
        setOriginalExpansionSetCode("DMC");
    }

    public KavuAllColorToken(final KavuAllColorToken token) {
        super(token);
    }

    public KavuAllColorToken copy() {
        return new KavuAllColorToken(this);
    }
}
