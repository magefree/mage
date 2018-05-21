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
package mage.cards.n;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.HellbentCondition;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.target.common.TargetOpponent;

/**
 *
 * @author TheElk801
 */
public final class NihilisticGlee extends CardImpl {

    public NihilisticGlee(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{B}{B}");

        // {2}{B}, Discard a card: Target opponent loses 1 life and you gain 1 life.
        Ability ability = new SimpleActivatedAbility(
                new LoseLifeTargetEffect(1),
                new ManaCostsImpl("{2}{B}")
        );
        ability.addEffect(new GainLifeEffect(1).setText("and you gain 1 life"));
        ability.addCost(new DiscardCardCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // Hellbent - {1}, Pay 2 life: Draw a card. Activate this ability only if you have no cards in hand.
        ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new DrawCardSourceControllerEffect(1),
                new GenericManaCost(1),
                HellbentCondition.instance
        );
        ability.addCost(new PayLifeCost(2));
        ability.setAbilityWord(AbilityWord.HELLBENT);
        this.addAbility(ability);
    }

    public NihilisticGlee(final NihilisticGlee card) {
        super(card);
    }

    @Override
    public NihilisticGlee copy() {
        return new NihilisticGlee(this);
    }
}
