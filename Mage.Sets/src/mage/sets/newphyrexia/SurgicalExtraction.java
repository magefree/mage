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

import java.util.List;
import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author North
 */
public class SurgicalExtraction extends CardImpl<SurgicalExtraction> {

    private static final FilterCard filter = new FilterCard("card in a graveyard other than a basic land card");

    static {
        filter.getSupertype().add("Basic");
        filter.setNotSupertype(true);
    }

    public SurgicalExtraction(UUID ownerId) {
        super(ownerId, 74, "Surgical Extraction", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{BP}");
        this.expansionSetCode = "NPH";

        this.color.setBlack(true);

        this.getSpellAbility().addEffect(new SurgicalExtractionEffect());
        this.getSpellAbility().addTarget(new TargetCardInGraveyard(filter));
    }

    public SurgicalExtraction(final SurgicalExtraction card) {
        super(card);
    }

    @Override
    public SurgicalExtraction copy() {
        return new SurgicalExtraction(this);
    }
}

class SurgicalExtractionEffect extends OneShotEffect<SurgicalExtractionEffect> {

    public SurgicalExtractionEffect() {
        super(Outcome.Exile);
        this.staticText = "Choose target card in a graveyard other than a basic land card. Search its owner's graveyard, hand, and library for any number of cards with the same name as that card and exile them. Then that player shuffles his or her library";
    }

    public SurgicalExtractionEffect(final SurgicalExtractionEffect effect) {
        super(effect);
    }

    @Override
    public SurgicalExtractionEffect copy() {
        return new SurgicalExtractionEffect(this);
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
