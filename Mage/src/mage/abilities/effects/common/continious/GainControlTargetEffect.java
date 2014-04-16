/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.abilities.effects.common.continious;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class GainControlTargetEffect extends ContinuousEffectImpl<GainControlTargetEffect> {

    private UUID controllingPlayerId;
    private boolean fixedControl;

    public GainControlTargetEffect(Duration duration) {
        this(duration, false, null);
    }

    /**
     *
     * @param duration
     * @param fixedControl Controlling player is fixed even if the controller of the ability changes later
     */
    public GainControlTargetEffect(Duration duration, boolean fixedControl) {
        this(duration, fixedControl, null);
    }

    /**
     *
     * @param duration
     * @param controllingPlayerId Player that controlls the target creature
     */
    public GainControlTargetEffect(Duration duration, UUID controllingPlayerId) {
        this(duration, true, controllingPlayerId);

    }
    public GainControlTargetEffect(Duration duration, boolean fixedControl, UUID controllingPlayerId) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.controllingPlayerId = controllingPlayerId;
        this.fixedControl = fixedControl;
    }

    public GainControlTargetEffect(final GainControlTargetEffect effect) {
        super(effect);
        this.controllingPlayerId = effect.controllingPlayerId;
        this.fixedControl = effect.fixedControl;
    }

    @Override
    public GainControlTargetEffect copy() {
        return new GainControlTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        if (this.controllingPlayerId == null && fixedControl) {
            this.controllingPlayerId = source.getControllerId();
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());        
        if (controller != null) {
            boolean targetStillExists = false;
            for (UUID permanentId: this.getTargetPointer().getTargets(game, source)) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    targetStillExists = true;
                    if (controllingPlayerId != null) {
                        permanent.changeControllerId(controllingPlayerId, game);                        
                    } else {
                        permanent.changeControllerId(source.getControllerId(), game);
                    }
                }                
            }
            if (!targetStillExists) {
                // no valid target exists, effect can be discarded
                this.discard();
            }
            return true;
        }   
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (!staticText.isEmpty()) {
            return staticText;
        }
        StringBuilder sb = new StringBuilder("Gain control of ");
        if (!mode.getTargets().get(0).getTargetName().startsWith("another")) {
            sb.append("target ");
        }
        sb.append(mode.getTargets().get(0).getTargetName());
        if (!duration.toString().isEmpty()) {
            sb.append(" ").append(duration.toString());
        }
        return sb.toString();
    }
}
