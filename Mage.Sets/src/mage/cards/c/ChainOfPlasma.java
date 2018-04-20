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

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author TheElk801
 */
public class ChainOfPlasma extends CardImpl {

    public ChainOfPlasma(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{R}");

        // Chain of Plasma deals 3 damage to target creature or player. Then that player or that creature's controller may discard a card. If the player does, he or she may copy this spell and may choose a new target for that copy.
        this.getSpellAbility().addEffect(new ChainOfPlasmaEffect());
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
    }

    public ChainOfPlasma(final ChainOfPlasma card) {
        super(card);
    }

    @Override
    public ChainOfPlasma copy() {
        return new ChainOfPlasma(this);
    }
}

class ChainOfPlasmaEffect extends OneShotEffect {

    ChainOfPlasmaEffect() {
        super(Outcome.Damage);
        this.staticText = "{this} deals 3 damage to target creature or player. Then that player or that creature's controller may discard a card. If the player does, he or she may copy this spell and may choose a new target for that copy.";
    }

    ChainOfPlasmaEffect(final ChainOfPlasmaEffect effect) {
        super(effect);
    }

    @Override
    public ChainOfPlasmaEffect copy() {
        return new ChainOfPlasmaEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            UUID targetId = source.getFirstTarget();
            Player affectedPlayer = null;
            Player player = game.getPlayer(targetId);
            if (player != null) {
                player.damage(3, source.getSourceId(), game, false, true);
                affectedPlayer = player;
            } else {
                Permanent permanent = game.getPermanent(targetId);
                if (permanent != null) {
                    permanent.damage(3, source.getSourceId(), game, false, true);
                    affectedPlayer = game.getPlayer(permanent.getControllerId());
                }
            }
            if (affectedPlayer != null) {
                if (affectedPlayer.chooseUse(Outcome.Copy, "Discard a card to copy the spell?", source, game)) {
                    Cost cost = new DiscardCardCost();
                    if (cost.pay(source, game, source.getSourceId(), affectedPlayer.getId(), false, null)) {
                        Spell spell = game.getStack().getSpell(source.getSourceId());
                        if (spell != null) {
                            spell.createCopyOnStack(game, source, affectedPlayer.getId(), true);
                            game.informPlayers(affectedPlayer.getLogName() + " copies " + spell.getName() + '.');
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }
}
