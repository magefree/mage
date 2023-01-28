package mage.cards.h;

import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HazardousBlast extends CardImpl {

    public HazardousBlast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Hazardous Blast deals 1 damage to each creature your opponents control. Creatures your opponents control can't block this turn.
        this.getSpellAbility().addEffect(new DamageAllEffect(
                1, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE
        ));
        this.getSpellAbility().addEffect(new CantBlockAllEffect(
                StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURES, Duration.EndOfTurn
        ));
    }

    private HazardousBlast(final HazardousBlast card) {
        super(card);
    }

    @Override
    public HazardousBlast copy() {
        return new HazardousBlast(this);
    }
}
