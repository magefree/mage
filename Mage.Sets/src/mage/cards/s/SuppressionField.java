
package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SuppressionField extends CardImpl {

    public SuppressionField(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");

        // Activated abilities cost {2} more to activate unless they're mana abilities.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new SuppressionFieldCostReductionEffect()));
    }

    private SuppressionField(final SuppressionField card) {
        super(card);
    }

    @Override
    public SuppressionField copy() {
        return new SuppressionField(this);
    }
}

class SuppressionFieldCostReductionEffect extends CostModificationEffectImpl {

    SuppressionFieldCostReductionEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Activated abilities cost {2} more to activate unless they're mana abilities";
    }

    private SuppressionFieldCostReductionEffect(final SuppressionFieldCostReductionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, 2);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify.isNonManaActivatedAbility();
    }

    @Override
    public SuppressionFieldCostReductionEffect copy() {
        return new SuppressionFieldCostReductionEffect(this);
    }
}
