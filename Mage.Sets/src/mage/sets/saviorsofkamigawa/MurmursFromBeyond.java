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
package mage.sets.saviorsofkamigawa;

import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public class MurmursFromBeyond extends CardImpl {

    public MurmursFromBeyond(UUID ownerId) {
        super(ownerId, 47, "Murmurs from Beyond", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{2}{U}");
        this.expansionSetCode = "SOK";
        this.subtype.add("Arcane");

        // Reveal the top three cards of your library. An opponent chooses one of them. Put that card into your graveyard and the rest into your hand.
        this.getSpellAbility().addEffect(new MurmursFromBeyondEffect());
    }

    public MurmursFromBeyond(final MurmursFromBeyond card) {
        super(card);
    }

    @Override
    public MurmursFromBeyond copy() {
        return new MurmursFromBeyond(this);
    }
}

class MurmursFromBeyondEffect extends OneShotEffect {

    public MurmursFromBeyondEffect() {
        super(Outcome.Benefit);
        this.staticText = "Reveal the top three cards of your library. An opponent chooses one of them. Put that card into your graveyard and the rest into your hand";
    }

    public MurmursFromBeyondEffect(final MurmursFromBeyondEffect effect) {
        super(effect);
    }

    @Override
    public MurmursFromBeyondEffect copy() {
        return new MurmursFromBeyondEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null && controller != null) {
            Cards cards = new CardsImpl();
            cards.addAll(controller.getLibrary().getTopCards(game, 3));
            if (!cards.isEmpty()) {
                controller.revealCards(staticText, cards, game);
                Card cardToGraveyard;
                if (cards.size() == 1) {
                    cardToGraveyard = cards.getRandom(game);
                } else {
                    Player opponent;
                    Set<UUID> opponents = game.getOpponents(controller.getId());
                    if (opponents.size() == 1) {
                        opponent = game.getPlayer(opponents.iterator().next());
                    } else {
                        Target target = new TargetOpponent(true);
                        controller.chooseTarget(Outcome.Detriment, target, source, game);
                        opponent = game.getPlayer(target.getFirstTarget());
                    }
                    TargetCard target = new TargetCard(1, Zone.LIBRARY, new FilterCard());
                    opponent.chooseTarget(outcome, cards, target, source, game);
                    cardToGraveyard = game.getCard(target.getFirstTarget());
                }                
                if (cardToGraveyard != null) {                    
                    controller.moveCards(cardToGraveyard,Zone.LIBRARY, Zone.GRAVEYARD, source, game);
                    cards.remove(cardToGraveyard);
                }
                controller.moveCards(cards, Zone.LIBRARY, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
