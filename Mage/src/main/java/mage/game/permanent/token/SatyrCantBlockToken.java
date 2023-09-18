package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class SatyrCantBlockToken extends TokenImpl {

    public SatyrCantBlockToken() {
        super("Satyr Token", "1/1 red Satyr creature token with \"This creature can't block.\"");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.SATYR);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new SimpleStaticAbility(new CantBlockSourceEffect(Duration.WhileOnBattlefield)
                .setText("this creature can't block")));
    }

    private SatyrCantBlockToken(final SatyrCantBlockToken token) {
        super(token);
    }

    public SatyrCantBlockToken copy() {
        return new SatyrCantBlockToken(this);
    }
}
