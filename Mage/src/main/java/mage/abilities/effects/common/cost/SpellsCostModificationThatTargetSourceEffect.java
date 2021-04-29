package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.cards.Card;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.Set;
import java.util.UUID;

/**
 * @author JayDi85
 */
public class SpellsCostModificationThatTargetSourceEffect extends CostModificationEffectImpl {

    private final FilterCard spellFilter;
    private final int modificationAmount;
    private String targetName = "{this}";
    private final TargetController targetController;

    public SpellsCostModificationThatTargetSourceEffect(int modificationAmount, FilterCard spellFilter, TargetController targetController) {
        super(Duration.WhileOnBattlefield, Outcome.Neutral, modificationAmount < 0 ? CostModificationType.REDUCE_COST : CostModificationType.INCREASE_COST);
        this.spellFilter = spellFilter;
        this.modificationAmount = modificationAmount;
        this.targetController = targetController;

        setText();
    }

    private void setText() {
        // example: Spells your opponents cast that target Accursed Witch cost {1} less to cast.
        StringBuilder sb = new StringBuilder();
        sb.append(this.spellFilter.getMessage());
        switch (this.targetController) {
            case ANY:
                break;
            case YOU:
                sb.append(" you cast");
                break;
            case OPPONENT:
                sb.append(" your opponents cast");
                break;
            default:
                throw new IllegalArgumentException("Unsupported target controller " + this.targetController);
        }

        sb.append(" that target ").append(this.targetName);
        if (this.modificationAmount < 0) {
            sb.append(" cost {").append(-1 * this.modificationAmount).append("} less to cast");
        } else {
            sb.append(" cost {").append(this.modificationAmount).append("} more to cast");
        }
        this.staticText = sb.toString();
    }

    private SpellsCostModificationThatTargetSourceEffect(SpellsCostModificationThatTargetSourceEffect effect) {
        super(effect);
        this.spellFilter = effect.spellFilter;
        this.modificationAmount = effect.modificationAmount;
        this.targetName = effect.targetName;
        this.targetController = effect.targetController;
    }

    @Override
    public SpellsCostModificationThatTargetSourceEffect copy() {
        return new SpellsCostModificationThatTargetSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (this.modificationAmount >= 0) {
            CardUtil.increaseCost(abilityToModify, this.modificationAmount);
        } else {
            CardUtil.reduceCost(abilityToModify, -1 * this.modificationAmount);
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (!(abilityToModify instanceof SpellAbility)) {
            return false;
        }

        Player sourceController = game.getPlayer(source.getControllerId());
        Player abilityController = game.getPlayer(abilityToModify.getControllerId());
        if (sourceController == null || abilityController == null) {
            return false;
        }

        switch (this.targetController) {
            case ANY:
                break;
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
            default:
                return false;
        }

        Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
        if (!this.spellFilter.match(spellCard, game)) {
            return false;
        }

        if (game.getStack().getStackObject(abilityToModify.getId()) != null) {
            // real cast
            Set<UUID> allTargets = CardUtil.getAllSelectedTargets(abilityToModify, game);
            return allTargets.contains(source.getSourceId());
        } else {
            // playable
            Set<UUID> allTargets = CardUtil.getAllPossibleTargets(abilityToModify, game);

            switch (this.getModificationType()) {
                case REDUCE_COST:
                    // must reduce all the time
                    return allTargets.contains(source.getSourceId());
                case INCREASE_COST:
                    // must increase if can't target another (e.g. user can choose another target without cost increase)
                    return allTargets.contains(source.getSourceId()) && allTargets.size() <= 1;
            }
        }
        return false;
    }

    public SpellsCostModificationThatTargetSourceEffect withTargetName(String targetName) {
        this.targetName = targetName;
        setText();
        return this;
    }
}
