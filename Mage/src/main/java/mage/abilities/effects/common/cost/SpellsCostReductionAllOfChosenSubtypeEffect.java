/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.cards.Card;
import mage.filter.FilterCard;
import mage.game.Game;

/**
 *
 * @author LevelX2
 */
public class SpellsCostReductionAllOfChosenSubtypeEffect extends SpellsCostReductionAllEffect {

    String subtype = null;

    public SpellsCostReductionAllOfChosenSubtypeEffect(FilterCard filter, int amount) {
        super(filter, amount);
    }

    public SpellsCostReductionAllOfChosenSubtypeEffect(final SpellsCostReductionAllOfChosenSubtypeEffect effect) {
        super(effect);
        this.subtype = effect.subtype;
    }

    @Override
    public SpellsCostReductionAllOfChosenSubtypeEffect copy() {
        return new SpellsCostReductionAllOfChosenSubtypeEffect(this);
    }

    @Override
    protected boolean selectedByRuntimeData(Card card, Ability source, Game game) {
        if (subtype != null) {
            return card.hasSubtype(subtype);
        }
        return false;
    }

    @Override
    protected void setRuntimeData(Ability source, Game game) {
        subtype = (String) game.getState().getValue(source.getSourceId() + "_type");
    }

}
