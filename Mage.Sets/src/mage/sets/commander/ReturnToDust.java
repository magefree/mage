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
import mage.abilities.SpellAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.target.TargetPermanent;

/**
 *
 * @author emerald000
 */
public class ReturnToDust extends CardImpl {
    
    private static final FilterPermanent filter = new FilterPermanent("artifact or enchantment");
    static {
        filter.add(Predicates.or(new CardTypePredicate(CardType.ARTIFACT),
                                 new CardTypePredicate(CardType.ENCHANTMENT)));
    }

    public ReturnToDust(UUID ownerId) {
        super(ownerId, 28, "Return to Dust", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{W}{W}");
        this.expansionSetCode = "CMD";


        // Exile target artifact or enchantment. If you cast this spell during your main phase, you may exile up to one other target artifact or enchantment.
        Effect effect = new ExileTargetEffect();
        effect.setText("Exile target artifact or enchantment. If you cast this spell during your main phase, you may exile up to one other target artifact or enchantment");
        this.getSpellAbility().addEffect(effect);
    }

    public ReturnToDust(final ReturnToDust card) {
        super(card);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (ability instanceof SpellAbility) {
            if (game.getActivePlayerId().equals(ability.getControllerId()) && game.isMainPhase()) {
                ability.addTarget(new TargetPermanent(1, 2, filter, false));
            }
            else {
                ability.addTarget(new TargetPermanent(filter));
            }
        }
    }

    @Override
    public ReturnToDust copy() {
        return new ReturnToDust(this);
    }
}
