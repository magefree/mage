package mage.cards.o;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LokiX
 */
public final class Overrun extends CardImpl {

    public Overrun(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{G}{G}{G}");

        // Creatures you control get +3/+3 and gain trample until end of turn.
        Effect effect = new BoostControlledEffect(3, 3, Duration.EndOfTurn);
        effect.setText("Creatures you control get +3/+3");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityControlledEffect(
                TrampleAbility.getInstance(),
                Duration.EndOfTurn,
                StaticFilters.FILTER_PERMANENT_CREATURES
        );
        effect.setText("and gain trample until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    private Overrun(final Overrun card) {
        super(card);
    }

    @Override
    public Overrun copy() {
        return new Overrun(this);
    }
}
