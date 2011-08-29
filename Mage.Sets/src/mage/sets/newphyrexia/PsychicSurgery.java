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
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public class PsychicSurgery extends CardImpl<PsychicSurgery> {

    public PsychicSurgery(UUID ownerId) {
        super(ownerId, 44, "Psychic Surgery", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.expansionSetCode = "NPH";

        this.color.setBlue(true);

        this.addAbility(new PsychicSurgeryTriggeredAbility());
    }

    public PsychicSurgery(final PsychicSurgery card) {
        super(card);
    }

    @Override
    public PsychicSurgery copy() {
        return new PsychicSurgery(this);
    }
}

class PsychicSurgeryTriggeredAbility extends TriggeredAbilityImpl<PsychicSurgeryTriggeredAbility> {

    public PsychicSurgeryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new PsychicSurgeryEffect(), true);
    }

    public PsychicSurgeryTriggeredAbility(final PsychicSurgeryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public PsychicSurgeryTriggeredAbility copy() {
        return new PsychicSurgeryTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.LIBRARY_SHUFFLED && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            this.getEffects().get(0).setValue("PsychicSurgeryOpponent", event.getPlayerId());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent shuffles his or her library, you may look at the top two cards of that library. You may exile one of those cards. Then put the rest on top of that library in any order.";
    }
}

class PsychicSurgeryEffect extends OneShotEffect<PsychicSurgeryEffect> {

    public PsychicSurgeryEffect() {
        super(Outcome.Exile);
        this.staticText = "look at the top two cards of that library. You may exile one of those cards. Then put the rest on top of that library in any order";
    }

    public PsychicSurgeryEffect(final PsychicSurgeryEffect effect) {
        super(effect);
    }

    @Override
    public PsychicSurgeryEffect copy() {
        return new PsychicSurgeryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID opponentId = (UUID) this.getValue("PsychicSurgeryOpponent");
        Player player = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(opponentId);

        if (player != null && opponent != null) {
            Cards cards = new CardsImpl(Zone.PICK);
            int count = Math.min(player.getLibrary().size(), 2);
            for (int i = 0; i < count; i++) {
                Card card = opponent.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                }
            }
            player.lookAtCards("Psychic Surgery", cards, game);

            if (!cards.isEmpty() && player.chooseUse(Outcome.Exile, "Do you wish to exile a card?", game)) {
                TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to exile"));
                if (player.choose(Outcome.Exile, cards, target, game)) {
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        cards.remove(card);
                        card.moveToZone(Zone.EXILED, source.getId(), game, false);
                    }
                }
            }

            TargetCard target = new TargetCard(Zone.PICK, new FilterCard("card to put on top of his library"));
            target.setRequired(true);
            while (cards.size() > 1) {
                player.choose(Outcome.Neutral, cards, target, game);
                Card card = cards.get(target.getFirstTarget(), game);
                if (card != null) {
                    cards.remove(card);
                    card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
                }
                target.clearChosen();
            }
            if (cards.size() == 1) {
                Card card = cards.get(cards.iterator().next(), game);
                card.moveToZone(Zone.LIBRARY, source.getId(), game, true);
            }

            return true;
        }
        return false;
    }
}
