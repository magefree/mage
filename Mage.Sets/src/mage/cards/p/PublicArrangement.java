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
package mage.sets.starwars;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.BountyAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.counters.CounterType;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.common.TargetOpponentsCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class PublicArrangement extends CardImpl {

    public PublicArrangement(UUID ownerId) {
        super(ownerId, 83, "Public Arrangement", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");
        this.expansionSetCode = "SWS";

        // When Public Arrangement enters the battlefield, put a bounty counter on up to two target creatures your opponents control.
        Ability ability = new EntersBattlefieldTriggeredAbility(new AddCountersTargetEffect(CounterType.BOUNTY.createInstance()));
        ability.addTarget(new TargetOpponentsCreaturePermanent(0, 2));
        this.addAbility(ability);

        // <i>Bounty</i> &mdash; Whenever a creature an opponent controls with a bounty counter on it dies, that creature's controler loses 2 life. Each other player gains 2 life.
        this.addAbility(new BountyAbility(new PublicArrangementEffect(), false, true));
    }

    public PublicArrangement(final PublicArrangement card) {
        super(card);
    }

    @Override
    public PublicArrangement copy() {
        return new PublicArrangement(this);
    }
}

class PublicArrangementEffect extends OneShotEffect {

    public PublicArrangementEffect() {
        super(Outcome.LoseLife);
        staticText = "that creature's controler loses 2 life. Each other player gains 2 life";
    }

    public PublicArrangementEffect(final PublicArrangementEffect effect) {
        super(effect);
    }

    @Override
    public PublicArrangementEffect copy() {
        return new PublicArrangementEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID controller = game.getControllerId(source.getFirstTarget());
        if (controller != null) {
            game.getPlayer(controller).loseLife(2, game, false);
            for (UUID playerId : game.getOpponents(controller)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.gainLife(2, game);
                }
            }
            return true;
        }
        return false;
    }

}
