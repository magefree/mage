package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class CatBirdToken extends TokenImpl {

    public CatBirdToken() {
        super("Cat Bird Token", "1/1 white Cat Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setWhite(true);
        subtype.add(SubType.CAT);
        subtype.add(SubType.BIRD);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
        setOriginalExpansionSetCode("IKO");
    }

    private CatBirdToken(final CatBirdToken token) {
        super(token);
    }

    @Override
    public CatBirdToken copy() {
        return new CatBirdToken(this);
    }
}
