package mage.game.permanent.token;

import mage.MageInt;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author TheElk801
 */
public final class FishNoAbilityToken extends TokenImpl {

    public FishNoAbilityToken() {
        super("Fish Token", "1/1 blue Fish creature token");
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.FISH);
        color.setBlue(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
    }

    protected FishNoAbilityToken(final FishNoAbilityToken token) {
        super(token);
    }

    public FishNoAbilityToken copy() {
        return new FishNoAbilityToken(this);
    }
}
