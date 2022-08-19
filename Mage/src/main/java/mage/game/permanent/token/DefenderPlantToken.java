
package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.DefenderAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author TheElk801
 */
public final class DefenderPlantToken extends TokenImpl {

    public DefenderPlantToken() {
        super("Plant Token", "0/2 green Plant creature token with defender");
        color.setGreen(true);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.PLANT);
        power = new MageInt(0);
        toughness = new MageInt(2);
        setOriginalExpansionSetCode("XLN");

        this.addAbility(DefenderAbility.getInstance());
    }

    public DefenderPlantToken(final DefenderPlantToken token) {
        super(token);
    }

    public DefenderPlantToken copy() {
        return new DefenderPlantToken(this);
    }
}
