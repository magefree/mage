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
public final class FungusCantBlockToken extends TokenImpl {

    public FungusCantBlockToken() {
        super("Fungus Token", "1/1 black Fungus creature token with \"This creature can't block.\"");
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.FUNGUS);
        power = new MageInt(1);
        toughness = new MageInt(1);

        this.addAbility(new SimpleStaticAbility(
                new CantBlockSourceEffect(Duration.WhileOnBattlefield)
                        .setText("this creature can't block")
        ));
    }

    private FungusCantBlockToken(final FungusCantBlockToken token) {
        super(token);
    }

    public FungusCantBlockToken copy() {
        return new FungusCantBlockToken(this);
    }
}
