package mage.cards.t;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.BlockedPredicate;
import mage.filter.predicate.permanent.BlockingPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class TacticalAdvantage extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("blocking or blocked creature you control");

    static {
        filter.add(
                Predicates.or(
                        BlockingPredicate.instance,
                        BlockedPredicate.instance

                ));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public TacticalAdvantage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Target blocking or blocked creature you control gets +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 2, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(filter));
    }

    private TacticalAdvantage(final TacticalAdvantage card) {
        super(card);
    }

    @Override
    public TacticalAdvantage copy() {
        return new TacticalAdvantage(this);
    }
}
