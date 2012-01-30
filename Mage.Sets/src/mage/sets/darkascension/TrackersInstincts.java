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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.TimingRule;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author North
 */
public class TrackersInstincts extends CardImpl<TrackersInstincts> {

    public TrackersInstincts(UUID ownerId) {
        super(ownerId, 128, "Tracker's Instincts", Rarity.UNCOMMON, new CardType[]{CardType.SORCERY}, "{1}{G}");
        this.expansionSetCode = "DKA";

        this.color.setGreen(true);

        // Reveal the top four cards of your library. Put a creature card from among them into your hand and the rest into your graveyard.
        this.getSpellAbility().addEffect(new TrackersInstinctsEffect());
        // Flashback {2}{U}
        this.addAbility(new FlashbackAbility(new ManaCostsImpl("{2}{U}"), TimingRule.SORCERY));
    }

    public TrackersInstincts(final TrackersInstincts card) {
        super(card);
    }

    @Override
    public TrackersInstincts copy() {
        return new TrackersInstincts(this);
    }
}

class TrackersInstinctsEffect extends OneShotEffect<TrackersInstinctsEffect> {

    public TrackersInstinctsEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top four cards of your library. Put a creature card from among them into your hand and the rest into your graveyard";
    }

    public TrackersInstinctsEffect(final TrackersInstinctsEffect effect) {
        super(effect);
    }

    @Override
    public TrackersInstinctsEffect copy() {
        return new TrackersInstinctsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            Cards cards = new CardsImpl(Zone.PICK);

            boolean creaturesFound = false;
            int count = Math.min(player.getLibrary().size(), 4);
            for (int i = 0; i < count; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    if (card.getCardType().contains(CardType.CREATURE)) {
                        creaturesFound = true;
                    }
                }
            }

            if (!cards.isEmpty()) {
                player.revealCards("Tracker's Instincts", cards, game);
                TargetCard target = new TargetCard(Zone.PICK, new FilterCreatureCard("creature card to put in hand"));
                if (creaturesFound && player.choose(Outcome.DrawCard, cards, target, game)) {
                    Card card = game.getCard(target.getFirstTarget());
                    if (card != null) {
                        cards.remove(card);
                        card.moveToZone(Zone.HAND, source.getId(), game, false);
                    }

                }

                for (UUID cardId : cards) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        card.moveToZone(Zone.GRAVEYARD, source.getId(), game, true);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
