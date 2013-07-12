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
package mage.sets.commander;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterStackObject;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetStackObject;

/**
 *
 * @author jeffwadsworth
 */
public class WildRicochet extends CardImpl<WildRicochet> {

    private static final FilterStackObject filter = new FilterStackObject("instant or sorcery spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.INSTANT),
                new CardTypePredicate(CardType.SORCERY)));
    }

    public WildRicochet(UUID ownerId) {
        super(ownerId, 139, "Wild Ricochet", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{2}{R}{R}");
        this.expansionSetCode = "CMD";

        this.color.setRed(true);

        // You may choose new targets for target instant or sorcery spell. Then copy that spell. You may choose new targets for the copy.
        this.getSpellAbility().addEffect(new WildRicochetEffect());
        this.getSpellAbility().addTarget(new TargetStackObject(filter));

    }

    public WildRicochet(final WildRicochet card) {
        super(card);
    }

    @Override
    public WildRicochet copy() {
        return new WildRicochet(this);
    }
}

class WildRicochetEffect extends OneShotEffect<WildRicochetEffect> {

    public WildRicochetEffect() {
        super(Outcome.Neutral);
        staticText = "You may choose new targets for target instant or sorcery spell. Then copy that spell. You may choose new targets for the copy";
    }

    public WildRicochetEffect(final WildRicochetEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Spell spell = game.getStack().getSpell(source.getFirstTarget());
        Player you = game.getPlayer(source.getControllerId());
        if (spell != null && you != null && you.chooseUse(Outcome.Benefit, "Do you wish to choose new targets for " + spell.getName() + "?", game)) {
            spell.chooseNewTargets(game, you.getId());
        }
        if (spell != null) {
            Spell copy = spell.copySpell();
            copy.setControllerId(source.getControllerId());
            copy.setCopiedSpell(true);
            game.getStack().push(copy);
            if (you != null && you.chooseUse(Outcome.Benefit, "Do you wish to choose new targets for the copied " + spell.getName() + "?", game)) {
                return copy.chooseNewTargets(game, you.getId());
            }
        }
        return false;
    }

    @Override
    public WildRicochetEffect copy() {
        return new WildRicochetEffect(this);
    }
}
