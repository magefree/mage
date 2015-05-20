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
package mage.sets.iceage;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.cards.repository.CardRepository;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author emerald000
 */
public class DemonicConsultation extends CardImpl {

    public DemonicConsultation(UUID ownerId) {
        super(ownerId, 9, "Demonic Consultation", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{B}");
        this.expansionSetCode = "ICE";


        // Name a card. Exile the top six cards of your library, then reveal cards from the top of your library until you reveal the named card. Put that card into your hand and exile all other cards revealed this way.
        this.getSpellAbility().addEffect(new DemonicConsultationEffect());
    }

    public DemonicConsultation(final DemonicConsultation card) {
        super(card);
    }

    @Override
    public DemonicConsultation copy() {
        return new DemonicConsultation(this);
    }
}

class DemonicConsultationEffect extends OneShotEffect {
    
    DemonicConsultationEffect() {
        super(Outcome.Benefit);
        this.staticText = "Name a card. Exile the top six cards of your library, then reveal cards from the top of your library until you reveal the named card. Put that card into your hand and exile all other cards revealed this way";
    }
    
    DemonicConsultationEffect(final DemonicConsultationEffect effect) {
        super(effect);
    }
    
    @Override
    public DemonicConsultationEffect copy() {
        return new DemonicConsultationEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            // Name a card.
            Choice choice = new ChoiceImpl();
            choice.setChoices(CardRepository.instance.getNames());
            while (!player.choose(Outcome.Benefit, choice, game)) {
                if (!player.isInGame()) {
                    return false;
                }
            }
            String name = choice.getChoice();
            game.informPlayers("Card named: " + name);
            
            // Exile the top six cards of your library,
            int num = Math.min(6, player.getLibrary().size());
            for (int i = 0; i < num; i++) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    player.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY, true);
                }
            }
            
            // then reveal cards from the top of your library until you reveal the named card.
            Cards cards = new CardsImpl(Zone.LIBRARY);
            while (player.getLibrary().size() > 0) {
                Card card = player.getLibrary().removeFromTop(game);
                if (card != null) {
                    cards.add(card);
                    // Put that card into your hand
                    if (card.getName().equals(name)) {
                        player.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.LIBRARY);
                        break;
                    }
                    // and exile all other cards revealed this way.
                    else {
                        player.moveCardToExileWithInfo(card, null, "", source.getSourceId(), game, Zone.LIBRARY, true);
                    }
                }
            }
            player.revealCards("Demonic Consultation", cards, game);
            return true;
        }
        return false;
    }
}
