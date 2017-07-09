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
package mage.cards.l;

import java.util.UUID;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TimingRule;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreatureOrPlayer;

/**
 *
 * @author fireshoes
 */
public class LavaDart extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent("a Mountain");
    
    static {
        filter.add(new SubtypePredicate(SubType.MOUNTAIN));
    }

    public LavaDart(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Lava Dart deals 1 damage to target creature or player.
        this.getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlayer());
        
        // Flashback-Sacrifice a Mountain.
        this.addAbility(new FlashbackAbility(new SacrificeTargetCost(new TargetControlledPermanent(filter)), TimingRule.INSTANT));
    }

    public LavaDart(final LavaDart card) {
        super(card);
    }

    @Override
    public LavaDart copy() {
        return new LavaDart(this);
    }
}
