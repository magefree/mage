package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.MenaceAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class DalekToken extends TokenImpl {

    public DalekToken() {
        super("Dalek Token", "3/3 black Dalek artifact creature token with menace");
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        color.setBlack(true);
        subtype.add(SubType.DALEK);
        power = new MageInt(3);
        toughness = new MageInt(3);

        addAbility(new MenaceAbility(false));
    }

    private DalekToken(final DalekToken token) {
        super(token);
    }

    @Override
    public DalekToken copy() {
        return new DalekToken(this);
    }
}
