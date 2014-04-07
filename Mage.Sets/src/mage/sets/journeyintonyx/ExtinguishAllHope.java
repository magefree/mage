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
package mage.sets.journeyintonyx;

import java.util.UUID;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.CardTypePredicate;

/**
 *
 * @author LevelX2
 */
public class ExtinguishAllHope extends CardImpl<ExtinguishAllHope> {

    private static final FilterPermanent filter = new FilterPermanent("nonenchantment creatures");
    
    static {
        filter.add(new CardTypePredicate(CardType.CREATURE));
        filter.add(Predicates.not(new CardTypePredicate(CardType.ENCHANTMENT)));
    }
    
    
    public ExtinguishAllHope(UUID ownerId) {
        //TODO: Fix card number
        super(ownerId, 999, "Extinguish All Hope", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{4}{B}{B}");
        this.expansionSetCode = "JOU";

        this.color.setBlack(true);

        // Destroy all nonenchantment creatures.
        this.getSpellAbility().addEffect(new DestroyAllEffect(filter));
        
    }

    public ExtinguishAllHope(final ExtinguishAllHope card) {
        super(card);
    }

    @Override
    public ExtinguishAllHope copy() {
        return new ExtinguishAllHope(this);
    }
}
