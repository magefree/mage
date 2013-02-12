package mage.abilities.effects.common;

import mage.Constants;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.effects.CostModificationEffectImpl;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;

public class AffinityEffect extends CostModificationEffectImpl<AffinityEffect> {
    private FilterControlledPermanent filter;

    public AffinityEffect(FilterControlledPermanent affinityFilter) {
        super(Constants.Duration.Custom, Constants.Outcome.Benefit);
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
        if (mana.getColorless() > 0) {
            int count = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
            int newCount = mana.getColorless() - count;
            if (newCount < 0) {
                newCount = 0;
            }
            mana.setColorless(newCount);
            spellAbility.getManaCostsToPay().load(mana.toString());
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility && abilityToModify.getSourceId().equals(source.getSourceId())) {
            return true;
        }
        return false;
    }

    @Override
    public AffinityEffect copy() {
        return new AffinityEffect(this);
    }
}
