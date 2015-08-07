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
package mage.sets.onslaught;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetNonlandPermanent;

/**
 *
 * @author Plopman
 */
public class ChainOfVapor extends CardImpl {

    public ChainOfVapor(UUID ownerId) {
        super(ownerId, 73, "Chain of Vapor", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{U}");
        this.expansionSetCode = "ONS";

        // Return target nonland permanent to its owner's hand. Then that permanent's controller may sacrifice a land. If the player does, he or she may copy this spell and may choose a new target for that copy.
        this.getSpellAbility().addEffect(new ChainOfVaporEffect());
        this.getSpellAbility().addTarget(new TargetNonlandPermanent());
    }

    public ChainOfVapor(final ChainOfVapor card) {
        super(card);
    }

    @Override
    public ChainOfVapor copy() {
        return new ChainOfVapor(this);
    }
}

class ChainOfVaporEffect extends OneShotEffect {

    public ChainOfVaporEffect() {
        super(Outcome.ReturnToHand);
    }

    public ChainOfVaporEffect(final ChainOfVaporEffect effect) {
        super(effect);
    }

    @Override
    public ChainOfVaporEffect copy() {
        return new ChainOfVaporEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            controller.moveCards(permanent, null, Zone.HAND, source, game);
            Player player = game.getPlayer(permanent.getControllerId());
            if (player.chooseUse(Outcome.ReturnToHand, "Sacrifice a land to copy this spell?", source, game)) {
                TargetControlledPermanent target = new TargetControlledPermanent(new FilterControlledLandPermanent());
                if (player.chooseTarget(Outcome.Sacrifice, target, source, game)) {
                    Permanent land = game.getPermanent(target.getFirstTarget());
                    if (land != null) {
                        if (land.sacrifice(source.getSourceId(), game)) {
                            Spell spell = game.getStack().getSpell(source.getSourceId());
                            if (spell != null) {
                                Spell copy = spell.copySpell();
                                copy.setControllerId(player.getId());
                                copy.setCopiedSpell(true);
                                game.getStack().push(copy);
                                copy.chooseNewTargets(game, player.getId());
                                String activateMessage = copy.getActivatedMessage(game);
                                if (activateMessage.startsWith(" casts ")) {
                                    activateMessage = activateMessage.substring(6);
                                }
                                game.informPlayers(player.getLogName() + " copies " + activateMessage);
                                return true;
                            }
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public String getText(Mode mode) {
        return "Return target nonland permanent to its owner's hand. Then that permanent's controller may sacrifice a land. If the player does, he or she may copy this spell and may choose a new target for that copy";
    }

}
