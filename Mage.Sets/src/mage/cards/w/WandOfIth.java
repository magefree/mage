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
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author fireshoes & L_J
 */
public class WandOfIth extends CardImpl {

    public WandOfIth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{4}");

        // {3}, {T}: Target player reveals a card at random from their hand. If it's a land card, that player discards it unless he or she pays 1 life. If it isn't a land card, the player discards it unless he or she pays life equal to its converted mana cost. Activate this ability only during your turn.
        Ability ability = new ActivateIfConditionActivatedAbility(Zone.BATTLEFIELD, new WandOfIthEffect(), new GenericManaCost(3), MyTurnCondition.instance);
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public WandOfIth(final WandOfIth card) {
        super(card);
    }

    @Override
    public WandOfIth copy() {
        return new WandOfIth(this);
    }
}

class WandOfIthEffect extends OneShotEffect {

    public WandOfIthEffect() {
        super(Outcome.Discard);
        staticText = "Target player reveals a card at random from their hand. If it's a land card, that player discards it unless he or she pays 1 life. If it isn't a land card, the player discards it unless he or she pays life equal to its converted mana cost";
    }

    public WandOfIthEffect(final WandOfIthEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(targetPointer.getFirst(game, source));
        Permanent sourcePermanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (player != null && !player.getHand().isEmpty()) {
            Cards revealed = new CardsImpl();
            Card card = player.getHand().getRandom(game);
            if (card != null) {
                revealed.add(card);
                player.revealCards(sourcePermanent.getName(), revealed, game);
                int lifeToPay = card.isLand() ? 1 : card.getConvertedManaCost();
                PayLifeCost cost = new PayLifeCost(lifeToPay);
                if (cost.canPay(source, source.getSourceId(), player.getId(), game)
                        && player.chooseUse(outcome, "Pay " + lifeToPay + " life to prevent discarding " + card.getLogName() + "?", source, game)
                        && cost.pay(source, game, source.getSourceId(), player.getId(), false, null)) {
                    game.informPlayers(player.getLogName() + " has paid " + lifeToPay + " life to prevent discarding " + card.getLogName() + " (" + sourcePermanent.getLogName() + ')');
                } else {
                    player.discard(card, source, game);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public WandOfIthEffect copy() {
        return new WandOfIthEffect(this);
    }

}
