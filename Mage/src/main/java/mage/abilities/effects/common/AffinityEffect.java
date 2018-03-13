package mage.abilities.effects.common;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.common.cost.CostModificationEffectImpl;
import mage.constants.CostModificationType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

public class AffinityEffect extends CostModificationEffectImpl {
    
    private final FilterControlledPermanent filter;

    public AffinityEffect(FilterControlledPermanent affinityFilter) {
        super(Duration.Custom, Outcome.Benefit, CostModificationType.REDUCE_COST);
        this.filter = affinityFilter;
        staticText = "Affinity for " + filter.getMessage();
    }

    public AffinityEffect(final AffinityEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public boolean apply(Game game, Ability source, Ability abilityToModify) {
        SpellAbility spellAbility = (SpellAbility)abilityToModify;
        Mana mana = spellAbility.getManaCostsToPay().getMana();
        if (mana.getGeneric() > 0) {
            int count = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
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
    public AffinityEffect copy() {
        return new AffinityEffect(this);
    }
}
