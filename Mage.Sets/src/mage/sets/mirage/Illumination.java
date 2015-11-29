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
package mage.sets.mirage;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.TargetSpell;

/**
 *
 * @author fireshoes
 */
public class Illumination extends CardImpl {
    
    private static final FilterSpell filter = new FilterSpell("artifact or enchantment spell");

    static {
        filter.add(Predicates.or(
                new CardTypePredicate(CardType.ARTIFACT),
                new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public Illumination(UUID ownerId) {
        super(ownerId, 225, "Illumination", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{W}{W}");
        this.expansionSetCode = "MIR";

        // Counter target artifact or enchantment spell.
        this.getSpellAbility().addEffect(new CounterTargetEffect());
        this.getSpellAbility().addTarget(new TargetSpell(filter));
        
        // Its controller gains life equal to its converted mana cost.
        this.getSpellAbility().addEffect(new IlluminationEffect());
    }

    public Illumination(final Illumination card) {
        super(card);
    }

    @Override
    public Illumination copy() {
        return new Illumination(this);
    }
}

class IlluminationEffect extends OneShotEffect {

    public IlluminationEffect() {
        super(Outcome.GainLife);
        staticText = "Its controller gains life equal to its converted mana cost";
    }

    public IlluminationEffect(final IlluminationEffect effect) {
        super(effect);
    }

    @Override
    public IlluminationEffect copy() {
        return new IlluminationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID targetId = source.getFirstTarget();
        Player controller = null;
        boolean countered = false;
        if (targetId != null) {
            controller = game.getPlayer(game.getControllerId(targetId));
        }
        if (targetId != null
                && game.getStack().counter(targetId, source.getSourceId(), game)) {
            countered = true;
        }
        if (controller != null) {
            Spell spell = game.getStack().getSpell(targetPointer.getFirst(game, source));
            int cost = spell.getManaCost().convertedManaCost();
            Player player = game.getPlayer(spell.getControllerId());
            if (player != null) {
                player.gainLife(cost, game);
            }
            return true;
        }
        return countered;
    }
}
