
package mage.cards.r;

import java.util.UUID;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DoUnlessAnyPlayerPaysEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author L_J
 */
public final class RhysticShield extends CardImpl {

    public RhysticShield(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Creatures you control get +0/+1 until end of turn. They get an additional +0/+2 until end of turn unless any player pays {2}.
        this.getSpellAbility().addEffect(new BoostControlledEffect(0, 1, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false));
        Effect effect = new DoUnlessAnyPlayerPaysEffect(new BoostControlledEffect(0, 2, Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURE, false), new ManaCostsImpl<>("{2}"));
        effect.setText("They get an additional +0/+2 until end of turn unless any player pays {2}");
        this.getSpellAbility().addEffect(effect);
    }

    private RhysticShield(final RhysticShield card) {
        super(card);
    }

    @Override
    public RhysticShield copy() {
        return new RhysticShield(this);
    }
}
