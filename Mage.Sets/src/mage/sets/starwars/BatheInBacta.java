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
package mage.sets.starwars;

import java.util.UUID;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.HateCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.watchers.common.LifeLossOtherFromCombatWatcher;

/**
 *
 * @author Styxo
 */
public class BatheInBacta extends CardImpl {

    public BatheInBacta(UUID ownerId) {
        super(ownerId, 129, "Bathe in Bacta", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{G}");
        this.expansionSetCode = "SWS";

        // You gain 6 life.        
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(6),
                new InvertCondition(HateCondition.getInstance()),
                "You gain 6 life"));

        // If you lost life from a source other than combat damage this turn, you gain 9 life instead.
        this.getSpellAbility().addEffect(new ConditionalOneShotEffect(
                new GainLifeEffect(9),
                HateCondition.getInstance(),
                "If you lost life from a source other than combat damage this turn, you gain 9 life instead"));

        this.getSpellAbility().addWatcher(new LifeLossOtherFromCombatWatcher());
    }

    public BatheInBacta(final BatheInBacta card) {
        super(card);
    }

    @Override
    public BatheInBacta copy() {
        return new BatheInBacta(this);
    }
}
