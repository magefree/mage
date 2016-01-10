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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class DashHopes extends CardImpl {

    public DashHopes(UUID ownerId) {
        super(ownerId, 68, "Dash Hopes", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{B}{B}");
        this.expansionSetCode = "PLC";

        // When you cast Dash Hopes, any player may pay 5 life. If a player does, counter Dash Hopes.
        this.addAbility(new CastSourceTriggeredAbility(new DashHopesCounterSourceEffect()));

        // Counter target spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell());
    }

    public DashHopes(final DashHopes card) {
        super(card);
    }

    @Override
    public DashHopes copy() {
        return new DashHopes(this);
    }
}

class DashHopesCounterSourceEffect extends OneShotEffect {

    public DashHopesCounterSourceEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "any player may pay 5 life. If a player does, counter {source}";
    }

    public DashHopesCounterSourceEffect(final DashHopesCounterSourceEffect effect) {
        super(effect);
    }

    @Override
    public DashHopesCounterSourceEffect copy() {
        return new DashHopesCounterSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            PayLifeCost cost = new PayLifeCost(5);
            for (UUID playerId : game.getState().getPlayerList(source.getControllerId())) {
                Player player = game.getPlayer(playerId);
                cost.clearPaid();
                if (cost.canPay(source, source.getSourceId(), player.getId(), game)
                        && player.chooseUse(outcome, "Pay 5 life to counter " + sourceObject.getIdName() + "?", source, game)) {
                    if (cost.pay(source, game, source.getSourceId(), player.getId(), false, null)) {
                        game.informPlayers(player.getLogName() + " pays 5 life to counter " + sourceObject.getIdName() + ".");
                        Spell spell = game.getStack().getSpell(source.getSourceId());
                        if (spell != null) {
                            game.getStack().counter(spell.getId(), source.getSourceId(), game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

}
