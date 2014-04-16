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
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
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
    private boolean withSource;
    private boolean withSecondTarget;
    private Map<UUID, Integer> zoneChangeCounter = new HashMap<>();
    private Map<UUID, UUID> lockedControllers = new HashMap<>();
 
    public ExchangeControlTargetEffect(Duration duration, String rule) {
        this(duration, rule, false);
    }
 
    public ExchangeControlTargetEffect(Duration duration, String rule, boolean withSource) {
        this(duration, rule, withSource, false);
    }
    
    public ExchangeControlTargetEffect(Duration duration, String rule, boolean withSource, boolean withSecondTarget) {
        super(duration, Layer.ControlChangingEffects_2, SubLayer.NA, Outcome.GainControl);
        this.withSource = withSource;
        this.withSecondTarget = withSecondTarget;
        this.rule = rule;
    }
 
    public ExchangeControlTargetEffect(final ExchangeControlTargetEffect effect) {
        super(effect);
        this.rule = effect.rule;
        this.withSource = effect.withSource;
        this.withSecondTarget = effect.withSecondTarget;
        this.lockedControllers = effect.lockedControllers;
        this.zoneChangeCounter = effect.zoneChangeCounter;
    }
 
    @Override
    public ExchangeControlTargetEffect copy() {
        return new ExchangeControlTargetEffect(this);
    }
 
    @Override
    public boolean isInactive(Ability source, Game game) {
       return isDiscarded();
    }
 
    @Override
    public void init(Ability source, Game game) {
        Permanent permanent1 = null;
        Permanent permanent2 = null;
        
        if (withSource) {
            permanent1 = game.getPermanent(targetPointer.getFirst(game, source));
            permanent2 = game.getPermanent(source.getSourceId());
        } else {
            for (UUID permanentId : targetPointer.getTargets(game, source)) {
                if (permanent1 == null) {
                    permanent1 = game.getPermanent(permanentId);
                } else if (permanent2 == null) {
                    permanent2 = game.getPermanent(permanentId);
                }
            }
            if (withSecondTarget) {
                UUID uuid = source.getTargets().get(1).getFirstTarget();
                permanent2 = game.getPermanent(uuid);
            }
        }
        if (permanent1 != null && permanent2 != null) {
            // exchange works only for two different controllers
            if (permanent1.getControllerId().equals(permanent2.getControllerId())) {
                // discard effect if controller of both permanents is the same
                discard();
                return;
            }
            this.lockedControllers.put(permanent1.getId(), permanent2.getControllerId());
            this.zoneChangeCounter.put(permanent1.getId(), permanent1.getZoneChangeCounter());
            this.lockedControllers.put(permanent2.getId(), permanent1.getControllerId());
            this.zoneChangeCounter.put(permanent2.getId(), permanent2.getZoneChangeCounter());            
        } else {
            // discard if there are less than 2 permanents
            discard();
        }
    }
 
    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> toDelete = new HashSet<>();
        for (Map.Entry<UUID, Integer> entry: zoneChangeCounter.entrySet()) {
            Permanent permanent = game.getPermanent(entry.getKey());
            if (permanent == null || permanent.getZoneChangeCounter() != entry.getValue()) {
                // controll effect cease if the same permanent is no longer on the battlefield
                toDelete.add(entry.getKey());
                continue;
            }
            permanent.changeControllerId(lockedControllers.get(permanent.getId()), game);
        }
        if (!toDelete.isEmpty()) {
            for(UUID uuid: toDelete) {
                zoneChangeCounter.remove(uuid);            
            }
            if (zoneChangeCounter.isEmpty()) {
                discard();
                return false;
            }
        }
        return true;
    }
 
    @Override
    public String getText(Mode mode) {
        return this.rule;
    }
}
