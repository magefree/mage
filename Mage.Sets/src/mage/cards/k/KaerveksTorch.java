
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.ManacostVariableValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.Target;
import mage.target.common.TargetAnyTarget;
import mage.util.CardUtil;

/**
 *
 * @author LoneFox
 *
 */
public final class KaerveksTorch extends CardImpl {

    public KaerveksTorch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{R}");

        // As long as Kaervek's Torch is on the stack, spells that target it cost {2} more to cast.
        this.addAbility(new SimpleStaticAbility(Zone.STACK, new KaerveksTorchCostIncreaseEffect()));
        // Kaervek's Torch deals X damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(ManacostVariableValue.REGULAR));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private KaerveksTorch(final KaerveksTorch card) {
        super(card);
    }

    @Override
    public KaerveksTorch copy() {
        return new KaerveksTorch(this);
    }
}

class KaerveksTorchCostIncreaseEffect extends CostModificationEffectImpl {

    KaerveksTorchCostIncreaseEffect() {
        super(Duration.WhileOnStack, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Spells that target {this} cost {2} more to cast";
    }

    private KaerveksTorchCostIncreaseEffect(final KaerveksTorchCostIncreaseEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            for (UUID modeId : abilityToModify.getModes().getSelectedModes()) {
                Mode mode = abilityToModify.getModes().get(modeId);
                for (Target target : mode.getTargets()) {
                    for (UUID targetId : target.getTargets()) {
                        if (targetId.equals(source.getSourceObject(game).getId())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    @Override
    public KaerveksTorchCostIncreaseEffect copy() {
        return new KaerveksTorchCostIncreaseEffect(this);
    }
}
