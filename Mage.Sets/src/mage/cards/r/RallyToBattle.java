package mage.cards.r;

import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RallyToBattle extends CardImpl {

    public RallyToBattle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{W}");


        // Creatures you control get +1/+3 until end of turn. Untap them.
        this.getSpellAbility().addEffect(
                new BoostControlledEffect(1, 3, Duration.EndOfTurn)
        );
        this.getSpellAbility().addEffect(new UntapAllControllerEffect(
                StaticFilters.FILTER_PERMANENT_CREATURE
        ).setText("Untap them"));
    }

    private RallyToBattle(final RallyToBattle card) {
        super(card);
    }

    @Override
    public RallyToBattle copy() {
        return new RallyToBattle(this);
    }
}
