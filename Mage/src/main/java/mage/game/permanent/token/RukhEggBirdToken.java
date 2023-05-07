package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 * @author spjspj
 */
public final class RukhEggBirdToken extends TokenImpl {

    public RukhEggBirdToken() {
        super("Bird Token", "4/4 red Bird creature token with flying");
        cardType.add(CardType.CREATURE);
        color.setRed(true);

        subtype.add(SubType.BIRD);
        power = new MageInt(4);
        toughness = new MageInt(4);
        addAbility(FlyingAbility.getInstance());
    }

    public RukhEggBirdToken(final RukhEggBirdToken token) {
        super(token);
    }

    public RukhEggBirdToken copy() {
        return new RukhEggBirdToken(this);
    }
}
