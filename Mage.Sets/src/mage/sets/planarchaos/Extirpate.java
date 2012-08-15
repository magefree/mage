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
package mage.sets.planarchaos;

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jonubuu
 */
public class Extirpate extends CardImpl<Extirpate> {

    private static final FilterCard filter = new FilterCard("card in a graveyard other than a basic land card");

    static {
        filter.add(Predicates.not(new SupertypePredicate("Basic")));
    }

    public Extirpate(UUID ownerId) {
        super(ownerId, 71, "Extirpate", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{B}");
        this.expansionSetCode = "PLC";

        this.color.setBlack(true);

        // Split second
        this.addAbility(SplitSecondAbility.getInstance());
        // Choose target card in a graveyard other than a basic land card. Search its owner's graveyard, hand, and library for all cards with the same name as that card and exile them. Then that player shuffles his or her library.
        this.getSpellAbility().addEffect(new ExtirpateEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
    }

    public Extirpate(final Extirpate card) {
        super(card);
    }

    @Override
    public Extirpate copy() {
        return new Extirpate(this);
    }
}

class ExtirpateEffect extends OneShotEffect<ExtirpateEffect> {

    public ExtirpateEffect() {
        super(Outcome.Exile);
        this.staticText = "Choose target card in a graveyard other than a basic land card. Search its owner's graveyard, hand, and library for any number of cards with the same name as that card and exile them. Then that player shuffles his or her library";
    }

    public ExtirpateEffect(final ExtirpateEffect effect) {
        super(effect);
    }

    @Override
    public ExtirpateEffect copy() {
        return new ExtirpateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getFirstTarget());
        Player player = game.getPlayer(source.getControllerId());

        if (card != null && player != null) {
            Player targetPlayer = game.getPlayer(card.getOwnerId());
            if (targetPlayer != null) {
                FilterCard filter = new FilterCard("card named " + card.getName());
                filter.add(new NamePredicate(card.getName()));

                Cards cardsInLibrary = new CardsImpl(Zone.LIBRARY);
                cardsInLibrary.addAll(targetPlayer.getLibrary().getCards(game));

                // cards in Graveyard
                int cardsCount = targetPlayer.getGraveyard().count(filter, game);
                if (cardsCount > 0) {
                    filter.setMessage("card named " + card.getName() + " in the graveyard of " + targetPlayer.getName());
                    TargetCardInGraveyard target = new TargetCardInGraveyard(0, cardsCount, filter);
                    if (player.choose(Outcome.Exile, targetPlayer.getGraveyard(), target, game)) {
                        List<UUID> targets = target.getTargets();
                        for (UUID targetId : targets) {
                            Card targetCard = targetPlayer.getGraveyard().get(targetId, game);
                            if (targetCard != null) {
                                targetPlayer.getGraveyard().remove(targetCard);
                                targetCard.moveToZone(Zone.EXILED, source.getId(), game, false);
                            }
                        }
                    }
                }

                // cards in Hand
                cardsCount = targetPlayer.getHand().count(filter, game);
                if (cardsCount > 0) {
                    filter.setMessage("card named " + card.getName() + " in the hand of " + targetPlayer.getName());
                    TargetCardInHand target = new TargetCardInHand(0, cardsCount, filter);
                    if (player.choose(Outcome.Exile, targetPlayer.getHand(), target, game)) {
                        List<UUID> targets = target.getTargets();
                        for (UUID targetId : targets) {
                            Card targetCard = targetPlayer.getHand().get(targetId, game);
                            if (targetCard != null) {
                                targetPlayer.getHand().remove(targetCard);
                                targetCard.moveToZone(Zone.EXILED, source.getId(), game, false);
                            }
                        }
                    }
                } else {
                    player.lookAtCards(targetPlayer.getName() + " hand", targetPlayer.getHand(), game);
                }

                // cards in Library
                cardsCount = cardsInLibrary.count(filter, game);
                if (cardsCount > 0) {
                    filter.setMessage("card named " + card.getName() + " in the library of " + targetPlayer.getName());
                    TargetCardInLibrary target = new TargetCardInLibrary(0, cardsCount, filter);
                    if (player.choose(Outcome.Exile, cardsInLibrary, target, game)) {
                        List<UUID> targets = target.getTargets();
                        for (UUID targetId : targets) {
                            Card targetCard = targetPlayer.getLibrary().remove(targetId, game);
                            if (targetCard != null) {
                                targetCard.moveToZone(Zone.EXILED, source.getId(), game, false);
                            }
                        }
                    }
                } else {
                    player.lookAtCards(targetPlayer.getName() + " library", cardsInLibrary, game);
                }
            }

            targetPlayer.shuffleLibrary(game);

            return true;
        }

        return false;
    }
}
