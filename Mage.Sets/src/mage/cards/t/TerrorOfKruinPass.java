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
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.TransformedCondition;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityAllEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.filter.predicate.permanent.ControllerPredicate;

/**
 *
 * @author North
 */
public class TerrorOfKruinPass extends CardImpl {

    private static final String ruleText = "Werewolves you control have menace. (They can't be blocked except by two or more creatures.)";
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("Werewolf you control");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
        filter.add(new SubtypePredicate(SubType.WEREWOLF));
    }

    public TerrorOfKruinPass(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"");
        this.subtype.add("Werewolf");

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.transformable = true;

        this.color.setRed(true);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        this.addAbility(DoubleStrikeAbility.getInstance());
        // Werewolves you control have menace. (They can't be blocked except by two or more creatures.)
        Effect effect = new ConditionalContinuousEffect(new GainAbilityAllEffect(new MenaceAbility(), Duration.Custom, filter), new TransformedCondition(), ruleText);
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, effect));
        
        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Terror of Kruin Pass.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalTriggeredAbility(ability, TwoOrMoreSpellsWereCastLastTurnCondition.instance, TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public TerrorOfKruinPass(final TerrorOfKruinPass card) {
        super(card);
    }

    @Override
    public TerrorOfKruinPass copy() {
        return new TerrorOfKruinPass(this);
    }
}
