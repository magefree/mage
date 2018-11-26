package mage.abilities.effects.common.cost;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;

public class SpellCostReductionSourceForOpponentsEffect extends CostModificationEffectImpl {

    public SpellCostReductionSourceForOpponentsEffect() {
        this("undaunted <i>(This spell costs {1} less to cast for each opponent.)</i>");
    }

    public SpellCostReductionSourceForOpponentsEffect(String newStaticText) {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        staticText = newStaticText;
    }

    public SpellCostReductionSourceForOpponentsEffect(final SpellCostReductionSourceForOpponentsEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility) abilityToModify;
        Mana mana = spellAbility.getManaCostsToPay().getMana();
        if (mana.getGeneric() > 0) {
            int count = game.getOpponents(source.getControllerId()).size();
            int newCount = mana.getGeneric() - count;
            if (newCount < 0) {
                newCount = 0;
            }
            mana.setGeneric(newCount);
            spellAbility.getManaCostsToPay().load(mana.toString());
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        return abilityToModify instanceof SpellAbility && abilityToModify.getSourceId().equals(source.getSourceId());
    }

    @Override
    public SpellCostReductionSourceForOpponentsEffect copy() {
        return new SpellCostReductionSourceForOpponentsEffect(this);
    }
}
