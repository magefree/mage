package mage.abilities.effects.common.cost;

import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.costs.mana.ManaCostsImpl;
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
import java.util.UUID;

public abstract class SpellsCostReductionEffect extends CostModificationEffectImpl {

    // Which spells to apply cost reduction to
    protected final FilterCard filter;

    // Number of times to apply cost reduction
    // When just reducing colorless mana, (constructors without a ManaCosts<ManaCost> argument)
    // this is the amount of colorless mana to reduce by
    protected final DynamicValue amount;

    // adds "up to" sliding scale for mana reduction (only available for generic mana cost reduction)
    protected final boolean upTo;

    protected ManaCosts<ManaCost> manaCostsToReduce = null;

    // true when colored mana can also reduce generic mana if no more mana of that color remains in the cost
    // See CardUtil.adjustCost
    protected final boolean convertToGeneric;

    protected SpellsCostReductionEffect(FilterCard filter, ManaCosts<ManaCost> manaCostsToReduce) {
        this(filter, manaCostsToReduce, StaticValue.get(1));
    }

    protected SpellsCostReductionEffect(FilterCard filter, ManaCosts<ManaCost> manaCostsToReduce, DynamicValue amount) {
        this(filter, manaCostsToReduce, amount, false);
    }

    protected SpellsCostReductionEffect(FilterCard filter, ManaCosts<ManaCost> manaCostsToReduce, DynamicValue amount, boolean convertToGeneric) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = filter;
        this.amount = amount;
        this.manaCostsToReduce = manaCostsToReduce;
        this.upTo = false;
        this.convertToGeneric = convertToGeneric;
    }

    protected SpellsCostReductionEffect(FilterCard filter, int amount) {
        this(filter, StaticValue.get(amount), false);
    }

    protected SpellsCostReductionEffect(FilterCard filter, int amount, boolean upTo) {
        this(filter, StaticValue.get(amount), upTo);
    }

    protected SpellsCostReductionEffect(FilterCard filter, DynamicValue amount) {
        this(filter, amount, false);
    }

    protected SpellsCostReductionEffect(FilterCard filter, DynamicValue amount, boolean upTo) {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = filter;
        this.amount = amount;
        this.upTo = upTo;
        this.convertToGeneric = false;
    }

    protected SpellsCostReductionEffect(final SpellsCostReductionEffect effect) {
        super(effect);
        this.filter = effect.filter;
        this.amount = effect.amount;
        this.manaCostsToReduce = effect.manaCostsToReduce;
        this.upTo = effect.upTo;
        this.convertToGeneric = effect.convertToGeneric;
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        int reductionAmount = this.amount.calculate(game, source, this);
        if (manaCostsToReduce != null) {
            ManaCosts<ManaCost> calculatedManaCostsToReduce = new ManaCostsImpl<>();
            for (int i = 0; i < reductionAmount; i++) {
                calculatedManaCostsToReduce.add(this.manaCostsToReduce.copy());
            }
            CardUtil.adjustCost(abilityToModify, calculatedManaCostsToReduce, convertToGeneric);
        } else {
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
                            reduce = reduceMax;
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

    protected abstract boolean checkSourceCondition(Ability abilityToModify, Ability source, Game game);
    protected abstract boolean checkSpellCondition(Ability abilityToModify, Ability source, Game game);

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility) {
            Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
            if (spellCard != null && checkSourceCondition(abilityToModify, source, game)) {
                return checkSpellCondition(abilityToModify, source, game);
            }
        }
        return false;
    }

    protected void createStaticText(String controllerText) {
        StringBuilder sb = new StringBuilder(filter.getMessage());
        if (!sb.toString().contains(controllerText)) {
            sb.append(" ").append(controllerText);
        }
        if (sb.toString().toLowerCase().contains("spells")) {
            sb.append(" cost ");
        } else {
            sb.append(" costs ");
        }
        if (upTo) {
            sb.append("up to ");
        }
        if (manaCostsToReduce != null) {
            sb.append(manaCostsToReduce.getText());
            if (convertToGeneric) {
                sb.append(" (<i>or {1}</i>)");
            }
        } else {
            sb.append("{").append(amount).append("}");
        }
        sb.append(" less to cast");
        if (!(amount instanceof StaticValue)) {
            sb.append(" for each ").append(amount.getMessage());
        }
        if (!convertToGeneric && manaCostsToReduce != null) {
            sb.append(". This effect reduces only the amount of colored mana you pay");
        }
        staticText = sb.toString();
    }
}