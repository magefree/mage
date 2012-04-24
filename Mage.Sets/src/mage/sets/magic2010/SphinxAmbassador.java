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
package mage.sets.magic2010;

import java.util.Collection;
import java.util.TreeSet;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public class SphinxAmbassador extends CardImpl<SphinxAmbassador> {

    public SphinxAmbassador(UUID ownerId) {
        super(ownerId, 73, "Sphinx Ambassador", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");
        this.expansionSetCode = "M10";
        this.subtype.add("Sphinx");

        this.color.setBlue(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        this.addAbility(FlyingAbility.getInstance());
        // Whenever Sphinx Ambassador deals combat damage to a player, search that player's library for a card, then that player names a card. If you searched for a creature card that isn't the named card, you may put it onto the battlefield under your control. Then that player shuffles his or her library.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new SphinxAmbassadorEffect(), false, true));
    }

    public SphinxAmbassador(final SphinxAmbassador card) {
        super(card);
    }

    @Override
    public SphinxAmbassador copy() {
        return new SphinxAmbassador(this);
    }
}

class SphinxAmbassadorEffect extends OneShotEffect<SphinxAmbassadorEffect> {

    public SphinxAmbassadorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "search that player's library for a card, then that player names a card. If you searched for a creature card that isn't the named card, you may put it onto the battlefield under your control. Then that player shuffles his or her library";
    }

    public SphinxAmbassadorEffect(final SphinxAmbassadorEffect effect) {
        super(effect);
    }

    @Override
    public SphinxAmbassadorEffect copy() {
        return new SphinxAmbassadorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(source));

        if (player != null && targetPlayer != null) {
            TargetCardInLibrary target = new TargetCardInLibrary();
            player.searchLibrary(target, game, targetPlayer.getId());

            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                TreeSet<String> choices = new TreeSet<String>();
                Collection<Card> cards = game.getCards();
                for (Card gameCard : cards) {
                    if (gameCard.getOwnerId().equals(targetPlayer.getId())) {
                        choices.add(gameCard.getName());
                    }
                }

                Choice cardChoice = new ChoiceImpl();
                cardChoice.setChoices(choices);
                cardChoice.clearChoice();
                while (!targetPlayer.choose(Outcome.Benefit, cardChoice, game)) {
                    game.debugMessage("player canceled choosing name. retrying.");
                }
                String cardName = cardChoice.getChoice();

                game.informPlayers("Sphinx Ambassador, named card: [" + cardName + "]");
                if (!card.getName().equals(cardName) && card.getCardType().contains(CardType.CREATURE)) {
                    card.putOntoBattlefield(game, Zone.LIBRARY, source.getId(), player.getId());
                }
            }

            targetPlayer.shuffleLibrary(game);
            return true;
        }
        return false;
    }
}
