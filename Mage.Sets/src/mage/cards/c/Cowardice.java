package mage.cards.c;

import mage.abilities.common.BecomesTargetAnyTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class Cowardice extends CardImpl {

    public Cowardice(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{U}{U}");

        // Whenever a creature becomes the target of a spell or ability, return that creature to its owner's hand.
        this.addAbility(new BecomesTargetAnyTriggeredAbility(new ReturnToHandTargetEffect(), StaticFilters.FILTER_PERMANENT_A_CREATURE));
    }

    private Cowardice(final Cowardice card) {
        super(card);
    }

    @Override
    public Cowardice copy() {
        return new Cowardice(this);
    }
}
