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
package mage.sets.conflux;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllNonCombatDamageToEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreatureInPlay;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author jeffwadsworth
 */
public class MarkOfAsylum extends CardImpl<MarkOfAsylum> {
    
    private static final FilterControlledCreatureInPlay filter = new FilterControlledCreatureInPlay("creatures you control");
    
    static {
        filter.getCreatureFilter().add(new ControllerPredicate(TargetController.YOU));
    }

    public MarkOfAsylum(UUID ownerId) {
        super(ownerId, 10, "Mark of Asylum", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}");
        this.expansionSetCode = "CON";

        this.color.setWhite(true);

        // Prevent all noncombat damage that would be dealt to creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventAllNonCombatDamageToEffect(Duration.WhileOnBattlefield, filter)));
        
    }

    public MarkOfAsylum(final MarkOfAsylum card) {
        super(card);
    }

    @Override
    public MarkOfAsylum copy() {
        return new MarkOfAsylum(this);
    }
}
