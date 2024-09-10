package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CanBlockOnlyFlyingEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class FaerieBlockFliersToken extends TokenImpl {

    public FaerieBlockFliersToken() {
        super("Faerie Token", "1/1 blue Faerie creature token with flying and \"This creature can block only creatures with flying.\"");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.FAERIE);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(new CanBlockOnlyFlyingEffect(Duration.WhileOnBattlefield)
                .setText("this creature can block only creatures with flying")));
    }

    private FaerieBlockFliersToken(final FaerieBlockFliersToken token) {
        super(token);
    }

    public FaerieBlockFliersToken copy() {
        return new FaerieBlockFliersToken(this);
    }
}
