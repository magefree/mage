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
package mage.sets.coldsnap;

import java.util.UUID;

import mage.ObjectColor;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.costs.common.ExileFromHandCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author dustinconrad
 */
public class SoulSpike extends CardImpl {

    private static final FilterCard filter = new FilterCard("two black cards");

    static {
        filter.add(new ColorPredicate(ObjectColor.BLACK));
    }

    public SoulSpike(UUID ownerId) {
        super(ownerId, 70, "Soul Spike", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{5}{B}{B}");
        this.expansionSetCode = "CSP";


        // You may exile two black cards from your hand rather than pay Soul Spike's mana cost.
        this.addAbility(new AlternativeCostSourceAbility(new ExileFromHandCost(new TargetCardInHand(2, filter))));
        // Soul Spike deals 4 damage to target creature or player and you gain 4 life.
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
        this.getSpellAbility().addEffect(new DamageTargetEffect(4));
        this.getSpellAbility().addEffect(new GainLifeEffect(4));
    }

    public SoulSpike(final SoulSpike card) {
        super(card);
    }

    @Override
    public SoulSpike copy() {
        return new SoulSpike(this);
    }
}
