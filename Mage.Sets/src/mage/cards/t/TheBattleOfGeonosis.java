
package mage.cards.t;

import java.util.UUID;
import mage.abilities.dynamicvalue.IntPlusDynamicValue;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.common.FilterOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class TheBattleOfGeonosis extends CardImpl {

    public TheBattleOfGeonosis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{X}{R}{R}");

        // The Battle of Geonosis deals X + 1 damage to each opponent and each creature your opponents control.
        Effect effect = new DamagePlayersEffect(Outcome.Damage, new IntPlusDynamicValue(1, ManacostVariableValue.REGULAR), TargetController.OPPONENT);
        effect.setText("The Battle of Geonosis deals X plus 1 damage to each opponent");
        this.getSpellAbility().addEffect(effect);
        effect = new DamageAllEffect(new IntPlusDynamicValue(1, ManacostVariableValue.REGULAR), new FilterOpponentsCreaturePermanent());
        effect.setText("and each creature your opponents control");
        this.getSpellAbility().addEffect(effect);

        // Creatures you control get +X/+0 until end of turn.
        this.getSpellAbility().addEffect(new BoostControlledEffect(ManacostVariableValue.REGULAR, StaticValue.get(0), Duration.EndOfTurn));

    }

    private TheBattleOfGeonosis(final TheBattleOfGeonosis card) {
        super(card);
    }

    @Override
    public TheBattleOfGeonosis copy() {
        return new TheBattleOfGeonosis(this);
    }
}
