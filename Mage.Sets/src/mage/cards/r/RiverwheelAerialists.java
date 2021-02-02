
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class RiverwheelAerialists extends CardImpl {

    public RiverwheelAerialists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.MONK);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Prowess
        this.addAbility(new ProwessAbility());
    }

    private RiverwheelAerialists(final RiverwheelAerialists card) {
        super(card);
    }

    @Override
    public RiverwheelAerialists copy() {
        return new RiverwheelAerialists(this);
    }
}
