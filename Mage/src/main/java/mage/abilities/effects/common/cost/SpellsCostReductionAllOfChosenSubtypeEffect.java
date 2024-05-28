package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.effects.common.ChooseCreatureTypeEffect;
import mage.cards.Card;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;

/**
 * @author LevelX2
 */
public class SpellsCostReductionAllOfChosenSubtypeEffect extends SpellsCostReductionAllEffect {

    public SpellsCostReductionAllOfChosenSubtypeEffect(FilterCard filter, int amount) {
        this(filter, amount, false);
    }

    public SpellsCostReductionAllOfChosenSubtypeEffect(FilterCard filter, int amount, boolean onlyControlled) {
        super(filter, amount, false, onlyControlled);
    }

    protected SpellsCostReductionAllOfChosenSubtypeEffect(final SpellsCostReductionAllOfChosenSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public SpellsCostReductionAllOfChosenSubtypeEffect copy() {
        return new SpellsCostReductionAllOfChosenSubtypeEffect(this);
    }

    @Override
    protected boolean selectedByRuntimeData(Card card, Ability source, Game game) {
        SubType subType = ChooseCreatureTypeEffect.getChosenCreatureType(source.getSourceId(), game);
        if (subType != null) {
            return card.hasSubtype(subType, game);
        }
        return false;
    }

}
