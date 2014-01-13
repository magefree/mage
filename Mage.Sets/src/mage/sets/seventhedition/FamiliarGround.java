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
package mage.sets.seventhedition;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByMoreThanOneAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public class FamiliarGround extends CardImpl<FamiliarGround> {

    public FamiliarGround(UUID ownerId) {
        super(ownerId, 243, "Familiar Ground", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{2}{G}");
        this.expansionSetCode = "7ED";

        this.color.setGreen(true);

        // Each creature you control can't be blocked by more than one creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CantBeBlockedByMoreThanOneAllEffect(new FilterControlledCreaturePermanent())));
    }

    public FamiliarGround(final FamiliarGround card) {
        super(card);
    }

    @Override
    public FamiliarGround copy() {
        return new FamiliarGround(this);
    }
}
