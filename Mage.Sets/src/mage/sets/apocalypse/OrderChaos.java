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

package mage.sets.apocalypse;

import java.util.UUID;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.combat.CantBlockAllEffect;
import mage.cards.SplitCard;
import mage.constants.Duration;
import mage.filter.common.FilterCreaturePermanent;
import mage.target.Target;
import mage.target.common.TargetAttackingCreature;

/**
 *
 * @author LevelX2
 */


public class OrderChaos extends SplitCard<OrderChaos> {

    public OrderChaos(UUID ownerId) {
        super(ownerId, 132, "Order", "Chaos", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{3}{W}", "{2}{R}", false);
        this.expansionSetCode = "APC";

        this.color.setWhite(true);
        this.color.setRed(true);

        // Order
        // Exile target attacking creature.
        getLeftHalfCard().getColor().setWhite(true);
        getLeftHalfCard().getSpellAbility().addEffect(new ExileTargetEffect());
        Target target = new TargetAttackingCreature();
        target.setRequired(true);
        getLeftHalfCard().getSpellAbility().addTarget(target);

        // Chaos
        // Creatures can't block this turn.
        getRightHalfCard().getColor().setRed(true);
        getRightHalfCard().getSpellAbility().addEffect(new CantBlockAllEffect(new FilterCreaturePermanent("Creatures"), Duration.EndOfTurn));

    }

    public OrderChaos(final OrderChaos card) {
        super(card);
    }

    @Override
    public OrderChaos copy() {
        return new OrderChaos(this);
    }
}
