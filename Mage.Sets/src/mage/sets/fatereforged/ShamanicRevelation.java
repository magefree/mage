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
package mage.sets.fatereforged;

import java.util.UUID;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.Filter;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;

/**
 *
 * @author LevelX2
 */
public class ShamanicRevelation extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("creature you control with power 4 or greater");

    static {
        filter.add(new PowerPredicate(Filter.ComparisonType.GreaterThan, 3));
    }

    public ShamanicRevelation(UUID ownerId) {
        super(ownerId, 138, "Shamanic Revelation", Rarity.RARE, new CardType[]{CardType.SORCERY}, "{3}{G}{G}");
        this.expansionSetCode = "FRF";

        // Draw a card for each creature you control.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent())));

        // <i>Ferocious</i> - You gain 4 life for each creature you control with power 4 or greater.
        DynamicValue amount = new PermanentsOnBattlefieldCount(filter, 4);
        Effect effect = new GainLifeEffect(amount);
        effect.setText("<br><i>Ferocious</i> &mdash; You gain 4 life for each creature you control with power 4 or greater.");
        this.getSpellAbility().addEffect(effect);

    }

    public ShamanicRevelation(final ShamanicRevelation card) {
        super(card);
    }

    @Override
    public ShamanicRevelation copy() {
        return new ShamanicRevelation(this);
    }
}
