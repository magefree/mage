package mage.cards.h;

import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Heartfire extends CardImpl {

    private static final FilterControlledPermanent filter
            = new FilterControlledPermanent("a creature or planeswalker");

    static {
        filter.add(Predicates.or(
                CardType.PLANESWALKER.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public Heartfire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // As an additional cost to cast this spell, sacrifice a creature or planeswalker.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));

        // Heartfire deals 4 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private Heartfire(final Heartfire card) {
        super(card);
    }

    @Override
    public Heartfire copy() {
        return new Heartfire(this);
    }
}
