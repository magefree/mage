
package mage.cards.d;

import java.util.UUID;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.filter.common.FilterLandPermanent;

/**
 *
 * @author LoneFox
 */
public final class DestructiveFlow extends CardImpl {

    public DestructiveFlow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}{R}{G}");

        // At the beginning of each player's upkeep, that player sacrifices a nonbasic land.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeEffect(FilterLandPermanent.nonbasicLand(), 1, "that player"),
            TargetController.ANY, false));

    }

    private DestructiveFlow(final DestructiveFlow card) {
        super(card);
    }

    @Override
    public DestructiveFlow copy() {
        return new DestructiveFlow(this);
    }
}
