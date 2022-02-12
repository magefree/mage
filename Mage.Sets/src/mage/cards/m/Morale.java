package mage.cards.m;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Galatolol
 */
public final class Morale extends CardImpl {

    public Morale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}{W}");
        
        // Attacking creatures get +1/+1 until end of turn.
        this.getSpellAbility().addEffect(new BoostAllEffect(1, 1, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false));
    }

    private Morale(final Morale card) {
        super(card);
    }

    @Override
    public Morale copy() {
        return new Morale(this);
    }
}
