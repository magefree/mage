package mage.cards.s;

import java.util.UUID;

import mage.abilities.effects.common.discard.DiscardCardYouChooseTargetEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.filter.predicate.Predicates;
import mage.target.common.TargetOpponent;

/**
 *
 * @author muz
 */
public final class SolveForDisappointment extends CardImpl {

    private static final FilterCard filter = new FilterPermanentCard("a nonland permanent card");

    static {
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public SolveForDisappointment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Target opponent reveals their hand. You choose a nonland permanent card from it. That player discards that card.
        this.getSpellAbility().addEffect(new DiscardCardYouChooseTargetEffect(filter));
        this.getSpellAbility().addTarget(new TargetOpponent());

        // Empower Jace 1.
        this.getSpellAbility().addEffect(new EmpowerJaceEffect(1).concatBy("<br>"));
    }

    private SolveForDisappointment(final SolveForDisappointment card) {
        super(card);
    }

    @Override
    public SolveForDisappointment copy() {
        return new SolveForDisappointment(this);
    }
}
