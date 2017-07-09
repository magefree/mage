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

import java.util.List;
import java.util.UUID;
import mage.constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author BetaSteward_at_googlemail.com
 */
public class DamageEverythingEffect extends OneShotEffect {

    private DynamicValue amount;
    private FilterPermanent filter;
    private UUID damageSource;

    public DamageEverythingEffect(int amount) {
        this(new StaticValue(amount), new FilterCreaturePermanent());
    }

    public DamageEverythingEffect(DynamicValue amount) {
        this(amount, new FilterCreaturePermanent());
    }

    public DamageEverythingEffect(int amount, FilterPermanent filter) {
        this(new StaticValue(amount), filter);
    }
    public DamageEverythingEffect(DynamicValue amount, FilterPermanent filter) {
        this(amount, filter, null);
    }
    
    public DamageEverythingEffect(DynamicValue amount, FilterPermanent filter, UUID damageSource) {
        super(Outcome.Damage);
        this.amount = amount;
        this.filter = filter;
        this.damageSource = damageSource;
        staticText = "{source} deals " + amount.toString() + " damage to each " + filter.getMessage() + " and each player";   
    }

    public DamageEverythingEffect(final DamageEverythingEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.filter = effect.filter;
        this.damageSource = effect.damageSource;
    }

    @Override
    public DamageEverythingEffect copy() {
        return new DamageEverythingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int damage = amount.calculate(game, source, this);
        List<Permanent> permanents = game.getBattlefield().getActivePermanents(filter, source.getControllerId(), game);
        for (Permanent permanent: permanents) {
            permanent.damage(damage, damageSource == null ? source.getSourceId(): damageSource, game, false, true);
        }
        for (UUID playerId: game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                player.damage(damage, damageSource == null ? source.getSourceId(): damageSource, game, false, true);
            }
        }
        return true;
    }
}
