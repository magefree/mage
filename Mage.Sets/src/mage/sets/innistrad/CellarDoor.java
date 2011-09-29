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
package mage.sets.innistrad;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author North
 */
public class CellarDoor extends CardImpl<CellarDoor> {

    public CellarDoor(UUID ownerId) {
        super(ownerId, 218, "Cellar Door", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT}, "{2}");
        this.expansionSetCode = "ISD";

        // {3}, {tap}: Target player puts the bottom card of his or her library into his or her graveyard. If it's a creature card, you put a 2/2 black Zombie creature token onto the battlefield.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new CellarDoorEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    public CellarDoor(final CellarDoor card) {
        super(card);
    }

    @Override
    public CellarDoor copy() {
        return new CellarDoor(this);
    }
}

class CellarDoorEffect extends OneShotEffect<CellarDoorEffect> {

    public CellarDoorEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Target player puts the bottom card of his or her library into his or her graveyard. If it's a creature card, you put a 2/2 black Zombie creature token onto the battlefield";
    }

    public CellarDoorEffect(final CellarDoorEffect effect) {
        super(effect);
    }

    @Override
    public CellarDoorEffect copy() {
        return new CellarDoorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null && player.getLibrary().size() > 0) {
            Card card = player.getLibrary().removeFromBottom(game);
            if (card != null) {
                card.moveToZone(Zone.GRAVEYARD, source.getId(), game, true);
                if (card.getCardType().contains(CardType.CREATURE)) {
                    ZombieToken token = new ZombieToken();
                    token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId());
                }
            }
            return true;
        }
        return false;
    }
}
