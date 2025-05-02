package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;

public class FaceDownSpellsCostReductionControllerEffect extends SpellsCostReductionControllerEffect{

    private static final FilterCreatureCard standardFilter = new FilterCreatureCard("Face-down creature spells");

    /**
     * Face-down creature spells you cast cost
     * @param amount less to cast
     */
    public FaceDownSpellsCostReductionControllerEffect(int amount) {
        super(standardFilter, amount);
    }

    /**
     * Face-down spells you cast cost
     * @param filter with matching characteristics
     * @param amount less to cast
     */
    public FaceDownSpellsCostReductionControllerEffect(FilterCard filter, int amount) {
        super(filter, amount);
    }

    protected FaceDownSpellsCostReductionControllerEffect(final FaceDownSpellsCostReductionControllerEffect effect) {
        super(effect);
    }

    @Override
    public FaceDownSpellsCostReductionControllerEffect copy() {
        return new FaceDownSpellsCostReductionControllerEffect(this);
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        if (abilityToModify instanceof SpellAbility && ((SpellAbility) abilityToModify).getSpellAbilityCastMode().isFaceDown()) {
            return super.applies(abilityToModify, source, game);
        }
        return false;
    }
}
