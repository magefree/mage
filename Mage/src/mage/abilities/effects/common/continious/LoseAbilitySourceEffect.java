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
import org.apache.log4j.Logger;
/**
 *
 * @author Noahsark
 */
public class LoseAbilitySourceEffect extends ContinuousEffectImpl<LoseAbilitySourceEffect>{
    
    private static final Logger logger = Logger.getLogger(LoseAbilitySourceEffect.class);
    protected Ability ability;
    
    public LoseAbilitySourceEffect(Ability ability){
        this(ability, Duration.WhileOnBattlefield);
    }
    
    public LoseAbilitySourceEffect(Ability ability, Duration duration){
        super(duration, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.LoseAbility);
        this.ability = ability;
        staticText = "{this} loses \"" + ability.getRule() + "\"" + duration.toString();
    }
    
    public LoseAbilitySourceEffect(final LoseAbilitySourceEffect effect){
        super(effect);
        this.ability = effect.ability.copy();
    }
    
    @Override
    public LoseAbilitySourceEffect copy(){
        return new LoseAbilitySourceEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source){
        Permanent permanent = game.getPermanent(source.getSourceId());
        if(permanent != null){
            while(permanent.getAbilities().contains(ability)){
                if (!permanent.getAbilities().remove(ability)) {
                          logger.warn("ability" + ability.getRule() + "couldn't be removed.");
                }
            }
        }
        return true;
    }
}
