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

import java.util.*;
import mage.Constants.Duration;
import mage.Constants.Layer;
import mage.Constants.Outcome;
import mage.Constants.SubLayer;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author magenoxx_at_googlemail.com
 */
public class ExchangeControlTargetEffect extends ContinuousEffectImpl<ExchangeControlTargetEffect> {

    private String rule;
    private Boolean withSource;
    private Map<UUID, UUID> lockedControllers;

    public ExchangeControlTargetEffect(Duration duration, String rule) {
        this(duration, rule, false);
    }

    public ExchangeControlTargetEffect(Duration duration, String rule, Boolean withSource) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.withSource = withSource;
        this.rule = rule;
    }

    public ExchangeControlTargetEffect(final ExchangeControlTargetEffect effect) {
        super(effect);
        this.rule = effect.rule;
        this.withSource = effect.withSource;
    }

    @Override
    public ExchangeControlTargetEffect copy() {
        return new ExchangeControlTargetEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        Set<UUID> controllers = new HashSet<UUID>();
        for (UUID permanentId : targetPointer.getTargets(game, source)) {
            Permanent permanent = game.getPermanent(permanentId);
            if (permanent != null) {
                controllers.add(permanent.getControllerId());
            }
        }
        if (withSource) {
            controllers.add(source.getControllerId());
        }
        // exchange works only for two different controllers
        if (controllers.size() != 2) {
            // discard effect
            this.discarded = true;
            return;
        }

        this.lockedControllers = new HashMap<UUID, UUID>();

        Iterator<UUID> it = controllers.iterator();
        UUID firstController = it.next();
        UUID secondController = it.next();

        if (withSource) {
            Permanent targetPermanent = game.getPermanent(targetPointer.getFirst(game, source));
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (targetPermanent != null && sourcePermanent != null) {
                    this.lockedControllers.put(targetPermanent.getId(), sourcePermanent.getControllerId());
                    this.lockedControllers.put(sourcePermanent.getId(), targetPermanent.getControllerId());
            }
        }
        else {
            for (UUID permanentId : targetPointer.getTargets(game, source)) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    this.lockedControllers.put(permanent.getId(), permanent.getControllerId().equals(firstController) ? secondController : firstController);
                }
            }
        }
    }

    @Override
    public boolean apply(Game game, Ability source) {
//        if (this.lockedControllers != null) {
            for (UUID permanentId : targetPointer.getTargets(game, source)) {
                Permanent permanent = game.getPermanent(permanentId);
                if (permanent != null) {
                    UUID controllerId = this.lockedControllers.get(permanent.getId());
                    if (controllerId != null) {
                        permanent.changeControllerId(controllerId, game);
                    }
                }
            }
            if (withSource) {
                Permanent permanent = game.getPermanent(source.getSourceId());
                UUID controllerId = this.lockedControllers.get(permanent.getId());
                if (controllerId != null) {
                    permanent.changeControllerId(controllerId, game);
                }
            }
            return true;
  //      }
//        return false;
    }

    @Override
    public String getText(Mode mode) {
        return this.rule;
    }
}
