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
package mage.sets.conspiracy;

import java.util.HashMap;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author LevelX2
 */
public class CustodiSquire extends CardImpl {

    public CustodiSquire(UUID ownerId) {
        super(ownerId, 18, "Custodi Squire", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{4}{W}");
        this.expansionSetCode = "CNS";
        this.subtype.add("Spirit");
        this.subtype.add("Cleric");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Will of the council - When Custodi Squire enters the battlefield, starting with you, each player votes for an artifact, creature, or enchantment card in your graveyard. Return each card with the most votes or tied for most votes to your hand.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CustodiSquireVoteEffect(), false, true));
    }

    public CustodiSquire(final CustodiSquire card) {
        super(card);
    }

    @Override
    public CustodiSquire copy() {
        return new CustodiSquire(this);
    }
}

class CustodiSquireVoteEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("artifact, creature, or enchantment card from your graveyard");

    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.CREATURE),
                new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    CustodiSquireVoteEffect() {
        super(Outcome.Benefit);
        this.staticText = "<i>Will of the council</i> - When {this} enters the battlefield, starting with you, each player votes for an artifact, creature, or enchantment card in your graveyard. Return each card with the most votes or tied for most votes to your hand";
    }

    CustodiSquireVoteEffect(final CustodiSquireVoteEffect effect) {
        super(effect);
    }

    @Override
    public CustodiSquireVoteEffect copy() {
        return new CustodiSquireVoteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards possibleCards = new CardsImpl();
            possibleCards.addAll(controller.getGraveyard().getCards(filter, game));
            if (!possibleCards.isEmpty()) {
                HashMap<UUID, Integer> cardCounter = new HashMap<>();
                TargetCard target = new TargetCard(1, 1, Zone.GRAVEYARD, filter);
                int maxCount = 1;
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        target.clearChosen();
                        player.chooseTarget(outcome, possibleCards, target, source, game);
                        Card card = game.getCard(target.getFirstTarget());
                        if (card != null) {
                            game.informPlayers(player.getName() + " voted for " + card.getLogName());
                            if (!cardCounter.containsKey(target.getFirstTarget())) {
                                cardCounter.put(target.getFirstTarget(), 1);
                            } else {
                                int count = cardCounter.get(target.getFirstTarget()) + 1;
                                if (count > maxCount) {
                                    maxCount = count;
                                }
                                cardCounter.put(target.getFirstTarget(), count);
                            }
                        }
                    }
                }
                Cards cardsToMove = new CardsImpl();
                for (UUID uuid : possibleCards) {
                    if (cardCounter.containsKey(uuid)) {
                        if (cardCounter.get(uuid) == maxCount) {
                            cardsToMove.add(uuid);
                        }
                    }
                }
                controller.moveCards(cardsToMove, Zone.HAND, source, game);
            }
            return true;
        }
        return false;

    }
}
