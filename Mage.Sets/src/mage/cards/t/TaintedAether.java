package mage.cards.t;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;

/**
 *
 * @author LoneFox
 */
public final class TaintedAether extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a creature or land");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.LAND.getPredicate()
        ));
    }

    public TaintedAether(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}{B}");

        // Whenever a creature enters the battlefield, its controller sacrifices a creature or land.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.BATTLEFIELD, new SacrificeEffect(filter, 1, "its controller"),
                StaticFilters.FILTER_PERMANENT_CREATURE, false, SetTargetPointer.PLAYER));
    }

    private TaintedAether(final TaintedAether card) {
        super(card);
    }

    @Override
    public TaintedAether copy() {
        return new TaintedAether(this);
    }
}
