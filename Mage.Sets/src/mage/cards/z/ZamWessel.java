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
package mage.cards.z;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CopyEffect;
import mage.abilities.effects.common.RevealHandTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Styxo
 */
public class ZamWessel extends CardImpl {

    public ZamWessel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}{U}");
        this.supertype.add("Legendary");
        this.subtype.add("Shapeshifter");
        this.subtype.add("Hunter");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When you cast Zam Wessel, target opponent reveals his or her hand. You may choose a creature card from it and have Zam Wessel enter the battlefield as a copy of that creature card.
        Ability ability = new CastSourceTriggeredAbility(new RevealHandTargetEffect());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    public ZamWessel(final ZamWessel card) {
        super(card);
    }

    @Override
    public ZamWessel copy() {
        return new ZamWessel(this);
    }
}

class ZamWesselEffect extends OneShotEffect {

    public ZamWesselEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may choose a creature card from it and have {this} enter the battlefield as a copy of that creature card";
    }

    public ZamWesselEffect(final ZamWesselEffect effect) {
        super(effect);
    }

    @Override
    public ZamWesselEffect copy() {
        return new ZamWesselEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (targetPlayer != null) {
                TargetCard targetCard = new TargetCard(0, 1, Zone.HAND, new FilterCreatureCard());
                controller.choose(outcome, targetPlayer.getHand(), targetCard, game);
                Card copyFromCard = game.getCard(targetCard.getFirstTarget());
                if (copyFromCard != null) {
                    game.informPlayers(controller.getLogName() + " chooses to copy " + copyFromCard.getName());
                    CopyEffect copyEffect = new CopyEffect(Duration.Custom, copyFromCard, source.getSourceId());
                    game.addEffect(copyEffect, source);
                }
            }
            return true;
        }
        return false;
    }
}
