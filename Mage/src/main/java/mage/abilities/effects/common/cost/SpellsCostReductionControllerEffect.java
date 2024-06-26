package mage.abilities.effects.common.cost;

import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.cards.Card;
import mage.choices.ChoiceImpl;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Reduces cost of spell for the controller of the effect.
 *
 * @author North
 */
public class SpellsCostReductionControllerEffect extends CostModificationEffectImpl {

    private final FilterCard filter;
    private final DynamicValue amount;
    private final boolean upTo;
    private ManaCosts<ManaCost> manaCostsToReduce = null;

    public SpellsCostReductionControllerEffect(FilterCard filter, ManaCosts<ManaCost> manaCostsToReduce) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = filter;
        this.amount = StaticValue.get(0);
        this.manaCostsToReduce = manaCostsToReduce;
        this.upTo = false;
        this.staticText = filter.getMessage() + " you cast cost " + manaCostsToReduce.getText() +
                " less to cast. This effect reduces only the amount of colored mana you pay.";
    }

    public SpellsCostReductionControllerEffect(FilterCard filter, int amount) {
        this(filter, StaticValue.get(amount), false);
    }

    public SpellsCostReductionControllerEffect(FilterCard filter, int amount, boolean upTo) {
        this(filter, StaticValue.get(amount), upTo);
    }

    public SpellsCostReductionControllerEffect(FilterCard filter, DynamicValue amount) {
        this(filter, amount, false);
    }

    public SpellsCostReductionControllerEffect(FilterCard filter, DynamicValue amount, boolean upTo) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = filter;
        this.amount = amount;
        this.upTo = upTo;
        this.staticText = (filter.getMessage().contains("you cast") ? filter.getMessage()
                : filter.getMessage() + " you cast")
                + " cost " + (upTo ? "up to " : "") + '{' + amount + "} less to cast";
    }

    protected SpellsCostReductionControllerEffect(final SpellsCostReductionControllerEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.amount = effect.amount;
        this.manaCostsToReduce = effect.manaCostsToReduce;
        this.upTo = effect.upTo;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        if (manaCostsToReduce != null) {
            CardUtil.adjustCost((SpellAbility) abilityToModify, manaCostsToReduce, false);
        } else {
            int reductionAmount = this.amount.calculate(game, source, this);
            if (upTo) {
                Mana mana = abilityToModify.getManaCostsToPay().getMana();
                int reduceMax = mana.getGeneric();
                if (reduceMax > reductionAmount) {
                    reduceMax = reductionAmount;
                }
                if (reduceMax > 0) {
                    Player controller = game.getPlayer(abilityToModify.getControllerId());
                    if (controller == null) {
                        return false;
                    }
                    int reduce = reduceMax;
                    if (!game.inCheckPlayableState()) {
                        ChoiceImpl choice = new ChoiceImpl(false);
                        Set<String> set = new LinkedHashSet<>();
                        for (int i = 0; i <= reduceMax; i++) {
                            set.add(String.valueOf(i));
                        }
                        choice.setChoices(set);
                        MageObject mageObject = game.getObject(abilityToModify.getSourceId());
                        choice.setMessage("Reduce cost of "
                                + (mageObject != null ? mageObject.getIdName() : filter.getMessage()));
                        if (controller.choose(Outcome.Benefit, choice, game)) {
                            reduce = Integer.parseInt(choice.getChoice());
                        } else {
                            reduce = reduceMax; // cancel will be set to max possible reduce
                        }
                    }
                    if (reduce > 0) {
                        CardUtil.reduceCost(abilityToModify, reduce);
                    }
                }
            } else {
                CardUtil.reduceCost(abilityToModify, reductionAmount);
            }
        }
        return true;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            if (abilityToModify.isControlledBy(source.getControllerId())) {
                Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
                if (spellCard != null) {
                    return this.filter.match(spellCard, source.getControllerId(), source, game);
                }
            }
        }
        return false;
    }

    @Override
    public SpellsCostReductionControllerEffect copy() {
        return new SpellsCostReductionControllerEffect(this);
    }
}
