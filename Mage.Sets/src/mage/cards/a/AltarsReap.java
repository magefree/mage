
package mage.cards.a;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author North
 */
public final class AltarsReap extends CardImpl {

    public AltarsReap(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{B}");


        // As an additional cost to cast Altar's Reap, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, true)));
        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private AltarsReap(final AltarsReap card) {
        super(card);
    }

    @Override
    public AltarsReap copy() {
        return new AltarsReap(this);
    }
}
