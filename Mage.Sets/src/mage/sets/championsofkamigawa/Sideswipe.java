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
package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChooseNewTargetsTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.TargetSpell;

/**
 *
 * @author LevelX2
 */
public class Sideswipe extends CardImpl<Sideswipe> {

    private static final FilterSpell filter = new FilterSpell("Arcane spell");
    static {
        filter.add(new SubtypePredicate("Arcane"));
    }

    public Sideswipe(UUID ownerId) {
        super(ownerId, 187, "Sideswipe", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{1}{R}");
        this.expansionSetCode = "CHK";

        this.color.setRed(true);

        // You may change any targets of target Arcane spell.
        Effect effect = new ChooseNewTargetsTargetEffect(false, false);
        effect.setText("You may change any targets of target Arcane spell");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetSpell(filter));
    }

    public Sideswipe(final Sideswipe card) {
        super(card);
    }

    @Override
    public Sideswipe copy() {
        return new Sideswipe(this);
    }
}
