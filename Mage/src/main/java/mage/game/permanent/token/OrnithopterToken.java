package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class OrnithopterToken extends TokenImpl {

    public OrnithopterToken() {
        super("Ornithopter", "0/2 colorless Thopter artifact creature token with flying named Ornithopter");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.THOPTER);
        power = new MageInt(0);
        toughness = new MageInt(2);

        addAbility(FlyingAbility.getInstance());
    }

    public OrnithopterToken(final OrnithopterToken token) {
        super(token);
    }

    @Override
    public OrnithopterToken copy() {
        return new OrnithopterToken(this);
    }
}
