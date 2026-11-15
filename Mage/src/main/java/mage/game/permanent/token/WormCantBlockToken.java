package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBlockSourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 * @author muz
 */
public final class WormCantBlockToken extends TokenImpl {

    public WormCantBlockToken() {
        super("Worm Token", "2/2 red and green Worm creature token with \"This token can't block.\"");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        color.setGreen(true);
        subtype.add(SubType.WORM);
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(new SimpleStaticAbility(
            new CantBlockSourceEffect(Duration.WhileOnBattlefield).setText("this token can't block")
        ));
    }

    private WormCantBlockToken(final WormCantBlockToken token) {
        super(token);
    }

    public WormCantBlockToken copy() {
        return new WormCantBlockToken(this);
    }
}
