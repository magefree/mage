/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class GainAbilityAllOfChosenSubtypeEffect extends GainAbilityAllEffect {

    SubType subtype = null;

    public GainAbilityAllOfChosenSubtypeEffect(Ability ability, Duration duration, FilterPermanent filter) {
        super(ability, duration, filter);
    }

    public GainAbilityAllOfChosenSubtypeEffect(final GainAbilityAllOfChosenSubtypeEffect effect) {
        super(effect);
        this.subtype = effect.subtype;
    }

    @Override
    public GainAbilityAllOfChosenSubtypeEffect copy() {
        return new GainAbilityAllOfChosenSubtypeEffect(this);
    }

    @Override
    protected boolean selectedByRuntimeData(Permanent permanent, Ability source, Game game) {
        if (subtype != null) {
            return permanent.hasSubtype(subtype, game);
        }
        return false;
    }

    @Override
    protected void setRuntimeData(Ability source, Game game) {
      String s = (String) game.getState().getValue(source.getSourceId() + "_type");
      subtype = SubType.byDescription(s);
    }

}
