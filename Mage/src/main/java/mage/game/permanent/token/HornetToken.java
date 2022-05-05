

package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author FenrisulfrX
 */
public final class HornetToken extends TokenImpl {
    
    public HornetToken() {
        this("DDE");
    }
    
    public HornetToken(String setCode) {
        super("Hornet Token", "1/1 colorless Insect artifact creature token with flying and haste");
        this.setOriginalExpansionSetCode(setCode);
        cardType.add(CardType.ARTIFACT);
        cardType.add(CardType.CREATURE);
        subtype.add(SubType.INSECT);
        power = new MageInt(1);
        toughness = new MageInt(1);
        addAbility(FlyingAbility.getInstance());
        addAbility(HasteAbility.getInstance());
    }

    public HornetToken(final HornetToken token) {
        super(token);
    }

    public HornetToken copy() {
        return new HornetToken(this);
    }
}
