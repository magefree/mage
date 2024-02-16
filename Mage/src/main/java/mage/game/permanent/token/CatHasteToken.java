package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class CatHasteToken extends TokenImpl {

    public CatHasteToken() {
        super("Cat Token", "2/2 green Cat creature token with haste");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.CAT);
        power = new MageInt(2);
        toughness = new MageInt(2);

        this.addAbility(HasteAbility.getInstance());
    }

    private CatHasteToken(final CatHasteToken token) {
        super(token);
    }

    public CatHasteToken copy() {
        return new CatHasteToken(this);
    }
}
