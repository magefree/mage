/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.constants.SubType;
import mage.filter.FilterCard;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class SpellsCostReductionAllOfChosenSubtypeEffect extends SpellsCostReductionAllEffect {

    public SpellsCostReductionAllOfChosenSubtypeEffect(FilterCard filter, int amount) {
        super(filter, amount);
    }

    public SpellsCostReductionAllOfChosenSubtypeEffect(final SpellsCostReductionAllOfChosenSubtypeEffect effect) {
        super(effect);
    }

    @Override
    public SpellsCostReductionAllOfChosenSubtypeEffect copy() {
        return new SpellsCostReductionAllOfChosenSubtypeEffect(this);
    }

    @Override
    protected boolean selectedByRuntimeData(Card card, Ability source, Game game) {
        String subtype = (String) game.getState().getValue(source.getSourceId() + "_type");
        if (subtype != null) {
            return card.hasSubtype(SubType.byDescription(subtype), game);
        }
        return false;
    }

}
