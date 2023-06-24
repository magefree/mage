package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class TyranidGargoyleToken extends TokenImpl {

    public TyranidGargoyleToken() {
        super("Tyranid Gargoyle Token", "1/1 blue Tyranid Gargoyle creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setBlue(true);
        subtype.add(SubType.TYRANID);
        subtype.add(SubType.GARGOYLE);
        power = new MageInt(1);
        toughness = new MageInt(1);

        addAbility(FlyingAbility.getInstance());
    }

    public TyranidGargoyleToken(final TyranidGargoyleToken token) {
        super(token);
    }

    @Override
    public TyranidGargoyleToken copy() {
        return new TyranidGargoyleToken(this);
    }
}
