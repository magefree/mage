
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class RidgeRannet extends CardImpl {

    public RidgeRannet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{R}{R}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(6);
        this.toughness = new MageInt(4);

        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private RidgeRannet(final RidgeRannet card) {
        super(card);
    }

    @Override
    public RidgeRannet copy() {
        return new RidgeRannet(this);
    }
}
