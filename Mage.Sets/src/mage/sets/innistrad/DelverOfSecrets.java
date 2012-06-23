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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author Alvin
 */
public class DelverOfSecrets extends CardImpl<DelverOfSecrets> {

    public DelverOfSecrets(UUID ownerId) {
        super(ownerId, 51, "Delver of Secrets", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{U}");
        this.expansionSetCode = "ISD";
        this.subtype.add("Human");
        this.subtype.add("Wizard");

        this.color.setBlue(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.canTransform = true;
        this.secondSideCard = new InsectileAberration(ownerId);

        // At the beginning of your upkeep, look at the top card of your library. You may reveal that card. If an instant or sorcery card is revealed this way, transform Delver of Secrets.
        this.addAbility(new TransformAbility());
        this.addAbility(new DelverOfSecretsAbility());
    }

    public DelverOfSecrets(final DelverOfSecrets card) {
        super(card);
    }

    @Override
    public DelverOfSecrets copy() {
        return new DelverOfSecrets(this);
    }
}

class DelverOfSecretsAbility extends TriggeredAbilityImpl<DelverOfSecretsAbility> {

    public DelverOfSecretsAbility() {
        super(Constants.Zone.BATTLEFIELD, new TransformSourceEffect(true), false);
    }

    public DelverOfSecretsAbility(DelverOfSecretsAbility ability) {
        super(ability);
    }

    @Override
    public DelverOfSecretsAbility copy() {
        return new DelverOfSecretsAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE && event.getPlayerId().equals(this.controllerId)) {
            Player player = game.getPlayer(this.controllerId);
            if (player != null && player.getLibrary().size() > 0) {
                Card card = player.getLibrary().getFromTop(game);
                Cards cards = new CardsImpl();
                cards.add(card);
                player.lookAtCards("This card", cards, game);
                if (player.chooseUse(Outcome.DrawCard, "Do you wish to reveal the card at the top of the liberary?", game))
                {
                    player.revealCards("Delver of Secrets", cards, game);
                    if ((card.getCardType().contains(CardType.INSTANT) || card.getCardType().contains(CardType.SORCERY))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "At the beginning of your upkeep, look at the top card of your library. You may reveal that card. If an instant or sorcery card is revealed this way, transform Delver of Secrets";
    }
}