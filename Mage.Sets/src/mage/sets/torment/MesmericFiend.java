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
package mage.sets.torment;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LeavesBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterNonlandCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */
public class MesmericFiend extends CardImpl {

    public MesmericFiend(UUID ownerId) {
        super(ownerId, 69, "Mesmeric Fiend", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.expansionSetCode = "TOR";
        this.subtype.add("Nightmare");
        this.subtype.add("Horror");

        this.color.setBlack(true);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Mesmeric Fiend enters the battlefield, target opponent reveals his or her hand and you choose a nonland card from it. Exile that card.
        Ability ability = new EntersBattlefieldTriggeredAbility(new MesmericFiendExileEffect(), false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // When Mesmeric Fiend leaves the battlefield, return the exiled card to its owner's hand.
        this.addAbility(new LeavesBattlefieldTriggeredAbility(new MesmericFiendLeaveEffect(), false ));
    }

    public MesmericFiend(final MesmericFiend card) {
        super(card);
    }

    @Override
    public MesmericFiend copy() {
        return new MesmericFiend(this);
    }
}
class MesmericFiendExileEffect extends OneShotEffect {

    public MesmericFiendExileEffect() {
        super(Outcome.Exile);
        this.staticText = "target opponent reveals his or her hand and you choose a nonland card from it. Exile that card";
    }

    public MesmericFiendExileEffect(final MesmericFiendExileEffect effect) {
        super(effect);
    }

    @Override
    public MesmericFiendExileEffect copy() {
        return new MesmericFiendExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player opponent = game.getPlayer(source.getFirstTarget());
        Permanent sourcePermanent = (Permanent) source.getSourceObject(game);
        if (controller != null && opponent != null && sourcePermanent != null) {
            opponent.revealCards(sourcePermanent.getName(), opponent.getHand(), game);

            TargetCard target = new TargetCard(Zone.HAND, new FilterNonlandCard("nonland card to exile"));
            if (controller.choose(Outcome.Exile, opponent.getHand(), target, game)) {
                Card card = opponent.getHand().get(target.getFirstTarget(), game);
                if (card != null) {
                    controller.moveCardToExileWithInfo(card, CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter()), sourcePermanent.getName(), source.getSourceId(), game, Zone.HAND);
                }
            }

            return true;
        }
        return false;
    }


}

class MesmericFiendLeaveEffect extends OneShotEffect {

    public MesmericFiendLeaveEffect() {
        super(Outcome.ReturnToHand);
        this.staticText = "return the exiled card to its owner's hand";
    }

    public MesmericFiendLeaveEffect(final MesmericFiendLeaveEffect effect) {
        super(effect);
    }

    @Override
    public MesmericFiendLeaveEffect copy() {
        return new MesmericFiendLeaveEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject !=null) {
            int zoneChangeCounter = (sourceObject instanceof PermanentToken) ? source.getSourceObjectZoneChangeCounter() : source.getSourceObjectZoneChangeCounter() -1;
            ExileZone exZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), zoneChangeCounter));
            if (exZone != null) {
                for (Card card : exZone.getCards(game)) {
                    if (card != null) {
                        controller.moveCardToHandWithInfo(card, source.getSourceId(), game, Zone.EXILED);
                    }
                }
                return true;
            }
        }
        return false;
    }
}