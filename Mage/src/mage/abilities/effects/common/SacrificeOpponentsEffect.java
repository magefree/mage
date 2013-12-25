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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.util.CardUtil;

/**
 * All opponents have to sacrifice [amount] permanents
 * that match the [filter].
 *
 * @author LevelX2
 */
public class SacrificeOpponentsEffect extends OneShotEffect<SacrificeOpponentsEffect> {

    protected DynamicValue amount;
    protected FilterPermanent filter;

    public SacrificeOpponentsEffect(FilterPermanent filter) {
        this(1, filter);
    }
    public SacrificeOpponentsEffect(int amount, FilterPermanent filter) {
        this(new StaticValue(amount), filter);
    }

    public SacrificeOpponentsEffect(DynamicValue amount, FilterPermanent filter) {
        super(Outcome.Sacrifice);
        this.amount = amount;
        this.filter = filter;
        setText();
    }

    public SacrificeOpponentsEffect(final SacrificeOpponentsEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.filter = effect.filter.copy();
    }

    @Override
    public SacrificeOpponentsEffect copy() {
        return new SacrificeOpponentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> perms = new ArrayList<UUID>();
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Player player = game.getPlayer(playerId);
            if (player != null) {
                int numTargets = Math.min(amount.calculate(game, source), game.getBattlefield().countAll(filter, player.getId(), game));
                TargetControlledPermanent target = new TargetControlledPermanent(numTargets, numTargets, filter, false);
                if (target.canChoose(player.getId(), game)) {
                    while (!target.isChosen() && player.isInGame()) {
                        player.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
                    }
                    perms.addAll(target.getTargets());
                }
            }
        }
        for (UUID permID : perms) {
            Permanent permanent = game.getPermanent(permID);
            if (permanent != null) {
                permanent.sacrifice(source.getSourceId(), game);
            }
        }
        return true;
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Each opponent sacrifices ");
        if (amount.toString().equals("X")) {
            sb.append(amount.toString());
        } else {
            if (amount.toString().equals("1")) {
                if (!filter.getMessage().startsWith("a ") && !filter.getMessage().startsWith("an ")) {
                    sb.append("a");
                }
            } else {
                sb.append(CardUtil.numberToText(amount.toString()));
            }
        }
        sb.append(" ");
        sb.append(filter.getMessage());
        staticText = sb.toString();
    }

}
