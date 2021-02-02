
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.keyword.InvestigateEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LevelX2
 */
public final class BywayCourier extends CardImpl {

    public BywayCourier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}");
        this.subtype.add(SubType.HUMAN, SubType.SCOUT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Byway Courier dies, investigate.
        this.addAbility(new DiesSourceTriggeredAbility(new InvestigateEffect(), false));
    }

    private BywayCourier(final BywayCourier card) {
        super(card);
    }

    @Override
    public BywayCourier copy() {
        return new BywayCourier(this);
    }
}
