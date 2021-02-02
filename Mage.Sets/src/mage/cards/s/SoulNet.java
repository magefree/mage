
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Quercitron
 */
public final class SoulNet extends CardImpl {

    public SoulNet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // Whenever a creature dies, you may pay {1}. If you do, you gain 1 life.
        this.addAbility(new DiesCreatureTriggeredAbility(new DoIfCostPaid(new GainLifeEffect(1), new GenericManaCost(1)), false));
    }

    private SoulNet(final SoulNet card) {
        super(card);
    }

    @Override
    public SoulNet copy() {
        return new SoulNet(this);
    }
}
