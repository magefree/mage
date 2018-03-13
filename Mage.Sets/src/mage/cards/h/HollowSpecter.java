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
package mage.cards.h;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author fireshoes
 */
public class HollowSpecter extends CardImpl {

    public HollowSpecter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");

        this.subtype.add(SubType.SPECTER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Hollow Specter deals combat damage to a player, you may pay {X}.
        // If you do, that player reveals X cards from his or her hand and you choose one of them. That player discards that card.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new HollowSpecterEffect(), false, true));
    }

    public HollowSpecter(final HollowSpecter card) {
        super(card);
    }

    @Override
    public HollowSpecter copy() {
        return new HollowSpecter(this);
    }
}

class HollowSpecterEffect extends OneShotEffect {

    public HollowSpecterEffect() {
        super(Outcome.Discard);
        staticText = "you may pay {X}. If you do, that player reveals X cards from his or her hand and you choose one of them. That player discards that card";
    }

    public HollowSpecterEffect(final HollowSpecterEffect effect) {
        super(effect);
    }

    @Override
    public HollowSpecterEffect copy() {
        return new HollowSpecterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {

        Player targetPlayer = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer != null && controller != null && controller.chooseUse(Outcome.BoostCreature, "Do you want to to pay {X}?", source, game)) {
            int costX = controller.announceXMana(0, Integer.MAX_VALUE, "Announce the value for {X}", game, source);
            Cost cost = new GenericManaCost(costX);
            int amountToReveal = costX;
            Cards revealedCards = new CardsImpl();
            if (cost.pay(source, game, source.getSourceId(), source.getControllerId(), false, null)) {
                if (amountToReveal > 0 && targetPlayer.getHand().size() > amountToReveal) {
                    Cards cardsInHand = new CardsImpl();
                    cardsInHand.addAll(targetPlayer.getHand());
                    TargetCard target = new TargetCard(amountToReveal, Zone.HAND, new FilterCard());
                    if (targetPlayer.choose(Outcome.Discard, cardsInHand, target, game)) {
                        List<UUID> targets = target.getTargets();
                        for (UUID targetId : targets) {
                            Card card = game.getCard(targetId);
                            if (card != null) {
                                revealedCards.add(card);
                            }
                        }
                    }
                } else {
                    revealedCards.addAll(targetPlayer.getHand());
                }
                TargetCard targetInHand = new TargetCard(Zone.HAND, new FilterCard("card to discard"));
                if (!revealedCards.isEmpty()) {
                    targetPlayer.revealCards("Hollow Specter", revealedCards, game);
                    Card card = null;
                    if(revealedCards.size() > 1) {
                        controller.choose(Outcome.Discard, revealedCards, targetInHand, game);
                        card = revealedCards.get(targetInHand.getFirstTarget(), game);
                    } else {
                        card = revealedCards.getRandom(game);
                    }
                    if (card != null) {
                        targetPlayer.discard(card, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}