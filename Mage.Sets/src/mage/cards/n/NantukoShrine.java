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
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.game.permanent.token.SquirrelToken;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class NantukoShrine extends CardImpl {

    public NantukoShrine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{G}");

        // Whenever a player casts a spell, that player puts X 1/1 green Squirrel creature tokens onto the battlefield, where X is the number of cards in all graveyards with the same name as that spell.
        this.addAbility(new SpellCastAllTriggeredAbility(new NantukoShrineEffect(), StaticFilters.FILTER_SPELL, false, SetTargetPointer.SPELL));
    }

    public NantukoShrine(final NantukoShrine card) {
        super(card);
    }

    @Override
    public NantukoShrine copy() {
        return new NantukoShrine(this);
    }
}

class NantukoShrineEffect extends OneShotEffect {

    public NantukoShrineEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "that player puts X 1/1 green Squirrel creature tokens onto the battlefield, where X is the number of cards in all graveyards with the same name as that spell";
    }

    public NantukoShrineEffect(final NantukoShrineEffect effect) {
        super(effect);
    }

    @Override
    public NantukoShrineEffect copy() {
        return new NantukoShrineEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(getTargetPointer().getFirst(game, source));
        if (spell != null) {
            Player controller = game.getPlayer(spell.getControllerId());
            if (controller != null) {
                int count = 0;
                String name = spell.getName();
                FilterCard filterCardName = new FilterCard();
                filterCardName.add(new NamePredicate(name));
                for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        count += player.getGraveyard().count(filterCardName, game);
                    }
                }
                if (count > 0) {
                    new SquirrelToken().putOntoBattlefield(count, game, source.getSourceId(), spell.getControllerId());
                }
                return true;
            }
        }
        return false;
    }
}
