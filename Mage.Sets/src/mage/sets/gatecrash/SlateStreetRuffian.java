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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesBlockedByCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SlateStreetRuffian extends CardImpl<SlateStreetRuffian> {

    public SlateStreetRuffian(UUID ownerId) {
        super(ownerId, 78, "Slate Street Ruffian", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{B}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Human");
        this.subtype.add("Warrior");

        this.color.setBlack(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);


        // Whenever Slate Street Ruffian becomes blocked, defending player discards a card.
        this.addAbility(new BecomesBlockedByCreatureTriggeredAbility(new SlateStreetRuffianDiscardEffect(), false));
    }

    public SlateStreetRuffian(final SlateStreetRuffian card) {
        super(card);
    }

    @Override
    public SlateStreetRuffian copy() {
        return new SlateStreetRuffian(this);
    }
}

class SlateStreetRuffianDiscardEffect extends OneShotEffect<SlateStreetRuffianDiscardEffect> {

    public SlateStreetRuffianDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "defending player discards a card";
    }

    public SlateStreetRuffianDiscardEffect(final SlateStreetRuffianDiscardEffect effect) {
        super(effect);
    }

    @Override
    public SlateStreetRuffianDiscardEffect copy() {
        return new SlateStreetRuffianDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent blockingCreature = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (blockingCreature != null) {
            Player opponent = game.getPlayer(blockingCreature.getControllerId());
            if (opponent != null) {
                opponent.discard(1, source, game);
                return true;
            }
        }
        return false;
    }
}
