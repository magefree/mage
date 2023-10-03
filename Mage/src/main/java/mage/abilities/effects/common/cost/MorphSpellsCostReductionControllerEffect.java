package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.keyword.MorphAbility;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;

public class MorphSpellsCostReductionControllerEffect extends SpellsCostReductionControllerEffect{
    private static final FilterCreatureCard standardFilter = new FilterCreatureCard("Face-down creature spells");

    public MorphSpellsCostReductionControllerEffect(int amount) {
        super(standardFilter, amount);
    }
    public MorphSpellsCostReductionControllerEffect(FilterCard filter, int amount) {
        super(filter, amount);
    }
    protected MorphSpellsCostReductionControllerEffect(final MorphSpellsCostReductionControllerEffect effect) {
        super(effect);
    }
    @Override
    public MorphSpellsCostReductionControllerEffect copy() {
        return new MorphSpellsCostReductionControllerEffect(this);
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof MorphAbility) {
            return super.applies(abilityToModify, source, game);
        }
        return false;
    }
}