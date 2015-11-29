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
package mage.sets.planeshift;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalReplacementEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageToTargetMultiAmountEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreatureOrPlayerAmount;

/**
 *
 * @author LoneFox

 */
public class PollenRemedy extends CardImpl {

    private final UUID originalId;

    public PollenRemedy(UUID ownerId) {
        super(ownerId, 13, "Pollen Remedy", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "PLS";

        // Kicker-Sacrifice a land.
        this.addAbility(new KickerAbility(new SacrificeTargetCost(new TargetControlledPermanent(1, 1, new FilterControlledLandPermanent("a land"), true))));
        // Prevent the next 3 damage that would be dealt this turn to any number of target creatures and/or players, divided as you choose. If Pollen Remedy was kicked, prevent the next 6 damage this way instead.
        Effect effect = new ConditionalReplacementEffect(new PreventDamageToTargetMultiAmountEffect(Duration.EndOfTurn, 6),
            KickedCondition.getInstance(), new PreventDamageToTargetMultiAmountEffect(Duration.EndOfTurn, 3));
        effect.setText("Prevent the next 3 damage that would be dealt this turn to any number of target creatures and/or players, divided as you choose. If {this} was kicked, prevent the next 6 damage this way instead.");
        this.getSpellAbility().addEffect(effect);
        originalId = this.getSpellAbility().getOriginalId();
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if(ability.getOriginalId().equals(originalId)) {
             ability.addTarget(new TargetCreatureOrPlayerAmount(KickedCondition.getInstance().apply(game, ability) ? 6 : 3));
        }
    }

    public PollenRemedy(final PollenRemedy card) {
        super(card);
        this.originalId = card.originalId;
    }

    @Override
    public PollenRemedy copy() {
        return new PollenRemedy(this);
    }
}
