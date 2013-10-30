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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class HeartlessHidetsugu extends CardImpl<HeartlessHidetsugu> {

    public HeartlessHidetsugu(UUID ownerId) {
        super(ownerId, 107, "Heartless Hidetsugu", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");
        this.expansionSetCode = "BOK";
        this.supertype.add("Legendary");
        this.subtype.add("Ogre");
        this.subtype.add("Shaman");

        this.color.setRed(true);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // {tap}: Heartless Hidetsugu deals damage to each player equal to half that player's life total, rounded down.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new HeartlessHidetsuguDamageEffect(), new TapSourceCost()));

    }

    public HeartlessHidetsugu(final HeartlessHidetsugu card) {
        super(card);
    }

    @Override
    public HeartlessHidetsugu copy() {
        return new HeartlessHidetsugu(this);
    }
}

class HeartlessHidetsuguDamageEffect extends OneShotEffect<HeartlessHidetsuguDamageEffect> {

    public HeartlessHidetsuguDamageEffect() {
        super(Outcome.Detriment);
        this.staticText = "{this} deals damage to each player equal to half that player's life total, rounded down";
    }

    public HeartlessHidetsuguDamageEffect(final HeartlessHidetsuguDamageEffect effect) {
        super(effect);
    }

    @Override
    public HeartlessHidetsuguDamageEffect copy() {
        return new HeartlessHidetsuguDamageEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : controller.getInRange()) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    int damage = player.getLife() / 2;
                    player.damage(damage, source.getSourceId(), game, false, true);
                }
            }
            return true;
        }
        return false;
    }
}
