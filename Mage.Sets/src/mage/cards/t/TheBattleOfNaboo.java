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
package mage.sets.starwars;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public class TheBattleOfNaboo extends CardImpl {

    public TheBattleOfNaboo(UUID ownerId) {
        super(ownerId, 35, "The Battle of Naboo", Rarity.MYTHIC, new CardType[]{CardType.SORCERY}, "{X}{X}{U}{U}");
        this.expansionSetCode = "SWS";

        // Return X target creatures to their owner's hands. Draw twice that many cards.
        Effect effect = new ReturnToHandTargetEffect();
        effect.setText("Return X target creatures to their owner's hands");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new TheBattleOfNabooEffect());

    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        ability.addTarget(new TargetCreaturePermanent(ability.getManaCostsToPay().getX()));
    }

    public TheBattleOfNaboo(final TheBattleOfNaboo card) {
        super(card);
    }

    @Override
    public TheBattleOfNaboo copy() {
        return new TheBattleOfNaboo(this);
    }
}

class TheBattleOfNabooEffect extends OneShotEffect {

    public TheBattleOfNabooEffect() {
        super(Outcome.DrawCard);
        staticText = "Draw twice that many cards";
    }

    public TheBattleOfNabooEffect(final TheBattleOfNabooEffect effect) {
        super(effect);
    }

    @Override
    public TheBattleOfNabooEffect copy() {
        return new TheBattleOfNabooEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            int x = source.getManaCostsToPay().getX();
            if (x > 0) {
                player.drawCards(2 * x, game);
            }
            return true;

        }
        return false;
    }

}
