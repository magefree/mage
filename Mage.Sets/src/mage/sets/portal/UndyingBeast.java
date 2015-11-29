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
package mage.sets.portal;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
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
 * @author fireshoes
 */
public class UndyingBeast extends CardImpl {

    public UndyingBeast(UUID ownerId) {
        super(ownerId, 36, "Undying Beast", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "POR";
        this.subtype.add("Beast");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // When Undying Beast dies, put it on top of its owner's library.
        this.addAbility(new DiesTriggeredAbility(new UndyingBeastEffect()));
    }

    public UndyingBeast(final UndyingBeast card) {
        super(card);
    }

    @Override
    public UndyingBeast copy() {
        return new UndyingBeast(this);
    }
}

class UndyingBeastEffect extends OneShotEffect {

    public UndyingBeastEffect() {
        super(Outcome.ReturnToHand);
        staticText = "put it on top of its owner's library";
    }

    public UndyingBeastEffect(final UndyingBeastEffect effect) {
        super(effect);
    }

    @Override
    public UndyingBeastEffect copy() {
        return new UndyingBeastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Card card = game.getCard(source.getSourceId());
        if (card != null && game.getState().getZone(source.getSourceId()) == Zone.GRAVEYARD) {
            Player owner = game.getPlayer(card.getOwnerId());
            owner.getGraveyard().remove(card);
            return card.moveToZone(Zone.LIBRARY, source.getSourceId(), game, true);
        }
        return true;
    }
}
