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
package mage.cards.p;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreatureSpell;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.permanent.token.ReflectionPureToken;
import mage.game.stack.Spell;
import mage.players.Player;

/**
 *
 * @author SpikesCafe-google
 */
public class PureReflection extends CardImpl {

    public PureReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{W}");
        // Whenever a player casts a creature spell, destroy all Reflections. Then that player creates an X/X white Reflection creature token, where X is the converted mana cost of that spell.
        this.addAbility(new SpellCastAllTriggeredAbility(new PureReflectionEffect(), new FilterCreatureSpell(), false, SetTargetPointer.SPELL));
    }

    public PureReflection(final PureReflection card) {
        super(card);
    }

    @Override
    public PureReflection copy() {
        return new PureReflection(this);
    }

    private class PureReflectionEffect extends OneShotEffect {

        public PureReflectionEffect() {
            super(Outcome.Benefit);
            staticText = "destroy all Reflections. Then that player creates an X/X white Reflection creature token, where X is the converted mana cost of that spell.";
        }

        public PureReflectionEffect(PureReflectionEffect effect) {
            super(effect);
        }

        @Override
        public boolean apply(Game game, Ability source) {
            Player controller = game.getPlayer(source.getControllerId());

            if (controller == null || game.getPermanentOrLKIBattlefield(source.getSourceId()) == null) {
                return false;
            }

            Spell spell = game.getSpellOrLKIStack(this.getTargetPointer().getFirst(game, source));

            if (spell == null) {
                return false;
            }

            // destroy all Reflections
            FilterPermanent filter = new FilterPermanent("Reflections");
            filter.add(new SubtypePredicate(SubType.REFLECTION));
            game.getBattlefield().getActivePermanents(filter, source.getControllerId(), source.getSourceId(), game).forEach((permanent) -> {
                permanent.destroy(source.getSourceId(), game,false);
            });
            game.applyEffects();
            
            // Then that player creates an X/X white Reflection creature token, where X is the converted mana cost of that spell.
            ReflectionPureToken token = new ReflectionPureToken(spell.getConvertedManaCost());
            token.putOntoBattlefield(1, game, source.getSourceId(), spell.getControllerId());

            return true;
        }

        @Override
        public Effect copy() {
            return new PureReflectionEffect(this);
        }
    }
}
