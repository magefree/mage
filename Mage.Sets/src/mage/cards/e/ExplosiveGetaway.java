package mage.cards.e;

import java.util.UUID;
import java.util.function.Predicate;

import mage.abilities.Ability;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.ExileReturnBattlefieldNextEndStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.predicate.Predicates;
import mage.target.TargetPermanent;

/**
 *
 * @author Jmlundeen
 */
public final class ExplosiveGetaway extends CardImpl {
    private static final FilterPermanent filter = new FilterPermanent("artifact or creature");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()
        ));
    }

    public ExplosiveGetaway(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{W}");
        

        // Exile up to one target artifact or creature. Return it to the battlefield under its owner's control at the beginning of the next end step.
        this.getSpellAbility().addEffect(new ExileReturnBattlefieldNextEndStepTargetEffect().withTextThatCard(false));
        this.getSpellAbility().addTarget(new TargetPermanent(0, 1, filter));
        // Explosive Getaway deals 4 damage to each creature.
        this.getSpellAbility().addEffect(new DamageAllEffect(4, StaticFilters.FILTER_PERMANENT_ALL_CREATURES).concatBy("<br>"));
    }

    private ExplosiveGetaway(final ExplosiveGetaway card) {
        super(card);
    }

    @Override
    public ExplosiveGetaway copy() {
        return new ExplosiveGetaway(this);
    }
}
