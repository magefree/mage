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
package mage.sets.planechase2012;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.Mana;
import mage.abilities.condition.common.ControlsPermanentCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.mana.ActivateIfConditionManaAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.filter.common.FilterLandPermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;

/**
 *
 * @author LevelX2
 */
public class TaintedIsle extends CardImpl<TaintedIsle> {

    private static final FilterLandPermanent filter = new FilterLandPermanent("you control a swamp");
    static {
        filter.add(new SubtypePredicate("Swamp"));
    }

    public TaintedIsle(UUID ownerId) {
        super(ownerId, 128, "Tainted Isle", Rarity.UNCOMMON, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "PC2";

        // {tap}: Add {1} to your mana pool.
        this.addAbility(new ColorlessManaAbility());

        // {tap}: Add {U} or {B} to your mana pool. Activate this ability only if you control a Swamp.
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD,
                new BasicManaEffect(Mana.BlueMana),
                new TapSourceCost(),
                new ControlsPermanentCondition(filter, ControlsPermanentCondition.CountType.MORE_THAN, 0)));
        this.addAbility(new ActivateIfConditionManaAbility(
                Zone.BATTLEFIELD,
                new BasicManaEffect(Mana.BlackMana),
                new TapSourceCost(),
                new ControlsPermanentCondition(filter, ControlsPermanentCondition.CountType.MORE_THAN, 0)));

    }

    public TaintedIsle(final TaintedIsle card) {
        super(card);
    }

    @Override
    public TaintedIsle copy() {
        return new TaintedIsle(this);
    }
}
