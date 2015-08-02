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
package mage.sets.timespiral;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SuspendAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.players.PlayerList;
import mage.target.Target;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author emerald000
 */
public class Hypergenesis extends CardImpl {

    public Hypergenesis(UUID ownerId) {
        super(ownerId, 201, "Hypergenesis", Rarity.RARE, new CardType[]{CardType.SORCERY}, "");
        this.expansionSetCode = "TSP";
        this.color.setGreen(true);

        // Suspend 3-{1}{G}{G}
        this.addAbility(new SuspendAbility(3, new ManaCostsImpl("{1}{G}{G}"), this));
        
        // Starting with you, each player may put an artifact, creature, enchantment, or land card from his or her hand onto the battlefield. Repeat this process until no one puts a card onto the battlefield.
        this.getSpellAbility().addEffect(new HypergenesisEffect());
    }

    public Hypergenesis(final Hypergenesis card) {
        super(card);
    }

    @Override
    public Hypergenesis copy() {
        return new Hypergenesis(this);
    }
}

@SuppressWarnings("unchecked")
class HypergenesisEffect extends OneShotEffect {
    
    private static final FilterCard filter = new FilterCard("an artifact, creature, enchantment, or land card");
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT), new CardTypePredicate(CardType.CREATURE), new CardTypePredicate(CardType.ENCHANTMENT), new CardTypePredicate(CardType.LAND)));
    }

    HypergenesisEffect() {
        super(Outcome.PutCardInPlay);
        this.staticText = "Starting with you, each player may put an artifact, creature, enchantment, or land card from his or her hand onto the battlefield. Repeat this process until no one puts a card onto the battlefield.";
    }

    HypergenesisEffect(final HypergenesisEffect effect) {
        super(effect);
    }

    @Override
    public HypergenesisEffect copy() {
        return new HypergenesisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            PlayerList playerList = game.getState().getPlayerList().copy();
            while (!playerList.get().equals(source.getControllerId()) && controller.canRespond()) {
                playerList.getNext();
            }
            Player currentPlayer = game.getPlayer(playerList.get());
            UUID firstInactivePlayer = null;
            Target target = new TargetCardInHand(filter);

            while (controller.canRespond()) {                
                if (currentPlayer != null && currentPlayer.canRespond() && controller.getInRange().contains(currentPlayer.getId())) {
                    if (firstInactivePlayer == null) {
                        firstInactivePlayer = currentPlayer.getId();
                    }
                    target.clearChosen();
                    if (target.canChoose(source.getSourceId(), currentPlayer.getId(), game)
                            && currentPlayer.chooseUse(outcome, "Put card from your hand to play?", source, game)) {
                        if (target.chooseTarget(outcome, currentPlayer.getId(), source, game)) {
                            Card card = game.getCard(target.getFirstTarget());
                            if (card != null) {
                                currentPlayer.putOntoBattlefieldWithInfo(card, game, Zone.HAND, source.getSourceId());
                                firstInactivePlayer = null;
                            }
                        }
                    }
                }
                // get next player
                playerList.getNext();
                currentPlayer = game.getPlayer(playerList.get());
                // if all player since this player didn't put permanent in play finish the process
                if (currentPlayer.getId().equals(firstInactivePlayer)) {
                    break;
                }
            }
            return true;
        }
        return false;
    }
}
