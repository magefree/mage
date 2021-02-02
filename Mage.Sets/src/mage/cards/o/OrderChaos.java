
package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.filter.StaticFilters;
import mage.target.Target;
import mage.target.common.TargetAttackingCreature;

public final class OrderChaos extends SplitCard {

    public OrderChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}", "{2}{R}", SpellAbilityType.SPLIT);

        // Order
        // Exile target attacking creature.
        getLeftHalfCard().getSpellAbility().addEffect(new ExileTargetEffect());
        Target target = new TargetAttackingCreature();
        getLeftHalfCard().getSpellAbility().addTarget(target);

        // Chaos
        // Creatures can't block this turn.
        getRightHalfCard().getSpellAbility().addEffect(new CantBlockAllEffect(StaticFilters.FILTER_PERMANENT_CREATURES, Duration.EndOfTurn));

    }

    private OrderChaos(final OrderChaos card) {
        super(card);
    }

    @Override
    public OrderChaos copy() {
        return new OrderChaos(this);
    }
}
