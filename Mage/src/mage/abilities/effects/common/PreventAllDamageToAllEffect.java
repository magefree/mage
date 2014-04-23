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

import java.util.UUID;
import mage.constants.Duration;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.constants.TargetController;
import mage.filter.FilterInPlay;
import mage.filter.common.FilterCreatureOrPlayer;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.other.PlayerIdPredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class PreventAllDamageToAllEffect extends PreventionEffectImpl<PreventAllDamageToAllEffect> {

    protected FilterInPlay filter;
    
    public PreventAllDamageToAllEffect(Duration duration, FilterCreaturePermanent filter) {
        this(duration, createFilter(filter));
    }

    public PreventAllDamageToAllEffect(Duration duration, FilterInPlay filter) {
        super(duration);
        this.filter = filter;
        staticText = "Prevent all damage that would be dealt to " + filter.getMessage() + (duration.toString().isEmpty() ?"": " "+ duration.toString());
    }

    public PreventAllDamageToAllEffect(final PreventAllDamageToAllEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    private static FilterInPlay createFilter(FilterCreaturePermanent filter) {
        FilterCreatureOrPlayer newfilter = new FilterCreatureOrPlayer(filter.getMessage());
        newfilter.setCreatureFilter(filter);
        newfilter.getPlayerFilter().add(new PlayerIdPredicate(UUID.randomUUID()));
        return newfilter;
    }
    
    @Override
    public PreventAllDamageToAllEffect copy() {
        return new PreventAllDamageToAllEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                if (filter.match(permanent, source.getSourceId(), source.getControllerId(), game)) {
                    return true;
                }
            }
            else {
                Player player = game.getPlayer(event.getTargetId());
                if (player != null && filter.match(player, source.getSourceId(), source.getControllerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }

}
