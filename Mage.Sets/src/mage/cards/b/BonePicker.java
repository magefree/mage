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
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MorbidCondition;
import mage.abilities.costs.AdjustingSourceCosts;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.util.CardUtil;
import mage.watchers.common.MorbidWatcher;

/**
 *
 * @author stravant
 */
public class BonePicker extends CardImpl {

    public BonePicker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        
        this.subtype.add("Bird");
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Bone Picker costs {3} less to cast if a creature died this turn.
        this.addAbility(new BonePickerCostAdjustmentAbility(), new MorbidWatcher());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

    }

    public BonePicker(final BonePicker card) {
        super(card);
    }

    @Override
    public BonePicker copy() {
        return new BonePicker(this);
    }
}

class BonePickerCostAdjustmentAbility extends SimpleStaticAbility implements AdjustingSourceCosts {

    public BonePickerCostAdjustmentAbility() {
        super(Zone.OUTSIDE, null);
    }

    public BonePickerCostAdjustmentAbility(final BonePickerCostAdjustmentAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new BonePickerCostAdjustmentAbility(this);
    }

    @Override
    public String getRule() {
        return "If a creature died this turn, {this} costs 3 less to cast.";
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (ability instanceof SpellAbility) { // Prevent adjustment of activated ability
            if (MorbidCondition.instance.apply(game, ability)) {
                CardUtil.adjustCost((SpellAbility) ability, 3);
            }
         }
    }
}
