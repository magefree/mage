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
package mage.sets.dragonsoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.condition.common.FormidableCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class SurrakTheHuntCaller extends CardImpl {

    public SurrakTheHuntCaller(UUID ownerId) {
        super(ownerId, 210, "Surrak, the Hunt Caller", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");
        this.expansionSetCode = "DTK";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Warrior");
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // <i>Formidable</i> - At the beginning of combat on your turn, if creatures you control have total power 8 or greater, target creature you control gains haste until end of turn.
        Ability ability = new ConditionalTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn), TargetController.YOU, false),
                FormidableCondition.getInstance(),
                "<i>Formidable</i> &mdash; At the beginning of combat on your turn, if creatures you control have total power 8 or greater, target creature you control gains haste until end of turn.");
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    public SurrakTheHuntCaller(final SurrakTheHuntCaller card) {
        super(card);
    }

    @Override
    public SurrakTheHuntCaller copy() {
        return new SurrakTheHuntCaller(this);
    }
}
