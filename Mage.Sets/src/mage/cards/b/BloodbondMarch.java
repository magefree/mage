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
package mage.cards.b;

import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author JRHerlehy
 */
public class BloodbondMarch extends CardImpl {

    public BloodbondMarch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{G}");
        // Whenever a player casts a creature spell, each player returns all cards with the same name as that spell from his or her graveyard to the battlefield.
        this.addAbility(new SpellCastAllTriggeredAbility(new BloodbondMarchEffect(), new FilterCreatureSpell(), false, SetTargetPointer.SPELL));
    }

    public BloodbondMarch(final BloodbondMarch card) {
        super(card);
    }

    @Override
    public BloodbondMarch copy() {
        return new BloodbondMarch(this);
    }

    private class BloodbondMarchEffect extends OneShotEffect {

        public BloodbondMarchEffect() {
            super(Outcome.Benefit);
            staticText = "each player returns all cards with the same name as that spell from his or her graveyard to the battlefield";
        }

        public BloodbondMarchEffect(BloodbondMarchEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());

            if (controller == null || game.getPermanentOrLKIBattlefield(source.getSourceId()) == null) {
                return false;
            }

            Spell spell = (Spell) game.getStack().getStackObject(targetPointer.getFirst(game, source));

            if (spell == null) {
                return false;
            }

            FilterCard filter = new FilterCard();
            filter.add(new NamePredicate(spell.getName()));

            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.moveCards(player.getGraveyard().getCards(filter, game), Zone.BATTLEFIELD, source, game);
                }
            }

            return true;
        }

        @Override
        public Effect copy() {
            return new BloodbondMarchEffect(this);
        }
    }
}
