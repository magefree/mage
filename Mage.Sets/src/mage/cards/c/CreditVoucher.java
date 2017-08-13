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
package mage.cards.c;

import java.util.Iterator;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author TheElk801
 */
public class CreditVoucher extends CardImpl {

    public CreditVoucher(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {2}, {tap}, Sacrifice Credit Voucher: Shuffle any number of cards from your hand into your library, then draw that many cards.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CreditVoucherEffect(), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    public CreditVoucher(final CreditVoucher card) {
        super(card);
    }

    @Override
    public CreditVoucher copy() {
        return new CreditVoucher(this);
    }
}

class CreditVoucherEffect extends OneShotEffect {

    CreditVoucherEffect() {
        super(Outcome.Neutral);
        this.staticText = "Shuffle any number of from your hand into your library, then draw that many cards";
    }

    CreditVoucherEffect(final CreditVoucherEffect effect) {
        super(effect);
    }

    @Override
    public CreditVoucherEffect copy() {
        return new CreditVoucherEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            FilterCard filter = new FilterCard("card in your hand to shuffle away");
            TargetCardInHand target = new TargetCardInHand(0, controller.getHand().size(), filter);
            target.setRequired(false);
            int amountShuffled = 0;
            if (target.canChoose(source.getControllerId(), game) && target.choose(Outcome.Neutral, source.getControllerId(), source.getSourceId(), game)) {
                if (!target.getTargets().isEmpty()) {
                    Iterator<UUID> targets = target.getTargets().iterator();
                    while (targets.hasNext()) {
                        Card card = game.getCard(targets.next());
                        if (card != null) {
                            controller.moveCards(card, Zone.LIBRARY, source, game);
                            amountShuffled++;
                        }
                    }
                }
            }
            controller.shuffleLibrary(source, game);
            if (amountShuffled > 0) {
                controller.drawCards(amountShuffled, game);
            }
            return true;
        }
        return false;
    }
}
