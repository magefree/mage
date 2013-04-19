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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
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
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new EnsnaringBridgeEffect()));
    }

    public EnsnaringBridge(final EnsnaringBridge card) {
        super(card);
    }

    @Override
    public EnsnaringBridge copy() {
        return new EnsnaringBridge(this);
    }
}

class EnsnaringBridgeEffect extends ReplacementEffectImpl<EnsnaringBridgeEffect> {


    public EnsnaringBridgeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "Creatures with power greater than the number of cards in your hand can't attack";
    }

    public EnsnaringBridgeEffect(final EnsnaringBridgeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public EnsnaringBridgeEffect copy() {
        return new EnsnaringBridgeEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == EventType.DECLARE_ATTACKER) {
            Permanent permanent = game.getPermanent(event.getSourceId());
            if (permanent != null) {
                Player player = game.getPlayer(source.getControllerId());
                if (player != null && player.getInRange().contains(permanent.getControllerId())) {
                    Player controller = game.getPlayer(source.getControllerId());
                    int cardInHand = controller.getHand().size();
                    if (permanent.getPower().getValue() > cardInHand){
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
