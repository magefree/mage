/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
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
 */
package mage.abilities.effects.common;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.Card;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSource;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */

public class PreventDamageBySourceEffect extends PreventionEffectImpl {

    private TargetSource target;
    private MageObjectReference mageObjectReference;

    public PreventDamageBySourceEffect() {
        this(new FilterObject("a"));
    }

    public PreventDamageBySourceEffect(FilterObject filterObject) {
        super(Duration.EndOfTurn);
        if (filterObject.getMessage().equals("a")) {
            this.target = new TargetSource(new FilterObject("source"));
        } else {
            this.target = new TargetSource(new FilterObject(filterObject.getMessage() + " source"));
        }
        staticText = "Prevent all damage " + filterObject.getMessage() + " source of your choice would deal this turn";
    }

    public PreventDamageBySourceEffect(final PreventDamageBySourceEffect effect) {
        super(effect);
        this.target = effect.target.copy();
        this.mageObjectReference = effect.mageObjectReference;
    }

    @Override
    public PreventDamageBySourceEffect copy() {
        return new PreventDamageBySourceEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.target.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), game);
        mageObjectReference = new MageObjectReference(target.getFirstTarget(), game);        
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            MageObject mageObject = game.getObject(event.getSourceId());
            if (mageObject != null && mageObjectReference.refersTo(mageObject, game)) {
                return true;
            }            
        }
        return false;
    }

}
