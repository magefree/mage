package mage.cards.r;

import mage.abilities.Mode;
import mage.abilities.dynamicvalue.common.GreatestAmongPermanentsValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.Predicates;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ReturnOfTheWildspeaker extends CardImpl {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent("non-Human creatures you control");

    static {
        filter.add(Predicates.not(SubType.HUMAN.getPredicate()));
    }

    private static final GreatestAmongPermanentsValue xValue = new GreatestAmongPermanentsValue(GreatestAmongPermanentsValue.Quality.Power, filter);
    private static final Hint hint = xValue.getHint();

    public ReturnOfTheWildspeaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{G}");

        // Choose one —
        // • Draw cards equal to the greatest power among non-Human creatures you control.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(xValue)
                .setText("draw cards equal to the greatest power among non-Human creatures you control"));
        this.getSpellAbility().addHint(hint);

        // • Non-Human creatures you control get +3/+3 until end of turn.
        this.getSpellAbility().addMode(new Mode(
                new BoostControlledEffect(3, 3, Duration.EndOfTurn, filter)
        ));
    }

    private ReturnOfTheWildspeaker(final ReturnOfTheWildspeaker card) {
        super(card);
    }

    @Override
    public ReturnOfTheWildspeaker copy() {
        return new ReturnOfTheWildspeaker(this);
    }
}