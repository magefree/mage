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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.discard.DiscardHandControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetOpponent;
import mage.watchers.common.AmountOfDamageAPlayerReceivedThisTurnWatcher;

/**
 *
 * @author jeffwadsworth
 */
public class KnollspineDragon extends CardImpl {

    public KnollspineDragon(UUID ownerId) {
        super(ownerId, 98, "Knollspine Dragon", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{5}{R}{R}");
        this.expansionSetCode = "SHM";
        this.subtype.add("Dragon");
        this.power = new MageInt(7);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Knollspine Dragon enters the battlefield, you may discard your hand and draw cards equal to the damage dealt to target opponent this turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(new KnollspineDragonEffect(), true);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability, new AmountOfDamageAPlayerReceivedThisTurnWatcher());

    }

    public KnollspineDragon(final KnollspineDragon card) {
        super(card);
    }

    @Override
    public KnollspineDragon copy() {
        return new KnollspineDragon(this);
    }
}

class KnollspineDragonEffect extends OneShotEffect {

    public KnollspineDragonEffect() {
        super(Outcome.Neutral);
        staticText = "you may discard your hand and draw cards equal to the damage dealt to target opponent this turn";
    }

    public KnollspineDragonEffect(KnollspineDragonEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(targetPointer.getFirst(game, source));
        if (controller != null) {
            new DiscardHandControllerEffect().apply(game, source);
            if (targetOpponent != null) {
                AmountOfDamageAPlayerReceivedThisTurnWatcher watcher = (AmountOfDamageAPlayerReceivedThisTurnWatcher) game.getState().getWatchers().get("AmountOfDamageReceivedThisTurn");
                if (watcher != null) {
                    int drawAmount = watcher.getAmountOfDamageReceivedThisTurn(targetOpponent.getId());
                    controller.drawCards(drawAmount, game);
                    game.informPlayers(controller.getLogName() + "draws " + drawAmount + " cards");
                    return true;
                }
            }
            game.informPlayers(controller.getLogName() + "drew no cards");
            return true;
        }
        return false;
    }

    @Override
    public KnollspineDragonEffect copy() {
        return new KnollspineDragonEffect(this);
    }

}
