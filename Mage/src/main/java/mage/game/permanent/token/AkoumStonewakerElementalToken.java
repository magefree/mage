

package mage.game.permanent.token;

import mage.constants.CardType;
import mage.constants.SubType;
import mage.MageInt;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;

/**
 *
 * @author spjspj
 */
public final class AkoumStonewakerElementalToken extends TokenImpl {

    public AkoumStonewakerElementalToken() {
        super("Elemental", "3/1 red Elemental creature token with trample and haste");
        cardType.add(CardType.CREATURE);
        color.setRed(true);
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(3);
        toughness = new MageInt(1);
        this.addAbility(TrampleAbility.getInstance());
        this.addAbility(HasteAbility.getInstance());
        this.setOriginalExpansionSetCode("BFZ");
        this.setTokenType(1);
    }

    public AkoumStonewakerElementalToken(final AkoumStonewakerElementalToken token) {
        super(token);
    }

    public AkoumStonewakerElementalToken copy() {
        return new AkoumStonewakerElementalToken(this);
    }
}

