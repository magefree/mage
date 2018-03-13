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
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.costs.Cost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.MayTapOrUntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class ChainStasis extends CardImpl {

    public ChainStasis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}");

        // You may tap or untap target creature. Then that creature's controller may pay {2}{U}. If the player does, he or she may copy this spell and may choose a new target for that copy.
        this.getSpellAbility().addEffect(new ChainStasisEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public ChainStasis(final ChainStasis card) {
        super(card);
    }

    @Override
    public ChainStasis copy() {
        return new ChainStasis(this);
    }
}

class ChainStasisEffect extends OneShotEffect {

    public ChainStasisEffect() {
        super(Outcome.Benefit);
    }

    public ChainStasisEffect(final ChainStasisEffect effect) {
        super(effect);
    }

    @Override
    public ChainStasisEffect copy() {
        return new ChainStasisEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (controller == null || sourceObject == null) {
            return false;
        }
        Permanent permanent = game.getPermanent(source.getFirstTarget());
        if (permanent != null) {
            Effect effect = new MayTapOrUntapTargetEffect();
            effect.setTargetPointer(new FixedTarget(source.getFirstTarget()));
            effect.apply(game, source);
            Player player = game.getPlayer(permanent.getControllerId());
            Cost cost = new ManaCostsImpl("{2}{U}");
            if (cost.pay(source, game, player.getId(), controller.getId(), false)) {
                if (player.chooseUse(outcome, "Copy the spell?", source, game)) {
                    Spell spell = game.getStack().getSpell(source.getSourceId());
                    if (spell != null) {
                        StackObject newStackObject = spell.createCopyOnStack(game, source, player.getId(), true);
                        if (newStackObject != null && newStackObject instanceof Spell) {
                            String activateMessage = ((Spell) newStackObject).getActivatedMessage(game);
                            if (activateMessage.startsWith(" casts ")) {
                                activateMessage = activateMessage.substring(6);
                            }
                            game.informPlayers(player.getLogName() + ' ' + activateMessage);
                        }
                    }
                }
            }
            return true;
        }

        return false;
    }

    @Override
    public String getText(Mode mode
    ) {
        return "You may tap or untap target creature. Then that creature's controller may pay {2}{U}. If the player does, he or she may copy this spell and may choose a new target for that copy";
    }

}
