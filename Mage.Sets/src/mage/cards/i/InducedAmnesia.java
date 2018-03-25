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
package mage.cards.i;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class InducedAmnesia extends CardImpl {

    public InducedAmnesia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // When Induced Amnesia enters the battlefield, target player exiles all the cards in their hand face down, then draws that many cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new InducedAmnesiaExileEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // When Induced Amnesia is put into a graveyard from the battlefield, return the exiled cards to their owner's hand.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new InducedAmnesiaReturnEffect()));
    }

    public InducedAmnesia(final InducedAmnesia card) {
        super(card);
    }

    @Override
    public InducedAmnesia copy() {
        return new InducedAmnesia(this);
    }
}

class InducedAmnesiaExileEffect extends OneShotEffect {

    public InducedAmnesiaExileEffect() {
        super(Outcome.Detriment);
        this.staticText = "target player exiles all the cards in their hand face down, then draws that many cards";
    }

    public InducedAmnesiaExileEffect(final InducedAmnesiaExileEffect effect) {
        super(effect);
    }

    @Override
    public InducedAmnesiaExileEffect copy() {
        return new InducedAmnesiaExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (targetPlayer != null && sourcePermanent != null) {
            int numberOfCards = targetPlayer.getHand().size();
            if (numberOfCards > 0) {
                UUID exileId = CardUtil.getCardExileZoneId(game, source);
                for (Card card : targetPlayer.getHand().getCards(game)) {
                    card.moveToExile(exileId, sourcePermanent.getName(), source.getSourceId(), game);
                    card.setFaceDown(true, game);
                }
                game.informPlayers(sourcePermanent.getLogName() + ": " + targetPlayer.getLogName() + " exiles their hand face down (" + numberOfCards + "card" + (numberOfCards > 1 ? "s" : "") + ')');
                game.applyEffects();
                targetPlayer.drawCards(numberOfCards, game);
            }
            return true;
        }
        return false;
    }
}

class InducedAmnesiaReturnEffect extends OneShotEffect {

    public InducedAmnesiaReturnEffect() {
        super(Outcome.Benefit);
        this.staticText = "return the exiled cards to their owner's hand";
    }

    public InducedAmnesiaReturnEffect(final InducedAmnesiaReturnEffect effect) {
        super(effect);
    }

    @Override
    public InducedAmnesiaReturnEffect copy() {
        return new InducedAmnesiaReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (controller != null && sourcePermanent != null) {
            UUID exileId = CardUtil.getCardExileZoneId(game, source.getSourceId(), true);
            int numberOfCards = 0;
            ExileZone exileZone = game.getExile().getExileZone(exileId);
            if (exileZone != null) {
                for (Card card : exileZone.getCards(game)) {
                    numberOfCards++;
                    card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
                    card.setFaceDown(false, game);
                }
            }
            if (numberOfCards > 0) {
                game.informPlayers(sourcePermanent.getLogName() + ": " + controller.getLogName() + " returns " + numberOfCards + " card" + (numberOfCards > 1 ? "s" : "") + " from exile to hand");
            }
            return true;
        }
        return false;
    }
}
