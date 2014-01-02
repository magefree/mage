/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.effects.common.continious;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author jeffwadsworth
 */
public class LoseAbilityTargetEffect extends ContinuousEffectImpl<LoseAbilityTargetEffect>{
    
    protected Ability ability;
    
    public LoseAbilityTargetEffect(Ability ability){
        this(ability, Duration.WhileOnBattlefield);
    }
    
    public LoseAbilityTargetEffect(Ability ability, Duration duration){
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        this.ability = ability;
    }
    
    public LoseAbilityTargetEffect(final LoseAbilityTargetEffect effect){
        super(effect);
        this.ability = effect.ability.copy();
    }
    
    @Override
    public LoseAbilityTargetEffect copy(){
        return new LoseAbilityTargetEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source){
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if(permanent != null){
            while(permanent.getAbilities().contains(ability)){
                permanent.getAbilities().remove(ability);
                }
            }
        return true;
    }
}

