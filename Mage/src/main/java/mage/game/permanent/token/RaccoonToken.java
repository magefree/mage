package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class RaccoonToken extends TokenImpl {

    public RaccoonToken() {
        super("Raccoon Token", "3/3 green Raccoon creature token");
        cardType.add(CardType.CREATURE);
        color.setGreen(true);
        subtype.add(SubType.RACCOON);
        power = new MageInt(3);
        toughness = new MageInt(3);
    }

    private RaccoonToken(final RaccoonToken token) {
        super(token);
    }

    @Override
    public RaccoonToken copy() {
        return new RaccoonToken(this);
    }
}
