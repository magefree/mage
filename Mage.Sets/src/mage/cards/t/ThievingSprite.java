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
package mage.cards.t;

import java.util.List;
import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class ThievingSprite extends CardImpl {

    public ThievingSprite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}");
        this.subtype.add("Faerie");
        this.subtype.add("Rogue");

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Thieving Sprite enters the battlefield, target player reveals X cards from his or her hand, where X is the number of Faeries you control.
        // You choose one of those cards. That player discards that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ThievingSpriteEffect(), false);
        TargetPlayer target = new TargetPlayer();
        ability.addTarget(target);
        this.addAbility(ability);

    }

    public ThievingSprite(final ThievingSprite card) {
        super(card);
    }

    @Override
    public ThievingSprite copy() {
        return new ThievingSprite(this);
    }
}

class ThievingSpriteEffect extends OneShotEffect {

    public ThievingSpriteEffect() {
        super(Outcome.Discard);
        this.staticText = "target player reveals X cards from his or her hand, where X is the number of Faeries you control. You choose one of those cards. "
                + "That player discards that card";
    }

    public ThievingSpriteEffect(final ThievingSpriteEffect effect) {
        super(effect);
    }

    @Override
    public ThievingSpriteEffect copy() {
        return new ThievingSpriteEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(source.getFirstTarget());
        Player controller = game.getPlayer(source.getControllerId());
        if (targetPlayer == null || controller == null) {
            return false;
        }

        FilterControlledPermanent filter = new FilterControlledPermanent();
        filter.add(new SubtypePredicate(SubType.FAERIE));
        int numberOfFaeries = game.getBattlefield().countAll(filter, controller.getId(), game);

        Cards revealedCards = new CardsImpl();
        if (numberOfFaeries > 0 && targetPlayer.getHand().size() > numberOfFaeries) {
            Cards cardsInHand = new CardsImpl();
            cardsInHand.addAll(targetPlayer.getHand());

            TargetCard target = new TargetCard(numberOfFaeries, Zone.HAND, new FilterCard());

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
            targetPlayer.revealCards("Thieving Sprite", revealedCards, game);
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
        return true;
    }
}
