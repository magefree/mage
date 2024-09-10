
package mage.cards.b;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;

/**
 *
 * @author Styxo
 */
public final class BattleTactics extends CardImpl {

    public BattleTactics(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{W}{W}");

        // Creatures you control get +2/+1 and gain lifelink until end of turn.
        Effect effect = new BoostControlledEffect(2, 1, Duration.EndOfTurn);
        effect.setText("Creatures you control get +2/+1");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, StaticFilters.FILTER_PERMANENT_CREATURES);
        effect.setText("and gain lifelink until end of turn");
        this.getSpellAbility().addEffect(effect);
    }

    private BattleTactics(final BattleTactics card) {
        super(card);
    }

    @Override
    public BattleTactics copy() {
        return new BattleTactics(this);
    }
}
