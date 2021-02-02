
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.RampageAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class WolverinePack extends CardImpl {

    public WolverinePack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.WOLVERINE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Rampage 2
        this.addAbility(new RampageAbility(2));
    }

    private WolverinePack(final WolverinePack card) {
        super(card);
    }

    @Override
    public WolverinePack copy() {
        return new WolverinePack(this);
    }
}
