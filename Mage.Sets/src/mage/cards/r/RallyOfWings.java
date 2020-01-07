package mage.cards.r;

import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RallyOfWings extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("Creatures you control with flying");

    static {
        filter.add(new AbilityPredicate(FlyingAbility.class));
        filter.add(TargetController.YOU.getControllerPredicate());
    }

    public RallyOfWings(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Untap all creatures you control. Creatures you control with flying get +2/+2 until end of turn.
        this.getSpellAbility().addEffect(new UntapAllControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
        this.getSpellAbility().addEffect(new BoostAllEffect(2, 2, Duration.EndOfTurn, filter, false));
    }

    private RallyOfWings(final RallyOfWings card) {
        super(card);
    }

    @Override
    public RallyOfWings copy() {
        return new RallyOfWings(this);
    }
}
