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
package mage.sets.visions;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesBlockedTriggeredAbility;
import mage.abilities.effects.common.DamageAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.filter.common.FilterAttackingOrBlockingCreature;

/**
 *
 * @author LoneFox
 */
public class GoblinSwineRider extends CardImpl {

    public GoblinSwineRider(UUID ownerId) {
        super(ownerId, 81, "Goblin Swine-Rider", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "VIS";
        this.subtype.add("Goblin");
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Goblin Swine-Rider becomes blocked, it deals 2 damage to each attacking creature and each blocking creature.
        this.addAbility(new BecomesBlockedTriggeredAbility(new DamageAllEffect(2, new FilterAttackingOrBlockingCreature("attacking creature and each blocking creature")), false));
    }

    public GoblinSwineRider(final GoblinSwineRider card) {
        super(card);
    }

    @Override
    public GoblinSwineRider copy() {
        return new GoblinSwineRider(this);
    }
}
