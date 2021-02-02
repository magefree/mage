
package mage.cards.b;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;

/**
 *
 * @author dustinconrad
 */
public final class BottomlessPit extends CardImpl {

    public BottomlessPit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{1}{B}{B}");


        // At the beginning of each player's upkeep, that player discards a card at random.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DiscardTargetEffect(1, true), TargetController.ANY, false));
    }

    private BottomlessPit(final BottomlessPit card) {
        super(card);
    }

    @Override
    public BottomlessPit copy() {
        return new BottomlessPit(this);
    }
}
