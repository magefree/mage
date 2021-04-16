package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.cards.Card;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author htrajan
 */
public class SpellsCostIncreasingSpecificCardUnderConditionEffect extends CostModificationEffectImpl {

    private final UUID cardId;
    private final Condition condition;
    private final UUID controllerId;
    private final int increaseGenericCost;
    private final ManaCosts<ManaCost> increaseManaCosts;

    public SpellsCostIncreasingSpecificCardUnderConditionEffect(int increaseGenericCost, UUID cardId, UUID controllerId, Condition condition) {
        super(Duration.Custom, Outcome.Detriment, CostModificationType.INCREASE_COST);
        this.cardId = cardId;
        this.condition = condition;
        this.controllerId = controllerId;
        this.increaseGenericCost = increaseGenericCost;
        this.increaseManaCosts = null;
    }

    public SpellsCostIncreasingSpecificCardUnderConditionEffect(ManaCosts<ManaCost> increaseManaCosts, UUID cardId, FilterCard filter, UUID controllerId, Condition condition) {
        super(Duration.Custom, Outcome.Detriment, CostModificationType.INCREASE_COST);
        this.cardId = cardId;
        this.condition = condition;
        this.controllerId = controllerId;
        this.increaseGenericCost = 0;
        this.increaseManaCosts = increaseManaCosts;
    }

    protected SpellsCostIncreasingSpecificCardUnderConditionEffect(SpellsCostIncreasingSpecificCardUnderConditionEffect effect) {
        super(effect);
        this.cardId = effect.cardId;
        this.condition = effect.condition;
        this.controllerId = effect.controllerId;
        this.increaseGenericCost = effect.increaseGenericCost;
        this.increaseManaCosts = effect.increaseManaCosts;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (increaseManaCosts != null) {
            CardUtil.increaseCost((SpellAbility) abilityToModify, increaseManaCosts);
        } else {
            CardUtil.increaseCost(abilityToModify, this.increaseGenericCost);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            Player abilityController = game.getPlayer(abilityToModify.getControllerId());

            if (abilityController == null || !abilityController.getId().equals(controllerId)) {
                return false;
            }

            Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
            if (spell != null) {
                // real cast with put on stack
                return spell.getId().equals(cardId) && condition.apply(game, source);
            } else {
                // get playable and other staff without put on stack
                // used at least for flashback ability because Flashback ability doesn't use stack
                Card sourceCard = game.getCard(abilityToModify.getSourceId());
                return sourceCard != null && sourceCard.getId().equals(cardId) && condition.apply(game, source);
            }
        }
        return false;
    }

    @Override
    public SpellsCostIncreasingSpecificCardUnderConditionEffect copy() {
        return new SpellsCostIncreasingSpecificCardUnderConditionEffect(this);
    }
}
