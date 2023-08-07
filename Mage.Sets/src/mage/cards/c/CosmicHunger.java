package mage.cards.c;

import mage.abilities.effects.common.DamageWithPowerFromOneToAnotherTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.other.AnotherTargetPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CosmicHunger extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent("another target creature, planeswalker, or battle");

    static {
        filter.add(Predicates.or(
                CardType.CREATURE.getPredicate(),
                CardType.PLANESWALKER.getPredicate(),
                CardType.BATTLE.getPredicate()
        ));
        filter.add(new AnotherTargetPredicate(2));
    }

    public CosmicHunger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");

        // Target creature you control deals damage equal to its power to another target creature, planeswalker, or battle.
        this.getSpellAbility().addEffect(new DamageWithPowerFromOneToAnotherTargetEffect());
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent().setTargetTag(1));
        this.getSpellAbility().addTarget(new TargetPermanent(filter).setTargetTag(2));
    }

    private CosmicHunger(final CosmicHunger card) {
        super(card);
    }

    @Override
    public CosmicHunger copy() {
        return new CosmicHunger(this);
    }
}
