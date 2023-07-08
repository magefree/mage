
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.UntapAllControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author LevelX2
 */
public final class FlyingCraneTechnique extends CardImpl {

    public FlyingCraneTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{U}{R}{W}");

        // Untap all creatures you control.  They gain flying and double strike until end of turn.
        this.getSpellAbility().addEffect(new UntapAllControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURES));
        Effect effect = new GainAbilityControlledEffect(FlyingAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("They gain flying");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityControlledEffect(DoubleStrikeAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("and double strike until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    private FlyingCraneTechnique(final FlyingCraneTechnique card) {
        super(card);
    }

    @Override
    public FlyingCraneTechnique copy() {
        return new FlyingCraneTechnique(this);
    }
}
