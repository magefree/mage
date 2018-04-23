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
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.OrCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author L_J
 */
public class LimDulsHex extends CardImpl {

    public LimDulsHex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // At the beginning of your upkeep, for each player, Lim-Dul's Hex deals 1 damage to that player unless he or she pays {B} or {3}.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new LimDulsHexEffect(), TargetController.YOU, false));
    }

    public LimDulsHex(final LimDulsHex card) {
        super(card);
    }

    @Override
    public LimDulsHex copy() {
        return new LimDulsHex(this);
    }
}

class LimDulsHexEffect extends OneShotEffect {

    public LimDulsHexEffect() {
        super(Outcome.Damage);
        this.staticText = "for each player, {this} deals 1 damage to that player unless he or she pays {B} or {3}";
    }
    
    public LimDulsHexEffect(final LimDulsHexEffect effect) {
        super(effect);
    }
    
    @Override
    public LimDulsHexEffect copy() {
        return new LimDulsHexEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    OrCost costToPay = new OrCost(new ManaCostsImpl("{B}"), new ManaCostsImpl("{3}"), "{B} or {3}");
                    costToPay.clearPaid();
                    String message = "Would you like to pay " + costToPay.getText() + " to prevent 1 damage from " + sourcePermanent.getLogName() + "?";
                    if (!(player.chooseUse(Outcome.Benefit, message, source, game) && costToPay.pay(source, game, source.getSourceId(), player.getId(), false, null))) {
                        game.informPlayers(player.getLogName() + " chooses not to pay " + costToPay.getText() + " to prevent 1 damage from " + sourcePermanent.getLogName());
                        player.damage(1, sourcePermanent.getId(), game, false, true);
                    } else {
                        game.informPlayers(player.getLogName() + " chooses to pay " + costToPay.getText() + " to prevent 1 damage from " + sourcePermanent.getLogName());
                    }
                }
            }
            return true;
        }
        return false;
    }
}
