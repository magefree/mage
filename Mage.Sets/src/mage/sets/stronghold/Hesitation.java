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
package mage.sets.stronghold;

import java.util.UUID;
import mage.abilities.common.SpellCastAllTriggeredAbility;
import mage.abilities.effects.common.CounterTargetEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.SetTargetPointer;
import mage.filter.FilterSpell;
import mage.abilities.Ability;
import mage.abilities.effects.Effect;

/**
 *
 * @author LoneFox
 */
public class Hesitation extends CardImpl {

    public Hesitation(UUID ownerId) {
        super(ownerId, 33, "Hesitation", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");
        this.expansionSetCode = "STH";

        // When a player casts a spell, sacrifice Hesitation and counter that spell.
        Ability ability = new SpellCastAllTriggeredAbility(new SacrificeSourceEffect(), new FilterSpell("a spell"), false, SetTargetPointer.SPELL);
        Effect effect = new CounterTargetEffect();
        effect.setText("and counter that spell");
        ability.addEffect(effect);
        this.addAbility(ability);
    }

    public Hesitation(final Hesitation card) {
        super(card);
    }

    @Override
    public Hesitation copy() {
        return new Hesitation(this);
    }
}
