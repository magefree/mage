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

import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterObject;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetSource;

/**
 *
 * @author LevelX2
 */
public class PreventAllDamageFromChosenSourceToYouEffect extends PreventionEffectImpl {

    protected final TargetSource targetSource;

    public PreventAllDamageFromChosenSourceToYouEffect(Duration duration) {
        this(duration, new FilterObject("source"));
    }

    public PreventAllDamageFromChosenSourceToYouEffect(Duration duration, FilterObject filter) {
        this(duration, filter, false);
    }

    public PreventAllDamageFromChosenSourceToYouEffect(Duration duration, FilterObject filter, boolean onlyCombat) {
        super(duration, Integer.MAX_VALUE, onlyCombat);
        this.targetSource = new TargetSource(filter);
        this.staticText = setText();
    }

    public PreventAllDamageFromChosenSourceToYouEffect(final PreventAllDamageFromChosenSourceToYouEffect effect) {
        super(effect);
        this.targetSource = effect.targetSource.copy();
    }

    @Override
    public PreventAllDamageFromChosenSourceToYouEffect copy() {
        return new PreventAllDamageFromChosenSourceToYouEffect(this);
    }

    @Override
    public void init(Ability source, Game game) {
        this.targetSource.choose(Outcome.PreventDamage, source.getControllerId(), source.getSourceId(), game);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        preventDamageAction(event, source, game);
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            if (event.getTargetId().equals(source.getControllerId()) && event.getSourceId().equals(targetSource.getFirstTarget())) {
                return true;
            }
        }
        return false;
    }

    private String setText() {
        StringBuilder sb = new StringBuilder("Prevent all ");
        if (onlyCombat) {
            sb.append("combat ");
        }
        sb.append("damage that would be dealt to you ");
        if (duration.equals(Duration.EndOfTurn)) {
            sb.append("this turn ");
        }
        sb.append("by a ").append(targetSource.getFilter().getMessage());
        sb.append(" of your choice");
        return sb.toString();
    }

}
