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
package mage.sets.newphyrexia;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class IsolationCell extends CardImpl<IsolationCell> {

    public IsolationCell(UUID ownerId) {
        super(ownerId, 141, "Isolation Cell", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "NPH";

        // Whenever an opponent casts a creature spell, that player loses 2 life unless he or she pays {2}.
        this.addAbility(new IsolationCellTriggeredAbility());
    }

    public IsolationCell(final IsolationCell card) {
        super(card);
    }

    @Override
    public IsolationCell copy() {
        return new IsolationCell(this);
    }
}

class IsolationCellTriggeredAbility extends TriggeredAbilityImpl<IsolationCellTriggeredAbility> {

    public IsolationCellTriggeredAbility() {
        super(Zone.BATTLEFIELD, new IsolationCellEffect(), false);
    }

    public IsolationCellTriggeredAbility(final IsolationCellTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public IsolationCellTriggeredAbility copy() {
        return new IsolationCellTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST
                && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            Card card = game.getCard(event.getSourceId());
            if (card != null && card.getCardType().contains(CardType.CREATURE)) {
                this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a creature spell, that player loses 2 life unless he or she pays {2}.";
    }
}

class IsolationCellEffect extends OneShotEffect<IsolationCellEffect> {

    public IsolationCellEffect() {
        super(Outcome.Neutral);
        this.staticText = "that player loses 2 life unless he or she pays {2}";
    }

    public IsolationCellEffect(final IsolationCellEffect effect) {
        super(effect);
    }

    @Override
    public IsolationCellEffect copy() {
        return new IsolationCellEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(source));
        if (player != null) {
            GenericManaCost cost = new GenericManaCost(2);
            if (!cost.pay(source, game, player.getId(), player.getId(), false)) {
                player.damage(2, source.getSourceId(), game, false, true);
            }
            return true;
        }
        return false;
    }
}
