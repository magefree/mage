
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author spjspj
 */
public final class RukhEggBirdToken extends TokenImpl {

    public RukhEggBirdToken() {
        this("ARN"); //there is no such token in either ARN, 8ED or 9ED
    }

    public RukhEggBirdToken(String setCode) {
        super("Bird Token", "4/4 red Bird creature token with flying");
        this.setOriginalExpansionSetCode(setCode);
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
