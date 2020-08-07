package mage.cards.s;

import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.common.FilterOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SuffocatingFumes extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterOpponentsCreaturePermanent("creatures your opponents control");

    public SuffocatingFumes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{B}");

        // Creatures your opponents control get -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(
                -1, -1, Duration.EndOfTurn, filter, false
        ));

        // Cycling {2}
        this.addAbility(new CyclingAbility(new ManaCostsImpl("{2}")));
    }

    private SuffocatingFumes(final SuffocatingFumes card) {
        super(card);
    }

    @Override
    public SuffocatingFumes copy() {
        return new SuffocatingFumes(this);
    }
}
