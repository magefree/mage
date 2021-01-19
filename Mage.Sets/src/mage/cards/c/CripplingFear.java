package mage.cards.c;

import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.ChosenSubtypePredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CripplingFear extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("Creatures that aren't of the chosen type");

    static {
        filter.add(ChosenSubtypePredicate.FALSE);
    }

    public CripplingFear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{B}");

        // Choose a creature type. Creatures that aren't of the chosen type get -3/-3 until end of turn.
        this.getSpellAbility().addEffect(new ChooseCreatureTypeEffect(Outcome.Neutral));
        this.getSpellAbility().addEffect(new BoostAllEffect(
                -3, -3, Duration.EndOfTurn, filter, false
        ));
    }

    private CripplingFear(final CripplingFear card) {
        super(card);
    }

    @Override
    public CripplingFear copy() {
        return new CripplingFear(this);
    }
}
