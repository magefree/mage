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
package mage.sets.worldwake;

import java.util.List;
import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author jeffwadsworth
 */
public class MiresToll extends CardImpl<MiresToll> {

    public MiresToll(UUID ownerId) {
        super(ownerId, 60, "Mire's Toll", Rarity.COMMON, new CardType[]{CardType.SORCERY}, "{B}");
        this.expansionSetCode = "WWK";

        this.color.setBlack(true);

        // Target player reveals a number of cards from his or her hand equal to the number of Swamps you control. You choose one of them. That player discards that card.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new MiresTollEffect());

    }

    public MiresToll(final MiresToll card) {
        super(card);
    }

    @Override
    public MiresToll copy() {
        return new MiresToll(this);
    }
}

class MiresTollEffect extends OneShotEffect<MiresTollEffect> {

    private static final FilterLandPermanent filter = new FilterLandPermanent("Swamps");

    static {
        filter.add(new SubtypePredicate("Swamp"));
        filter.add(new ControllerPredicate(Constants.TargetController.YOU));
    }

    public MiresTollEffect() {
        super(Constants.Outcome.Discard);
        staticText = "Target player reveals a number of cards from his or her hand equal to the number of Swamps you control. You choose one of them. That player discards that card";
    }

    public MiresTollEffect(final MiresTollEffect effect) {
        super(effect);
    }

    @Override
    public MiresTollEffect copy() {
        return new MiresTollEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int swamps = game.getBattlefield().count(filter, source.getSourceId(), source.getControllerId(), game);
        Player targetPlayer = game.getPlayer(targetPointer.getFirst(game, source));
        if (swamps > 0 && targetPlayer != null) {
            Cards revealedCards = new CardsImpl(Constants.Zone.PICK);
            int amount = Math.min(targetPlayer.getHand().size(), swamps);
            FilterCard filter = new FilterCard("card in target player's hand");
            TargetCardInHand chosenCards = new TargetCardInHand(amount, amount, filter);
            chosenCards.setRequired(true);
            chosenCards.setNotTarget(true);
            if (chosenCards.canChoose(targetPlayer.getId(), game) && targetPlayer.choose(Constants.Outcome.Discard, targetPlayer.getHand(), chosenCards, game)) {
                if (!chosenCards.getTargets().isEmpty()) {
                    List<UUID> targets = chosenCards.getTargets();
                    for (UUID targetid : targets) {
                        Card card = game.getCard(targetid);
                        if (card != null) {
                            revealedCards.add(card);
                        }
                    }
                }
            }

            targetPlayer.revealCards("Mire's Toll", revealedCards, game);

            Player you = game.getPlayer(source.getControllerId());

            if (you != null) {
                TargetCard yourChoice = new TargetCard(Constants.Zone.PICK, new FilterCard());
                yourChoice.setRequired(true);
                yourChoice.setNotTarget(true);
                if (you.choose(Constants.Outcome.Benefit, revealedCards, yourChoice, game)) {
                    Card card = targetPlayer.getHand().get(yourChoice.getFirstTarget(), game);
                    if (card != null) {
                        return targetPlayer.discard(card, source, game);
                    }
                }
            }
        }

        return false;
    }
}
