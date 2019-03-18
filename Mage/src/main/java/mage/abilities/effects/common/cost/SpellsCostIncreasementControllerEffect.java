
package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.cards.Card;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.util.CardUtil;

/**
 *
 * @author Quercitron
 */
public class SpellsCostIncreasementControllerEffect extends CostModificationEffectImpl {

    private final FilterCard filter;
    private final int amount;
    private ManaCosts<ManaCost> manaCostsToIncrease = null;

    public SpellsCostIncreasementControllerEffect(FilterCard filter, ManaCosts<ManaCost> manaCostsToReduce) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.INCREASE_COST);
        this.filter = filter;
        this.amount = 0;
        this.manaCostsToIncrease = manaCostsToReduce;

        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage()).append(" you cast cost ");
        for (String manaSymbol : manaCostsToReduce.getSymbols()) {
            sb.append(manaSymbol);
        }
        sb.append(" more to cast");
        this.staticText = sb.toString();
    }

    public SpellsCostIncreasementControllerEffect(FilterCard filter, int amount) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.INCREASE_COST);
        this.filter = filter;
        this.amount = amount;

        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage()).append(" you cast cost {").append(amount).append("} more to cast");
        this.staticText = sb.toString();
    }

    protected SpellsCostIncreasementControllerEffect(SpellsCostIncreasementControllerEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.amount = effect.amount;
        this.manaCostsToIncrease = effect.manaCostsToIncrease;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (manaCostsToIncrease != null) {
            CardUtil.increaseCost((SpellAbility) abilityToModify, manaCostsToIncrease);
        } else {
            CardUtil.increaseCost(abilityToModify, this.amount);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (abilityToModify.isControlledBy(source.getControllerId())) {
                Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
                if (spell != null) {
                    return this.filter.match(spell, game);
                } else {
                    // used at least for flashback ability because Flashback ability doesn't use stack
                    Card sourceCard = game.getCard(abilityToModify.getSourceId());
                    return sourceCard != null && this.filter.match(sourceCard, game);
                }
            }
        }
        return false;
    }

    @Override
    public SpellsCostIncreasementControllerEffect copy() {
        return new SpellsCostIncreasementControllerEffect(this);
    }
}
