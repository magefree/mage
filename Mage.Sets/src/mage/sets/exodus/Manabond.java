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
package mage.sets.exodus;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class Manabond extends CardImpl {

    public Manabond(UUID ownerId) {
        super(ownerId, 113, "Manabond", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{G}");
        this.expansionSetCode = "EXO";

        // At the beginning of your end step, reveal your hand and put all land cards from it onto the battlefield. If you do, discard your hand.
        this.addAbility(new BeginningOfYourEndStepTriggeredAbility(new ManabondEffect(), true));
    }

    public Manabond(final Manabond card) {
        super(card);
    }

    @Override
    public Manabond copy() {
        return new Manabond(this);
    }
}

class ManabondEffect extends OneShotEffect {

    public ManabondEffect() {
        super(Outcome.PutCardInPlay);
        staticText = "you may reveal your hand and put all land cards from it onto the battlefield. If you do, discard your hand";
    }

    public ManabondEffect(final ManabondEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && sourceObject != null) {
            controller.revealCards(sourceObject.getIdName(), controller.getHand(), game);
            Set<Card> toBattlefield = new LinkedHashSet<>();
            for (UUID uuid : controller.getHand()) {
                Card card = game.getCard(uuid);
                if (card != null && card.getCardType().contains(CardType.LAND)) {
                    toBattlefield.add(card);
                }

            }
            controller.moveCards(toBattlefield, Zone.BATTLEFIELD, source, game, false, false, true, null);
            controller.discard(controller.getHand().size(), false, source, game);
            return true;
        }
        return false;
    }

    @Override
    public ManabondEffect copy() {
        return new ManabondEffect(this);
    }
}
