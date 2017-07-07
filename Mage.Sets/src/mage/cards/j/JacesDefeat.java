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
package mage.cards.j;

import java.util.UUID;
import mage.MageObject;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public class JacesDefeat extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("blue spell");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLUE));
    }

    public JacesDefeat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}");

        // Counter target blue spell.  If it was a Jace planeswalker spell, scry 2.
        this.getSpellAbility().addEffect(new JacesDefeatEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    public JacesDefeat(final JacesDefeat card) {
        super(card);
    }

    @Override
    public JacesDefeat copy() {
        return new JacesDefeat(this);
    }
}

class JacesDefeatEffect extends OneShotEffect {

    private static final FilterSpell filter = new FilterSpell();

    static {
        filter.add(new CardTypePredicate(CardType.PLANESWALKER));
        filter.add(new SubtypePredicate(SubType.JACE));
    }

    public JacesDefeatEffect() {
        super(Outcome.Damage);
        this.staticText = "Counter target blue spell.  If it was a Jace planeswalker spell, scry 2.";
    }

    public JacesDefeatEffect(final JacesDefeatEffect effect) {
        super(effect);
    }

    @Override
    public JacesDefeatEffect copy() {
        return new JacesDefeatEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (sourceObject != null) {
            for (UUID targetId : getTargetPointer().getTargets(game, source) ) {
                Spell spell = game.getStack().getSpell(targetId);
                if (spell != null) {
                    game.getStack().counter(targetId, source.getSourceId(), game);
                    Player controller = game.getPlayer(source.getControllerId());
                    // If it was a Jace planeswalker, you may discard a card. If you do, draw a card
                    if (filter.match(spell, game) && controller != null) {
                        controller.scry(2, source, game);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
