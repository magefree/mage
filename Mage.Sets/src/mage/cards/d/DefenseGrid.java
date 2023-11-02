
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;

/**
 *
 * @author Plopman
 */
public final class DefenseGrid extends CardImpl {

    public DefenseGrid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // Each spell costs {3} more to cast except during its controller's turn.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DefenseGridCostModificationEffect()));

    }

    private DefenseGrid(final DefenseGrid card) {
        super(card);
    }

    @Override
    public DefenseGrid copy() {
        return new DefenseGrid(this);
    }
}

class DefenseGridCostModificationEffect extends CostModificationEffectImpl {

    DefenseGridCostModificationEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        staticText = "Each spell costs {3} more to cast except during its controller's turn";
    }

    private DefenseGridCostModificationEffect(final DefenseGridCostModificationEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        spellAbility.addManaCostsToPay(new GenericManaCost(3));
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (!abilityToModify.isControlledBy(game.getActivePlayerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public DefenseGridCostModificationEffect copy() {
        return new DefenseGridCostModificationEffect(this);
    }

}
