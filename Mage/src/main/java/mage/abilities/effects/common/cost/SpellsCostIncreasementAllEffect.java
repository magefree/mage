
package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
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
 * @author Plopman
 */
public class SpellsCostIncreasementAllEffect extends CostModificationEffectImpl {

    private FilterCard filter;
    private int amount;

    public SpellsCostIncreasementAllEffect(int amount) {
        this(new FilterCard("Spells"), amount);
    }

    public SpellsCostIncreasementAllEffect(FilterCard filter, int amount) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.INCREASE_COST);
        this.filter = filter;
        this.amount = amount;
        this.staticText = new StringBuilder(filter.getMessage()).append(" cost {").append(amount).append("} more to cast").toString();
    }

    protected SpellsCostIncreasementAllEffect(SpellsCostIncreasementAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.amount = effect.amount;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        CardUtil.increaseCost(abilityToModify, this.amount);
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
            if (spell != null) {
                return this.filter.match(spell, game);
            } else {
                // used at least for flashback ability because Flashback ability doesn't use stack
                Card sourceCard = game.getCard(abilityToModify.getSourceId());
                return sourceCard != null && this.filter.match(sourceCard, game);
            }
        }
        return false;
    }

    @Override
    public SpellsCostIncreasementAllEffect copy() {
        return new SpellsCostIncreasementAllEffect(this);
    }
}
