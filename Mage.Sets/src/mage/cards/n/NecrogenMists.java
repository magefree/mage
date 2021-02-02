
package mage.cards.n;

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
public final class NecrogenMists extends CardImpl {

    public NecrogenMists(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");


        // At the beginning of each player's upkeep, that player discards a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new DiscardTargetEffect(1), TargetController.ANY, false));
    }

    private NecrogenMists(final NecrogenMists card) {
        super(card);
    }

    @Override
    public NecrogenMists copy() {
        return new NecrogenMists(this);
    }
}
