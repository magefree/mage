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
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class StoneforgeMasterwork extends CardImpl {

    public StoneforgeMasterwork(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");
        this.subtype.add("Equipment");

        // Equipped creature gets +1/+1 for each other creature you control that shares a creature type with it.
        StoneforgeMasterworkDynamicValue countEnchantments = new StoneforgeMasterworkDynamicValue();
        Effect effect = new BoostEquippedEffect(countEnchantments, countEnchantments);
        effect.setText("Equipped creature gets +1/+1 for each other creature you control that shares a creature type with it");
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(2)));
    }

    public StoneforgeMasterwork(final StoneforgeMasterwork card) {
        super(card);
    }

    @Override
    public StoneforgeMasterwork copy() {
        return new StoneforgeMasterwork(this);
    }
}

class StoneforgeMasterworkDynamicValue implements DynamicValue {

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        Permanent equipment = game.getPermanent(sourceAbility.getSourceId());
        int xValue = 0;
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent equipped = game.getPermanent(equipment.getAttachedTo());
            if (equipped != null) {
                for (Permanent permanent : game.getBattlefield().getAllActivePermanents(StaticFilters.FILTER_PERMANENT_CREATURE, sourceAbility.getControllerId(), game)) {
                    if (!permanent.getId().equals(equipped.getId())) {
                        if (equipped.shareSubtypes(permanent, game)) {
                            xValue++;
                        }
                    }
                }
            }
        }
        return xValue;
    }

    @Override
    public StoneforgeMasterworkDynamicValue copy() {
        return new StoneforgeMasterworkDynamicValue();
    }

    @Override
    public String toString() {
        return "X";
    }

    @Override
    public String getMessage() {
        return "other creature you control that shares a creature type with it";
    }
}
