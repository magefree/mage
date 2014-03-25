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
package mage.sets.stronghold;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author Plopman
 */
public class EnsnaringBridge extends CardImpl<EnsnaringBridge> {

    public EnsnaringBridge(UUID ownerId) {
        super(ownerId, 127, "Ensnaring Bridge", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "STH";

        // Creatures with power greater than the number of cards in your hand can't attack.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new EnsnaringBridgeRestrictionEffect()));
    }

    public EnsnaringBridge(final EnsnaringBridge card) {
        super(card);
    }

    @Override
    public EnsnaringBridge copy() {
        return new EnsnaringBridge(this);
    }
}


class EnsnaringBridgeRestrictionEffect extends RestrictionEffect<EnsnaringBridgeRestrictionEffect> {

    public EnsnaringBridgeRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        this.staticText = "Creatures with power greater than the number of cards in your hand can't attack";
    }

    public EnsnaringBridgeRestrictionEffect(final EnsnaringBridgeRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return permanent.getPower().getValue() > controller.getHand().size();
        }
        return false;
    }

    @Override
    public boolean canAttack(Game game) {
        return false;
    }

    @Override
    public EnsnaringBridgeRestrictionEffect copy() {
        return new EnsnaringBridgeRestrictionEffect(this);
    }

}
