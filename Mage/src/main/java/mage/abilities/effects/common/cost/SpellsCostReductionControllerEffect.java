package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.costs.mana.ManaCost;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.cards.Card;
import mage.filter.FilterCard;
import mage.game.Game;

import java.util.UUID;

public class SpellsCostReductionControllerEffect extends SpellsCostReductionEffect {

    public SpellsCostReductionControllerEffect(FilterCard filter, ManaCosts<ManaCost> manaCostsToReduce) {
        this(filter, manaCostsToReduce, StaticValue.get(1));
    }

    public SpellsCostReductionControllerEffect(FilterCard filter, ManaCosts<ManaCost> manaCostsToReduce, DynamicValue amount) {
        this(filter, manaCostsToReduce, amount, false);
    }

    public SpellsCostReductionControllerEffect(FilterCard filter, ManaCosts<ManaCost> manaCostsToReduce, DynamicValue amount, boolean convertToGeneric) {
        super(filter, manaCostsToReduce, amount, convertToGeneric);
        createStaticText("you cast");
    }

    public SpellsCostReductionControllerEffect(FilterCard filter, int amount) {
        this(filter, StaticValue.get(amount));
    }

    public SpellsCostReductionControllerEffect(FilterCard filter, int amount, boolean upTo) {
        this(filter, StaticValue.get(amount), upTo);
    }

    public SpellsCostReductionControllerEffect(FilterCard filter, DynamicValue amount) {
        this(filter, amount, false);
    }

    public SpellsCostReductionControllerEffect(FilterCard filter, DynamicValue amount, boolean upTo) {
        super(filter, amount, upTo);
        createStaticText("you cast");
    }

    protected SpellsCostReductionControllerEffect(final SpellsCostReductionControllerEffect effect) {
        super(effect);
    }

    @Override
    protected boolean checkSourceCondition(Ability abilityToModify, Ability source, Game game) {
        return true;
    }

    @Override
    protected boolean checkSpellCondition(Ability abilityToModify, Ability source, Game game) {
        Card spellCard = ((SpellAbility) abilityToModify).getCharacteristics(game);
        return abilityToModify.getControllerId().equals(source.getControllerId()) &&
                filter.match(spellCard, source.getControllerId(), source, game);
    }

    @Override
    public SpellsCostReductionControllerEffect copy() {
        return new SpellsCostReductionControllerEffect(this);
    }
}