package mage.cards.h;

import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.CounterAnyPredicate;

import java.util.UUID;

/**
 * @author spjspj
 */
public final class HazardousConditions extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("creatures with no counters on them");

    static {
        filter.add(Predicates.not(CounterAnyPredicate.instance));
    }

    public HazardousConditions(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{G}");

        // Creatures with no counters on them get -2/-2 until end of turn
        this.getSpellAbility().addEffect(new BoostAllEffect(-2, -2, Duration.EndOfTurn, filter, false));
    }

    private HazardousConditions(final HazardousConditions card) {
        super(card);
    }

    @Override
    public HazardousConditions copy() {
        return new HazardousConditions(this);
    }
}
