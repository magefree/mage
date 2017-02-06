/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class LoseAbilityAllEffect extends ContinuousEffectImpl {

    protected final FilterPermanent filter;
    protected final Ability ability;

    public LoseAbilityAllEffect(FilterPermanent filter, Ability ability, Duration duration) {
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.AddAbility);
        this.filter = filter;
        this.ability = ability;
        staticText = filter.getMessage() + " lose " + ability.toString() + (duration.toString().isEmpty() ? "" : ' ' + duration.toString());
    }

    public LoseAbilityAllEffect(final LoseAbilityAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.ability = effect.ability;
    }

    @Override
    public LoseAbilityAllEffect copy() {
        return new LoseAbilityAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game)) {
            if (permanent != null) {
                while (permanent.getAbilities().contains(ability)) {
                    permanent.getAbilities().remove(ability);
                }
            }
        }
        return true;
    }

}
