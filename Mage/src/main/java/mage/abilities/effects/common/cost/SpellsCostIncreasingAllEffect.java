package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.cards.Card;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author JayDi85
 */
public class SpellsCostIncreasingAllEffect extends CostModificationEffectImpl {

    private final FilterCard filter;
    private final TargetController targetController;
    private final int increaseGenericCost;
    private final ManaCosts<ManaCost> increaseManaCosts;

    public SpellsCostIncreasingAllEffect(int increaseGenericCost, FilterCard filter, TargetController targetController) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.INCREASE_COST);
        this.filter = filter;
        this.targetController = targetController;
        this.increaseGenericCost = increaseGenericCost;
        this.increaseManaCosts = null;

        setText();
    }

    public SpellsCostIncreasingAllEffect(ManaCosts<ManaCost> increaseManaCosts, FilterCard filter, TargetController targetController) {
        super(Duration.WhileOnBattlefield, Outcome.Detriment, CostModificationType.INCREASE_COST);
        this.filter = filter;
        this.targetController = targetController;
        this.increaseGenericCost = 0;
        this.increaseManaCosts = increaseManaCosts;

        setText();
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append(filter.getMessage());
        switch (this.targetController) {
            case YOU:
                sb.append(" you cast");
                break;
            case OPPONENT:
                sb.append(" your opponents cast");
                break;
            case ANY:
                break;
            default:
                throw new IllegalArgumentException("Unsupported TargetController " + this.targetController);
        }

        sb.append(" cost ");
        if (this.increaseManaCosts != null) {
            sb.append(this.increaseManaCosts.getText());
        } else {
            sb.append("{").append(increaseGenericCost).append("}");
        }

        sb.append(" more to cast");
        this.staticText = sb.toString();
    }

    protected SpellsCostIncreasingAllEffect(SpellsCostIncreasingAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.targetController = effect.targetController;
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
            Player sourceController = game.getPlayer(source.getControllerId());
            if (abilityController == null || sourceController == null) {
                return false;
            }

            switch (this.targetController) {
                case YOU:
                    if (!sourceController.getId().equals(abilityController.getId())) {
                        return false;
                    }
                    break;
                case OPPONENT:
                    if (!sourceController.hasOpponent(abilityController.getId(), game)) {
                        return false;
                    }
                    break;
                case ANY:
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported TargetController " + this.targetController);
            }

            Spell spell = (Spell) game.getStack().getStackObject(abilityToModify.getId());
            if (spell != null) {
                // real cast with put on stack
                return this.filter.match(spell, game);
            } else {
                // get playable and other staff without put on stack
                // used at least for flashback ability because Flashback ability doesn't use stack
                Card sourceCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
                return this.filter.match(sourceCard, game);
            }
        }
        return false;
    }

    @Override
    public SpellsCostIncreasingAllEffect copy() {
        return new SpellsCostIncreasingAllEffect(this);
    }
}
