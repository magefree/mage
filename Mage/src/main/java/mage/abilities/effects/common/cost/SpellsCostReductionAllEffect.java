package mage.abilities.effects.common.cost;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.cards.Card;
import mage.choices.ChoiceImpl;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author LevelX2
 */
public class SpellsCostReductionAllEffect extends CostModificationEffectImpl {

    private FilterCard filter;
    private int amount;
    private final boolean upTo;
    private boolean onlyControlled;
    private UUID controllerId;

    public SpellsCostReductionAllEffect(int amount) {
        this(new FilterCard("Spells"), amount);
    }

    public SpellsCostReductionAllEffect(FilterCard filter, int amount) {
        this(filter, amount, false);
    }

    public SpellsCostReductionAllEffect(FilterCard filter, int amount, boolean upTo) {
        this(filter, amount, upTo, false);
    }

    public SpellsCostReductionAllEffect(FilterCard filter, int amount, boolean upTo, boolean onlyControlled) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = filter;
        this.amount = amount;
        this.upTo = upTo;
        this.onlyControlled = onlyControlled;
        this.staticText = filter.getMessage() + " cost " + (upTo ? "up to " : "") + '{' + amount + "} less to cast";
        this.controllerId = null;
    }

    protected SpellsCostReductionAllEffect(final SpellsCostReductionAllEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.amount = effect.amount;
        this.upTo = effect.upTo;
        this.onlyControlled = effect.onlyControlled;
        this.controllerId = effect.controllerId;
    }

    @Override
    public void init(Ability source, Game game) {
        super.init(source, game);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (upTo) {
            if (game.inCheckPlayableState()) {
                CardUtil.reduceCost(abilityToModify, this.amount);
                return true;
            }
            Mana mana = abilityToModify.getManaCostsToPay().getMana();
            int reduceMax = mana.getGeneric();
            if (reduceMax > 2) {
                reduceMax = 2;
            }
            if (reduceMax > 0) {
                Player controller = game.getPlayer(abilityToModify.getControllerId());
                if (controller == null) {
                    return false;
                }
                ChoiceImpl choice = new ChoiceImpl(true);
                Set<String> set = new LinkedHashSet<>();
                for (int i = 0; i <= reduceMax; i++) {
                    set.add(String.valueOf(i));
                }
                choice.setChoices(set);
                choice.setMessage("Reduce cost of " + filter);
                if (controller.choose(Outcome.Benefit, choice, game)) {
                    int reduce = Integer.parseInt(choice.getChoice());
                    CardUtil.reduceCost(abilityToModify, reduce);
                } else {
                    return false;
                }
            }
        } else {
            CardUtil.reduceCost(abilityToModify, this.amount);
        }
        return true;
    }

    /**
     * Overwrite this in effect that inherits from this
     *
     * @param card
     * @param source
     * @param game
     * @return
     */
    protected boolean selectedByRuntimeData(Card card, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (onlyControlled && !abilityToModify.isControlledBy(source.getControllerId())) {
            return false;
        }
        if (controllerId != null && !abilityToModify.isControlledBy(controllerId)) {
            return false;
        }
        if (abilityToModify instanceof SpellAbility) {
            Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
            if (spellCard != null) {
                return this.filter.match(spellCard, game) && selectedByRuntimeData(spellCard, source, game);
            }
        }
        return false;
    }

    public void setControllerId(UUID controllerId) {
        this.controllerId = controllerId;
    }

    @Override
    public SpellsCostReductionAllEffect copy() {
        return new SpellsCostReductionAllEffect(this);
    }
}
