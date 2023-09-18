package mage.cards.m;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author anonymous
 */
public final class Mightstone extends CardImpl {

    public Mightstone(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // Attacking creatures get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(1, 0, Duration.WhileOnBattlefield, StaticFilters.FILTER_ATTACKING_CREATURES, false)));
    }

    private Mightstone(final Mightstone card) {
        super(card);
    }

    @Override
    public Mightstone copy() {
        return new Mightstone(this);
    }
}
