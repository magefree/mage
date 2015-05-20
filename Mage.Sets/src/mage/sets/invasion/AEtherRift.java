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
package mage.sets.invasion;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoUnlessAnyPlayerPaysEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author LevelX2
 */
public class AEtherRift extends CardImpl {

    public AEtherRift(UUID ownerId) {
        super(ownerId, 227, "AEther Rift", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{R}{G}");
        this.expansionSetCode = "INV";


        // At the beginning of your upkeep, discard a card at random. If you discard a creature card this way, return it from your graveyard to the battlefield unless any player pays 5 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AEtherRiftEffect(), TargetController.YOU, false));

    }

    public AEtherRift(final AEtherRift card) {
        super(card);
    }

    @Override
    public AEtherRift copy() {
        return new AEtherRift(this);
    }
}

class AEtherRiftEffect extends OneShotEffect {

    public AEtherRiftEffect() {
        super(Outcome.Benefit);
        this.staticText = "discard a card at random. If you discard a creature card this way, return it from your graveyard to the battlefield unless any player pays 5 life";
    }

    public AEtherRiftEffect(final AEtherRiftEffect effect) {
        super(effect);
    }

    @Override
    public AEtherRiftEffect copy() {
        return new AEtherRiftEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Card card = controller.discardOne(true, source, game);
            if (card != null && card.getCardType().contains(CardType.CREATURE)) {
                Effect returnEffect = new ReturnFromGraveyardToBattlefieldTargetEffect();
                returnEffect.setTargetPointer(new FixedTarget(card.getId()));
                Effect doEffect = new DoUnlessAnyPlayerPaysEffect(returnEffect, new PayLifeCost(5),
                        "Pay 5 life to prevent " + card.getLogName() + " to return from graveyard to battlefield?");
                return doEffect.apply(game, source);
            }
            return true;
        }
        return false;
    }
}
