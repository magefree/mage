/*
 *  
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 * 
 */
package mage.abilities.effects.common.continious;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.StackObject;
import mage.players.Player;
import java.util.List;
import mage.ObjectColor;

/**
 *
 * @author philvt101
 */

public class BecomesColorOrColorsTargetEffect extends ContinuousEffectImpl {
   
    private final List<ObjectColor> colors;
    
    /**
     * Set the color or colors of a spell or permanent
     * 
     * @param duration 
     * @param colors
     */
    
    public BecomesColorOrColorsTargetEffect(List<ObjectColor> colors, Duration duration) {
        super(duration, Layer.ColorChangingEffects_5, SubLayer.NA, Outcome.Benefit);
        this.colors=colors;
    }
    
    @Override
    public boolean apply(Game game, Ability source){
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        boolean result = false;
        
        for (UUID targetId :targetPointer.getTargets(game, source)) {
                MageObject o = game.getObject(targetId);
                if(o != null){
                    if(o instanceof Permanent || o instanceof StackObject);{
                        if(!colors.isEmpty()){    
                            /**As target may become a single color other than its original color,
                             * all colors must be initialized to false before applying chosen color/colors
                             */
                            o.getColor().setWhite(false);
                            o.getColor().setBlue(false);
                            o.getColor().setBlack(false);
                            o.getColor().setRed(false);
                            o.getColor().setGreen(false);
                            
                            for (ObjectColor color : colors) {
                                if (color.isWhite()) {
                                    o.getColor().setWhite(true);
                                } else if (color.isBlue()) {
                                    o.getColor().setBlue(true);
                                } else if (color.isBlack()) {
                                    o.getColor().setBlack(true);
                                } else if (color.isRed()) {
                                    o.getColor().setRed(true);
                                } else if (color.isGreen()) {
                                    o.getColor().setGreen(true);
                                }
                            }
                        }      
                    }
                }
        }
        
        if (!result) {
            if (this.getDuration().equals(Duration.Custom)) {
                this.discard();
            }
        }
        return result;
    }
    
    public BecomesColorOrColorsTargetEffect(final BecomesColorOrColorsTargetEffect effect) {
        super(effect);
        this.colors = effect.colors;
    }
    
    @Override
    public BecomesColorOrColorsTargetEffect copy() {
        return new BecomesColorOrColorsTargetEffect(this);
    }
    
}
