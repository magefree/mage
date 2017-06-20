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
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfCombatTriggeredAbility;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.condition.common.ModeChoiceSourceCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public class CitadelSiege extends CardImpl {

    private final static String ruleTrigger1 = "&bull Khans &mdash; At the beginning of combat on your turn, put two +1/+1 counters on target creature you control.";
    private final static String ruleTrigger2 = "&bull Dragons &mdash; At the beginning of combat on each opponent's turn, tap target creature that player controls.";

    public CitadelSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{W}{W}");

        // As Citadel Siege enters the battlefield, choose Khans or Dragons.
        this.addAbility(new EntersBattlefieldAbility(new ChooseModeEffect("Khans or Dragons?","Khans", "Dragons"),null,
                "As {this} enters the battlefield, choose Khans or Dragons.",""));

        // * Khans - At the beginning of combat on your turn, put two +1/+1 counters on target creature you control.
        Ability ability = new ConditionalTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)), TargetController.YOU, false),
                new ModeChoiceSourceCondition("Khans"),
                ruleTrigger1);
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);

        // * Dragons - At the beginning of combat on each opponent's turn, tap target creature that player controls.
        ability = new ConditionalTriggeredAbility(
                new BeginningOfCombatTriggeredAbility(new TapTargetEffect(), TargetController.OPPONENT, false),
                new ModeChoiceSourceCondition("Dragons"),
                ruleTrigger2);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    @Override
    public void adjustTargets(Ability ability, Game game) {
        if (this.getAbilities().contains(ability) && ability.getRule().startsWith("&bull Dragons")) {
            FilterCreaturePermanent filter = new FilterCreaturePermanent("creature that player controls");
            filter.add(new ControllerIdPredicate(game.getCombat().getAttackerId()));
            ability.getTargets().clear();
            ability.addTarget(new TargetCreaturePermanent(filter));
        }
    }


    public CitadelSiege(final CitadelSiege card) {
        super(card);
    }

    @Override
    public CitadelSiege copy() {
        return new CitadelSiege(this);
    }
}
