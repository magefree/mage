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

import mage.Constants;
import mage.Constants.Outcome;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 *
 * @author maurer.it_at_gmail.com
 */
public class SacrificeEffect extends OneShotEffect<SacrificeEffect>{

    private FilterPermanent filter;
    private String preText;
    private DynamicValue count;

    public SacrificeEffect ( FilterPermanent filter, DynamicValue count, String preText ) {
        super(Outcome.Sacrifice);
        this.filter = filter;
        this.count = count;
        this.preText = preText;
        setText();
    }

    public SacrificeEffect ( FilterPermanent filter, int count, String preText ) {
        this(filter, new StaticValue(count), preText);
    }

    public SacrificeEffect ( SacrificeEffect effect ) {
        super(effect);
        this.filter = effect.filter;
        this.count = effect.count;
        this.preText = effect.preText;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));

        if (player == null) {
            return false;
        }

        filter.add(new ControllerPredicate(Constants.TargetController.YOU));

        int amount = count.calculate(game, source);
        int realCount = game.getBattlefield().countAll(filter, player.getId(), game);
        amount = Math.min(amount, realCount);

        Target target = new TargetControlledPermanent(amount, amount, filter, false);
        target.setRequired(true);

        //A spell or ability could have removed the only legal target this player
        //had, if thats the case this ability should fizzle.
        if (amount > 0 && target.canChoose(source.getSourceId(), player.getId(), game)) {
            boolean abilityApplied = false;
            while (!target.isChosen() && target.canChoose(player.getId(), game)) {
                player.choose(Outcome.Sacrifice, target, source.getSourceId(), game);
            }

            for ( int idx = 0; idx < target.getTargets().size(); idx++) {
                Permanent permanent = game.getPermanent((UUID)target.getTargets().get(idx));

                if ( permanent != null ) {
                    abilityApplied |= permanent.sacrifice(source.getSourceId(), game);
                }
            }

            return abilityApplied;
        }
        return false;
    }

    public void setAmount(DynamicValue amount) {
        this.count = amount;
    }

    @Override
    public SacrificeEffect copy() {
        return new SacrificeEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder();
        sb.append(preText);
        if (preText.contains("player")) {
            sb.append(" sacrifices ");
        } else {
            sb.append(" sacrifice ");
        }
        if (!count.toString().equals("1")) {
            sb.append(count).append(" ");
        }
        sb.append(filter.getMessage());
        staticText = sb.toString();
    }
}
